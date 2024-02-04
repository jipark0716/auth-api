package com.jipark.auth.extensions.java.lang.Object;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Column;

import java.util.HashMap;
import java.util.Map;

@Extension
public final class ReflectExtension {
    public static Map<String, Object> toColumMap(@This Object source, String query) {
        var result = new HashMap<String, Object>();
        for (var field : source.getClass().getDeclaredFields()) {
            if (field.getAnnotation(ReadOnlyProperty.class) != null) continue;

            var column = field.getAnnotation(Column.class);
            if (column == null) continue;

            if (! query.contains(":" + column.value())) continue ;

            field.setAccessible(true);
            try {
                result.put(
                        column.value(),
                        switch (field.get(source)) {
                            case Enum e -> e.toString();
                            default -> field.get(source);
                        }
                );
            } catch (IllegalAccessException ignored) {

            }
        }
        return result;
    }
}
