package org.diorite;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.Color;
import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

@SuppressWarnings("MagicNumber")
public class DyeColor implements SimpleEnum<DyeColor>
{
    public static final DyeColor WHITE      = new DyeColor("WHITE", 0, 0x0, 0xF, Color.fromRGB(0xFFFFFF), Color.fromRGB(0xF0F0F0));
    public static final DyeColor ORANGE     = new DyeColor("ORANGE", 1, 0x1, 0xE, Color.fromRGB(0xD87F33), Color.fromRGB(0xEB8844));
    public static final DyeColor MAGENTA    = new DyeColor("MAGENTA", 2, 0x2, 0xD, Color.fromRGB(0xB24CD8), Color.fromRGB(0xC354CD));
    public static final DyeColor LIGHT_BLUE = new DyeColor("LIGHT_BLUE", 3, 0x3, 0xC, Color.fromRGB(0x6699D8), Color.fromRGB(0x6689D3));
    public static final DyeColor YELLOW     = new DyeColor("YELLOW", 4, 0x4, 0xB, Color.fromRGB(0xE5E533), Color.fromRGB(0xDECF2A));
    public static final DyeColor LIME       = new DyeColor("LIME", 5, 0x5, 0xA, Color.fromRGB(0x7FCC19), Color.fromRGB(0x41CD34));
    public static final DyeColor PINK       = new DyeColor("PINK", 6, 0x6, 0x9, Color.fromRGB(0xF27FA5), Color.fromRGB(0xD88198));
    public static final DyeColor GRAY       = new DyeColor("GRAY", 7, 0x7, 0x8, Color.fromRGB(0x4C4C4C), Color.fromRGB(0x434343));
    public static final DyeColor SILVER     = new DyeColor("SILVER", 8, 0x8, 0x7, Color.fromRGB(0x999999), Color.fromRGB(0xABABAB));
    public static final DyeColor CYAN       = new DyeColor("CYAN", 9, 0x9, 0x6, Color.fromRGB(0x4C7F99), Color.fromRGB(0x287697));
    public static final DyeColor PURPLE     = new DyeColor("PURPLE", 10, 0xA, 0x5, Color.fromRGB(0x7F3FB2), Color.fromRGB(0x7B2FBE));
    public static final DyeColor BLUE       = new DyeColor("BLUE", 11, 0xB, 0x4, Color.fromRGB(0x334CB2), Color.fromRGB(0x253192));
    public static final DyeColor BROWN      = new DyeColor("BROWN", 12, 0xC, 0x3, Color.fromRGB(0x664C33), Color.fromRGB(0x51301A));
    public static final DyeColor GREEN      = new DyeColor("GREEN", 13, 0xD, 0x2, Color.fromRGB(0x667F33), Color.fromRGB(0x3B511A));
    public static final DyeColor RED        = new DyeColor("RED", 14, 0xE, 0x1, Color.fromRGB(0x993333), Color.fromRGB(0xB3312C));
    public static final DyeColor BLACK      = new DyeColor("BLACK", 15, 0xF, 0x0, Color.fromRGB(0x191919), Color.fromRGB(0x1E1B1B));

    private static final Map<String, DyeColor>   byName = new SimpleStringHashMap<>(4, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<DyeColor> byID   = new TIntObjectHashMap<>(4, SMALL_LOAD_FACTOR);
    private final String enumName;
    private final int    id;
    private final byte   blockFlag;
    private final short  itemFlag;
    private final Color  color;
    private final Color  fireworkColor;

    public DyeColor(final String enumName, final int id, final int blockFlag, final int itemFlag, final Color color, final Color fireworkColor)
    {
        this.enumName = enumName;
        this.id = id;
        this.blockFlag = (byte) blockFlag;
        this.itemFlag = (short) itemFlag;
        this.color = color;
        this.fireworkColor = fireworkColor;
    }

    public Color getFireworkColor()
    {
        return this.fireworkColor;
    }

    public short getItemFlag()
    {
        return this.itemFlag;
    }

    public byte getBlockFlag()
    {
        return this.blockFlag;
    }

    public Color getColor()
    {
        return this.color;
    }

    @Override
    public String name()
    {
        return this.enumName;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    @Override
    public DyeColor byId(final int id)
    {
        return byID.get(id);
    }

    @Override
    public DyeColor byName(final String name)
    {
        return byName.get(name);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("id", this.id).append("blockFlag", this.blockFlag).append("itemFlag", this.itemFlag).append("color", this.color).append("fireworkColor", this.fireworkColor).toString();
    }

    public static DyeColor getByLevel(final int level)
    {
        return byID.get(level);
    }

    public static DyeColor getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final DyeColor element)
    {
        byID.put(element.getId(), element);
        byName.put(element.name(), element);
    }

    static
    {
        register(WHITE);
        register(ORANGE);
        register(MAGENTA);
        register(LIGHT_BLUE);
        register(YELLOW);
        register(LIME);
        register(PINK);
        register(GRAY);
        register(SILVER);
        register(CYAN);
        register(PURPLE);
        register(BLUE);
        register(BROWN);
        register(GREEN);
        register(RED);
        register(BLACK);
    }
}
