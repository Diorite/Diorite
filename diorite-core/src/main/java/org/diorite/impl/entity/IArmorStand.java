package org.diorite.impl.entity;

import org.diorite.entity.ArmorStand;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IArmorStand extends ILivingEntity, ArmorStand, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.5F, 2.0F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                               = 17;
    /**
     * Byte bit mask. <br>
     * Small armorstand, HasGravity, HasArms, RemoveBasePlate, Marker (no bounding box)
     */
    byte META_KEY_ARMOR_STAND_STATUS             = 10;
    /**
     * Vector3F, Euler angle
     */
    byte META_KEY_ARMOR_STAND_HEAD_ROTATION      = 11;
    /**
     * Vector3F, Euler angle
     */
    byte META_KEY_ARMOR_STAND_BODY_ROTATION      = 12;
    /**
     * Vector3F, Euler angle
     */
    byte META_KEY_ARMOR_STAND_LEFT_ARM_ROTATION  = 13;
    /**
     * Vector3F, Euler angle
     */
    byte META_KEY_ARMOR_STAND_RIGHT_ARM_ROTATION = 14;
    /**
     * Vector3F, Euler angle
     */
    byte META_KEY_ARMOR_STAND_LEFT_LEG_ROTATION  = 15;
    /**
     * Vector3F, Euler angle
     */
    byte META_KEY_ARMOR_STAND_RIGHT_LEG_ROTATION = 16;

    /**
     * Contains status flags used in matadata.
     */
    interface ArmorStandStatusFlag
    {
        byte SMALL             = 0;
        byte HAS_GRAVITY       = 1;
        byte HAS_ARMS          = 2;
        byte REMOVE_BASE_PLATE = 3;
        byte MARKER            = 4;
    }
}
