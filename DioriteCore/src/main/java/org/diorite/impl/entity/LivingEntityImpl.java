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

package org.diorite.impl.entity;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerEntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataFloatEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.LivingEntity;

public abstract class LivingEntityImpl extends AttributableEntityImpl implements LivingEntity
{
    /**
     * float entry, hp of entity
     */
    protected static final byte META_KEY_HEALTH = 6;

    /**
     * byte entry, Potion effect color over entity
     */
    protected static final byte META_KEY_POTION_EFFECT_COLOR = 7;

    /**
     * byte/bool entry, if potion is ambiend (less visible)
     */
    protected static final byte META_KEY_POTION_IS_AMBIENT = 8;

    /**
     * byte entry, number of arrows in player.
     */
    protected static final byte META_KEY_ARROWS_IN_BODY = 9;

    /**
     * byte/bool entry, if entity have AI
     */
    protected static final byte META_KEY_NO_AI = 15;

    public LivingEntityImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.metadata.add(new EntityMetadataFloatEntry(META_KEY_HEALTH, 1F));
        this.metadata.add(new EntityMetadataIntEntry(META_KEY_POTION_EFFECT_COLOR, 0));
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_POTION_IS_AMBIENT, 0));
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_ARROWS_IN_BODY, 0));

        // test TODO: remove
//        this.metadata.add(new EntityMetadataByteEntry(META_KEY_ARROWS_IN_BODY, 127));
    }

    @Override
    public PacketPlayServer[] getSpawnPackets()
    {
        // TODO: add other stuff, like potions
        return new PacketPlayServer[]{this.getSpawnPacket(), new PacketPlayServerEntityMetadata(this, true)};
    }
}
