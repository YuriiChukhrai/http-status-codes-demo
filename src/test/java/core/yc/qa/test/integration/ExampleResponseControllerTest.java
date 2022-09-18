package core.yc.qa.test.integration;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import core.yc.qa.HttpStatusCodesApplication;
import core.yc.qa.http.codes.entity.HttpCode;
import core.yc.qa.test.TestGroups;
import core.yc.qa.test.utils.BaseTestUtils;
import core.yc.qa.test.utils.CustomMvcResultHandlers;
import io.qameta.allure.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

/**
 * @author limit (Yurii Chukhrai)
 *
 * Controller layer unit tests
 */

@SpringBootTest(classes = HttpStatusCodesApplication.class)
public class ExampleResponseControllerTest extends AbstractTestNGSpringContextTests {

    private static final String exampleResponseControllerEndpointPath = "/api/v1/http/code/example";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @BeforeClass
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /*
     * I do not think it makes sense to implement integration tests for all [ 66 ]
     * codes presented in this demo to prove my skills in that :))). <br>
     *
     * So, I will implement only 5 of them (one for each of the categories: 1xx, 2xx, 3xx, 4xx, 5xx).
     */

    @DataProvider(parallel = true)
    public Object[][] getHttpCodeByCodeDataProvider() {
        return new Object[][]{
                {new HttpCode().setCode(100).setCategory("1** Informational").setReason_phrase("Continue").setDefinition("This interim response indicates that the client should continue the request or ignore the response if the request is already finished.")},
                {new HttpCode().setCode(200).setCategory("2** Success").setReason_phrase("OK").setDefinition("The request succeeded. The result meaning of 'success' depends on the HTTP method:\\nGET: The resource has been fetched and transmitted in the message body.\\nHEAD: The representation headers are included in the response without any message body.\\nPUT or POST: The resource describing the result of the action is transmitted in the message body.n\\TRACE: The message body contains the request message as received by the server.")},
                {new HttpCode().setCode(300).setCategory("3** Redirection").setReason_phrase("Multiple Choices").setDefinition("The request has more than one possible response. The user agent or user should choose one of them. (There is no standardized way of choosing one of the responses, but HTML links to the possibilities are recommended so the user can pick.)")},
                {new HttpCode().setCode(400).setCategory("4** Client Error").setReason_phrase("Bad Request").setDefinition("The server could not understand the request due to invalid syntax.")},
                {new HttpCode().setCode(500).setCategory("5** Server Error").setReason_phrase("Internal Server Error").setDefinition("The server has encountered a situation it does not know how to handle.")}
        };
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("JSON"), @Feature("getHttpCodeByCode")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("001"), @TmsLink("002")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Flaky
    @Severity(SeverityLevel.MINOR)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")

    @SneakyThrows
    @Test(enabled = true, groups = TestGroups.INTEGRATION, dataProvider = "getHttpCodeByCodeDataProvider")
    public void getHttpCodeByCodeJsonTest(HttpCode expectedResponse) {

        mvc.perform(get(exampleResponseControllerEndpointPath + String.format("/%s", expectedResponse.getCode() )).accept(MediaType.APPLICATION_JSON_VALUE))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect( status().is(expectedResponse.getCode().intValue()) )
                .andExpect(jsonPath("$.id", notNullValue() ))
                .andExpect(jsonPath("$.code", is(expectedResponse.getCode()) ))
                .andExpect(jsonPath("$.category", is(expectedResponse.getCategory()) ))
                .andExpect(jsonPath("$.reason_phrase", is(expectedResponse.getReason_phrase()) ))
                .andExpect(jsonPath("$.definition", is(expectedResponse.getDefinition()) ));
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("XML"), @Feature("getHttpCodeByCode")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.MINOR)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")

    @SneakyThrows
    @Test(enabled = true, groups = TestGroups.INTEGRATION, dataProvider = "getHttpCodeByCodeDataProvider")
    public void getHttpCodeByIdXmlTest(HttpCode expectedResponse) {

        final String fooHttpCodeXmlString = new XmlMapper().writeValueAsString(expectedResponse);
        BaseTestUtils.attachText("Request Body.", fooHttpCodeXmlString);
        mvc.perform(get(exampleResponseControllerEndpointPath + String.format("/%s", expectedResponse.getCode() )).accept(MediaType.APPLICATION_XML_VALUE))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE))
                .andExpect( status().is(expectedResponse.getCode().intValue()) )

                .andExpect(xpath("//id").string(notNullValue()))
                .andExpect(xpath("//code").string(expectedResponse.getCode().toString()))
                .andExpect(xpath("//category").string(expectedResponse.getCategory()))
                .andExpect(xpath("//reason_phrase").string(expectedResponse.getReason_phrase()))
                .andExpect(xpath("//definition").string(expectedResponse.getDefinition()));
    }
}
