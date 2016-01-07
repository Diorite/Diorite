package org.diorite.impl.entity;

import org.diorite.entity.VillagerGolem;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IVillagerGolem extends IAnimalEntity, VillagerGolem
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(1.4F, 2.9F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                                 = 12;
    /**
     * byte as bool
     */
    byte META_KEY_VILLAGER_GOLEM_IS_PLAYER_CREATED = 11;
}
