/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.event;

import javax.annotation.Nullable;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.diorite.commons.arrays.DioriteArrayUtils;

import net.engio.mbassy.common.IPredicate;
import net.engio.mbassy.common.ReflectionUtils;
import net.engio.mbassy.dispatch.el.ElFilter;
import net.engio.mbassy.listener.Enveloped;
import net.engio.mbassy.listener.Filter;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.IMessageFilter;
import net.engio.mbassy.listener.MessageHandler;
import net.engio.mbassy.listener.MessageHandler.Properties;
import net.engio.mbassy.listener.MessageListener;
import net.engio.mbassy.listener.MetadataReader;
import net.engio.mbassy.listener.Synchronized;
import net.engio.mbassy.subscription.MessageEnvelope;

@SuppressWarnings({"rawtypes", "unchecked"})
class EventHandlerMetadataReader extends MetadataReader
{
    //  This predicate is used to find all message listeners (methods annotated with @EventHandler)
    private static final IPredicate<Method> AllMessageHandlers = target -> ReflectionUtils.getAnnotation(target, EventHandler.class) != null;

    // cache already created filter instances
    private final Map<Class<? extends IMessageFilter>, IMessageFilter> filterCache = new HashMap<>(10);

    // retrieve all instances of filters associated with the given subscription
    private IMessageFilter[] getFilter(Filter[] subFilters)
    {
        if (subFilters.length == 0)
        {
            return DioriteArrayUtils.getEmptyObjectArray(IMessageFilter.class);
        }
        IMessageFilter[] filters = new IMessageFilter[subFilters.length];
        int i = 0;
        for (Filter filterDef : subFilters)
        {
            IMessageFilter filter = this.filterCache.get(filterDef.value());
            if (filter == null)
            {
                try
                {
                    filter = filterDef.value().newInstance();
                    this.filterCache.put(filterDef.value(), filter);
                }
                catch (Exception e)
                {
                    throw new RuntimeException(e);// propagate as runtime exception
                }
            }
            filters[i] = filter;
            i++;
        }
        return filters;
    }

    // get all listeners defined by the given class (includes
    // listeners defined in super classes)
    @Override
    public MessageListener getMessageListener(Class target)
    {
        MessageListener listenerMetadata = new MessageListener(target);
        // get all handlers (this will include all (inherited) methods directly annotated using @EventHandler)
        Method[] allHandlers = ReflectionUtils.getMethods(AllMessageHandlers, target);
        int length = allHandlers.length;

        Method handler;
        for (Method allHandler : allHandlers)
        {
            handler = allHandler;

            // retain only those that are at the bottom of their respective class hierarchy (deepest overriding method)
            if (! ReflectionUtils.containsOverridingMethod(allHandlers, handler))
            {

                // for each handler there will be no overriding method that specifies @EventHandler annotation
                // but an overriding method does inherit the listener configuration of the overwritten method

                EventHandler handlerConfig = ReflectionUtils.getAnnotation(handler, EventHandler.class);
                Handler handlerConfigRaw = ReflectionUtils.getAnnotation(handler, Handler.class);
                if (! handlerConfig.enabled() || ! this.isValidMessageHandler(handler))
                {
                    continue; // disabled or invalid listeners are ignored
                }
                IMessageFilter[] filters = this.getFilter(handlerConfig.filters());
                IMessageFilter[] rawFilters = this.getFilter(handlerConfigRaw.filters());
                IMessageFilter[] join = DioriteArrayUtils.join(rawFilters, filters);

                Method overriddenHandler = ReflectionUtils.getOverridingMethod(handler, target);
                // if a handler is overwritten it inherits the configuration of its parent method
                Map<String, Object> handlerProperties =
                        readProperties((overriddenHandler == null) ? handler : overriddenHandler, handlerConfig, join, listenerMetadata);
                EventMessageHandler handlerMetadata = new EventMessageHandler(handlerProperties);
                listenerMetadata.addHandler(handlerMetadata);
            }
        }

        return listenerMetadata;
    }

