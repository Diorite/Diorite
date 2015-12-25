package org.diorite.impl.entity;

import org.diorite.impl.connection.packets.play.client.PacketPlayClientAbilities;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerAbilities;
import org.diorite.entity.Human;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;
import org.diorite.utils.others.NamedUUID;

public interface IHuman extends ILivingEntity, Human
{
    float BASE_HEAD_HEIGHT      = 1.62F;
    float CROUCHING_HEAD_HEIGHT = BASE_HEAD_HEIGHT - 0.08F;
    float SLEEP_HEAD_HEIGHT     = 0.2F;
    float ITEM_DROP_MOD_Y       = 0.3F;
    @SuppressWarnings("MagicNumber")
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 1.8F);
    /**
     * byte entry with visible skin parts flags.
     */
    byte META_KEY_SKIN_FLAGS = 10;
    /**
     * byte entry, 0x02 bool cape hidden
     */
    byte META_KEY_CAPE = 16;
    /**
     * float entry, amount of absorption hearts (yellow/gold ones)
     */
    byte META_KEY_ABSORPTION_HEARTS = 17;
    /**
     * int entry, amount of player points
     */
    byte META_KEY_SCORE = 18;

    void setNamedUUID(NamedUUID namedUUID);

    float getHeadHeight();

    void pickupItems();

    PacketPlayServerAbilities getAbilities();

    void setAbilities(PacketPlayServerAbilities abilities);

    void setAbilities(PacketPlayClientAbilities abilities);

    void closeInventory(int id);
}
