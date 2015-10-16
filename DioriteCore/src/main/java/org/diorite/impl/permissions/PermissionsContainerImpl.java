/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.permissions;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.permissions.perm.AdvancedPermissionEntry;
import org.diorite.impl.permissions.perm.CheckExtendedPermission;
import org.diorite.impl.permissions.perm.PermissionEntry;
import org.diorite.impl.permissions.perm.PermissionImpl;
import org.diorite.impl.permissions.perm.PermissionImplEntry;
import org.diorite.Diorite;
import org.diorite.permissions.AdvancedPermission;
import org.diorite.permissions.CyclicPermissionsException;
import org.diorite.permissions.Permissible;
import org.diorite.permissions.Permission;
import org.diorite.permissions.PermissionLevel;
import org.diorite.permissions.PermissionsContainer;
import org.diorite.permissions.PermissionsManager;
import org.diorite.permissions.ServerOperator;
import org.diorite.permissions.pattern.ExtendedPermissionPattern;
import org.diorite.permissions.pattern.PermissionPattern;
import org.diorite.permissions.pattern.group.SpecialGroup;

public class PermissionsContainerImpl implements PermissionsContainer
{
    /**
     * Parent for this container, can be null.
     */
    protected PermissionsContainer                                    parent;
    /**
     * Map with permissions for this container, can be null to save memory.
     */
    protected Map<Permission, PermissionImplEntry>                    permissions;
    /**
     * Map with advanced permissions for this container, can be null to save memory.
     */
    protected Map<ExtendedPermissionPattern, AdvancedPermissionEntry> advancedPermissions;
    protected WeakReference<Permissible>                              permissible;

    protected PermissionsContainerImpl()
    {
    }

    protected PermissionsContainerImpl(final Permissible permissible)
    {
        this.permissible = new WeakReference<>(permissible);
    }

    protected PermissionsContainerImpl(final PermissionsContainerImpl parent)
    {
        this.parent = parent;
    }

    public void setParent(final PermissionsContainerImpl parent)
    {
        this.parent = parent;
    }

    private synchronized void addChilds(final Permission permission, final Set<PermissionPattern> cyclic)
    {
        if (permission.containsPermissions())
        {
            this.addChilds(permission.getPattern(), cyclic);
        }
    }

    private synchronized void addChilds(final PermissionPattern pat, final Set<PermissionPattern> cyclic)
    {
        if (! pat.containsPermissions())
        {
            return;
        }
        for (final Entry<Permission, PermissionLevel> entry : pat.getPermissions().entrySet())
        {
            final Permission permission = entry.getKey();
            final PermissionPattern pattern = permission.getPattern();
            if (permission instanceof PermissionImpl)
            {
                this.permissions.put(permission, new PermissionImplEntry(permission, entry.getValue(), true));
            }
            else if (permission instanceof AdvancedPermission)
            {
                this.advancedPermissions.put((ExtendedPermissionPattern) pattern, new AdvancedPermissionEntry((AdvancedPermission) permission, entry.getValue(), true));
            }
            else
            {
                throw new UnsupportedOperationException("Unknown type of permission!");
            }
            if (! cyclic.add(pattern))
            {
                throw new CyclicPermissionsException(pattern);
            }
            this.addChilds(pattern, cyclic);
        }
    }

