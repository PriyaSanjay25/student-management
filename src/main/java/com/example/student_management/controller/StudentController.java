package com.example.student_management.controller;

import com.example.student_management.model.Student;
import com.example.student_management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController              // Tells Spring: this class handles HTTP requests
@RequestMapping("/students") // All APIs will start with /students
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 1. GET all students
    // URL: GET http://localhost:8080/students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // 2. GET student by ID
    // URL: GET http://localhost:8080/students/1
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. POST - Add new student
    // URL: POST http://localhost:8080/students
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student saved = studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // 4. PUT - Update student
    // URL: PUT http://localhost:8080/students/1
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,
                                                  @RequestBody Student student) {
        try {
            Student updated = studentService.updateStudent(id, student);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE - Delete student
    // URL: DELETE http://localhost:8080/students/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}