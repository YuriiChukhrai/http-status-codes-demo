package core.yc.qa.test.utils;

import io.qameta.allure.Attachment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class BaseUtils {

    private static final Log logger = LogFactory.getLog("org.springframework.test.web.servlet.result");

    private BaseUtils() {
        throw new UnsupportedOperationException("Illegal access to private constructor");
    }

    /* This method make Text attachment for Allure report */
    @Attachment(value = "{0}", type = "text/plain")
    public static synchronized String attachText(final String nameOfAttachment, final String bodyOfMessage) {

        logger.info(String.format("TID [%d] - Attached to allure file [%s].", Thread.currentThread().getId(), nameOfAttachment));

        return bodyOfMessage;
    }
}
