package shop.mtcoding.boardproject._core.error.ex;

import org.springframework.http.HttpStatus;

public class MyException extends RuntimeException {
    private HttpStatus httpStatus;

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
