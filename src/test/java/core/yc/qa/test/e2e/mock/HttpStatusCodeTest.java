package core.yc.qa.test.e2e.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import core.yc.qa.test.TestGroups;
import io.qameta.allure.*;
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
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsIterableContaining.hasItems;

@Link(name="Rest-Assured", url="https://github.com/rest-assured/rest-assured/wiki/Usage")
public class HttpStatusCodeTest {

    private final static Map<String, String> HEADERS = Collections.unmodifiableMap(new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
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
     * <http_code>
     *   <id>55</id>
     *   <code>500</code>
     *   <category>5** Server Error</category>
     *   <reason_phrase>Internal Server Error</reason_phrase>
     *   <definition>The server has encountered a situation it does not know how to handle.</definition>
     * </http_code>
     * */
    private static final String XML_BODY = "<http_code><id>55</id><code>500</code><category>5** Server Error</category><reason_phrase>Internal Server Error</reason_phrase><definition>The server has encountered a situation it does not know how to handle.</definition></http_code>";

    /**
     * {
     *   "id": 55,
     *   "code": 500,
     *   "category": "5** Server Error",
     *   "reason_phrase": "Internal Server Error",
     *   "definition": "The server has encountered a situation it does not know how to handle."
     * }
     * */
    private static final String JSON_BODY = "{\"id\":55,\"code\":500,\"category\":\"5** Server Error\",\"reason_phrase\":\"Internal Server Error\",\"definition\":\"The server has encountered a situation it does not know how to handle.\"}";

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
    @Description("HTTP status code. XML")
    @Features( {@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.RA), @Feature(TestGroups.MOCK)} )
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.RA, TestGroups.MOCK})
    public void getHttpCodeByCodeTest() {

        // Arrange
        wireMockServer.stubFor(
                WireMock.get("/api/v1/http/code/info?code=500")//https://localhost:{port_number}/api/v1/http/code/info?code=500
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withBody(XML_BODY)
                                .withHeader("Content-Type", "application/xml")
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
                .get(wireMockServer.url("/api/v1/http/code/info?code=500"))
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .contentType(ContentType.XML)
                .body(notNullValue());

    }

    @Epic("Epic-001")
    @TmsLink("0001")
    @Severity(SeverityLevel.NORMAL)
    @Description("HTTP status code. XML")
    @Features( {@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.RA), @Feature(TestGroups.MOCK)} )
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.RA, TestGroups.MOCK})
    public void postHttpCodeTest() {

        // Arrange
        wireMockServer.stubFor(
                WireMock.get("/api/v1/http/code/info?code=500")//https://localhost:{port_number}/api/v1/http/code/info?code=500
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withBody(XML_BODY)
                                .withHeader("Content-Type", "application/xml")
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
                .get(wireMockServer.url("/api/v1/http/code/info?code=500"))
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .contentType(ContentType.XML)
                .body(notNullValue());

    }
}
