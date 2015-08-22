package org.diorite.cfg.annotations.defaults;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used by {@link CfgDelegateDefault} to provide more imports to code, so you don't need use full names.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface CfgDelegateImports
{
    /**
     * @return classs/packages to import.
     */
    String[] value();
}
