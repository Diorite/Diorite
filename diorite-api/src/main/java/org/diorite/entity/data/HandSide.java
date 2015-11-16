package org.diorite.entity.data;

/**
 * Represent hand side, right or left hand.
 */
public enum HandSide
{
    /**
     * Left hand of player.
     */
    LEFT,
    /**
     * Right hand of player.
     */
    RIGHT;

    /**
     * Returns opposite hand, like left for right.
     *
     * @return opposite hand, like left for right.
     */
    public HandSide getOpposite()
    {
        switch (this)
        {
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                throw new AssertionError();
        }
    }
}
