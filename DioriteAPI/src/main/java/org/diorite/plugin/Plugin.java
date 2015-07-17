package org.diorite.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Plugin
{
    String name();

    String version() default "unknown";

    String author() default "unknown";

    String description() default "";

    String website() default "";
}
