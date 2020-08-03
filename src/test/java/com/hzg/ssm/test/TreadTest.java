package com.hzg.ssm.test;

import org.junit.Test;

//1.定义继承线程类
class MyThread extends Thread{
    //2.覆写 run 方法
    //在这里编写当前线程需要执行的代码
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("看电影"+i);
        }
    }
}