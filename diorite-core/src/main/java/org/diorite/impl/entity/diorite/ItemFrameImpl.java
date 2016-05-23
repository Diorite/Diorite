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
import org.diorite.impl.entity.IItemFrame;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataItemStackEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;
import org.diorite.inventory.item.ItemStack;

class ItemFrameImpl extends EntityImpl implements IItemFrame
{
    // TODO: more complicated aabb

    ItemFrameImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IItemFrame.META_KEYS);
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
        this.metadata.add(new EntityMetadataItemStackEntry(META_KEY_ITEM_FRAME_ITEM, null));
        this.metadata.add(new EntityMetadataIntEntry(META_KEY_ITEM_FRAME_ROTATION, 0));
    }

    @Override
    public EntityType getType()
    {
        return EntityType.ITEM_FRAME;
    }

    @Override
    public int getEntityObjectData()
    {
        return 0;
    }

    @Override
    public ItemStack getItem()
    {
        return this.metadata.getItemStack(META_KEY_ITEM_FRAME_ITEM);
    }

    @Override
    public void setItem(final ItemStack item)
    {
        this.metadata.setItemStack(META_KEY_ITEM_FRAME_ITEM, item);
    }

    @Override
    public int getRotation()
    {
        return this.metadata.getInt(META_KEY_ITEM_FRAME_ROTATION);
    }

    @Override
    public void setRotation(final int rotation)
    {
        this.metadata.setInt(META_KEY_ITEM_FRAME_ROTATION, rotation);
    }
}

