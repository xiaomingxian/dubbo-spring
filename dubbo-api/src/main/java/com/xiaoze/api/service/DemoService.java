package com.xiaoze.api.service;

/**
 * DemoService
 * 服务Api接口类
 * @author xiaoze
 * @date 2018/6/6
 */
public interface DemoService {

    String sayHello(String name);

    /**
     * 容错
     * @param name
     * @return
     */
    String sayHelloException(String name);

}
