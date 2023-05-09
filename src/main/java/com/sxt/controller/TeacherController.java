package com.sxt.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxt.pojo.*;
import com.sxt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Controller
public class TeacherController {
    @Autowired
    PClassService pClassService;
    @Autowired
    UsersService usersService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    CourseService courseService;
    @Autowired
    ExamService examService;
    @Autowired
    PaperService paperService;
    @Autowired
    StudentExamService studentExamService;


    //查询学生列表
    @GetMapping("/StudentList")
    public String findStuUsers(HttpServletRequest request, Model model, @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){
        //获取班级id
        Integer teaClassid = (Integer) request.getSession().getAttribute("TeaClassid");
        //查看教师所在班级
        PClass classid = pClassService.getClassid(teaClassid);
        model.addAttribute("pj",classid);

        //设置分页
        PageHelper.startPage(pageNum,5);
        //查询学生列表
        List<Users> users = usersService.pageByClassid(teaClassid);
        PageInfo<Users> usersPageInfo = new PageInfo<>(users);
        model.addAttribute("pageInfo",usersPageInfo);
        model.addAttribute("liss",users);

        //获取所有班级信息
        List<PClass> allPClass = pClassService.getAllPClass();
        model.addAttribute("list",allPClass);

        return "teacher/StudentList";
    }
    //添加学生
    @PostMapping("/addStu")
    public  String addStuUsers(String username,String userpwd,String truename, Integer classid ){
       //查询学生是否存在
        Users byName = usersService.getByName(username);
        Users users = new Users();
        if(byName==null){
            users.setUsername(username);
            users.setUserpwd(userpwd);
            users.setTruename(truename);
            users.setClassid(classid);
            usersService.addUsers(users);
        }else {
            System.out.println("该学生已存在");
        }

        return "redirect:/StudentList";
    }
//查询所有班级
    @ResponseBody
    @PostMapping("/findAllClass")
    public List<PClass> pClassList(){
        List<PClass> allPClass = pClassService.getAllPClass();
        return allPClass;
    }
//获取用户信息
    @ResponseBody
    @PostMapping("/StuEdit")
    public  Users stuEdit(@RequestBody Users users){
        Users byUserid = usersService.getByUserid(users.getUserid());
        if (byUserid!=null){
            return byUserid;
        }else {
            return null;
        }
    }

    //修改学生
    @PostMapping("/updateStu")
    public String updStuUser(Users users){
        usersService.updateUsers(users);
        return "redirect:/StudentList";
    }
    //删除单个学生
    @GetMapping("/DeleteStu")
    public String deleteUsers(HttpServletRequest request){
        Integer userid = Integer.valueOf(request.getParameter("userid"));
        usersService.delUserid(userid);
        return "redirect:/StudentList";
    }
    //删除教师所在班级的全部学生
    @GetMapping("/deleteAll")
    public String deleteUsersAll(HttpServletRequest request){
        Integer teaClassid = (Integer) request.getSession().getAttribute("TeaClassid");
        usersService.delClassid(teaClassid);
        return "redirect:/StudentList";
    }

    //查询题目
    @GetMapping("/finddanxuan")
    public String findSingle(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,Model model){
        PageHelper.startPage(pageNum,5);
        List<Subject> allSubject = subjectService.getAllSubject();
        for (Subject subject:allSubject){
            Course allById = courseService.getAllById(subject.getCno());
            subject.setCourse(allById);
        }
        PageInfo<Subject> subjectPageInfo = new PageInfo<>(allSubject);
        model.addAttribute("pageInfo",subjectPageInfo);
        model.addAttribute("subjectlist",allSubject);
        return "teacher/Single";
    }
    //获取所有课程
    @ResponseBody
    @GetMapping("/findAllCourse")
    public List<Course> courseList(){
        List<Course> allCourse = courseService.getAllCourse();
        return allCourse;
    }
    //添加题
    @PostMapping("/addSingle")
    public String addSingle(Integer cno,String scontent,String sa,String sb,String sc,String sd,String skey){

        Subject subject = new Subject();
        subject.setCno(cno);
        subject.setScontent(scontent);
        subject.setSa(sa);
        subject.setSb(sb);
        subject.setSc(sc);
        subject.setSd(sd);
        subject.setSkey(skey);
        subjectService.addSingle(subject);
        return "redirect:/finddanxuan";
    }
    //获取题数据
    @ResponseBody
    @PostMapping("/findBySid")
    public Subject findBySid(@RequestBody Subject subject){
        Subject subject1 = subjectService.geiBySid(subject.getSid());
        if (subject1!=null){
            return subject1;
        }else {
            return null;
        }
    }
    //修改题
    @PostMapping("/updateSingle")
    public String updSingle(Subject subject){
        subjectService.updateSingle(subject);
        return "redirect:/finddanxuan";
    }
    //删除题
    @PostMapping("/deleteSingle")
    public String delSingle(@RequestParam Integer sid){
        subjectService.delSingle(sid);
        return "redirect:/finddanxuan";
    }

