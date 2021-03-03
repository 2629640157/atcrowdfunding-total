package com.you.crowd;

import com.you.crowd.entity.Role;
import com.you.crowd.mapper.AdminMapper;
import com.you.crowd.mapper.RoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 游斌
 * @create 2020-07-05  9:38
 */

// 指定 Spring 给 Junit 提供的运行器类
@RunWith(SpringJUnit4ClassRunner.class)
// 加载 Spring 配置文件的注解
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class RoleMapperTest {
    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void insertRoles() {
      /*  for (int i = 0; i <400 ; i++) {
            roleMapper.insert(new Role(null,"Role"+i));
        }*/
    }
}
