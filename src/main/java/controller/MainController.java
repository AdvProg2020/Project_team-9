package controller;

import model.*;

public class MainController {
    private Account loggedInAccount;
    private Product[] temporaryCart;

    public MainController() {
    }

    public void login(String username, String password) {
    }

    public void registerCustomer(String username, String password, String firstName, String lastName, String email, String phone) {
    }

    public void registerSeller(String username, String password, String firstName, String lastName, String email, String phone) {
    }

    public void registerAdministrator(String username, String password, String firstName, String lastName, String email, String phone) {
    }

    public void showPersonalInfo() {
    }

    public void changePassword(String oldPassword, String newPassword) {
    }

    public void setFirstName(String newValue) {
    }

    public void setLastName(String newValue) {
    }

    public void setEmail(String newValue) {
    }

    public void setPhoneNumber(String newValue) {
    }

    public void setCompanyDetails(String newValue) {
    }

    public String getUsername() {
    }

    ;

    public String getFirstName() {
    }

    ;

    public String getLastName() {
    }

    ;

    public String getEmail() {
    }

    ;

    public String getPhone() {
    }

    ;

    public String getCompanyDetails() {
    }

    ;

    public Account[] getAllAccounts() {
    }

    ;

    public String getLoggedInAccountDetails() {
    }

    ;

    public Account getLoggedInAccount() {
    }

    ;

    public void deleteAccount(String username) {
    }

    ;

    public void removeProductFromAllProducts(int productId) {
    }

    public void addCoupon(int discountPercent, int maximumDiscount) {
    }

    public Coupon[] getCoupons() {
    }

    public void removeCoupon(Coupon coupon) {
    }

    public Request[] getAdminRequests() {
    }

    public void acceptAdminRequest(Request request) {
    }

    public void declineAdminRequest(Request request) {
    }

    public Category[] getAllCategories() {
    }

    public Category[] getSubCategories(Category category) {
    }

    public Product[] getAllProductsInCategory(Category category) {
    }

    public void addCategory(Category category) {
    }

    public void appendCategory(Category category) {
    }

    public void removeCategory(Category category) {
    }

    public boolean addProductToCategory(Product product, Category category) {
    }

    public Product[] getSellsHistory() {
    }

    public Product[] getSellerProducts() {
    }

    public boolean addProductToCart(Product product) {
    }

    public boolean checkout() {
    }

    public void addProduct(Product product) {
    }

    public void removeProduct(Product product) {
    }

    public void getAllSales() {
    }

    public void addSale(Sale sale) {
    }

    public Product[] getCart() {
    }

    public void incrementCountOfProductInCart(Product product) {
    }

    public void requestEditingProductName(Product oldProduct, Product newProduct) {
    }

    public void requestEditingProductBrand(Product oldProduct, Product newProduct) {
    }

    public void requestEditingProductPrice(Product oldProduct, Product newProduct) {
    }

    public void requestEditingProductCategory(Product oldProduct, Product newProduct) {
    }

    public void requestEditingProductDescription(Product oldProduct, Product newProduct) {
    }

    public void requestEditingNumberAvailable(Product oldProduct, Product newProduct) {
    }

    public void requestAddingProduct(Product product) {
    }

    public void requestAddingSale(Sale sale) {
    }

    public void requestAddingProductToSaLe(Sale sale, int productId) {
    }

    public void requestRemovingProductFromSale(Sale sale, int productId) {
    }

    public void requestEditingSaleStatus(Sale oldSale, Sale newSale) {
    }

    public void requestEditingDiscountAmount(Sale oldSale, Sale newSale) {
    }

    public void requestEditingSaleStartTime(Sale oldSale, Sale newSale) {
    }

    public void requestEditingSaleEndTime(Sale oldSale, Sale newSale) {
    }

    public void requestEditingSaleDeliveryStatus(Sale oldSale, Sale newSale) {
    }

    public void submitCommentForProduct(Comment comment, Product product) {
    }

    public void submitScoreForProduct(Score score, Product product) {
    }

    public void addCoupon(Coupon coupon) {
    }

    public void editCoupon(Coupon oldCoupon, Coupon newCoupon) {
    }

}
