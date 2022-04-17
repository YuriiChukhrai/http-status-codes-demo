package core.yc.qa.http.codes.repositories;

import core.yc.qa.http.codes.entity.HttpCode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author limit (Yurii Chukhrai)
 */

public interface HttpCodeCustomRepository {

    Optional<HttpCode> findHttpCodeByCode(Integer code);
    Stream<HttpCode> findHttpCodesByCategory(String category);
    Optional<HttpCode> findHttpCodeByReasonPhrase(String reasonPhrase);
}
