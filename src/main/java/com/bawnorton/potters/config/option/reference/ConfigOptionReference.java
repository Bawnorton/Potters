package com.bawnorton.potters.config.option.reference;

import com.bawnorton.potters.config.annotation.*;
import com.bawnorton.potters.config.option.NestedConfigOption;
import com.bawnorton.potters.config.option.OptionType;
import com.bawnorton.potters.config.reflection.Reflection;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Objects;

public class ConfigOptionReference {
    private final Object instance;
    private final Field field;
    private @NotNull Object value;

    protected ConfigOptionReference(Object instance, Field field) {
        this.instance = instance;
        this.field = field;
        this.value = Reflection.accessField(field, instance);
        if (value == null) throw new IllegalStateException("Value for " + field.getName() + " field is null.");
    }

    public static ConfigOptionReference of(Object instance, Field field) {
        return new ConfigOptionReference(instance, field);
    }

    private void validateType(Class<?> clazz) {
        if (!clazz.isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException("Invalid type " + clazz.getName() + " for " + value.getClass()
                .getName());
        }
    }

    private <T> void setConfigValue(T value) {
        if (value == null) throw new IllegalArgumentException("Value cannot be null.");
        validateType(value.getClass());
        Reflection.setField(field, instance, value);
        this.value = value;
    }

    private <T> T getValueAsType(Class<T> clazz) {
        validateType(clazz);
        return clazz.cast(value);
    }

    public String getFormattedName() {
        StringBuilder builder = new StringBuilder();
        String name = field.getName();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                builder.append('.');
                builder.append(Character.toLowerCase(c));
            } else builder.append(c);
        }
        return builder.toString();
    }

    public boolean isOf(OptionType type) {
        return getOptionType() == type;
    }

    protected OptionType getOptionType() {
        return switch (getType()) {
            case BOOLEAN -> field.getAnnotation(BooleanOption.class).type();
            case INTEGER -> field.getAnnotation(IntOption.class).type();
            case LONG -> field.getAnnotation(LongOption.class).type();
            case FLOAT -> field.getAnnotation(FloatOption.class).type();
            case NESTED -> field.getAnnotation(NestedOption.class).type();
        };
    }

    public boolean isNested() {
        return getType() == FieldType.NESTED;
    }

    public FieldType getType() {
        return FieldType.of(field.getType());
    }

    public void booleanValue(Boolean value) {
        setConfigValue(value);
    }

    public void intValue(Integer value) {
        setConfigValue(value);
    }

    public void longValue(Long value) {
        setConfigValue(value);
    }

    public void floatValue(Float value) {
        setConfigValue(value);
    }

    public Boolean booleanValue() {
        return getValueAsType(Boolean.class);
    }

    public Integer intValue() {
        return getValueAsType(Integer.class);
    }

    public Long longValue() {
        return getValueAsType(Long.class);
    }

    public Float floatValue() {
        return getValueAsType(Float.class);
    }

    public NestedConfigOption nestedValue() {
        return getValueAsType(NestedConfigOption.class);
    }

    private Number minValue() {
        return switch (getType()) {
            case FLOAT -> field.getAnnotation(FloatOption.class).min();
            case INTEGER -> field.getAnnotation(IntOption.class).min();
            case LONG -> field.getAnnotation(LongOption.class).min();
            case BOOLEAN, NESTED -> throw new IllegalArgumentException("Cannot get min value for type " + getType());
        };
    }

    private Number maxValue() {
        return switch (getType()) {
            case FLOAT -> field.getAnnotation(FloatOption.class).max();
            case INTEGER -> field.getAnnotation(IntOption.class).max();
            case LONG -> field.getAnnotation(LongOption.class).max();
            case BOOLEAN, NESTED -> throw new IllegalArgumentException("Cannot get max value for type " + getType());
        };
    }

    public Float minFloatValue() {
        return (Float) minValue();
    }

    public Float maxFloatValue() {
        return (Float) maxValue();
    }

    public Integer minIntValue() {
        return (Integer) minValue();
    }

    public Integer maxIntValue() {
        return (Integer) maxValue();
    }

    public Long minLongValue() {
        return (Long) minValue();
    }

    public Long maxLongValue() {
        return (Long) maxValue();
    }

    public @Nullable Identifier findTexture() {
        if (!field.isAnnotationPresent(TextureLocation.class)) return null;

        TextureLocation location = field.getAnnotation(TextureLocation.class);
        return new Identifier(location.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ConfigOptionReference other)) return false;
        return Objects.equals(field, other.field);
    }

    @Override
    public String toString() {
        return "ConfigOptionReference[" + "field=" + field + ']';
    }

    public enum FieldType {
        BOOLEAN, INTEGER, LONG, FLOAT, NESTED;

        public static FieldType of(Class<?> clazz) {
            if (Boolean.class.isAssignableFrom(clazz)) return BOOLEAN;
            if (Long.class.isAssignableFrom(clazz)) return LONG;
            if (Integer.class.isAssignableFrom(clazz)) return INTEGER;
            if (Float.class.isAssignableFrom(clazz)) return FLOAT;
            if (NestedConfigOption.class.isAssignableFrom(clazz)) return NESTED;
            throw new IllegalArgumentException("Unknown type " + clazz.getName());
        }
    }
}
