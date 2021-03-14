package com.you.crowd;

import com.you.crowd.entity.po.MemberPO;
import com.you.crowd.mapper.MemberPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 游斌
 * @create 2020-07-25  21:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {
    @Autowired
    private DataSource dataSource;
    @Resource
    private MemberPOMapper MemberPOMapper;
    private Logger logger = LoggerFactory.getLogger(MybatisTest.class);

    @Test
    public void testMapper() {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String source = "123123";
        String encode = passwordEncoder.encode(source);

        MemberPO memberPO = new MemberPO(null, "jack", encode, " 杰 克 ", "jack@qq.com", 1, 1, "杰克", "123123", 2);

        MemberPOMapper.insert(memberPO);
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println("connection = " + connection);
    }
}