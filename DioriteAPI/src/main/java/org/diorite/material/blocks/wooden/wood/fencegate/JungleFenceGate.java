package org.diorite.material.blocks.wooden.wood.fencegate;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.FenceGate;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "JungleFenceGate" and all its subtypes.
 */
public class JungleFenceGate extends WoodenFenceGate
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__JUNGLE_FENCE_GATE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__JUNGLE_FENCE_GATE__HARDNESS;

    public static final JungleFenceGate JUNGLE_FENCE_GATE_SOUTH = new JungleFenceGate();
    public static final JungleFenceGate JUNGLE_FENCE_GATE_WEST  = new JungleFenceGate("WEST", BlockFace.WEST, false);
    public static final JungleFenceGate JUNGLE_FENCE_GATE_NORTH = new JungleFenceGate("NORTH", BlockFace.NORTH, false);
    public static final JungleFenceGate JUNGLE_FENCE_GATE_EAST  = new JungleFenceGate("EAST", BlockFace.EAST, false);

    public static final JungleFenceGate JUNGLE_FENCE_GATE_SOUTH_OPEN = new JungleFenceGate("SOUTH_OPEN", BlockFace.SOUTH, true);
    public static final JungleFenceGate JUNGLE_FENCE_GATE_WEST_OPEN  = new JungleFenceGate("WEST_OPEN", BlockFace.WEST, true);
    public static final JungleFenceGate JUNGLE_FENCE_GATE_NORTH_OPEN = new JungleFenceGate("NORTH_OPEN", BlockFace.NORTH, true);
    public static final JungleFenceGate JUNGLE_FENCE_GATE_EAST_OPEN  = new JungleFenceGate("EAST_OPEN", BlockFace.EAST, true);

    private static final Map<String, JungleFenceGate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<JungleFenceGate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected JungleFenceGate()
    {
        super("JUNGLE_FENCE_GATE", 185, "minecraft:jungle_fence_gate", "SOUTH", WoodType.JUNGLE, BlockFace.SOUTH, false);
    }

    public JungleFenceGate(final String enumName, final BlockFace face, final boolean open)
    {
        super(JUNGLE_FENCE_GATE_SOUTH.name(), JUNGLE_FENCE_GATE_SOUTH.getId(), JUNGLE_FENCE_GATE_SOUTH.getMinecraftId(), enumName, WoodType.JUNGLE, face, open);

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
    public JungleFenceGate getBlockFacing(final BlockFace face)
    {
        return getByID(FenceGate.combine(face, this.open));
    }

    @Override
    public JungleFenceGate getOpen(final boolean open)
    {
        return getByID(FenceGate.combine(this.face, open));
    }

    @Override
    public JungleFenceGate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public JungleFenceGate getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public JungleFenceGate getType(final BlockFace face, final boolean open)
    {
        return getByID(FenceGate.combine(face, open));
    }

    /**
     * Returns one of JungleFenceGate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of JungleFenceGate or null
     */
    public static JungleFenceGate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of JungleFenceGate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of JungleFenceGate or null
     */
    public static JungleFenceGate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of JungleFenceGate sub-type based on facing direction and open state.
     * It will never return null.
     *
     * @param blockFace facing direction of gate.
     * @param open      if gate should be open.
     *
     * @return sub-type of JungleFenceGate
     */
    public static JungleFenceGate getJungleFenceGate(final BlockFace blockFace, final boolean open)
    {
        return getByID(FenceGate.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final JungleFenceGate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        JungleFenceGate.register(JUNGLE_FENCE_GATE_SOUTH);
        JungleFenceGate.register(JUNGLE_FENCE_GATE_WEST);
        JungleFenceGate.register(JUNGLE_FENCE_GATE_NORTH);
        JungleFenceGate.register(JUNGLE_FENCE_GATE_EAST);
        JungleFenceGate.register(JUNGLE_FENCE_GATE_SOUTH_OPEN);
        JungleFenceGate.register(JUNGLE_FENCE_GATE_WEST_OPEN);
        JungleFenceGate.register(JUNGLE_FENCE_GATE_NORTH_OPEN);
        JungleFenceGate.register(JUNGLE_FENCE_GATE_EAST_OPEN);
    }
}
