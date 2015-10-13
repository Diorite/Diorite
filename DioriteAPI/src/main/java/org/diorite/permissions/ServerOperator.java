package org.diorite.permissions;

/**
 * Represent <strong>possible</strong> server operator (Op) that should have most of permissions by default.
 */
public interface ServerOperator extends Permissible
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

    @Override
    default boolean hasPermission(final Permission permission)
    {
        final PermissionsContainer pc = this.getPermissionsContainer();
        return (pc == null) ? permission.getDefaultLevel().getValue(this.isOp()) : pc.hasPermission(permission);
    }
}
