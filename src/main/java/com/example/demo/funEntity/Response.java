package com.example.demo.funEntity;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Component
@Data
public class Response {
	
	@JsonProperty("session")
	private Session session;
	
	@JsonProperty("txtData")
	private TextData txtData;

}
