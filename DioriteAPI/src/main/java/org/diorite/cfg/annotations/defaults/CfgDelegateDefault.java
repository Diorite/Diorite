package org.diorite.cfg.annotations.defaults;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to define default value of custom object type.
 * Object is read using String path to static non-arg method
 * that should return default value.
 * <p>
 * Additionally you can use few placeholders:
 * {@literal {emptyMap} -> new HashMap<>(1);}
 * {@literal {emptyList} -> new ArrayList<>(1);}
 * {@literal {emptySet} -> new HashSet<>(1);}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CfgDelegateDefault
{
    /**
     * String should be a path to default value, like package.Class#getDefaultValue
     * Mehod must be static and no-args, you can skip class with method is in this same
     * class as field that will use this default value.
     * <p>
     * Additionally you can use few placeholders:
     * {@literal {emptyMap} -> new HashMap<>(1);}
     * {@literal {emptyList} -> new ArrayList<>(1);}
     * {@literal {emptySet} -> new HashSet<>(1);}
     *
     * @return name of method (and class) that returns default value or placeholder.
     */
    String value(); // it should show no-args method that will return default value, like package.Class#getDefaultValue
}
