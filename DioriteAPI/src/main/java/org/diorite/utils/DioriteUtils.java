package org.diorite.utils;

import java.lang.reflect.Field;

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
}
