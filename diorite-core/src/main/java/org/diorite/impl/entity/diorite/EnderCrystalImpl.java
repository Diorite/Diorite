/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientbound;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSpawnEntity;
import org.diorite.impl.entity.IEnderCrystal;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataBlockLocationEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataBooleanEntry;
import org.diorite.BlockLocation;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class EnderCrystalImpl extends EntityImpl implements IEnderCrystal
{
    EnderCrystalImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IEnderCrystal.META_KEYS);
    }

    @Override
    public PacketPlayClientbound getSpawnPacket()
    {
        return new PacketPlayClientboundSpawnEntity(this);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        final EntityMetadata meta = this.getMetadata();
        meta.add(new EntityMetadataBlockLocationEntry(META_KEY_ENDER_CRYSTAL_TARGET, null, true));
        meta.add(new EntityMetadataBooleanEntry(META_KEY_ENDER_CRYSTAL_SHOW_BOTTOM, true));
    }

    @Override
    public EntityType getType()
    {
        return EntityType.ENDER_CRYSTAL;
    }

    @Override
    public int getEntityObjectData()
    {
        return 0;
    }

    @Override
    public boolean isShowBottom()
    {
        return this.getMetadata().getBoolean(META_KEY_ENDER_CRYSTAL_SHOW_BOTTOM);
    }

    @Override
    public void setShowBottom(final boolean showBottom)
    {
        this.getMetadata().setBoolean(META_KEY_ENDER_CRYSTAL_SHOW_BOTTOM, showBottom);
    }

    @Override
    public BlockLocation getBeamTarget()
    {
        return this.getMetadata().getBlockLocation(META_KEY_ENDER_CRYSTAL_TARGET);
    }

    @Override
    public void setBeamTarget(final BlockLocation location)
    {
        this.getMetadata().setBlockLocation(META_KEY_ENDER_CRYSTAL_TARGET, location);
    }
}

