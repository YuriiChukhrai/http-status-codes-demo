package core.yc.qa.http.codes.exception.model;

/**
 *
 * @author limit (Yurii Chukhrai)
 */

public class IllegalRequestException extends RuntimeException {

    public IllegalRequestException() {
        super();
    }

    public IllegalRequestException(String msg) {
        super(msg);
    }

    public IllegalRequestException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
