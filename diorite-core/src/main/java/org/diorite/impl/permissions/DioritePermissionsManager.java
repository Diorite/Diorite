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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import org.diorite.impl.permissions.pattern.ExtendedPermissionPatternImpl;
import org.diorite.impl.permissions.pattern.PermissionPatternImpl;
import org.diorite.impl.permissions.perm.AdvancedPermissionImpl;
import org.diorite.impl.permissions.perm.PermissionImpl;
import org.diorite.Diorite;
import org.diorite.entity.Player;
import org.diorite.permissions.GroupEntry;
import org.diorite.permissions.GroupablePermissionsContainer;
import org.diorite.permissions.Permissible;
import org.diorite.permissions.Permission;
import org.diorite.permissions.PermissionLevel;
import org.diorite.permissions.PermissionsContainer;
import org.diorite.permissions.PermissionsGroup;
import org.diorite.permissions.PermissionsManager;
import org.diorite.permissions.PlayerPermissionsContainer;
import org.diorite.permissions.pattern.ExtendedPermissionPattern;
import org.diorite.permissions.pattern.PermissionPattern;
import org.diorite.permissions.pattern.group.SpecialGroup;
import org.diorite.permissions.pattern.group.SpecialGroupsManager;
import org.diorite.plugin.DioritePlugin;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class DioritePermissionsManager implements PermissionsManager
{
    private final Map<String, Permission>            permissionMap    = new CaseInsensitiveMap<>(200, .5f);
    private final Map<PermissionPattern, Permission> permissionPatMap = new HashMap<>(200, .5f);
    private final Set<PermissionPattern>             patterns         = new HashSet<>(50, .4f);
    private final Map<String, PermissionsGroup>      groups           = new CaseInsensitiveMap<>(10, .3f);

    @Override
    public DioritePlugin getImplementingPlugin()
    {
        return null;
    }

    @Override
    public PermissionsContainer createContainer(final Permissible permissible)
    {
        if (permissible instanceof PermissionsGroup)
        {
            return new GroupPermissionsContainerImpl((PermissionsGroup) permissible);
        }
        return new PermissionsContainerImpl();
    }

    @Override
    public PlayerPermissionsContainer createPlayerContainer(final Player permissible)
    {
        return new PlayerPermissionsContainerImpl(false, permissible);
    }

    @Override
    public GroupablePermissionsContainer createGroupableContainer(final Permissible permissible)
    {
        return new GroupablePermissionsContainerImpl();
    }

    @Override
    public Permission getPermission(final String permissionPattern)
    {
        Validate.notNull(permissionPattern, "permissionPattern can't be null.");
        return this.permissionMap.get(permissionPattern);
    }

    @Override
    public Permission getPermission(final PermissionPattern permissionPattern)
    {
        Validate.notNull(permissionPattern, "permissionPattern can't be null.");
        return this.permissionPatMap.get(permissionPattern);
    }

    @Override
    public PermissionPattern getPermissionPattern(final String permissionPattern)
    {
        Validate.notNull(permissionPattern, "permissionPattern can't be null.");
        final Permission perm = this.permissionMap.get(permissionPattern);
        if (perm != null)
        {
            return perm.getPattern();
        }
        for (final PermissionPattern pattern : this.patterns)
        {
            if (pattern.isValid(permissionPattern))
            {
                return pattern;
            }
            if (pattern instanceof ExtendedPermissionPattern)
            {
                if (((ExtendedPermissionPattern) pattern).isValidValue(permissionPattern))
                {
                    return pattern;
                }
            }
        }
        return null;
    }

    @Override
    public PermissionPattern getPatternByPermission(final String permission)
    {
        Validate.notNull(permission, "permission can't be null.");
        final Permission perm = this.permissionMap.get(permission);
        if (perm != null)
        {
            return perm.getPattern();
        }
        for (final PermissionPattern p : this.patterns)
        {
            if (p.isValid(permission))
            {
                return p;
            }
        }
        return null;
    }

    @Override
    public void registerPermission(final Permission permission)
    {
        Validate.notNull(permission, "permission can't be null.");
        final PermissionPattern pat = permission.getPattern();
        this.permissionMap.put(pat.getValue().intern(), permission);
        this.permissionPatMap.put(pat, permission);
        this.patterns.add(pat);
    }

    @Override
    public boolean unregisterPermission(final PermissionPattern pat)
    {
        final Permission perm = this.permissionPatMap.remove(pat);
        if (perm == null)
        {
            return false;
        }
        this.permissionMap.remove(pat.getValue().intern());
        this.patterns.remove(pat);
        return true;
    }

    @Override
    public boolean isRegisteredPermission(final Permission permission)
    {
        Validate.notNull(permission, "permission can't be null.");
        return permission.equals(this.permissionPatMap.get(permission.getPattern()));
    }

    @Override
    public PermissionPattern createPattern(final String permissionPattern)
    {
        Validate.notNull(permissionPattern, "permissionPattern can't be null.");
        final PermissionPattern result = this.getPermissionPattern(permissionPattern);
        if (result != null)
        {
            return result;
        }
        final String[] parts = StringUtils.split(permissionPattern, '.');
        final List<SpecialGroup<?, ?>> groups = new ArrayList<>(parts.length);
        for (final String part : parts)
        {
            final SpecialGroup<?, ?> group = SpecialGroupsManager.get(part);
            if (group != null)
            {
                groups.add(group);
            }
        }
        if (groups.isEmpty())
        {
            return new PermissionPatternImpl(permissionPattern);
        }
        return new ExtendedPermissionPatternImpl(permissionPattern, groups.toArray(new SpecialGroup<?, ?>[groups.size()]));
    }

    @Override
    public Permission createPermission(final String permission, final PermissionLevel defaultLevel)
    {
        final PermissionPattern pat = this.getPatternByPermission(permission);
        if (pat == null)
        {
            return this.createPermission(permission, permission, defaultLevel);
        }
        return this.createPermission(pat, permission, defaultLevel);
    }

    @Override
    public Permission createPermission(final String permissionPattern, final String permission, final PermissionLevel defaultLevel)
    {
        Validate.notNull(permission, "permission can't be null.");
        Validate.notNull(defaultLevel, "defaultLevel can't be null.");
        return this.createPermission(this.createPattern(permissionPattern), permission, defaultLevel);
    }

    @Override
    public Permission createPermission(final PermissionPattern permissionPattern, final String permission, final PermissionLevel defaultLevel)
    {
        Validate.notNull(permissionPattern, "permissionPattern can't be null.");
        Validate.notNull(permission, "permission can't be null.");
        Validate.notNull(defaultLevel, "defaultLevel can't be null.");
        if (permissionPattern instanceof PermissionPatternImpl)
        {
            return new PermissionImpl(defaultLevel, permissionPattern);
        }
        if (permissionPattern instanceof ExtendedPermissionPattern)
        {
            return new AdvancedPermissionImpl((ExtendedPermissionPattern) permissionPattern, defaultLevel, permission);
        }
        // TODO: supprot for special permissions
        throw new UnsupportedOperationException();
    }

    @Override
    public PermissionsGroup createGroup(final String name)
    {
        Validate.notNull(name, "name of group can't be null.");
        PermissionsGroup group = this.groups.get(name);
        if (group != null)
        {
            return group;
        }
        group = new PermissionsGroupImpl(name);
        this.groups.put(name, group);
        return group;
    }

    @Override
    public void addGroup(final PermissionsGroup group)
    {
        Validate.notNull(group, "group can't be null.");

        final PermissionsGroup oldGroup = this.groups.get(group.getName());
        if (oldGroup == null)
        {
            this.groups.put(group.getName(), group);
            return;
        }
        Diorite.getOnlinePlayers().stream().map(Player::getPermissionsContainer).forEach(p -> {
            GroupEntry entry = p.removeGroup(oldGroup);
            if (entry != null)
            {
                p.addGroup(new GroupEntry(group, entry.getPriotity()));
            }
        });
    }

    @Override
    public PermissionsGroup getGroup(final String name)
    {
        Validate.notNull(name, "name of group can't be null.");
        return this.groups.get(name);
    }

    @Override
    public boolean containsGroup(final String name)
    {
        Validate.notNull(name, "name of group can't be null.");
        return this.groups.containsKey(name);
    }

    @SuppressWarnings("ObjectEquality")
    @Override
    public boolean containsGroup(final PermissionsGroup group)
    {
        Validate.notNull(group, "group can't be null.");
        return this.groups.get(group.getName()) == group;
    }

    @Override
    public PermissionsGroup removeGroup(final String name)
    {
        Validate.notNull(name, "name of group can't be null.");
        final PermissionsGroup group = this.groups.remove(name);
        if (group != null)
        {
            Diorite.getOnlinePlayers().forEach(p -> p.getPermissionsContainer().removeGroup(group));
        }
        return group;
    }

    @Override
    public SortedSet<GroupEntry> getPermissibleGroups(final Permissible permissible)
    {
        Validate.notNull(permissible, "permissible can't be null.");
        final PermissionsContainer container = permissible.getPermissionsContainer();
        if (! (container instanceof GroupablePermissionsContainer))
        {
            return new TreeSet<>();
        }
        return ((GroupablePermissionsContainer) container).getGroups();
    }

    @Override
    public boolean addPermissibleToGroup(final Permissible permissible, final GroupEntry groupEntry)
    {
        Validate.notNull(permissible, "permissible can't be null.");
        Validate.notNull(groupEntry, "groupEntry can't be null.");
        final PermissionsContainer container = permissible.getPermissionsContainer();
        if (! (container instanceof GroupablePermissionsContainer))
        {
            return false;
        }
        final GroupablePermissionsContainer groupableContainer = (GroupablePermissionsContainer) container;
        groupableContainer.removeGroup(groupEntry.getGroup());
        groupableContainer.addGroup(groupEntry);
        return true;
    }

    @Override
    public boolean removePermissibleFromGroup(final Permissible permissible, final PermissionsGroup group)
    {
        Validate.notNull(permissible, "permissible can't be null.");
        Validate.notNull(group, "group can't be null.");
        final PermissionsContainer container = permissible.getPermissionsContainer();
        if (! (container instanceof GroupablePermissionsContainer))
        {
            return true;
        }
        final GroupablePermissionsContainer groupableContainer = (GroupablePermissionsContainer) container;
        return groupableContainer.removeGroup(group) != null;
    }

    @Override
    public boolean removePermissibleFromGroup(final Permissible permissible, final GroupEntry groupEntry)
    {
        Validate.notNull(permissible, "permissible can't be null.");
        Validate.notNull(groupEntry, "groupEntry can't be null.");
        final PermissionsContainer container = permissible.getPermissionsContainer();
        if (! (container instanceof GroupablePermissionsContainer))
        {
            return true;
        }
        final GroupablePermissionsContainer groupableContainer = (GroupablePermissionsContainer) container;
        return groupableContainer.removeGroup(groupEntry);
    }

    @Override
    public void setPermission(final Permissible permissible, final Permission permission, final PermissionLevel level)
    {
        Validate.notNull(permissible, "permissible can't be null.");
        Validate.notNull(permission, "permission can't be null.");
        final PermissionsContainer container = permissible.getPermissionsContainer();
        container.setPermission(permission, level);
    }
}
