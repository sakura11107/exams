package com.sxt.service;

import com.sxt.pojo.Users;

import java.util.List;


public interface UsersService {
    Users login(String username, String userpwd);

    Integer addUsers(Users users);

    Users getByName(String username);

    List<Users> pageByClassid(Integer classid);

    Integer updateUsers(Users users);

    Users getByUserid(Integer userid);

    Integer delUserid(Integer userid);

    Integer delClassid(Integer classid);

    Integer updUserPwd(String userpwd,Integer userid);

    Integer getByNameid(String username);

    String getTrueName(String username);

    Integer getByClass(String username);
}
