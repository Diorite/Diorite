package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgCollectionType
{
    CollectionType value();

    enum CollectionType
    {
        OBJECTS,
        STRINGS,
        PRIMITIVES,
        STRINGS_AND_PRIMITIVES;
    }
}
