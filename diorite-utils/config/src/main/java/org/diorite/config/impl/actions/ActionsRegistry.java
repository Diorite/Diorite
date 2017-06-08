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

package org.diorite.config.impl.actions;

import javax.annotation.Nullable;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import org.diorite.config.ActionMatcherResult;
import org.diorite.config.ConfigPropertyAction;
import org.diorite.config.impl.actions.collections.AddToCollectionPropertyAction;
import org.diorite.config.impl.actions.collections.ContainsInCollectionPropertyAction;
import org.diorite.config.impl.actions.collections.ContainsInCollectionPropertyNegatedAction;
import org.diorite.config.impl.actions.collections.GetFromCollectionPropertyAction;
import org.diorite.config.impl.actions.collections.IsEmptyCollectionPropertyAction;
import org.diorite.config.impl.actions.collections.RemoveFromCollectionIfPropertyAction;
import org.diorite.config.impl.actions.collections.RemoveFromCollectionIfPropertyNegatedAction;
import org.diorite.config.impl.actions.collections.RemoveFromCollectionPropertyAction;
import org.diorite.config.impl.actions.collections.SizeOfCollectionPropertyAction;

/**
 * Registry for actions.
 */
@SuppressWarnings("MagicNumber")
public final class ActionsRegistry
{
    private static final SortedSet<ConfigPropertyActionEntry> actions = new TreeSet<>();
    private static final ReentrantReadWriteLock               lock    = new ReentrantReadWriteLock();

    private ActionsRegistry()
    {
    }

    static
    {
        // @formatter:off
        registerAction(new NumericPropertyAction(
                "add", "+", "(?:add(?<property>[A-Z0-9].*))", "(?:increment(?<property>[A-Z0-9].*?)(?:By)?)"), 100);
        registerAction(new NumericPropertyAction(
                "subtract", "-", "(?:subtract(?:From)?(?<property>[A-Z0-9].*))", "(?:decrement(?<property>[A-Z0-9].*?)(?:By)?)"), 100);
        registerAction(new NumericPropertyAction(
                "multiple", "*", "(?:multiple|multi)(?<property>[A-Z0-9].*?)(?:By)?"), 100);
        registerAction(new NumericPropertyAction(
                "divide", "/", "(?:divide|div)(?<property>[A-Z0-9].*?)(?:By)?"), 100);
        registerAction(new NumericPropertyAction(
                "power", "**", "(?:power|pow)(?<property>[A-Z0-9].*?)(?:By)?"), 100);
        // @formatter:on

        registerAction(new GetPropertyAction(), 100);
        registerAction(new SetPropertyAction(), 100);
        registerAction(new EqualsPropertyAction(), 100);
        registerAction(new NotEqualsPropertyAction(), 100);
        registerAction(new AddToCollectionPropertyAction(), 100);
        registerAction(new ContainsInCollectionPropertyAction(), 100);
        registerAction(new ContainsInCollectionPropertyNegatedAction(), 100);
        registerAction(new RemoveFromCollectionIfPropertyAction(), 100);
        registerAction(new RemoveFromCollectionIfPropertyNegatedAction(), 100);
        registerAction(new RemoveFromCollectionPropertyAction(), 100);
        registerAction(new GetFromCollectionPropertyAction(), 90);
        registerAction(new SizeOfCollectionPropertyAction(), 90);
        registerAction(new IsEmptyCollectionPropertyAction(), 90);
    }

    /**
     * Register own config action.
     *
     * @param action
     *         action to register.
     * @param priority
     *         priority of action.
     */
    public static void registerAction(ConfigPropertyAction action, double priority)
    {
        WriteLock writeLock = lock.writeLock();
        try
        {
            writeLock.lock();
            actions.add(new ConfigPropertyActionEntry(action, priority));
        }
        finally
        {
            writeLock.unlock();
        }
    }

    @Nullable
    public static Pair<ConfigPropertyAction, ActionMatcherResult> findMethod(Method method)
    {
        return findMethod(method, s -> true);
    }

    @Nullable
    public static Pair<ConfigPropertyAction, ActionMatcherResult> findMethod(Method method, Predicate<String> propertyNameChecker)
    {
        Pair<ConfigPropertyAction, ActionMatcherResult> lastMatching = null;

        ReadLock readLock = lock.readLock();
        try
        {
            readLock.lock();
            for (ConfigPropertyActionEntry actionEntry : actions)
            {
                ConfigPropertyAction action = actionEntry.action;
                ActionMatcherResult actionMatcherResult = action.matchesAction(method);
                if (actionMatcherResult.isMatching())
                {
                    actionMatcherResult.setValidatedName(action.declaresProperty() || propertyNameChecker.test(actionMatcherResult.getPropertyName()));
                    lastMatching = new ImmutablePair<>(action, actionMatcherResult);
                    if (actionMatcherResult.isValidatedName())
                    {
                        return lastMatching;
                    }
                }
            }
        }
        finally
        {
            readLock.unlock();
        }
        return lastMatching;
    }

    static class ConfigPropertyActionEntry implements Comparable<ConfigPropertyActionEntry>
    {
        private final ConfigPropertyAction action;
        private final double               priority;

        ConfigPropertyActionEntry(ConfigPropertyAction action, double priority)
        {
            this.action = action;
            this.priority = priority;
        }

        @Override
        public boolean equals(Object object)
        {
            if (this == object)
            {
                return true;
            }
            if (! (object instanceof ConfigPropertyActionEntry))
            {
                return false;
            }
            ConfigPropertyActionEntry that = (ConfigPropertyActionEntry) object;
            return (this.priority == that.priority) && Objects.equals(this.action, that.action);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(this.action, this.priority);
        }

        @Override
        public int compareTo(ConfigPropertyActionEntry o)
        {
            int compare = Double.compare(o.priority, this.priority);
            if (compare != 0)
            {
                return compare;
            }
            return this.action.getActionName().compareTo(o.action.getActionName());
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this).appendSuper(super.toString()).append("action", this.action).append("priority", this.priority).toString();
        }
    }
}
