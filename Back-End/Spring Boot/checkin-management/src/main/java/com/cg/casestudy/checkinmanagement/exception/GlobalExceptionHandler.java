package com.cg.casestudy.checkinmanagement.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cg.casestudy.checkinmanagement.model.CustomError;
import com.cg.casestudy.checkinmanagement.model.ErrorList;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		CustomError customError = new CustomError(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(status).body(customError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		CustomError customError = new CustomError(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(status).body(customError);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		CustomError customError = new CustomError(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(status).body(customError);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		CustomError customError = new CustomError(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(status).body(customError);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		CustomError customError = new CustomError(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(status).body(customError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		CustomError customError = new CustomError(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(status).body(customError);
	}
	
	// Handling exception if inputs are not valid
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorList error = new ErrorList(LocalDateTime.now(), "Validation Failed", details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

	// Custom Exception - Handling exception if 'Fare' is not available in the database
	@ExceptionHandler(CheckInNotFoundException.class)
	public ResponseEntity<Object> handleFareNotFoundException(CheckInNotFoundException ex, WebRequest request){
		CustomError customError = new CustomError(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customError);
	}
	// Custom Exception - Handling exception if 'Id' is not available in the database
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<Object> handleIdNotFoundException(IdNotFoundException ex, WebRequest request){
		CustomError customError = new CustomError(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customError);
	}
	
	// Custom Exception - Handling All other exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request){
		CustomError customError = new CustomError(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customError);
	}
	
}