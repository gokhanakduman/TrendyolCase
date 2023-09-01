package com.trendyol.linkConverter.controller.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.URIConversionException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	@ExceptionHandler(InvalidURIException.class)
    public ResponseEntity<Object> handleInvalidURIException(InvalidURIException ex, ServletWebRequest servletWebRequest) {
		logErrorWithRequestPath(servletWebRequest.getRequest().getRequestURI().toString(), ex);
		Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getErrorDescription());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(URIConversionException.class)
    public ResponseEntity<Object> handleURIConversionException(URIConversionException ex, ServletWebRequest servletWebRequest) {
		logErrorWithRequestPath(servletWebRequest.getRequest().getRequestURI().toString(), ex);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        
        switch (ex.getReason()) {
		case COULD_NOT_BUILD_URI:
			body.put("message", "Error occured while parsing respose URI");
			break;
		case COULD_NOT_MAP_TO_MODEL:
			body.put("message", "Error occured while mappingrequest URI to object");
			break;
		}

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	private void logErrorWithRequestPath(String requestPath, Exception e) {
		log.error(
				"Error occured at endpoint: {}", 
				requestPath,
				e
				);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleOtherExceptions(Exception ex) {
		log.error("Unknown Error occured", ex);
		ex.printStackTrace();
		Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}