package se.sbab.insurance.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Global exception handler.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * Handle validation exception error response.
   *
   * @param ex the ex
   * @return the error response
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    List<String> errors = new ArrayList<>();
    for (FieldError fieldError : fieldErrors) {
      errors.add(fieldError.getField() + " " +fieldError.getDefaultMessage());
    }
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errors);
  }


  /**
   * Handle exception error response.
   *
   * @param ex the ex
   * @return the error response
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ErrorResponse handleException(Exception ex) {
    log.error(ex.getMessage());
    return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There is server error");
  }

  @Data
  @AllArgsConstructor
  private static class ErrorResponse {
    private int status;
    private Object message;
  }
}
