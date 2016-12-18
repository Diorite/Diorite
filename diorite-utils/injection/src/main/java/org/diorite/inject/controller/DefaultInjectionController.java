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

package org.diorite.inject.controller;

import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.diorite.inject.AfterInject;
import org.diorite.inject.BeforeInject;
import org.diorite.inject.DelegatedQualifier;
import org.diorite.inject.Inject;
import org.diorite.inject.InjectionControllerBasic;
import org.diorite.inject.NamedInject;
import org.diorite.inject.Provider;
import org.diorite.inject.Qualifier;
import org.diorite.inject.Qualifiers;
import org.diorite.inject.Scope;
import org.diorite.inject.ShortcutInject;
import org.diorite.inject.binder.Binder;
import org.diorite.unsafe.ByteBuddyUtils;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.annotation.AnnotatedCodeElement;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.field.FieldDescription.InDefinedShape;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeDescription.ForLoadedType;
import net.bytebuddy.description.type.TypeDescription.Generic;

public final class DefaultInjectionController extends InjectionControllerBasic<AnnotatedCodeElement, ForLoadedType, Generic>
{
    static final TypeDescription.ForLoadedType INJECT              = new TypeDescription.ForLoadedType(Inject.class);
    static final TypeDescription.ForLoadedType PROVIDER            = new TypeDescription.ForLoadedType(Provider.class);
    static final TypeDescription.ForLoadedType SHORTCUT_INJECT     = new TypeDescription.ForLoadedType(ShortcutInject.class);
    static final TypeDescription.ForLoadedType DELEGATED_QUALIFIER = new TypeDescription.ForLoadedType(DelegatedQualifier.class);
    static final TypeDescription.ForLoadedType QUALIFIER           = new TypeDescription.ForLoadedType(Qualifier.class);
    static final TypeDescription.ForLoadedType SCOPE               = new TypeDescription.ForLoadedType(Scope.class);
    static final TypeDescription.ForLoadedType AFTER               = new TypeDescription.ForLoadedType(AfterInject.class);
    static final TypeDescription.ForLoadedType BEFORE              = new TypeDescription.ForLoadedType(BeforeInject.class);

    final         Collection<BindValueData> bindValues  = new ConcurrentLinkedQueue<>();
    private final Set<Class<?>>             transformed = new HashSet<>(100);

    public DefaultInjectionController()
    {
        ByteBuddyAgent.getInstrumentation().addTransformer(new InjectedClassTransformer(this, ByteBuddyAgent.getInstrumentation()), true);
    }

    static String fixName(TypeDescription.Generic type, String currentName)
    {
        if (type.asErasure().equals(PROVIDER))
        {
            String name = currentName.toLowerCase();
            int providerIndex = name.indexOf("provider");
            if (providerIndex != - 1)
            {
                name = currentName.substring(0, providerIndex);
            }
            else
            {
                name = currentName;
            }
            return name;
        }
        else
        {
            return currentName;
        }
    }

    @Override
    public ClassData addClassData(TypeDescription.ForLoadedType typeDescription, org.diorite.inject.data.ClassData<TypeDescription.ForLoadedType.Generic> classData)
    {
        if (! (classData instanceof ClassData))
        {
            throw new IllegalArgumentException("Unsupported class data for this controller");
        }
        this.map.put(typeDescription, classData);
        Lock lock = this.lock.writeLock();
        try
        {
            lock.lock();
            ((ClassData) classData).setIndex(this.dataList.size());
            this.dataList.add(classData);
        }
        finally
        {
            lock.unlock();
        }
        try
        {
            ByteBuddyAgent.getInstrumentation().retransformClasses(ByteBuddyUtils.toClass(typeDescription));
        }
        catch (Exception e)
        {
            throw new GeneratedCodeError(e);
        }
        return (ClassData) classData;
    }

