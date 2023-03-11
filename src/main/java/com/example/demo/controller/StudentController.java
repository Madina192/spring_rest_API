package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping("/students")
    Student newStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("/students")
    List<Student> getAllStudents() {
        return (List) studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    Optional<Student> getOneStudent(@PathVariable Long id) {
        return  studentRepository.findById(id);
    }

    @PutMapping("/students/{id}")
    Student updateStudent(@RequestBody Student newStudent, @PathVariable Long id) {
        return studentRepository.findById(id).map(
                student -> {
                    student.setName(newStudent.getName());
                    student.setGpa(newStudent.getGpa());
                    return studentRepository.save(student);
                }).orElseGet(() -> {
                    newStudent.setId(id);
                    return studentRepository.save(newStudent);
                }
        );
    }

    @DeleteMapping("/student/{id}")
    void deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}
