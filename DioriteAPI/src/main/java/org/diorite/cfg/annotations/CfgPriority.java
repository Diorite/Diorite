package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * By default all fields are stored in this same order,
 * as in class file, this annotation allows to change
 * position of single field.
 * <p>
 * Larger values are lower in file, but value returned by
 * this annotation is multipled by -1, so bigger values will
 * move field to the top.
 * <p>
 * By default fields use indexes from class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgPriority
{
    /**
     * @return priority of field order, larger values to move up.
     */
    int value();
}
