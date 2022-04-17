package core.yc.qa.http.codes.services;

import core.yc.qa.http.codes.entity.HttpCode;
import java.util.List;

/**
 *
 * @author limit (Yurii Chukhrai)
 */

public interface HttpCodeService {

    HttpCode findHttpCodeByCode(Integer code);
    HttpCode findHttpCodeById(Long id);

    List<HttpCode> findHttpCodesByCategory(String category);
    HttpCode findHttpCodeByReasonPhrase(String reasonPhrase);
    List<HttpCode> getAllHttpCodes();

    long getHttpCodesSize();
    HttpCode save(HttpCode newHttpCode);
    void delete(Long id);

    HttpCode put(HttpCode newHttpCode, Long id);
}