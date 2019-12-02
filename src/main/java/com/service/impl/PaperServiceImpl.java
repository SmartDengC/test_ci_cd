package com.service.impl;/**
 * @title: PaperServiceImpl
 * @projectName ssm
 * @description: TODO
 * @author msi
 * @date 2019/9/512:24
 */

import com.dao.PaperDao;
import com.pojo.Paper;
import com.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: ssm
 *
 * @description: test
 *
 * @author: Mr.Wang
 *
 * @create: 2019-09-05 12:24
 **/
@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperDao paperDao;
    @Override
    public int addPaper(Paper paper) {
        return paperDao.addPaper(paper);
    }

    @Override
    public int deletePaperById(long id) {
        return paperDao.deletePaperById(id);
    }

    @Override
    public int updatePaper(Paper paper) {
        return paperDao.updatePaper(paper);
    }

    @Override
    public Paper queryById(long id) {
        return paperDao.queryById(id);
    }

    @Override
    public List<Paper> queryAllPaper() {
        return paperDao.queryAllPaper();
    }
}
