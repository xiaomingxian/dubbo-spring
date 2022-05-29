package com.xiaoze.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xiaoze.api.service.DemoService;
import lombok.extern.slf4j.Slf4j;

/**
 * DemoServiceImpl
 * 服务提供类
 * @author xiaoze
 * @date 2018/6/7
 */
@Slf4j
@Service(version = "${demo.service.version}")
public class DemoServiceImpl implements DemoService {


    @Override
    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }


    @HystrixCommand
    @Override
    public String sayHelloException(String name) {
        if (Math.random()<1){
            log.info("模拟报错。。。");
            throw  new RuntimeException("报错啦");
        }
        return "正确结果："+name;
    }
}
