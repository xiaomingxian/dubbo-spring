package com.xiaoze.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.config.spring.schema.DubboBeanDefinitionParser;
import com.alibaba.dubbo.registry.integration.RegistryProtocol;
import com.alibaba.dubbo.registry.support.ProviderConsumerRegTable;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * DubboProviderApplication
 * 服务提供启动类
 * @author xiaoze
 * @date 2018/6/7
 */
@EnableDubbo
@SpringBootApplication
@Slf4j
@EnableHystrix
public class DubboProviderApplication {

//    DubboBeanDefinitionParser
//    ProviderConsumerRegTable
//    FactoryBean
//    RegistryProtocol
//    DubboProtocol

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
        log.info("********************启动成功*************************");
    }
}
