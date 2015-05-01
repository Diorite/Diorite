package org.diorite.material.blocks.others;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;

/**
 * Base abstract class for mushroom blocks.
 */
public abstract class MushroomBlock extends BlockMaterialData
{
    protected final Type mushroomType;

    public MushroomBlock(final String enumName, final int id, final String minecraftId, final Type mushroomType)
    {
        super(enumName, id, minecraftId, mushroomType.name(), mushroomType.getFlag());
        this.mushroomType = mushroomType;
    }

    /**
     * @return type of mushroom texture/block.
     */
    public Type getMushroomType()
    {
        return this.mushroomType;
    }

    /**
     * Get mushroom block of selected {@link Type}
     *
     * @param mushroomType type of mushroom texture/block.
     *
     * @return sub-type of mushroom
     */
    public abstract MushroomBlock getMushroomType(Type mushroomType);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("mushroomType", this.mushroomType).toString();
    }

    /**
     * Enum with all usable mushroom block texture types.
     */
    @SuppressWarnings("MagicNumber")
    public enum Type
    {
        /**
         * Pores on all sides.
         */
        PORES_FULL(0x0),

        /**
         * Cap texture on top, west and north
         */
        CAP_NORTH_WEST(0x1),

        /**
         * Cap texture on top and north
         */
        CAP_NORTH(0x2),

        /**
         * Cap texture on top, north and east
         */
        CAP_NORTH_EAST(0x3),

        /**
         * Cap texture on top and west
         */
        CAP_WEST(0x4),

        /**
         * Cap texture on top
         */
        CAP(0x5),

        /**
         * Cap texture on top and east
         */
        CAP_EAST(0x6),

        /**
         * Cap texture on top, south and west
         */
        CAP_SOUTH_WEST(0x7),

        /**
         * Cap texture on top and south
         */
        CAP_SOUTH(0x8),

        /**
         * Cap texture on top, east and south
         */
        CAP_SOUTH_EAST(0x9),

        /**
         * Stem texture on all four sides, pores on top and bottom
         */
        STEAM(0xA),

        /**
         * Cap texture on all six sides
         */
        CAP_FULL(0xE),

        /**
         * Stem texture on all six sides
         */
        STEAM_FULL(0xF);
        private final byte flag;

        Type(final int flag)
        {
            this.flag = (byte) flag;
        }

        /**
         * @return sub-id for mushroom based blocks of this type.
         */
        public byte getFlag()
        {
            return this.flag;
        }
    }
}
