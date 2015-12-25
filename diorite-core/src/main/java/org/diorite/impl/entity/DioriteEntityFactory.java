package org.diorite.impl.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.ILocation;
import org.diorite.ImmutableLocation;
import org.diorite.auth.GameProfile;
import org.diorite.entity.Creeper;
import org.diorite.entity.Entity;
import org.diorite.entity.EntityType;
import org.diorite.entity.Item;

@SuppressWarnings("unchecked")
public class DioriteEntityFactory implements IEntityFactory
{
    private final Map<EntityType, Function<ImmutableLocation, IEntity>>              typeMap      = new HashMap<>(50);
    private final Map<Class<? extends Entity>, Function<ImmutableLocation, IEntity>> typeClassMap = new HashMap<>(100);

    private final DioriteCore core;

    public DioriteEntityFactory(final DioriteCore core)
    {
        this.core = core;
        this.init();
    }

    private void init()
    {
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new CreeperImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.CREEPER, func);
            this.typeClassMap.put(Creeper.class, func);
            this.typeClassMap.put(ICreeper.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new ItemImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.ITEM, func);
            this.typeClassMap.put(Item.class, func);
            this.typeClassMap.put(IItem.class, func);
        }
    }

    @Override
    public IEntity createEntity(final EntityType type, final ILocation location)
    {
        final Function<ImmutableLocation, IEntity> func = this.typeMap.get(type);
        if (func == null)
        {
            return null;
        }
        return func.apply(location.toImmutableLocation());
    }

    @Override
    public <T extends Entity> T createEntity(final Class<T> clazz, final ILocation location)
    {
        final Function<ImmutableLocation, IEntity> func = this.typeClassMap.get(clazz);
        if (func == null)
        {
            return null;
        }
        return (T) func.apply(location.toImmutableLocation());
    }


    @Override
    public IHuman createHuman(final GameProfile profile, final ILocation location)
    {
        return new HumanImpl(this.core, profile, IEntity.getNextEntityID(), location.toImmutableLocation());
    }

    @Override
    public IPlayer createPlayer(final GameProfile profile, final CoreNetworkManager networkManager, final ILocation location)
    {
        return new PlayerImpl(this.core, profile, networkManager, IEntity.getNextEntityID(), location.toImmutableLocation());
    }
}
