package org.diorite.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Plugin
{
    /**
     * Plugin name, must be unique.
     *
     * @return plugin name;
     */
    String name();

    /**
     * Version of plugin.
     *
     * @return version of plugin.
     */
    String version() default "unknown";

    /**
     * Prefix of plugin, used in logger and some other places.
     *
     * @return prefix of plugin.
     */
    String prefix() default "%name% v%version%"; // supprot name and version placeholder

    /**
     * Author of plugin.
     *
     * @return author of plugin.
     */
    String author() default "unknown";

    /**
     * Description of plugin.
     *
     * @return description of plugin.
     */
    String description() default "";

    /**
     * Website of plugin.
     *
     * @return website of plugin.
     */
    String website() default "";

}
