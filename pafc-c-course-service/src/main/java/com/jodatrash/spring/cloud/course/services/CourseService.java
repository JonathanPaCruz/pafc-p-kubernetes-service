package com.jodatrash.spring.cloud.course.services;

import java.util.List;
import java.util.Optional;

import com.jodatrash.spring.cloud.course.entity.Course;

public interface CourseService {
    List<Course> info();

    Optional<Course> byId(Long id);

    Course save(Course course);

    void delete(Long id);

}
