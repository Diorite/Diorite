package org.diorite.entity.attrib;

import static org.diorite.cfg.magic.MagicNumbers.*;


import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.collections.SimpleStringHashMap;

public class AttributeType
{
    public static final  AttributeType              GENERIC_MAX_HEALTH           = new AttributeType("GENERIC_MAX_HEALTH", "generic.maxHealth", ATTRIBUTES__GENERIC_MAX_HEALTH__DEFAULT, ATTRIBUTES__GENERIC_MAX_HEALTH__MIN, ATTRIBUTES__GENERIC_MAX_HEALTH__MAX);
    public static final  AttributeType              GENERIC_FOLLOW_RANGE         = new AttributeType("GENERIC_FOLLOW_RANGE", "generic.followRange", ATTRIBUTES__GENERIC_FOLLOW_RANGE__DEFAULT, ATTRIBUTES__GENERIC_FOLLOW_RANGE__MIN, ATTRIBUTES__GENERIC_FOLLOW_RANGE__MAX);
    public static final  AttributeType              GENERIC_KNOCKBACK_RESISTANCE = new AttributeType("GENERIC_KNOCKBACK_RESISTANCE", "generic.knockbackResistance", ATTRIBUTES__GENERIC_KNOCKBACK_RESISTANCE__DEFAULT, ATTRIBUTES__GENERIC_KNOCKBACK_RESISTANCE__MIN, ATTRIBUTES__GENERIC_KNOCKBACK_RESISTANCE__MAX);
    public static final  AttributeType              GENERIC_MOVEMENT_SPEED       = new AttributeType("GENERIC_MOVEMENT_SPEED", "generic.movementSpeed", ATTRIBUTES__GENERIC_MOVEMENT_SPEED__DEFAULT, ATTRIBUTES__GENERIC_MOVEMENT_SPEED__MIN, ATTRIBUTES__GENERIC_MOVEMENT_SPEED__MAX);
    public static final  AttributeType              GENERIC_ATTACK_DAMAGE        = new AttributeType("GENERIC_ATTACK_DAMAGE", "generic.attackDamage", ATTRIBUTES__GENERIC_ATTACK_DAMAGE__DEFAULT, ATTRIBUTES__GENERIC_ATTACK_DAMAGE__MIN, ATTRIBUTES__GENERIC_ATTACK_DAMAGE__MAX);
    public static final  AttributeType              HORSE_JUMP_STRENGTH          = new AttributeType("HORSE_JUMP_STRENGTH", "horse.jumpStrength", ATTRIBUTES__HORSE_JUMP_STRENGTH__DEFAULT, ATTRIBUTES__HORSE_JUMP_STRENGTH__MIN, ATTRIBUTES__HORSE_JUMP_STRENGTH__MAX);
    public static final  AttributeType              ZOMBIE_SPAWN_REINFORCMENTS   = new AttributeType("ZOMBIE_SPAWN_REINFORCMENTS", "zombie.spawnReinforcements", ATTRIBUTES__ZOMBIE_SPAWN_REINFORCMENTS__DEFAULT, ATTRIBUTES__ZOMBIE_SPAWN_REINFORCMENTS__MIN, ATTRIBUTES__ZOMBIE_SPAWN_REINFORCMENTS__MAX);
    private static final Map<String, AttributeType> byName                       = new SimpleStringHashMap<>(7, .1f);
    private static final Map<String, AttributeType> byKey                        = new SimpleStringHashMap<>(7, .1f);
    private final String enumName;

    private final String key;
    private final double defaultValue;
    private final double minValue;
    private final double maxValue;

    public AttributeType(final String enumName, final String key, final double defaultValue, final double minValue, final double maxValue)
    {
        this.enumName = enumName;
        this.key = key;
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public String name()
    {
        return this.enumName;
    }

    public String getKey()
    {
        return this.key;
    }

    public double getDefaultValue()
    {
        return this.defaultValue;
    }

    public double getMinValue()
    {
        return this.minValue;
    }

    public double getMaxValue()
    {
        return this.maxValue;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("key", this.key).append("defaultValue", this.defaultValue).append("minValue", this.minValue).append("maxValue", this.maxValue).toString();
    }

    public static AttributeType getByKey(final String key)
    {
        return byKey.get(key);
    }

    public static AttributeType getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final AttributeType element)
    {
        byName.put(element.name(), element);
        byKey.put(element.getKey(), element);
    }

    static
    {
        register(GENERIC_MAX_HEALTH);
        register(GENERIC_FOLLOW_RANGE);
        register(GENERIC_KNOCKBACK_RESISTANCE);
        register(GENERIC_MOVEMENT_SPEED);
        register(GENERIC_ATTACK_DAMAGE);
        register(HORSE_JUMP_STRENGTH);
        register(ZOMBIE_SPAWN_REINFORCMENTS);
    }
}
