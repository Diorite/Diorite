package org.diorite.permissions;

import org.diorite.permissions.pattern.PermissionPattern;

/**
 * Thrown when some permission contains child that contains this permissions as own child.
 */
public class CyclicPermissionsException extends RuntimeException
{
    private static final long serialVersionUID = 0L;

    /**
     * Construct new cyclic exception using given pattern to form message.
     *
     * @param permissionPattern pattern of permissions where cyclic reference was found.
     */
    public CyclicPermissionsException(final PermissionPattern permissionPattern)
    {
        super("Detected cyclic permissions structure for " + permissionPattern.getValue());
    }

    /**
     * Constructs a new cyclic reference exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CyclicPermissionsException(final String message)
    {
        super(message);
    }
}
