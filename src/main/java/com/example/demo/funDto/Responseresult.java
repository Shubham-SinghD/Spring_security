package com.example.demo.funDto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.funEntity.Response;

import lombok.Data;
@Component
@Data
public class Responseresult {
	
	private List<Response> response;

}
