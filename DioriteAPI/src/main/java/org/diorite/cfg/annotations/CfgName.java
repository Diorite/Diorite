package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to change name of generated field,
 * changing name of field my make file impossbile to load,
 * wtihout manually playing around Map of data.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgName
{
    /**
     * @return new name of field used in yaml file.
     */
    String value();
}
