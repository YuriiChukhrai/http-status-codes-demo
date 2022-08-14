package core.yc.qa.http.codes.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import core.yc.qa.http.codes.validation.ValidHttpCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

/**
 *
 * @author limit (Yurii Chukhrai)
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-10">RFC 2616 - definitions of status codes</a>
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc7231#section-6">RFC 7231 - updated specification</a>
 *
 */

@Entity(name = "http_code")
@JacksonXmlRootElement(localName = "http_code")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Accessors(chain = true)
@ToString
@Schema(name="HttpCode", description = "Representation of model Request/Response")
public final class HttpCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Positive
    @Schema(name = "id", description = "DB unique primary key", required = true, example = "1L")
    private Long id;

    @NotNull(message = "HTTP code cannot be null")
    @ValidHttpCode
    @Min(0)
    @Max(599)
    @Schema(name = "code", description = "The status-code element is a three-digit integer code giving the result of the attempt to understand and satisfy the request. The first digit of the status-code defines the class of response. The last two digits do not have any categorization role.", required = true, example = "203")
    private Integer code;

    @NotNull(message = "HTTP category cannot be null")
    @Size(max = 20)
    @Schema(name = "category", description = "Category of HTTP code", required = true, example = "Success")
    private String category;

    @NotNull(message = "HTTP phrase cannot be null")
    @Size(max = 50)
    @Schema(name = "reason_phrase", description = "The reason phrases listed here are only recommendations -- they can be replaced by local equivalents without affecting the protocol.", required = true, example = "Non-authoritative Information")
    private String reason_phrase;

    @Size(max = 1000)
    @Schema(name = "definition")
    private String definition;
}
