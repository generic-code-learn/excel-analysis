package com.github.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel单元格注解，用于标注属性对应的行头
 *
 * @author 杨中肖
 * @date 2021/09/06
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelCell {
    /**
     * 行头名
     *
     * @return {@link String[] }
     * @author 杨中肖
     * @date 2021/09/10
     */
    String[] headerName() default {};
}
