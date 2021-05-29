package tw.ym.fshra.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tw.ym.fshra.pojo.ResponseObj;

import java.util.ArrayList;
import java.util.List;


/**
 * Restful 接收資料並進行驗證，產生的 error 訊息做客製化設定
 *
 * @author H-yin
 */
@ControllerAdvice
public class ApiExceptionConfig extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                              HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ResponseObj apiError = new ResponseObj(HttpStatus.BAD_REQUEST, errors, ex.getLocalizedMessage(), "Bad Request");
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

}
