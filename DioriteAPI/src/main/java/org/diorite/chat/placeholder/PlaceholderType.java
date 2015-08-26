package org.diorite.chat.placeholder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class PlaceholderType<T>
{
    private final String   id;
    private final Class<T> type;
    private final Map<String, PlaceholderItem<T>> items = new CaseInsensitiveMap<>(5, .3f);
    private final Set<PlaceholderType<? super T>> superTypes;

    public PlaceholderType(final String id, final Class<T> type)
    {
        this.id = id;
        this.type = type;
        this.superTypes = new HashSet<>(10);
    }

    public PlaceholderType(final String id, final Class<T> type, final Collection<PlaceholderType<? super T>> superTypes)
    {
        this.id = id;
        this.type = type;
        this.superTypes = new HashSet<>(superTypes);
    }

    @SafeVarargs
    public PlaceholderType(final String id, final Class<T> type, final PlaceholderType<? super T>... superTypes)
    {
        this.id = id;
        this.type = type;
        this.superTypes = Sets.newHashSet(superTypes);
    }

    public void registerItem(final PlaceholderItem<T> item)
    {
        this.items.put(item.getId(), item);
    }

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

    public String getId()
    {
        return this.id;
    }

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