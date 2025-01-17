package com.miaoqi.mybatis.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.miaoqi.mybatis.bean.Employee;
import com.miaoqi.mybatis.bean.EmployeeExample;
import com.miaoqi.mybatis.bean.EmployeeExample.Criteria;
import com.miaoqi.mybatis.dao.EmployeeMapper;

/**
 * @author miaoqi
 * @date 2017-11-25 下午6:04
 **/
public class MyBatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testMbg() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("mbg.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    //    @Test
    //    public void testMyBatis3Simple() throws Exception {
    //        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    //        SqlSession openSession = sqlSessionFactory.openSession();
    //        try {
    //            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
    //            List<Employee> emps = mapper.selectAll();
    //            for (Employee employee : emps) {
    //                System.out.println(employee.getId());
    //            }
    //        } finally {
    //            openSession.close();
    //        }
    //    }

    @Test
    public void testMyBatis3() throws Exception {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            // xxxExample就是封装查询条件的
            // 1. 查询所有
            List<Employee> emps = mapper.selectByExample(null);
            // 2. 查询员工姓名中带有e字母的, 和员工性别是1的
            // 封装查询条件的example
            EmployeeExample example = new EmployeeExample();
            // 创建一个Criteria, 这个Criteria就是拼装查询条件的
            Criteria criteria1 = example.createCriteria();
            criteria1.andLastNameLike("%e%");
            criteria1.andGenderEqualTo("1");

            // criteria1和criteria2是或的关系
            Criteria criteria2 = example.createCriteria();
            criteria2.andEmailLike("%e%");
            example.or(criteria2);

            emps = mapper.selectByExample(example);
            for (Employee employee : emps) {
                System.out.println(employee.getId());
            }
        } finally {
            openSession.close();
        }
    }

}
