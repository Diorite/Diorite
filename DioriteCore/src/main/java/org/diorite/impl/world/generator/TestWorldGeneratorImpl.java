package org.diorite.impl.world.generator;

import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.others.WoolMat;
import org.diorite.material.blocks.stony.StoneMat;
import org.diorite.utils.math.DioriteRandomUtils;
import org.diorite.utils.math.noise.NoiseGenerator;
import org.diorite.utils.math.noise.SimplexNoiseGenerator;
import org.diorite.utils.math.noise.SimplexOctaveGenerator;
import org.diorite.world.Block;
import org.diorite.world.World;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.ChunkBuilder;
import org.diorite.world.generator.WorldGenerator;
import org.diorite.world.generator.WorldGeneratorInitializer;

public class TestWorldGeneratorImpl extends WorldGenerator
{
    private final NoiseGenerator generator;

    public TestWorldGeneratorImpl(final World world, final String name, final String options)
    {
        super(world, name, options);
        this.generator = new SimplexNoiseGenerator(world);
    }

    @Override
    @SuppressWarnings("MagicNumber")
    public ChunkBuilder generate(final ChunkBuilder builder, final ChunkPos pos)
    {
        // Main.debug("Generating: " + pos + ", (" + this.world.getName() + ")");
        final SimplexOctaveGenerator overhangs = new SimplexOctaveGenerator(this.world, 8);
        final SimplexOctaveGenerator bottoms = new SimplexOctaveGenerator(this.world, 8);

        overhangs.setScale(1 / 64.0);
        bottoms.setScale(1 / 128.0);

        final int overhangsMagnitude = 16; //used when we generate the noise for the tops of the overhangs
        final int bottomsMagnitude = 32;

        for (int x = 0; x < 16; x++)
        {
            for (int z = 0; z < 16; z++)
            {
                final int realX = x + (pos.getX() << 4);
                final int realZ = z + (pos.getZ() << 4);

                final int bottomHeight = (int) ((bottoms.noise(realX, realZ, 0.5, 0.5) * bottomsMagnitude) + 64);
                final int maxHeight = ((int) overhangs.noise(realX, realZ, 0.5, 0.5) * overhangsMagnitude) + bottomHeight + 32;
                final double threshold = 0.3;

                //make the terrain
                for (int y = 0; y < maxHeight; y++)
                {
                    if (y > bottomHeight)
                    { //part where we do the overhangs
                        final double density = overhangs.noise(realX, y, realZ, 0.5, 0.5);

                        if (density > threshold)
                        {
                            builder.setBlock(x, y, z, StoneMat.getByID(DioriteRandomUtils.getRandInt(3, 4)));
                        }

                    }
                    else
                    {
                        builder.setBlock(x, y, z, StoneMat.getByID(DioriteRandomUtils.getRandInt(3, 4)));
                    }
                }

                //turn the tops into grass

                builder.setBlock(x, bottomHeight, z, Material.GRASS);
                builder.setBlock(x, bottomHeight - 1, z, Material.DIRT);
                builder.setBlock(x, bottomHeight - 2, z, Material.DIRT);

                for (int y = bottomHeight + 1; (y > bottomHeight) && (y < maxHeight); y++)
                { //the overhang
                    final int thisblock = builder.getBlockType(x, y, z).getId();
                    final int blockabove = builder.getBlockType(x, y + 1, z).getId();

                    if ((thisblock != Material.AIR.getId()) && (blockabove == Material.AIR.getId()))
                    {
                        builder.setBlock(x, y, z, Material.GRASS);
                        if (builder.getBlockType(x, y - 1, z).getType() != Material.AIR.getId())
                        {
                            builder.setBlock(x, y - 1, z, Material.DIRT);
                        }
                        if (builder.getBlockType(x, y - 2, z).getType() != Material.AIR.getId())
                        {
                            builder.setBlock(x, y - 2, z, Material.DIRT);
                        }
                    }
                }

            }
        }
        return builder;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("generator", this.generator).toString();
    }

