package com.jodatrash.spring.cloud.course.repositories;

import org.springframework.data.repository.CrudRepository;

import com.jodatrash.spring.cloud.course.entity.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {

}
