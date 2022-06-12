package core.yc.qa.http.codes.repositories;

import core.yc.qa.http.codes.entity.HttpCode;
import core.yc.qa.http.codes.util.QueryParams;
import org.springframework.stereotype.Repository;;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author limit (Yurii Chukhrai)
 */

@Repository
//@Transactional(readOnly = true) - Can't keep stream open
public class HttpCodeCustomRepositoryImpl implements HttpCodeCustomRepository {

    /*
    * For pagination can be used this approach:
    *
    * ```
    * Query query = .....
    *
    * .setMaxResults(5)
    * .setFirstResult(0)
    * .getResultStream();
    * ```
    * */

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<HttpCode> findHttpCodeByCode(int code) {
        final String baseSql = "SELECT hc.* FROM http_code as hc WHERE hc.code = :code";

        final Query query = entityManager.createNativeQuery(baseSql, HttpCode.class);
        query.setParameter(QueryParams.CODE, code);

        return query.getResultStream().findFirst();
    }

    @Override
    public Stream<HttpCode> findHttpCodesByCategory(String category) {
        final String baseSql = "SELECT hc.* FROM http_code as hc WHERE UPPER(hc.category) like CONCAT('%',UPPER(:category),'%')";
        final Query query = entityManager.createNativeQuery(baseSql, HttpCode.class);
        query.setParameter(QueryParams.CATEGORY, category);

        return query.getResultStream();
    }

    @Override
    public Optional<HttpCode> findHttpCodeByReasonPhrase(String reasonPhrase) {
        final String baseSql = "SELECT hc.* FROM http_code as hc WHERE  UPPER(hc.reason_phrase) = UPPER(:reason_phrase)";
        final Query query = entityManager.createNativeQuery(baseSql, HttpCode.class);
        query.setParameter(QueryParams.REASON_PHRASE, reasonPhrase);

        return query.getResultStream().findFirst();
    }
}
