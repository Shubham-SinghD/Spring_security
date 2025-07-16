package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, String>{


	Optional<Customer> findByEmailIgnoreCase(String email);
	Optional<Customer> findByMobile(long mobile);

}
