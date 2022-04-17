package core.yc.qa.http.codes.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import core.yc.qa.http.codes.validation.ValidHttpCode;
import lombok.Data;
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
 */

@Entity(name = "http_code")
@JacksonXmlRootElement(localName = "http_code")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Accessors(chain = true)
public final class HttpCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Positive
    private Long id;

    @NotNull(message = "HTTP code cannot be null")
    @ValidHttpCode
    @Min(0)
    @Max(599)
    private Integer code;

    @NotNull(message = "HTTP category cannot be null")
    @Size(max = 20)
    private String category;

    @NotNull(message = "HTTP phrase cannot be null")
    @Size(max = 50)
    private String reason_phrase;

    @Size(max = 1000)
    private String definition;
}
