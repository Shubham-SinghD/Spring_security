package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.DuplicateStudent;
import com.example.demo.exception.StudentNotFound;
import com.example.demo.funDto.Response2;
import com.example.demo.funDto.ResponseWrapper2;
import com.example.demo.funDto.Responseresult;
import com.example.demo.funDto.TextData2;
import com.example.demo.funEntity.Response;
import com.example.demo.funEntity.ResponseWrapper;
import com.example.demo.funEntity.Session;
import com.example.demo.funEntity.Student;
import com.example.demo.funEntity.StudentData;
import com.example.demo.funEntity.TextData;
import com.example.demo.repo.StudentRepo;

@Service
public class FunctionService {
	@Autowired
	private StudentRepo studentrepo;

	@Autowired
	private ResponseWrapper rw;
	@Autowired
	private Responseresult rr;
	@Autowired
	private  TextData txtData;
	@Autowired
	private StudentData studentData ;
	@Autowired
	private Response response ;
	@Autowired
	private Session session ;
	@Autowired
	private ResponseWrapper2 rr2 ;
	@Autowired
	private TextData2 textData2;
	
	// Posting Data
	public void adding(ResponseWrapper rs) {
		String section = rs.getResponse().getTxtData().getSection();
		StudentData data = rs.getResponse().getTxtData().getData();
		Optional<Student> bySectionAndRollno = studentrepo.findBySectionAndRollno(section.toUpperCase(),data.getRollno());
		Optional<Student> byId = studentrepo.findById(data.getRollno());
		if(bySectionAndRollno.isPresent()||byId.isPresent()) {
			throw new DuplicateStudent("Student Alredy Present");
		}
		Student stu=new Student();
		stu.setName(data.getName());
		stu.setAddress(data.getAddress());
		stu.setRollno(data.getRollno());
		stu.setSection(section.toUpperCase());
		
		 studentrepo.save(stu);
	}
	
	// Find Single Data
	public ResponseWrapper getPartiStudent(String sectionName,long studentRollno) {
		Optional<Student> bySectionAndRollno = studentrepo.findBySectionAndRollno(sectionName.toUpperCase(), studentRollno);
		if(bySectionAndRollno.isEmpty()) {
			throw new StudentNotFound("Student Not Present in that : "+ sectionName.toUpperCase() +"  and   "+studentRollno);
		}
		Student stu = bySectionAndRollno.get();
		txtData.setSection(stu.getSection());
		studentData.setAddress(stu.getAddress());
		studentData.setName(stu.getName());
		studentData.setRollno(stu.getRollno());
		response.setSession(session);
		txtData.setData(studentData);
		response.setTxtData(txtData);
		rw.setResponse(response);
		return rw;
	}
	// Getting All Details
	public Responseresult getAllDetails() {
	    List<Student> allStudents = studentrepo.findAllStudent();
	    List<Response> responses = new ArrayList<>();
	    for (Student student : allStudents) {
	    	TextData textData = new TextData();
	        textData.setSection(student.getSection());
	        StudentData studentData = new StudentData();
	        studentData.setAddress(student.getAddress());
	        studentData.setName(student.getName());
	        studentData.setRollno(student.getRollno());
	        Response response = new Response();
	        response.setSession(session);
	        response.setTxtData(textData);
	        textData.setData(studentData);
	        responses.add(response);
	    }
	    rr.setResponse(responses);
	    return rr;
	}
	// Sorting by section
	public ResponseWrapper2 sortBySection(String section) {
	    List<Student> bySection = studentrepo.findBySection(section.toUpperCase());
	    if(bySection==null||bySection.isEmpty()) {
	    	throw new StudentNotFound("Student Not Present ");
	    }
	    textData2.setSection(section.toUpperCase()); 
	    List<StudentData> studentDataList = new ArrayList<>();
	    for (Student student : bySection) {
	        StudentData studentData = new StudentData();
	        studentData.setRollno(student.getRollno());
	        studentData.setName(student.getName());
	        studentData.setAddress(student.getAddress());
	        studentDataList.add(studentData);
	    }
	    textData2.setDatas(studentDataList);
	    Response2 response2 = new Response2();
	    response2.setSession(session);
	    response2.setTxtData2(textData2);
	    rr2.setResponse(response2);
	    return rr2;
	}
	
	//Delete Operation
	public void deleteData(Long studentRollno) {
		Optional<Student> byId = studentrepo.findById(studentRollno);
		if(byId.isEmpty()) {
			throw new StudentNotFound("Student Not Present ");
		}
		Student student = byId.get();
		studentrepo.delete(student);
	}
	
	
	//Find By Roll no
	public ResponseWrapper getDataByRollNo(Long studentRollno) {
		Optional<Student> byId = studentrepo.findById(studentRollno);
		if(byId.isEmpty()) {
			throw new StudentNotFound("Student Not Found");
		}
		Student student = byId.get();
		txtData.setSection(student.getSection()); 
	        StudentData studentData = new StudentData();
	        studentData.setRollno(student.getRollno());
	        studentData.setName(student.getName());
	        studentData.setAddress(student.getAddress());
	    txtData.setData(studentData);
	    response.setSession(session);
	    response.setTxtData(txtData);
	    rw.setResponse(response);
	    return rw;
		
	}
	

}
