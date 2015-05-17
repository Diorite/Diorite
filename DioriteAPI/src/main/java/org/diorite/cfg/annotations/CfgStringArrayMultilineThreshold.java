package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * if list contains any string with more chars than this amount,
 * it will use new-line style.
 * 25 by default.
 *
 * <pre>newLineStyle:
 * - first long element
 * - second long element</pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgStringArrayMultilineThreshold
{
    /**
     * @return Threshold to use new-line style.
     */
    int value();
}
