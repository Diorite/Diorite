package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Determine if every custom element from node list should contains comments,
 * or only first one.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgCommentOptions
{
    /**
     * @return if every element from list/map should contains comments.
     */
    boolean commentEveryElement() default true;
}
