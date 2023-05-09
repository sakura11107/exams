package com.sxt.service.impl;

import com.sxt.dao.ExamDao;
import com.sxt.dao.PaperDao;
import com.sxt.dao.StudentExamDao;
import com.sxt.pojo.Exam;
import com.sxt.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    ExamDao examDao;
    @Autowired
    PaperDao paperDao;
    @Autowired
    StudentExamDao studentExamDao;
    @Override
    public Integer addExam(Exam exam) {
        return examDao.addExam(exam);
    }

    @Override
    public List<Exam> getAllExam() {
        return examDao.getAllExam();
    }

    @Override
    public Exam getExam(Integer eid) {
        return examDao.getExam(eid);
    }

    @Override
    public Integer updExam(Exam exam) {
        return examDao.updExam(exam);
    }

    @Override
    public Integer delExam(Integer eid) {
        //清空试卷信息
        paperDao.deleteByEid(eid);
        studentExamDao.deleteEid(eid);
        return examDao.delExam(eid);
    }

    @Override
    public List<Exam> getExamClassid(Integer classid) {
        return examDao.getExamClassid(classid);
    }
}
