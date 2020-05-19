package modeltest;

import static org.junit.Assert.*;

import model.SaleStatus;
import org.junit.Test;

public class SaleStatusTest {
    @Test
    public void testSaleStatusToStringExpectUnderReviewToMake() {
        SaleStatus status = SaleStatus.UNDER_REVIEW_TO_MAKE;
        assertEquals("Under review to make", status.toString());
    }

    @Test
    public void testSaleStatusToStringExpectUnderReviewToModify() {
        SaleStatus status = SaleStatus.UNDER_REVIEW_TO_MODIFY;
        assertEquals("Under review to modify", status.toString());
    }

    @Test
    public void testSaleStatusToStringExpectConfirmed() {
        SaleStatus status = SaleStatus.CONFIRMED;
        assertEquals("Confirmed", status.toString());
    }
}
