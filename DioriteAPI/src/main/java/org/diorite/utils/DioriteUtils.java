package org.diorite.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.diorite.inventory.item.ItemStack;

import sun.misc.Unsafe;

public final class DioriteUtils
{
    private static final Unsafe unsafeInstance;
    public static final ItemStack[] EMPTY_ITEM_STACK = new ItemStack[0];
    public static final int[]       EMPTY_INT        = new int[0];

    private DioriteUtils()
    {
    }

    static
    {
        try
        {
            final Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafeInstance = (Unsafe) field.get(null);
        } catch (final Throwable e)
        {
            throw new Error("Can't find unsafe instance.", e);
        }
    }

    public static Unsafe getUnsafe()
    {
        return unsafeInstance;
    }

    /**
     * Compact given array, it will create the smallest possible array with given items,
     * so it will join duplicated items etc.
     *
     * @param respectStackSize
     * @param items
     *
     * @return
     */
    public static ItemStack[] compact(final boolean respectStackSize, final ItemStack... items)
    {
        for (int i = 0, itemsLength = items.length; i < itemsLength; i++)
        {
            final ItemStack item = items[i];
            if ((item == null) || item.isAir())
            {
                continue;
            }
            for (int k = i + 1; k < itemsLength; k++)
            {
                final ItemStack item2 = items[i];
                if (item.isSimilar(item2))
                {
                    if (respectStackSize)
                    {
                        final int space = item.getMaterial().getMaxStack() - item.getAmount();
                        if (space > 0)
                        {
                            final int toAdd = item2.getAmount();
                            if (space > toAdd)
                            {
                                item.setAmount(item.getAmount() + toAdd);
                                items[i] = null;
                            }
                            else
                            {
                                item.setAmount(item.getAmount() + space);
                                item2.setAmount(toAdd - space);
                            }
                        }
                    }
                    else
                    {
                        item.setAmount(item.getAmount() + item2.getAmount());
                        items[i] = null;
                    }
                }

            }
        }
        final List<ItemStack> result = new ArrayList<>(items.length);
        for (final ItemStack item : items)
        {
            if ((item == null) || item.isAir())
            {
                continue;
            }
            result.add(item);
        }
        return result.toArray(new ItemStack[result.size()]);
    }
}
