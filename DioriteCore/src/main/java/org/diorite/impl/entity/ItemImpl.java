package org.diorite.impl.entity;

import java.util.UUID;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.connection.packets.play.out.PacketPlayOut;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutCollect;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutEntityMetadata;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutSpawnEntity;
import org.diorite.impl.entity.meta.entry.EntityMetadataItemStackEntry;
import org.diorite.impl.entity.tracker.BaseTracker;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;
import org.diorite.entity.Item;
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
        final ItemStack[] left = player.getInventory().getFullEqInventory().add(this.getItemStack());
        if (left.length != 0)
        {
            this.setItemStack(left[0]);
            return false;
        }

        player.getNetworkManager().sendPacket(new PacketPlayOutCollect(this.getId(), player.getId())); // TODO Entity tracker zeby inni widzieli podnoszenie
        this.remove(true);
        return true;
    }

    private int timeLived = 0; // in centiseconds, 1/100 of second.

    public void joinItem(final ItemImpl item)
    {
        //noinspection ObjectEquality
        if (this == item)
        {
            throw new IllegalStateException("Can't join to itself!");
        }
        ItemStack oi = item.getItemStack();
        final ItemStack i = this.getItemStack();
        if ((oi == null) || (i == null) || ! oi.getMaterial().equals(i.getMaterial()))
        {
            return;
        }
        oi = i.combine(oi);
        this.setItemStack(i);
        this.timeLived = 0;
        item.setItemStack(oi);
        item.timeLived = 0;
        if (oi == null)
        {
            item.remove(true);
        }
    }

    @Override
    public void onSpawn(final BaseTracker<?> tracker)
    {
        super.onSpawn(tracker);
        this.getNearbyEntities(3, 3, 3, ItemImpl.class).forEach((item) -> item.joinItem(this));
    }

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
    }

    @Override
    protected void doPhysics()
    {
        if ((this.timeLived % 100) == 0)
        {
            this.tracker.forceLocationUpdate();
        }
        super.doPhysics();
        if (this.isOnGround())
        {
            return;
        }
        this.velY *= 0.98D;
        this.velY -= 0.08D;
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
