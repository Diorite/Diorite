package org.diorite.material.items.tool.tool;

import java.util.Map;

import org.diorite.material.ToolMaterial;
import org.diorite.material.ToolType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.lazy.LazyValue;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class StoneSwordMat extends SwordMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final StoneSwordMat STONE_SWORD = new StoneSwordMat();

    private static final Map<String, StoneSwordMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<StoneSwordMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<StoneSwordMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<StoneSwordMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected StoneSwordMat()
    {
        super("STONE_SWORD", 272, "minecraft:stone_Sword", "NEW", (short) 0, ToolMaterial.STONE);
    }

    protected StoneSwordMat(final int durability)
    {
        super(STONE_SWORD.name(), STONE_SWORD.getId(), STONE_SWORD.getMinecraftId(), Integer.toString(durability), (short) durability, ToolMaterial.STONE);
    }

    protected StoneSwordMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected StoneSwordMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public StoneSwordMat[] types()
    {
        return new StoneSwordMat[]{STONE_SWORD};
    }

    @Override
    public StoneSwordMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public StoneSwordMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public StoneSwordMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public StoneSwordMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public StoneSwordMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of StoneSword sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of StoneSword.
     */
    public static StoneSwordMat getByID(final int id)
    {
        StoneSwordMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new StoneSwordMat(id);
            if ((id > 0) && (id < STONE_SWORD.getBaseDurability()))
            {
                StoneSwordMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of StoneSword sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of StoneSword.
     */
    public static StoneSwordMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of StoneSword-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StoneSword.
     */
    public static StoneSwordMat getByEnumName(final String name)
    {
        final StoneSwordMat mat = byName.get(name);
        if (mat == null)
        {
            final Integer idType = DioriteMathUtils.asInt(name);
            if (idType == null)
            {
                return null;
            }
            return getByID(idType);
        }
        return mat;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StoneSwordMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static StoneSwordMat[] stoneSwordTypes()
    {
        return new StoneSwordMat[]{STONE_SWORD};
    }

    static
    {
        StoneSwordMat.register(STONE_SWORD);
    }
}