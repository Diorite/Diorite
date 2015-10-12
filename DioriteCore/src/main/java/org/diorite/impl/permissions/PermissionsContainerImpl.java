package org.diorite.impl.permissions;

import java.lang.ref.WeakReference;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.permissions.AdvancedPermission;
import org.diorite.permissions.Permissible;
import org.diorite.permissions.Permission;
import org.diorite.permissions.PermissionLevel;
import org.diorite.permissions.PermissionsContainer;
import org.diorite.permissions.PermissionsManager;
import org.diorite.permissions.SimplePermission;
import org.diorite.permissions.pattern.PermissionPattern;

public class PermissionsContainerImpl implements PermissionsContainer
{
    /**
     * Parent for this container, can be null.
     */
    protected PermissionsContainer                                               parent;
    /**
     * Map with permissions for this container, can be null to save memory.
     */
    protected Map<Permission, PermissionLevel>                                   permissions;
    /**
     * Map with advanced permissions for this container, can be null to save memory.
     */
    protected Map<PermissionPattern, Entry<AdvancedPermission, PermissionLevel>> advancedPermissions;
    protected WeakReference<Permissible>                                         permissible;

    protected PermissionsContainerImpl()
    {
    }

    protected PermissionsContainerImpl(final Permissible permissible)
    {
        this.permissible = new WeakReference<>(permissible);
    }

    protected PermissionsContainerImpl(final Map<Permission, PermissionLevel> permissions)
    {
        this.permissions = new HashMap<>(permissions);
    }

    protected PermissionsContainerImpl(final PermissionsContainerImpl parent)
    {
        this.parent = parent;
    }

    protected PermissionsContainerImpl(final PermissionsContainer parent, final Map<Permission, PermissionLevel> permissions)
    {
        this.parent = parent;
        this.permissions = new HashMap<>(permissions);
    }

    public void setParent(final PermissionsContainerImpl parent)
    {
        this.parent = parent;
    }

    @Override
    public PermissionLevel getPermissionLevel(final Permission permission)
    {

        final PermissionLevel level;
        if (permission instanceof SimplePermission)
        {
            level = (this.permissions == null) ? null : this.permissions.get(permission);
        }
        else if (permission instanceof AdvancedPermission)
        {
            if (this.advancedPermissions == null)
            {
                level = null;
            }
            else
            {
                final Entry<AdvancedPermission, PermissionLevel> entry = this.advancedPermissions.get(permission.getPattern());
                level = entry.getValue();
            }
        }
        else
        {
            throw new UnsupportedOperationException("unknown type of permission.");
        }
        if (level == null)
        {
            if (this.parent != null)
            {
                return this.parent.getPermissionLevel(permission);
            }
            return permission.getDefaultLevel();
        }
        return level;
    }

    @Override
    public synchronized void setPermission(final Permission permission, final PermissionLevel level)
    {
        final PermissionsManager mag = getManager();
        if (! mag.isRegisteredPermission(permission))
        {
            mag.registerPermission(permission);
        }
        if (permission instanceof SimplePermission)
        {
            if (this.permissions == null)
            {
                if (level == null)
                {
                    return;
                }
                this.permissions = new HashMap<>(32, .5f);
            }
            if ((level == null) && (this.permissions.remove(permission) != null) && this.permissions.isEmpty()) // remove permission and check if map is empty
            {
                this.permissions = null;
                return;
            }
            this.permissions.put(permission, level);
            return;
        }
        if (permission instanceof AdvancedPermission)
        {
            if (this.advancedPermissions == null)
            {
                if (level == null)
                {
                    return;
                }
                this.advancedPermissions = new HashMap<>(16, .5f);
            }
            if ((level == null) && (this.advancedPermissions.remove(permission.getPattern()) != null) && this.advancedPermissions.isEmpty()) // remove permission and check if map is empty
            {
                this.advancedPermissions = null;
                return;
            }
            this.advancedPermissions.put(permission.getPattern(), new SimpleEntry<>((AdvancedPermission) permission, level));
            return;
        }
        throw new UnsupportedOperationException("unknown type of permission.");
    }

    @Override
    public synchronized void removePermission(final Permission permission)
    {
        this.setPermission(permission, null);
    }

    protected static PermissionsManager getManager()
    {
        return Diorite.getServerManager().getPermissionsManager();
    }

    @Override
    public boolean hasPermission(final String permission, final boolean checkAdvanced)
    {
        final PermissionsManager mag = getManager();
        if (checkAdvanced)
        {
            return this.hasPermission(mag.getPatternByPermission(permission), permission);
        }
        Permission perm = mag.getPermission(permission);
        if (perm == null)
        {
            perm = mag.createPermission(permission, permission, PermissionLevel.OP);
        }
        return this.hasPermission(perm);
    }

    @Override
    public boolean hasPermission(final String pattern, final String permission)
    {
        final PermissionsManager mag = getManager();
        Permission perm = mag.getPermission(pattern);
        if (perm == null)
        {
            perm = mag.createPermission(pattern, permission, PermissionLevel.OP);
        }
        return this.hasPermission(perm);
    }

    @Override
    public boolean hasPermission(final PermissionPattern pattern, final String permission)
    {
        final PermissionsManager mag = getManager();
        Permission perm = mag.getPermission(pattern);
        if (perm == null)
        {
            perm = mag.createPermission(pattern, permission, PermissionLevel.OP);
        }
        return this.hasPermission(perm);
    }

    @Override
    public boolean hasPermission(final Permission permission)
    {
        final PermissionsManager mag = getManager();
        final Permissible permissible = this.getPermissible();
        if (permissible != null)
        {
            return permissible.hasPermission(permission);
        }
        final PermissionLevel level = mag.onPermissionCheck(null, permission, this.getPermissionLevel(permission));

        return level.getValue(false);
    }

    @Override
    public PermissionsContainer getPermissionsContainer()
    {
        return this;
    }

    public Permissible getPermissible()
    {
        return (this.permissible == null) ? null : this.permissible.get();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("parent", this.parent).toString();
    }
}
