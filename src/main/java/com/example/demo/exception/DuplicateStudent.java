package com.example.demo.exception;

public class DuplicateStudent extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DuplicateStudent(String s) {
		super(s);
	}
	
}
