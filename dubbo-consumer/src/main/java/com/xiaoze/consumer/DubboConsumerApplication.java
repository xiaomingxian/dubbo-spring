package com.xiaoze.consumer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DubboConsumerApplication
 * 消费者启动类
 * @author xiaoze
 * @date 2018/6/7
 */
@EnableDubbo
@SpringBootApplication
@Slf4j
public class DubboConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class, args);
        log.info("***********************消费者启动成功*************************");
    }
}
