package com.example.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.funEntity.Student;


@Repository
public interface StudentRepo extends JpaRepository<Student, Long>{
	
	Optional<Student> findBySectionAndRollno( String section,Long rollno);
	@Query("SELECT u FROM Student u where u.section=:section order by u.rollno ")
	List<Student> findBySection(String section);
	@Query("SELECT u FROM Student u  order by u.rollno ")
	List<Student> findAllStudent();
}
