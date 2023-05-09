package com.sxt.service;

import com.sxt.pojo.Studentexam;

import java.util.List;



public interface StudentExamService {
    Integer addStudentExam(Studentexam studentexam);

    List<Studentexam> getstuExamList(Integer userid);

    List<Studentexam> getIsPaper(Integer userid,Integer eid);

    List<Studentexam> getAllStuScore(Integer classid);
}
