package com.sxt.service.impl;

import com.sxt.dao.CourseDao;
import com.sxt.pojo.Course;
import com.sxt.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDao courseDao;
    @Override
    public Course getAllById(Integer cno) {
        return courseDao.getAllById(cno);
    }

    @Override
    public List<Course> getAllCourse() {
        return courseDao.getAllCourse();
    }
}