    @Override
    public PermissionLevel getPermissionLevel(final Permission permission)
    {
        final PermissionEntry<?> entry;
        if (permission instanceof PermissionImpl)
        {
            entry = (this.permissions == null) ? null : this.permissions.get(permission);
        }
        else if ((permission instanceof CheckExtendedPermission) || (permission instanceof AdvancedPermission))
        {
            entry = (this.advancedPermissions == null) ? null : this.advancedPermissions.get(permission.getPattern());
        }
        else
        {
            throw new UnsupportedOperationException("Unknown type of permission!");
        }

        if ((entry == null) || ((permission instanceof CheckExtendedPermission) && (entry.getPermission() instanceof AdvancedPermission) && ! isMatching((AdvancedPermission) entry.getPermission(), (CheckExtendedPermission) permission)))
        {
            if (this.parent != null)
            {
                return this.parent.getPermissionLevel(permission);
            }
            return null;
        }
        validateType(entry.getPermission());
        return entry.getLevel();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected static boolean isMatching(final AdvancedPermission userPermission, final CheckExtendedPermission checkPermission)
    {
        final Object[] user = userPermission.getData();
        final Object[] check = checkPermission.getData();
        final SpecialGroup[] groups = userPermission.getPattern().getGroups();
        if ((user.length != groups.length) || (check.length != groups.length))
        {
            return false;
        }
        for (int i = 0, groups1Length = groups.length; i < groups1Length; i++)
        {
            final SpecialGroup s = groups[i];
            if (! s.isMatching(user[i], check[i]))
            {
                return false;
            }
        }
        return true;
    }

    protected static void validateType(final Permission perm)
    {
        if (perm instanceof CheckExtendedPermission)
        {
            throw new RuntimeException("You can't use this type of permission.");
        }
    }

    @Override
    public synchronized void setPermission(final Permission permission, final PermissionLevel level)
    {
        validateType(permission);
        final PermissionsManager mag = getManager();
        if (! mag.isRegisteredPermission(permission))
        {
            mag.registerPermission(permission);
        }
        if (permission instanceof PermissionImpl)
        {
            if (this.permissions == null)
            {
                if (level == null)
                {
                    return;
                }
                this.permissions = new HashMap<>(32, .5f);
            }
            if (level == null)
            {
                final PermissionImplEntry entry = this.permissions.get(permission);
                if (! entry.isFromParent())
                {
                    if ((this.permissions.remove(permission) != null) && this.permissions.isEmpty())
                    {
                        this.permissions = null;
                    }
                }
                return;
            }
            final PermissionPattern pattern = permission.getPattern();
            final Set<PermissionPattern> cyclic = new HashSet<>(50, .7f);
            cyclic.add(pattern);
            this.addChilds(pattern, cyclic);
            this.permissions.put(permission, new PermissionImplEntry(permission, level, false));
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
            final ExtendedPermissionPattern pattern = (ExtendedPermissionPattern) permission.getPattern();
            if (level == null)
            {
                final AdvancedPermissionEntry entry = this.advancedPermissions.get(pattern);
                if (! entry.isFromParent())
                {
                    if ((this.advancedPermissions.remove(pattern) != null) && this.advancedPermissions.isEmpty())
                    {
                        this.advancedPermissions = null;
                    }
                }
                return;
            }
            final Set<PermissionPattern> cyclic = new HashSet<>(50, .7f);
            cyclic.add(pattern);
            this.addChilds(pattern, cyclic);
            this.advancedPermissions.put(pattern, new AdvancedPermissionEntry((AdvancedPermission) permission, level, false));
            return;
        }
        throw new UnsupportedOperationException("Unknown type of permission!");
    }

    @Override
    public synchronized void removePermission(final Permission permission)
    {
        this.setPermission(permission, null);
    }

    @Override
    public synchronized void recalculatePermissions()
    {
        if (this.permissions != null)
        {
            final Map<Permission, PermissionImplEntry> tempPerms = this.permissions;
            this.permissions = new HashMap<>(tempPerms.size(), .5f);
            final Map<Permission, PermissionImplEntry> tempMap = new HashMap<>(tempPerms.size());
            for (final Entry<Permission, PermissionImplEntry> entry : tempPerms.entrySet())
            {
                final PermissionImplEntry permissionEntry = entry.getValue();
                final Permission permission = entry.getKey();
                if (permission instanceof CheckExtendedPermission)
                {
                    continue;
                }
                if (permissionEntry.isFromParent())
                {
                    continue;
                }
                tempMap.put(permission, permissionEntry);
                final Set<PermissionPattern> cyclic = new HashSet<>(50, .7f);
                cyclic.add(permission.getPattern());
                this.addChilds(permission, cyclic);
            }
            this.permissions.putAll(tempMap);
        }
        if (this.advancedPermissions != null)
        {
            final Map<ExtendedPermissionPattern, AdvancedPermissionEntry> tempPerms = this.advancedPermissions;
            this.advancedPermissions = new HashMap<>(tempPerms.size(), .4f);
            final Map<ExtendedPermissionPattern, AdvancedPermissionEntry> tempMap = new HashMap<>(tempPerms.size());
            for (final Entry<ExtendedPermissionPattern, AdvancedPermissionEntry> entry : tempPerms.entrySet())
            {
                final AdvancedPermissionEntry permissionEntry = entry.getValue();
                if (permissionEntry.isFromParent())
                {
                    continue;
                }
                final ExtendedPermissionPattern pattern = entry.getKey();
                tempMap.put(pattern, permissionEntry);
                final Set<PermissionPattern> cyclic = new HashSet<>(50, .7f);
                cyclic.add(pattern);
                this.addChilds(pattern, cyclic);
            }
            this.advancedPermissions.putAll(tempMap);
        }
    }

    protected static PermissionsManager getManager()
    {
        return Diorite.getServerManager().getPermissionsManager();
    }

    @Override
    public boolean hasPermission(final String permission)
    {
        final PermissionsManager mag = getManager();
        final PermissionPattern pat = mag.getPatternByPermission(permission);
        if (pat != null)
        {
            return this.hasPermission(pat, permission);
        }
        Permission perm = mag.getPermission(permission);
        if (perm == null)
        {
            perm = mag.createPermission(permission, permission, PermissionLevel.OP);
            if ((perm instanceof AdvancedPermission) && (((AdvancedPermission) perm).getData() == null))
            {
                perm = new CheckExtendedPermission(perm.getDefaultLevel(), (ExtendedPermissionPattern) perm.getPattern(), perm.getValue());
            }
        }
        return this.hasPermission(perm);
    }

    @Override
    public boolean hasPermission(final String pattern, final String permission)
    {
        final PermissionsManager mag = getManager();
        final Permission perm = mag.getPermission(pattern);
        if (perm != null)
        {
            return this.hasPermission(perm.getPattern(), permission);
        }
        return this.hasPermission(mag.createPermission(pattern, permission, PermissionLevel.OP));
    }


    @Override
    public boolean hasPermission(final PermissionPattern pattern, final String permission)
    {
        final PermissionsManager mag = getManager();
        Permission perm;
        if (pattern instanceof ExtendedPermissionPattern)
        {
            perm = new CheckExtendedPermission(PermissionLevel.OP, (ExtendedPermissionPattern) pattern, permission);
        }
        else
        {
            perm = mag.getPermission(pattern);
            if (perm == null)
            {
                perm = mag.createPermission(pattern, permission, PermissionLevel.OP);
            }
        }
        return this.hasPermission(perm);
    }

    @Override
    public boolean hasPermission(final Permission permission)
    {
        final PermissionsManager mag = getManager();
        final Permissible permissible = this.getPermissible();
        boolean op = false;
        if ((permissible instanceof ServerOperator))
        {
            op = ((ServerOperator) permissible).isOp();
        }
        final PermissionLevel level = mag.onPermissionCheck(null, permission, this.getPermissionLevel(permission));
        return level.getValue(op);
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
