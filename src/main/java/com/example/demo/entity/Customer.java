package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="shubham_customer")
public class Customer {
	
	@Id
	private String email;
	private String password;
	private String name;
	private String role;
	@Column(nullable = false,unique = true)
	private Long mobile;
	
	
	

}
