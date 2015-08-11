package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add comment to current node.
 * Rendered below node, annotation may be used multiple times.
 * Can be used with {@link CfgFooterComments}
 *
 * @see CfgComment
 * @see CfgComments
 * @see CfgCommentOptions
 * @see CfgFooterNoNewLine
 * @see CfgFooterComments
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Repeatable(CfgFooterCommentsArray.class)
public @interface CfgFooterComment
{
    /**
     * Don't add {@literal #} symbol.
     *
     * @return comment text.
     */
    String value();
}
