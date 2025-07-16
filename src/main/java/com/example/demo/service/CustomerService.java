package com.example.demo.service;

import java.util.Optional;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.ResetClass;
import com.example.demo.repo.CustomerRepo;


@Service
public class CustomerService {
	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	AuthenticationManager authenticationManager;
	public Customer validateLogin(Customer loginHandler) throws AuthenticationException {
	    String identifier = loginHandler.getEmail(); 

	    if (identifier == null || identifier.trim().isEmpty()) {
	        if (loginHandler.getMobile()!=null) {
	            identifier = String.valueOf(loginHandler.getMobile());
	        } else {
	            throw new BadCredentialsException("Email or Mobile must be provided");
	        }
	    }
	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(
	            identifier,
	            loginHandler.getPassword()
	        )
	    );

	    if (authentication.isAuthenticated()) {
	        Optional<Customer> customer;
	        if (identifier.contains("@")) {
	            customer = customerRepo.findByEmailIgnoreCase(identifier);
	        } else {
	            customer = customerRepo.findByMobile(Long.parseLong(identifier));
	        }
	        return customer.orElseThrow(() -> new BadCredentialsException("User not found"));
	    }

	    return null;
	}

	//Registration
	private BCryptPasswordEncoder en=new BCryptPasswordEncoder(12);
	public boolean register(Customer data) {
		 Optional<Customer> byEmailIgnoreCase = customerRepo.findByEmailIgnoreCase(data.getEmail());
		 Optional<Customer> byMobile = customerRepo.findByMobile(data.getMobile());
		if(!byEmailIgnoreCase.isPresent()&&!byMobile.isPresent()) {
		data.setPassword(en.encode(data.getPassword()));
		customerRepo.save(data);
		return true;
		}else {
			return false;
		}
	}

	public boolean reset(ResetClass resetClass) {
	    Optional<Customer> optionalCustomer = customerRepo.findByEmailIgnoreCase(resetClass.getEmail());
	    if (optionalCustomer.isEmpty()) {
	        return false;
	    }
	    Customer customer = optionalCustomer.get();
	    customer.setPassword(en.encode(resetClass.getPassword()));
	    customerRepo.save(customer);
	    return true;
	}


}
