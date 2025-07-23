package com.example.demo.exception;

public class StudentNotFound extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	public StudentNotFound(String s) {
		super(s);
	}
	

}
