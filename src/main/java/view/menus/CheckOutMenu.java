package view.menus;

import controller.DataManager;
import jdk.jfr.DataAmount;
import model.*;

import java.time.LocalDateTime;
import java.util.*;

public class CheckOutMenu extends Menu {
    public CheckOutMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
    }

    @Override
    public void show() {
        if (!checkIfCustomer()) return;
        Cart cart = ((Customer) (DataManager.shared().getLoggedInAccount())).getCart();
        HashMap<Product, Integer> products = cart.getProducts();
        Customer customer = (Customer) DataManager.shared().getLoggedInAccount();
        getAddress(customer);
        getPhoneNumber(customer);
        Coupon coupon = getCoupon(customer); // if null, there is no coupon code
        String orderId = DataManager.getNewId();
        System.out.println("\nOrder #" + orderId);
        Iterator it = products.entrySet().iterator();
        double totalPrice = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            double price = ((Product) pair.getKey()).getPrice() * (int) pair.getValue();
            if (((Product) pair.getKey()).getNumberAvailable() > 0) {
                for (Sale sale : DataManager.shared().getAllSales()) {
                    // TODO: What is the next line's "suspicious code"??
                    if (sale.getProducts().contains(pair.getKey()) && sale.getStartTime().isBefore(LocalDateTime.now()) && sale.getEndTime().isAfter(LocalDateTime.now())) {
                        price -= sale.getDiscountAmount() * (int) pair.getValue();
                        if (price < 1) price = 1;
                    }
                }
                totalPrice += price;
                System.out.println(pair.getValue() + "x\t" + ((Product) pair.getKey()).getName() + "\t$" + price);
            }
        }
        System.out.println("\nTotal price (before discount, with sales effected): $" + totalPrice);
        if (coupon == null) {
            coupon = new Coupon(DataManager.getNewId(), new ArrayList<>());
            coupon.setDiscountPercent(0);
            coupon.setMaximumDiscount(0);
        }
        double priceAfterDiscount = totalPrice * (1 - (coupon.getDiscountPercent() / 100.0));
        if (totalPrice - priceAfterDiscount > coupon.getMaximumDiscount()) {
            priceAfterDiscount = totalPrice - coupon.getMaximumDiscount();
        }
        if (totalPrice > 1000) {
            priceAfterDiscount -= 100;
            System.out.println("There is an extra $100 discount for you, because you've purchased more than $1000!");
        }
        System.out.println("Amount of discount: -$" + (totalPrice - priceAfterDiscount));
        System.out.println("Price to pay: $" + priceAfterDiscount);
        if (customer.getCredit() < priceAfterDiscount) {
            System.out.println("You have $" + customer.getCredit() + " in your account; unfortunately, you're not able to purchase these products.");
        } else {
            System.out.print("If you are sure you want to purchase these products, type \"yes\"; anything else will be regarded as a discard message: ");
            String response = scanner.nextLine().trim();
            if (response.equalsIgnoreCase("yes")) {
                customer.decreaseCredit((int) priceAfterDiscount);
                coupon.decrementRemainingUsagesCountForAccount(DataManager.shared().getLoggedInAccount());
                PurchaseLog purchaseLog = new PurchaseLog(DataManager.getNewId(), LocalDateTime.now(), (int) totalPrice, (int) (totalPrice - priceAfterDiscount), products, DeliveryStatus.ORDERED, customer);
                DataManager.shared().addLog(purchaseLog);
                Iterator it2 = products.entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry pair = (Map.Entry) it2.next();
                    if (((Product) pair.getKey()).getNumberAvailable() > 0) {
                        ((Product) pair.getKey()).decrementNumberAvailable();
                        HashMap<Product, Integer> productsHashMap = new HashMap<>();
                        productsHashMap.put((Product) pair.getKey(), 1);
                        for (int i = 0; i < (int) pair.getValue(); i++) {
                            SellLog sellLog = new SellLog(LocalDateTime.now(), productsHashMap, DataManager.getNewId(), ((Product) pair.getKey()).getPrice(), ((Product) pair.getKey()).getCurrentSeller(), (int) ((totalPrice - priceAfterDiscount) / (double) products.size()), DeliveryStatus.ORDERED);
                            DataManager.shared().addLog(sellLog);
                        }
                        ((Product) pair.getKey()).getCurrentSeller().increaseCredit((int) priceAfterDiscount);
                    }
                }
                ((Customer) DataManager.shared().getLoggedInAccount()).emptyCart();
                DataManager.saveData();
                System.out.println("Thank you for your purchase!");
            }
        }
        parentMenu.show();
        parentMenu.execute();
    }

    private Coupon getCoupon(Customer customer) {
        String couponCode;
        Coupon coupon = null;
        while (true) {
            System.out.print("Enter a coupon code if you have one, or only enter if you don't have any (try entering \"chance\" too, you may have had a secret coupon code!): ");
            couponCode = scanner.nextLine();
            if (couponCode.equals("")) {
                System.out.println("Proceeding purchase with no coupon code");
                break;
            }
            if (couponCode.equals("chance")) {
                Random random = new Random();
                int r = random.nextInt(10);
                if (r == 2) {
                    int percent = random.nextInt(90);
                    System.out.println("Congratulations! You are rewarded a coupon with " + percent + " percent discount!");
                    coupon = new Coupon(DataManager.getNewId(), new ArrayList<>());
                    coupon.setDiscountPercent(percent);
                    coupon.setMaximumDiscount(1000);
                    return coupon;
                } else {
                    System.out.println("Sorry... :(\nProceeding purchase with no coupon code");
                    break;
                }
            }
            coupon = DataManager.shared().getCouponWithId(couponCode);
            if (coupon == null || coupon.getStartTime().isAfter(LocalDateTime.now()) || coupon.getEndTime().isBefore(LocalDateTime.now())) {
                System.out.println("Invalid coupon code");
                continue;
            }
            if (coupon.getRemainingUsagesCount().get(customer.getUsername()) < 1) {
                System.out.println("You can't use this coupon code anymore");
                continue;
            }
            break;
        }
        return coupon;
    }

    private String getPhoneNumber(Customer customer) {
        String phoneNumber;
        while (true) {
            if (customer.getPhoneNumber().equals("")) {
                System.out.print("Enter your phone number: ");
            } else {
                System.out.print("Enter your phone number, or only press enter if your phone number is still " + customer.getPhoneNumber() + ": ");
            }
            phoneNumber = scanner.nextLine();
            if (phoneNumber.equals("") && !(customer.getPhoneNumber().equals(""))) {
                phoneNumber = customer.getPhoneNumber();
                break;
            }
            if (phoneNumber.equals("")) {
                System.out.println("Wrong phone number. Please try again.");
                continue;
            }
            customer.setPhoneNumber(phoneNumber);
            break;
        }
        return phoneNumber;
    }

    private String getAddress(Customer customer) {
        String address;
        while (true) {
            if (customer.getAddress().equals("")) {
                System.out.print("Enter your shipping address: ");
            } else {
                System.out.print("Enter your shipping address, or only press enter if your address is still " + customer.getAddress() + ": ");
            }
            address = scanner.nextLine();
            if (address.equals("") && !(customer.getAddress().equals(""))) {
                address = customer.getAddress();
                break;
            }
            if (address.equals("")) {
                System.out.println("Wrong address. Please try again.");
                continue;
            }
            customer.setAddress(address);
            break;
        }
        return address;
    }

    private boolean checkIfCustomer() {
        if (DataManager.shared().getLoggedInAccount() == null || !(DataManager.shared().getLoggedInAccount() instanceof Customer)) {
            System.out.println("You should log in as a customer to proceed purchase");
            return false;
        }
        return true;
    }

    @Override
    protected void showHelp() {

    }
}
