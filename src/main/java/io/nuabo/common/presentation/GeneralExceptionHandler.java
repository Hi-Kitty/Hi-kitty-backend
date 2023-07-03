package io.nuabo.common.presentation;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.nuabo.common.domain.exception.*;
import io.nuabo.common.domain.utils.ApiUtils;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.lang.module.ResolutionException;
import java.security.SignatureException;



@Slf4j
@ControllerAdvice
public class GeneralExceptionHandler {


    private ResponseEntity<ApiUtils.ApiResult<?>> newResponse(Throwable throwable, HttpStatus status) {
        return newResponse(throwable.getMessage(), status);
    }

    private ResponseEntity<ApiUtils.ApiResult<?>> newResponse(String message, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(ApiUtils.error(message, status), headers, status);
    }

    // 필요한 경우 적절한 예외타입을 선언하고 newResponse 메소드를 통해 응답을 생성하도록 합니다.

    @ExceptionHandler({
            NoHandlerFoundException.class,
            NotFoundException.class,
            ResolutionException.class,
//            MyUsernameNotFoundException.class,
            ResourceNotFoundException.class
    })
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        return newResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureException() {
        return newResponse("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<?> handleMalformedJwtException() {
        return newResponse("올바르지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException() {
        return newResponse("토큰이 만료되었습니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException() {
        return newResponse("접근 권한이 없습니다. 확인해주세요.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException() {
        return newResponse("올바르지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<?> handleInsufficientAuthenticationException() {
        return newResponse("토큰의 값이 비었습니다. 확인해주세요", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException() {
        return newResponse("이메일과 비밀번호가 올바르지 않습니다. 다시 확인해주세요", HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler({UnauthorizedException.class
    })
    public ResponseEntity<?> handleUnauthorizedException(Exception e) {
        return newResponse(e, HttpStatus.UNAUTHORIZED);
    }




    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            NotValidException.class
    })
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        log.debug("Bad request exception occurred: {}", e.getMessage(), e);
        if (e instanceof MethodArgumentNotValidException) {
            return newResponse(
                    ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipartFileException.class)
    public ResponseEntity<?> handleMultipartFileException(Exception e) {
        log.debug("handleMultipartFileException occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SaveAmazonS3Exception.class)
    public ResponseEntity<?> handleSaveAmazonS3Exception(Exception e) {
        log.debug("SaveAmazonS3Exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<?> handleHttpMediaTypeException(Exception e) {
        return newResponse(e, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<?> handleMessageException(Exception e) {
        return newResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowedException(Exception e) {
        return newResponse(e, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CertificationCodeNotMatchedException.class)
    public ResponseEntity<?> handleCertificationCodeNotMatchedException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxUploadSizeExceededException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponse("10MB안으로 가능합니다!!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponse(e.getMessage() + "데이터의 크기를 확인해주세요!!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HeartException.class)
    public ResponseEntity<?> handleHeartException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}