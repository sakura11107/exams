package com.sxt.service.impl;

import com.sxt.dao.PClassDao;
import com.sxt.pojo.PClass;
import com.sxt.service.PClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PClassServiceIpml implements PClassService {
    @Autowired
    PClassDao pClassDao;
    @Override
    public List<PClass> getAllPClass() {
        return pClassDao.getAllClass();
    }

    @Override
    public PClass getClassid(Integer classid) {
        return pClassDao.GetClassId(classid);
    }
}
