package core.yc.qa.http.codes.controllers;

import core.yc.qa.http.codes.services.HttpCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> getGeneralInformation() {

        final Map<String, Object> map = new HashMap<>();

        map.put("app-name", "HTTP status codes. Demo");
        map.put("app-version", appVersion);
        map.put("http-codes-size", httpCodeService.getHttpCodesSize());

        map.put("dev", "Yurii Chukhrai");
        map.put("e-mail", "chuhray.uriy@gmail.com");
        map.put("git-hub-url", "https://github.com/YuriiChukhrai");

        map.put("linkedin-url", "https://www.linkedin.com/in/yurii-c-b55aa6174");

        return map;
    }
}
