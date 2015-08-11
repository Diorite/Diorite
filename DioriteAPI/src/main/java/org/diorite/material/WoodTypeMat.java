package org.diorite.material;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

/**
 * Simple enum for types of wood.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class WoodTypeMat extends ASimpleEnum<WoodTypeMat>
{
    static
    {
        init(WoodTypeMat.class, 6);
    }

    public static final WoodTypeMat OAK      = new WoodTypeMat("OAK", 0b0000, 0x00, false);
    public static final WoodTypeMat SPRUCE   = new WoodTypeMat("SPRUCE", 0b0001, 0x01, false);
    public static final WoodTypeMat BIRCH    = new WoodTypeMat("BIRCH", 0b0010, 0x02, false);
    public static final WoodTypeMat JUNGLE   = new WoodTypeMat("JUNGLE", 0b0011, 0x03, false);
    public static final WoodTypeMat ACACIA   = new WoodTypeMat("ACACIA", 0b0000, 0x04, true);
    public static final WoodTypeMat DARK_OAK = new WoodTypeMat("DARK_OAK", 0b0001, 0x05, true);

    private final boolean secondLogID;
    private final byte    logFlag;
    private final byte    planksMeta;

    protected WoodTypeMat(final String enumName, final int ordinal, final int logFlag, final int planksMeta, final boolean secondLogID)
    {
        super(enumName, ordinal);
        this.secondLogID = secondLogID;
        this.planksMeta = (byte) planksMeta;
        this.logFlag = (byte) logFlag;
    }

    protected WoodTypeMat(final String enumName, final int logFlag, final int planksMeta, final boolean secondLogID)
    {
        super(enumName);
        this.secondLogID = secondLogID;
        this.planksMeta = (byte) planksMeta;
        this.logFlag = (byte) logFlag;
    }

    public byte getLogFlag()
    {
        return this.logFlag;
    }

    public byte getPlanksMeta()
    {
        return this.planksMeta;
    }

    public boolean isSecondLogID()
    {
        return this.secondLogID;
    }

    /**
     * Register new {@link WoodTypeMat} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final WoodTypeMat element)
    {
        ASimpleEnum.register(WoodTypeMat.class, element);
    }

    /**
     * Get one of {@link WoodTypeMat} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static WoodTypeMat getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(WoodTypeMat.class, ordinal);
    }

    /**
     * Get one of WoodTypeMat entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static WoodTypeMat getByEnumName(final String name)
    {
        return getByEnumName(WoodTypeMat.class, name);
    }

    /**
     * @return all values in array.
     */
    public static WoodTypeMat[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(WoodTypeMat.class);
        return (WoodTypeMat[]) map.values(new WoodTypeMat[map.size()]);
    }

    static
    {
        WoodTypeMat.register(OAK);
        WoodTypeMat.register(SPRUCE);
        WoodTypeMat.register(BIRCH);
        WoodTypeMat.register(JUNGLE);
        WoodTypeMat.register(ACACIA);
        WoodTypeMat.register(DARK_OAK);
    }
}
