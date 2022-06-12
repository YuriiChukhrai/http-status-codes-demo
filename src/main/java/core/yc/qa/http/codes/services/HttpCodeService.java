package core.yc.qa.http.codes.services;

import core.yc.qa.http.codes.entity.HttpCode;
import java.util.List;

/**
 *
 * @author limit (Yurii Chukhrai)
 */

public interface HttpCodeService {

    HttpCode findHttpCodeByCode(int code);
    HttpCode findHttpCodeById(long id);

    List<HttpCode> findHttpCodesByCategory(String category);
    HttpCode findHttpCodeByReasonPhrase(String reasonPhrase);
    List<HttpCode> getAllHttpCodes();

    long getHttpCodesSize();
    HttpCode save(HttpCode newHttpCode);
    void delete(long id);

    HttpCode put(HttpCode newHttpCode, long id);
}