package core.yc.qa.test.e2e;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected static Map<String, String> HEADERS = Collections.unmodifiableMap(new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("Connection", "keep-alive");
            put("Cache-Control", "no-cache");
            put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
            put("Accept-Encoding", "gzip, deflate, br");
        }
    });

    protected static final RestAssuredConfig config = RestAssured.config().httpClient(HttpClientConfig
            .httpClientConfig().setParam("http.connection.timeout", 60_000).setParam("http.socket.timeout", 60_000));

    protected static final int port = 7777;

}
