package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.funDto.ResponseWrapper2;
import com.example.demo.funDto.Responseresult;
import com.example.demo.funEntity.ResponseWrapper;
import com.example.demo.jjwt.JwtUtil;
import com.example.demo.service.FunctionService;



@RestController
@RequestMapping("/student")
public class FunController {
	
	@Autowired
	FunctionService studentService;
	
	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/addStudent")
	public ResponseEntity<Map<String, String>> addStudent1(@RequestBody ResponseWrapper rs) {
		studentService.adding(rs);
		return new  ResponseEntity<Map<String, String>>(Map.of("Status","Student Added"),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getSingleStudent")
	public ResponseWrapper getData(@RequestParam Long studentRollno,@RequestParam String sectionName ) {
		System.out.println(sectionName+"    "+studentRollno);
		return studentService.getPartiStudent(sectionName,studentRollno);
	}
	
	@GetMapping("/GetAllData")
	public Responseresult getDataAll() {
		return studentService.getAllDetails();
	}
	
	@GetMapping("/sectionData")
	public ResponseWrapper2 getSectionData(@RequestParam String sectionName) {
		return studentService.sortBySection(sectionName);
	}
	
	@DeleteMapping("/deleted")
	public ResponseEntity<Map<String, String>> deleteStudent(@RequestParam Long studentRollno){
		studentService.deleteData(studentRollno);
	return new  ResponseEntity<Map<String, String>>(Map.of("Status","Student Delete"),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/rollno")
	public ResponseWrapper getDataStudent(@RequestParam Long studentRollno) {
		return studentService.getDataByRollNo(studentRollno);
	}
	
	
	
}
