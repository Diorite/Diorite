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

import javax.annotation.Nullable;
import javax.inject.Provider;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.diorite.inject.binder.Binder;
import org.diorite.inject.binder.DynamicProvider;
import org.diorite.inject.binder.qualifier.AnnotationTransformer;
import org.diorite.inject.data.ClassData;
import org.diorite.inject.data.InjectValueData;

public abstract class InjectionController<MEMBER, TYPE, GENERIC>
{
    @SuppressWarnings("rawtypes")
    public static final Provider        NULL_PROVIDER         = () -> null;
    @SuppressWarnings("rawtypes")
    public static final DynamicProvider NULL_DYNAMIC_PROVIDER = (a, b) -> null;
    public static final String          CONSTRUCTOR_NAME      = "<init>";
    public static final String          STATIC_BLOCK_NAME     = "<clinit>";

    // scope methods

    public abstract <T, S extends Annotation> Supplier<ScopeHandler<T, S>> registerScopeHandler(Class<S> scope, Supplier<ScopeHandler<T, S>> handler);

    public abstract <T> void applyScopes(InjectValueData<T, GENERIC> data);

    public abstract <T, S extends Annotation> DynamicProvider<T> applyScope(S scope, DynamicProvider<T> provider);

    // transformer methods

    public abstract <T extends Annotation> void addTransformer(Class<T> type, AnnotationTransformer<T, MEMBER, TYPE> transformer);

    public abstract <T extends Annotation> T transform(T annotation, AnnotatedMemberData<MEMBER, TYPE> memberData);

    // inject methods

    @Nullable
    protected abstract <T> T getInjectedField(Object $this, int type, int field);

    @Nullable
    protected abstract <T> T getInjectedMethod(Object $this, int type, int method, int argument);

    public abstract boolean isInjectElement(MEMBER element);

    protected abstract void extractQualifierAnnotations(ShortcutInject shortcutAnnotation, Annotation element, Consumer<Annotation> addFunc);

    protected abstract Map<Class<? extends Annotation>, ? extends Annotation> extractRawQualifierAnnotations(MEMBER element);

    protected abstract Map<Class<? extends Annotation>, ? extends Annotation> extractRawScopeAnnotations(MEMBER element);

    protected abstract Map<Class<? extends Annotation>, ? extends Annotation> transformAll(TYPE classType, String name, MEMBER member,
                                                                                           Map<Class<? extends Annotation>, ? extends Annotation> raw);

    // class data methods

    @Nullable
    protected abstract ClassData<GENERIC> generateMemberData(TYPE typeDescription);

    @Nullable
    public abstract ClassData<GENERIC> getClassData(Class<?> type);

    @Nullable
    public abstract ClassData<GENERIC> getClassData(TYPE type);

    protected abstract ClassData<GENERIC> addClassData(TYPE typeDescription, ClassData<GENERIC> classData);

    @Nullable
    protected abstract ClassData<GENERIC> addClassData(TYPE typeDescription);

    // binder methods

    public abstract <T> Binder<T> bindToClass(Class<T> clazz);

    public abstract <T> Binder<T> bindToClass(Class<T> clazz, boolean exact);

    public abstract <T> Binder<T> bindToClass(Predicate<Class<?>> typePredicate);

    public abstract void rebind();

    // util methods

    @SuppressWarnings("unchecked")
    public static <T> Provider<T> getNullProvider()
    {
        return NULL_PROVIDER;
    }

    @SuppressWarnings("unchecked")
    public static <T> DynamicProvider<T> getNullDynamicProvider()
    {
        return NULL_DYNAMIC_PROVIDER;
    }
}
