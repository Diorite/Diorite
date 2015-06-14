package org.diorite.entity;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

@SuppressWarnings({"MagicNumber", "ClassHasNoToStringMethod"})
public class EntityType extends ASimpleEnum<EntityType>
{
    static
    {
        init(EntityType.class, 2);
    }

    public static final EntityType PLAYER            = new EntityType("PLAYER", Player.class, - 1 /* ??? */, "PLAYER" /* ??? */);
    public static final EntityType MINECART_RIDEABLE = new EntityType("MINECART_RIDEABLE", MinecartRideable.class, /*42*/ 50, "MinecartRideable");
    // TODO

    private final Class<? extends Entity> dioriteEntityClass;
    private final boolean                 living;
    private final int                     mcId;
    private final String                  mcName;

    public EntityType(final String enumName, final int enumId, final Class<? extends Entity> dioriteEntityClass, final boolean living, final int mcId, final String mcName)
    {
        super(enumName, enumId);
        this.dioriteEntityClass = dioriteEntityClass;
        this.living = living;
        this.mcId = mcId;
        this.mcName = mcName;
    }

    public EntityType(final String enumName, final Class<? extends Entity> dioriteEntityClass, final int mcId, final String mcName)
    {
        super(enumName);
        this.dioriteEntityClass = dioriteEntityClass;
        this.living = LivingEntity.class.isAssignableFrom(dioriteEntityClass);
        this.mcId = mcId;
        this.mcName = mcName;
    }

    public String getMcName()
    {
        return this.mcName;
    }

    public int getMcId()
    {
        return this.mcId;
    }

    public boolean isLiving()
    {
        return this.living;
    }

    public Class<? extends Entity> getDioriteEntityClass()
    {
        return this.dioriteEntityClass;
    }

    /**
     * Register new {@link EntityType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final EntityType element)
    {
        ASimpleEnum.register(EntityType.class, element);
    }

    /**
     * Get one of {@link EntityType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static EntityType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(EntityType.class, ordinal);
    }

    /**
     * Get one of {@link EntityType} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static EntityType getByEnumName(final String name)
    {
        return getByEnumName(EntityType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static EntityType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(EntityType.class);
        return (EntityType[]) map.values(new EntityType[map.size()]);
    }

    static
    {
        EntityType.register(PLAYER);
        EntityType.register(MINECART_RIDEABLE);
    }
}
