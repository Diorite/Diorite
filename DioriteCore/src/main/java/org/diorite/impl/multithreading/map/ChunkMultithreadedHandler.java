package org.diorite.impl.multithreading.map;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutBlockChange;
import org.diorite.impl.multithreading.BlockBreakAction;
import org.diorite.impl.multithreading.BlockPlaceAction;
import org.diorite.BlockLocation;
import org.diorite.material.Material;
import org.diorite.world.World;

// TODO: it's only code for testing
public class ChunkMultithreadedHandler extends Thread
{
    private static final Queue<BlockBreakAction> breakActions = new ConcurrentLinkedQueue<>();
//    private static final Queue<BlockPlaceAction> placeActions = new ConcurrentLinkedQueue<>();

    public static void add(final BlockBreakAction action)
    {
        breakActions.add(action);
        synchronized (breakActions)
        {
            breakActions.notifyAll();
        }
    }

    public static void add(final BlockPlaceAction action)
    {
//        placeActions.add(action);
    }

    private final ServerImpl server;

    public ChunkMultithreadedHandler(final ServerImpl server)
    {
        super("{Diorite|Chunk}");
        this.server = server;
        this.setDaemon(true);
    }

    @Override
    public void run()
    {
        while (this.server.isRunning())
        {
            try
            {
                {
                    final BlockBreakAction action = breakActions.poll();
                    if (action != null)
                    {
                        final BlockLocation location = action.getLocation();
                        final World world = location.getWorld();
                        world.setBlock(location, Material.AIR);
                        this.server.getPlayersManager().forEach(p -> p.getWorld().equals(world), new PacketPlayOutBlockChange(location, Material.AIR));
                    }
                    else
                    {
                        try
                        {
                            synchronized (breakActions)
                            {
                                breakActions.wait();
                            }
                        } catch (final InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                {
//                final BlockPlaceAction action = placeActions.poll();
//                if (action != null)
//                {
//                    final BlockLocation location = action.getLocation();
//                    final World world = location.getWorld();
//                    final BlockMaterialDataMat materialData = action.getPlaced();
//                    world.setBlock(location, materialData);
//                    this.server.getPlayersManager().forEach(new PacketPlayOutBlockChange(location, materialData));
//                }
                }
            } catch (final Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server).toString();
    }
}
