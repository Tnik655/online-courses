package ru.tinik.online_courses.exceptionhandlers;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.tinik.online_courses.dto.ApiError;

@RestControllerAdvice
public class HandleErrorService {

	@ExceptionHandler
	public ResponseEntity<ApiError> noSuchElementExceptionHandler(NoSuchElementException ex) {
		return new ResponseEntity<>(new ApiError(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ApiError> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
		return new ResponseEntity<>(new ApiError(ex.getBindingResult().getFieldError().getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
	}
}
