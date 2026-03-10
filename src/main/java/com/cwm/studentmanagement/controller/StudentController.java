package com.cwm.studentmanagement.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.studentmanagement.dto.StudentDTO;
import com.cwm.studentmanagement.service.StudentService;

import jakarta.validation.Valid;

/*
 * Copyright (c) 2026 Mahesh Shet
 * Licensed under the MIT License.
 */

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Page<StudentDTO>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        return ResponseEntity.ok(studentService.getStudents(page, size));
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> all() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody StudentDTO dto) {
        if (studentService.existsByEmailIgnoreCase(dto.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        return ResponseEntity.ok(studentService.createStudent(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody StudentDTO dto) {
        if (studentService.existsByEmailIgnoreCaseAndIdNot(dto.getEmail(), id)) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        return ResponseEntity.ok(studentService.updateStudent(id, dto));
    }

    /**
     * DELETE /api/students/{id}
     * Cascade delete: because Students.enrollments has CascadeType.ALL + orphanRemoval=true,
     * all Enrollment rows for this student are deleted automatically by JPA.
     * This fixes the "ghost enrollments" bug when a student record is removed.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}