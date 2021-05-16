package com.github.kayjamlang.io.stream;


import com.github.kayjamlang.executor.Executor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputTests {
    public static Executor executor = new Executor();

    @BeforeAll
    public static void init() throws Exception {
        executor.addLibrary(new IOStreamLibrary());
    }

    @Test
    public void outputCreate() throws Exception {
        assertEquals(OutputStreamClass.class, executor.execute("return Output()").getClass());
    }

    @Test
    public void write() throws Exception {
        Object response = executor.execute("var output = Output();" +
                "output.write(74);" +
                "return output.toString()");
        assertEquals(String.class, response.getClass());
        assertEquals("J", response);
    }
}
