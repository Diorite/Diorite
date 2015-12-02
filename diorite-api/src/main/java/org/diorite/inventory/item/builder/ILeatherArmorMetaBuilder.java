package org.diorite.inventory.item.builder;

import org.diorite.inventory.item.meta.LeatherArmorMeta;
import org.diorite.utils.Color;

/**
 * Interface of builder of leather armor item meta data.
 */
public interface ILeatherArmorMetaBuilder<B extends ILeatherArmorMetaBuilder<B, M>, M extends LeatherArmorMeta> extends IRepairableMetaBuilder<B, M>
{
    /**
     * Sets color of armor.
     *
     * @param color new color of armor.
     *
     * @return builder for method chains.
     */
    default B color(final Color color)
    {
        this.meta().setColor(color);
        return this.getBuilder();
    }

    /**
     * Sets color of armor.
     *
     * @param src source item meta to copy color from it.
     *
     * @return builder for method chains.
     */
    default B color(final LeatherArmorMeta src)
    {
        this.meta().setColor(src.getColor());
        return this.getBuilder();
    }

    /**
     * Removes custom color from armor.
     *
     * @return builder for method chains.
     */
    default B removeColor()
    {
        this.meta().removeColor();
        return this.getBuilder();
    }
}
