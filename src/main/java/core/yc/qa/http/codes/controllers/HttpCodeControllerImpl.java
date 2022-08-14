package core.yc.qa.http.codes.controllers;

import core.yc.qa.http.codes.entity.HttpCode;
import core.yc.qa.http.codes.services.HttpCodeService;
import core.yc.qa.http.codes.util.QueryParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author limit (Yurii Chukhrai)
 */
@RestController
@RequestMapping("/api/v1/http/code")
class HttpCodeControllerImpl implements HttpCodeController {

    @Autowired
    private HttpCodeService httpCodeService;

    @Operation(summary = "Get HTTP code description by his code", method = "GET", operationId = "getHttpCodeByCode")
    @ApiResponses(value = {@ApiResponse(content = @Content(schema = @Schema(implementation = HttpCode.class)))})
    @Override
    @GetMapping(value = {"/info"}, params = QueryParams.CODE, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public HttpCode getHttpCodeByCode(@NotNull @RequestParam(value = QueryParams.CODE, required = true)
                                          @Parameter(in = ParameterIn.QUERY, name = QueryParams.CODE, schema = @Schema(implementation = Integer.class))
                                              int code) {

        return httpCodeService.findHttpCodeByCode(code);
    }

    @Operation(summary = "Get HTTP code descriptions by the category", method = "GET", operationId = "findHttpCodesByCategory")
    @ApiResponses(value = {@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpCode.class))))})
    @Override
    @GetMapping(value = {"/info"}, params = QueryParams.CATEGORY, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<HttpCode> findHttpCodesByCategory(@NotNull @RequestParam(value = QueryParams.CATEGORY, required = true)
                                                      @Parameter(in = ParameterIn.QUERY, name = QueryParams.CATEGORY, schema = @Schema(implementation = String.class))
                                                              String category) {

        return httpCodeService.findHttpCodesByCategory(category);
    }

    @Operation(summary = "Get all HTTP codes", description = "Returns a list of all HTTP codes in DB", operationId = "getAllHttpCodes")
    @ApiResponses(value = {@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpCode.class))))})
    @Override
    @GetMapping(value = {"/info/all"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<HttpCode> getAllHttpCodes() {
        return httpCodeService.getAllHttpCodes();
    }

    @Operation(summary = "Get HTTP code description by reason phrase", method = "GET", operationId = "findHttpCodeByReasonPhrase")
    @ApiResponses(value = {@ApiResponse(content = @Content(schema = @Schema(implementation = HttpCode.class)))})
    @Override
    @GetMapping(value = {"/info"}, params = QueryParams.REASON_PHRASE, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public HttpCode findHttpCodeByReasonPhrase(@NotNull @RequestParam(value = QueryParams.REASON_PHRASE, required = true)
                                                   @Parameter(in = ParameterIn.QUERY, name = QueryParams.REASON_PHRASE, schema = @Schema(implementation = String.class))
                                                           String reasonPhrase) {
        return httpCodeService.findHttpCodeByReasonPhrase(reasonPhrase);
    }


    /*  CRUD Implementation */
    @Operation(summary = "Get HTTP code description by ID in DB", method = "GET", operationId = "getHttpCodeById")
    @ApiResponses(value = {@ApiResponse(content = @Content(schema = @Schema(implementation = HttpCode.class)))})
    @Override
    @GetMapping(value = {"/{id}"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public HttpCode getHttpCodeById(@NotNull @PathVariable @Parameter(in = ParameterIn.PATH, name = QueryParams.ID, schema = @Schema(implementation = Long.class)) long id) {

        return httpCodeService.findHttpCodeById(id);
    }

    @Operation(summary = "Save HTTP code", method = "POST", operationId = "saveHttpCode")
    @ApiResponses(value = {@ApiResponse(content = @Content(schema = @Schema(implementation = HttpCode.class)))})
    @Override
    @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public HttpCode saveHttpCode(@NotNull @RequestBody(required = true) HttpCode newHttpCode) {
        return httpCodeService.save(newHttpCode);
    }

    @Operation(summary = "Put/Update HTTP code by ID ", method = "PUT", operationId = "putHttpCode")
    @ApiResponses(value = {@ApiResponse(content = @Content(schema = @Schema(implementation = HttpCode.class)))})
    @Override
    @PutMapping(value = {"/{id}"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public HttpCode putHttpCode(@NotNull @RequestBody HttpCode newHttpCode,
                                @NotNull @PathVariable @Parameter(in = ParameterIn.PATH, name = QueryParams.ID, schema = @Schema(implementation = Long.class)) long id) {

        return httpCodeService.put(newHttpCode, id);
    }

    @Operation(summary = "Delete HTTP code by ID", method = "DELETE", operationId = "deleteHttpCode")
    @Override
    @DeleteMapping(value = {"/{id}"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void deleteHttpCode(@NotNull @PathVariable @Parameter(in = ParameterIn.PATH, name = QueryParams.ID, schema = @Schema(implementation = Long.class)) long id) {
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