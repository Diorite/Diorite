package org.diorite.material.blocks.wooden.wood;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.Material;
import org.diorite.material.WoodTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Sapling" and all its subtypes.
 */
public class SaplingMat extends WoodMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 12;

    public static final SaplingMat SAPLING_OAK      = new SaplingMat();
    public static final SaplingMat SAPLING_SPRUCE   = new SaplingMat(WoodTypeMat.SPRUCE, SaplingStage.NEW);
    public static final SaplingMat SAPLING_BIRCH    = new SaplingMat(WoodTypeMat.BIRCH, SaplingStage.NEW);
    public static final SaplingMat SAPLING_JUNGLE   = new SaplingMat(WoodTypeMat.JUNGLE, SaplingStage.NEW);
    public static final SaplingMat SAPLING_ACACIA   = new SaplingMat(WoodTypeMat.ACACIA, SaplingStage.NEW);
    public static final SaplingMat SAPLING_DARK_OAK = new SaplingMat(WoodTypeMat.DARK_OAK, SaplingStage.NEW);

    public static final SaplingMat SAPLING_OAK_OLDER      = new SaplingMat(WoodTypeMat.OAK, SaplingStage.OLDER);
    public static final SaplingMat SAPLING_SPRUCE_OLDER   = new SaplingMat(WoodTypeMat.SPRUCE, SaplingStage.OLDER);
    public static final SaplingMat SAPLING_BIRCH_OLDER    = new SaplingMat(WoodTypeMat.BIRCH, SaplingStage.OLDER);
    public static final SaplingMat SAPLING_JUNGLE_OLDER   = new SaplingMat(WoodTypeMat.JUNGLE, SaplingStage.OLDER);
    public static final SaplingMat SAPLING_ACACIA_OLDER   = new SaplingMat(WoodTypeMat.ACACIA, SaplingStage.OLDER);
    public static final SaplingMat SAPLING_DARK_OAK_OLDER = new SaplingMat(WoodTypeMat.DARK_OAK, SaplingStage.OLDER);

    private static final Map<String, SaplingMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SaplingMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final SaplingStage stage;

    protected SaplingMat()
    {
        super("SAPLING", 6, "minecraft:sapling", "OAK", (byte) 0x00, WoodTypeMat.OAK, 0, 0);
        this.stage = SaplingStage.NEW;
    }

    protected SaplingMat(final WoodTypeMat woodType, final SaplingStage stage)
    {
        super(SAPLING_OAK.name(), SAPLING_OAK.ordinal(), SAPLING_OAK.getMinecraftId(), woodType + (stage.getFlag() == 0 ? "" : "_" + stage.name()), combine(woodType, stage), woodType, SAPLING_OAK.getHardness(), SAPLING_OAK.getBlastResistance());
        this.stage = stage;
    }

    protected SaplingMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final SaplingStage stage, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
        this.stage = stage;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return this.getType(this.woodType, SaplingStage.NEW);
    }

    public SaplingMat getOtherStage()
    {
        return this.getType(this.woodType, (this.stage == SaplingStage.NEW) ? SaplingStage.OLDER : SaplingStage.NEW);
    }

    public SaplingMat normalize() // make sure that sappling is from first stage (NEW)
    {
        return this.getType(this.woodType, SaplingStage.NEW);
    }

    public SaplingStage getStage()
    {
        return this.stage;
    }

    public SaplingMat getType(final WoodTypeMat woodType, final SaplingStage stage)
    {
        return getByID(combine(woodType, stage));
    }

    @Override
    public WoodMat getWoodType(final WoodTypeMat woodType)
    {
        return getByID(combine(woodType, this.stage));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("stage", this.stage).toString();
    }

    @Override
    public SaplingMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SaplingMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isSolid()
    {
        return false;
    }

    public enum SaplingStage
    {
        NEW(0x0),
        OLDER(0x8);

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

    private static byte combine(final WoodTypeMat woodType, final SaplingStage stage)
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
    public static SaplingMat getByID(final int id)
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
    public static SaplingMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static SaplingMat getSapling(final WoodTypeMat woodType, final SaplingStage stage)
    {
        return getByID(combine(woodType, stage));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SaplingMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SaplingMat[] types()
    {
        return SaplingMat.saplingTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SaplingMat[] saplingTypes()
    {
        return byID.values(new SaplingMat[byID.size()]);
    }

    static
    {
        SaplingMat.register(SAPLING_OAK);
        SaplingMat.register(SAPLING_SPRUCE);
        SaplingMat.register(SAPLING_BIRCH);
        SaplingMat.register(SAPLING_JUNGLE);
        SaplingMat.register(SAPLING_ACACIA);
        SaplingMat.register(SAPLING_DARK_OAK);

        SaplingMat.register(SAPLING_OAK_OLDER);
        SaplingMat.register(SAPLING_SPRUCE_OLDER);
        SaplingMat.register(SAPLING_BIRCH_OLDER);
        SaplingMat.register(SAPLING_JUNGLE_OLDER);
        SaplingMat.register(SAPLING_ACACIA_OLDER);
        SaplingMat.register(SAPLING_DARK_OAK_OLDER);
    }
}
