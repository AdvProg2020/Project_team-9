package modeltest;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Cart;
import com.sasp.saspstore.model.Product;

import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class CartTest {

    @Test
    public void testAddProductToCartAndGetProductsExpectCartToContainOneAndOnlyOneProduct() {
        Product product = mock(Product.class);
        when(product.getProductId()).thenReturn("1");

        Cart cart = new Cart();
        cart.addProduct(product);

        Set<String> productIds = cart.getProductIds();
        assertEquals(1, productIds.size());
    }
    @Test

    public void testAddMultipleProductToCartAndGetProductsExpectCartToContainProperNumberOfProduct() {
        Product product1 = mock(Product.class);
        when(product1.getProductId()).thenReturn("1");
        Product product2 = mock(Product.class);
        when(product2.getProductId()).thenReturn("2");
        Product product3 = mock(Product.class);
        when(product3.getProductId()).thenReturn("3");

        Cart cart = new Cart();
        cart.addProduct(product1);
        cart.addProduct(product2);
        cart.addProduct(product3);

        Set<String> productIds = cart.getProductIds();
        assertEquals(3, productIds.size());
    }

    @Test
    public void testAddProductToCartAndGetProductsExpectCartToContainTheProduct() {
        Product product = mock(Product.class);
        when(product.getProductId()).thenReturn("1");

        assertNotNull(product);

        Cart cart = new Cart();
        cart.addProduct(product);

        Set<String> productIds = cart.getProductIds();
        assertEquals("1", productIds.iterator().next());
    }

    @Test
    public void testGetProductCountExpectProperNumberOfProducts() {
        Cart cart = new Cart();

        Product product1 = mock(Product.class);
        when(product1.getProductId()).thenReturn("1");
        Product product2 = mock(Product.class);
        when(product2.getProductId()).thenReturn("2");
        Product product3 = mock(Product.class);
        when(product3.getProductId()).thenReturn("3");

        cart.addProduct(product1);
        cart.addProduct(product2);
        cart.addProduct(product3);
        assertEquals(cart.toString(), 3, cart.getProductCount());
    }

    @Test
    public void testGetProductsExpectProperProductObjects() {
        Cart cart = new Cart();

        Product product1 = mock(Product.class);
        when(product1.getProductId()).thenReturn("1");
        Product product2 = mock(Product.class);
        when(product2.getProductId()).thenReturn("2");
        Product product3 = mock(Product.class);
        when(product3.getProductId()).thenReturn("3");

        cart.addProduct(product1);
        cart.addProduct(product2);
        cart.addProduct(product3);

        DataManager spy = spy(DataManager.shared());
        doReturn(product1).when(spy).getProductWithId("1");
        doReturn(product2).when(spy).getProductWithId("2");
        doReturn(product3).when(spy).getProductWithId("3");

        HashMap<Product, Integer> products = cart.getProducts();
        assertEquals(products.toString(), 3, products.size());
    }

//    @Test
//    public void testSetProductsExpectProductIdsToBeSetInResult() {
//        Product product1 = mock(Product.class);
//        when(product1.getProductId()).thenReturn("1");
//        Product product2 = mock(Product.class);
//        when(product2.getProductId()).thenReturn("2");
//        Product product3 = mock(Product.class);
//        when(product3.getProductId()).thenReturn("3");
//
//        HashMap<Product, Integer> products = new HashMap<>();
//        products.put(product1, 12321);
//        products.put(product2, 45);
//        products.put(product3, 23);
//
//        DataManager spy = spy(DataManager.shared());
//
//        cart.setProducts();
//    }
}
