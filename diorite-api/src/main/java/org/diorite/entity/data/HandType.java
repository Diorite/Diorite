package org.diorite.entity.data;

/**
 * Represent hand type, main or off hand. <br>
 * Every player may use other (left or right) hand as main.
 */
public enum HandType
{
    /**
     * Main hand of player.
     */
    MAIN("mainhand"),
    /**
     * Off hand of player.
     */
    OFF("offhand");
    /**
     * String representation of hand, used in NBT etc..
     */
    private final String stringType;

    HandType(final String stringType)
    {
        this.stringType = stringType;
    }

    /**
     * Returns string id of hand type, used in nbt etc...
     *
     * @return string id of hand type, used in nbt etc...
     */
    public String getStringType()
    {
        return this.stringType;
    }

    /**
     * Get one of HandType entry by its string id/type.
     *
     * @param id id of entry.
     *
     * @return one of entry or null.
     */
    public static HandType getByStringType(final String id)
    {
        if (id.equalsIgnoreCase(MAIN.stringType))
        {
            return MAIN;
        }
        if (id.equalsIgnoreCase(OFF.stringType))
        {
            return OFF;
        }
        return null;
    }

    /**
     * Returns opposite hand type, like off for main.
     *
     * @return opposite hand type, like off for main.
     */
    public HandType getOpposite()
    {
        switch (this)
        {
            case MAIN:
                return OFF;
            case OFF:
                return MAIN;
            default:
                throw new AssertionError();
        }
    }
}
