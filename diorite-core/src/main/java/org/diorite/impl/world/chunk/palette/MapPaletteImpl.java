package org.diorite.impl.world.chunk.palette;

import java.lang.ref.SoftReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.material.BlockMaterialData;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;


public class MapPaletteImpl implements PaletteData
{
    protected static final int GLOBAL_SIZE_DOWN = 4095;
    protected final Int2IntMap pattern;
    protected final Int2IntMap mirror;
    private int lastUsed = 0;

    public MapPaletteImpl()
    {
        this.pattern = new Int2IntOpenHashMap(64, .5F);
        this.pattern.defaultReturnValue(0);
        this.mirror = new Int2IntOpenHashMap(64, .5F);
        this.mirror.defaultReturnValue(- 1);
    }

    private MapPaletteImpl(final Int2IntMap pattern, final Int2IntMap mirror, final int lastUsed)
    {
        this.pattern = new Int2IntOpenHashMap(pattern);
        this.mirror = new Int2IntOpenHashMap(mirror);
        this.lastUsed = lastUsed;
    }

    public MapPaletteImpl(final ArrayPaletteImpl old)
    {
        this.pattern = new Int2IntOpenHashMap(64, .5F);
        this.pattern.defaultReturnValue(0);
        this.mirror = new Int2IntOpenHashMap(64, .5F);
        this.mirror.defaultReturnValue(- 1);
        for (final BlockMaterialData mat : old.pattern)
        {
            final int mcID = mat.getIdAndMeta();
            this.pattern.put(this.lastUsed, mcID);
            this.mirror.put(mcID, this.lastUsed++);
        }
    }

    @Override
    public int bitsPerBlock()
    {
        final int size = this.lastUsed;
        if (size <= 1)
        {
            return 4;
        }
        return Math.max(4, Integer.SIZE - Integer.numberOfLeadingZeros(size - 1));
    }

    @Override
    public int byteSize()
    {
        int bytes = PacketDataSerializer.varintSize(this.lastUsed);
        for (int i = 0; i < this.lastUsed; ++ i)
        {
            bytes += PacketDataSerializer.varintSize(this.pattern.get(i));
        }
        return bytes;
    }

    @Override
    public int put(final int minecraftIDandData)
    {
        if (this.pattern.containsValue(minecraftIDandData))
        {
            return this.mirror.get(minecraftIDandData);
        }
        if (this.lastUsed >= GLOBAL_SIZE_DOWN)
        {
            return - 1;
        }
        this.pattern.put(this.lastUsed, minecraftIDandData);
        return this.lastUsed++;
    }

    @Override
    public int getAsInt(final int sectionID)
    {
        return this.pattern.get(sectionID);
    }

    @Override
    public int size()
    {
        return this.lastUsed;
    }

    private transient SoftReference<int[]> ref;

    @Override
    public void write(final PacketDataSerializer data)
    {
        int[] mapping = (this.ref == null) ? null : this.ref.get();
        if (mapping == null)
        {
            synchronized (this.pattern)
            {
                mapping = new int[this.pattern.size()];
                for (final Entry entry : this.pattern.int2IntEntrySet())
                {
                    mapping[entry.getIntKey()] = entry.getIntValue();
                }
                this.ref = new SoftReference<>(mapping);
            }
        }
        data.writeVarInt(mapping.length);
        for (final int i : mapping)
        {
            data.writeVarInt(i);
        }
    }

    @Override
    public void read(final PacketDataSerializer data)
    {
        final int size = data.readVarInt();
        for (int i = 0; i < size; i++)
        {
            final int k = data.readVarInt();
            this.pattern.put(i, k);
            this.mirror.put(k, i);
            this.lastUsed++;
        }
    }

    @Override
    public PaletteData getNext()
    {
        return GlobalPaletteImpl.get();
    }

    @Override
    public PaletteData clone()
    {
        return new MapPaletteImpl(this.pattern, this.mirror, this.lastUsed);
    }

//    // for bits >13
//    private static int   lastSize = Material.getAllMaterialsCount();
//    private static int[] mapping  = null;
//
//    @SuppressWarnings("SynchronizeOnNonFinalField")
//    private static void tryUpdateMapping()
//    {
//        if (mapping == null)
//        {
//            updateMapping();
//            return;
//        }
//        synchronized (mapping)
//        {
//            if (lastSize != Material.getAllMaterialsCount())
//            {
//                lastSize = Material.getAllMaterialsCount();
//                updateMapping();
//            }
//        }
//    }
//
//    @SuppressWarnings("SynchronizeOnNonFinalField")
//    private static synchronized void updateMapping()
//    {
//        mapping = new int[Material.getAllMaterialsCount()];
//        synchronized (mapping)
//        {
//            int i = 0;
//            for (final Material material : Material.values())
//            {
//                if (! (material instanceof BlockMaterialData))
//                {
//                    continue;
//                }
//                final BlockMaterialData blockMat = (BlockMaterialData) material;
//                for (final BlockMaterialData data : blockMat.types())
//                {
//                    mapping[i++] = ((data.getId() << 4) | data.getType());
//                }
//            }
//        }
//    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).append("lastUsed", this.lastUsed).toString();
    }
}