    @Override
    protected ClassData generateMemberData(TypeDescription.ForLoadedType typeDescription)
    {
        boolean inject = false;
        Collection<MemberData<?>> members = new LinkedList<>();
        Map<String, Collection<MemberData<?>>> membersMap = new HashMap<>(2);
        if (this.isInjectElement(typeDescription))
        {
            inject = true;
        }
        else
        {
            int index = 0;
            for (InDefinedShape fieldDescription : typeDescription.getDeclaredFields())
            {
                if (this.isInjectElement(fieldDescription))
                {
                    String name = fixName(fieldDescription.getType(), fieldDescription.getName());
                    FieldData<Object> fieldData = new FieldData<>(this, typeDescription, fieldDescription, name, index++);
                    members.add(fieldData);
                    inject = true;
                    Collection<MemberData<?>> memberData = membersMap.computeIfAbsent(fieldDescription.getName(), k -> new HashSet<>(2));
                    memberData.add(fieldData);
                }
            }
            for (MethodDescription.InDefinedShape methodDescription : typeDescription.getDeclaredMethods())
            {
                if (this.isInjectElement(methodDescription))
                {
                    String name;
                    {
                        String name_ = fixName(methodDescription.getReturnType(), methodDescription.getName());
                        if (name_.startsWith("inject"))
                        {
                            name_ = name_.substring("inject".length());
                            char c = Character.toLowerCase(name_.charAt(0));
                            name_ = c + name_.substring(1);
                        }
                        name = name_;
                    }
                    MethodData methodData = new MethodData(this, typeDescription, methodDescription, name, index++);
                    members.add(methodData);
                    inject = true;
                    Collection<MemberData<?>> memberData = membersMap.computeIfAbsent(methodDescription.getName(), k -> new HashSet<>(2));
                    memberData.add(methodData);
                }
            }
        }
        if (! inject)
        {
            return null;
        }
        ClassData classData = new ClassData(typeDescription, members.toArray(new MemberData<?>[members.size()]));

        for (MethodDescription.InDefinedShape methodDescription : typeDescription.getDeclaredMethods())
        {
            String name = methodDescription.getName();
            Annotation[] annotations = ByteBuddyUtils.getAnnotations(methodDescription, RetentionPolicy.RUNTIME);
            for (Annotation annotation : annotations)
            {
                String[] value;
                boolean after;
                if (annotation instanceof BeforeInject)
                {
                    value = ((BeforeInject) annotation).value();
                    after = false;
                }
                else if (annotation instanceof AfterInject)
                {
                    value = ((AfterInject) annotation).value();
                    after = true;
                }
                else
                {
                    continue;
                }
                if (value.length == 0)
                {
                    if (after)
                    {
                        classData.addAfter(name);
                    }
                    else
                    {
                        classData.addBefore(name);
                    }
                    continue;
                }
                for (String s : value)
                {
                    Collection<MemberData<?>> memberData = membersMap.get(s);
                    if (memberData != null)
                    {
                        for (MemberData<?> data : memberData)
                        {
                            if (after)
                            {
                                data.addAfter(name);
                            }
                            else
                            {
                                data.addBefore(name);
                            }
                        }
                    }
                }
            }
        }
        return classData;
    }

    private boolean isAnyInjectElement(AnnotatedElement[] element)
    {
        for (AnnotatedElement field : element)
        {
            if (this.isInjectElement(field))
            {
                return true;
            }
        }
        return false;
    }

//    public void ensureTransformed(TypeDescription type) throws Exception
//    {
//        ByteBuddyAgent.getInstrumentation().retransformClasses(ByteBuddyUtils.toClass(type));
//    }

