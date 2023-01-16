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

        //CT4
        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, "abc", null,
                null, null, null, null
        ));

        //CT5
        requestLogDetails.setResponseStatus(200);
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, "201",
                null, null, null
        ));

        //CT6
        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, "200",
                null, null, null
        ));

        //CT7
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, "abc 180", null,
                null, null, null
        ));

        //CT8
        requestLogDetails.setResponseTime(200L);
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, "> 250", null,
                null, null, null
        ));

        //CT9
        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, "> 180", null,
                null, null, null
        ));


        //CT10
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, ">= 250", null,
                null, null, null
        ));

        //CT11
        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, ">= 200", null,
                null, null, null
        ));

        //CT12
        requestLogDetails.setResponseTime(250L);
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, "< 250", null,
                null, null, null
        ));

        //CT13
        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, "< 251", null,
                null, null, null
        ));

        //CT14
        requestLogDetails.setResponseTime(251L);
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, "<= 250", null,
                null, null, null
        ));

        //CT15
        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, "<= 251", null,
                null, null, null
        ));

        //CT16
        RequestLogUser userInfo = new RequestLogUser();
        userInfo.setRegkey("abc");
        requestLogDetails.setUserInfo(userInfo);
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, null,
                "ab", null, null
        ));

        //CT17
        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, null,
                "abc", null, null
        ));



        userInfo.setEmail("abc");
        //CT18
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, null,
                null, "ab", null
        ));

        //CT19
        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, null,
                null, "abc", null
        ));

        //CT20
        userInfo.setGoogleId("abc");
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null,
                null, null, null, "ab"
        ));

        //CT21
        assertTrue(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null,
                null, null, null, "abc"
        ));

        //CT22
        requestLogDetails.setUserInfo(null);
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, null,
                "abc", null, null
        ));

        //CT23
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, null,
                null, "abc", null
        ));

        //CT24
        assertFalse(new LocalLoggingService().isRequestFilterSatisfied(
                requestLogDetails, null, null, null,
                null, null, "abc"
        ));
    }
}
