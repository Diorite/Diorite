package org.diorite.cfg.annotations.defaults;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to define default value of byte type.
 * It will be read from field only if field have compatybile type.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgByteDefault
{
    /**
     * @return default value of configuration field.
     */
    byte value();
}
