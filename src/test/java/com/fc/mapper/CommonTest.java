package com.fc.mapper;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

/**
 * Created by lidp on 2019/5/30.
 */
public class CommonTest {
    @Test
    public  void main() throws InterruptedException {
        RateLimiter limiter = RateLimiter.create(10);// 代码1
        Thread.currentThread().sleep(1000);//步骤1
        if (limiter.tryAcquire(20))//代码2
            System.out.println("======== Time1:" + System.currentTimeMillis() / 1000);

        Thread.currentThread().sleep(1001);
        if (limiter.tryAcquire(1))//代码3
            System.out.println("======== Time2:" + System.currentTimeMillis() / 1000);

        if (limiter.tryAcquire(5))
            System.out.println("======== Time3:" + System.currentTimeMillis() / 1000);

    }
}
