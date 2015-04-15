package org.diorite.material.blocks.wooden.wood;

/**
 * Class representing block "WoodenSlab" and all its subtypes.
 */
public class WoodenSlab// extends WoodSlab
{
// TODO: auto-generated class, implement other types (sub-ids). 
//    public static final byte  USED_DATA_VALUES = 1;
//    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WOODEN_SLAB__BLAST_RESISTANCE;
//    public static final float HARDNESS         = MagicNumbers.MATERIAL__WOODEN_SLAB__HARDNESS;
//
//    public static final WoodenSlab WOODEN_SLAB = new WoodenSlab();
//
//    private static final Map<String, WoodenSlab>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
//    private static final TByteObjectMap<WoodenSlab> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
//
//    @SuppressWarnings("MagicNumber")
//    protected WoodenSlab()
//    {
//        super("WOODEN_SLAB", 126, "minecraft:wooden_slab", "WOODEN_SLAB", (byte) 0x00);
//    }
//
//    public WoodenSlab(final String enumName, final int type)
//    {
//        super(WOODEN_SLAB.name(), WOODEN_SLAB.getId(), WOODEN_SLAB.getMinecraftId(), enumName, (byte) type);
//    }
//
//    public WoodenSlab(final int maxStack, final String typeName, final byte type)
//    {
//        super(WOODEN_SLAB.name(), WOODEN_SLAB.getId(), WOODEN_SLAB.getMinecraftId(), maxStack, typeName, type);
//    }
//
//    @Override
//    public float getBlastResistance()
//    {
//        return BLAST_RESISTANCE;
//    }
//
//    @Override
//    public float getHardness()
//    {
//        return HARDNESS;
//    }
//
//    @Override
//    public WoodenSlab getType(final String name)
//    {
//        return getByEnumName(name);
//    }
//
//    @Override
//    public WoodenSlab getType(final int id)
//    {
//        return getByID(id);
//    }
//
//    /**
     * Returns one of WoodenSlab sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of WoodenSlab or null
     */
    public static WoodenSlab getByID(final int id)
//    {
//        return byID.get((byte) id);
//    }
//
//    /**
     * Returns one of WoodenSlab sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of WoodenSlab or null
     */
    public static WoodenSlab getByEnumName(final String name)
//    {
//        return byName.get(name);
//    }
//
//    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final WoodenSlab element)
//    {
//        byID.put(element.getType(), element);
//        byName.put(element.name(), element);
//    }
//
//    static
//    {
//        WoodenSlab.register(WOODEN_SLAB);
//    }
}
