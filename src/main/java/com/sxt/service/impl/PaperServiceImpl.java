package com.sxt.service.impl;

import com.sxt.dao.PaperDao;
import com.sxt.pojo.Paper;
import com.sxt.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    PaperDao paperDao;
    @Override
    public Integer addPaper(Paper paper) {
        return paperDao.addPaper(paper);
    }

    @Override
    public List<Paper> getByEid(Integer eid) {
        return paperDao.getByEid(eid);
    }
}
