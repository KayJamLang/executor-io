package com.github.kayjamlang.io.stream;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.executor.ClassUtils;
import com.github.kayjamlang.executor.Context;
import com.github.kayjamlang.executor.Executor;
import com.github.kayjamlang.executor.exceptions.KayJamRuntimeException;
import com.github.kayjamlang.executor.libs.Library;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;

public class OutputStreamClass extends Library.LibClass {
    public static final String CLASS_NAME = "Output";
    public static final String OUTPUT_STREAM_FIELD = "@output";
    public static final String OUTPUT_STREAM_BYTE_PARAMETER = "byte";

    public static OutputStreamClass create(Executor executor, OutputStream stream) throws Exception {
        return ClassUtils.newInstance(executor, new OutputStreamClass(),
                ClassUtils.findConstructor(executor.mainContext,
                        Collections.singletonList(Type.ANY), new OutputStreamClass()),
                Collections.singletonList(stream));
    }

    public OutputStreamClass() throws Exception {
        super(CLASS_NAME, null);
        implementsClass.add(Closeable.CLASS_NAME);

        addConstructor(new Library.LibConstructor((mainContext, context) -> {
            Object someInput = context.getVariable(OUTPUT_STREAM_FIELD);
            if(someInput instanceof OutputStream){
                context.parentContext.addVariable(OUTPUT_STREAM_FIELD, someInput);
            }else throw new KayJamRuntimeException(mainContext.parent, "wtf");

            return Void.TYPE;
        }, new Argument(Type.ANY, OUTPUT_STREAM_FIELD)));

        addConstructor(new Library.LibConstructor((mainContext, context) -> {
            context.parentContext.addVariable(OUTPUT_STREAM_FIELD, new ByteArrayOutputStream());
            return Void.TYPE;
        }));

        addFunction(new Library.LibFunction("write", Type.VOID, (mainContext, context) -> {
            Object someInput = context.parentContext.getVariable(OUTPUT_STREAM_FIELD);
            if(someInput instanceof OutputStream){
                ((OutputStream) someInput).write((int) context.getVariable(OUTPUT_STREAM_BYTE_PARAMETER));
            }

            return Void.TYPE;
        }, new Argument(Type.INTEGER, OUTPUT_STREAM_BYTE_PARAMETER)));

        addFunction(new Library.LibFunction("flush", Type.VOID, (mainContext, context) -> {
            Object someInput = context.parentContext.getVariable(OUTPUT_STREAM_FIELD);
            if(someInput instanceof OutputStream){
                ((OutputStream) someInput).flush();
            }

            return Void.TYPE;
        }, new Argument(Type.INTEGER, OUTPUT_STREAM_BYTE_PARAMETER)));

        addFunction(new Library.LibFunction("close", Type.VOID, (mainContext, context) -> {
            Object someInput = context.parentContext.getVariable(OUTPUT_STREAM_FIELD);
            if(someInput instanceof OutputStream){
                ((OutputStream) someInput).close();
            }

            return Void.TYPE;
        }));

        addFunction(new Library.LibFunction("toString", Utils.nullable(Type.STRING), (mainContext, context) -> {
            Object someInput = context.parentContext.getVariable(OUTPUT_STREAM_FIELD);
            if(someInput instanceof OutputStream){
                return someInput.toString();
            }

            return null;
        }));
    }

    public InputStream getOutputStream(){
        return ((Context) data.get("ctx")).getVariable(OUTPUT_STREAM_FIELD);
    }
}
