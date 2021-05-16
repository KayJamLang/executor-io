package com.github.kayjamlang.io.file;

import com.github.kayjamlang.executor.libs.Library;

public class FileLibrary extends Library {

    public FileLibrary() throws Exception {
        classes.put(FileClass.CLASS_NAME, new FileClass());
    }
}
