package com.github.marchocode;

import java.lang.reflect.Proxy;

public class MybatisMain {

    public static void main(String[] args) {

        Mapper mapper = (Mapper) Proxy.newProxyInstance(Mapper.class.getClassLoader(),new Class[]{Mapper.class},new MapperHandler());

        mapper.select();

    }

}
