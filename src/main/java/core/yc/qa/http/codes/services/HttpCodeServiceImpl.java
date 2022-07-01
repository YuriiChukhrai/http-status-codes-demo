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
 * Business logic here
 */

@Service
public class HttpCodeServiceImpl implements HttpCodeService {

    private static final int HARDCODED_LIMITATION = 66;

    @Autowired
    private HttpCodeRepository httpCodeRepository;

    @Override
    public HttpCode findHttpCodeByCode(final int code) {
        if (code > 0) {
            return httpCodeRepository.findHttpCodeByCode(code).orElseThrow(() -> new EntityNotFoundException(HttpCode.class, QueryParams.CODE, String.valueOf(code)));
        }
        throw new IllegalRequestException(String.format("Wrong code [%s]", code));
    }

    @Override
    public HttpCode findHttpCodeById(final long id) {
        if (id > 0) {
            return httpCodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(HttpCode.class, QueryParams.ID, String.valueOf(id)));
        }
        throw new IllegalRequestException(String.format("Wrong ID [%s]", id));
    }

    /**
     * @info - Check if input (category: 1** Informational; 2** Success; ...) valid (not null, not empty, and length correct - according to the DB field)
     * @return - list of instance HttpCode's
     * */
    @Override
    public List<HttpCode> findHttpCodesByCategory(final String category) {

        if (Objects.nonNull(category) && !category.trim().isEmpty() && category.length() <= 20) {
            return httpCodeRepository.findHttpCodesByCategory(category.trim()).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        }
        throw new IllegalRequestException(String.format("Wrong category [%s]", getSafeString(category, 20)));
    }

    /**
     * @info - Check if input (reasonPhrase: OK; Created; ...) valid (not null, not empty, and length correct - according to the DB field)
     * @return - instance of HttpCode
     * */
    @Override
    public HttpCode findHttpCodeByReasonPhrase(final String reasonPhrase) {
        if (Objects.nonNull(reasonPhrase) && !reasonPhrase.trim().isEmpty() && reasonPhrase.length() <= 50) {
            return httpCodeRepository.findHttpCodeByReasonPhrase(reasonPhrase).orElseThrow(() -> new EntityNotFoundException(HttpCode.class, QueryParams.REASON_PHRASE, reasonPhrase));
        }
        throw new IllegalRequestException(String.format("Wrong reason phrase [%s]", getSafeString(reasonPhrase, 50) ));
    }

    public List<HttpCode> getAllHttpCodes() {
        return httpCodeRepository.findAll();
    }

    @Override
    public long getHttpCodesSize() {
        return httpCodeRepository.count();
    }

    @Override
    public HttpCode save(final HttpCode newHttpCode) {
        if (Objects.nonNull(newHttpCode) && newHttpCode.getCode() > 0 && !httpCodeRepository.findHttpCodeByCode(newHttpCode.getCode()).isPresent()) {
            return httpCodeRepository.save(newHttpCode);
        } else {
            throw new EntityExistException(HttpCode.class, "RequestBody", Objects.nonNull(newHttpCode) ? newHttpCode.toString() : "null");
        }
    }

    @Override
    public void delete(final long id) {
        /*
         * The limitation [&& id > HARDCODED_LIMITATION] putted to protect correct HttpCode entities in DB
         * */
        if (id > 0 && id > HARDCODED_LIMITATION) {
            httpCodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(HttpCode.class, QueryParams.ID, String.valueOf(id)));
            httpCodeRepository.deleteById(id);
            return;
        }
        throw new IllegalRequestException(String.format("Wrong ID [%s]", id));
    }

    @Override
    public HttpCode put(final HttpCode newHttpCode, final long id) {

        /*
         * The limitation [&& id > HARDCODED_LIMITATION] putted to protect correct HttpCode entities in DB
         * */
        if (Objects.nonNull(newHttpCode) && newHttpCode.getCode() > 0 && id > 0 && id > HARDCODED_LIMITATION) {
            return httpCodeRepository.findById(id).map(httpCode -> {

                httpCode.setCode(newHttpCode.getCode());
                httpCode.setCategory(newHttpCode.getCategory());
                httpCode.setReason_phrase(newHttpCode.getReason_phrase());
                httpCode.setDefinition(newHttpCode.getDefinition());

                return httpCode;}

            ).orElseGet(() -> httpCodeRepository.save(newHttpCode));
        }
        throw new IllegalRequestException(String.format("Wrong request. ID [%s]. HttpCode [%s].", id, Objects.nonNull(newHttpCode) ? newHttpCode.toString() : "null"));
    }

    private String getSafeString(final String value, final int safeLength){

        if(Objects.nonNull(value)){
            return value.length() > safeLength ? value.substring(0, safeLength) : value;
        }

        return value;
    }

}