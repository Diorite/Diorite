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
import org.diorite.impl.entity.IMinecart;
import org.diorite.impl.entity.IMinecartCommandBlock;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.ImmutableLocation;
import org.diorite.command.sender.MessageOutput;
import org.diorite.entity.EntityType;
import org.diorite.permissions.PermissionsContainer;

class MinecartCommandBlockImpl extends AbstractMinecartImpl implements IMinecartCommandBlock
{
    MinecartCommandBlockImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(IMinecart.BASE_SIZE.create(this));
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IMinecartCommandBlock.META_KEYS);
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
    }

    @Override
    public EntityType getType()
    {
        return EntityType.MINECART_COMMAND_BLOCK;
    }

    @Override
    public int getEntityObjectData()
    {
        return 0;
    }

    @Override
    public String getName()
    {
        return "MinecartCommandBlock";
    }

    // TODO: implement

    @Override
    public MessageOutput getMessageOutput()
    {
        return MessageOutput.IGNORE;
    }

    @Override
    public void setMessageOutput(final MessageOutput messageOutput)
    {

    }

    @Override
    public boolean isOp()
    {
        return true;
    }

    @Override
    public boolean setOp(final boolean op)
    {
        return false;
    }

    @Override
    public PermissionsContainer getPermissionsContainer()
    {
        return null;
    }
}

