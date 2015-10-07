package org.diorite.material;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

/**
 * Simple enum for types of wood.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class WoodType extends ASimpleEnum<WoodType>
{
    static
    {
        init(WoodType.class, 6);
    }

    public static final WoodType OAK      = new WoodType("OAK", 0b0000, 0x00, false);
    public static final WoodType SPRUCE   = new WoodType("SPRUCE", 0b0001, 0x01, false);
    public static final WoodType BIRCH    = new WoodType("BIRCH", 0b0010, 0x02, false);
    public static final WoodType JUNGLE   = new WoodType("JUNGLE", 0b0011, 0x03, false);
    public static final WoodType ACACIA   = new WoodType("ACACIA", 0b0000, 0x04, true);
    public static final WoodType DARK_OAK = new WoodType("DARK_OAK", 0b0001, 0x05, true);

    private final boolean secondLogID;
    private final byte    logFlag;
    private final byte    planksMeta;

    protected WoodType(final String enumName, final int ordinal, final int logFlag, final int planksMeta, final boolean secondLogID)
    {
        super(enumName, ordinal);
        this.secondLogID = secondLogID;
        this.planksMeta = (byte) planksMeta;
        this.logFlag = (byte) logFlag;
    }

    protected WoodType(final String enumName, final int logFlag, final int planksMeta, final boolean secondLogID)
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
     * Register new {@link WoodType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final WoodType element)
    {
        ASimpleEnum.register(WoodType.class, element);
    }

    /**
     * Get one of {@link WoodType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static WoodType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(WoodType.class, ordinal);
    }

    /**
     * Get one of WoodTypeMat entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static WoodType getByEnumName(final String name)
    {
        return getByEnumName(WoodType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static WoodType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(WoodType.class);
        return (WoodType[]) map.values(new WoodType[map.size()]);
    }

    static
    {
        WoodType.register(OAK);
        WoodType.register(SPRUCE);
        WoodType.register(BIRCH);
        WoodType.register(JUNGLE);
        WoodType.register(ACACIA);
        WoodType.register(DARK_OAK);
    }
}
