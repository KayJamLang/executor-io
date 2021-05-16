package com.github.kayjamlang.io.stream;

import com.github.kayjamlang.executor.libs.Library;

public class IOStreamLibrary extends Library {

    public IOStreamLibrary() throws Exception {
        classes.put(Closeable.CLASS_NAME, new Closeable());
        classes.put(InputStreamClass.CLASS_NAME, new InputStreamClass());
        classes.put(OutputStreamClass.CLASS_NAME, new OutputStreamClass());
    }
}
