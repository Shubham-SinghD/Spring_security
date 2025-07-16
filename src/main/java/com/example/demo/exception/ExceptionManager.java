package com.example.demo.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionManager {
	@ExceptionHandler(DuplicateStudent.class)
	public ResponseEntity<Map<String, String>> duplicateHandle(DuplicateStudent d) {
		return new ResponseEntity<Map<String, String>>(Map.of("Error",d.getMessage()),HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(StudentNotFound.class)
	public ResponseEntity<Map<String, String>> absentHandle(StudentNotFound d) {
		return new ResponseEntity<Map<String, String>>(Map.of("Error",d.getMessage()),HttpStatus.NOT_FOUND);
	}
	

}