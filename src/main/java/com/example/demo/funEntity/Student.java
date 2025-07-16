package com.example.demo.funEntity;


import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Component
@Entity
@Table(name="shubham_student_Manag100")
public class Student {
	@Id
	private Long rollno;
	private String section;
	private String name;
	private String address;
}
