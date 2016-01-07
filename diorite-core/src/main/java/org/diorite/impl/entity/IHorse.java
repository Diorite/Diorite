package org.diorite.impl.entity;

import org.diorite.entity.Horse;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IHorse extends IAnimalEntity, Horse
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(1.4F, 2.9F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                 = 17;
    /**
     * Status bit set. <br>
     * none, IsTamed, HasSaddle, HasChest, IsBred, IsEating, IsRearing, MouthOpen
     */
    byte META_KEY_HORSE_STATUS     = 12;
    /**
     * int, enum, horse type, Normal, Donkey, Mule, Zombie, Sekeleton
     */
    byte META_KEY_HORSE_TYPE       = 13;
    /**
     * int, 2x enum, horse variant and color. <br>
     * 0x00FF - color: White, Creamy, Chestnut, Brown, Black, Gray, Dark Brown <br>
     * 0xFF00 - variant: None, White, Whitefield, White Dots, Black Dots.
     */
    byte META_KEY_HORSE_VARIANT    = 14;
    /**
     * UUID
     */
    byte META_KEY_HORSE_OWNER_UUID = 15;
    /**
     * int, enum? Armor type.
     */
    byte META_KEY_HORSE_ARMOR      = 16;


    /**
     * Contains status flags used in matadata.
     */
    interface HorseStatusFlag
    {
        byte TAMED      = 1;
        byte SADDLE     = 2;
        byte CHEST      = 3;
        byte BRED       = 4;
        byte EATING     = 5;
        byte REARING    = 6;
        byte MOUTH_OPEN = 7;
    }
}
