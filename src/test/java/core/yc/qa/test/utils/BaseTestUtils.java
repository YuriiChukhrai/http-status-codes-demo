package core.yc.qa.test.utils;

import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BaseTestUtils {

    private BaseTestUtils() {
        throw new UnsupportedOperationException("Illegal access to private constructor");
    }

    /* This method make Text attachment for Allure report */
    @Attachment(value = "{0}", type = "text/plain")
    public static synchronized String attachText(final String nameOfAttachment, final String bodyOfMessage) {

        log.info(String.format("TID [%d] - Attached to allure file [%s].", Thread.currentThread().getId(), nameOfAttachment));

        return bodyOfMessage;
    }
}
