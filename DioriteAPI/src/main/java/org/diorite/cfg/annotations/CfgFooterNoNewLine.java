package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to remove new line before footer,
 * so comment will be right after node, instead below it:
 * <br>
 * <pre>node: value # my comment
 * otherNode: [1, 2, 3] # other comment</pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface CfgFooterNoNewLine
{
    /**
     * @return if comment should be rgiht after node.
     */
    boolean value() default true;
}