    @Override
    public void rebind()
    {
        Lock writeLock = this.lock.writeLock();
        try
        {
            writeLock.lock();
            for (org.diorite.inject.data.ClassData<TypeDescription.ForLoadedType.Generic> classData : this.dataList)
            {
                Collection<org.diorite.inject.data.InjectValueData<?, Generic>> allData = new ArrayList<>(100);
                for (org.diorite.inject.data.MemberData<TypeDescription.ForLoadedType.Generic> fieldData : classData.getMembers())
                {
                    allData.addAll(fieldData.getInjectValues());
                }
                for (org.diorite.inject.data.InjectValueData<?, TypeDescription.ForLoadedType.Generic> valueData : allData)
                {
                    Iterator<BindValueData> iterator = this.bindValues.iterator();
                    BindValueData best = null;
                    while (iterator.hasNext())
                    {
                        BindValueData data = iterator.next();
                        if (! data.isCompatible(valueData))
                        {
                            continue;
                        }
                        if ((best == null) || (best.compareTo(data) > 0))
                        {
                            best = data;
                        }
                    }
                    if (best == null)
                    {
                        valueData.setProvider(null);
                    }
                    else
                    {
                        valueData.setProvider(best.getProvider());
                    }
                    this.applyScopes(valueData);
                }
            }
        }
        finally
        {
            writeLock.unlock();
        }
    }

//    protected final <T> void rebindField(ForLoadedType type, String name, Provider<T> provider)
//    {
//        org.diorite.di.data.ClassData classData = this.map.get(type);
//        org.diorite.di.data.ClassData.FieldData<T> field = classData.getField(name);
//        if (field != null)
//        {
//            field.getValueData().setProvider(provider);
//        }
//    }
//
//    protected final <T> void rebindMethod(ForLoadedType type, String name, String param, Provider<T> provider)
//    {
//        org.diorite.di.data.ClassData classData = this.map.get(type);
//        org.diorite.di.data.ClassData.MethodData method = classData.getMethod(name);
//        if (method == null)
//        {
//            return;
//        }
//        org.diorite.di.data.ClassData.InjectValueData<T> valueData = method.getValueData(param);
//        if (valueData != null)
//        {
//            valueData.setProvider(provider);
//        }
//    }

    @Override
    public org.diorite.inject.data.ClassData<TypeDescription.ForLoadedType.Generic> getClassData(Class<?> type)
    {
        return this.getClassData(new ForLoadedType(type));
    }

    @Override
    protected final <T> T getInjectedField(Object $this, int type, int field)
    {
        org.diorite.inject.data.ClassData<TypeDescription.ForLoadedType.Generic> classData;
        Lock lock = this.lock.readLock();
        try
        {
            lock.lock();
            classData = this.dataList.get(type);
        }
        catch (Exception e)
        {
            new RuntimeException("Can't find (missing type) to inject. (type: " + type + ", field: " + field + "), null returned instead.", e)
                    .printStackTrace();
            return null;
        }
        finally
        {
            lock.unlock();
        }
        org.diorite.inject.data.FieldData<T, TypeDescription.ForLoadedType.Generic> fieldData = null;
        try
        {
            fieldData = classData.getField(field);
            return fieldData.getValueData().tryToGet($this);
        }
        catch (Exception e)
        {
            new RuntimeException("Can't find value (missing/invalid field) to inject. (type: " + type + ", field: " + field + ", classData: " + classData +
                                 ", fieldData: " + fieldData + "), null returned instead.", e).printStackTrace();
            return null;
        }
    }

