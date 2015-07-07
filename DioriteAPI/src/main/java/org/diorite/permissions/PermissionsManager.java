package org.diorite.permissions;

import org.diorite.plugin.DioritePlugin;

public interface PermissionsManager
{
    boolean hasPermission(Permissible permissible, String permission);

    boolean hasPermission(Permissible permissible, Permission permission);

    /**
     * Method to get plugin that is managing permission system,
     * may return null if there is no plugin for that, and server
     * is using default implementation.
     *
     * @return plugin implementing permissions system or null.
     */
    DioritePlugin getImplementingPlugin();

//    default boolean allHasPermission(final Collection<Permissible> permissibles, final String permission)
//    {
//        return ! permissibles.stream().filter(p -> ! this.hasPermission(p, permission)).findAny().isPresent();
//    }
//
//    default boolean allHasPermission(final Collection<Permissible> permissibles, final Permission permission)
//    {
//        return ! permissibles.stream().filter(p -> ! this.hasPermission(p, permission)).findAny().isPresent();
//    }
//
//    default boolean hasAllPermissions(final Permissible permissible, final Collection<String> permissions)
//    {
//        return ! permissions.stream().filter(p -> ! this.hasPermission(permissible, p)).findAny().isPresent();
//    }
//
//    default boolean hasAllPermissions(final Permissible permissible, final Collection<Permission> permissions)
//    {
//        return ! permissions.stream().filter(p -> ! this.hasPermission(permissible, p)).findAny().isPresent();
//    }

}
