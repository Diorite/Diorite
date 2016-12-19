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

package org.diorite.inject.impl.controller;

import javax.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.diorite.inject.binder.DynamicProvider;
import org.diorite.inject.binder.qualifier.QualifierData;
import org.diorite.inject.impl.data.InjectValueData;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.method.ParameterList;

final class BinderToConstructor<T> implements DynamicProvider<T>
{
    private final Controller controller;

    final List<ControllerInjectValueData<?>> params;
    final Function<Object, T>                constructor;

    private final AtomicBoolean lazyInit = new AtomicBoolean(false);

    @SuppressWarnings({"unchecked", "rawtypes"})
    BinderToConstructor(Controller controller, Class<? extends T> type, Constructor<?> constructor)
    {
        this.controller = controller;
        Constructor<? extends T> constructor1 = (Constructor<? extends T>) constructor;
        constructor1.setAccessible(true);

        MethodDescription.ForLoadedConstructor constructorDesc = new MethodDescription.ForLoadedConstructor(constructor);
        Map<Class<? extends Annotation>, ? extends Annotation> scopeMap = controller.extractRawScopeAnnotations(constructorDesc);
        Map<Class<? extends Annotation>, ? extends Annotation> qualifierMap = controller.extractRawQualifierAnnotations(constructorDesc);

        int parameterCount = constructor.getParameterCount();
        ArrayList<ControllerInjectValueData<?>> params = new ArrayList<>(parameterCount);
        ParameterList<ParameterDescription.InDefinedShape> parameters = constructorDesc.getParameters();
        for (int i = 0; i < parameterCount; i++)
        {
            ParameterDescription.InDefinedShape param = parameters.get(i);
            ControllerInjectValueData<Object> value = controller.createValue(- 1, constructorDesc.getDeclaringType(), param.getType(), param,
                                                                             param.getName(), scopeMap, qualifierMap);
            params.add(value);
        }
        this.params = List.of(params.toArray(new ControllerInjectValueData[parameterCount]));

        this.constructor = $this ->
        {
            Object[] args = new Object[parameterCount];
            for (int i = 0; i < parameterCount; i++)
            {
                args[i] = this.params.get(i).tryToGet($this);
            }
            try
            {
                return constructor1.newInstance(args);
            }
            catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
            {
                throw new RuntimeException("Can't bind value to " + constructor1.getDeclaringClass().getName() + " constructor. " +
                                           Arrays.toString(constructor1.getParameterTypes()), e);
            }
        };
    }

    @Override
    public Collection<? extends InjectValueData<?, ?>> getInjectValues()
    {
        return this.params;
    }

    @Nullable
    @Override
    public T tryToGet(Object object, QualifierData data)
    {
        if (! this.lazyInit.getAndSet(true))
        {
            for (ControllerInjectValueData<?> param : this.params)
            {
                this.controller.findBinder(param);
            }
        }
        return this.constructor.apply(object);
    }

}
