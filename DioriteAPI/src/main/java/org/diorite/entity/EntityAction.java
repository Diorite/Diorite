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

package org.diorite.entity;

import java.util.function.BiConsumer;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class EntityAction extends ASimpleEnum<EntityAction>
{
    static
    {
        init(EntityAction.class, 7);
    }

    public static final EntityAction CROUCH          = new EntityAction("CROUCH", (p, args) -> p.setCrouching(true));
    public static final EntityAction UNCROUCH        = new EntityAction("UNCROUCH", (p, args) -> p.setCrouching(false));
    public static final EntityAction LEAVE_BED       = new EntityAction("LEAVE_BED", null);
    public static final EntityAction START_SPRINTING = new EntityAction("START_SPRINTING", (p, args) -> p.setSprinting(true));
    public static final EntityAction STOP_SPRINTING  = new EntityAction("STOP_SPRINTING", (p, args) -> p.setSprinting(false));
    public static final EntityAction JUMP_WITH_HORSE = new EntityAction("JUMP_WITH_HORSE", null);
    public static final EntityAction OPEN_INVENTORY  = new EntityAction("OPEN_INVENTORY", null);

    private final BiConsumer<Player, Object[]> onAction; // idk if this is good idea...

    public EntityAction(final String enumName, final int enumId, final BiConsumer<Player, Object[]> onAction)
    {
        super(enumName, enumId);
        this.onAction = onAction;
    }

    public EntityAction(final String enumName, final BiConsumer<Player, Object[]> onAction)
    {
        super(enumName);
        this.onAction = onAction;
    }

    public BiConsumer<Player, Object[]> getOnAction()
    {
        return this.onAction;
    }

    public void doAction(final Player player, final Object... args)
    {
        if (this.onAction != null)
        {
            this.onAction.accept(player, args);
        }
    }

    /**
     * Register new {@link EntityAction} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final EntityAction element)
    {
        ASimpleEnum.register(EntityAction.class, element);
    }

    /**
     * Get one of {@link EntityAction} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static EntityAction getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(EntityAction.class, ordinal);
    }

    /**
     * Get one of {@link EntityAction} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static EntityAction getByEnumName(final String name)
    {
        return getByEnumName(EntityAction.class, name);
    }

    /**
     * @return all values in array.
     */
    public static EntityAction[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(EntityAction.class);
        return (EntityAction[]) map.values(new EntityAction[map.size()]);
    }

    static
    {
        EntityAction.register(CROUCH);
        EntityAction.register(UNCROUCH);
        EntityAction.register(LEAVE_BED);
        EntityAction.register(START_SPRINTING);
        EntityAction.register(STOP_SPRINTING);
        EntityAction.register(JUMP_WITH_HORSE);
        EntityAction.register(OPEN_INVENTORY);
    }
}
