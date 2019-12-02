package com.dao;
import com.pojo.Paper;

import java.util.List;

/**
 * @author msi
 * @title: PaperDao
 * @projectName ssm
 * @description: TODO
 * @date 2019/9/517:04
 */
public interface PaperDao {
    /**
     * 添加一个paper
     * @param paper
     * @return 成功与否
     */
    int addPaper(Paper paper);

    /**
     * 根据id删除一个id
     * @param id
     * @return 成功与否
     */
    int deletePaperById(long id);

    /**
     * 修改一个Paper
     * @param paper
     * @return 是否成功
     */
    int updatePaper(Paper paper);

    /**
     * 根据id查询一个paper
     * @param id
     * @return 查询的结果
     */
    Paper queryById(long id);


    /**
     * asdf
     * @return  asdf
     */
    List<Paper> queryAllPaper();

}
