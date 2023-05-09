package com.sxt.dao;

import com.sxt.pojo.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CourseDao {
    //通过id查询课程
    Course getAllById(Integer cno);
    //查询全部
    List<Course> getAllCourse();
}
