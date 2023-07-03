package com.github.marchocode;

import net.sf.cglib.proxy.Enhancer;

public class CgLibMain {

    public static void main(String[] args) {

        AliyunSmsService service = (AliyunSmsService) CgLibMain.getProxy(AliyunSmsService.class);
        service.sendMessage();
    }

    public static Object getProxy(Class<?> clas) {
        Enhancer e = new Enhancer();

        e.setClassLoader(clas.getClassLoader());
        e.setSuperclass(clas);
        e.setCallback(new AliyunSmsHandler());

        return e.create();
    }

}
