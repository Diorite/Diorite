package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnEntity;
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
    public PacketPlayServer getSpawnPacket()
    {
        return new PacketPlayServerSpawnEntity(this);
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

