/**
 * Permission API, diorite contains default implementation of permission system, but whole system can be overridden by {@link org.diorite.ServerManager#setPermissionsManager(org.diorite.permissions.PermissionsManager)}. <br>
 * If you just want give/take permission to player/group or change/create/give groups, you can use default implementation, and always use {@link org.diorite.ServerManager#getPermissionsManager()} instead of other interfaces methods.
 */
package org.diorite.permissions;