package org.diorite.material.blocks.end;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.PortalMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "EndPortal" and all its subtypes.
 */
public class EndPortalMat extends BlockMaterialData implements PortalMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__END_PORTAL__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__END_PORTAL__HARDNESS;

    public static final EndPortalMat END_PORTAL = new EndPortalMat();

    private static final Map<String, EndPortalMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EndPortalMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected EndPortalMat()
    {
        super("END_PORTAL", 119, "minecraft:end_portal", "END_PORTAL", (byte) 0x00);
    }

    protected EndPortalMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
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
    public EndPortalMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EndPortalMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of EndPortal sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of EndPortal or null
     */
    public static EndPortalMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of EndPortal sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of EndPortal or null
     */
    public static EndPortalMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EndPortalMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public EndPortalMat[] types()
    {
        return EndPortalMat.endPortalTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static EndPortalMat[] endPortalTypes()
    {
        return byID.values(new EndPortalMat[byID.size()]);
    }

    static
    {
        EndPortalMat.register(END_PORTAL);
    }
}
