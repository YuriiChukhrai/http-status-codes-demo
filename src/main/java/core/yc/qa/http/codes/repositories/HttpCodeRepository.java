package core.yc.qa.http.codes.repositories;

import core.yc.qa.http.codes.entity.HttpCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author limit (Yurii Chukhrai)
 */

public interface HttpCodeRepository extends JpaRepository<HttpCode, Long>, HttpCodeCustomRepository {}
