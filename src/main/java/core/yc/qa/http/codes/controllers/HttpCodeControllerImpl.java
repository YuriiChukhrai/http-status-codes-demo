package core.yc.qa.http.codes.controllers;

import core.yc.qa.http.codes.entity.HttpCode;
import core.yc.qa.http.codes.services.HttpCodeService;
import core.yc.qa.http.codes.util.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 *
 * @author limit (Yurii Chukhrai)
 */

@RestController
@RequestMapping("/api/v1/http/code")
public class HttpCodeControllerImpl implements HttpCodeController {

    @Autowired
    HttpCodeService httpCodeService;

    @Override
    @GetMapping(value = {"/info"}, params = QueryParams.CODE, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public HttpCode getHttpCodeByCode(@NotNull @RequestParam(value = QueryParams.CODE, required = true) int code) {

        return httpCodeService.findHttpCodeByCode(code);
    }

    @Override
    @GetMapping(value = {"/info"}, params = QueryParams.CATEGORY, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<HttpCode> findHttpCodesByCategory(@NotNull @RequestParam(value = QueryParams.CATEGORY, required = true) String category) {

        return httpCodeService.findHttpCodesByCategory(category);
    }

    @Override
    @GetMapping(value = {"/info/all"}, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<HttpCode> getAllHttpCodes() {
        return httpCodeService.getAllHttpCodes();
    }

    @Override
    @GetMapping(value = {"/info"}, params = QueryParams.REASON_PHRASE, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public HttpCode findHttpCodeByReasonPhrase(@NotNull @RequestParam(value = QueryParams.REASON_PHRASE, required = true) String reasonPhrase) {
        return httpCodeService.findHttpCodeByReasonPhrase(reasonPhrase);
    }


    /*  CRUD Implementation */

    @Override
    @GetMapping(value = {"/{id}"}, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public HttpCode getHttpCodeById(@NotNull @PathVariable long id) {

        return httpCodeService.findHttpCodeById(id);
    }

    @Override
    @PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public HttpCode saveHttpCode(@NotNull @RequestBody HttpCode newHttpCode) {
        return httpCodeService.save(newHttpCode);
    }

    @Override
    @PutMapping(value = {"/{id}"}, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public HttpCode putHttpCode(@NotNull @RequestBody HttpCode newHttpCode, @NotNull @PathVariable long id) {

        return httpCodeService.put(newHttpCode, id);
    }

    @Override
    @DeleteMapping(value = {"/{id}"}, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public void deleteHttpCode(@NotNull @PathVariable long id) {
        httpCodeService.delete(id);
    }
}

//To keep contract for REST controller clear
interface HttpCodeController {
    HttpCode getHttpCodeByCode(int code);
    HttpCode getHttpCodeById(long id);
    List<HttpCode> findHttpCodesByCategory(String category);

    List<HttpCode> getAllHttpCodes();
    HttpCode findHttpCodeByReasonPhrase(String reasonPhrase);
    HttpCode saveHttpCode(HttpCode newHttpCode);

    HttpCode putHttpCode(HttpCode newHttpCode, long id);
    void deleteHttpCode(long id);
}