    /**
     * Create the property map for the {@link MessageHandler} constructor using the default objects.
     *
     * @param handler
     *         The handler annotated method of the listener
     * @param handlerConfig
     *         The annotation that configures the handler
     * @param filter
     *         The set of preconfigured filters if any
     * @param listenerConfig
     *         The listener metadata
     *
     * @return A map of properties initialized from the given parameters that will conform to the requirements of the {@link MessageHandler} constructor.
     */
    private static Map<String, Object> readProperties(@Nullable Method handler, EventHandler handlerConfig, @Nullable IMessageFilter[] filter,
                                                      MessageListener listenerConfig)
    {
        if (handler == null)
        {
            throw new IllegalArgumentException("The message handler configuration may not be null");
        }
        if (filter == null)
        {
            filter = DioriteArrayUtils.getEmptyObjectArray(IMessageFilter.class);
        }
        Enveloped enveloped = ReflectionUtils.getAnnotation(handler, Enveloped.class);
        Class[] handledMessages = (enveloped != null) ? enveloped.messages() : handler.getParameterTypes();
        handler.setAccessible(true);
        Map<String, Object> properties = new HashMap<>(11);
        properties.put(Properties.HandlerMethod, handler);
        // add EL filter if a condition is present
        if (! handlerConfig.condition().isEmpty())
        {
            if (! ElFilter.isELAvailable())
            {
                throw new IllegalStateException("A handler uses an EL filter but no EL implementation is available.");
            }

            IMessageFilter[] expandedFilter = new IMessageFilter[filter.length + 1];
            System.arraycopy(filter, 0, expandedFilter, 0, filter.length);
            expandedFilter[filter.length] = new ElFilter();
            filter = expandedFilter;
        }
        properties.put(Properties.Filter, filter);
        properties.put(Properties.Condition, cleanEL(handlerConfig.condition()));
        properties.put(Properties.Priority, handlerConfig.priority());
        properties.put(Properties.Invocation, handlerConfig.invocation());
        properties.put(Properties.InvocationMode, handlerConfig.delivery());
        properties.put(Properties.Enveloped, enveloped != null);
        properties.put(Properties.AcceptSubtypes, ! handlerConfig.rejectSubtypes());
        properties.put(Properties.Listener, listenerConfig);
        properties.put(Properties.IsSynchronized, ReflectionUtils.getAnnotation(handler, Synchronized.class) != null);
        properties.put(Properties.HandledMessages, handledMessages);
        return properties;
    }

    private static String cleanEL(String expression)
    {
        if (! expression.trim().startsWith("${") && ! expression.trim().startsWith("#{"))
        {
            expression = "${" + expression + "}";
        }
        return expression;
    }

    private boolean isValidMessageHandler(@Nullable Method handler)
    {
        if ((handler == null) ||
            ((ReflectionUtils.getAnnotation(handler, EventHandler.class) == null) && (ReflectionUtils.getAnnotation(handler, Handler.class) == null)))
        {
            return false;
        }
        if (handler.getParameterTypes().length != 1)
        {
            // a messageHandler only defines one parameter (the message)
            System.out.println(
                    "Found no or more than one parameter in messageHandler [" + handler.getName() + "]. A messageHandler must define exactly one parameter");
            return false;
        }
        Enveloped envelope = ReflectionUtils.getAnnotation(handler, Enveloped.class);
        if ((envelope != null) && ! MessageEnvelope.class.isAssignableFrom(handler.getParameterTypes()[0]))
        {
            System.out.println("Message envelope configured but no subclass of MessageEnvelope found as parameter");
            return false;
        }
        if ((envelope != null) && (envelope.messages().length == 0))
        {
            System.out.println("Message envelope configured but message types defined for handler");
            return false;
        }
        return true;
    }
}