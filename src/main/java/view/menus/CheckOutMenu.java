package view.menus;

import controller.DataManager;
import jdk.jfr.DataAmount;
import model.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CheckOutMenu extends Menu {
    public CheckOutMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
    }

    private void startGettingShippingDetails() {
    }

    private void startGettingCouponAndConfirmingPurchase() {
    }

    @Override
    public void show() {
        if (!checkIfCustomer()) return;
        Cart cart = ((Customer)(DataManager.shared().getLoggedInAccount())).getCart();
        HashMap<Product, Integer> products = cart.getProducts();
        Customer customer = (Customer) DataManager.shared().getLoggedInAccount();
        String address = getAddress(customer);
        String phoneNumber = getPhoneNumber(customer);
        String couponCode;
        Coupon coupon = getCoupon(); // if null, there is no coupon code
        String orderId = DataManager.getNewId();
        System.out.println("\nOrder #" + orderId);
        Iterator it = products.entrySet().iterator();
        double totalPrice = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            double price = ((Product)pair.getKey()).getPrice();
            for (Sale sale : DataManager.shared().getAllSales()) {
                // TODO: What is the next line's "suspicious code"??
                if (sale.getProducts().contains(pair.getKey())) {
                    price -= sale.getDiscountAmount();
                    if (price < 1) price = 1;
                }
            }
            totalPrice += price;
            System.out.println(pair.getValue() + "x\t" + ((Product)pair.getKey()).getName() + "\t$" + price);
        }
        System.out.println("");
        System.out.println("Total price (before discount, with sales effected): $" + totalPrice);
        double priceAfterDiscount = totalPrice * (1 - (coupon.getDiscountPercent() / 100.0));
        if (totalPrice - priceAfterDiscount > coupon.getMaximumDiscount()) {
            priceAfterDiscount = totalPrice - coupon.getMaximumDiscount();
        }
        System.out.println("Amount of discount: -$" + (totalPrice - priceAfterDiscount));
        System.out.println("Price to pay: $" + priceAfterDiscount);
        if (customer.getCredit() < priceAfterDiscount) {
            System.out.println("You have $" + customer.getCredit() + " in your account; unfortunately, you're not able to purchase these products.");
        } else {
            System.out.print("If you are sure you want to purchase these products, type \"yes\"; anything else will be regarded as a discard message: ");
            String response = scanner.nextLine().trim();
            if (response.equalsIgnoreCase("yes")) {
                customer.decreaseCredit((int)priceAfterDiscount);
                coupon.decrementRemainingUsagesCountForAccount(DataManager.shared().getLoggedInAccount());
                PurchaseLog purchaseLog = new PurchaseLog(DataManager.getNewId(), LocalDateTime.now(), (int)totalPrice, (int)(totalPrice - priceAfterDiscount), products, DeliveryStatus.ORDERED, customer);
                DataManager.shared().addLog(purchaseLog);
                System.out.println("Thank you for your purchase!");
            }
        }
    }

    private Coupon getCoupon() {
        String couponCode;
        Coupon coupon = null;
        while (true) {
            System.out.print("Enter a coupon code if you have one, or only enter if you don't have any: ");
            couponCode = scanner.nextLine();
            if (couponCode.equals("")) {
                System.out.println("Proceeding purchase with no coupon code");
                break;
            }
            coupon = DataManager.shared().getCouponWithId(couponCode);
            if (coupon == null) {
                System.out.println("Invalid coupon code");
                continue;
            }
            // TODO: Coupon remaining usage count, account is permitted or not, and start and date time are all unchecked...!!!
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
