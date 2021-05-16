package com.github.kayjamlang.io.stream;


import com.github.kayjamlang.executor.Executor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputTests {
    public static Executor executor = new Executor();

    @BeforeAll
    public static void init() throws Exception {
        executor.addLibrary(new IOStreamLibrary());
    }

    @Test
    public void inputCreate() throws Exception {
        assertEquals(InputStreamClass.class, executor.execute("return Input(\"test\")").getClass());
    }

    @Test
    public void read() throws Exception {
        Object response = executor.execute("return Input(\"test\").read()");
        assertEquals(Integer.class, response.getClass());
        assertEquals(0x74, response);
    }

    @Test
    public void available() throws Exception {
        Object response = executor.execute("return Input(\"test\").available()");
        assertEquals(Integer.class, response.getClass());
        assertEquals(4, response);
    }
}
