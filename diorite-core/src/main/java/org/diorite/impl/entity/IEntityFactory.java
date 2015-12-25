package org.diorite.impl.entity;

import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.EntityFactory;
import org.diorite.ILocation;
import org.diorite.auth.GameProfile;

public interface IEntityFactory extends EntityFactory
{
    IPlayer createPlayer(GameProfile profile, CoreNetworkManager networkManager, ILocation location);
}
