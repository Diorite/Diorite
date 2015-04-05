package org.diorite.material.blocks.wooden.wood;

public class WoodenSlab //extends Wood implements Slab
{
//    public static final byte  USED_DATA_VALUES = 1;
//    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WOODEN_SLAB__BLAST_RESISTANCE;
//    public static final float HARDNESS         = MagicNumbers.MATERIAL__WOODEN_SLAB__HARDNESS;
//
//    public static final WoodenSlab WOODEN_SLAB = new WoodenSlab();
//
//    private static final Map<String, WoodenSlab>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
//    private static final TByteObjectMap<WoodenSlab> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
//
//    protected final SlabType slabType;
//
//    @SuppressWarnings("MagicNumber")
//    protected WoodenSlab()
//    {
//        super("WOODEN_SLAB", 126, "minecraft:wooden_slab", "OAK", (byte) 0x00, WoodType.OAK);
//        this.slabType = SlabType.BOTTOM;
//    }
//
//    public WoodenSlab(final String enumName, final WoodType woodType, final SlabType slabType)
//    {
//        super(WOODEN_SLAB.name(), WOODEN_SLAB.getId(), WOODEN_SLAB.getMinecraftId(), enumName, (byte) type);
//        this.slabType = slabType;
//    }
//
//    public WoodenSlab(final int maxStack, final String typeName, final SlabType slabType)
//    {
//        super(WOODEN_SLAB.name(), WOODEN_SLAB.getId(), WOODEN_SLAB.getMinecraftId(), maxStack, typeName, type);
//        this.slabType = slabType;
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
//    @Override
//    public SlabType getSlabType()
//    {
//        return this.slabType;
//    }
//
//    @Override
//    public Slab getSlab(final SlabType type)
//    {
//        return null;
//    }
//
//    public static WoodenSlab getByID(final int id)
//    {
//        return byID.get((byte) id);
//    }
//
//    public static WoodenSlab getByEnumName(final String name)
//    {
//        return byName.get(name);
//    }
//
//    public static void register(final WoodenSlab element)
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
