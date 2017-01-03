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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import org.diorite.config.ActionMatcherResult;
import org.diorite.config.ConfigPropertyAction;
import org.diorite.config.impl.actions.collections.AddToCollectionPropertyAction;
import org.diorite.config.impl.actions.collections.ContainsInCollectionPropertyAction;
import org.diorite.config.impl.actions.collections.RemoveFromCollectionIfPropertyAction;
import org.diorite.config.impl.actions.collections.RemoveFromCollectionPropertyAction;
import org.diorite.config.impl.actions.numeric.AddNumericPropertyAction;
import org.diorite.config.impl.actions.numeric.DivideNumericPropertyAction;
import org.diorite.config.impl.actions.numeric.MultipleNumericPropertyAction;
import org.diorite.config.impl.actions.numeric.SubtractNumericPropertyAction;

/**
 * Registry for actions.
 */
public final class ActionsRegistry
{
    private static final SortedSet<ConfigPropertyActionEntry> actions = new TreeSet<>();

    private ActionsRegistry()
    {
    }

    static
    {
        registerAction(new GetPropertyAction(), 100);
        registerAction(new SetPropertyAction(), 100);
        registerAction(new EqualsPropertyAction(), 100);
        registerAction(new AddNumericPropertyAction(), 100);
        registerAction(new SubtractNumericPropertyAction(), 100);
        registerAction(new MultipleNumericPropertyAction(), 100);
        registerAction(new DivideNumericPropertyAction(), 100);
        registerAction(new AddToCollectionPropertyAction(), 100);
        registerAction(new ContainsInCollectionPropertyAction(), 100);
        registerAction(new RemoveFromCollectionIfPropertyAction(), 100);
        registerAction(new RemoveFromCollectionPropertyAction(), 100);
    }

    /**
     * Register own config action.
     *
     * @param action
     *         action to register.
     * @param priority
     *         priority of action.
     */
    static void registerAction(ConfigPropertyAction action, double priority)
    {
        actions.add(new ConfigPropertyActionEntry(action, priority));
    }

    @Nullable
    public static Pair<ConfigPropertyAction, ActionMatcherResult> findMethod(Method method)
    {
        for (ConfigPropertyActionEntry actionEntry : actions)
        {
            ConfigPropertyAction action = actionEntry.action;
            ActionMatcherResult actionMatcherResult = action.matchesAction(method);
            if (actionMatcherResult.isMatching())
            {
                return new ImmutablePair<>(action, actionMatcherResult);
            }
        }
        return null;
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
