package com.example.demo.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.repo.CustomerRepo;

@Service
public class MyUserService implements UserDetailsService {
	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    Optional<Customer> customer = customerRepo.findByEmailIgnoreCase(username);

	    if (customer.isEmpty()) {
	        try {
	            long mobile = Long.parseLong(username);
	            customer = customerRepo.findByMobile(mobile);
	        } catch (NumberFormatException e) {
	            throw new UsernameNotFoundException("Invalid email or mobile: " + username);
	        }
	    }

	    if (customer.isEmpty()) {
	        throw new UsernameNotFoundException("Customer not found with email or mobile: " + username);
	    }

	    return new UserPrinciple(customer.get());
	}
}
