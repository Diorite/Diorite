package org.diorite.material.blocks.redstone;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Activatable;
import org.diorite.material.blocks.Directional;

public abstract class Button extends BlockMaterialData implements Activatable, Directional
{
    /**
     * Bit flag defining if rail is active.
     * If bit is set to 0, then it isn't active
     */
    public static final byte ACTIVE_FLAG = 0x08;

    protected final BlockFace face;
    protected final boolean   activated;

    public Button(final String enumName, final int id, final String minecraftId, final BlockFace face, final boolean activated)
    {
        super(enumName, id, minecraftId, face.name() + (activated ? "_ACTIVATED" : ""), combine(face, activated));
        this.face = face;
        this.activated = activated;
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public boolean isActivated()
    {
        return this.activated;
    }

    /**
     * Returns one of Button sub-type based on {@link BlockFace} and activated state.
     * It will never return null.
     *
     * @param face      facing direction of piston.
     * @param activated if button should be activated.
     *
     * @return sub-type of Button
     */
    public abstract Button getType(BlockFace face, boolean activated);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("activated", this.activated).toString();
    }

    protected static byte combine(final BlockFace face, final boolean activated)
    {
        byte result = activated ? ACTIVE_FLAG : 0x0;
        switch (face)
        {
            case EAST:
                result |= 0x1;
                break;
            case WEST:
                result |= 0x2;
                break;
            case SOUTH:
                result |= 0x3;
                break;
            case NORTH:
                result |= 0x4;
                break;
            case UP:
                result |= 0x5;
                break;
            default:
                break;
        }
        return result;
    }
}
