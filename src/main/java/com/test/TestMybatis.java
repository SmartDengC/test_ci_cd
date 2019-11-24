package com.test;

import com.dao.BasicConfigDao;
import com.dao.DataImportDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Rose
 */
public class TestMybatis {
    public BasicConfigDao basicConfigDao;
    public SqlSessionFactory sqlSessionFactory;
    public DataImportDao dataImportDao;
    @Before
    public void setBasicConfigDao() throws IOException {
        String resouce="mybatis-config.xml";
        InputStream inputStream= Resources.getResourceAsStream(resouce);
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testinsert(){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        dataImportDao=sqlSession.getMapper(DataImportDao.class);
        List list;
        list=dataImportDao.requestTD_SFDM();
        System.out.print(list);
    }

}
