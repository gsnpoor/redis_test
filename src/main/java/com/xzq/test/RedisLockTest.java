package com.xzq.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author xiaozq
 * @Date 2022/11/7 16:30
 * <p>@Description:</p>
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisLockTest {

    @Autowired
    private RedissonClient redisson;

    @Test
    public void lockPrac(){

        RLock lock = redisson.getLock("anyLock");
        // 加锁
        lock.lock();
        try {
            System.out.println("业务进行中");
        }finally {
             lock.unlock();
        }

    }



}
