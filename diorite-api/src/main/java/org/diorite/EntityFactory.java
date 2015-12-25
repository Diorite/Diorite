package org.diorite;

import org.diorite.auth.GameProfile;
import org.diorite.entity.Entity;
import org.diorite.entity.EntityType;
import org.diorite.entity.Human;

/**
 * Used to create entity instances.
 */
public interface EntityFactory
{
    /**
     * Create entity by entity type.
     *
     * @param type     type of entity to create.
     * @param location location of entity to create.
     *
     * @return created entity.
     */
    Entity createEntity(EntityType type, ILocation location);

    /**
     * Create entity by entity API interface class.
     *
     * @param clazz    interface class of entity to create.
     * @param location location of entity to create.
     * @param <T>      type of entity.
     *
     * @return created entity.
     */
    <T extends Entity> T createEntity(Class<T> clazz, ILocation location);

    /**
     * Create new human entity.
     *
     * @param profile  game profile of entity to create.
     * @param location location of entity to create.
     *
     * @return created entity.
     */
    Human createHuman(GameProfile profile, ILocation location);
}
