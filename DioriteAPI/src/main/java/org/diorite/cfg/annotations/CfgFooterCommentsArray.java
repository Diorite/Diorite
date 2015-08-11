package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used by {@link CfgFooterComment} for {@link java.lang.annotation.Repeatable} annotations.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface CfgFooterCommentsArray
{
    /**
     * @return all single comments annotations.
     */
    CfgFooterComment[] value();
}
