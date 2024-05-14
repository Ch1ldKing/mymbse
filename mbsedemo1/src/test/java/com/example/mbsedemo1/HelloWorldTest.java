package com.example.mbsedemo1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HelloWorldTest {

    @Test
    public void testHelloWorld() {
        // 调用你要测试的方法，这里是一个简单的打印语句
        String hello = helloWorld();

        // 校验方法的输出是否符合预期
        assertEquals("Hello World", hello, "方法应该返回'Hello World'");
    }

    public String helloWorld() {
        System.out.println("Hello World");
        return "Hello World";
    }
}
