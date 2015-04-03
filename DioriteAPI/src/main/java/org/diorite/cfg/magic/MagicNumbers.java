package org.diorite.cfg.magic;

@SuppressWarnings("MagicNumber")
public final class MagicNumbers
{
    /*
     * ==================
     * Multithreading
     * ==================
     */
    @MagicNumber("threads.chunk.maxActions")
    public static final int THREADS__CHUNK__MAX_ACTIONS = 50;

    /*
     * ==================
     * ...
     * ==================
     */

    @MagicNumber("items.defaultStackSize")
    public static final int ITEMS__DEFAULT_STACK_SIZE = 64;

    @MagicNumber("player.moveSpeed")
    public static double PLAYER__MOVE_SPEED = 0.10000000149011612;

    /*
     * ==================
     * Material section
     * ==================
     */

    // material/stone
    @MagicNumber("material.stone.hardness")
    public static float MATERIAL__STONE__HARDNESS         = 1.5f;
    @MagicNumber("material.stone.blastResistance")
    public static float MATERIAL__STONE__BLAST_RESISTANCE = 30;

    // material/sapling
    @MagicNumber("material.sapling.hardness")
    public static float MATERIAL__SAPLING__HARDNESS         = 0;
    @MagicNumber("material.sapling.blastResistance")
    public static float MATERIAL__SAPLING__BLAST_RESISTANCE = 0;

    // material/planks
    @MagicNumber("material.planks.hardness")
    public static float MATERIAL__PLANKS__HARDNESS         = 2;
    @MagicNumber("material.planks.blastResistance")
    public static float MATERIAL__PLANKS__BLAST_RESISTANCE = 15;

    // material/log
    @MagicNumber("material.log.hardness")
    public static float MATERIAL__LOG__HARDNESS         = 2;
    @MagicNumber("material.log.blastResistance")
    public static float MATERIAL__LOG__BLAST_RESISTANCE = 10;

    // material/leaves
    @MagicNumber("material.leaves.hardness")
    public static float MATERIAL__LEAVES__HARDNESS         = 0.2f;
    @MagicNumber("material.leaves.blastResistance")
    public static float MATERIAL__LEAVES__BLAST_RESISTANCE = 1;

    // material/grass
    @MagicNumber("material.grass.hardness")
    public static float MATERIAL__GRASS__HARDNESS         = 0.6f;
    @MagicNumber("material.grass.blastResistance")
    public static float MATERIAL__GRASS__BLAST_RESISTANCE = 3;

    // material/dirt
    @MagicNumber("material.dirt.hardness")
    public static float MATERIAL__DIRT__HARDNESS         = 0.5f;
    @MagicNumber("material.dirt.blastResistance")
    public static float MATERIAL__DIRT__BLAST_RESISTANCE = 2.5f;

    // material/sand
    @MagicNumber("material.sand.hardness")
    public static float MATERIAL__SAND__HARDNESS         = 0.5f;
    @MagicNumber("material.sand.blastResistance")
    public static float MATERIAL__SAND__BLAST_RESISTANCE = 2.5f;

    // material/sponge
    @MagicNumber("material.sponge.hardness")
    public static float MATERIAL__SPONGE__HARDNESS         = 0.6f;
    @MagicNumber("material.sponge.blastResistance")
    public static float MATERIAL__SPONGE__BLAST_RESISTANCE = 3;

    // material/glass
    @MagicNumber("material.glass.hardness")
    public static float MATERIAL__GLASS__HARDNESS         = 0.3f;
    @MagicNumber("material.glass.blastResistance")
    public static float MATERIAL__GLASS__BLAST_RESISTANCE = 1.5f;

    // material/gravel
    @MagicNumber("material.gravel.hardness")
    public static float MATERIAL__GRAVEL__HARDNESS         = 0.5f;
    @MagicNumber("material.gravel.blastResistance")
    public static float MATERIAL__GRAVEL__BLAST_RESISTANCE = 2.5f;

