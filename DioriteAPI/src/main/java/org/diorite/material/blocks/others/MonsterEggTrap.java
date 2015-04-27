package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.stony.Cobblestone;
import org.diorite.material.blocks.stony.Stone;
import org.diorite.material.blocks.stony.StoneBrick;
import org.diorite.material.blocks.stony.Stony;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "MonsterEggTrap" and all its subtypes.
 */
public class MonsterEggTrap extends Stony
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 6;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__MONSTER_EGG_TRAP__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__MONSTER_EGG_TRAP__HARDNESS;

    public static final MonsterEggTrap MONSTER_EGG_TRAP_STONE                = new MonsterEggTrap();
    public static final MonsterEggTrap MONSTER_EGG_TRAP_COBBLESTONE          = new MonsterEggTrap("COBBLESTONE", 0x1, Cobblestone.COBBLESTONE);
    public static final MonsterEggTrap MONSTER_EGG_TRAP_STONE_BRICK          = new MonsterEggTrap("STONE_BRICK", 0x2, StoneBrick.STONE_BRICK);
    public static final MonsterEggTrap MONSTER_EGG_TRAP_STONE_BRICK_MOSSY    = new MonsterEggTrap("STONE_BRICK_MOSSY", 0x3, StoneBrick.STONE_BRICK_MOSSY);
    public static final MonsterEggTrap MONSTER_EGG_TRAP_STONE_BRICK_CRACKED  = new MonsterEggTrap("STONE_BRICK_CRACKED", 0x4, StoneBrick.STONE_BRICK_CRACKED);
    public static final MonsterEggTrap MONSTER_EGG_TRAP_STONE_BRICK_CHISELED = new MonsterEggTrap("STONE_BRICK_CHISELED", 0x5, StoneBrick.STONE_BRICK_CHISELED);

    private static final Map<String, MonsterEggTrap>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<MonsterEggTrap> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockMaterialData block;

    @SuppressWarnings("MagicNumber")
    protected MonsterEggTrap()
    {
        super("MONSTER_EGG_TRAP", 97, "minecraft:monster_egg", "STONE", (byte) 0x00);
        this.block = Stone.STONE;
    }

    public MonsterEggTrap(final String enumName, final int type, final BlockMaterialData block)
    {
        super(MONSTER_EGG_TRAP_STONE.name(), MONSTER_EGG_TRAP_STONE.getId(), MONSTER_EGG_TRAP_STONE.getMinecraftId(), enumName, (byte) type);
        this.block = block;
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

    /**
     * @return type of fake-block. (used textures)
     */
    public BlockMaterialData getBlock()
    {
        return this.block;
    }

    /**
     * Returns sub-type of MonsterEggTrap based on type of fake-block
     *
     * @param block type of fake block.
     *
     * @return sub-type of MonsterEggTrap
     */
    public MonsterEggTrap getBlock(final BlockMaterialData block)
    {
        for (final MonsterEggTrap v : byName.values())
        {
            if (v.block.equals(block))
            {
                return v;
            }
        }
        return null;
    }

    @Override
    public MonsterEggTrap getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public MonsterEggTrap getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("block", this.block).toString();
    }

    /**
     * Returns one of MonsterEggTrap sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of MonsterEggTrap or null
     */
    public static MonsterEggTrap getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of MonsterEggTrap sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of MonsterEggTrap or null
     */
    public static MonsterEggTrap getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of MonsterEggTrap based on type of fake-block
     *
     * @param block type of fake block.
     *
     * @return sub-type of MonsterEggTrap
     */
    public static MonsterEggTrap getMonsterEggTrap(final BlockMaterialData block)
    {
        for (final MonsterEggTrap v : byName.values())
        {
            if (v.block.equals(block))
            {
                return v;
            }
        }
        return null;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final MonsterEggTrap element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        MonsterEggTrap.register(MONSTER_EGG_TRAP_STONE);
        MonsterEggTrap.register(MONSTER_EGG_TRAP_COBBLESTONE);
        MonsterEggTrap.register(MONSTER_EGG_TRAP_STONE_BRICK);
        MonsterEggTrap.register(MONSTER_EGG_TRAP_STONE_BRICK_MOSSY);
        MonsterEggTrap.register(MONSTER_EGG_TRAP_STONE_BRICK_CRACKED);
        MonsterEggTrap.register(MONSTER_EGG_TRAP_STONE_BRICK_CHISELED);
    }
}
