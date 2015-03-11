package diorite.entity.attrib;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.utils.collections.SimpleStringHashMap;

@SuppressWarnings("MagicNumber")
public class AttributeType
{
    public static final  AttributeType              GENERIC_MAX_HEALTH           = new AttributeType("GENERIC_MAX_HEALTH", "generic.maxHealth", 20.0, 0.0, Double.MAX_VALUE);
    public static final  AttributeType              GENERIC_FOLLOW_RANGE         = new AttributeType("GENERIC_FOLLOW_RANGE", "generic.followRange", 32.0, 0.0, 2048.0);
    public static final  AttributeType              GENERIC_KNOCKBACK_RESISTANCE = new AttributeType("GENERIC_KNOCKBACK_RESISTANCE", "generic.knockbackResistance", 0, 0.0, 1.0);
    public static final  AttributeType              GENERIC_MOVEMENT_SPEED       = new AttributeType("GENERIC_MOVEMENT_SPEED", "generic.movementSpeed", 0.699999988079071, 0.0, Double.MAX_VALUE);
    public static final  AttributeType              GENERIC_ATTACK_DAMAGE        = new AttributeType("GENERIC_ATTACK_DAMAGE", "generic.attackDamage", 2.0, 0.0, Double.MAX_VALUE);
    public static final  AttributeType              HORSE_JUMP_STRENGTH          = new AttributeType("HORSE_JUMP_STRENGTH", "horse.jumpStrength", 0.7, 0.0, 2.0);
    public static final  AttributeType              ZOMBIE_SPAWN_REINFORCMENTS   = new AttributeType("ZOMBIE_SPAWN_REINFORCMENTS", "zombie.spawnReinforcements", 0.0, 0.0, 1.0);
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
