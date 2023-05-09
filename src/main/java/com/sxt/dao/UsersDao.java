package com.sxt.dao;

import com.sxt.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UsersDao {
    Users login(String username, String userpwd);

    Integer addUsers(Users users);

    Users getByName(String username);

    List<Users> pageById(Integer classid);

    Integer updateUsers(Users users);

    Users getByUserid(Integer userid);

    Integer delUserid(Integer userid);

    Integer delClassid(Integer classid);

    //修改密码
    Integer updUserPwd(String userpwd,Integer userid);
    //查询用户id
    Integer getByNameId(String username);
    //查询用户的真是姓名
    String getTrueName(String username);
    //查询班级id
    Integer getByClass(String username);

    //查询所有学生id
    List<Users> getUserid(Integer classid);

}
