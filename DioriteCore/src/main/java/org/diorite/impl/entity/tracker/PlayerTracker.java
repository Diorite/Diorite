package org.diorite.impl.entity.tracker;

import org.diorite.impl.connection.packets.play.out.PacketPlayOut;
import org.diorite.impl.entity.PlayerImpl;

@SuppressWarnings({"ObjectEquality", "MagicNumber"})
public class PlayerTracker extends BaseTracker<PlayerImpl>
{
    public PlayerTracker(final PlayerImpl entity)
    {
        super(entity);
    }

//    @Override
//    public void tick(final int tps, final Iterable<PlayerImpl> players)
//    {
//        super.tick(tps, players);
//        if (this.isMoving)
//        {
//            this.tracker.getWorld().getEntityTrackers().updatePlayer(this.tracker);
//        }
//    }

    @Override
    public void sendToAllExceptOwn(final PacketPlayOut packet)
    {
        this.tracked.stream().filter(p -> p != this.tracker).forEach(p -> p.getNetworkManager().sendPacket(packet));
    }


    @Override
    public void sendToAllExceptOwn(final PacketPlayOut[] packet)
    {
        this.tracked.stream().filter(p -> p != this.tracker).forEach(p -> p.getNetworkManager().sendPackets(packet));
    }
}
