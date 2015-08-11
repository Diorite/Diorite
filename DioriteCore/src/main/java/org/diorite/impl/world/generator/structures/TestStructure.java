package org.diorite.impl.world.generator.structures;

import java.util.Random;

import org.diorite.BlockLocation;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.structures.Structure;

public class TestStructure implements Structure
{
    @Override
    public boolean generate(final ChunkPos pos, final Random random, final BlockLocation location)
    {
        BlockMaterialData wool1;
        BlockMaterialData wool2;
        final Material[] values = BlockMaterialData.values();
        do
        {
            wool1 = (BlockMaterialData) values[random.nextInt(values.length)];
            wool2 = (BlockMaterialData) values[random.nextInt(values.length)];
            final BlockMaterialData[] w1t = wool1.types();
            final BlockMaterialData[] w2t = wool2.types();
            if (w1t.length > 1)
            {
                wool1 = w1t[random.nextInt(w1t.length)];
            }
            if (w2t.length > 1)
            {
                wool2 = w2t[random.nextInt(w2t.length)];
            }
        } while ((wool1 == null) || (wool2 == null) || ! wool1.isSolid() || ! wool2.isSolid() || wool1.equals(wool2));
        boolean s = false;
        for (int x = - 1; x <= 16; x++)
        {
            this.setBlock(location, x, 0, - 1, (s = ! s) ? wool1 : wool2);
        }
        for (int z = 0; z <= 15; z++)
        {
            this.setBlock(location, 16, 0, z, (s = ! s) ? wool1 : wool2);
        }
        for (int x = 16; x >= - 1; x--)
        {
            this.setBlock(location, x, 0, 16, (s = ! s) ? wool1 : wool2);
        }
        for (int z = 15; z >= 0; z--)
        {
            this.setBlock(location, - 1, 0, z, (s = ! s) ? wool1 : wool2);
        }
        return true;
    }
}
