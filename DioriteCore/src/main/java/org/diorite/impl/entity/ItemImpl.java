package org.diorite.impl.entity;

import java.util.UUID;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.connection.packets.play.out.PacketPlayOut;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutCollect;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutEntityMetadata;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutSpawnEntity;
import org.diorite.impl.entity.meta.entry.EntityMetadataItemStackEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.Entity;
import org.diorite.entity.EntityType;
import org.diorite.entity.Item;
import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.math.DioriteMathUtils;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public class ItemImpl extends EntityImpl implements Item, EntityObject
{
    /**
     * ItemStack entry
     */
    protected static final byte META_KEY_ITEM = 10;
    private static final   int  DESPAWN_TIME  = 30000; // 5 min, TODO: add config value for that.

    @SuppressWarnings("MagicNumber")
    public static final ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.25F, 0.25F);

    public ItemImpl(final UUID uuid, final ServerImpl server, final int id, final ImmutableLocation location)
    {
        super(uuid, server, id, location);
        this.aabb = BASE_SIZE.create(this);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.metadata.add(new EntityMetadataItemStackEntry(META_KEY_ITEM, null));
    }

    public ItemStack getItemStack()
    {
        return this.metadata.getItemStack(META_KEY_ITEM);
    }

    public void setItemStack(final ItemStack item)
    {
        this.metadata.add(new EntityMetadataItemStackEntry(META_KEY_ITEM, item));
    }

    public boolean pickUpItem(final PlayerImpl player)
    {
        // TODO Dopracowac mechanike podnoszenia w wypadku gdy gracz ma pelne eq
        if (player.getInventory().getFullEqInventory().add(this.getItemStack()).length != 0)
        {
            return false;
        }
        player.getInventory().update(); // TODO REMOVE!!!

        player.getNetworkManager().sendPacket(new PacketPlayOutCollect(this.getId(), player.getId())); // TODO Entity tracker zeby inni widzieli podnoszenie
        this.remove(true);
        return true;
    }

    private int timeLived = 0; // in centiseconds, 1/100 of second.

    @Override
    public void doTick(final int tps)
    {
        super.doTick(tps);
        if (! this.aiEnabled)
        {
            return;
        }
        this.timeLived += DioriteMathUtils.centisecondsPerTick(tps);
        if (this.timeLived >= DESPAWN_TIME)
        {
            this.remove(true);
            return;
        }

        for (final Entity entity : this.getNearbyEntities(1, 2, 1))
        {
            // TODO Dobre miejsce zeby dodac laczenie sie kilku takich samych itemkow w jeden
            if (!(entity instanceof Player))
            {
                continue;
            }

            if (this.pickUpItem((PlayerImpl)entity))
            {
                break;
            }
        }
    }

    @Override
    public PacketPlayOut getSpawnPacket()
    {
        return new PacketPlayOutSpawnEntity(this);
    }

    @Override
    public PacketPlayOut[] getSpawnPackets()
    {
        return new PacketPlayOut[]{this.getSpawnPacket(), new PacketPlayOutEntityMetadata(this, true)};
    }

    @Override
    public void setAiEnabled(final boolean aiEnabled)
    {
        super.setAiEnabled(aiEnabled);
//        this.timeLived = -1; not sure about that
    }

    @Override
    public EntityType getType()
    {
        return EntityType.ITEM;
    }

    @Override
    public int getEntityObjectData()
    {
        return 1;
    }
}
