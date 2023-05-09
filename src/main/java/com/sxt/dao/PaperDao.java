package com.sxt.dao;

import com.sxt.pojo.Paper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface PaperDao {
    Integer addPaper(Paper paper);
    //根据考试信息id查询试卷
    List<Paper> getByEid(Integer eid);
    //删除试卷信息----eid
    Integer deleteByEid(Integer eid);
}
