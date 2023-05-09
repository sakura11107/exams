package com.sxt.dao;

import com.sxt.pojo.PClass;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.ManagedBean;
import java.util.List;


@Mapper
public interface PClassDao {
    //查询班级列表
    List<PClass> getAllClass();
    //查询班级id
    PClass GetClassId(Integer classid);
}
