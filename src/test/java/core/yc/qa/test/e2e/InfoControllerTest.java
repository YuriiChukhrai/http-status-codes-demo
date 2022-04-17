//package core.yc.qa.test.e2e;
//
//import core.yc.qa.http.codes.controllers.InfoController;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
//import static org.hamcrest.Matchers.containsString;
//
///**
// * @author limit (Yurii Chukhrai)
// */
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class InfoControllerTest {
//
//    @Autowired
//    private InfoController infoController;
//
//    @LocalServerPort
//    private int port;
//
//    @Test
//    public void givenInfoControllerHasRightKeys() {
//
//
////        when().get(String.format("http://localhost:%s/movies/100", port))
////                .then()
////                .statusCode(is(200))
////                .body(containsString("Hello World!".toLowerCase()));
//    }
//
//    //givenInfoControllerHasRightKeys
//}
