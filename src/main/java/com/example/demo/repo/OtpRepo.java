package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.OtpEntity;

public interface OtpRepo extends JpaRepository<OtpEntity, String> {

	OtpEntity findByEmailAndOtp(String email, String otp);

}
