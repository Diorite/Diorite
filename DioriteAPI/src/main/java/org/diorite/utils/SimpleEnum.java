package org.diorite.utils;

import org.diorite.utils.reflections.DioriteReflectionUtils;

/**
 * Simple interface-based enum, that can be edited in runtime.
 * <p>
 * Every simple enum must contains static methods:
 * void register(T)
 * T[] values()
 *
 * @param <T> type of values.
 */
public interface SimpleEnum<T extends SimpleEnum<T>>
{
    float SMALL_LOAD_FACTOR = .1f;

    String name();

    int getId();

    T byId(int id);

    T byName(String name);

    /**
     * Safe method to get one of enum values by name or ordinal,
     * use -1 as id, to use only name,
     * and null or empty string for name, to use only ordinal id.
     *
     * @param name      name of enum field (ignore-case)
     * @param id        ordinal id.
     * @param enumClass class of enum.
     * @param <T>       type of enum.
     *
     * @return enum element or null.
     */
    static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final String name, final int id, final Class<T> enumClass)
    {
        return DioriteReflectionUtils.getSimpleEnumValueSafe(name, id, enumClass);
    }

    /**
     * Safe method to get one of enum values by name or ordinal,
     * use -1 as id, to use only name,
     * and null or empty string for name, to use only ordinal id.
     *
     * @param name name of enum field (ignore-case)
     * @param id   ordinal id.
     * @param def  default value, can't be null.
     * @param <T>  type of enum.
     *
     * @return enum element or def.
     */
    static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final String name, final int id, final T def)
    {
        return DioriteReflectionUtils.getSimpleEnumValueSafe(name, id, def);
    }

    /**
     * Safe method to get one of enum values by ordinal.
     *
     * @param id        ordinal id.
     * @param enumClass class of enum.
     * @param <T>       type of enum.
     *
     * @return enum element or null.
     */
    static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final int id, final Class<T> enumClass)
    {
        return DioriteReflectionUtils.getSimpleEnumValueSafe(null, id, enumClass);
    }

    /**
     * Safe method to get one of enum values by ordinal.
     *
     * @param id  ordinal id.
     * @param def default value, can't be null.
     * @param <T> type of enum.
     *
     * @return enum element or def.
     */
    static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final int id, final T def)
    {
        return DioriteReflectionUtils.getSimpleEnumValueSafe(null, id, def);
    }

    /**
     * Safe method to get one of enum values by name.
     *
     * @param name      name of enum field (ignore-case)
     * @param enumClass class of enum.
     * @param <T>       type of enum.
     *
     * @return enum element or null.
     */
    static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final String name, final Class<T> enumClass)
    {
        return DioriteReflectionUtils.getSimpleEnumValueSafe(name, - 1, enumClass);
    }

    /**
     * Safe method to get one of enum values by name.
     *
     * @param name name of enum field (ignore-case)
     * @param def  default value, can't be null.
     * @param <T>  type of enum.
     *
     * @return enum element or def.
     */
    static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final String name, final T def)
    {
        return DioriteReflectionUtils.getSimpleEnumValueSafe(name, - 1, def);
    }
}
