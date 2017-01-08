/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.commons.reflections;

import javax.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.MalformedParametersException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.diorite.commons.DioriteUtils;

/**
 * Class used to access/invoke previously prepared methods,
 * methods used by this class must be accessible.
 */
public class MethodInvoker implements ReflectMethod
{
    protected final Method method;

    /**
     * Construct new invoker for given method, it don't check its accessible status.
     *
     * @param method
     *         method to wrap.
     */
    public MethodInvoker(Method method)
    {
        this.method = method;
    }

    /**
     * Invoke method and create new object.
     *
     * @param target
     *         target object, use null for static fields.
     * @param arguments
     *         arguments for method.
     *
     * @return method invoke result.
     */
    @Nullable
    public Object invoke(@Nullable Object target, Object... arguments)
    {
        try
        {
            return this.method.invoke(target, arguments);
        }
        catch (IllegalAccessException | IllegalArgumentException e)
        {
            throw new RuntimeException("Cannot invoke method " + this.method, e);
        }
        catch (InvocationTargetException e)
        {
            throw DioriteUtils.sneakyThrow(e.getCause());
        }
        catch (Exception e)
        {
            throw DioriteUtils.sneakyThrow(e);
        }
    }

    /**
     * @return wrapped method.
     */
    public Method getMethod()
    {
        return this.method;
    }

    @Nullable
    private MethodHandle cached;

    @Override
    public boolean isConstructor()
    {
        return false;
    }

    @Nullable
    @Override
    public Object invokeWith(Object... args) throws IllegalArgumentException
    {
        if (this.isStatic())
        {
            return this.invoke(null, args);
        }
        if (args.length == 0)
        {
            throw new IllegalArgumentException("Missing object instance!");
        }
        Object inst = args[0];
        if (args.length == 1)
        {
            return this.invoke(inst);
        }
        Object[] newArgs = new Object[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, args.length - 1);
        return this.invoke(inst, newArgs);
    }

    @Override
    public String getName()
    {
        return this.method.getName();
    }

    @Override
    public int getModifiers()
    {
        return this.method.getModifiers();
    }

    @Override
    public void ensureAccessible()
    {
        DioriteReflectionUtils.getAccess(this.method);
    }

    @Override
    public MethodHandle getHandle()
    {
        if (this.cached != null)
        {
            return this.cached;
        }
        try
        {
            return this.cached = MethodHandles.lookup().unreflect(this.method);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("Cannot access reflection.", e);
        }
    }

    @Override
    public String toString()
    {
        return this.method.toString();
    }

    /**
     * Returns the {@code Class} object representing the class or interface that declares the method represented by this object.
     *
     * @return the {@code Class} object representing the class or interface that declares the method represented by this object.
     */
    public Class<?> getDeclaringClass()
    {
        return this.method.getDeclaringClass();
    }

    /**
     * Returns an array of {@code TypeVariable} objects that represent the
     * type variables declared by the generic declaration represented by this
     * {@code GenericDeclaration} object, in declaration order.  Returns an
     * array of length 0 if the underlying generic declaration declares no type
     * variables.
     *
     * @return an array of {@code TypeVariable} objects that represent the type variables declared by this generic declaration
     *
     * @throws GenericSignatureFormatError
     *         if the generic signature of this generic declaration does not conform to the format specified in <cite>The Java&trade; Virtual Machine
     *         Specification</cite>
     */
    public TypeVariable<Method>[] getTypeParameters()
    {
        return this.method.getTypeParameters();
    }

    /**
     * Returns a {@code Class} object that represents the formal return type
     * of the method represented by this {@code Method} object.
     *
     * @return the return type for the method this object represents
     */
    public Class<?> getReturnType()
    {
        return this.method.getReturnType();
    }

    /**
     * Returns a {@code Type} object that represents the formal return
     * type of the method represented by this {@code Method} object.
     *
     * <p>If the return type is a parameterized type,
     * the {@code Type} object returned must accurately reflect
     * the actual type parameters used in the source code.
     *
     * <p>If the return type is a type variable or a parameterized type, it
     * is created. Otherwise, it is resolved.
     *
     * @return a {@code Type} object that represents the formal return type of the underlying  method
     *
     * @throws GenericSignatureFormatError
     *         if the generic method signature does not conform to the format specified in <cite>The Java&trade; Virtual Machine Specification</cite>
     * @throws TypeNotPresentException
     *         if the underlying method's return type refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException
     *         if the underlying method's return typed refers to a parameterized type that cannot be instantiated for any reason
     */
    public Type getGenericReturnType()
    {
        return this.method.getGenericReturnType();
    }

