package com.github.ibpm.config.annotation;

import com.github.ibpm.config.datasource.DataSourceName;

import java.lang.annotation.*;

/**
 * multi datasource annotation
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiDataSource {
	
	/** change to second */
    String value() default DataSourceName.SECOND;
}
