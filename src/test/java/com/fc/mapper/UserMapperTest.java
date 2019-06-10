package com.fc.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml","classpath:spring-mvc.xml"})
public class UserMapperTest {
    @Autowired
    private Jedis jedis;

    @Test
    public void tset0(){
//        Long a =jedis.del("post_prefix1","post_prefix2","post_prefix3","post_prefix4");
//        Long b =jedis.del("post_prefix*");
        Object  c = jedis.eval("redis-cli keys \"map_info_*\" | xargs redis-cli del");
    }
}
