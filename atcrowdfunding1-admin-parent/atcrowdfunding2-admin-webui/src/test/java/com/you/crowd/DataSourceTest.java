package com.you.crowd;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.you.crowd.entity.Admin;
import com.you.crowd.entity.AdminExample;
import com.you.crowd.mapper.AdminMapper;
import com.you.crowd.service.aop.AdminService;
import com.you.crowd.service.impl.AdminServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 游斌
 * @create 2020-06-30  20:23
 */
//创建 Spring 的 Junit 测试类
// 指定 Spring 给 Junit 提供的运行器类
@RunWith(SpringJUnit4ClassRunner.class)
// 加载 Spring 配置文件的注解
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class DataSourceTest {
    @Autowired
    private DataSource dataSource;
    //    这里必须写接口（由于JDK动态代理所代理的目标必须是接口，而这里却是一个普通类，所以就抛出此异常。BeanNotOfRequiredTypeException: Bean named ‘loginLogService’ is expected to be of type ‘com.qst.service.LoginLogServiceImpl’ but was actually of type ‘com.sun.proxy.$Proxy28’）
    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminMapper adminMapper;

    /*使用日志
        日志门面：SLF4J
        日志实现：Logback
        需要对日志进行统一，统一项目中使用日志的技术。
        */
//测试数据源是否能拿到
    @Test
    public void getConnection() throws SQLException {
        System.out.println("dataSource.getConnection() = " + dataSource.getConnection());
    }

    //测试Spring整合Mybatis是否成功
    @Test
    public void selectAllTest() {
        List<Admin> allAdmins = adminService.getAllAdmins();
        System.out.println(allAdmins.get(0));
    }

    //使用使用SLF4J统一日志打印
    @Test
    public void loggingTest() {
//        打印自己的日志
//        1.获取日志打印器
        Logger logger = LoggerFactory.getLogger(this.getClass());
//        2.通过logger打印日志
//        日志输出级别，可以指定日志输出的级别，指定之后，在指定级别一下的日志不会打印
//        默认输出日志级别是debug

        logger.info("自己的日志");
        logger.warn("警告日志");
        logger.error("错误日志");
    }

    //测试事务能否正常使用
    @Test
    public void updateAdminTest() {
        Integer i = adminService.updateAdminById(new Admin(1, "zhangsan1", "1234567", "李四", "zhangsan@qq.com", null));
        System.out.println("改变的行数：" + i);
    }

    //    插入多条数据
    @Test
    public void addAdmins() throws SQLException {

        Connection connection = dataSource.getConnection();
        String sql = "INSERT INTO t_admin (login_acct,user_pswd,user_name,email) VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < 1000; i++) {
            statement.setObject(1, "logAcct" + i);
            statement.setObject(2, "E10ADC3949BA59ABBE56E057F20F883E");
            statement.setObject(3, "userName" + i);
            statement.setObject(4, "userName" + i + "@aliyun.com");
            statement.executeUpdate();
        }
        statement.close();
        connection.close();
    }


    @Test
    public void testPageHelper() {
//        在查询之前调用这个方法，以后的查询会自带分页 pageNum：当前页 pageSize：页面大小
        PageHelper.startPage(3, 10);

        List<Admin> admins = adminMapper.selectByExample(null);
        for (Admin admin : admins) {
            System.out.println(admin);
        }
//       PageInfo： 可以将查询到的集合封装到PageInfo中，这样PageInfo就保存了很多的信息
        PageInfo<Admin> adminPageInfo = new PageInfo<Admin>(admins);
        System.out.println("下一页 " + adminPageInfo.getNextPage());
        System.out.println("上一页 " + adminPageInfo.getPrePage());
        System.out.println("导航页首页" + adminPageInfo.getNavigateFirstPage());
        System.out.println("导航页尾页" + adminPageInfo.getNavigateLastPage());
//        System.out.println("adminPageInfo.getNextPage() = " + );
        System.out.println("导航页尾页" + adminPageInfo.getNavigatePages());
        System.out.println("该页首条是第" + adminPageInfo.getStartRow() + "条");
//        返回导航页显示页码
        int[] nums = adminPageInfo.getNavigatepageNums();
        for (int num : nums) {
            System.out.println("num = " + num);
        }
        System.out.println("是否有上一页" + adminPageInfo.isHasPreviousPage());
        System.out.println("是否有下一页" + adminPageInfo.isHasNextPage());
        System.out.println("当前页" + adminPageInfo.getPageNum());
        int size = adminPageInfo.getSize();
        System.out.println("数据的多少条 " + size);

    }


}
