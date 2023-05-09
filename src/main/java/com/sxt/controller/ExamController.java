package com.sxt.controller;


import com.sxt.pojo.Course;
import com.sxt.pojo.Exam;
import com.sxt.pojo.Paper;
import com.sxt.pojo.Studentexam;
import com.sxt.service.CourseService;
import com.sxt.service.ExamService;
import com.sxt.service.PaperService;
import com.sxt.service.StudentExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Controller
public class ExamController {
    @Autowired
    ExamService examService;
    @Autowired
    CourseService courseService;
    @Autowired
    PaperService paperService;
    @Autowired
    StudentExamService studentExamService;

    //查询考试信息
    @GetMapping("/examList")
    public String examList(HttpServletRequest request, Model model){
        Integer classid = (Integer) request.getSession().getAttribute("classid");
        List<Exam> examClassid = examService.getExamClassid(classid);
        for (Exam exam : examClassid) {
            Course allById = courseService.getAllById(exam.getCno());
            exam.setCourse(allById);
        }
        model.addAttribute("examslenth",examClassid.size());
        model.addAttribute("exams",examClassid);
        return "student/examList";
    }
    //查询数据
    @ResponseBody
    @PostMapping("/findExamByEid")
    public Exam findExamById(@RequestBody Exam exam){
        Exam exam1 = examService.getExam(exam.getEid());
        if (exam1!=null){
            return exam1;
        }else {
            return null;
        }
    }
    //查询试卷
    @GetMapping("/paper")
    public String findPaper(Integer eid, HttpServletRequest request,Model model){
        List<Paper> byEid = paperService.getByEid(eid);
        Integer size = byEid.size();
        Exam exam = examService.getExam(eid);
        request.getSession().setAttribute("single",byEid);
        model.addAttribute("cont",size);
        model.addAttribute("exam",exam);

        return "student/papers";
    }
    //显示考试成绩
    @PostMapping("/PaperScore")
    public String paperScore(HttpServletRequest request,Model model){
        //存放答案
        String stuArr[]=null;
        //初始成绩
        Integer score=0;
        //
        Integer eid = Integer.valueOf(request.getParameter("eid"));
        Exam exam = examService.getExam(eid);
        Integer singlecore = exam.getSinglecore();


        //获取试卷问题列表
        List<Paper> single = (List<Paper>) request.getSession().getAttribute("single");
        for (int i = 0;i<single.size();i++){
            Paper paper = single.get(i);
            stuArr = request.getParameterValues(String.valueOf(paper.getSid()));
            if (stuArr!=null){
                String stuKeys="";
                //封装答案
                for (int j = 0; j < stuArr.length; j++) {
                    stuKeys +=stuArr[j];
                }
                if (stuKeys.equalsIgnoreCase(paper.getSkey())){
                    score=score+singlecore;
                }else {

                }

            }else {
                return "redirect:/examList";
            }
        }

        //计算总成绩
        int zscore = single.size() * singlecore;
        //学生成绩
        model.addAttribute("score",score);
        String pname = request.getParameter("pname");
        String tjtime = request.getParameter("tjtime");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Timestamp.valueOf(tjtime);
        Integer classid = (Integer) request.getSession().getAttribute("classid");
        Integer userid = (Integer) request.getSession().getAttribute("lis");


        Studentexam studentexam = new Studentexam();
        studentexam.setEid(eid);
        studentexam.setPname(pname);
        studentexam.setScore(score);
        studentexam.setClassid(classid);
        studentexam.setUserid(userid);
        studentexam.setTitime(tjtime);
        studentexam.setZscore(zscore);
        studentExamService.addStudentExam(studentexam);

        return "student/paperScore";
    }

    //查询学生成绩
    @GetMapping("/findAllStuPaper")
    public String findStuPaperList(HttpServletRequest request,Model model){
        Integer userid = (Integer) request.getSession().getAttribute("lis");
        List<Studentexam> studentexams = studentExamService.getstuExamList(userid);
        model.addAttribute("stuexamlist",studentexams);
        return "student/stuPaperList";
    }
    //是否做过该试卷
    @ResponseBody
    @PostMapping("/findOneStuExam")
    public List<Studentexam> findIsPaper(HttpServletRequest request,@RequestBody Exam exam){
        //学生id
        Integer userid = (Integer) request.getSession().getAttribute("lis");
        //考试信息id
        Integer eid = exam.getEid();
        List<Studentexam> isPaper = studentExamService.getIsPaper(userid, eid);
        return isPaper;
    }
    //解决点击在线考试报错
    @GetMapping("/StuMan")
    public String getStuMan(){
        return "student/StuMan";
    }


}
