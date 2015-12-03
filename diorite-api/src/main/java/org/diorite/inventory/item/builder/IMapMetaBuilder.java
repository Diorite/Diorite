package org.diorite.inventory.item.builder;

import java.util.Collection;

import org.diorite.inventory.item.meta.MapMeta;
import org.diorite.map.MapIcon;

/**
 * Interface of builder of map item meta data.
 *
 * @param <B> type of builder.
 * @param <M> type of item meta.
 */
public interface IMapMetaBuilder<B extends IMapMetaBuilder<B, M>, M extends MapMeta> extends IMetaBuilder<B, M>
{
    /**
     * Sets scale of this map.
     *
     * @param value true to scale
     *
     * @return builder for method chains.
     */
    default B scaling(final int value)
    {
        this.meta().setScaling(value);
        return this.getBuilder();
    }

    /**
     * Sets scale of this map.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B scaling(final MapMeta src)
    {
        this.meta().setScaling(src.getScaling());
        return this.getBuilder();
    }

    /**
     * Adds new map icon to this meta.
     *
     * @param icon icon to be added.
     *
     * @return builder for method chains.
     */
    default B addIcon(final MapIcon icon)
    {
        this.meta().addIcon(icon);
        return this.getBuilder();
    }

    /**
     * Adds new map icon to this meta.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B addIcons(final MapMeta src)
    {
        final Collection<MapIcon> icons = src.getIcons();
        if ((icons == null) || icons.isEmpty())
        {
            return this.getBuilder();
        }
        final M meta = this.meta();
        icons.forEach(meta::addIcon);
        return this.getBuilder();
    }

    /**
     * Sets new map icons to this meta.
     *
     * @param icons icons to be set.
     *
     * @return builder for method chains.
     */
    default B setIcons(final Collection<MapIcon> icons)
    {
        final M meta = this.meta();
        meta.removeIcons();
        icons.forEach(meta::addIcon);
        return this.getBuilder();
    }

    /**
     * Sets new map icons to this meta.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B setIcons(final MapMeta src)
    {
        this.meta().removeIcons();
        return this.addIcons(src);
    }

    /**
     * Remove all map icon from this meta.
     *
     * @return builder for method chains.
     */
    default B removeIcons()
    {
        this.meta().removeIcons();
        return this.getBuilder();
    }
}
