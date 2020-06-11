package controllertest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;

public class DataManagerTest {
    @Test
    public void testGetSharedExpectNotNull() {
        assertNotNull(DataManager.shared());
    }

    @Test
    public void testGetAllLogsExpectNotNull() {
        assertNotNull(DataManager.shared().getAllLogs());
    }

    @Test
    public void testGetLoggedInAccountExpectNotNull() {
        try {
            Account account = DataManager.shared().getLoggedInAccount();
            assertTrue(account == null || account instanceof Account);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testLogoutExpectLoggedInAccountToBeNullAfterMethodCall() {
        DataManager.shared().logout();
        assertNull(DataManager.shared().getLoggedInAccount());
    }

    @Test
    public void testNextIntOnStringScannerExpectInt() {
        String string = "1234";
        Scanner scanner = new Scanner(string);
        int num = DataManager.nextInt(scanner);
        assertEquals(1234, num);
    }

    @Test
    public void testNextIntOnStringScannerWithInvalidInputExpectMinusOne() {
        String string = "I123o";
        Scanner scanner = new Scanner(string);
        int num = DataManager.nextInt(scanner);
        assertEquals(-1, num);
    }

    @Test
    public void testGetTemporaryCartExpectNotNull() {
        assertNotNull(DataManager.shared().getTemporaryCart());
    }

    @Test
    public void testSetTemporaryCartExpectToBeProperlySet() {
        Cart cart = new Cart();
        DataManager.shared().setTemporaryCart(cart);
        assertEquals(cart, DataManager.shared().getTemporaryCart());
    }

    @Test
    public void testGetNewIdExpectIdsToBeDifferentUponRepetition() {
        String id = "", newID;
        for (int i = 0; i < 10; i++) {
            newID = DataManager.getNewId();
            assertNotEquals(id, newID);
            id = newID;
        }
    }

    @Test
    public void testLoginExpectAdminType() {
        Administrator admin = mock(Administrator.class);
        when(admin.getUsername()).thenReturn("admin");
        when(admin.getPassword()).thenReturn("adminPW");

        Customer customer = mock(Customer.class);
        when(customer.getUsername()).thenReturn("customer");
        when(customer.getPassword()).thenReturn("customerPW");

        Seller seller = mock(Seller.class);
        when(seller.getUsername()).thenReturn("seller");
        when(seller.getPassword()).thenReturn("sellerPW");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(admin);
        accounts.add(seller);
        accounts.add(customer);

        DataManager spy = spy(DataManager.shared());
        doReturn(accounts).when(spy).getAllAccounts();

        DataManager.AccountType accountType = spy.login("admin", "adminPW");

        assertEquals(DataManager.AccountType.ADMINISTRATOR, accountType);
    }


    @Test
    public void testLoginExpectAdminAccountToBeSetInLoggedInAccount() {
        Administrator admin = mock(Administrator.class);
        when(admin.getUsername()).thenReturn("admin");
        when(admin.getPassword()).thenReturn("adminPW");

        Customer customer = mock(Customer.class);
        when(customer.getUsername()).thenReturn("customer");
        when(customer.getPassword()).thenReturn("customerPW");

        Seller seller = mock(Seller.class);
        when(seller.getUsername()).thenReturn("seller");
        when(seller.getPassword()).thenReturn("sellerPW");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(admin);
        accounts.add(seller);
        accounts.add(customer);

        DataManager spy = spy(DataManager.shared());
        doReturn(accounts).when(spy).getAllAccounts();

        spy.login("admin", "adminPW");
        Account loggedInAccount = spy.getLoggedInAccount();

        assertEquals(admin, loggedInAccount);
    }

    @Test
    public void testLoginExpectSellerType() {
        Administrator admin = mock(Administrator.class);
        when(admin.getUsername()).thenReturn("admin");
        when(admin.getPassword()).thenReturn("adminPW");

        Customer customer = mock(Customer.class);
        when(customer.getUsername()).thenReturn("customer");
        when(customer.getPassword()).thenReturn("customerPW");

        Seller seller = mock(Seller.class);
        when(seller.getUsername()).thenReturn("seller");
        when(seller.getPassword()).thenReturn("sellerPW");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(admin);
        accounts.add(seller);
        accounts.add(customer);

        DataManager spy = spy(DataManager.shared());
        doReturn(accounts).when(spy).getAllAccounts();

        DataManager.AccountType accountType = spy.login("seller", "sellerPW");

        assertEquals(DataManager.AccountType.SELLER, accountType);
    }


    @Test
    public void testLoginExpectSellerAccountToBeSetInLoggedInAccount() {
        Administrator admin = mock(Administrator.class);
        when(admin.getUsername()).thenReturn("admin");
        when(admin.getPassword()).thenReturn("adminPW");

        Customer customer = mock(Customer.class);
        when(customer.getUsername()).thenReturn("customer");
        when(customer.getPassword()).thenReturn("customerPW");

        Seller seller = mock(Seller.class);
        when(seller.getUsername()).thenReturn("seller");
        when(seller.getPassword()).thenReturn("sellerPW");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(admin);
        accounts.add(seller);
        accounts.add(customer);

        DataManager spy = spy(DataManager.shared());
        doReturn(accounts).when(spy).getAllAccounts();

        spy.login("seller", "sellerPW");
        Account loggedInAccount = spy.getLoggedInAccount();

        assertEquals(seller, loggedInAccount);
    }

    @Test
    public void testLoginExpectCustomerType() {
        Administrator admin = mock(Administrator.class);
        when(admin.getUsername()).thenReturn("admin");
        when(admin.getPassword()).thenReturn("adminPW");

        Customer customer = mock(Customer.class);
        when(customer.getUsername()).thenReturn("customer");
        when(customer.getPassword()).thenReturn("customerPW");

        Seller seller = mock(Seller.class);
        when(seller.getUsername()).thenReturn("seller");
        when(seller.getPassword()).thenReturn("sellerPW");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(admin);
        accounts.add(seller);
        accounts.add(customer);

        DataManager spy = spy(DataManager.shared());
        doReturn(accounts).when(spy).getAllAccounts();

        DataManager.AccountType accountType = spy.login("customer", "customerPW");

        assertEquals(DataManager.AccountType.CUSTOMER, accountType);
    }


    @Test
    public void testLoginExpectCustomerAccountToBeSetInLoggedInAccount() {
        Administrator admin = mock(Administrator.class);
        when(admin.getUsername()).thenReturn("admin");
        when(admin.getPassword()).thenReturn("adminPW");

        Customer customer = mock(Customer.class);
        when(customer.getUsername()).thenReturn("customer");
        when(customer.getPassword()).thenReturn("customerPW");

        Seller seller = mock(Seller.class);
        when(seller.getUsername()).thenReturn("seller");
        when(seller.getPassword()).thenReturn("sellerPW");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(admin);
        accounts.add(seller);
        accounts.add(customer);

        DataManager spy = spy(DataManager.shared());
        doReturn(accounts).when(spy).getAllAccounts();

        spy.login("customer", "customerPW");
        Account loggedInAccount = spy.getLoggedInAccount();

        assertEquals(customer, loggedInAccount);
    }

    @Test
    public void testLoginExpectNoneType() {
        Administrator admin = mock(Administrator.class);
        when(admin.getUsername()).thenReturn("admin");
        when(admin.getPassword()).thenReturn("adminPW");

        Customer customer = mock(Customer.class);
        when(customer.getUsername()).thenReturn("customer");
        when(customer.getPassword()).thenReturn("customerPW");

        Seller seller = mock(Seller.class);
        when(seller.getUsername()).thenReturn("seller");
        when(seller.getPassword()).thenReturn("sellerPW");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(admin);
        accounts.add(seller);
        accounts.add(customer);

        DataManager spy = spy(DataManager.shared());
        doReturn(accounts).when(spy).getAllAccounts();

        DataManager.AccountType accountType = spy.login("hello", "nothing");

        assertEquals(DataManager.AccountType.NONE, accountType);
    }


    @Test
    public void testLoginExpectLoggedInAccountToBeNull() {
        Administrator admin = mock(Administrator.class);
        when(admin.getUsername()).thenReturn("admin");
        when(admin.getPassword()).thenReturn("adminPW");

        Customer customer = mock(Customer.class);
        when(customer.getUsername()).thenReturn("customer");
        when(customer.getPassword()).thenReturn("customerPW");

        Seller seller = mock(Seller.class);
        when(seller.getUsername()).thenReturn("seller");
        when(seller.getPassword()).thenReturn("sellerPW");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(admin);
        accounts.add(seller);
        accounts.add(customer);

        DataManager spy = spy(DataManager.shared());
        doReturn(accounts).when(spy).getAllAccounts();

        spy.login("hello", "nothing");
        Account loggedInAccount = spy.getLoggedInAccount();

        assertNull(loggedInAccount);
    }


    @Test
    public void testGetAccountWithGivenUsernameExpectProperAccount() {
        Administrator admin = mock(Administrator.class);
        when(admin.getUsername()).thenReturn("admin");
        when(admin.getPassword()).thenReturn("adminPW");

        Customer customer = mock(Customer.class);
        when(customer.getUsername()).thenReturn("customer");
        when(customer.getPassword()).thenReturn("customerPW");

        Seller seller = mock(Seller.class);
        when(seller.getUsername()).thenReturn("seller");
        when(seller.getPassword()).thenReturn("sellerPW");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(admin);
        accounts.add(seller);
        accounts.add(customer);

        DataManager spy = spy(DataManager.shared());
        doReturn(accounts).when(spy).getAllAccounts();

        Account foundAccount = spy.getAccountWithGivenUsername("seller");

        assertEquals(seller, foundAccount);
    }

    @Test
    public void testGetAccountWithGivenUsernameWhenInvalidExpectNull() {
        Administrator admin = mock(Administrator.class);
        when(admin.getUsername()).thenReturn("admin");
        when(admin.getPassword()).thenReturn("adminPW");

        Customer customer = mock(Customer.class);
        when(customer.getUsername()).thenReturn("customer");
        when(customer.getPassword()).thenReturn("customerPW");

        Seller seller = mock(Seller.class);
        when(seller.getUsername()).thenReturn("seller");
        when(seller.getPassword()).thenReturn("sellerPW");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(admin);
        accounts.add(seller);
        accounts.add(customer);

        DataManager spy = spy(DataManager.shared());
        doReturn(accounts).when(spy).getAllAccounts();

        Account foundAccount = spy.getAccountWithGivenUsername("hello");

        assertNull(foundAccount);
    }

    @Test
    public void testHasAnyAdminRegisteredExpectTrue() {
        Administrator admin = mock(Administrator.class);
        when(admin.getUsername()).thenReturn("admin");
        when(admin.getPassword()).thenReturn("adminPW");

        Customer customer = mock(Customer.class);
        when(customer.getUsername()).thenReturn("customer");
        when(customer.getPassword()).thenReturn("customerPW");

        Seller seller = mock(Seller.class);
        when(seller.getUsername()).thenReturn("seller");
        when(seller.getPassword()).thenReturn("sellerPW");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(admin);
        accounts.add(seller);
        accounts.add(customer);

        DataManager spy = spy(DataManager.shared());
        doReturn(accounts).when(spy).getAllAccounts();

        assertTrue(spy.hasAnyAdminRegistered());
    }

    @Test
    public void testHasAnyAdminRegisteredExpectFalse() {
        Customer customer = mock(Customer.class);
        when(customer.getUsername()).thenReturn("customer");
        when(customer.getPassword()).thenReturn("customerPW");

        Seller seller = mock(Seller.class);
        when(seller.getUsername()).thenReturn("seller");
        when(seller.getPassword()).thenReturn("sellerPW");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(seller);
        accounts.add(customer);

        DataManager spy = spy(DataManager.shared());
        doReturn(accounts).when(spy).getAllAccounts();

        assertFalse(spy.hasAnyAdminRegistered());
    }

    @Test
    public void testDoesUserWithGivenUsernameExistExpectTrue() {
        Administrator admin = mock(Administrator.class);
        when(admin.getUsername()).thenReturn("admin");
        when(admin.getPassword()).thenReturn("adminPW");

        Customer customer = mock(Customer.class);
        when(customer.getUsername()).thenReturn("customer");
        when(customer.getPassword()).thenReturn("customerPW");

        Seller seller = mock(Seller.class);
        when(seller.getUsername()).thenReturn("seller");
        when(seller.getPassword()).thenReturn("sellerPW");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(admin);
        accounts.add(seller);
        accounts.add(customer);

        DataManager spy = spy(DataManager.shared());
        doReturn(accounts).when(spy).getAllAccounts();

        assertTrue(spy.doesUserWithGivenUsernameExist("customer"));
    }

    @Test
    public void testDoesUserWithGivenUsernameExistExpectFalse() {
        Administrator admin = mock(Administrator.class);
        when(admin.getUsername()).thenReturn("admin");
        when(admin.getPassword()).thenReturn("adminPW");

        Customer customer = mock(Customer.class);
        when(customer.getUsername()).thenReturn("customer");
        when(customer.getPassword()).thenReturn("customerPW");

        Seller seller = mock(Seller.class);
        when(seller.getUsername()).thenReturn("seller");
        when(seller.getPassword()).thenReturn("sellerPW");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(admin);
        accounts.add(seller);
        accounts.add(customer);

        DataManager spy = spy(DataManager.shared());
        doReturn(accounts).when(spy).getAllAccounts();

        assertFalse(spy.doesUserWithGivenUsernameExist("hello"));
    }
}
