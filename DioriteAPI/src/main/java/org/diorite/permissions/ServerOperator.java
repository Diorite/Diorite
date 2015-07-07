package org.diorite.permissions;

/**
 * Represent <strong>possible</strong> server operator (Op) that should have most of permissions by default.
 */
public interface ServerOperator
{
    /**
     * Check if this is representing server operator.
     *
     * @return true if this represent server operator.
     */
    boolean isOp();

    /**
     * Set if this is an op.
     *
     * @param op if this should be an op.
     *
     * @return true if state was changed.
     */
    boolean setOp(boolean op);
}
