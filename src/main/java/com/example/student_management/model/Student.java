package com.example.student_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private String course;

    // ── Empty Constructor ──────────────────
    public Student() {}

    // ── Full Constructor ───────────────────
    public Student(Long id, String name, String email, String phone, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.course = course;
    }

    // ── Getters ────────────────────────────
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getCourse() { return course; }

    // ── Setters ────────────────────────────
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setCourse(String course) { this.course = course; }

    // ── toString (for printing/debugging) ──
    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + 
               "', phone='" + phone + "', course='" + course + "'}";
    }
}