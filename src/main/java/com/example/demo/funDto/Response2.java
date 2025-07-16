package com.example.demo.funDto;


import org.springframework.stereotype.Component;

import com.example.demo.funEntity.Session;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Component
@Data
public class Response2 {
	
	@JsonProperty("session")
	private Session session;
	
	@JsonProperty("txtData")
	private TextData2 txtData2;

}
