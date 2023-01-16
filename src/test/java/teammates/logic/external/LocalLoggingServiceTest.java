package teammates.logic.external;

import org.testng.annotations.Test;
import teammates.common.datatransfer.logs.EmailSentLogDetails;
import teammates.common.datatransfer.logs.RequestLogDetails;
import teammates.common.datatransfer.logs.RequestLogUser;
import teammates.test.BaseTestCase;

public class LocalLoggingServiceTest extends BaseTestCase {
    @Test
    public void testIsRequestFilterSatisfied() {
        //CT1
        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                null, null, null,
                null, null, null, null));

        //CT2
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                new EmailSentLogDetails(), "ABC", null,
                null, null, null, null));

        //CT3
        RequestLogDetails requestLogDetails = new RequestLogDetails();
        requestLogDetails.setActionClass("abc");
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, "ab", null,
                null, null, null, null
        ));

        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, "abc", null,
                null, null, null, null
        ));

        //CT4
        requestLogDetails.setResponseStatus(200);
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, "201",
                null, null, null
        ));

        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, "200",
                null, null, null
        ));

        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, "abc 180", null,
                null, null, null
        ));

        //CT5
        requestLogDetails.setResponseTime(200L);
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, "> 250", null,
                null, null, null
        ));

        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, "> 180", null,
                null, null, null
        ));


        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, ">= 250", null,
                null, null, null
        ));

        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, ">= 200", null,
                null, null, null
        ));

        requestLogDetails.setResponseTime(250L);
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, "< 250", null,
                null, null, null
        ));

        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, "< 251", null,
                null, null, null
        ));

        requestLogDetails.setResponseTime(251L);
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, "<= 250", null,
                null, null, null
        ));

        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, "<= 251", null,
                null, null, null
        ));

        RequestLogUser userInfo = new RequestLogUser();
        userInfo.setRegkey("abc");
        requestLogDetails.setUserInfo(userInfo);
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, null,
                "ab", null, null
        ));

        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, null,
                "abc", null, null
        ));



        userInfo.setEmail("abc");
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, null,
                null, "ab", null
        ));

        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, null,
                null, "abc", null
        ));

        userInfo.setGoogleId("abc");
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null,
                null, null, null, "ab"
        ));

        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null,
                null, null, null, "abc"
        ));

        requestLogDetails.setUserInfo(null);
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, null,
                "abc", null, null
        ));

        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, null,
                null, "abc", null
        ));

        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, null,
                null, null, "abc"
        ));
    }
}