    // material/gold ore
    @MagicNumber("material.gold_ore.hardness")
    public static float MATERIAL__GOLD_ORE__HARDNESS         = 3;
    @MagicNumber("material.gold_ore.blastResistance")
    public static float MATERIAL__GOLD_ORE__BLAST_RESISTANCE = 15;

    // material/iron ore
    @MagicNumber("material.iron_ore.hardness")
    public static float MATERIAL__IRON_ORE__HARDNESS         = 3;
    @MagicNumber("material.iron_ore.blastResistance")
    public static float MATERIAL__IRON_ORE__BLAST_RESISTANCE = 15;

    // material/coal ore
    @MagicNumber("material.coal_ore.hardness")
    public static float MATERIAL__COAL_ORE__HARDNESS         = 3;
    @MagicNumber("material.coal_ore.blastResistance")
    public static float MATERIAL__COAL_ORE__BLAST_RESISTANCE = 15;

    // material/lapis ore
    @MagicNumber("material.lapis_ore.hardness")
    public static float MATERIAL__LAPIS_ORE__HARDNESS         = 3;
    @MagicNumber("material.lapis_ore.blastResistance")
    public static float MATERIAL__LAPIS_ORE__BLAST_RESISTANCE = 15;

    // material/lapis block
    @MagicNumber("material.lapis_block.hardness")
    public static float MATERIAL__LAPIS_BLOCK__HARDNESS         = 3;
    @MagicNumber("material.lapis_block.blastResistance")
    public static float MATERIAL__LAPIS_BLOCK__BLAST_RESISTANCE = 15;

    // material/cobblestone
    @MagicNumber("material.cobblestone.hardness")
    public static float MATERIAL__COBBLESTONE__HARDNESS         = 2;
    @MagicNumber("material.cobblestone.blastResistance")
    public static float MATERIAL__COBBLESTONE__BLAST_RESISTANCE = 30;

    // material/bedrock
    @MagicNumber("material.bedrock.hardness")
    public static float MATERIAL__BEDROCK__HARDNESS         = - 1;
    @MagicNumber("material.bedrock.blastResistance")
    public static float MATERIAL__BEDROCK__BLAST_RESISTANCE = 18_000_000;

    // material/air
    @MagicNumber("material.air.hardness")
    public static float MATERIAL__AIR__HARDNESS         = 0;
    @MagicNumber("material.air.blastResistance")
    public static float MATERIAL__AIR__BLAST_RESISTANCE = 0;

    // material/water
    @MagicNumber("material.water.hardness")
    public static float MATERIAL__WATER__HARDNESS               = 100;
    @MagicNumber("material.water.blastResistance")
    public static float MATERIAL__WATER__BLAST_RESISTANCE       = 500;
    @MagicNumber("material.water.still.hardness")
    public static float MATERIAL__WATER_STILL__HARDNESS         = 100;
    @MagicNumber("material.water.still.blastResistance")
    public static float MATERIAL__WATER_STILL__BLAST_RESISTANCE = 500;

    // material/lava
    @MagicNumber("material.lava.hardness")
    public static float MATERIAL__LAVA__HARDNESS               = 100;
    @MagicNumber("material.lava.blastResistance")
    public static float MATERIAL__LAVA__BLAST_RESISTANCE       = 0;
    @MagicNumber("material.lava.still.hardness")
    public static float MATERIAL__LAVA_STILL__HARDNESS         = 100;
    @MagicNumber("material.lava.still.blastResistance")
    public static float MATERIAL__LAVA_STILL__BLAST_RESISTANCE = 500;

    /*
     * ==================
     * Attributes section
     * ==================
     */

    @MagicNumber("attributes.modifiers.sprint")
    public static double ATTRIBUTES__MODIFIERS__SPRINT = 0.30000001192092896;

