package com.jodatrash.spring.cloud.course.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jodatrash.spring.cloud.course.entity.Course;
import com.jodatrash.spring.cloud.course.services.CourseService;

@RestController
@RequestMapping("/api")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/private/v1/info/all")
    public ResponseEntity<List<Course>> view() {
        return ResponseEntity.ok(courseService.info());
    }

    @GetMapping("/private/v1/info/course/{id}")
    public ResponseEntity<?> detailCourse(@PathVariable Long id) {
        Optional<Course> oCourse = courseService.byId(id);
        if (oCourse.isPresent()) {
            return ResponseEntity.ok().body(oCourse.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/private/v1/create/course")
    public ResponseEntity<?> create(@Valid @RequestBody Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return getValidateResponse(bindingResult);
        }
        Course courseDb = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseDb));
    }

    @PutMapping("/private/v1/edit/course/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody Course course, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()){
            return getValidateResponse(bindingResult);
        }
        Optional<Course> oCourse = courseService.byId(id);
        if (oCourse.isPresent()) {
            Course courseDb = oCourse.get();
            courseDb.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/private/v1/delete/course/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Course> oCourse = courseService.byId(id);
        if (oCourse.isPresent()) {
            courseService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }
    private static ResponseEntity<Map<String, String>> getValidateResponse(BindingResult bindingResult) {
        Map<String, String> error = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            error.put(fieldError.getField(), "Campo " + fieldError.getField() +  " " + fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(error);
    }
}
