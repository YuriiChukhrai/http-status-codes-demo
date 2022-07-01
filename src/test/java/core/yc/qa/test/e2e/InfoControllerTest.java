package core.yc.qa.test.e2e;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

/**
 * @author limit (Yurii Chukhrai)
 *
 * The idea of these test's it's running on services then the code deployed to server.
 * These test can be implemented in a separate repository (outside the main code base/app).
 */
public final class InfoControllerTest extends BaseTest {

    private final String appNameExpected = "HTTP status codes. Demo";
    private final String appVersionExpected = "0.0.1";
    private final int appCodeSizeExpected = 66;

    private final String appDev = "Yurii Chukhrai";
    private final String devEmail = "chuhray.uriy@gmail.com";
    private final String githubLink = "https://github.com/YuriiChukhrai";
    private final String linkedinLink = "https://www.linkedin.com/in/yurii-c-b55aa6174";
    private final String endpointPath = "/api/v1/info/";

    @Epic("Epic-001")
    @Severity(SeverityLevel.NORMAL)
    @Features( {@Feature("JSON"), @Feature("Rest-Assured") })
    @TmsLink("0001")
    @Description("Get general information about application")
    @Test
    public void getGeneralInformationJsonTest() {

                RestAssured.given()
                        .config(config)
                        .relaxedHTTPSValidation()
                        .filter(new AllureRestAssured())
                        .request()
                        .headers(HEADERS)
                        .accept(ContentType.JSON)
                        .port(port)
                        .when()
                        .get(String.format("http://%s%s", "localhost", endpointPath))
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .body(notNullValue())
                        .body("dev", equalTo(appDev))
                        .body("http-codes-size", equalTo(appCodeSizeExpected))
                        .body("git-hub-url", equalTo(githubLink))
                        .body("e-mail", equalTo(devEmail))
                        .body("app-name", equalTo(appNameExpected))
                        .body("app-version", equalTo(appVersionExpected))
                        .body("linkedin-url", equalTo(linkedinLink));
    }

    @Epic("Epic-001")
    @Severity(SeverityLevel.NORMAL)
    @Features( {@Feature("XML"), @Feature("Rest-Assured") })
    @TmsLink("0002")
    @Description("Get general information about application")
    @Test
    public void getGeneralInformationXmlTest() {

        RestAssured.given()
                .config(config)
                .relaxedHTTPSValidation()
                .filter(new AllureRestAssured())
                .request()
                .headers(HEADERS)
                .accept(ContentType.XML)
                .port(port)
                .when()
                .get(String.format("http://%s%s", "localhost", endpointPath))
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body(notNullValue())
                .body("Map.dev", equalTo(appDev))
                .body("Map.http-codes-size", equalTo(String.valueOf(appCodeSizeExpected)))
                .body("Map.git-hub-url", equalTo(githubLink))
                .body("Map.e-mail", equalTo(devEmail))
                .body("Map.app-name", equalTo(appNameExpected))
                .body("Map.app-version", equalTo(appVersionExpected))
                .body("Map.linkedin-url", equalTo(linkedinLink));
    }
}
