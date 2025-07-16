package com.example.demo.funEntity;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
@Component
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Session {

}