    //查询考试信息
    @GetMapping("/selectexam")
    public String selectExam(@RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum,Model model){
        PageHelper.startPage(pageNum,5);
        List<Exam> allExam = examService.getAllExam();
        PageInfo<Exam> examPageInfo = new PageInfo<>(allExam);
        model.addAttribute("examlist",allExam);
        model.addAttribute("pageInfo",examPageInfo);
        return "teacher/SelectExam";
    }
    //跳转到添加考试页面
    @GetMapping("/addexam")
    public String toAddExam(){
        return "teacher/addexam";
    }

    //创建考试
    @PostMapping("/addexames")
    public String addExam(String pname, Integer userid, Integer cno, Integer classid,
                          Integer singlenumber, Integer singlecore, Date examdate,
                          Date examtime,Integer testtime){

        //生成考试信息
        Exam exam=new Exam();
        exam.setPname(pname);
        exam.setUserid(userid);
        exam.setCno(cno);
        exam.setClassid(classid);
        exam.setSinglenumber(singlenumber);
        exam.setSinglecore(singlecore);
        exam.setExamdate(examdate);
        exam.setExamtime(examtime);
        exam.setTesttime(testtime);
        examService.addExam(exam);



        //通过课程查询
        List<Subject> subject = subjectService.getSubject(cno);

        //题的随机生成
        ArrayList<Subject> subjects = new ArrayList<>();
        Random random = new Random();
        if (singlenumber>0){
            for (int i=1;i<=singlenumber;i++){
                int s = random.nextInt(subject.size());

                Subject subject1 = subject.get(s);


                if (i==subject.size()){
                    break;
                }else {
                    if (subjects.contains(subject1)){
                        i--;
                    }else {
                        subjects.add(subject.get(s));
                        Paper paper = new Paper();
                        paper.setEid(exam.getEid());
                        paper.setCno(cno);
                        paper.setSid(subject1.getSid());
                        paper.setScontent(subject1.getScontent());
                        paper.setSa(subject1.getSa());
                        paper.setSb(subject1.getSb());
                        paper.setSc(subject1.getSc());
                        paper.setSd(subject1.getSd());
                        paper.setSkey(subject1.getSkey());
                        paperService.addPaper(paper);
                    }
                }
            }
        }

        return "redirect:/selectexam";
    }
//查询试卷信息
    @GetMapping("/paperDetails")
    public  String paperDetails(Integer eid,Model model){
        List<Paper> byEid = paperService.getByEid(eid);
        model.addAttribute("tm",byEid);
        Exam exam = examService.getExam(eid);
        model.addAttribute("exam",exam);
        return "teacher/paperDetails";
    }
    //查询一场考试
    @ResponseBody
    @PostMapping("/findByOneExam")
    public  Exam findByOneExam(@RequestBody Exam exam){
        Exam exam1 = examService.getExam(exam.getEid());
        if (exam1==null) {
            return null;
        }else {
            return exam1;
        }
    }
    //获取所有班级
    @ResponseBody
    @GetMapping("/findAllClasses")
    public List<PClass> findAllClasses(){
        List<PClass> allPClass = pClassService.getAllPClass();
        return allPClass;
    }
    //修改考试信息
    @PostMapping("/updateExam")
    public String updateExam(Exam exam){
        examService.updExam(exam);
        return "redirect:/selectexam";
    }
    //删除考试信息
    @GetMapping("/deleteExam")
    public String deleteExam(Integer eid){
        examService.delExam(eid);
        return "redirect:/selectexam";
    }

    //查询学生成绩
    @GetMapping("/findAllScore")
    public String findAllScore(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request,Model model){
       PageHelper.startPage(pageNum,5);
        //获取教师班级id
        Integer teaClassid = (Integer) request.getSession().getAttribute("TeaClassid");
        PClass classes = pClassService.getClassid(teaClassid);
        model.addAttribute("cs",classes);
        List<Studentexam> allStuScore = studentExamService.getAllStuScore(teaClassid);
        for (Studentexam studentexam : allStuScore) {
            Users byUserid = usersService.getByUserid(studentexam.getUserid());
            studentexam.setUsers(byUserid);
        }
        PageInfo<Studentexam> studentexamPageInfo = new PageInfo<>(allStuScore);
        model.addAttribute("pageInfo",studentexamPageInfo);
        model.addAttribute("score",allStuScore);
        return "teacher/studentScore";
    }

}
