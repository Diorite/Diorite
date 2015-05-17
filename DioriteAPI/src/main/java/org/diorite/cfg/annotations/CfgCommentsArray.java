package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used by {@link CfgComment} for {@link java.lang.annotation.Repeatable} annotations.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface CfgCommentsArray
{
    /**
     * @return all single comments annotations.
     */
    CfgComment[] value();
}
