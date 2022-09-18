package core.yc.qa.test.utils;

import lombok.SneakyThrows;
import org.springframework.lang.Nullable;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.PrintingResultHandler;
import org.springframework.util.CollectionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @info
 * The implementation based on the {@link MockMvcResultHandlers}
 * */
public abstract class CustomMvcResultHandlers  {

    /**
     * @info implement Allure attachment {@link io.qameta.allure.Attachment} for Allure report. <br/>
     * This approach preferred if you have more than 3 TC's :). The very important requirement for debugging/investigation - it's to have all possible context (in out example of HTTP request information)<br/>
     * Like alternative, for console debugging, you can use {@code mvc.andDo(print())} to print the result info to console.
     *
     * */
    public static ResultHandler allureAttachment() {
        return new CustomMvcResultHandlers.AllureResultHandler();
    }

    private static class AllureResultHandler implements ResultHandler {
        private AllureResultHandler() {}

        @SneakyThrows
        public void handle(MvcResult result) {
            StringWriter stringWriter = new StringWriter();
            ResultHandler printingResultHandler = new CustomMvcResultHandlers.PrintWriterPrintingResultHandler(new PrintWriter(stringWriter));
                printingResultHandler.handle(result);
            BaseTestUtils.attachText("MvcResult", stringWriter.toString());
        }
    }

    private static class PrintWriterPrintingResultHandler extends PrintingResultHandler {
        public PrintWriterPrintingResultHandler(PrintWriter writer) {
            super(new ResultValuePrinter() {
                public void printHeading(String heading) {
                    writer.println();
                    writer.println(String.format("%s:", heading));
                }

                public void printValue(String label, @Nullable Object value) {
                    if (value != null && value.getClass().isArray()) {
                        value = CollectionUtils.arrayToList(value);
                    }

                    writer.println(String.format("%17s = %s", label, value));
                }
            });
        }
    }
}
