package core.yc.qa.http.codes.services;

import core.yc.qa.http.codes.entity.HttpCode;
import core.yc.qa.http.codes.exception.model.EntityExistException;
import core.yc.qa.http.codes.exception.model.EntityNotFoundException;
import core.yc.qa.http.codes.exception.model.IllegalRequestException;
import core.yc.qa.http.codes.repositories.HttpCodeRepository;
import core.yc.qa.http.codes.util.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author limit (Yurii Chukhrai)
 */

// Business logic here
@Service
public class HttpCodeServiceImpl implements HttpCodeService {

    private static final int HARDCODED_LIMITATION = 66;

    @Autowired
    HttpCodeRepository httpCodeRepository;

    @Override
    public HttpCode findHttpCodeByCode(Integer code) {
        if (code > 0) {
            return httpCodeRepository.findHttpCodeByCode(code).orElseThrow(() -> new EntityNotFoundException(HttpCode.class, QueryParams.CODE));
        }
        throw new IllegalRequestException(String.format("Wrong code [%s]", code));
    }

    @Override
    public HttpCode findHttpCodeById(Long id) {
        if (id > 0) {
            return httpCodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(HttpCode.class, QueryParams.ID));
        }
        throw new IllegalRequestException(String.format("Wrong ID [%s]", id));
    }

    @Override
    public List<HttpCode> findHttpCodesByCategory(String category) {

        if (!category.isEmpty()) {
            return httpCodeRepository.findHttpCodesByCategory(category.trim()).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        }
        throw new IllegalRequestException(String.format("Wrong category [%s]", category));
    }

    @Override
    public HttpCode findHttpCodeByReasonPhrase(String reasonPhrase) {
        if (!reasonPhrase.isEmpty()) {
            return httpCodeRepository.findHttpCodeByReasonPhrase(reasonPhrase).orElseThrow(() -> new EntityNotFoundException(HttpCode.class, QueryParams.REASON_PHRASE));
        }
        throw new IllegalRequestException(String.format("Wrong reason phrase [%s]", reasonPhrase));
    }

    public List<HttpCode> getAllHttpCodes() {

        return httpCodeRepository.findAll();
    }

    @Override
    public long getHttpCodesSize() {

        return httpCodeRepository.count();
    }

    @Override
    public HttpCode save(HttpCode newHttpCode) {

        if (!httpCodeRepository.findHttpCodeByCode(newHttpCode.getCode()).isPresent()) {
            return httpCodeRepository.save(newHttpCode);
        } else {
            throw new EntityExistException(HttpCode.class);
        }
    }

    @Override
    public void delete(Long id) {
        /*
         * The limitation [&& id > HARDCODED_LIMITATION] putted to protect correct HttpCode entities in DB
         * */
        if (id > 0 && id > HARDCODED_LIMITATION) {
            httpCodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(HttpCode.class, QueryParams.ID));
            httpCodeRepository.deleteById(id);
            return;
        }
        throw new IllegalRequestException(String.format("Wrong ID [%s]", id));
    }

    @Override
    public HttpCode put(HttpCode newHttpCode, Long id) {

        /*
         * The limitation [&& id > HARDCODED_LIMITATION] putted to protect correct HttpCode entities in DB
         * */
        if (id > 0 && id > HARDCODED_LIMITATION) {
            return httpCodeRepository.findById(id).map(httpCode -> {

                httpCode.setCode(newHttpCode.getCode());
                httpCode.setCategory(newHttpCode.getCategory());
                httpCode.setReason_phrase(newHttpCode.getReason_phrase());
                httpCode.setDefinition(newHttpCode.getDefinition());
                return httpCode;}

            ).orElseGet(() -> httpCodeRepository.save(newHttpCode));
        }
        throw new IllegalRequestException(String.format("Wrong ID [%s]", id));
    }
}