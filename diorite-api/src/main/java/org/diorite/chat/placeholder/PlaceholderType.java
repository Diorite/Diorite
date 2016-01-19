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
import java.util.function.Function;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Core;
import org.diorite.OfflinePlayer;
import org.diorite.cfg.DioriteConfig;
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
     * {@link org.diorite.Core} placeholder type.
     */
    public static final PlaceholderType<Core>          CORE    = create("core", Core.class);
    /**
     * {@link org.diorite.Core} placeholder type.
     */
    public static final PlaceholderType<DioriteConfig> CONFIG  = create("config", DioriteConfig.class);
    /**
     * {@link CommandSender} placeholder type.
     */
    public static final PlaceholderType<CommandSender> SENDER  = create("sender", CommandSender.class);
    /**
     * {@link org.diorite.OfflinePlayer} placeholder type.
     */
    public static final PlaceholderType<OfflinePlayer> OFFLINE = create("offline", OfflinePlayer.class);
    /**
     * {@link Entity} placeholder type.
     */
    public static final PlaceholderType<Entity>        ENTITY  = create("entity", Entity.class);
    /**
     * {@link Player} placeholder type, it use sender and entity placeholders too.
     */
    public static final PlaceholderType<Player>        PLAYER  = create("player", Player.class, SENDER, OFFLINE, ENTITY);
    /**
     * {@link Object} placeholder type, used by simple placeholders without any type, like just "points" instead of some object like "player.points"
     */
    public static final PlaceholderType<Object>        OBJECT  = create("", Object.class);


    static
    {
        SENDER.registerItem(SENDER, "name", CommandSender::getName);

        CORE.registerItem(CORE, "version", Core::getVersion);

        CONFIG.registerItem(CONFIG, "hostname", DioriteConfig::getHostname);

        OFFLINE.registerItem(OFFLINE, "name", OfflinePlayer::getName);


        SENDER.registerChild("core", CORE, CommandSender::getCore);
        CORE.registerChild("config", CONFIG, Core::getConfig);
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
    private final Map<String, PlaceholderItem<T>>         items            = new CaseInsensitiveMap<>(5, .3f);
    private final Map<String, ChildPlaceholderItem<?, ?>> cachedChildItems = new CaseInsensitiveMap<>(5, .3f);
    private final Map<String, ChildPlaceholderType<?, ?>> childTypes       = new CaseInsensitiveMap<>(5, .3f);
    private final Set<PlaceholderType<? super T>> superTypes;

    private static class ChildPlaceholderItem<PARENT, CHILD> implements PlaceholderItem<PARENT>
    {
        private final ChildPlaceholderType<PARENT, CHILD> type;
        private final PlaceholderItem<CHILD>              item;

        private ChildPlaceholderItem(final ChildPlaceholderType<PARENT, CHILD> type, final PlaceholderItem<CHILD> item)
        {
            this.type = type;
            this.item = item;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("type", this.type).append("item", this.item).toString();
        }

        @Override
        public PlaceholderType<PARENT> getType()
        {
            return this.type.parent;
        }

        @Override
        public String getId()
        {
            return this.item.getId();
        }

        @Override
        public Object apply(final PARENT obj)
        {
            return this.item.apply(this.type.function.apply(obj));
        }
    }

    private static class ChildPlaceholderType<PARENT, CHILD>
    {
        private final PlaceholderType<PARENT> parent;
        private final PlaceholderType<CHILD>  type;
        private final Function<PARENT, CHILD> function;

        private ChildPlaceholderType(final PlaceholderType<PARENT> parent, final PlaceholderType<CHILD> type, final Function<PARENT, CHILD> function)
        {
            this.parent = parent;
            this.type = type;
            this.function = function;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("type", this.type).append("function", this.function).toString();
        }
    }

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
     * Register new placeholder item to this placeholder type.
     *
     * @param type type of placeholder, like that "player" in player.name.
     * @param id   id/name of placeholder, like that "name" in player.name.
     * @param func function that should return {@link String} or {@link org.diorite.chat.component.BaseComponent}, when using BaseComponent you may add click events, hovers events and all that stuff.
     *
     * @return Created placeholder.
     */
    public PlaceholderItem<T> registerItem(final PlaceholderType<T> type, final String id, final Function<T, Object> func)
    {
        final PlaceholderItem<T> item = new BasePlaceholderItem<>(type, id, func);
        this.registerItem(item);
        return item;
    }

    /**
     * Register new child-type to this type.
     *
     * @param name key-name of type.
     * @param type child type to register.
     */
    public <C> void registerChild(final String name, final PlaceholderType<C> type, final Function<T, C> converter)
    {
        this.childTypes.put(name, new ChildPlaceholderType<>(this, type, converter));
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
    public PlaceholderItem<?> getItem(final String id)
    {
        PlaceholderItem<?> item = this.items.get(id);
        if (item == null)
        {
            item = this.cachedChildItems.get(id);
            if (item != null)
            {
                return item;
            }
        }
        else
        {
            return item;
        }
        if (! this.superTypes.isEmpty())
        {
            for (final PlaceholderType<?> type : this.superTypes)
            {
                item = type.getItem(id);
                if (item != null)
                {
                    break;
                }
            }
        }
        if (item == null)
        {
            final int index = id.indexOf('.');
            if (index == - 1)
            {
                return null;
            }
            final String childTypeID = id.substring(0, index);
            final ChildPlaceholderType<?, ?> childType = this.childTypes.get(childTypeID);
            if (childType == null)
            {
                return null;
            }
            //noinspection unchecked, rawtypes
            final ChildPlaceholderItem<?, ?> childItem = new ChildPlaceholderItem(childType, childType.type.getItem(id.substring(index + 1)));
            item = childItem;
            this.cachedChildItems.put(id, childItem);
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