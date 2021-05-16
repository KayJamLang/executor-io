package com.github.kayjamlang.io.stream;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.executor.ClassUtils;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.libs.Library;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class InputStreamClass extends Library.LibClass {
    public static final String CLASS_NAME = "Input";
    public static final String INPUT_STREAM_FIELD = "@input";

    public static InputStreamClass create(Executor executor, InputStream stream) throws Exception {
        return ClassUtils.newInstance(executor, new InputStreamClass(),
                ClassUtils.findConstructor(executor.mainContext,
                        Collections.singletonList(Type.ANY), new InputStreamClass()),
                Collections.singletonList(stream));
    }

    public InputStreamClass() throws Exception {
        super(CLASS_NAME, null);
        implementsClass.add(Closeable.CLASS_NAME);

        addConstructor(new Library.LibConstructor((mainContext, context) -> {
            String input = context.getVariable(INPUT_STREAM_FIELD);
            context.parentContext.addVariable(INPUT_STREAM_FIELD, new ByteArrayInputStream(input
                    .getBytes(StandardCharsets.UTF_8)));

            return Void.TYPE;
        }, new Argument(Type.STRING, INPUT_STREAM_FIELD)));

        addConstructor(new Library.LibConstructor((mainContext, context) -> {
            Object someInput = context.getVariable(INPUT_STREAM_FIELD);
            if(someInput instanceof InputStream){
                context.parentContext.addVariable(INPUT_STREAM_FIELD, someInput);
            }else throw new KayJamRuntimeException(mainContext.parent, "wtf");

            return Void.TYPE;
        }, new Argument(Type.ANY, INPUT_STREAM_FIELD)));

        addFunction(new Library.LibFunction("read", Type.INTEGER, (mainContext, context) -> {
            Object someInput = context.parentContext.getVariable(INPUT_STREAM_FIELD);
            if(someInput instanceof InputStream){
                return ((InputStream) someInput).read();
            }

            return -1;
        }));

        addFunction(new Library.LibFunction("available", Type.INTEGER, (mainContext, context) -> {
            Object someInput = context.parentContext.getVariable(INPUT_STREAM_FIELD);
            if(someInput instanceof InputStream){
                return ((InputStream) someInput).available();
            }

            return 0;
        }));

        addFunction(new Library.LibFunction("markSupported", Type.BOOLEAN, (mainContext, context) -> {
            Object someInput = context.parentContext.getVariable(INPUT_STREAM_FIELD);
            if(someInput instanceof InputStream){
                return ((InputStream) someInput).markSupported();
            }

            return false;
        }));

        addFunction(new Library.LibFunction("skip", Type.LONG, (mainContext, context) -> {
            Object someInput = context.parentContext.getVariable(INPUT_STREAM_FIELD);
            if(someInput instanceof InputStream){
                return ((InputStream) someInput).skip(context.getVariable("n"));
            }

            return 0L;
        }, new Argument(Type.LONG, "n")));

        addFunction(new Library.LibFunction("close", Type.VOID, (mainContext, context) -> {
            Object someInput = context.parentContext.getVariable(INPUT_STREAM_FIELD);
            if(someInput instanceof InputStream){
                ((InputStream) someInput).close();
            }

            return 0L;
        }));
    }

    public InputStream getInputStream(){
        return ((Context) data.get("ctx")).getVariable(INPUT_STREAM_FIELD);
    }
}
