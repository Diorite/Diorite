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

import javax.inject.Named;
import javax.inject.Scope;
import javax.inject.Singleton;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

import org.diorite.inject.binder.DynamicProvider;
import org.diorite.inject.binder.qualifier.AnnotationTransformer;
import org.diorite.inject.data.ClassData;
import org.diorite.inject.data.InjectValueData;
import org.diorite.inject.scopes.SingletonScopeHandler;

public abstract class InjectionControllerBasic<MEMBER, TYPE, GENERIC> extends InjectionController<MEMBER, TYPE, GENERIC>
{
    static
    {
        // ensure loaded first:
        Class<InjectionLibrary> libraryClass = InjectionLibrary.class;
    }

    protected final Map<TYPE, ClassData<GENERIC>> map      = new ConcurrentHashMap<>(100);
    protected final List<ClassData<GENERIC>>      dataList = new ArrayList<>(20);

    protected final Map<Class<?>, Supplier<ScopeHandler<?, ?>>>                              scopeHandles = new ConcurrentHashMap<>(10);
    protected final Map<Class<? extends Annotation>, AnnotationTransformer<?, MEMBER, TYPE>> transformers = new ConcurrentHashMap<>(10);

    protected final ReadWriteLock lock = new ReentrantReadWriteLock();

    public InjectionControllerBasic()
    {
        this.addTransformer(Named.class, (memberData, named) -> named.value().isEmpty() ? Qualifiers.of(Named.class, memberData.getName()) : named);
        this.registerScopeHandler(Singleton.class, SingletonScopeHandler::new);
    }

    // scopes

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T, S extends Annotation> Supplier<ScopeHandler<T, S>> registerScopeHandler(Class<S> scope, Supplier<ScopeHandler<T, S>> handler)
    {
        if (! scope.isAnnotationPresent(Scope.class))
        {
            throw new IllegalArgumentException("Given annotation isn't a scope annotation: " + scope);
        }
        return (Supplier<ScopeHandler<T, S>>) this.scopeHandles.put(scope, (Supplier) handler);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> void applyScopes(InjectValueData<T, GENERIC> data)
    {
        Collection<? extends Annotation> scopes = data.getScopes().values();
        if (scopes.isEmpty())
        {
            return;
        }
        DynamicProvider<T> provider = data.getProvider();
        for (Annotation scope : scopes)
        {
            provider = this.applyScope(scope, provider);
        }
        data.setProvider(provider);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T, S extends Annotation> DynamicProvider<T> applyScope(S scope, DynamicProvider<T> provider)
    {
        Supplier<ScopeHandler<?, ?>> scopeHandler = this.scopeHandles.get(scope.annotationType());
        if (scopeHandler == null)
        {
            throw new IllegalArgumentException("Unknown scope given: " + scope + ", expected one of: " + this.scopeHandles.keySet());
        }
        return ((ScopeHandler) scopeHandler.get()).apply(provider, scope);
    }

    // transformers

    @Override
    public <T extends Annotation> void addTransformer(Class<T> type, AnnotationTransformer<T, MEMBER, TYPE> transformer)
    {
        this.transformers.put(type, transformer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Annotation> T transform(T annotation, AnnotatedMemberData<MEMBER, TYPE> memberData)
    {
        AnnotationTransformer<T, MEMBER, TYPE> transformer = (AnnotationTransformer<T, MEMBER, TYPE>) this.transformers.get(annotation.annotationType());
        if (transformer == null)
        {
            return annotation;
        }
        return transformer.apply(memberData, annotation);
    }

    // member data

    @Override
    public ClassData<GENERIC> getClassData(TYPE type)
    {
        return this.map.get(type);
    }

    @Override
    public ClassData<GENERIC> addClassData(TYPE typeDescription, ClassData<GENERIC> classData)
    {
        this.map.put(typeDescription, classData);
        Lock lock = this.lock.writeLock();
        try
        {
            lock.lock();
            this.dataList.add(classData);
        }
        finally
        {
            lock.unlock();
        }
        return classData;
    }

    @Override
    public ClassData<GENERIC> addClassData(TYPE typeDescription)
    {
        ClassData<GENERIC> classData = this.generateMemberData(typeDescription);
        if (classData == null)
        {
            return null;
        }
        return this.addClassData(typeDescription, classData);
    }

}
