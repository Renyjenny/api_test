package com.reny.test;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description: 测试类
 * @Author: reny
 * @CreateTime: 2020/10/20 11:45
 */
public class DemoTest {

    private static Properties properties;



    @BeforeTest
    public void beforeTest() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("db.properties");
        properties = new Properties();
        properties.load(inputStream);

        System.out.println("111");

    }


    @Test
    public void test1() {
        System.out.println("111");
    }


}
