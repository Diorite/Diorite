package org.diorite.material.data.drops;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;
import org.diorite.utils.math.DioriteRandom;
import org.diorite.world.Block;

public class PossibleDrops implements Iterable<PossibleDrop>
{
    protected final List<PossibleDrop> possibleDrops = new CopyOnWriteArrayList<>();

    public PossibleDrops(final PossibleDrop basic)
    {
        Validate.notNull(basic, "Basic drop can't be null.");
        this.possibleDrops.add(basic);
    }

    public static PossibleDrops createEmpty(final Material mat)
    {
        Validate.notNull(mat, "Material can't be null.");
        return new PossibleDrops(new PossibleFixedDrop(new BaseItemStack(mat, 0), 0));
    }

    /**
     * Returns copy of possible drops as normal ArrayList
     *
     * @return copy of possible drops.
     */
    public List<PossibleDrop> getPossibleDrops()
    {
        return new ArrayList<>(this.possibleDrops);
    }

    /**
     * Add new drop to possible drops.
     *
     * @param drop drop to add.
     *
     * @return true if drop was added.
     */
    public boolean addDrop(final PossibleDrop drop)
    {
        return this.possibleDrops.add(drop);
    }

    /**
     * Remove drop from possible drops.
     *
     * @param drop drop to remove.
     *
     * @return true if drop was removed.
     */
    public boolean removeDrop(final PossibleDrop drop)
    {
        return this.possibleDrops.remove(drop);
    }

    /**
     * Check if possible drops contains this entry.
     *
     * @param drop drop to find/check.
     *
     * @return true if possible drops contains it.
     */
    public boolean containsDrop(final PossibleDrop drop)
    {
        return this.possibleDrops.contains(drop);
    }

    /**
     * Clear whole drop list.
     */
    public void clear()
    {
        this.possibleDrops.clear();
    }

    /**
     * Get selected drop by given index.
     *
     * @param i index of element.
     *
     * @return element on this index.
     */
    public PossibleDrop getDrop(final int i)
    {
        return this.possibleDrops.get(i);
    }

    /**
     * Implementation of this method should simulate real drop from block. <br>
     * Method must handle case when block or usedTool is null.
     *
     * @param rand     random instance, should be used if random number is needed.
     * @param usedTool may be null! Used tool.
     * @param block    can be null! Block related to this drop simulation if exist.
     */
    public Set<ItemStack> simulateDrop(final DioriteRandom rand, final ItemStack usedTool, final Block block)
    {
        final Set<ItemStack> drops = new HashSet<>(this.possibleDrops.size() + 3);
        this.possibleDrops.stream().forEach(d -> d.simulateDrop(rand, drops, usedTool, block));
        return drops;
    }

    @Override
    public Iterator<PossibleDrop> iterator()
    {
        return this.possibleDrops.iterator();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("possibleDrops", this.possibleDrops).toString();
    }
}
