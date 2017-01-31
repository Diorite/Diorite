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

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.diorite.commons.objects.Cancellable;

import net.engio.mbassy.dispatch.HandlerInvocation;
import net.engio.mbassy.dispatch.ReflectiveHandlerInvocation;
import net.engio.mbassy.listener.Filter;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;
import net.engio.mbassy.listener.Synchronized;

/**
 * Event listener method annotation, each listener method should be annotated with with handler.
 */
@SuppressWarnings("rawtypes")
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Handler(filters = @Filter(EventSystemMessageFilter.class))
@Synchronized
@Target(value = {ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface EventHandler
{
    /**
     * If event is instance of {@link Cancellable} handler can skip already cancelled events.
     */
    boolean ignoreCancelled() default false;

    /**
     * Add any numbers of filters to the handler. All filters are evaluated before the handler
     * is actually invoked, which is only if all the filters accept the message.
     */
    Filter[] filters() default {};

    /**
     * Defines a filter condition as Expression Language. This can be used to filter the events based on
     * attributes of the event object. Note that the expression must resolve to either
     * <code>true</code> to allow the event or <code>false</code> to block it from delivery to the handler.
     * The message itself is available as "msg" variable.
     *
     * @return the condition in EL syntax.
     */
    String condition() default "";

    /**
     * Define the mode in which a message is delivered to each listener. Listeners can be notified
     * sequentially or concurrently.
     */
    Invoke delivery() default Invoke.Synchronously;

    /**
     * Handlers are ordered by priority and handlers with higher priority are processed before
     * those with lower priority, i.e. Influence the order in which different handlers that consume
     * the same message type are invoked.
     */
    int priority() default 0;

    /**
     * Define whether or not the handler accepts sub types of the message type it declares in its
     * signature.
     */
    boolean rejectSubtypes() default false;

    /**
     * Enable or disable the handler. Disabled handlers do not receive any messages.
     * This property is useful for quick changes in configuration and necessary to disable
     * handlers that have been declared by a superclass but do not apply to the subclass
     */
    boolean enabled() default true;

    /**
     * Each handler call is implemented as an invocation object that implements the invocation mechanism.
     * The basic implementation uses reflection and is the default. It is possible though to provide a custom
     * invocation to add additional logic.
     *
     * Note: Providing a custom invocation will most likely reduce performance, since the JIT-Compiler
     * can not do some of its sophisticated byte code optimizations.
     */
    Class<? extends HandlerInvocation> invocation() default ReflectiveHandlerInvocation.class;

}
