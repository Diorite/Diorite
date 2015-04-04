package org.diorite.material.blocks.wooden.wood;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Leaves extends Wood
{
    public static final byte  USED_DATA_VALUES = 24;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__LEAVES__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__LEAVES__HARDNESS;

    public static final Leaves LEAVES_OAK      = new Leaves();
    public static final Leaves LEAVES_SPRUCE   = new Leaves("SPRUCE", WoodType.SPRUCE, false, true);
    public static final Leaves LEAVES_BIRCH    = new Leaves("BIRCH", WoodType.BIRCH, false, true);
    public static final Leaves LEAVES_JUNGLE   = new Leaves("JUNGLE", WoodType.JUNGLE, false, true);
    public static final Leaves LEAVES_ACACIA   = new Leaves("ACACIA", WoodType.ACACIA, false, true);
    public static final Leaves LEAVES_DARK_OAK = new Leaves("DARK_OAK", WoodType.DARK_OAK, false, true);

    public static final Leaves LEAVES_OAK_NO_DECAY      = new Leaves("OAK_NO_DECAY", WoodType.OAK, false, false);
    public static final Leaves LEAVES_SPRUCE_NO_DECAY   = new Leaves("SPRUCE_NO_DECAY", WoodType.SPRUCE, false, false);
    public static final Leaves LEAVES_BIRCH_NO_DECAY    = new Leaves("BIRCH_NO_DECAY", WoodType.BIRCH, false, false);
    public static final Leaves LEAVES_JUNGLE_NO_DECAY   = new Leaves("JUNGLE_NO_DECAY", WoodType.JUNGLE, false, false);
    public static final Leaves LEAVES_ACACIA_NO_DECAY   = new Leaves("ACACIA_NO_DECAY", WoodType.ACACIA, false, false);
    public static final Leaves LEAVES_DARK_OAK_NO_DECAY = new Leaves("DARK_OAK_NO_DECAY", WoodType.DARK_OAK, false, false);

    public static final Leaves LEAVES_OAK_CHECK_DECAY      = new Leaves("OAK_CHECK_DECAY", WoodType.OAK, true, true);
    public static final Leaves LEAVES_SPRUCE_CHECK_DECAY   = new Leaves("SPRUCE_CHECK_DECAY", WoodType.SPRUCE, true, true);
    public static final Leaves LEAVES_BIRCH_CHECK_DECAY    = new Leaves("BIRCH_CHECK_DECAY", WoodType.BIRCH, true, true);
    public static final Leaves LEAVES_JUNGLE_CHECK_DECAY   = new Leaves("JUNGLE_CHECK_DECAY", WoodType.JUNGLE, true, true);
    public static final Leaves LEAVES_ACACIA_CHECK_DECAY   = new Leaves("ACACIA_CHECK_DECAY", WoodType.ACACIA, true, true);
    public static final Leaves LEAVES_DARK_OAK_CHECK_DECAY = new Leaves("DARK_OAK_CHECK_DECAY", WoodType.DARK_OAK, true, true);

    public static final Leaves LEAVES_OAK_NO_DECAY_AND_CHECK      = new Leaves("OAK_NO_DECAY_AND_CHECK", WoodType.OAK, true, false);
    public static final Leaves LEAVES_SPRUCE_NO_DECAY_AND_CHECK   = new Leaves("SPRUCE_NO_DECAY_AND_CHECK", WoodType.SPRUCE, true, false);
    public static final Leaves LEAVES_BIRCH_NO_DECAY_AND_CHECK    = new Leaves("BIRCH_NO_DECAY_AND_CHECK", WoodType.BIRCH, true, false);
    public static final Leaves LEAVES_JUNGLE_NO_DECAY_AND_CHECK   = new Leaves("JUNGLE_NO_DECAY_AND_CHECK", WoodType.JUNGLE, true, false);
    public static final Leaves LEAVES_ACACIA_NO_DECAY_AND_CHECK   = new Leaves("ACACIA_NO_DECAY_AND_CHECK", WoodType.ACACIA, true, false);
    public static final Leaves LEAVES_DARK_OAK_NO_DECAY_AND_CHECK = new Leaves("DARK_OAK_NO_DECAY_AND_CHECK", WoodType.DARK_OAK, true, false);

    private static final Map<String, Leaves>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Leaves> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final boolean checkDecay;
    protected final boolean decayable;

    @SuppressWarnings("MagicNumber")
    protected Leaves()
    {
        super("LEAVES", 18, "minecraft:leaves", "QAK", (byte) 0x00, WoodType.OAK);
        this.checkDecay = false;
        this.decayable = true;
    }

    @SuppressWarnings("MagicNumber")
    public Leaves(final String enumName, final WoodType type, final boolean checkDecay, final boolean decayable)
    {
        super(LEAVES_OAK.name() + (type.isSecondLogID() ? "2" : ""), type.isSecondLogID() ? 161 : 18, LEAVES_OAK.getMinecraftId() + (type.isSecondLogID() ? "2" : ""), enumName, combine(type, checkDecay, decayable), type);
        this.checkDecay = checkDecay;
        this.decayable = decayable;
    }

    @SuppressWarnings("MagicNumber")
    public Leaves(final int maxStack, final String enumName, final WoodType type, final boolean checkDecay, final boolean decayable)
    {
        super(LEAVES_OAK.name() + (type.isSecondLogID() ? "2" : ""), type.isSecondLogID() ? 161 : 18, LEAVES_OAK.getMinecraftId() + (type.isSecondLogID() ? "2" : ""), maxStack, enumName, combine(type, checkDecay, decayable), type);
        this.checkDecay = checkDecay;
        this.decayable = decayable;
    }

    private static byte combine(final WoodType type, final boolean checkDecay, final boolean decayable)
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

    public boolean isCheckDecay()
    {
        return this.checkDecay;
    }

    public boolean isDecayable()
    {
        return this.decayable;
    }

    @Override
    public Leaves getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Leaves getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isFlammable()
    {
        return true;
    }

    @Override
    public boolean isBurnable()
    {
        return true;
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
    public Leaves getWoodType(final WoodType woodType)
    {
        return getLeaves(woodType, this.checkDecay, this.decayable);
    }

    public Leaves getCheckDecay(final boolean checkDecay)
    {
        return getLeaves(this.woodType, checkDecay, this.decayable);
    }

    public Leaves getDecayable(final boolean decayable)
    {
        return getLeaves(this.woodType, this.checkDecay, decayable);
    }

    public Leaves getType(final WoodType woodType, final boolean checkDecay, final boolean decayable)
    {
        return getLeaves(woodType, checkDecay, decayable);
    }

    public static Leaves getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Leaves getByEnumName(final String name)
    {
        return byName.get(name);
    }

    @SuppressWarnings("MagicNumber")
    public static Leaves getLeaves(final WoodType type, final boolean checkDecay, final boolean decayable)
    {
        return getByID(combine(type, checkDecay, decayable) + (type.isSecondLogID() ? 16 : 0));
    }

    @SuppressWarnings("MagicNumber")
    protected byte getFixedDataValue()
    {
        return (byte) (this.getType() + ((this.getWoodType().isSecondLogID()) ? 16 : 0));
    }

    public static void register(final Leaves element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Leaves.register(LEAVES_OAK);
        Leaves.register(LEAVES_SPRUCE);
        Leaves.register(LEAVES_BIRCH);
        Leaves.register(LEAVES_JUNGLE);
        Leaves.register(LEAVES_ACACIA);
        Leaves.register(LEAVES_DARK_OAK);
        Leaves.register(LEAVES_OAK_NO_DECAY);
        Leaves.register(LEAVES_SPRUCE_NO_DECAY);
        Leaves.register(LEAVES_BIRCH_NO_DECAY);
        Leaves.register(LEAVES_JUNGLE_NO_DECAY);
        Leaves.register(LEAVES_ACACIA_NO_DECAY);
        Leaves.register(LEAVES_DARK_OAK_NO_DECAY);
        Leaves.register(LEAVES_OAK_CHECK_DECAY);
        Leaves.register(LEAVES_SPRUCE_CHECK_DECAY);
        Leaves.register(LEAVES_BIRCH_CHECK_DECAY);
        Leaves.register(LEAVES_JUNGLE_CHECK_DECAY);
        Leaves.register(LEAVES_ACACIA_CHECK_DECAY);
        Leaves.register(LEAVES_DARK_OAK_CHECK_DECAY);
        Leaves.register(LEAVES_OAK_NO_DECAY_AND_CHECK);
        Leaves.register(LEAVES_SPRUCE_NO_DECAY_AND_CHECK);
        Leaves.register(LEAVES_BIRCH_NO_DECAY_AND_CHECK);
        Leaves.register(LEAVES_JUNGLE_NO_DECAY_AND_CHECK);
        Leaves.register(LEAVES_ACACIA_NO_DECAY_AND_CHECK);
        Leaves.register(LEAVES_DARK_OAK_NO_DECAY_AND_CHECK);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("checkDecay", this.checkDecay).append("decayable", this.decayable).toString();
    }
}
