package com.example.student_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student_management.exception.StudentNotFoundException;
import com.example.student_management.model.Student;
import com.example.student_management.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	public List<Student> getAllStudents()
	{
		return studentRepository.findAll();
	}
	
	public Student getStudentById(Long id) {
	    return studentRepository.findById(id)
	            .orElseThrow(() -> new StudentNotFoundException(id));
	}
	
	public Student addStudent(Student student)
	{
		return studentRepository.save(student);
	}
	
	public Student updateStudent(Long id, Student updatedStudent)
	{
		Student existing = studentRepository.findById(id)
		        .orElseThrow(() -> new StudentNotFoundException(id));
		
		existing.setName(updatedStudent.getName());
		existing.setEmail(updatedStudent.getEmail());
		existing.setPhone(updatedStudent.getPhone());
		existing.setCourse(updatedStudent.getCourse());
		
		return studentRepository.save(existing);
		
	}
	
	public void deleteStudent(Long id)
	{
		if (!studentRepository.existsById(id)) {
		    throw new StudentNotFoundException(id);
		}
		studentRepository.deleteById(id);
	}

}
