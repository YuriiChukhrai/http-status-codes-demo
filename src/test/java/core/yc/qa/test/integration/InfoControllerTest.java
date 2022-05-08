package core.yc.qa.test.integration;

import core.yc.qa.HttpStatusCodesApplication;
import core.yc.qa.test.utils.CustomMvcResultHandlers;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author limit (Yurii Chukhrai)
 */
@SpringBootTest(classes = HttpStatusCodesApplication.class )
public class InfoControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

   @BeforeClass
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private final String appNameExpected = "HTTP status codes. Demo";
    private final String appVersionExpected = "0.0.1";
    private final int appCodeSizeExpected = 66;

    private final String appDev = "Yurii Chukhrai";
    private final String devEmail = "chuhray.uriy@gmail.com";
    private final String githubLink = "https://github.com/YuriiChukhrai";
    private final String linkedinLink = "https://www.linkedin.com/in/yurii-c-b55aa6174";
    private final String endpointPath = "/api/v1/info/";

    @Test
    public void getGeneralInformationTestJson() throws Exception {

        mvc.perform(get(endpointPath).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                /*
                 * OR you can use: .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.app-name", is(appNameExpected)))
                .andExpect(jsonPath("$.app-version", is(appVersionExpected)))
                .andExpect(jsonPath("$.http-codes-size", is(appCodeSizeExpected)))

                .andExpect(jsonPath("$.dev", is(appDev)))
                .andExpect(jsonPath("$.e-mail", is(devEmail)))
                .andExpect(jsonPath("$.git-hub-url", is(githubLink)))
                .andExpect(jsonPath("$.linkedin-url", is(linkedinLink)));
    }

    @Test
    public void getGeneralInformationTestXml() throws Exception {
        mvc.perform(get(endpointPath).accept(MediaType.APPLICATION_XML_VALUE).contentType(MediaType.APPLICATION_XML_VALUE))
                /*
                 * OR you can use: .andDo(print()) to printout the mvc results to console
                 * */
                .andDo(CustomMvcResultHandlers.allureAttachment())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE))
                .andExpect(xpath("//app-name").string(appNameExpected))
                .andExpect(xpath("//app-version").string(appVersionExpected))
                .andExpect(xpath("//http-codes-size").string(String.valueOf(appCodeSizeExpected)))

                .andExpect(xpath("//dev").string(appDev))
                .andExpect(xpath("//e-mail").string(devEmail))
                .andExpect(xpath("//git-hub-url").string(githubLink))
                .andExpect(xpath("//linkedin-url").string(linkedinLink));
    }
}