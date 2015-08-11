package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows define collection type of selected field.
 * Any type other than unknown will disable build-in type checks,
 * so yaml generation will be faster, but may fail if you don't use valid type.
 * <p>
 * If collection contais other collections (or maps or arrays) of primitive types, you can
 * select primitive type.
 * <p>
 * Ignored by fields that don't know how to use it.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgCollectionType
{
    /**
     * @return {@link org.diorite.cfg.annotations.CfgCollectionType.CollectionType} for this field.
     */
    CollectionType value();

    /**
     * enum with all possible types.
     */
    enum CollectionType
    {
        /**
         * Default value, unknown type.
         */
        UNKNOWN,
        /**
         * Custom objects.
         */
        OBJECTS,
        /**
         * Strings, or other nested collections/maps/arrays contains strings.
         */
        STRINGS,
        /**
         * Primitives, or other nested collections/maps/arrays contains primitives.
         */
        PRIMITIVES,
        /**
         * Strings and/or primitives, or other nested collections/maps/arrays contains strings and/or primitives.
         */
        STRINGS_AND_PRIMITIVES
    }
}