    /**
     * Returns an array of {@code Class} objects that represent the formal
     * parameter types, in declaration order, of the executable
     * represented by this object.  Returns an array of length
     * 0 if the underlying executable takes no parameters.
     *
     * @return the parameter types for the executable this object represents
     */
    public Class<?>[] getParameterTypes()
    {
        return this.method.getParameterTypes();
    }

    /**
     * Returns the number of formal parameters (whether explicitly
     * declared or implicitly declared or neither) for the executable
     * represented by this object.
     *
     * @return The number of formal parameters for the executable this object represents
     */
    public int getParameterCount()
    {
        return this.method.getParameterCount();
    }

    /**
     * Returns an array of {@code Type} objects that represent the formal
     * parameter types, in declaration order, of the executable represented by
     * this object. Returns an array of length 0 if the
     * underlying executable takes no parameters.
     *
     * <p>If a formal parameter type is a parameterized type,
     * the {@code Type} object returned for it must accurately reflect
     * the actual type parameters used in the source code.
     *
     * <p>If a formal parameter type is a type variable or a parameterized
     * type, it is created. Otherwise, it is resolved.
     *
     * @return an array of {@code Type}s that represent the formal parameter types of the underlying executable, in declaration order
     *
     * @throws GenericSignatureFormatError
     *         if the generic method signature does not conform to the format specified in <cite>The Java&trade; Virtual Machine Specification</cite>
     * @throws TypeNotPresentException
     *         if any of the parameter types of the underlying executable refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException
     *         if any of the underlying executable's parameter types refer to a parameterized type that cannot be instantiated for any reason
     */
    public Type[] getGenericParameterTypes()
    {
        return this.method.getGenericParameterTypes();
    }

    /**
     * Returns an array of {@code Class} objects that represent the
     * types of exceptions declared to be thrown by the underlying
     * executable represented by this object.  Returns an array of
     * length 0 if the executable declares no exceptions in its {@code
     * throws} clause.
     *
     * @return the exception types declared as being thrown by the executable this object represents
     */
    public Class<?>[] getExceptionTypes()
    {
        return this.method.getExceptionTypes();
    }

    /**
     * Returns an array of {@code Type} objects that represent the
     * exceptions declared to be thrown by this executable object.
     * Returns an array of length 0 if the underlying executable declares
     * no exceptions in its {@code throws} clause.
     *
     * <p>If an exception type is a type variable or a parameterized
     * type, it is created. Otherwise, it is resolved.
     *
     * @return an array of Types that represent the exception types thrown by the underlying executable
     *
     * @throws GenericSignatureFormatError
     *         if the generic method signature does not conform to the format specified in <cite>The Java&trade; Virtual Machine Specification</cite>
     * @throws TypeNotPresentException
     *         if the underlying executable's {@code throws} clause refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException
     *         if the underlying executable's {@code throws} clause refers to a parameterized type that cannot be instantiated for any reason
     */
    public Type[] getGenericExceptionTypes()
    {
        return this.method.getGenericExceptionTypes();
    }

    /**
     * Returns a string describing this {@code Method}, including
     * type parameters.  The string is formatted as the method access
     * modifiers, if any, followed by an angle-bracketed
     * comma-separated list of the method's type parameters, if any,
     * followed by the method's generic return type, followed by a
     * space, followed by the class declaring the method, followed by
     * a period, followed by the method name, followed by a
     * parenthesized, comma-separated list of the method's generic
     * formal parameter types.
     *
     * If this method was declared to take a variable number of
     * arguments, instead of denoting the last parameter as
     * "<code><i>Type</i>[]</code>", it is denoted as
     * "<code><i>Type</i>...</code>".
     *
     * A space is used to separate access modifiers from one another
     * and from the type parameters or return type.  If there are no
     * type parameters, the type parameter list is elided; if the type
     * parameter list is present, a space separates the list from the
     * class name.  If the method is declared to throw exceptions, the
     * parameter list is followed by a space, followed by the word
     * "{@code throws}" followed by a comma-separated list of the generic
     * thrown exception types.
     *
     * <p>The access modifiers are placed in canonical order as
     * specified by "The Java Language Specification".  This is
     * {@code public}, {@code protected} or {@code private} first,
     * and then other modifiers in the following order:
     * {@code abstract}, {@code default}, {@code static}, {@code final},
     * {@code synchronized}, {@code native}, {@code strictfp}.
     *
     * @return a string describing this {@code Method}, include type parameters
     */
    public String toGenericString()
    {
        return this.method.toGenericString();
    }

