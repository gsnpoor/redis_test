package com.xzq.controller;

import com.xzq.util.RedisUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author xiaozq
 * @Date 2022/11/8 10:11
 * <p>@Description:</p>
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    public static final String COMMON = "key:";

    public  static final String STOCK = "stock";

    public static final String  DUCESTOCK = "duceStock";

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedissonClient redission;

    /**
     * 设置库存
     * @return
     */
    @PostMapping("/setStock")
    public Map setStock(){
        redisUtil.set(STOCK,"20");
       Map map= new HashMap<>();
       map.put("code",200);
       map.put("mes","设置商品库存成功");
        return map;
    }

    /**
     * 模拟商品超卖
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/deductstock")
    public Map deductStock() throws InterruptedException {

        RLock lock = redission.getLock(COMMON + DUCESTOCK);

        //尝试获取锁20s,获取失败则返回
        boolean  requireLock = lock.tryLock(20, TimeUnit.SECONDS);
        Map  map = new HashMap<>();
        if(!requireLock){
            map.put("code",300);
            map.put("mes","获锁超时");
            return  map;
        }

        try {
            // 获取redis库中的商品库存数量
            Integer stock= Integer.parseInt(String.valueOf(redisUtil.get(STOCK)));
            if(stock>0){
                stock =  stock-1;
                redisUtil.set(STOCK,stock);
                System.out.println("商品扣减成功，剩余商品："+stock);
            }else{
                System.out.println("库存不足 剩余商品:"+stock);
            }

            map.put("code",200);
            map.put("mes","扣去库存成功,剩余库存数 stock="+stock);
            return  map;

        }catch (Exception e){
            map.put("code",500);
            map.put("mes","程序出错");
            e.printStackTrace();
            return map;
        }finally {
            // 释放锁
            lock.unlock();
        }

    }

}
