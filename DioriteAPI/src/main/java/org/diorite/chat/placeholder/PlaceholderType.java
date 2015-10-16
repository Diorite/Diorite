/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.chat.placeholder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.sender.CommandSender;
import org.diorite.entity.Entity;
import org.diorite.entity.Player;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

/**
 * Represent type of placeholder, like {@link Player} for player.name. <br>
 * <br>
 * Class contains also public static instances of basic types.
 *
 * @param <T> type of placeholder object.
 */
public class PlaceholderType<T>
{
    private static final Map<String, PlaceholderType<?>> types = new HashMap<>(5, .1f);

    /**
     * {@link CommandSender} placeholder type.
     */
    public static final PlaceholderType<CommandSender> SENDER = create("sender", CommandSender.class);
    /**
     * {@link Entity} placeholder type.
     */
    public static final PlaceholderType<Entity>        ENTITY = create("entity", Entity.class);
    /**
     * {@link Player} placeholder type, it use sender and entity placeholders too.
     */
    public static final PlaceholderType<Player>        PLAYER = create("player", Player.class, SENDER, ENTITY);

    static
    {
        SENDER.registerItem(new PlaceholderItem<>(SENDER, "name", CommandSender::getName));
    }

    @SafeVarargs
    private static <T> PlaceholderType<T> create(final String id, final Class<T> clazz, final PlaceholderType<? super T>... superTypes)
    {
        final PlaceholderType<T> type = new PlaceholderType<>(id, clazz, superTypes);
        types.put(type.getId(), type);
        return type;
    }

    /**
     * Get placeholder by id, note: this don't check any types.
     *
     * @param id  id of placeholder type.
     * @param <T> type of placeholder object.
     *
     * @return placeholder type or null.
     */
    @SuppressWarnings("unchecked")
    public static <T> PlaceholderType<? super T> get(final String id)
    {
        return (PlaceholderType<? super T>) types.get(id);
    }

    private final String   id;
    private final Class<T> type;
    private final Map<String, PlaceholderItem<T>> items = new CaseInsensitiveMap<>(5, .3f);
    private final Set<PlaceholderType<? super T>> superTypes;

    /**
     * Construct new placeholder type for given id and class without any super types.
     *
     * @param id   id of placeholder.
     * @param type type of placeholder object.
     */
    public PlaceholderType(final String id, final Class<T> type)
    {
        this.id = id;
        this.type = type;
        this.superTypes = new HashSet<>(3);
    }

    /**
     * Construct new placeholder type for given id and class with possible super types
     *
     * @param id         id of placeholder.
     * @param type       type of placeholder object.
     * @param superTypes super types of placeholder, this placeholder may use placeholder items from this types.
     */
    public PlaceholderType(final String id, final Class<T> type, final Collection<PlaceholderType<? super T>> superTypes)
    {
        this.id = id;
        this.type = type;
        this.superTypes = new HashSet<>(superTypes);
    }

    /**
     * Construct new placeholder type for given id and class with possible super types
     *
     * @param id         id of placeholder.
     * @param type       type of placeholder object.
     * @param superTypes super types of placeholder, this placeholder may use placeholder items from this types.
     */
    @SafeVarargs
    public PlaceholderType(final String id, final Class<T> type, final PlaceholderType<? super T>... superTypes)
    {
        this.id = id;
        this.type = type;
        this.superTypes = Sets.newHashSet(superTypes);
    }

    /**
     * Register new placeholder item to this placeholder type.
     *
     * @param item item to register.
     */
    public void registerItem(final PlaceholderItem<T> item)
    {
        this.items.put(item.getId(), item);
    }

    /**
     * Returns item for given id, or null if there isn't any item with maching name. <br>
     * If there is no maching item in this types, method will try get maching item from one
     * of super types.
     *
     * @param id id of item, like "name" for player.name.
     *
     * @return placeholder item or null.
     */
    public PlaceholderItem<? super T> getItem(final String id)
    {
        PlaceholderItem<? super T> item = this.items.get(id);
        if ((item == null) && ! this.superTypes.isEmpty())
        {
            for (final PlaceholderType<? super T> type : this.superTypes)
            {
                item = type.getItem(id);
                if (item != null)
                {
                    break;
                }
            }
        }
        return item;
    }

    /**
     * Returns id of this type, like "player" for player.name.
     *
     * @return id of this type.
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * Returns class type of this type, like {@link Player} for player.name.
     *
     * @return class type of this type.
     */
    public Class<T> getType()
    {
        return this.type;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof PlaceholderType))
        {
            return false;
        }

        final PlaceholderType<?> that = (PlaceholderType<?>) o;

        return this.id.equals(that.id) && this.type.equals(that.type);

    }

    @Override
    public int hashCode()
    {
        int result = this.id.hashCode();
        result = (31 * result) + this.type.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("type", this.type).append("id", this.id).toString();
    }
}