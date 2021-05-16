package com.github.kayjamlang.io.stream;

import com.github.kayjamlang.core.Type;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Utils {
    public static Type nullable(Type type){
        type = type.clone();
        type.nullable = true;

        return type;
    }

    public static void setFinal(Object object, Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(object, newValue);
    }
}
