package core.yc.qa.test.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import core.yc.qa.HttpStatusCodesApplication;
import core.yc.qa.http.codes.entity.HttpCode;
import core.yc.qa.http.codes.util.QueryParams;
import core.yc.qa.test.TestGroups;
import core.yc.qa.test.utils.BaseTestUtils;
import core.yc.qa.test.utils.CustomMvcResultHandlers;
import io.qameta.allure.*;
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

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author limit (Yurii Chukhrai)
 *
 * Controller layer unit tests
 */

@SpringBootTest(classes = HttpStatusCodesApplication.class)
public class HttpCodeControllerTest extends AbstractTestNGSpringContextTests {

    private static final String httpCodeControllerEndpointPath = "/api/v1/http/code";
    private static final HttpCode fooHttpCodeJson = new HttpCode().setCode(555).setCategory("5** Server Error").setReason_phrase("Bad Connection").setDefinition("We can't guarantee the network connection.");
    private static final HttpCode fooHttpCodeXml = new HttpCode().setCode(556).setCategory("5** Server Error").setReason_phrase("Bad Connection").setDefinition("We can't guarantee the network connection.");

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @BeforeClass
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DataProvider(parallel = true)
    public Iterator<Long> getHttpCodeByIdDataProvider() {
        //Generate ID's sequence (from 1 to 66)
        return Stream.iterate(1L, i -> i + 1).limit(66).collect(Collectors.toList()).iterator();
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("JSON"), @Feature("getHttpCodeById")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("001"), @TmsLink("002")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Flaky
    @Severity(SeverityLevel.MINOR)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION, dataProvider = "getHttpCodeByIdDataProvider")
    public void getHttpCodeByIdJsonTest(Long id) throws Exception {

        mvc.perform(get(httpCodeControllerEndpointPath + String.format("/%s", id))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.code", is(both(greaterThan(0)).and(lessThan(600)))))
                .andExpect(jsonPath("$.category", notNullValue()))
                .andExpect(jsonPath("$.reason_phrase", notNullValue()))
                .andExpect(jsonPath("$.definition", notNullValue()));
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("XML"), @Feature("getHttpCodeById")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("001"), @TmsLink("002")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.MINOR)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION, dataProvider = "getHttpCodeByIdDataProvider")
    public void getHttpCodeByIdXmlTest(Long id) throws Exception {

        this.mvc.perform(get(httpCodeControllerEndpointPath + String.format("/%s", id))
                        .accept(MediaType.APPLICATION_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML_VALUE))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE))
                .andExpect(xpath("//id").number(is(id.doubleValue())))
                .andExpect(xpath("//code").number(is(both(greaterThan(0.0)).and(lessThan(600.0)))))
                .andExpect(xpath("//category").string(notNullValue()))
                .andExpect(xpath("//reason_phrase").string(notNullValue()))
                .andExpect(xpath("//definition").string(notNullValue()));
    }


    @Features({@Feature(TestGroups.INTEGRATION), @Feature("JSON"), @Feature("getAllHttpCodes")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION)
    public void getAllHttpCodesJsonTest() throws Exception {

        mvc.perform(get(httpCodeControllerEndpointPath + "/info/all")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.*", hasSize(66)))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].id", notNullValue()))
                .andExpect(jsonPath("$[*].category", notNullValue() ))
                .andExpect(jsonPath("$[*].code", notNullValue() ))
                .andExpect(jsonPath("$[*].reason_phrase", notNullValue() ))
                .andExpect(jsonPath("$[*].definition", notNullValue() ));
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("XML"), @Feature("getAllHttpCodes")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION)
    public void getAllHttpCodesXmlTest() throws Exception {

        mvc.perform(get(httpCodeControllerEndpointPath + "/info/all")
                        .accept(MediaType.APPLICATION_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML_VALUE))

                /*
                 * OR you can use: .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE))
                .andExpect(xpath(".//item").nodeCount(66) )
                .andExpect(xpath(".//item[*]/id").string(notNullValue()) )
                .andExpect(xpath(".//item[*]/category").string(notNullValue()) )
                .andExpect(xpath(".//item[*]/code").string(notNullValue()) )
                .andExpect(xpath(".//item[*]/reason_phrase").string(notNullValue()) )
                .andExpect(xpath(".//item[*]/definition").string(notNullValue()) );
    }

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
    @TmsLinks({@TmsLink("005"), @TmsLink("006")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION, dataProvider = "getHttpCodeByCodeDataProvider")
    public void getHttpCodeByCodeJsonTest(HttpCode expectedResponse) throws Exception {

        mvc.perform(get(httpCodeControllerEndpointPath + "/info").param(QueryParams.CODE, expectedResponse.getCode().toString())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.code", is(expectedResponse.getCode())))
                .andExpect(jsonPath("$.category", is(expectedResponse.getCategory())))
                .andExpect(jsonPath("$.reason_phrase", is(expectedResponse.getReason_phrase())))
                .andExpect(jsonPath("$.definition", is(expectedResponse.getDefinition())));
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("XML"), @Feature("getHttpCodeByCode")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("005"), @TmsLink("006")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION, dataProvider = "getHttpCodeByCodeDataProvider")
    public void getHttpCodeByCodeXmlTest(HttpCode expectedResponse) throws Exception {

        mvc.perform(get(httpCodeControllerEndpointPath + "/info").param(QueryParams.CODE, expectedResponse.getCode().toString())
                        .accept(MediaType.APPLICATION_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML_VALUE))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE))
                .andExpect(xpath("//id").string(notNullValue()))
                .andExpect(xpath("//code").string(expectedResponse.getCode().toString()))
                .andExpect(xpath("//category").string(expectedResponse.getCategory()))
                .andExpect(xpath("//reason_phrase").string(expectedResponse.getReason_phrase()))
                .andExpect(xpath("//definition").string(expectedResponse.getDefinition()));
    }


    @Features({@Feature(TestGroups.INTEGRATION), @Feature("JSON"), @Feature("findHttpCodesByCategory")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("007"), @TmsLink("008")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION, dataProvider = "getHttpCodeByCodeDataProvider")
    public void findHttpCodesByCategoryJsonTest(HttpCode expectedResponse) throws Exception {

        mvc.perform(get(httpCodeControllerEndpointPath + "/info").param(QueryParams.CATEGORY, expectedResponse.getCategory())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", is(not(0))))
                .andExpect(jsonPath("$[*].id", notNullValue()))
                .andExpect(jsonPath("$[*].category", hasItem(is(expectedResponse.getCategory())) ))
                .andExpect(jsonPath("$[*].code", hasItem(is(expectedResponse.getCode())) ))
                .andExpect(jsonPath("$[*].reason_phrase", hasItem(is(expectedResponse.getReason_phrase())) ))
                .andExpect(jsonPath("$[*].definition", hasItem(is(expectedResponse.getDefinition())) ));
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("XML"), @Feature("findHttpCodesByCategory")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("009"), @TmsLink("010")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION, dataProvider = "getHttpCodeByCodeDataProvider")
    public void findHttpCodesByCategoryXmlTest(HttpCode expectedResponse) throws Exception {

        mvc.perform(get(httpCodeControllerEndpointPath + "/info").param(QueryParams.CATEGORY, expectedResponse.getCategory())
                        .accept(MediaType.APPLICATION_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML_VALUE))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE))
                .andExpect(xpath(".//item").nodeCount(is(not(0))) )
                .andExpect(xpath(".//item[*]/id").string(notNullValue()) )
                .andExpect(xpath(".//item[*]/category").string(is(expectedResponse.getCategory())) )
                .andExpect(xpath(".//item[*]/code").string(is(expectedResponse.getCode().toString())) )
                .andExpect(xpath(".//item[*]/reason_phrase").string(is(expectedResponse.getReason_phrase())) )
                .andExpect(xpath(".//item[*]/definition").string(is(expectedResponse.getDefinition())) );
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("JSON"), @Feature("findHttpCodeByReasonPhrase")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("011"), @TmsLink("012")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION, dataProvider = "getHttpCodeByCodeDataProvider")
    public void findHttpCodeByReasonPhraseJsonTest(HttpCode expectedResponse) throws Exception {

        mvc.perform(get(httpCodeControllerEndpointPath + "/info").param(QueryParams.REASON_PHRASE, expectedResponse.getReason_phrase())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                
                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.code", is(expectedResponse.getCode())))
                .andExpect(jsonPath("$.category", is(expectedResponse.getCategory())))
                .andExpect(jsonPath("$.reason_phrase", is(expectedResponse.getReason_phrase())))
                .andExpect(jsonPath("$.definition", is(expectedResponse.getDefinition())));
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("XML"), @Feature("findHttpCodeByReasonPhrase")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("013"), @TmsLink("014")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION, dataProvider = "getHttpCodeByCodeDataProvider")
    public void findHttpCodeByReasonPhraseXmlTest(HttpCode expectedResponse) throws Exception {

        mvc.perform(get(httpCodeControllerEndpointPath + "/info").param(QueryParams.REASON_PHRASE, expectedResponse.getReason_phrase())
                        .accept(MediaType.APPLICATION_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML_VALUE))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE))

                .andExpect(xpath("//id").string(notNullValue()))
                .andExpect(xpath("//code").string(expectedResponse.getCode().toString()))
                .andExpect(xpath("//category").string(expectedResponse.getCategory()))
                .andExpect(xpath("//reason_phrase").string(expectedResponse.getReason_phrase()))
                .andExpect(xpath("//definition").string(expectedResponse.getDefinition()));
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("JSON"), @Feature("saveHttpCode")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("015"), @TmsLink("016")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION)
    public void saveHttpCodeJsonTest() throws Exception {

        final String fooHttpCodeJsonString = new ObjectMapper().writeValueAsString(fooHttpCodeJson);
        BaseTestUtils.attachText("Request Body.", fooHttpCodeJsonString);

        final String responseBody = mvc.perform(post(httpCodeControllerEndpointPath )
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(fooHttpCodeJsonString))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.code", is(fooHttpCodeJson.getCode())))
                .andExpect(jsonPath("$.category", is(fooHttpCodeJson.getCategory())))
                .andExpect(jsonPath("$.reason_phrase", is(fooHttpCodeJson.getReason_phrase())))
                .andExpect(jsonPath("$.definition", is(fooHttpCodeJson.getDefinition())))

                /*
                * Extract only Body to convert it to the HttCode object
                * */
                .andReturn().getResponse().getContentAsString();

        BaseTestUtils.attachText("Response.", responseBody);

        final HttpCode responseHttpCode = new ObjectMapper().readValue(responseBody, HttpCode.class);
        /*
        * For PUT we should provide ID of that entity in the DB.
        * */
        fooHttpCodeJson.setId(responseHttpCode.getId());
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("XML"), @Feature("saveHttpCode")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("017"), @TmsLink("018")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION)
    public void saveHttpCodeXmlTest() throws Exception {

        final String fooHttpCodeXmlString = new XmlMapper().writeValueAsString(fooHttpCodeXml);
        BaseTestUtils.attachText("Request Body.", fooHttpCodeXmlString);

        final String responseBody = mvc.perform(post(httpCodeControllerEndpointPath )
                        .accept(MediaType.APPLICATION_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .content(fooHttpCodeXmlString))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE))

                .andExpect(xpath("//id").string(notNullValue()))
                .andExpect(xpath("//code").string(fooHttpCodeXml.getCode().toString()))
                .andExpect(xpath("//category").string(fooHttpCodeXml.getCategory()))
                .andExpect(xpath("//reason_phrase").string(fooHttpCodeXml.getReason_phrase()))
                .andExpect(xpath("//definition").string(fooHttpCodeXml.getDefinition()))

                /*
                 * Extract only Body to convert it to the HttCode object
                 * */
                .andReturn().getResponse().getContentAsString();

        BaseTestUtils.attachText("Response.", responseBody);

        final HttpCode responseHttpCode = new XmlMapper().readValue(responseBody, HttpCode.class);//new GsonBuilder().create().fromJson(responseBody, HttpCode.class);
        /*
         * For PUT we should provide ID of that entity in the DB.
         * */
        fooHttpCodeXml.setId(responseHttpCode.getId());
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("JSON"), @Feature("putHttpCode")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("019"), @TmsLink("020")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION, dependsOnMethods="saveHttpCodeJsonTest")
    public void putHttpCodeJsonTest() throws Exception {

        final String fooHttpCodeJsonString = new ObjectMapper().writeValueAsString(fooHttpCodeJson.setDefinition("Call PUT"));
        BaseTestUtils.attachText("Request Body.", fooHttpCodeJsonString);

        mvc.perform(put(httpCodeControllerEndpointPath + "/" + fooHttpCodeJson.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(fooHttpCodeJsonString))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))

                /*
                * Short version of the assertions:
                *
                * .andExpect(jsonPath("$.id", is(fooHttpCodeJson.getId().intValue())))
                * .andExpect(jsonPath("$.code", is(fooHttpCodeJson.getCode())))
                * .andExpect(jsonPath("$.category", is(fooHttpCodeJson.getCategory())))
                * .andExpect(jsonPath("$.reason_phrase", is(fooHttpCodeJson.getReason_phrase())))
                * .andExpect(jsonPath("$.definition", is(fooHttpCodeJson.getDefinition())));
                *
                * */
                .andExpect( content().json(fooHttpCodeJsonString) );
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("XML"), @Feature("putHttpCode")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("021"), @TmsLink("022")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION, dependsOnMethods="saveHttpCodeXmlTest")
    public void putHttpCodeXmlTest() throws Exception {

        final String fooHttpCodeXmlString = new XmlMapper().writeValueAsString(fooHttpCodeXml.setDefinition("Call PUT"));
        BaseTestUtils.attachText("Request Body.", fooHttpCodeXmlString);

        mvc.perform(put(httpCodeControllerEndpointPath + "/" + fooHttpCodeXml.getId())
                        .accept(MediaType.APPLICATION_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .content(fooHttpCodeXmlString))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE))

                /*
                 * Short version of the assertions:
                 *
                 * .andExpect(xpath("//id", is(fooHttpCodeXml.getId().intValue())))
                 * .andExpect(xpath("//code", is(fooHttpCodeXml.getCode())))
                 * .andExpect(xpath("//category", is(fooHttpCodeXml.getCategory())))
                 * .andExpect(xpath("//reason_phrase", is(fooHttpCodeXml.getReason_phrase())))
                 * .andExpect(xpath("//definition", is(fooHttpCodeXml.getDefinition())));
                 *
                 * */
                .andExpect( content().xml(fooHttpCodeXmlString) );
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("JSON"), @Feature("deleteHttpCode")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("023"), @TmsLink("024")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION, dependsOnMethods="putHttpCodeJsonTest")
    public void deleteHttpCodeJsonTest() throws Exception {

        mvc.perform(delete(httpCodeControllerEndpointPath + "/" + fooHttpCodeJson.getId() )
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk());
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature("XML"), @Feature("deleteHttpCode")})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("025"), @TmsLink("026")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Spring API. Integration tests")
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = TestGroups.INTEGRATION, dependsOnMethods="putHttpCodeXmlTest")
    public void deleteHttpCodeXmlTest() throws Exception {

        mvc.perform(delete(httpCodeControllerEndpointPath + "/" + fooHttpCodeXml.getId() )
                        .accept(MediaType.APPLICATION_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML_VALUE))

                /*
                 * OR you can use:
                 * import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
                 * .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk());
    }
}