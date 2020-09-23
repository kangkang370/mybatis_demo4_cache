package com.bupt.test;

import com.bupt.dao.IUserDao;
import com.bupt.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class UserTest {

    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession sqlSession;
    private IUserDao userDao;

    @Before //用于在测试方法执行之前执行
    public void init() throws Exception {
        // 1.读取配置文件，生成字节输入流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 2.获取SqlSessionFactory
        factory = new SqlSessionFactoryBuilder().build(in);
        // 3.使用工厂对象，创建SqlSession对象
        sqlSession = factory.openSession(true);
        // 4.获取代理对象
        userDao = sqlSession.getMapper(IUserDao.class);

    }

    @After // 用于在测试方法执行之后执行
    public void destroy() throws Exception {
        // 6.提交事务
//        sqlSession.commit();
        // 6.释放资源
        sqlSession.close();
        in.close();
    }

    /**
     * 测试查询所有
     */
    @Test
    public void testFirstLevelCache(){
        User user1 = userDao.findById(41);
        System.out.println(user1);
        /*// 释放sqlsession资源
        sqlSession.close();
        // 再次获取sqlsession对象
        SqlSession sqlSession = factory.openSession(true);*/

        sqlSession.clearCache();// 此方法也可以清空缓存

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        User user2 = userDao.findById(41);
        System.out.println(user1);

        System.out.println(user1 == user2);

    }

    /**
     * 测试缓存的同步
     */
    @Test
    public void testClearCache(){
        // 1.根据id查询用户
        User user1 = userDao.findById(41);
        System.out.println(user1);
        // 2.更新用户信息
        user1.setUsername("update2222 user clear cache");
        user1.setAddress("北京市海淀区");
        userDao.updateUser(user1);// update操作会清空一级缓存
        // 3.再次查询id为41的用户
        User user2 = userDao.findById(41);
        System.out.println(user1);

        System.out.println(user1 == user2);

    }


}
