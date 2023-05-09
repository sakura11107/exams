package com.sxt.service;

import com.sxt.pojo.TeaUser;


public interface TeaUserService {
    TeaUser teaLogin(String username,String userpwd);

    String getTeaTruename(String username);

    Integer getClassId(String username);

    Integer getUserid(String username);
}
