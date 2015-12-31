package org.diorite.impl.entity;

import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.EntityFactory;
import org.diorite.ILocation;
import org.diorite.auth.GameProfile;
import org.diorite.entity.EntityType;

public interface IEntityFactory extends EntityFactory
{
    IPlayer createPlayer(GameProfile profile, CoreNetworkManager networkManager, ILocation location);

    @Override
    IEntity createEntity(EntityType type, ILocation location);

    int getEntityNetworkID(EntityType type);

    EntityType getEntityTypeByNetworkID(int id, boolean isObject);
}
