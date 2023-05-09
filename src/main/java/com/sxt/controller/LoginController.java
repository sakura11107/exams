package com.sxt.controller;


import com.sxt.pojo.TeaUser;
import com.sxt.pojo.Users;

import com.sxt.service.TeaUserService;
import com.sxt.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;


@Controller

public class LoginController {
    @Autowired
    UsersService usersService;
    @Autowired
    TeaUserService teaUserService;


    //教师登陆与学生登陆的互相跳转
    @GetMapping("toTeacher")
    public String toTeaLogin(){
        return "teacher/login";
    }
    @GetMapping("toStuLogin")
    public String toStuLogin(){
        return "student/login";
    }

    //学生登陆
    @PostMapping("/stulogin")
    public String stulogin(String username, String userpwd, HttpServletRequest servletRequest){
        Users login = usersService.login(username, userpwd);

        if (login==null){
            System.out.println("登陆失败");
            servletRequest.getSession().setAttribute("mag","密码错误");
            return "redirect:/toStuLogin";
        }else {
            Integer userid = usersService.getByNameid(username);
            String trueName = usersService.getTrueName(username);
            Integer byClass = usersService.getByClass(username);
            System.out.println("登陆成功");
            servletRequest.getSession().setAttribute("liss",userpwd);
            servletRequest.getSession().setAttribute("truename",trueName);
            servletRequest.getSession().setAttribute("lis",userid);
            servletRequest.getSession().setAttribute("classid",byClass);
            return "student/StuMan";
        }

    }


    //教师登陆
    @PostMapping("/tealogin")
    public String teaLogin(String username,String userpwd,HttpServletRequest request){

        //查询班级id
        Integer classId = teaUserService.getClassId(username);
        //获取教师id
        Integer userid = teaUserService.getUserid(username);

        TeaUser teaUser = teaUserService.teaLogin(username, userpwd);
        if (teaUser==null){
            request.getSession().setAttribute("msg","登陆密码错误");
            return "redirect:/toTeacher";
        }else {
            String teaTruename = teaUserService.getTeaTruename(username);
            request.getSession().setAttribute("Teatruename",teaTruename);
            request.getSession().setAttribute("TeaClassid",classId);
            request.getSession().setAttribute("Teauserid",userid);
            return "teacher/manage";
        }
    }

}