    // attributes/generic.maxHealth
    @MagicNumber("attributes.types.generic.maxHealth.default")
    public static double ATTRIBUTES__GENERIC_MAX_HEALTH__DEFAULT = 20;
    @MagicNumber("attributes.types.generic.maxHealth.min")
    public static double ATTRIBUTES__GENERIC_MAX_HEALTH__MIN     = 0;
    @MagicNumber("attributes.types.generic.maxHealth.max")
    public static double ATTRIBUTES__GENERIC_MAX_HEALTH__MAX     = Integer.MAX_VALUE;

    // attributes/generic.followRange
    @MagicNumber("attributes.types.generic.followRange.default")
    public static double ATTRIBUTES__GENERIC_FOLLOW_RANGE__DEFAULT = 32;
    @MagicNumber("attributes.types.generic.followRange.min")
    public static double ATTRIBUTES__GENERIC_FOLLOW_RANGE__MIN     = 0;
    @MagicNumber("attributes.types.generic.followRange.max")
    public static double ATTRIBUTES__GENERIC_FOLLOW_RANGE__MAX     = 2048;

    // attributes/generic.knockbackResistance
    @MagicNumber("attributes.types.generic.knockbackResistance.default")
    public static double ATTRIBUTES__GENERIC_KNOCKBACK_RESISTANCE__DEFAULT = 0;
    @MagicNumber("attributes.types.generic.knockbackResistance.min")
    public static double ATTRIBUTES__GENERIC_KNOCKBACK_RESISTANCE__MIN     = 0;
    @MagicNumber("attributes.types.generic.knockbackResistance.max")
    public static double ATTRIBUTES__GENERIC_KNOCKBACK_RESISTANCE__MAX     = 1;

    // attributes/generic.movementSpeed
    @MagicNumber("attributes.types.generic.movementSpeed.default")
    public static double ATTRIBUTES__GENERIC_MOVEMENT_SPEED__DEFAULT = 0.699999988079071;
    @MagicNumber("attributes.types.generic.movementSpeed.min")
    public static double ATTRIBUTES__GENERIC_MOVEMENT_SPEED__MIN     = 0;
    @MagicNumber("attributes.types.generic.movementSpeed.max")
    public static double ATTRIBUTES__GENERIC_MOVEMENT_SPEED__MAX     = Double.MAX_VALUE;

    // attributes/generic.attackDamage
    @MagicNumber("attributes.types.generic.attackDamage.default")
    public static double ATTRIBUTES__GENERIC_ATTACK_DAMAGE__DEFAULT = 2;
    @MagicNumber("attributes.types.generic.attackDamage.min")
    public static double ATTRIBUTES__GENERIC_ATTACK_DAMAGE__MIN     = 0;
    @MagicNumber("attributes.types.generic.attackDamage.max")
    public static double ATTRIBUTES__GENERIC_ATTACK_DAMAGE__MAX     = Double.MAX_VALUE;

    // attributes/horse.jumpStrength
    @MagicNumber("attributes.types.horse.jumpStrength.default")
    public static double ATTRIBUTES__HORSE_JUMP_STRENGTH__DEFAULT = 0.7;
    @MagicNumber("attributes.types.horse.jumpStrength.min")
    public static double ATTRIBUTES__HORSE_JUMP_STRENGTH__MIN     = 0;
    @MagicNumber("attributes.types.horse.jumpStrength.max")
    public static double ATTRIBUTES__HORSE_JUMP_STRENGTH__MAX     = 2;

    // attributes/zombie.spawnReinforcements
    @MagicNumber("attributes.types.zombie.spawnReinforcements.default")
    public static double ATTRIBUTES__ZOMBIE_SPAWN_REINFORCMENTS__DEFAULT = 0;
    @MagicNumber("attributes.types.zombie.spawnReinforcements.min")
    public static double ATTRIBUTES__ZOMBIE_SPAWN_REINFORCMENTS__MIN     = 0;
    @MagicNumber("attributes.types.zombie.spawnReinforcements.max")
    public static double ATTRIBUTES__ZOMBIE_SPAWN_REINFORCMENTS__MAX     = 1;


    private MagicNumbers()
    {
    }
}
