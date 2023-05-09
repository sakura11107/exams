package com.sxt.service.impl;

import com.sxt.dao.StudentExamDao;
import com.sxt.dao.UsersDao;
import com.sxt.pojo.Users;
import com.sxt.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersDao usersDao;
    @Autowired
    StudentExamDao studentExamDao;
    @Override
    public Users login(String username, String userpwd) {
        Users login = usersDao.login(username, userpwd);
        return login;
    }

    @Override
    public Integer addUsers(Users users) {
        return usersDao.addUsers(users);
    }

    @Override
    public Users getByName(String username) {
        return usersDao.getByName(username);
    }

    @Override
    public List<Users> pageByClassid(Integer classid) {
        return usersDao.pageById(classid);
    }

    @Override
    public Integer updateUsers(Users users) {
        return usersDao.updateUsers(users);
    }

    @Override
    public Users getByUserid(Integer userid) {
        return usersDao.getByUserid(userid);
    }

    @Override
    public Integer delUserid(Integer userid) {
        studentExamDao.deleteByUser(userid);
        return usersDao.delUserid(userid);
    }

    @Override
    public Integer delClassid(Integer classid) {
        List<Users> userid = usersDao.getUserid(classid);
        for (Users users : userid) {
            Integer userid1 = users.getUserid();
            studentExamDao.deleteByUser(userid1);
        }
        return usersDao.delClassid(classid);
    }

    @Override
    public Integer updUserPwd(String userpwd, Integer userid) {
        return usersDao.updUserPwd(userpwd,userid);
    }

    @Override
    public Integer getByNameid(String username) {
        return usersDao.getByNameId(username);
    }

    @Override
    public String getTrueName(String username) {
        return usersDao.getTrueName(username);
    }

    @Override
    public Integer getByClass(String username) {
        return usersDao.getByClass(username);
    }


}
