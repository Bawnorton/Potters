package com.bawnorton.potters.config.annotation;

import com.bawnorton.potters.config.option.OptionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LongOption {
    OptionType type() default OptionType.VANILLA;

    long value();

    long min() default 0;

    long max() default Long.MAX_VALUE;
}