    /**
     * Returns {@code true} if this method is a bridge
     * method; returns {@code false} otherwise.
     *
     * @return true if and only if this method is a bridge method as defined by the Java Language Specification.
     */
    public boolean isBridge()
    {
        return this.method.isBridge();
    }

    /**
     * Returns {@code true} if this executable is a synthetic
     * construct; returns {@code false} otherwise.
     *
     * @return true if and only if this executable is a synthetic construct as defined by <cite>The Java&trade; Language Specification</cite>.
     */
    public boolean isVarArgs()
    {
        return this.method.isVarArgs();
    }

    /**
     * Returns {@code true} if this executable is a synthetic
     * construct; returns {@code false} otherwise.
     *
     * @return true if and only if this executable is a synthetic construct as defined by <cite>The Java&trade; Language Specification</cite>.
     */
    public boolean isSynthetic()
    {
        return this.method.isSynthetic();
    }

    /**
     * Returns {@code true} if this method is a default
     * method; returns {@code false} otherwise.
     *
     * A default method is a public non-abstract instance method, that
     * is, a non-static method with a body, declared in an interface
     * type.
     *
     * @return true if and only if this method is a default method as defined by the Java Language Specification.
     */
    public boolean isDefault()
    {
        return this.method.isDefault();
    }

    /**
     * Returns the default value for the annotation member represented by
     * this {@code Method} instance.  If the member is of a primitive type,
     * an instance of the corresponding wrapper type is returned. Returns
     * null if no default is associated with the member, or if the method
     * instance does not represent a declared member of an annotation type.
     *
     * @return the default value for the annotation member represented by this {@code Method} instance.
     *
     * @throws TypeNotPresentException
     *         if the annotation is of type {@link Class} and no definition can be found for the default class value.
     */
    public Object getDefaultValue()
    {
        return this.method.getDefaultValue();
    }

    /**
     * Returns this element's annotation for the specified type if
     * such an annotation is <em>present</em>, else null.
     *
     * @param <T>
     *         the type of the annotation to query for and return if present
     * @param annotationClass
     *         the Class object corresponding to the annotation type
     *
     * @return this element's annotation for the specified annotation type if present on this element, else null
     *
     * @throws NullPointerException
     *         if the given annotation class is null
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass)
    {
        return this.method.getAnnotation(annotationClass);
    }

    /**
     * Returns annotations that are <em>directly present</em> on this element.
     * This method ignores inherited annotations.
     *
     * If there are no annotations <em>directly present</em> on this element,
     * the return value is an array of length 0.
     *
     * The caller of this method is free to modify the returned array; it will
     * have no effect on the arrays returned to other callers.
     *
     * @return annotations directly present on this element
     */
    public Annotation[] getDeclaredAnnotations()
    {
        return this.method.getDeclaredAnnotations();
    }

    /**
     * Returns an array of arrays of {@code Annotation}s that
     * represent the annotations on the formal parameters, in
     * declaration order, of the {@code Executable} represented by
     * this object.  Synthetic and mandated parameters (see
     * explanation below), such as the outer "this" parameter to an
     * inner class constructor will be represented in the returned
     * array.  If the executable has no parameters (meaning no formal,
     * no synthetic, and no mandated parameters), a zero-length array
     * will be returned.  If the {@code Executable} has one or more
     * parameters, a nested array of length zero is returned for each
     * parameter with no annotations. The annotation objects contained
     * in the returned arrays are serializable.  The caller of this
     * method is free to modify the returned arrays; it will have no
     * effect on the arrays returned to other callers.
     *
     * A compiler may add extra parameters that are implicitly
     * declared in source ("mandated"), as well as parameters that
     * are neither implicitly nor explicitly declared in source
     * ("synthetic") to the parameter list for a method.  See {@link
     * java.lang.reflect.Parameter} for more information.
     *
     * @return an array of arrays that represent the annotations on the formal and implicit parameters, in declaration order, of the executable represented by
     * this object
     *
     * @see java.lang.reflect.Parameter
     * @see java.lang.reflect.Parameter#getAnnotations
     */
    public Annotation[][] getParameterAnnotations()
    {
        return this.method.getParameterAnnotations();
    }

