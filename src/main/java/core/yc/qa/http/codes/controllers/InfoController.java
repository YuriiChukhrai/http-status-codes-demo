package core.yc.qa.http.codes.controllers;

import core.yc.qa.http.codes.services.HttpCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/info")
class InfoController {

    @Value("${app.version}")
    private String appVersion;

    @Autowired
    private HttpCodeService httpCodeService;

    @Operation(summary = "Get information about the project", description = "Get broad information about the project, what is the current version, etc.", method = "GET")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "ok, successful operation", content = @Content(schema = @Schema(implementation = Info.class)))})
    @RequestMapping(value = "/", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Info getGeneralInformation() {

        return new Info(
                "HTTP status codes. Demo",
                appVersion,
                httpCodeService.getHttpCodesSize(),
                "Yurii Chukhrai",
                "chuhray.uriy@gmail.com",
                "https://github.com/YuriiChukhrai",
                "https://www.linkedin.com/in/yurii-c-b55aa6174",
                "https://server:port/context-path/swagger-ui.html",
                "https://server:port/context-path/v3/api-docs",
                "https://server:port/context-path/v3/api-docs.yaml");
    }
}

/**
 * Java 16 - Record
 * @see <a href="https://docs.oracle.com/en/java/javase/16/language/records.html">Java 16 - Record</>
 * */
    record Info(String app_name,
                String app_version,
                long http_codes_size,
                String dev,
                String e_mail,
                String git_hub_url,
                String linkedin_url,
                String swagger_url,
                String openapi_url,
                String openapi_yaml_url) {}