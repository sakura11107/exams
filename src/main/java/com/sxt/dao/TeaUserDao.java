package com.sxt.dao;

import com.sxt.pojo.TeaUser;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TeaUserDao {
    TeaUser teaLogin(String username,String userpwd);
   //获取教师姓名
   String getTeaTruename(String username);
   //获取班级id
    Integer getClassId(String username);
    //教师id获取
    Integer getUserid(String username);
}
