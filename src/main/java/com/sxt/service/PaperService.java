package com.sxt.service;

import com.sxt.pojo.Paper;

import java.util.List;


public interface PaperService {
    Integer addPaper(Paper paper);
    List<Paper> getByEid(Integer eid);
}
