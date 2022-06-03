package com.xiaoze.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.schema.DubboBeanDefinitionParser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xiaoze.api.service.DemoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DemoConsumerController
 * 消费者控制层
 * @author xiaoze
 * @date 2018/6/7
 */
@RestController
public class DemoConsumerController {

    @Reference(url = "localhost:20881",version = "${demo.service.version}")
    private DemoService demoService;

    @RequestMapping("/sayHello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return demoService.sayHello(name);
    }


    @RequestMapping("/sayHelloExc/{name}")
    @HystrixCommand(fallbackMethod = "sayHelloException")
    public String sayHelloExc(@PathVariable("name") String name) {
        return demoService.sayHello(name);
    }
    public String sayHelloException(String name ){
        //hystrix 异常回调方法的参数列表得与原方法保持一致？
        return "HystrixCommand容错方案 调用异常 默认返回";
    }

}
