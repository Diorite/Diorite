package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows define collection style of selected field.
 * Defining style may speed-up yaml generation, but it
 * may also cause problems if style isn't compatyble with used data.
 * <p>
 * Ignored by fields that don't know how to use it.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgCollectionStyle
{
    /**
     * @return {@link org.diorite.cfg.annotations.CfgCollectionStyle.CollectionStyle} for this field.
     */
    CollectionStyle value();

    /**
     * enum with all possible styles.
     */
    enum CollectionStyle
    {
        /**
         * Default style, template will automagically choose best option.
         * (it must scan all elements, so it will be slower)
         */
        DEFAULT,
        /**
         * Use simple style, like list: [1,2,3], map: {22: 1, 12: 2}
         * if all elements are primitives.
         */
        SIMPLE_IF_PRIMITIVES,
        /**
         * Use simple style, like list: [1,2,3], map: {keyOne: 1, keyTwo: 2}
         * if all elements are primitives or one-line strings.
         *
         * @see CfgStringArrayMultilineThreshold
         */
        SIMPLE_IF_PRIMITIVES_OR_STRINGS,
        /**
         * Always use simple style, this may speed-up yaml generation,
         * but it will cause problems if collection contains multi-line strings or objects.
         */
        ALWAYS_SIMPLE,
        /**
         * Always use new-line style, this may speed-up yaml generation,
         * yaml generated with this style will be always matching with oryginal.
         * <br>
         * <pre>List:
         * - element1
         * - element2
         * Map:
         *   keyOne: valueOne
         *   keyTwo: valueTwo</pre>
         *
         * @see CfgStringArrayMultilineThreshold
         */
        ALWAYS_NEW_LINE;
    }
}
