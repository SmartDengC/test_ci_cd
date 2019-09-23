package com.service;

import com.pojo.Paper;

import java.util.List;

/**
 * @author msi
 * @title: PaperService
 * @projectName ssm
 * @description: TODO
 * @date 2019/9/512:22
 */
public interface PaperService {
    /**
     * 添加一个paper
     * @param paper
     * @return 添加结果
     */
    int addPaper(Paper paper);

    /**
     * 根据id删除一个paper
     * @param id
     * @return 返回删除的结果
     */
    int deletePaperById(long id);

    /**
     * 修改一个paper
     * @param paper
     * @return 修改的结果
     */
    int updatePaper(Paper paper);

    /**
     * 根据id查询paper
     * @param id
     * @return 返回查询的结果
     */
    Paper queryById(long id);

    /**
     * 查询所有的id
     * @return 查询的结果
     */
    List<Paper> queryAllPaper();
}
