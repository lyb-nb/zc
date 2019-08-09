package cn.lyb.zc.test;

import cn.lyb.zc.entity.Admin;
import cn.lyb.zc.mapper.AdminMapper;
import cn.lyb.zc.service.AdminService;
import cn.lyb.zc.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * @author lyb
 * @since 2019/8/7 22:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:spring/spring-dao.xml"})
@Slf4j
public class ConnectionTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testMybatis() {
        List<Admin> adminList = adminService.list();
        adminList.forEach(System.out::println);
    }

    @Test
    public void testTx() {
        adminService.updateAdmin();
    }

    @Test
    public void testBatchSave() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            String randomStr = UUID.randomUUID().toString().substring(0, 5);
            if (i % 2 == 0) {
                adminMapper.insert(new Admin(
                        null, randomStr, randomStr + i, randomStr, randomStr + "@sina.com", null
                ));
            } else {
                adminMapper.insert(new Admin(
                        null, randomStr + i, randomStr, randomStr + i, randomStr + "@gmail.com", null
                ));
            }

        }
        long end = System.currentTimeMillis();
        log.info("总共耗时: {}", (end - start));
    }


}
