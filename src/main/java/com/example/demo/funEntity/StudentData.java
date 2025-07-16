package com.example.demo.funEntity;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Component
@Data
public class StudentData {
	
	@JsonProperty("studentName")
	private String name;
	@JsonProperty("studentRollNo")
	private Long rollno;
	@JsonProperty("studentAddress")
	private String address;
	

}
