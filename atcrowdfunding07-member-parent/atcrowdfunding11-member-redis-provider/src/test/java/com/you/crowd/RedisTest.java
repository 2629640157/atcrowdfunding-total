package com.you.crowd;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    // private Logger logger = LoggerFactory.getLogger(RedisTest.class); @Autowired
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testSet() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

//        operations.set("apple1","yellow"); //设置k-v
//        operations.set("apple","xiaoming",1);  //设置指定区间范围内的值
        /*
        第三个参数： 数值大小
        第四个参数： 时间单位
        */
        operations.set("age", "18", 10, TimeUnit.SECONDS);
    }

}