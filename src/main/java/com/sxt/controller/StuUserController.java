package com.sxt.controller;

import com.sxt.pojo.PClass;
import com.sxt.pojo.Users;
import com.sxt.service.PClassService;
import com.sxt.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class StuUserController {
    @Autowired
    UsersService usersService;
    @Autowired
    PClassService pClassService;

    @GetMapping("/register")
    public String toRegister(Model model){
        List<PClass> allPClass = pClassService.getAllPClass();
        model.addAttribute("list",allPClass);
        return "student/register";
    }

    @PostMapping("/CanRegister")
    public String register(Users users,String username,String  userpwd,String truename,Integer classid,HttpServletRequest request){
        Users byName = usersService.getByName(username);
        users.setUsername(username);
        users.setUserpwd(userpwd);
        users.setTruename(truename);
        users.setClassid(classid);
        usersService.addUsers(users);
        if (byName==null){
            if (userpwd.equals("")){
                request.getSession().setAttribute("msg1","密码为空");
                return "redirect:/register";
            }
            if (truename.equals("")){
                request.getSession().setAttribute("msg2","真实姓名为空");
                return "redirect:/register";
            }
            if (username.equals("")){
                request.getSession().setAttribute("msg3","用户名为空");
                return "redirect:/register";
            }
            System.out.println("用户可以注册");
            return "redirect:/toStuLogin";
        }else {
            System.out.println("用户已存在");
            return "redirect:/register";
        }

    }
    //修改密码
    @PostMapping("/xiugai")
    public String updateUserPwd(String userpwd,Integer userid){
        Integer i = usersService.updUserPwd(userpwd, userid);
        if (i==0){
            return "";
        }else {
            return "redirect:/toStuLogin";
        }

    }
    //退出登陆
    @GetMapping("/logout")
    public String slogout(HttpServletRequest request){
        request.getSession().removeAttribute("lis");
        request.getSession().removeAttribute("truename");
        return "redirect:/toStuLogin";
    }
}