    public static WorldGeneratorInitializer<TestWorldGeneratorImpl> createInitializer()
    {
        return new WorldGeneratorInitializer<TestWorldGeneratorImpl>("default")
        {
            @Override
            public TestWorldGeneratorImpl baseInit(final World world, final String options)
            {
                return new TestWorldGeneratorImpl(world, this.name, options);
            }

            {
                final Random random = new Random();
                this.populators.add(chunk -> {
                    final World world = chunk.getWorld();
                    int centerX = (chunk.getX() << 4) + random.nextInt(16);
                    int centerZ = (chunk.getZ() << 4) + random.nextInt(16);

                    byte data = 0;
                    int chance = 0;
                    int height = 4 + random.nextInt(3);
                    int multiplier = 1;

                    if (random.nextBoolean())
                    {
                        data = 2;
                        height = 5 + random.nextInt(3);
                    }
                    chance = 160;
                    multiplier = 10;

                    for (int i = 0; i < multiplier; i++)
                    {
                        centerX = (chunk.getX() << 4) + random.nextInt(16);
                        centerZ = (chunk.getZ() << 4) + random.nextInt(16);
                        if (random.nextInt(300) < chance)
                        {

                            Block sourceBlock = world.getHighestBlock(centerX, centerZ);
                            int centerY = sourceBlock.getY();

                            if (Objects.equals(sourceBlock.getType(), Material.GRASS))
                            {
                                world.setBlock(centerX, centerY + height + 1, centerZ, BlockMaterialData.getByID(18, data));
                                for (int j = 0; j < 4; j++)
                                {
                                    world.setBlock(centerX, centerY + height + 1 - j, centerZ - 1, BlockMaterialData.getByID(18, data));
                                    world.setBlock(centerX, centerY + height + 1 - j, centerZ + 1, BlockMaterialData.getByID(18, data));
                                    world.setBlock(centerX - 1, centerY + height + 1 - j, centerZ, BlockMaterialData.getByID(18, data));
                                    world.setBlock(centerX + 1, centerY + height + 1 - j, centerZ, BlockMaterialData.getByID(18, data));
                                }

                                if (random.nextBoolean())
                                {
                                    world.setBlock(centerX + 1, centerY + height, centerZ + 1, BlockMaterialData.getByID(18, data));
                                }
                                if (random.nextBoolean())
                                {
                                    world.setBlock(centerX + 1, centerY + height, centerZ - 1, BlockMaterialData.getByID(18, data));
                                }
                                if (random.nextBoolean())
                                {
                                    world.setBlock(centerX - 1, centerY + height, centerZ + 1, BlockMaterialData.getByID(18, data));
                                }
                                if (random.nextBoolean())
                                {
                                    world.setBlock(centerX - 1, centerY + height, centerZ - 1, BlockMaterialData.getByID(18, data));
                                }

                                world.setBlock(centerX + 1, centerY + height - 1, centerZ + 1, BlockMaterialData.getByID(18, data));
                                world.setBlock(centerX + 1, centerY + height - 1, centerZ - 1, BlockMaterialData.getByID(18, data));
                                world.setBlock(centerX - 1, centerY + height - 1, centerZ + 1, BlockMaterialData.getByID(18, data));
                                world.setBlock(centerX - 1, centerY + height - 1, centerZ - 1, BlockMaterialData.getByID(18, data));
                                world.setBlock(centerX + 1, centerY + height - 2, centerZ + 1, BlockMaterialData.getByID(18, data));
                                world.setBlock(centerX + 1, centerY + height - 2, centerZ - 1, BlockMaterialData.getByID(18, data));
                                world.setBlock(centerX - 1, centerY + height - 2, centerZ + 1, BlockMaterialData.getByID(18, data));
                                world.setBlock(centerX - 1, centerY + height - 2, centerZ - 1, BlockMaterialData.getByID(18, data));

                                for (int j = 0; j < 2; j++)
                                {
                                    for (int k = - 2; k <= 2; k++)
                                    {
                                        for (int l = - 2; l <= 2; l++)
                                        {
                                            world.setBlock(centerX + k, centerY + height - 1 - j, centerZ + l, BlockMaterialData.getByID(18, data));
                                        }
                                    }
                                }

                                for (int j = 0; j < 2; j++)
                                {
                                    if (random.nextBoolean())
                                    {
                                        world.setBlock(centerX + 2, centerY + height - 1 - j, centerZ + 2, BlockMaterialData.getByID(0, (byte) 0));
                                    }
                                    if (random.nextBoolean())
                                    {
                                        world.setBlock(centerX + 2, centerY + height - 1 - j, centerZ - 2, BlockMaterialData.getByID(0, (byte) 0));
                                    }
                                    if (random.nextBoolean())
                                    {
                                        world.setBlock(centerX - 2, centerY + height - 1 - j, centerZ + 2, BlockMaterialData.getByID(0, (byte) 0));
                                    }
                                    if (random.nextBoolean())
                                    {
                                        world.setBlock(centerX - 2, centerY + height - 1 - j, centerZ - 2, BlockMaterialData.getByID(0, (byte) 0));
                                    }
                                }

                                // Trunk
                                for (int y = 1; y <= height; y++)
                                {
                                    world.setBlock(centerX, centerY + y, centerZ, BlockMaterialData.getByID(17, data));
                                }
                            }
                        }
                    }
                });
                this.populators.add(chunk -> {
                    //TODO: this is only test code
                    int r = 15;
                    int rr = 0;
                    IntStream.rangeClosed(0, 15).forEach(x -> {

                        if ((x == r) || (x == rr))
                        {
                            IntStream.rangeClosed(rr, r).forEach(z -> chunk.setBlock(x, chunk.getHighestBlock(x, z).getY(), z, WoolMat.getByID(DioriteRandomUtils.getRandInt(0, 15))));
                            return;
                        }
                        chunk.setBlock(x, chunk.getHighestBlock(x, r).getY(), r, WoolMat.getByID(DioriteRandomUtils.getRandInt(0, 15)));
                        chunk.setBlock(x, chunk.getHighestBlock(x, rr).getY(), rr, WoolMat.getByID(DioriteRandomUtils.getRandInt(0, 15)));
                    });
                });
            }
        };
    }
}
