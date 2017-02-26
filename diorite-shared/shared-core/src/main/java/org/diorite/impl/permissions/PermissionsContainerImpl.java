/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import javax.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.permissions.perm.AdvancedPermissionEntry;
import org.diorite.impl.permissions.perm.CheckExtendedPermission;
import org.diorite.impl.permissions.perm.PermissionEntry;
import org.diorite.impl.permissions.perm.PermissionImpl;
import org.diorite.impl.permissions.perm.PermissionImplEntry;
import org.diorite.SharedAPI;
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
    private static final float LOAD_FACTOR = 0.2F;

    protected @Nullable PermissionsContainer parent;
    protected Map<Permission, PermissionImplEntry>                    permissions         = new ConcurrentHashMap<>(10, LOAD_FACTOR);
    protected Map<ExtendedPermissionPattern, AdvancedPermissionEntry> advancedPermissions = new ConcurrentHashMap<>(10, LOAD_FACTOR);
    protected @Nullable WeakReference<Permissible> permissible;

    protected PermissionsContainerImpl()
    {
    }

    protected PermissionsContainerImpl(Permissible permissible)
    {
        this.permissible = new WeakReference<>(permissible);
    }

    protected PermissionsContainerImpl(PermissionsContainerImpl parent)
    {
        this.parent = parent;
    }

    public void setParent(PermissionsContainerImpl parent)
    {
        this.parent = parent;
    }

    private synchronized void addChildren(Permission permission, Set<PermissionPattern> cyclic)
    {
        if (permission.containsPermissions())
        {
            this.addChildren(permission.getPattern(), cyclic);
        }
    }

    private synchronized void addChildren(PermissionPattern pat, Set<PermissionPattern> cyclic)
    {
        if (! pat.containsPermissions())
        {
            return;
        }
        for (Entry<Permission, PermissionLevel> entry : pat.getPermissions().entrySet())
        {
            Permission permission = entry.getKey();
            PermissionPattern pattern = permission.getPattern();
            if (permission instanceof PermissionImpl)
            {
                this.permissions.put(permission, new PermissionImplEntry(permission, entry.getValue(), true));
            }
            else if (permission instanceof AdvancedPermission)
            {
                this.advancedPermissions
                        .put((ExtendedPermissionPattern) pattern, new AdvancedPermissionEntry((AdvancedPermission) permission, entry.getValue(), true));
            }
            else
            {
                throw new UnsupportedOperationException("Unknown type of permission!");
            }
            if (! cyclic.add(pattern))
            {
                throw new CyclicPermissionsException(pattern);
            }
            this.addChildren(pattern, cyclic);
        }
    }

    @Override
    public PermissionLevel getPermissionLevel(Permission permission)
    {
        PermissionEntry<?> entry;
        if (permission instanceof PermissionImpl)
        {
            entry = this.permissions.get(permission);
        }
        else if ((permission instanceof CheckExtendedPermission) || (permission instanceof AdvancedPermission))
        {
            entry = this.advancedPermissions.get(permission.getPattern());
        }
        else
        {
            throw new UnsupportedOperationException("Unknown type of permission!");
        }

        if ((entry == null) || ((permission instanceof CheckExtendedPermission) && (entry.getPermission() instanceof AdvancedPermission) &&
                                ! isMatching((AdvancedPermission) entry.getPermission(), (CheckExtendedPermission) permission)))
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
    protected static boolean isMatching(AdvancedPermission userPermission, CheckExtendedPermission checkPermission)
    {
        Object[] user = userPermission.getData();
        Object[] check = checkPermission.getData();
        SpecialGroup[] groups = userPermission.getPattern().getGroups();
        if ((user.length != groups.length) || (check == null) || (check.length != groups.length))
        {
            return false;
        }
        for (int i = 0, groups1Length = groups.length; i < groups1Length; i++)
        {
            SpecialGroup s = groups[i];
            if (! s.isMatching(user[i], check[i]))
            {
                return false;
            }
        }
        return true;
    }

    protected static void validateType(Permission perm)
    {
        if (perm instanceof CheckExtendedPermission)
        {
            throw new RuntimeException("You can't use this type of permission.");
        }
    }

    @Override
    public synchronized void setPermission(Permission permission, @Nullable PermissionLevel level)
    {
        validateType(permission);
        PermissionsManager mag = getManager();
        if (! mag.isRegisteredPermission(permission))
        {
            mag.registerPermission(permission);
        }
        if (permission instanceof PermissionImpl)
        {
            if (level == null)
            {
                PermissionImplEntry entry = this.permissions.get(permission);
                if (! entry.isFromParent())
                {
                    this.permissions.remove(permission);
                }
                return;
            }
            PermissionPattern pattern = permission.getPattern();
            Set<PermissionPattern> cyclic = new HashSet<>(50, .7f);
            cyclic.add(pattern);
            this.addChildren(pattern, cyclic);
            this.permissions.put(permission, new PermissionImplEntry(permission, level, false));
            return;
        }
        if (permission instanceof AdvancedPermission)
        {
            ExtendedPermissionPattern pattern = (ExtendedPermissionPattern) permission.getPattern();
            if (level == null)
            {
                AdvancedPermissionEntry entry = this.advancedPermissions.get(pattern);
                if (! entry.isFromParent())
                {
                    this.advancedPermissions.remove(pattern);
                }
                return;
            }
            Set<PermissionPattern> cyclic = new HashSet<>(50, .7f);
            cyclic.add(pattern);
            this.addChildren(pattern, cyclic);
            this.advancedPermissions.put(pattern, new AdvancedPermissionEntry((AdvancedPermission) permission, level, false));
            return;
        }
        throw new UnsupportedOperationException("Unknown type of permission!");
    }

    @Override
    public synchronized void removePermission(Permission permission)
    {
        this.setPermission(permission, null);
    }

    @Override
    public synchronized void recalculatePermissions()
    {
        {
            Map<Permission, PermissionImplEntry> tempPerms = this.permissions;
            this.permissions = new HashMap<>(tempPerms.size(), .5f);
            Map<Permission, PermissionImplEntry> tempMap = new HashMap<>(tempPerms.size());
            for (Entry<Permission, PermissionImplEntry> entry : tempPerms.entrySet())
            {
                PermissionImplEntry permissionEntry = entry.getValue();
                Permission permission = entry.getKey();
                if (permission instanceof CheckExtendedPermission)
                {
                    continue;
                }
                if (permissionEntry.isFromParent())
                {
                    continue;
                }
                tempMap.put(permission, permissionEntry);
                Set<PermissionPattern> cyclic = new HashSet<>(50, .7f);
                cyclic.add(permission.getPattern());
                this.addChildren(permission, cyclic);
            }
            this.permissions.putAll(tempMap);
        }
        {
            Map<ExtendedPermissionPattern, AdvancedPermissionEntry> tempPerms = this.advancedPermissions;
            this.advancedPermissions = new HashMap<>(tempPerms.size(), .4f);
            Map<ExtendedPermissionPattern, AdvancedPermissionEntry> tempMap = new HashMap<>(tempPerms.size());
            for (Entry<ExtendedPermissionPattern, AdvancedPermissionEntry> entry : tempPerms.entrySet())
            {
                AdvancedPermissionEntry permissionEntry = entry.getValue();
                if (permissionEntry.isFromParent())
                {
                    continue;
                }
                ExtendedPermissionPattern pattern = entry.getKey();
                tempMap.put(pattern, permissionEntry);
                Set<PermissionPattern> cyclic = new HashSet<>(50, .7f);
                cyclic.add(pattern);
                this.addChildren(pattern, cyclic);
            }
            this.advancedPermissions.putAll(tempMap);
        }
    }

    protected static PermissionsManager getManager()
    {
        return SharedAPI.getSharedAPI().getPermissionsManager();
    }

    @Override
    public boolean hasPermission(String permission)
    {
        PermissionsManager mag = getManager();
        PermissionPattern pat = mag.getPatternByPermission(permission);
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
    public boolean hasPermission(String pattern, String permission)
    {
        PermissionsManager mag = getManager();
        Permission perm = mag.getPermission(pattern);
        if (perm != null)
        {
            return this.hasPermission(perm.getPattern(), permission);
        }
        return this.hasPermission(mag.createPermission(pattern, permission, PermissionLevel.OP));
    }

    @Override
    public boolean hasPermission(PermissionPattern pattern, String permission)
    {
        PermissionsManager mag = getManager();
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
    public boolean hasPermission(Permission permission)
    {
        PermissionsManager mag = getManager();
        Permissible permissible = this.getPermissible();
        boolean op = false;
        if ((permissible instanceof ServerOperator))
        {
            op = ((ServerOperator) permissible).isOp();
        }
        PermissionLevel level = mag.onPermissionCheck(null, permission, this.getPermissionLevel(permission));
        return level.getValue(op);
    }

    @Override
    public PermissionsContainer getPermissionsContainer()
    {
        return this;
    }

    @Nullable
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
