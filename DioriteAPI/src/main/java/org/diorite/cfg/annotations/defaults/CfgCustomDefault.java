package org.diorite.cfg.annotations.defaults;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to define default value of custom enum {@link Enum} type.
 * It will be read from field only if field have compatybile type.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface CfgCustomDefault
{
    /**
     * @return class of enum for default value of configuration field.
     */
    Class<? extends Enum<?>> value();
}
