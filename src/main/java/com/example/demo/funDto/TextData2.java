package com.example.demo.funDto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.funEntity.StudentData;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Component
@Data
public class TextData2 {
	
	@JsonProperty("sectionName")
	private String section;
	
	@JsonProperty("data")
	private List<StudentData> datas;


}
