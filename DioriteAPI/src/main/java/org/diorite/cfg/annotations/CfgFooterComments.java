package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add comment array to current node.
 * Rendered below node,
 * can be used with {@link CfgFooterComment}
 *
 * @see CfgComment
 * @see CfgComments
 * @see CfgCommentOptions
 * @see CfgFooterNoNewLine
 * @see CfgFooterComment
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface CfgFooterComments
{
    /**
     * Don't add {@literal #} symbol.
     *
     * @return string array with comments.
     */
    String[] value();
}
