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

package org.diorite.inject;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.diorite.inject.binder.Binder;
import org.diorite.inject.binder.DynamicProvider;
import org.diorite.inject.binder.Provider;
import org.diorite.inject.binder.qualifier.AnnotationTransformer;

/**
 * Controller API interface.
 */
public interface Controller
{
    /**
     * Provider that always returns null value.
     */
    @SuppressWarnings("rawtypes") Provider        NULL_PROVIDER         = () -> null;
    /**
     * Dynamic provider that always returns null value.
     */
    @SuppressWarnings("rawtypes") DynamicProvider NULL_DYNAMIC_PROVIDER = (a, b) -> null;

    /**
     * Register custom scope handler, like {@link org.diorite.inject.impl.scopes.SingletonScopeHandler} for {@link Singleton} annotation. <br/>
     * Each value have own scope handler instance, so Supplier should always return new instance of handler.
     *
     * @param scope
     *         scope annotation.
     * @param handler
     *         constructor of handlers for this scope.
     * @param <T>
     *         type of injected object.
     * @param <S>
     *         type of scope annotation.
     *
     * @return
     */
    <T, S extends Annotation> Supplier<ScopeHandler<T, S>> registerScopeHandler(Class<S> scope, Supplier<ScopeHandler<T, S>> handler);

    /**
     * Adds transformer of annotation, transformers are used to alert annotation data based on member. <br/>
     * Like empty name in {@link Named} annotation can be replaced with member name.
     *
     * @param type
     *         class of annotation.
     * @param transformer
     *         transformer of annotation.
     * @param <T>
     *         type of annotation.
     */
    <T extends Annotation> void addTransformer(Class<T> type, AnnotationTransformer<T> transformer);

    /**
     * Start binding to given class, invokes {@link #bindToClass(Class, boolean)} with true as exact parameter.
     *
     * @param clazz
     *         class to bind.
     * @param <T>
     *         bind type.
     *
     * @return binder instance.
     *
     * @see #bindToClass(Class, boolean)
     * @see #bindToClass(Predicate)
     */
    default <T> Binder<T> bindToClass(Class<T> clazz)
    {
        return this.bindToClass(clazz, true);
    }

    /**
     * Start binding to given class, if exact flag is false binder with be created for all assignable types.
     *
     * @param clazz
     *         class to bind.
     * @param exact
     *         exact flag, see description above.
     * @param <T>
     *         bind type.
     *
     * @return binder instance.
     *
     * @see #bindToClass(Predicate)
     */
    default <T> Binder<T> bindToClass(Class<T> clazz, boolean exact)
    {
        if (exact)
        {
            return this.bindToClass(c -> c.equals(clazz));
        }
        return this.bindToClass(c -> c.isAssignableFrom(clazz));
    }

    /**
     * Start binding for all classes that matches predicate.
     *
     * @param typePredicate
     *         class to bind predicate.
     * @param <T>
     *         bind type.
     *
     * @return binder instance.
     */
    <T> Binder<T> bindToClass(Predicate<Class<?>> typePredicate);

    /**
     * Register class in controller. <br/>
     * Only for usage of redefined classes.
     *
     * @param clazz
     *         class to register.
     */
    void addClassData(Class<?> clazz);

    /**
     * Rebind all registered values.
     */
    void rebind();

    /**
     * Returns provider that always returns null.
     *
     * @param <T>
     *         tyoe of provider.
     *
     * @return provider that always returns null.
     */
    @SuppressWarnings("unchecked")
    static <T> Provider<T> getNullProvider()
    {
        return InjectionController.NULL_PROVIDER;
    }

    /**
     * Returns provider that always returns null.
     *
     * @param <T>
     *         tyoe of provider.
     *
     * @return provider that always returns null.
     */
    @SuppressWarnings("unchecked")
    static <T> DynamicProvider<T> getNullDynamicProvider()
    {
        return InjectionController.NULL_DYNAMIC_PROVIDER;
    }
}
