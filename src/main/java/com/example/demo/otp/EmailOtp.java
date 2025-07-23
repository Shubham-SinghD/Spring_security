package com.example.demo.otp;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.OtpEntity;
import com.example.demo.repo.CustomerRepo;
import com.example.demo.repo.OtpRepo;

import jakarta.mail.MessagingException;

@Service
public class EmailOtp {
	
	 @Autowired
	    private JavaMailSender mailSender;
	 
	 @Autowired
	private CustomerRepo customerRepo;

	 
	 
	 @Autowired
	private  OtpRepo otpRepo;
	 
	    private String generateOtp() {
	        SecureRandom random = new SecureRandom();
	        int otp = 100000 + random.nextInt(900000);
	        return String.valueOf(otp);
	    }
	    public boolean sendOtp(String email) {
	        String otp = generateOtp();
	        String cleanEmail = email.trim().replaceAll("\\s+", "");
	        Optional<Customer> byEmailIgnoreCase = customerRepo.findByEmailIgnoreCase(cleanEmail);
	        if(byEmailIgnoreCase.isPresent()) {
	        	OtpEntity en=new OtpEntity();
		        LocalDateTime dateTime=LocalDateTime.now();
		        en.setEmail(cleanEmail);
		        en.setOtp(otp);
		        en.setLocalDateTime(dateTime);
		        otpRepo.save(en);
		        try {
		            sendOtpToEmail(cleanEmail, otp);
		            return true;
		        } catch (MessagingException e) {
		            e.printStackTrace();
		            return false;
		        }
	        }else {
	        	return false;
	        }
	    }
	    
	    private void sendOtpToEmail(String email, String otp) throws MessagingException {
	       SimpleMailMessage simpleMailMessage =new SimpleMailMessage();
	       simpleMailMessage.setSubject("Varification");
	       simpleMailMessage.setTo(email);
	       simpleMailMessage.setText("Your otp is : "+otp +"\n"+"Valid for five minutes");
//	       simpleMailMessage.setFrom("Shubham Singh<shubham@example.com>");
	       simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
	       mailSender.send(simpleMailMessage);
	    }
	    
	    public boolean getValidation(String email,String otp) {
	    	  OtpEntity otpEntity = otpRepo.findByEmailAndOtp(email,otp);
	    	 
	    	 LocalDateTime dateTime=LocalDateTime.now();
	    	 if(dateTime.isAfter((otpEntity.getLocalDateTime().plusMinutes(5)))){
	    		 return false;
	    	 }
	    	 if(email==null||otp==null) {
	    		 return false;
	    	 }
	    	return true; 
	    	
	    }
	    
	    
}
