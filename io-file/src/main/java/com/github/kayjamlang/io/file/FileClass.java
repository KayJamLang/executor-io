package com.github.kayjamlang.io.file;

import com.github.kayjamlang.core.Type;
import com.github.kayjamlang.core.expressions.data.Argument;
import com.github.kayjamlang.executor.Void;
import com.github.kayjamlang.executor.libs.Library;
import com.github.kayjamlang.io.stream.InputStreamClass;
import com.github.kayjamlang.io.stream.OutputStreamClass;

import java.io.*;

public class FileClass extends Library.LibClass {
    public static final String CLASS_NAME = "File";
    public static final String FILE_FIELD = "@file";
    public static final String IS_DIR_FIELD = "@isDir";

    public FileClass() throws Exception {
        super(CLASS_NAME, null);

        addConstructor(new Library.LibConstructor((mainContext, context) -> {
            String path = context.getVariable("path");
            File file = new File(path);
            context.parentContext.addVariable(FILE_FIELD, file);
            context.parentContext.addVariable(IS_DIR_FIELD, file.isDirectory());

            return Void.INSTANCE;
        }, new Argument(Type.STRING, "path")));

        addFunction(new Library.LibFunction("getInput", Type.of(InputStreamClass.CLASS_NAME),
                (mainContext, context) -> InputStreamClass.create(mainContext.executor,
                        new FileInputStream((File) context.parentContext.getVariable(FILE_FIELD)))));
        addFunction(new Library.LibFunction("getOutput", Type.of(OutputStreamClass.CLASS_NAME),
                (mainContext, context) -> OutputStreamClass.create(mainContext.executor,
                        new FileOutputStream((File) context.parentContext.getVariable(FILE_FIELD)))));

        addFunction(new Library.LibFunction("exists", Type.BOOLEAN,
                (mainContext, context) -> {
                    File file = context.parentContext.getVariable(FILE_FIELD);
                    return file.exists();
                }));

        addFunction(new Library.LibFunction("length", Type.LONG,
                (mainContext, context) -> {
                    File file = context.parentContext.getVariable(FILE_FIELD);
                    return file.length();
                }));

        addFunction(new Library.LibFunction("delete", Type.BOOLEAN,
                (mainContext, context) -> {
                    File file = context.parentContext.getVariable(FILE_FIELD);
                    return file.delete();
                }));

        addFunction(new Library.LibFunction("getName", Type.STRING,
                (mainContext, context) -> {
                    File file = context.parentContext.getVariable(FILE_FIELD);
                    return file.getName();
                }));

        addFunction(new Library.LibFunction("createFile", Type.BOOLEAN,
                (mainContext, context) -> {
                    File file = context.parentContext.getVariable(FILE_FIELD);
                    return file.createNewFile();
                }));

        addFunction(new Library.LibFunction("createDirectory", Type.BOOLEAN,
                (mainContext, context) -> {
                    File file = context.parentContext.getVariable(FILE_FIELD);
                    return file.mkdirs();
                }));
    }
}
