package com.sxt.service;

import com.sxt.pojo.PClass;

import java.util.List;


public interface PClassService {
    List<PClass> getAllPClass();

    PClass getClassid(Integer classid);
}
