package org.diorite.material.blocks;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Sponge extends BlockMaterialData
{
    public static final byte  USED_DATA_VALUES = 2;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SPONGE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SPONGE__HARDNESS;

    public static final Sponge SPONGE     = new Sponge();
    public static final Sponge SPONGE_WET = new Sponge("WET", 0x01, true);

    private static final Map<String, Sponge>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Sponge> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final boolean isWet;

    @SuppressWarnings("MagicNumber")
    protected Sponge()
    {
        super("SPONGE", 19, "minecraft:sponge", "SPONGE", (byte) 0x00);
        this.isWet = false;
    }

    public Sponge(final String enumName, final int type, final boolean isWet)
    {
        super(SPONGE.name(), SPONGE.getId(), SPONGE.getMinecraftId(), enumName, (byte) type);
        this.isWet = isWet;
    }

    public Sponge(final int maxStack, final String typeName, final byte type, final boolean isWet)
    {
        super(SPONGE.name(), SPONGE.getId(), SPONGE.getMinecraftId(), maxStack, typeName, type);
        this.isWet = isWet;
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
    public Sponge getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Sponge getType(final int id)
    {
        return getByID(id);
    }

    public boolean isWet()
    {
        return this.isWet;
    }

    public Sponge getWet(final boolean wet)
    {
        return getByID(wet ? 1 : 0);
    }

    public static Sponge getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Sponge getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Sponge element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Sponge.register(SPONGE);
        Sponge.register(SPONGE_WET);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("isWet", this.isWet).toString();
    }
}
