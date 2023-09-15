package shop.mtcoding.boardproject._core.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.mtcoding.boardproject._core.error.ex.MyApiException;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.util.ApiUtil;
import shop.mtcoding.boardproject._core.util.Script;

@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(MyException.class)
    public String error(MyException e) {
        log.error("에러 : "+ e.getMessage());
        return Script.back(e.getMessage());
    }

    @ExceptionHandler(MyApiException.class)
    public ApiUtil<String> error(MyApiException e) {
        log.error("에러 : "+ e.getMessage());
        return new ApiUtil<>(false, e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public String error(NullPointerException e) {
        log.error("에러 : "+ e.getMessage());
        return Script.back("잘못된 요청입니다.");
    }
}
