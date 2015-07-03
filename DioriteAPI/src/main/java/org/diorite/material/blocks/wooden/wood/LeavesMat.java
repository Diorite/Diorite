package org.diorite.material.blocks.wooden.wood;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Leaves" and all its subtypes.
 */
public class LeavesMat extends WoodMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 24;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__LEAVES__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__LEAVES__HARDNESS;

    public static final LeavesMat LEAVES_OAK      = new LeavesMat();
    public static final LeavesMat LEAVES_SPRUCE   = new LeavesMat("SPRUCE", WoodTypeMat.SPRUCE, false, true);
    public static final LeavesMat LEAVES_BIRCH    = new LeavesMat("BIRCH", WoodTypeMat.BIRCH, false, true);
    public static final LeavesMat LEAVES_JUNGLE   = new LeavesMat("JUNGLE", WoodTypeMat.JUNGLE, false, true);
    public static final LeavesMat LEAVES_ACACIA   = new Leaves2("ACACIA", WoodTypeMat.ACACIA, false, true);
    public static final LeavesMat LEAVES_DARK_OAK = new Leaves2("DARK_OAK", WoodTypeMat.DARK_OAK, false, true);

    public static final LeavesMat LEAVES_OAK_NO_DECAY      = new LeavesMat("OAK_NO_DECAY", WoodTypeMat.OAK, false, false);
    public static final LeavesMat LEAVES_SPRUCE_NO_DECAY   = new LeavesMat("SPRUCE_NO_DECAY", WoodTypeMat.SPRUCE, false, false);
    public static final LeavesMat LEAVES_BIRCH_NO_DECAY    = new LeavesMat("BIRCH_NO_DECAY", WoodTypeMat.BIRCH, false, false);
    public static final LeavesMat LEAVES_JUNGLE_NO_DECAY   = new LeavesMat("JUNGLE_NO_DECAY", WoodTypeMat.JUNGLE, false, false);
    public static final LeavesMat LEAVES_ACACIA_NO_DECAY   = new Leaves2("ACACIA_NO_DECAY", WoodTypeMat.ACACIA, false, false);
    public static final LeavesMat LEAVES_DARK_OAK_NO_DECAY = new Leaves2("DARK_OAK_NO_DECAY", WoodTypeMat.DARK_OAK, false, false);

    public static final LeavesMat LEAVES_OAK_CHECK_DECAY      = new LeavesMat("OAK_CHECK_DECAY", WoodTypeMat.OAK, true, true);
    public static final LeavesMat LEAVES_SPRUCE_CHECK_DECAY   = new LeavesMat("SPRUCE_CHECK_DECAY", WoodTypeMat.SPRUCE, true, true);
    public static final LeavesMat LEAVES_BIRCH_CHECK_DECAY    = new LeavesMat("BIRCH_CHECK_DECAY", WoodTypeMat.BIRCH, true, true);
    public static final LeavesMat LEAVES_JUNGLE_CHECK_DECAY   = new LeavesMat("JUNGLE_CHECK_DECAY", WoodTypeMat.JUNGLE, true, true);
    public static final LeavesMat LEAVES_ACACIA_CHECK_DECAY   = new Leaves2("ACACIA_CHECK_DECAY", WoodTypeMat.ACACIA, true, true);
    public static final LeavesMat LEAVES_DARK_OAK_CHECK_DECAY = new Leaves2("DARK_OAK_CHECK_DECAY", WoodTypeMat.DARK_OAK, true, true);

    public static final LeavesMat LEAVES_OAK_NO_DECAY_AND_CHECK      = new LeavesMat("OAK_NO_DECAY_AND_CHECK", WoodTypeMat.OAK, true, false);
    public static final LeavesMat LEAVES_SPRUCE_NO_DECAY_AND_CHECK   = new LeavesMat("SPRUCE_NO_DECAY_AND_CHECK", WoodTypeMat.SPRUCE, true, false);
    public static final LeavesMat LEAVES_BIRCH_NO_DECAY_AND_CHECK    = new LeavesMat("BIRCH_NO_DECAY_AND_CHECK", WoodTypeMat.BIRCH, true, false);
    public static final LeavesMat LEAVES_JUNGLE_NO_DECAY_AND_CHECK   = new LeavesMat("JUNGLE_NO_DECAY_AND_CHECK", WoodTypeMat.JUNGLE, true, false);
    public static final LeavesMat LEAVES_ACACIA_NO_DECAY_AND_CHECK   = new Leaves2("ACACIA_NO_DECAY_AND_CHECK", WoodTypeMat.ACACIA, true, false);
    public static final LeavesMat LEAVES_DARK_OAK_NO_DECAY_AND_CHECK = new Leaves2("DARK_OAK_NO_DECAY_AND_CHECK", WoodTypeMat.DARK_OAK, true, false);

    private static final Map<String, LeavesMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<LeavesMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final boolean checkDecay;
    protected final boolean decayable;

    @SuppressWarnings("MagicNumber")
    protected LeavesMat()
    {
        super("LEAVES", 18, "minecraft:leaves", "QAK", (byte) 0x00, WoodTypeMat.OAK);
        this.checkDecay = false;
        this.decayable = true;
    }

    @SuppressWarnings("MagicNumber")
    protected LeavesMat(final String enumName, final WoodTypeMat type, final boolean checkDecay, final boolean decayable)
    {
        super(LEAVES_OAK.name() + (type.isSecondLogID() ? "2" : ""), type.isSecondLogID() ? 161 : 18, LEAVES_OAK.getMinecraftId() + (type.isSecondLogID() ? "2" : ""), enumName, combine(type, checkDecay, decayable), type);
        this.checkDecay = checkDecay;
        this.decayable = decayable;
    }

    protected LeavesMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final boolean checkDecay, final boolean decayable)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType);
        this.checkDecay = checkDecay;
        this.decayable = decayable;
    }

    public boolean isCheckDecay()
    {
        return this.checkDecay;
    }

    public boolean isDecayable()
    {
        return this.decayable;
    }

    @Override
    public LeavesMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public LeavesMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public float getBlastResistance()
    {
        return BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return HARDNESS;
    }

    @Override
    public LeavesMat getWoodType(final WoodTypeMat woodType)
    {
        return getLeaves(woodType, this.checkDecay, this.decayable);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("checkDecay", this.checkDecay).append("decayable", this.decayable).toString();
    }

    public LeavesMat getCheckDecay(final boolean checkDecay)
    {
        return getLeaves(this.woodType, checkDecay, this.decayable);
    }

    public LeavesMat getDecayable(final boolean decayable)
    {
        return getLeaves(this.woodType, this.checkDecay, decayable);
    }

    public LeavesMat getType(final WoodTypeMat woodType, final boolean checkDecay, final boolean decayable)
    {
        return getLeaves(woodType, checkDecay, decayable);
    }

    @SuppressWarnings("MagicNumber")
    protected byte getFixedDataValue()
    {
        return (byte) (this.getType() + ((this.getWoodType().isSecondLogID()) ? 16 : 0));
    }

    private static byte combine(final WoodTypeMat type, final boolean checkDecay, final boolean decayable)
    {
        byte result = type.getLogFlag();
        if (! decayable)
        {
            result |= 0b0100;
        }
        if (checkDecay)
        {
            result |= 0b1000;
        }
        return result;
    }

    /**
     * Returns one of Leaves sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Leaves or null
     */
    public static LeavesMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Leaves sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Leaves or null
     */
    public static LeavesMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    @SuppressWarnings("MagicNumber")
    public static LeavesMat getLeaves(final WoodTypeMat type, final boolean checkDecay, final boolean decayable)
    {
        return getByID(combine(type, checkDecay, decayable) + (type.isSecondLogID() ? 16 : 0));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final LeavesMat element)
    {
        byID.put((byte) (element.getType() + ((element instanceof Leaves2) ? 16 : 0)), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public LeavesMat[] types()
    {
        return LeavesMat.leavesTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static LeavesMat[] leavesTypes()
    {
        return byID.values(new LeavesMat[byID.size()]);
    }

    /**
     * Helper class for second leaves ID
     */
    public static class Leaves2 extends LeavesMat
    {
        protected Leaves2(final String enumName, final WoodTypeMat type, final boolean checkDecay, final boolean decayable)
        {
            super(enumName, type, checkDecay, decayable);
        }

        protected Leaves2(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final boolean checkDecay, final boolean decayable)
        {
            super(enumName, id, minecraftId, maxStack, typeName, type, woodType, checkDecay, decayable);
        }

        @Override
        public LeavesMat getType(final int id)
        {
            return getByID(id);
        }

        @SuppressWarnings("MagicNumber")
        /**
         * Returns one of Leaves sub-type based on sub-id, may return null
         * @param id sub-type id
         * @return sub-type of Leaves or null
         */ public static LeavesMat getByID(final int id)
        {
            return byID.get((byte) (id + 16));
        }
    }

    static
    {
        LeavesMat.register(LEAVES_OAK);
        LeavesMat.register(LEAVES_SPRUCE);
        LeavesMat.register(LEAVES_BIRCH);
        LeavesMat.register(LEAVES_JUNGLE);
        LeavesMat.register(LEAVES_ACACIA);
        LeavesMat.register(LEAVES_DARK_OAK);
        LeavesMat.register(LEAVES_OAK_NO_DECAY);
        LeavesMat.register(LEAVES_SPRUCE_NO_DECAY);
        LeavesMat.register(LEAVES_BIRCH_NO_DECAY);
        LeavesMat.register(LEAVES_JUNGLE_NO_DECAY);
        LeavesMat.register(LEAVES_ACACIA_NO_DECAY);
        LeavesMat.register(LEAVES_DARK_OAK_NO_DECAY);
        LeavesMat.register(LEAVES_OAK_CHECK_DECAY);
        LeavesMat.register(LEAVES_SPRUCE_CHECK_DECAY);
        LeavesMat.register(LEAVES_BIRCH_CHECK_DECAY);
        LeavesMat.register(LEAVES_JUNGLE_CHECK_DECAY);
        LeavesMat.register(LEAVES_ACACIA_CHECK_DECAY);
        LeavesMat.register(LEAVES_DARK_OAK_CHECK_DECAY);
        LeavesMat.register(LEAVES_OAK_NO_DECAY_AND_CHECK);
        LeavesMat.register(LEAVES_SPRUCE_NO_DECAY_AND_CHECK);
        LeavesMat.register(LEAVES_BIRCH_NO_DECAY_AND_CHECK);
        LeavesMat.register(LEAVES_JUNGLE_NO_DECAY_AND_CHECK);
        LeavesMat.register(LEAVES_ACACIA_NO_DECAY_AND_CHECK);
        LeavesMat.register(LEAVES_DARK_OAK_NO_DECAY_AND_CHECK);
    }
}
