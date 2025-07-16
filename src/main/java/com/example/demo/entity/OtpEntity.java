package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class OtpEntity {

	@Id
	private String email;
	private String otp;
	private LocalDateTime localDateTime;
}
