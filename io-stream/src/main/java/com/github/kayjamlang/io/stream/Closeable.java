package com.github.kayjamlang.io.stream;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.executor.Void;
import com.github.kayjamlang.executor.libs.Library;

public class Closeable extends Library.LibClass {
    public final static String CLASS_NAME = "Closeable";
    public Closeable() throws Exception {
        super(CLASS_NAME, null);
        addFunction(new Library.LibFunction("close", Type.VOID, (mainContext, context) ->
                Void.INSTANCE));
    }
}
