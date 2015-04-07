package org.diorite.entity;

import java.util.Map;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class EntityType implements SimpleEnum<EntityType>
{
    public static final EntityType PLAYER            = new EntityType("PLAYER", 0, Player.class, - 1 /* ??? */, "PLAYER" /* ??? */);
    public static final EntityType MINECART_RIDEABLE = new EntityType("MINECART_RIDEABLE", 1, EntityMinecartRideable.class, /*42*/50, "MinecartRideable");
    // TODO

    private static final Map<String, EntityType>   byName = new SimpleStringHashMap<>(20, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<EntityType> byID   = new TIntObjectHashMap<>(20, SMALL_LOAD_FACTOR);

    private String                  enumName;
    private int                     enumId;
    private Class<? extends Entity> dioriteEntityClass;
    private boolean                 living;
    private int                     mcId;
    private String                  mcName;

    public EntityType(final String enumName, final int enumId, final Class<? extends Entity> dioriteEntityClass, final int mcId, final String mcName)
    {
        this.enumName = enumName;
        this.enumId = enumId;
        this.dioriteEntityClass = dioriteEntityClass;
        this.living = LivingEntity.class.isAssignableFrom(dioriteEntityClass);
        this.mcId = mcId;
        this.mcName = mcName;
    }

    @Override
    public String name()
    {
        return enumName;
    }

    @Override
    public int getId()
    {
        return enumId;
    }

    @Override
    public EntityType byId(final int id)
    {
        return byID.get(id);
    }

    @Override
    public EntityType byName(final String name)
    {
        return byName.get(name);
    }

    public String getMcName()
    {
        return mcName;
    }

    public int getMcId()
    {
        return mcId;
    }

    public boolean isLiving()
    {
        return living;
    }

    public Class<? extends Entity> getDioriteEntityClass()
    {
        return dioriteEntityClass;
    }

    public static void register(final EntityType element)
    {
        byID.put(element.getId(), element);
        byName.put(element.name(), element);
    }

    static
    {
        register(PLAYER);
        register(MINECART_RIDEABLE);
    }
}
