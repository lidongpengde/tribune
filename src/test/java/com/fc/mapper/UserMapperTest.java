package com.fc.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml","classpath:spring-mvc.xml"})
public class UserMapperTest {
    @Autowired
    private Jedis jedis;

    @Test
    public void tset0(){
        jedis.del("post_prefix1");
    }
}