    /**
     * Returns an {@code AnnotatedType} object that represents the use of a type to
     * specify the return type of the method/constructor represented by this
     * Executable.
     *
     * If this {@code Executable} object represents a constructor, the {@code
     * AnnotatedType} object represents the type of the constructed object.
     *
     * If this {@code Executable} object represents a method, the {@code
     * AnnotatedType} object represents the use of a type to specify the return
     * type of the method.
     *
     * @return an object representing the return type of the method or constructor represented by this {@code Executable}
     */
    public AnnotatedType getAnnotatedReturnType()
    {
        return this.method.getAnnotatedReturnType();
    }

    /**
     * Returns an array of {@code Parameter} objects that represent
     * all the parameters to the underlying executable represented by
     * this object.  Returns an array of length 0 if the executable
     * has no parameters.
     *
     * <p>The parameters of the underlying executable do not necessarily
     * have unique names, or names that are legal identifiers in the
     * Java programming language (JLS 3.8).
     *
     * @return an array of {@code Parameter} objects representing all the parameters to the executable this object represents.
     *
     * @throws MalformedParametersException
     *         if the class file contains a MethodParameters attribute that is improperly formatted.
     */
    public Parameter[] getParameters()
    {
        return this.method.getParameters();
    }

    /**
     * Returns annotations that are <em>associated</em> with this element.
     *
     * If there are no annotations <em>associated</em> with this element, the return
     * value is an array of length 0.
     *
     * The difference between this method and {@link #getAnnotation(Class)}
     * is that this method detects if its argument is a <em>repeatable
     * annotation type</em> (JLS 9.6), and if so, attempts to find one or
     * more annotations of that type by "looking through" a container
     * annotation.
     *
     * The caller of this method is free to modify the returned array; it will
     * have no effect on the arrays returned to other callers.
     * <br>
     * The default implementation first calls {@link #getDeclaredAnnotationsByType(Class)} passing {@code annotationClass} as the argument. If the
     * returned array has length greater than zero, the array is returned. If the returned array is zero-length and this {@code AnnotatedElement} is a class and
     * the argument type is an inheritable annotation type, and the superclass of this {@code AnnotatedElement} is non-null, then the returned result is the
     * result of calling {@link #getAnnotationsByType(Class)} on the superclass with {@code annotationClass} as the argument. Otherwise, a zero-length array is
     * returned.
     *
     * @param <T>
     *         the type of the annotation to query for and return if present
     * @param annotationClass
     *         the Class object corresponding to the annotation type
     *
     * @return all this element's annotations for the specified annotation type if associated with this element, else an array of length zero
     *
     * @throws NullPointerException
     *         if the given annotation class is null
     */
    public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass)
    {
        return this.method.getAnnotationsByType(annotationClass);
    }

    /**
     * Returns an {@code AnnotatedType} object that represents the use of a
     * type to specify the receiver type of the method/constructor represented
     * by this {@code Executable} object.
     *
     * The receiver type of a method/constructor is available only if the
     * method/constructor has a receiver parameter (JLS 8.4.1). If this {@code
     * Executable} object <em>represents an instance method or represents a
     * constructor of an inner member class</em>, and the
     * method/constructor <em>either</em> has no receiver parameter or has a
     * receiver parameter with no annotations on its type, then the return
     * value is an {@code AnnotatedType} object representing an element with no
     * annotations.
     *
     * If this {@code Executable} object represents a static method or
     * represents a constructor of a top level, static member, local, or
     * anonymous class, then the return value is null.
     *
     * @return an object representing the receiver type of the method or constructor represented by this {@code Executable} or {@code null} if this {@code
     * Executable} can not have a receiver parameter
     */
    public AnnotatedType getAnnotatedReceiverType()
    {
        return this.method.getAnnotatedReceiverType();
    }

    /**
     * Returns an array of {@code AnnotatedType} objects that represent the use
     * of types to specify formal parameter types of the method/constructor
     * represented by this Executable. The order of the objects in the array
     * corresponds to the order of the formal parameter types in the
     * declaration of the method/constructor.
     *
     * Returns an array of length 0 if the method/constructor declares no
     * parameters.
     *
     * @return an array of objects representing the types of the formal parameters of the method or constructor represented by this {@code Executable}
     */
    public AnnotatedType[] getAnnotatedParameterTypes()
    {
        return this.method.getAnnotatedParameterTypes();
    }

    /**
     * Returns an array of {@code AnnotatedType} objects that represent the use
     * of types to specify the declared exceptions of the method/constructor
     * represented by this Executable. The order of the objects in the array
     * corresponds to the order of the exception types in the declaration of
     * the method/constructor.
     *
     * Returns an array of length 0 if the method/constructor declares no
     * exceptions.
     *
     * @return an array of objects representing the declared exceptions of the method or constructor represented by this {@code Executable}
     */
    public AnnotatedType[] getAnnotatedExceptionTypes()
    {
        return this.method.getAnnotatedExceptionTypes();
    }

    /**
     * Get the value of the {@code accessible} flag for this object.
     *
     * @return the value of the object's {@code accessible} flag
     */
    public boolean isAccessible()
    {
        return this.method.isAccessible();
    }

    /**
     * Returns true if an annotation for the specified type
     * is <em>present</em> on this element, else false.  This method
     * is designed primarily for convenient access to marker annotations.
     *
     * <p>The truth value returned by this method is equivalent to:
     * {@code getAnnotation(annotationClass) != null}
     *
     * <p>The body of the default method is specified to be the code
     * above.
     *
     * @param annotationClass
     *         the Class object corresponding to the annotation type
     *
     * @return true if an annotation for the specified annotation type is present on this element, else false
     *
     * @throws NullPointerException
     *         if the given annotation class is null
     */
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass)
    {
        return this.method.isAnnotationPresent(annotationClass);
    }

    /**
     * Returns annotations that are <em>present</em> on this element.
     *
     * If there are no annotations <em>present</em> on this element, the return
     * value is an array of length 0.
     *
     * The caller of this method is free to modify the returned array; it will
     * have no effect on the arrays returned to other callers.
     *
     * @return annotations present on this element
     */
    public Annotation[] getAnnotations()
    {
        return this.method.getAnnotations();
    }

    /**
     * Returns this element's annotation for the specified type if
     * such an annotation is <em>directly present</em>, else null.
     *
     * This method ignores inherited annotations. (Returns null if no
     * annotations are directly present on this element.)
     * <br>
     * The default implementation first performs a null check and then loops over the results of {@link #getDeclaredAnnotations} returning the first
     * annotation whose annotation type matches the argument type.
     *
     * @param <T>
     *         the type of the annotation to query for and return if directly present
     * @param annotationClass
     *         the Class object corresponding to the annotation type
     *
     * @return this element's annotation for the specified annotation type if directly present on this element, else null
     *
     * @throws NullPointerException
     *         if the given annotation class is null
     */
    public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass)
    {
        return this.method.getDeclaredAnnotation(annotationClass);
    }

    /**
     * Returns this element's annotation(s) for the specified type if
     * such annotations are either <em>directly present</em> or
     * <em>indirectly present</em>. This method ignores inherited
     * annotations.
     *
     * If there are no specified annotations directly or indirectly
     * present on this element, the return value is an array of length
     * 0.
     *
     * The difference between this method and {@link
     * #getDeclaredAnnotation(Class)} is that this method detects if its
     * argument is a <em>repeatable annotation type</em> (JLS 9.6), and if so,
     * attempts to find one or more annotations of that type by "looking
     * through" a container annotation if one is present.
     *
     * The caller of this method is free to modify the returned array; it will
     * have no effect on the arrays returned to other callers.
     * <br>
     * The default implementation may call {@link #getDeclaredAnnotation(Class)} one or more times to find a directly present annotation and, if the
     * annotation type is repeatable, to find a container annotation. If annotations of the annotation type {@code annotationClass} are found to be both
     * directly and indirectly present, then {@link #getDeclaredAnnotations()} will get called to determine the order of the elements in the returned array.
     *
     * <p>Alternatively, the default implementation may call {@link #getDeclaredAnnotations()} a single time and the returned array examined for both directly
     * and indirectly present annotations. The results of calling {@link #getDeclaredAnnotations()} are assumed to be consistent with the results of calling
     * {@link #getDeclaredAnnotation(Class)}.
     *
     * @param <T>
     *         the type of the annotation to query for and return if directly or indirectly present
     * @param annotationClass
     *         the Class object corresponding to the annotation type
     *
     * @return all this element's annotations for the specified annotation type if directly or indirectly present on this element, else an array of length zero
     *
     * @throws NullPointerException
     *         if the given annotation class is null
     */
    public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass)
    {
        return this.method.getDeclaredAnnotationsByType(annotationClass);
    }
}
