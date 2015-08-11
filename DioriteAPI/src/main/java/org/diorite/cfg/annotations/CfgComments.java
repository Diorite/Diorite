package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add comment array to current node.
 * Rendered abovde node,
 * can be used with {@link CfgComment}
 *
 * @see CfgComment
 * @see CfgCommentOptions
 * @see CfgFooterComment
 * @see CfgFooterComments
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface CfgComments
{
    /**
     * Don't add {@literal #} symbol.
     *
     * @return string array with comments.
     */
    String[] value();
}
