package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.Customer;
import com.example.demo.entity.ResetClass;
import com.example.demo.jjwt.JwtUtil;
import com.example.demo.otp.EmailOtp;
import com.example.demo.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/student")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	EmailOtp emailOtp;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@PostMapping("/login")
	public Map<String, Object> getToken(@RequestBody Customer handler) throws AuthenticationException {
		Customer validateLogin = customerService.validateLogin(handler);
		 System.err.println(validateLogin);
		 String token = jwtUtil.generateToken(handler.getEmail());
		 Map<String , Object>mp=new HashMap<String, Object>();
		 mp.put("Data", validateLogin);
		 mp.put("Token", token);
		 return mp;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> getRegister(@RequestBody Customer  loginData) {
		  boolean register = customerService.register(loginData);
		 if(register) {
			 return ResponseEntity.ok().body("User Save Successfully");
		 }else {
			 return ResponseEntity.status(HttpStatus.CONFLICT).body("User Already Found");
		 }
		 
	}
	
	@PostMapping("/reset")
	public ResponseEntity<String> getReset(@RequestBody ResetClass loginData) {
	    boolean reset = customerService.reset(loginData);

	    if (reset) {
	        return ResponseEntity.ok("User password updated successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }
	}

	@GetMapping("/otp")
	public ResponseEntity<String> getOtp(@RequestParam String email) {
	    boolean sendOtp = emailOtp.sendOtp(email);
	    System.err.println(sendOtp);
	    if (!sendOtp) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTP could not be sent.");
	    }
	    return ResponseEntity.ok("OTP sent successfully.");
	}
	
	@PostMapping("/Otpvalidation")
	public String ValidateOtp(@RequestParam String email ,@RequestParam String otp) {
		System.err.println(email+"  "+otp);
		boolean validation = emailOtp.getValidation(email, otp);
		if(validation) {
			return "validation";
		}else {
			return "Invalide or Expired";
		}
	}
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader, HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate(); 
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
//	        String token = authHeader.substring(7); 

	        return ResponseEntity.ok("Logged out successfully and token revoked.");
	    } else {
	        return ResponseEntity.badRequest().body("Invalid Authorization header.");
	    }
	}
	

}
