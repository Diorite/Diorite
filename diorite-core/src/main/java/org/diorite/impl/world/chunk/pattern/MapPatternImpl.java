package org.diorite.impl.world.chunk.pattern;

import java.lang.ref.SoftReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.PacketDataSerializer;

import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;

public class MapPatternImpl implements PatternData
{
    protected static final int GLOBAL_SIZE_DOWN = 4095;
    protected final TIntIntMap pattern;
    protected final TIntIntMap mirror;
    private int lastUsed = 0;

    public MapPatternImpl()
    {
        this.pattern = new TIntIntHashMap(64, .5F, - 1, 0);
        this.mirror = new TIntIntHashMap(64, .5F, 0, - 1);
        ;
    }

    private MapPatternImpl(final TIntIntMap pattern, final TIntIntMap mirror, final int lastUsed)
    {
        this.pattern = new TIntIntHashMap(pattern);
        this.mirror = new TIntIntHashMap(mirror);
        this.lastUsed = lastUsed;
    }

    public MapPatternImpl(final ArrayPatternImpl old)
    {
        this.pattern = new TIntIntHashMap(64, .5F, - 1, 0);
        this.mirror = new TIntIntHashMap(64, .5F, 0, - 1);
        for (final int mcID : old.pattern)
        {
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
    public int removeBySection(final int sectionID)
    {
        final int v = this.pattern.remove(sectionID);
        this.mirror.remove(v);
        return v;
    }

    @Override
    public int removeByMinecraft(final int minecraftID)
    {
        final int k = this.mirror.remove(minecraftID);
        if (k == - 1)
        {
            return - 1;
        }
        this.pattern.remove(k);
        return k;
    }

    @Override
    public int size()
    {
        return this.pattern.size();
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
                final TIntIntIterator it = this.pattern.iterator();
                while (it.hasNext())
                {
                    it.advance();
                    mapping[it.key()] = it.value();
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
    public void read(final PacketDataSerializer data, final int size)
    {
        for (int i = 0; i < size; i++)
        {
            final int k = data.readVarInt();
            this.pattern.put(i, k);
            this.mirror.put(k, i);
            this.lastUsed++;
        }
    }

    @Override
    public PatternData getNext()
    {
        return GlobalPatternImpl.get();
    }

    @Override
    public PatternData clone()
    {
        return new MapPatternImpl(this.pattern, this.mirror, this.lastUsed);
    }

    @Override
    public void clear()
    {

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
