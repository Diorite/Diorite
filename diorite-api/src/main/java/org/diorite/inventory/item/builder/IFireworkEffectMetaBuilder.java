package org.diorite.inventory.item.builder;

import org.diorite.firework.FireworkEffect;
import org.diorite.inventory.item.meta.FireworkEffectMeta;

/**
 * Interface of builder of firework effect item meta data.
 *
 * @param <B> type of builder.
 * @param <M> type of item meta.
 */
public interface IFireworkEffectMetaBuilder<B extends IFireworkEffectMetaBuilder<B, M>, M extends FireworkEffectMeta> extends IMetaBuilder<B, M>
{
    /**
     * Sets the firework effect for this meta.
     *
     * @param effect the effect to set.
     *
     * @return builder for method chains.
     */
    default B effect(final FireworkEffect effect)
    {
        this.meta().setEffect(effect);
        return this.getBuilder();
    }

    /**
     * Sets the firework effect for this meta.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B effect(final FireworkEffectMeta src)
    {
        this.meta().setEffect(src.getEffect());
        return this.getBuilder();
    }

    /**
     * Remove effect from this meta.
     *
     * @return builder for method chains.
     */
    default B removeEffect()
    {
        this.meta().removeEffect();
        return this.getBuilder();
    }
}
