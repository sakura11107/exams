package com.sxt.service;

import com.sxt.pojo.Subject;

import java.util.List;


public interface SubjectService {
    List<Subject> getAllSubject();

    Integer addSingle(Subject subject);
    Integer updateSingle(Subject subject);

    Subject geiBySid(Integer sid);

    Integer delSingle(Integer sid);

    List<Subject> getSubject(Integer cno);

}
