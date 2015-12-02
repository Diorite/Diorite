package org.diorite.inventory.item.builder;

import org.diorite.inventory.item.meta.RepairableMeta;

/**
 * Interface of builder of repairable item meta data.
 */
public interface IRepairableMetaBuilder<B extends IRepairableMetaBuilder<B, M>, M extends RepairableMeta> extends IMetaBuilder<B, M>
{
    /**
     * Set unbreakable state of item.
     *
     * @param unbreakable if item should be unbreakable.
     *
     * @return builder for method chains.
     */
    default B unbreakable(final boolean unbreakable)
    {
        this.meta().setUnbreakable(unbreakable);
        return this.getBuilder();
    }

    /**
     * Set unbreakable state of item.
     *
     * @param src source item meta to copy unbreakable state from it.
     *
     * @return builder for method chains.
     */
    default B unbreakable(final RepairableMeta src)
    {
        this.meta().setUnbreakable(src.isUnbreakable());
        return this.getBuilder();
    }

    /**
     * Set repair cost of item.
     *
     * @param repairCost new repair cost of item.
     *
     * @return builder for method chains.
     */
    default B repairCost(final int repairCost)
    {
        this.meta().setRepairCost(repairCost);
        return this.getBuilder();
    }

    /**
     * Set repair cost of item.
     *
     * @param src source item meta to copy repair cost from it.
     *
     * @return builder for method chains.
     */
    default B repairCost(final RepairableMeta src)
    {
        this.meta().setRepairCost(src.getRepairCost());
        return this.getBuilder();
    }
}
