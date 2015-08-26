package org.diorite.chat.placeholder;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PlaceholderPack
{
    private final Map<String, PlaceholderPackEntry<?>> placeholders;

    public PlaceholderPack(final Map<String, PlaceholderPackEntry<?>> placeholders)
    {
        this.placeholders = placeholders;
    }

    public Set<PlaceholderReplacer> entries()
    {
        return this.placeholders.entrySet().stream().flatMap(e -> e.getValue().enabledItems.stream().map(item -> new PlaceholderReplacer(e.getKey() + "." + item.getId(), item))).collect(Collectors.toSet());
    }

    public static class PlaceholderReplacer
    {
        private final String replace;
        private final PlaceholderItem<?> item;

        public PlaceholderReplacer(final String replace, final PlaceholderItem<?> item)
        {
            this.replace = "$<"+replace+">";
            this.item = item;
        }

        public String getReplace()
        {
            return this.replace;
        }

        public PlaceholderItem<?> getItem()
        {
            return this.item;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("replace", this.replace).append("item", this.item).toString();
        }
    }

    public static class PlaceholderPackEntry<T>
    {
        private final String                          id;
        private final PlaceholderType<T>              types;
        private final Set<PlaceholderItem<? super T>> enabledItems;

        public PlaceholderPackEntry(final String id, final PlaceholderType<T> types, final Set<PlaceholderItem<? super T>> enabledItems)
        {
            this.id = id;
            this.types = types;
            this.enabledItems = enabledItems;
        }

        public String getId()
        {
            return this.id;
        }

        public PlaceholderType<T> getTypes()
        {
            return this.types;
        }

        public Set<PlaceholderItem<? super T>> getEnabledItems()
        {
            return this.enabledItems;
        }

        public boolean isEnabled(final PlaceholderItem<? super T> item)
        {
            return this.enabledItems.contains(item);
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).append("types", this.types).append("enabledItems", this.enabledItems.stream().map(PlaceholderItem::getId).collect(Collectors.toSet())).toString();
        }
    }
}
