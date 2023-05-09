package com.sxt.service;

import com.sxt.pojo.Exam;

import java.util.List;


public interface ExamService {
    Integer addExam(Exam exam);

    List<Exam> getAllExam();
    Exam getExam(Integer eid);

    Integer updExam(Exam exam);

    Integer delExam(Integer eid);

    List<Exam> getExamClassid(Integer classid);
}
