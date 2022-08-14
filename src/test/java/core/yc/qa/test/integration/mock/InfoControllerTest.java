package core.yc.qa.test.integration.mock;

import core.yc.qa.HttpStatusCodesApplication;
import core.yc.qa.http.codes.repositories.HttpCodeRepository;
import core.yc.qa.http.codes.services.HttpCodeService;
import core.yc.qa.test.utils.CustomMvcResultHandlers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.*;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author limit (Yurii Chukhrai)
 *
 * Controller (with context) layer unit tests with mocked repository layer (Database) only.
 *
 * @MockBean issue: https://github.com/spring-projects/spring-boot/issues/7689
 *
 */
@SpringBootTest(classes = HttpStatusCodesApplication.class )
public class InfoControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    @Autowired
    HttpCodeRepository httpCodeRepository;

    @InjectMocks
    @Autowired
    private HttpCodeService httpCodeService;

    private MockMvc mvc;

    @BeforeClass
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @BeforeMethod
    public void beforeMethod() {
        /*
         * Mocked only @Repository (DB data source), and keep all logic works in @Service. So we can test MVC with all API calls.
         * */
        when(httpCodeRepository.count()).thenReturn(777L); // Stub
    }

    @AfterMethod
    public void afterMethod() {
       reset(httpCodeRepository);
    }

    private final String appNameExpected = "HTTP status codes. Demo";
    private final String appVersionExpected = "0.0.1";
    private final String appDev = "Yurii Chukhrai";

    private final String devEmail = "chuhray.uriy@gmail.com";
    private final String githubLink = "https://github.com/YuriiChukhrai";
    private final String linkedinLink = "https://www.linkedin.com/in/yurii-c-b55aa6174";

    private final String endpointPath = "/api/v1/info/";

    @Test
    public void getGeneralInformationTestJson() throws Exception {

        //Act
        mvc.perform(get(endpointPath).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                /*
                 * OR you can use: .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.app_name", is(appNameExpected)))
                .andExpect(jsonPath("$.app_version", is(appVersionExpected)))
                .andExpect(jsonPath("$.http_codes_size", is(777)))

                .andExpect(jsonPath("$.dev", is(appDev)))
                .andExpect(jsonPath("$.e_mail", is(devEmail)))
                .andExpect(jsonPath("$.git_hub_url", is(githubLink)))
                .andExpect(jsonPath("$.linkedin_url", is(linkedinLink)));


        //Assert
        Mockito.verify(httpCodeRepository).count();
    }

    @Test(enabled = true)
    public void getGeneralInformationTestXml() throws Exception {

        //Act
        mvc.perform(get(endpointPath).accept(MediaType.APPLICATION_XML_VALUE).contentType(MediaType.APPLICATION_XML_VALUE))
                /*
                 * OR you can use: .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE))
                .andExpect(xpath("//app_name").string(appNameExpected))
                .andExpect(xpath("//app_version").string(appVersionExpected))
                .andExpect(xpath("//http_codes_size").string(String.valueOf(777L)))

                .andExpect(xpath("//dev").string(appDev))
                .andExpect(xpath("//e_mail").string(devEmail))
                .andExpect(xpath("//git_hub_url").string(githubLink))
                .andExpect(xpath("//linkedin_url").string(linkedinLink));

        //Assert
        Mockito.verify(httpCodeRepository,  times(1)).count();
    }
}