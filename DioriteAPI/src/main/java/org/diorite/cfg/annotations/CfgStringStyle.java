package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows define string style of selected field.
 * Defining style may speed-up yaml generation, but it
 * may also cause problems if style isn't compatyble with used data.
 * <p>
 * Ignored by fields that don't know how to use it.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgStringStyle
{
    /**
     * @return {@link org.diorite.cfg.annotations.CfgStringStyle.StringStyle} for this field.
     */
    StringStyle value();

    /**
     * enum with all possible styles.
     */
    enum StringStyle
    {
        /**
         * Default style, template will automagically choose best option.
         * (it must scan all elements, so it will be slower)
         */
        DEFAULT,
        /**
         * Always use double quotes in strings, will cause problems with
         * multi-lined strings.
         * <p>
         * node: "value of node"
         */
        ALWAYS_QUOTED,
        /**
         * Always use single quotes in strings, will cause problems with
         * multi-lined strings.
         * <p>
         * node: 'value of node'
         */
        ALWAYS_SINGLE_QUOTED,
        /**
         * Always use multi-lined style for strings, don't cause any problems.
         * <br>
         * <pre>node: |2-
         *   Some multi line
         *   string ;)</pre>
         */
        ALWAYS_MULTI_LINE
    }
}
