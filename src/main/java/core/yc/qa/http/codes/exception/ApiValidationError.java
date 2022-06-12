package core.yc.qa.http.codes.exception;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import core.yc.qa.http.codes.exception.model.LowerCaseClassNameResolver;
import lombok.*;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiValidationError extends ApiSubError {

    @NonNull
    private String object;
    private String field;
    private Object rejectedValue;

    @NonNull
    private String message;
}
