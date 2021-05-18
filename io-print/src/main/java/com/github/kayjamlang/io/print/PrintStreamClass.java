package com.github.kayjamlang.io.print;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.containers.ClassContainer;
import com.github.kayjamlang.core.expressions.CallOrCreateExpression;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.executor.ClassUtils;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.Void;
import com.github.kayjamlang.executor.exceptions.KayJamNotFoundException;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.libs.Library;
import com.github.kayjamlang.io.stream.OutputStreamClass;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collections;

public class PrintStreamClass extends Library.LibClass {
    public static final String CLASS_NAME = "PrintStream";
    public static final String PRINT_STREAM_FIELD = OutputStreamClass.OUTPUT_STREAM_FIELD;

    public static PrintStreamClass create(Executor executor, OutputStream stream) throws Exception {
        return ClassUtils.newInstance(executor, new PrintStreamClass(),
                ClassUtils.findConstructor(executor.mainContext,
                        Collections.singletonList(Type.ANY), new PrintStreamClass()),
                Collections.singletonList(stream));
    }

    public PrintStreamClass() throws Exception {
        super(CLASS_NAME, null);

        addConstructor(new Library.LibConstructor((mainContext, context) -> {
            boolean autoFlush = context.getVariable("autoFlush");
            OutputStreamClass outputStreamClass = context.getVariable("output");
            String charset = context.getVariable("charset");

            context.parentContext.addVariable(PRINT_STREAM_FIELD, new PrintStream(
                    outputStreamClass.getOutputStream(), autoFlush, charset));

            return Void.INSTANCE;
        }, new Argument(Type.BOOLEAN, "autoFlush"),
                new Argument(Type.of(OutputStreamClass.CLASS_NAME), "output"),
                new Argument(Type.STRING, "charset")));

        addConstructor(new Library.LibConstructor((mainContext, context) -> {
            boolean autoFlush = context.getVariable("autoFlush");
            OutputStreamClass outputStreamClass = context.getVariable("output");
            context.parentContext.addVariable(PRINT_STREAM_FIELD, new PrintStream(
                    outputStreamClass.getOutputStream(), autoFlush));

            return Void.INSTANCE;
        }, new Argument(Type.BOOLEAN, "autoFlush"),
                new Argument(Type.of(OutputStreamClass.CLASS_NAME), "output")));

        addConstructor(new Library.LibConstructor((mainContext, context) -> {
            OutputStreamClass outputStreamClass = context.getVariable("output");
            context.parentContext.addVariable(PRINT_STREAM_FIELD, new PrintStream(
                    outputStreamClass.getOutputStream()));

            return Void.INSTANCE;
        }, new Argument(Type.of(OutputStreamClass.CLASS_NAME), "output")));

        addConstructor(new Library.LibConstructor((mainContext, context) -> {
            Object someInput = context.getVariable("ps");
            if(someInput instanceof PrintStream){
                context.parentContext.addVariable(PRINT_STREAM_FIELD, someInput);
            }else throw new KayJamRuntimeException(mainContext.parent, "wtf");

            return Void.INSTANCE;
        }, new Argument(Type.ANY, "ps")));

        addFunction(new Library.LibFunction("print", Type.VOID, (mainContext, context) -> {
            PrintStream printStream = context.getVariable("ps");
            printStream.print(toString(mainContext.executor, context.getVariable("value")));
            return Void.INSTANCE;
        }, new Argument(Type.ANY, "value")));

        addFunction(new Library.LibFunction("println", Type.VOID, (mainContext, context) -> {
            PrintStream printStream = context.getVariable("ps");
            printStream.println(toString(mainContext.executor, context.getVariable("value")));
            return Void.INSTANCE;
        }, new Argument(Type.ANY, "value")));

        addFunction(new Library.LibFunction("println", Type.VOID, (mainContext, context) -> {
            PrintStream printStream = context.getVariable("ps");
            printStream.println();

            return Void.INSTANCE;
        }));
    }

    public static String toString(Executor executor, Object value) throws Exception {
        if(value instanceof ClassContainer){
            try {
                ClassContainer classContainer = (ClassContainer) value;
                if(classContainer.data.containsKey("ctx")){
                    Context context = (Context) classContainer.data.get("ctx");
                    executor.provide(new CallOrCreateExpression("toString",
                            Collections.emptyList(), 0), context, context);
                }
            }catch (KayJamNotFoundException ignored){}
        }

        return value.toString();
    }
}