    @Override
    protected final <T> T getInjectedMethod(Object $this, int type, int method, int argument)
    {
        org.diorite.inject.data.ClassData<TypeDescription.ForLoadedType.Generic> classData;
        Lock lock = this.lock.readLock();
        try
        {
            lock.lock();
            classData = this.dataList.get(type);
        }
        catch (Exception e)
        {
            new RuntimeException("Can't find value (missing type) to inject. (type: " + type + ", method: " + method + ", argument: " + argument +
                                 "), null returned instead.", e)
                    .printStackTrace();
            return null;
        }
        finally
        {
            lock.unlock();
        }
        org.diorite.inject.data.MethodData<TypeDescription.ForLoadedType.Generic> methodData;
        try
        {
            methodData = classData.getMethod(method);
        }
        catch (Exception e)
        {
            new RuntimeException("Can't find value (missing method) to inject. (type: " + type + ", method: " + method + ", argument: " + argument +
                                 "), null returned instead.", e)
                    .printStackTrace();
            return null;
        }
        org.diorite.inject.data.InjectValueData<T, TypeDescription.ForLoadedType.Generic> valueData = null;
        try
        {
            valueData = methodData.getValueData(argument);
            return valueData.tryToGet($this);
        }
        catch (Exception e)
        {
            new RuntimeException("Can't find value (missing/invalid argument) to inject. (type: " + type + ", method: " + method + ", argument: " + argument +
                                 ", classData: " + classData + ", methodData: " + methodData + ", valueData: " + valueData + "), null returned instead.", e)
                    .printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isInjectElement(AnnotatedCodeElement element)
    {
        for (AnnotationDescription annotation : ByteBuddyUtils.getAnnotationList(element))
        {
            TypeDescription annotationType = annotation.getAnnotationType();
            if (annotationType.equals(INJECT))
            {
                return true;
            }
            if (annotationType.getInheritedAnnotations().isAnnotationPresent(SHORTCUT_INJECT))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isInjectElement(AnnotatedElement element)
    {
        for (Annotation annotation : element.getAnnotations())
        {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.equals(NamedInject.class))
            {
                return true;
            }
            if (annotationType.isAnnotationPresent(ShortcutInject.class))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void extractQualifierAnnotations(ShortcutInject shortcutAnnotation, Annotation element, Consumer<Annotation> addFunc)
    {
//        Set<Class<? extends Annotation>> supported = Set.of(shortcutAnnotation.value());
        Method[] declaredMethods = element.annotationType().getDeclaredMethods();
        Map<Class<? extends Annotation>, Map<String, Entry<Method, Object>>> dataMap = new HashMap<>(declaredMethods.length);
        for (Method declaredMethod : declaredMethods)
        {
            DelegatedQualifier delegatedQualifier = declaredMethod.getAnnotation(DelegatedQualifier.class);
            if (delegatedQualifier == null)
            {
                continue;
            }
//            if (! supported.contains(delegatedQualifier.value()))
//            {
//                continue;
//            }
            Map<String, Entry<Method, Object>> map = dataMap.computeIfAbsent(delegatedQualifier.value(), k -> new HashMap<>(3));
            try
            {
                map.put(delegatedQualifier.method(), Map.entry(declaredMethod, declaredMethod.invoke(element)));
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        for (Entry<Class<? extends Annotation>, Map<String, Entry<Method, Object>>> entry : dataMap.entrySet())
        {
            Class<? extends Annotation> key = entry.getKey();
            Map<String, Entry<Method, Object>> value = entry.getValue();

            Annotation annotation;
            if (value.size() == 1)
            {
                Entry<String, Entry<Method, Object>> next = value.entrySet().iterator().next();
                if (next.getKey().isEmpty())
                {
                    if (key.getDeclaredMethods().length == 1)
                    {
                        annotation = Qualifiers.of(key, next.getValue().getValue());
                    }
                    else
                    {
                        annotation = Qualifiers.of(key, next.getValue().getKey().getName(), next.getValue().getValue());
                    }
                }
                else
                {
                    annotation = Qualifiers.of(key, next.getKey(), next.getValue().getValue());
                }
            }
            else
            {
                Map<String, Object> data = new HashMap<>(value.size());
                for (Entry<String, Entry<Method, Object>> entryEntry : value.entrySet())
                {
                    data.put(entryEntry.getKey(), entryEntry.getValue().getValue());
                }
                annotation = Qualifiers.of(key, data);
            }
            addFunc.accept(annotation);
        }
    }

    @Override
    public Map<Class<? extends Annotation>, ? extends Annotation> extractRawQualifierAnnotations(AnnotatedCodeElement element)
    {
        Annotation[] annotations = ByteBuddyUtils.getAnnotations(element, RetentionPolicy.RUNTIME);
        Map<Class<? extends Annotation>, Annotation> resultMap = new HashMap<>(annotations.length + 1);
        for (Annotation annotation : annotations)
        {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.isAnnotationPresent(Qualifier.class))
            {
                if (resultMap.containsKey(annotationType))
                {
                    throw new IllegalStateException("Duplicated qualifier! Found: " + annotation + ", others: " + resultMap);
                }
                resultMap.put(annotationType, annotation);
            }
            if (annotationType.isAnnotationPresent(ShortcutInject.class))
            {
                this.extractQualifierAnnotations(annotationType.getAnnotation(ShortcutInject.class), annotation, r ->
                {
                    Class<? extends Annotation> type = r.annotationType();
                    if (resultMap.containsKey(type))
                    {
                        throw new IllegalStateException("Duplicated qualifier! Found: " + r + ", others: " + resultMap);
                    }
                    resultMap.put(type, r);
                });

            }
        }
        return resultMap;
    }

    @Override
    public Map<Class<? extends Annotation>, ? extends Annotation> extractRawScopeAnnotations(AnnotatedCodeElement element)
    {
        Annotation[] annotations = ByteBuddyUtils.getAnnotations(element, RetentionPolicy.RUNTIME);
        Map<Class<? extends Annotation>, Annotation> resultMap = new HashMap<>(annotations.length + 1);
        for (Annotation annotation : annotations)
        {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.isAnnotationPresent(Scope.class))
            {
                if (resultMap.containsKey(annotationType))
                {
                    throw new IllegalStateException("Duplicated scope! Found: " + annotation + ", others: " + resultMap);
                }
                resultMap.put(annotationType, annotation);
            }
        }
        return resultMap;
    }

    @Override
    public <T> Binder<T> bindToClass(Class<T> clazz)
    {
        return this.bindToClass(clazz, true);
    }

    @Override
    public <T> Binder<T> bindToClass(Class<T> clazz, boolean exact)
    {
        if (exact)
        {
            return this.bindToClass(c -> c.equals(clazz));
        }
        return this.bindToClass(c -> c.isAssignableFrom(clazz));
    }

    @Override
    public <T> Binder<T> bindToClass(Predicate<Class<?>> typePredicate)
    {
        return new SimpleBinderInstance<>(this, type ->
        {
            try
            {
                return typePredicate.test(Class.forName(type.asErasure().getActualName()));
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
                return false;
            }
        });
    }

    public <T> Binder<T> bindToType(Predicate<Generic> typePredicate)
    {
        return new SimpleBinderInstance<>(this, typePredicate);
    }

    @Override
    protected Map<Class<? extends Annotation>, ? extends Annotation> transformAll(TypeDescription.ForLoadedType classType, String name,
                                                                                  AnnotatedCodeElement member,
                                                                                  Map<Class<? extends Annotation>, ? extends Annotation> raw)
    {
        Map<Class<? extends Annotation>, Annotation> scopeAnnotations = new HashMap<>(raw.size());
        for (Entry<Class<? extends Annotation>, ? extends Annotation> entry : raw.entrySet())
        {
            Annotation value = this.transform(entry.getValue(), new MemberDataBuilderImpl<>(classType, name, member, raw));
            scopeAnnotations.put(entry.getKey(), value);
        }
        return scopeAnnotations;
    }

    <T, B extends AnnotatedCodeElement & NamedElement.WithRuntimeName> InjectValueData<T> createValue
            (int index, TypeDescription.ForLoadedType classType, TypeDescription.ForLoadedType.Generic type, B member, String name,
             Map<Class<? extends Annotation>, ? extends Annotation> parentRawScopeAnnotations,
             Map<Class<? extends Annotation>, ? extends Annotation> parentRawQualifierAnnotations)
    {
        Map<Class<? extends Annotation>, ? extends Annotation> scopeAnnotations;
        {
            Map<Class<? extends Annotation>, ? extends Annotation> memberRawScopeAnnotations = this.extractRawScopeAnnotations(member);
            Map<Class<? extends Annotation>, Annotation> rawScopeAnnotations = new HashMap<>(parentRawScopeAnnotations);
            rawScopeAnnotations.putAll(memberRawScopeAnnotations);
            scopeAnnotations = this.transformAll(classType, name, member, rawScopeAnnotations);
        }
        Map<Class<? extends Annotation>, ? extends Annotation> qualifierAnnotations;
        {
            Map<Class<? extends Annotation>, ? extends Annotation> memberRawQualifierAnnotations = this.extractRawQualifierAnnotations(member);
            Map<Class<? extends Annotation>, Annotation> rawQualifierAnnotations = new HashMap<>(parentRawQualifierAnnotations);
            rawQualifierAnnotations.putAll(memberRawQualifierAnnotations);
            qualifierAnnotations = this.transformAll(classType, name, member, rawQualifierAnnotations);
        }
        return new InjectValueData<>(index, name, type, scopeAnnotations, qualifierAnnotations);
    }

}
