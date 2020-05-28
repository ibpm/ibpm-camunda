package com.github.ibpm.config.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreLogResult {

	boolean value() default true;
}
