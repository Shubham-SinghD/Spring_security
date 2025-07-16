package com.example.demo.funEntity;


import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Component
@Data
public class TextData {
	
	@JsonProperty("sectionName")
	private String section;
	
	@JsonProperty("data")
	private StudentData data;

}
