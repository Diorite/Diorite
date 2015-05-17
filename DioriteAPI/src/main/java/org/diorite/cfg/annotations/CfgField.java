package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to include selected field to template.
 * You don't need use that by default,
 * use that only if you enabled manualy field selecting by {@link CfgClass#allFields()},
 * or if you want add single transient field.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgField
{
}
