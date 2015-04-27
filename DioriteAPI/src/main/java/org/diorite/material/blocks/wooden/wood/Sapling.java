package org.diorite.material.blocks.wooden.wood;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Sapling" and all its subtypes.
 */
public class Sapling extends Wood
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SAPLING__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SAPLING__HARDNESS;

    public static final Sapling SAPLING_OAK      = new Sapling();
    public static final Sapling SAPLING_SPRUCE   = new Sapling("SPRUCE", WoodType.SPRUCE, SaplingStage.NEW);
    public static final Sapling SAPLING_BIRCH    = new Sapling("BIRCH", WoodType.BIRCH, SaplingStage.NEW);
    public static final Sapling SAPLING_JUNGLE   = new Sapling("JUNGLE", WoodType.JUNGLE, SaplingStage.NEW);
    public static final Sapling SAPLING_ACACIA   = new Sapling("ACACIA", WoodType.ACACIA, SaplingStage.NEW);
    public static final Sapling SAPLING_DARK_OAK = new Sapling("DARK_OAK", WoodType.DARK_OAK, SaplingStage.NEW);

    public static final Sapling SAPLING_QAK_OLDER      = new Sapling("QAK_OLDER", WoodType.OAK, SaplingStage.OLDER);
    public static final Sapling SAPLING_SPRUCE_OLDER   = new Sapling("SPRUCE_OLDER", WoodType.SPRUCE, SaplingStage.OLDER);
    public static final Sapling SAPLING_BIRCH_OLDER    = new Sapling("BIRCH_OLDER", WoodType.BIRCH, SaplingStage.OLDER);
    public static final Sapling SAPLING_JUNGLE_OLDER   = new Sapling("JUNGLE_OLDER", WoodType.JUNGLE, SaplingStage.OLDER);
    public static final Sapling SAPLING_ACACIA_OLDER   = new Sapling("ACACIA_OLDER", WoodType.ACACIA, SaplingStage.OLDER);
    public static final Sapling SAPLING_DARK_OAK_OLDER = new Sapling("DARK_OAK_OLDER", WoodType.DARK_OAK, SaplingStage.OLDER);

    private static final Map<String, Sapling>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Sapling> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final SaplingStage stage;

    protected Sapling()
    {
        super("SAPLING", 5, "minecraft:sapling", "OAK", (byte) 0x00, WoodType.OAK);
        this.stage = SaplingStage.NEW;
    }

    public Sapling(final String enumName, final WoodType woodType, final SaplingStage stage)
    {
        super(SAPLING_OAK.name(), SAPLING_OAK.getId(), SAPLING_OAK.getMinecraftId(), enumName, combine(woodType, stage), woodType);
        this.stage = stage;
    }

    public Sapling(final int maxStack, final String typeName, final WoodType woodType, final SaplingStage stage)
    {
        super(SAPLING_OAK.name(), SAPLING_OAK.getId(), SAPLING_OAK.getMinecraftId(), maxStack, typeName, combine(woodType, stage), woodType);
        this.stage = stage;
    }

    public Sapling getOtherStage()
    {
        return this.getType(this.woodType, (this.stage == SaplingStage.NEW) ? SaplingStage.OLDER : SaplingStage.NEW);
    }

    public Sapling normalize() // make sure that sappling is from first stage (NEW)
    {
        return this.getType(this.woodType, SaplingStage.NEW);
    }

    public SaplingStage getStage()
    {
        return this.stage;
    }

    public Sapling getType(final WoodType woodType, final SaplingStage stage)
    {
        return getByID(combine(woodType, stage));
    }

    @Override
    public Wood getWoodType(final WoodType woodType)
    {
        return getByID(combine(woodType, this.stage));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("stage", this.stage).toString();
    }

    @Override
    public Sapling getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Sapling getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isSolid()
    {
        return false;
    }

    @Override
    public boolean isTransparent()
    {
        return true;
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
    public boolean isOccluding()
    {
        return false;
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

    public enum SaplingStage
    {
        NEW(0x00),
        OLDER(0x08);

        private final byte flag;

        SaplingStage(final int flag)
        {
            this.flag = (byte) flag;
        }

        public byte getFlag()
        {
            return this.flag;
        }
    }

    private static byte combine(final WoodType woodType, final SaplingStage stage)
    {
        byte result = woodType.getPlanksMeta();
        result |= stage.getFlag();
        return result;
    }

    /**
     * Returns one of Sapling sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Sapling or null
     */
    public static Sapling getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Sapling sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Sapling or null
     */
    public static Sapling getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static Sapling getSapling(final WoodType woodType, final SaplingStage stage)
    {
        return getByID(combine(woodType, stage));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Sapling element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Sapling.register(SAPLING_OAK);
        Sapling.register(SAPLING_SPRUCE);
        Sapling.register(SAPLING_BIRCH);
        Sapling.register(SAPLING_JUNGLE);
        Sapling.register(SAPLING_ACACIA);
        Sapling.register(SAPLING_DARK_OAK);
    }
}
