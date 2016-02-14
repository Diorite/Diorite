package org.diorite.utils.reflections;

import javassist.CtClass;

/**
 * Utils for javassist library, in separate class so javassist library is optional
 */
public class JavassistUtils
{
    /**
     * Array of empty javassit classes
     */
    public static final CtClass[] EMPTY_CLASSES = new CtClass[0];
}
