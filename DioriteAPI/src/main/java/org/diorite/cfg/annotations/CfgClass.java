package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * You can annotate classes used by your configuration system,
 * additionally it provide few options:
 * option to disable auto-using all fields. (enabled by default)
 * option to disable using super class. (enabled by default)
 * option to disable skipping transient fields. (enabled by default)
 * option to skip selected fields. (empty by default)
 * @see CfgExclude
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CfgClass
{

    /**
     * Name of configuration unit, should be class name.
     *
     * @return Name of configuration unit
     */
    String name();

    /**
     * true by default.
     * Disabling that gives you ability to manually select fields by {@link CfgField} annotation.
     *
     * @return if all fields non-excluded should be used.
     */
    boolean allFields() default true;

    /**
     * true by default.
     *
     * @return if fields from super class should be used too.
     */
    boolean superFields() default true;

    /**
     * true by default.
     *
     * @return if transient fields should be ignored.
     */
    boolean ignoreTransient() default true;

    /**
     * Fields to exclude (type name)
     * @see CfgExclude
     *
     * @return list of field names to skip.
     */
    String[] excludeFields() default {};
}
