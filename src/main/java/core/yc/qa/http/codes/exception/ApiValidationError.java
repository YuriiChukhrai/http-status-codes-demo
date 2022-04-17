package core.yc.qa.http.codes.exception;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import core.yc.qa.http.codes.exception.model.LowerCaseClassNameResolver;

/**
 *
 * @author limit (Yurii Chukhrai)
 */

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    ApiValidationError(String object, String field, Object rejectedValue, String message){
        this.object = object;
        this.message = message;
        this.field = field;
        this.rejectedValue = rejectedValue;
    }
}
