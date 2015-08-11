package org.diorite;

import java.util.UUID;

import org.diorite.entity.Entity;

public interface EntityManager
{
    Entity getEntity(int id);

    Entity getEntity(UUID id);

    Entity removeEntity(Entity entity);

    Entity removeEntity(int id);

    int getCurrentID();

    Core getServer();
}
