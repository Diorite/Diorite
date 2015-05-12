package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgStringStyle
{
    StringStyle value();

    enum StringStyle
    {
        DEFAULT,
        ALWAYS_QUOTED,
        ALWAYS_SINGLE_QUOTED,
        ALWAYS_MULTI_LINE
    }
}
