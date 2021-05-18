package com.github.kayjamlang.io.print;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.executor.Void;
import com.github.kayjamlang.executor.libs.Library;

public class PrintLibrary extends Library {

    public PrintLibrary() throws Exception {
        classes.put(PrintStreamClass.CLASS_NAME, new PrintStreamClass());
        functions.add(new LibFunction("print", Type.VOID, (mainContext, context) -> {
            System.out.print(PrintStreamClass.toString(mainContext.executor, context.getVariable("value")));
            return Void.INSTANCE;
        }, new Argument(Type.ANY, "value")));

        functions.add(new LibFunction("println", Type.VOID, (mainContext, context) -> {
            System.out.println(PrintStreamClass.toString(mainContext.executor, context.getVariable("value")));
            return Void.INSTANCE;
        }, new Argument(Type.ANY, "value")));

        functions.add(new LibFunction("println", Type.VOID, (mainContext, context) -> {
            System.out.println();
            return Void.INSTANCE;
        }));
    }

    public void initOut() {

    }
}
