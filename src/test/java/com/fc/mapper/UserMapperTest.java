package com.fc.mapper;

import com.fc.util.MyConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import javax.mail.internet.MimeMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml","classpath:spring-mvc.xml"})
public class UserMapperTest {
    @Autowired
    private Jedis jedis;

    @Test
    public void tset0(){

        for (int i = 0; i < 50; i++) {
            Long b =jedis.del("post_prefix"+i);
        }
    }
    @Autowired
    private JavaMailSender javaMailSender;
    @Test
    public void testMail(){
        javaMailSender.send(new MimeMessagePreparator(){

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                System.out.println("开始发邮件...");
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
                mimeMessageHelper.setFrom("13153771737@163.com");
                mimeMessageHelper.setTo("821888642@qq.com");
                mimeMessageHelper.setSubject("一封激活邮件");
                StringBuilder sb  = new StringBuilder();
                sb.append("<html><head></head><body>");
                mimeMessageHelper.setText(sb.toString(),true);

                System.out.println("结束发邮件...");
            }
        });
    }
}
