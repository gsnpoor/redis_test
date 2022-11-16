package com.xzq.test;

import com.xzq.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.Set;

/**
 * @Author xiaozq
 * @Date 2022/11/7 14:19
 * <p>@Description:</p>
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public  void  stringAdd(){
       if(redisUtil.set("fast","测试失效",30)){
           System.out.println("操作成功");
       }else{
           System.out.println("操作失败");
       }
    }

    /**
     * 判断键是否失效（失效则不存在）
     */
    @Test
    public  void  stringIsExist(){
        if(redisUtil.hasKey("fast")){
            System.out.println("存在");
        }else{
            System.out.println("已失效、不存在");
        }
    }

    @Test
    public  void  stringExpireTime() {
        long expireTime= redisUtil.getExpire("fast");
        if(expireTime<-1){
            System.out.println("键已过期");
        }else{
            System.out.println("过期时间："+expireTime);
        }
    }

    @Test
    public  void  mapAdd() {
        redisUtil.hset("topic","port","8080");
        redisUtil.hset("topic","host","172.169.20.120");
    }

    @Test
    public  void  mapGet() {
        // 获取一个
       String port = (String)redisUtil.hget("topic", "port");
       System.out.println("端口号："+port);

       // 获取所有
        Map<Object, Object> topic = redisUtil.hmget("topic");
        Set<Map.Entry<Object, Object>> entries = topic.entrySet();
        for(Map.Entry<Object, Object> entry:entries){
            System.out.println(entry.getKey()+"---"+entry.getValue());
        }
    }
    









}
