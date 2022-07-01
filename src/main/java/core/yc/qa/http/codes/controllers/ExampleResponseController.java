package core.yc.qa.http.codes.controllers;

import core.yc.qa.http.codes.entity.HttpCode;
import core.yc.qa.http.codes.services.HttpCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


/**
 *
 * @author limit (Yurii Chukhrai)
 */
@RestController
@RequestMapping("/api/v1/http/code/example")
class ExampleResponseController {

    @Autowired
    private HttpCodeService httpCodeService;

    @RequestMapping(value = {"/{code}"}, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
    public ResponseEntity<HttpCode> getResponseEntityById(@NotNull @PathVariable Integer code) {

        return ResponseEntity
                .status(HttpStatus.valueOf(code))
                .body(httpCodeService.findHttpCodeByCode(code));
    }
}
