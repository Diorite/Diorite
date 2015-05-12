package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgCollectionStyle
{
    CollectionStyle value();

    enum CollectionStyle
    {
        DEFAULT,
        SIMPLE_IF_PRIMITIVES,
        SIMPLE_IF_PRIMITIVES_OR_STRINGS,
        ALWAYS_SIMPLE,
        ALWAYS_NEW_LINE;
    }
}
