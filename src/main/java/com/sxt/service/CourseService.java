package com.sxt.service;

import com.sxt.pojo.Course;

import java.util.List;


public interface CourseService {
    Course getAllById(Integer cno);
    List<Course> getAllCourse();
}
