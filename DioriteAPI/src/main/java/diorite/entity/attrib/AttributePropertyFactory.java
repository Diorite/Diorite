package diorite.entity.attrib;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import diorite.entity.Entity;

@SuppressWarnings("MagicNumber")
public abstract class AttributePropertyFactory // class with some basic entity attributes
{
    private static final Random rand = new Random();

    public static final AttributePropertyFactory RANDOM_SPAWN_BONUS = new AttributePropertyFactory()
    {
        @Override
        public Collection<AttributeProperty> get(final Entity entity)
        {
            final Set<AttributeProperty> set = new HashSet<>(2);
            set.add(new AttributeProperty(AttributeType.GENERIC_FOLLOW_RANGE, rand.nextGaussian() * 0.050000000000000003D));
            // if (entity instanceof XXX) // zombie
            set.add(new AttributeProperty(AttributeType.GENERIC_KNOCKBACK_RESISTANCE, rand.nextGaussian() * 0.05000000074505806D));
            return set;
        }
    };

    public abstract Collection<AttributeProperty> get(Entity entity);
}
