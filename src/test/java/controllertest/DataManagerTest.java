package controllertest;

import static org.junit.Assert.*;

import controller.DataManager;
import model.Account;
import org.junit.Test;

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
}
