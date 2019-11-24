package com.test;

import com.dao.*;
import com.pojo.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @ClassName TestSaveProcess
 * @Version 1.0
 * @Author Rose
 * @Date 2019/11/15 17:00
 * @Description TODO
 * Modification User： 程序修改时由修改人员编写
 * Modification Date： 程序修改时间
 */
public class TestSaveProcess {
    public  UseProcessDao useProcessDao;
    public SqlSession sqlSession;
    @Test
    public void TestSaveProcess() throws IOException {
        String resource="mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        sqlSession = sqlSessionFactory.openSession();
       /* useProcessDao=sqlSession.getMapper(UseProcessDao.class);
        Map map=new HashMap();
        map.put("XM","何政梁");
        map.put("SF",null);
        useProcessDao.getSf(map);
        sqlSession.commit();
        System.out.println(" 姓名  省份");
        System.out.println("何政梁  "+map.get("SF"));
        sqlSession.close();*/
        UserCodeDao userCodeDao=sqlSession.getMapper(UserCodeDao.class);
        int flag;
        List<Permission> list;
        list=userCodeDao.FindPermissionByUsername("123");
        sqlSession.commit();
        System.out.print(list);

    }
}