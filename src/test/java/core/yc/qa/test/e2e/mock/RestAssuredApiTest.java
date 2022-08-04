package core.yc.qa.test.e2e.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import core.yc.qa.test.TestGroups;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Features;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.notNullValue;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsIterableContaining.hasItems;

@Link(name="Rest-Assured", url="https://github.com/rest-assured/rest-assured/wiki/Usage")
public class RestAssuredApiTest {

    private final static Map<String, String> HEADERS = Collections.unmodifiableMap(new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            //put("Content-Type", "application/json; charset=UTF-8");
            //put("Accept", "*/*");

            put("Connection", "keep-alive");
            put("Cache-Control", "no-cache");
            put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
            put("Accept-Encoding", "gzip, deflate, br");
        }
    });

    private final static RestAssuredConfig config = RestAssured.config()
            .httpClient(HttpClientConfig
                    .httpClientConfig()
                    .setParam("http.connection.timeout", 60_000)
                    .setParam("http.socket.timeout", 60_000));


    /**
     * <html
     * <head>
     * <meta http-equiv="content-type" content="text/html; charset=UTF-8">
     * <title>Bla title</title>
     * </head>
     *     <body class="client">
     *         <a class="gwt-Anchor" href="https://bla.com/published/index.html" target="_blank">Contact Us</a>
     *         <a class="gwt-Anchor" href="/blac/index.html" target="_blank">Documentation</a>
     *     </body>
     * </html>
     * */
    private static final String LANDING_PAGE_HTML = "<html> <head> <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"> <title>Bla title</title> </head> <body class=\"client\"> <a class=\"gwt-Anchor\" href=\"https://bla.com/published/index.html\" target=\"_blank\">Contact Us</a> <a class=\"gwt-Anchor\" href=\"/blac/index.html\" target=\"_blank\">Documentation</a> </body> </html>";

    /**
     * <person>
     *     <fn>Yurii</fn>
     *     <ln>Chukhrai</ln>
     *     <age>38</age>
     *     <ssn>000-00-00</ssn>
     *     <hobbies>
     *         <hobbie>hiking</hobbie>
     *         <hobbie>diving</hobbie>
     *         <hobbie>reading</hobbie>
     *         <hobbie>flying</hobbie>
     *         <hobbie>movies</hobbie>
     *     </hobbies>
     * </person>
     * */
    private static final String XML_BODY = "<Person> <fn>Yurii</fn> <ln>Chukhrai</ln> <age>38</age> <ssn>000-00-00</ssn> <hobbies> <hobbie>hiking</hobbie> <hobbie>diving</hobbie> <hobbie>reading</hobbie> <hobbie>flying</hobbie> <hobbie>movies</hobbie> </hobbies> </Person>";

    /**
     * {
     *     "fn":   "Yurii",
     *     "ln":   "Chukhrai",
     *     "age":  38,
     *     "ssn":  "000-00-00",
     *     "hobbies": ["hiking", "diving", "reading", "flying", "movies"]
     * }
     * */
    private static final String JSON_BODY = "{\"fn\":\"Yurii\", \"ln\":\"Chukhrai\", \"age\":38, \"ssn\":\"000-00-00\", \"hobbies\":[\"hiking\", \"diving\", \"reading\", \"flying\", \"movies\"]}";

    private WireMockServer wireMockServer;


    /**
     * It's a fake server what does not have any dependency from real life, so any API can be tested safely.
     * */
    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockServer.start();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        if(Objects.nonNull(wireMockServer)){
            wireMockServer.shutdown();
        }
    }
    
    @Epic("Epic-001")
    @TmsLink("0001")
    @Severity(SeverityLevel.NORMAL)
    @Description("G2 landing page. HTML")
    @Features( {@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.RA), @Feature(TestGroups.MOCK)} )
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.RA, TestGroups.MOCK})
    public void landingPageBasicValidation() {

        //Arrange
        wireMockServer.stubFor(
                WireMock.get("/test/html/app.jsp")//https://localhost:{port_number}/test/html/app.jsp
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withBody(LANDING_PAGE_HTML)
                                .withHeader("Content-Type", "text/html;charset=UTF-8")
                                .withStatusMessage("OK")
                        )
        );

        // Act
        // Assert
        RestAssured.given()
                .config(config)
                .relaxedHTTPSValidation()
                .filter(new AllureRestAssured())
                .request()
                .headers(HEADERS)
                .when()
                .get(wireMockServer.url("/test/html/app.jsp"))
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .contentType(ContentType.HTML)
                .body(notNullValue());

    }

    @Epic("Epic-002")
    @TmsLink("0002")
    @Severity(SeverityLevel.NORMAL)
    @Description("G2 landing page. HTML. Headers validation")
    @Features( {@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.RA), @Feature(TestGroups.MOCK)} )
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.RA, TestGroups.MOCK})
    public void landingPageTitleValidation() {

        //Arrange
        wireMockServer.stubFor(
                WireMock.get("/test/html/app.jsp")//https://localhost:{port_number}/test/html/app.jsp
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withBody(LANDING_PAGE_HTML)
                                .withHeader("Content-Type", "text/html;charset=UTF-8")
                                .withHeader("X-Content-Type-Options", "nosniff")
                                .withHeader("X-Frame-Options", "SAMEORIGIN")
                                .withHeader("X-OneAgent-JS-Injection", "true")
                                .withStatusMessage("OK")
                        )
        );

        // Act
        Response response = RestAssured.given()
                .config(config)
                .relaxedHTTPSValidation()
                .filter(new AllureRestAssured())
                .request()
                .headers(HEADERS)
                .when()
                .get(wireMockServer.url("/test/html/app.jsp"))
                .thenReturn();

        // Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK");
        Assert.assertEquals(response.getContentType(), "text/html;charset=utf-8");
        Assert.assertEquals(response.getHeader("X-Content-Type-Options"), "nosniff");
        Assert.assertEquals(response.getHeader("X-Frame-Options"), "SAMEORIGIN");
        Assert.assertEquals(response.getHeader("X-OneAgent-JS-Injection"), "true");
    }

    @Epic("Epic-003")
    @TmsLink("0003")
    @Severity(SeverityLevel.NORMAL)
    @Description("G2 landing page. JSON. Schema validation")
    @Features( {@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.RA), @Feature(TestGroups.MOCK)} )
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.RA, TestGroups.MOCK})
    public void jsonSchemaValidation() {

        //Arrange
        wireMockServer.stubFor(
                WireMock.get("/test/json/app.jsp")//https://localhost:{port_number}/test/json/app.jsp
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withHeader("Content-Type", "application/json")
                                .withBody(JSON_BODY)
                                .withStatusMessage("OK")
                        )
        );

        // Act
        // Assert
        RestAssured.given()
                .config(config)
                .relaxedHTTPSValidation()
                .filter(new AllureRestAssured())
                .request()
                .headers(HEADERS)
                .when()
                .get(wireMockServer.url("/test/json/app.jsp"))
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(notNullValue())
                .body(matchesJsonSchemaInClasspath("person-json-schema.json"));
    }

    @Epic("Epic-004")
    @TmsLink("0004")
    @Description("G2 landing page. JSON. Path validation")
    @Features( {@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.RA), @Feature(TestGroups.MOCK)} )
    @Test(enabled = true, groups = {TestGroups.INTEGRATION, TestGroups.RA, TestGroups.MOCK})
    public void jsonPathValidation() {

        //Arrange
        wireMockServer.stubFor(
                WireMock.get("/test/json/app.jsp")//https://localhost:{port_number}/test/json/app.jsp
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withHeader("Content-Type", "application/json")
                                .withBody(JSON_BODY)
                                .withStatusMessage("OK")
                        )
        );

        // Act
        // Assert
        RestAssured.given()
                .config(config)
                .relaxedHTTPSValidation()
                .filter(new AllureRestAssured())
                .request()
                .headers(HEADERS)
                .when()
                .get(wireMockServer.url("/test/json/app.jsp"))
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body(notNullValue())
                .body("fn", equalTo("Yurii"))
                .body("ln", equalTo("Chukhrai"))
                .body("age", equalTo(38))
                .body("ssn", equalTo("000-00-00"))
                .body("hobbies", allOf(hasItem("hiking"), hasItem("diving"), hasItem("reading"), hasItem("flying"), hasItem("movies")));
    }

    @Epic("Epic-005")
    @TmsLink("0005")
    @Description("G2 landing page. XML validation")
    @Features( {@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.RA), @Feature(TestGroups.MOCK)} )
    @Test(enabled = true, groups = {TestGroups.INTEGRATION, TestGroups.RA, TestGroups.MOCK})
    public void xmlValidation() {

        //Arrange
        wireMockServer.stubFor(
                WireMock.get("/test/xml/app.jsp")//https://localhost:{port_number}/test/xml/app.jsp
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withHeader("Content-Type", "application/xml")
                                .withBody(XML_BODY)
                                .withStatusMessage("OK")
                        )
        );

        // Act
        // Assert
        RestAssured.given()
                .config(config)
                .relaxedHTTPSValidation()
                .filter(new AllureRestAssured())
                .request()
                .headers(HEADERS)
                .when()
                .get(wireMockServer.url("/test/xml/app.jsp"))
                .then()
                .assertThat()
                .contentType(ContentType.XML)
                .statusCode(200)
                .body(notNullValue())
                .body(hasXPath("/Person/fn[text()='Yurii']"))
                .body(hasXPath("/Person/ln", containsString("Chukhrai")))
                .body("person.age", equalTo("38"))
                .body("person.ssn", equalTo("000-00-00"))
                .body("person.hobbies.collect { it }", hasItems("hiking", "diving", "reading", "flying", "movies"));
    }
}
