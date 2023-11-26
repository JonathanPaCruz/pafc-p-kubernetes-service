package com.jodatrash.spring.cloud.course.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jodatrash.spring.cloud.course.entity.Course;
import com.jodatrash.spring.cloud.course.services.CourseService;

@RestController
@RequestMapping("/api")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/private/v1/info/course")
    public ResponseEntity<List<Course>> view() {
        return ResponseEntity.ok(courseService.info());
    }

    @GetMapping("/private/v1/info/course/{id}")
    public ResponseEntity<?> detailCourse(@PathVariable Long id) {
        Optional<Course> oCurse = courseService.byId(id);
        if (oCurse.isPresent()) {
            return ResponseEntity.ok().body(oCurse.get());
        }
        return ResponseEntity.notFound().build();
    }
}
