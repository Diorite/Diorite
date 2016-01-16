package org.diorite.impl.entity.diorite;

import javax.vecmath.Vector3f;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientbound;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSpawnEntity;
import org.diorite.impl.entity.IAreaEffectCloud;
import org.diorite.impl.entity.ILivingEntity;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataBooleanEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataFloatEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.ImmutableLocation;
import org.diorite.Particle;
import org.diorite.effect.StatusEffect;
import org.diorite.effect.StatusEffectType;
import org.diorite.entity.Entity;
import org.diorite.entity.EntityType;
import org.diorite.inventory.item.meta.PotionMeta.PotionTypes;
import org.diorite.utils.Color;
import org.diorite.utils.math.geometry.EntityBoundingBox;

class AreaEffectCloudImpl extends EntityImpl implements IAreaEffectCloud
{
    public static final double DEF_RADIUS_PER_CENTI = - 0.001;
    public static final double DEF_RADIUS_ON_USE    = - 0.5;

    private final Collection<StatusEffect> effects              = new CopyOnWriteArrayList<>();
    private       int                      duration             = - 1;
    private       int                      reapplicationDelay   = 100;
    private       int                      waitTime             = 0;
    private       double                   durationOnUse        = 0;
    private       double                   radiusOnUse          = DEF_RADIUS_ON_USE;
    private       double                   radiusPerCentisecond = DEF_RADIUS_PER_CENTI;
    private       String                   defaultPotionEffect  = PotionTypes.AWKWARD_POTION;
    private UUID owner;

    private transient int lastRep = 0;

    AreaEffectCloudImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        final double r = this.getRadius();
        final EntityBoundingBox box = new EntityBoundingBox(r, HEIGHT);
        box.setCenter(this);
        this.setBoundingBox(box);// TODO
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IAreaEffectCloud.META_KEYS);
    }

    @Override
    public PacketPlayClientbound getSpawnPacket()
    {
        return new PacketPlayClientboundSpawnEntity(this);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        final EntityMetadata met = this.getMetadata();
        met.add(new EntityMetadataFloatEntry(META_KEY_AREA_EFFECT_CLOUD_RADIUS, 3));
        met.add(new EntityMetadataIntEntry(META_KEY_AREA_EFFECT_CLOUD_COLOR, 0));
        met.add(new EntityMetadataBooleanEntry(META_KEY_AREA_EFFECT_CLOUD_IS_POINT, false));
        met.add(new EntityMetadataIntEntry(META_KEY_AREA_EFFECT_CLOUD_PARTICLE_ID, Particle.SPELL_MOB.getParticleId()));
    }

    @Override
    public void doTick(final int tps)
    {
        super.doTick(tps);
        if ((this.duration != - 1) && (this.age >= this.duration))
        {
            this.remove(true);
            return;
        }
        if (this.waitTime > this.age)
        {
            return;
        }
        double radius = this.getRadius();
        if (this.radiusPerCentisecond != 0)
        {
            radius += (this.radiusPerCentisecond * this.core.getCpt(tps));
            this.setRadius(radius);
        }
        if (this.age <= (this.lastRep + this.reapplicationDelay))
        {
            return;
        }
        this.lastRep = this.age;
        final Collection<? extends Entity> entitesInRadius = this.getEntitesInRadius();
        if (entitesInRadius.isEmpty())
        {
            return;
        }
        for (final Entity entity : entitesInRadius)
        {
            // TODO: arrow case
            if (entity instanceof ILivingEntity)
            {
                final ILivingEntity livingEntity = (ILivingEntity) entity;
                // TODO apply effect
                radius += this.radiusOnUse;
                this.duration += this.durationOnUse;
                if (radius <= 0)
                {
                    this.remove(true);
                    return;
                }
                this.setRadius(radius);
            }
        }
    }

    @Override
    public int getDuration()
    {
        return this.duration;
    }

    @Override
    public void setDuration(final int duration)
    {
        this.duration = duration;
    }

    @Override
    public int getReapplicationDelay()
    {
        return this.reapplicationDelay;
    }

    @Override
    public void setReapplicationDelay(final int delay)
    {
        this.reapplicationDelay = delay;
    }

    @Override
    public int getWaitTime()
    {
        return this.waitTime;
    }

    @Override
    public void setWaitTime(final int waitTime)
    {
        this.waitTime = waitTime;
    }

    @Override
    public UUID getOwner()
    {
        return this.owner;
    }

    @Override
    public void setOwner(final UUID owner)
    {
        this.owner = owner;
    }

    @Override
    public double getDurationOnUse()
    {
        return this.durationOnUse;
    }

    @Override
    public void setDurationOnUse(final double durationOnUse)
    {
        this.durationOnUse = durationOnUse;
    }

    @Override
    public double getRadiusOnUse()
    {
        return this.radiusOnUse;
    }

    @Override
    public void setRadiusOnUse(final double radiusOnUse)
    {
        this.radiusOnUse = radiusOnUse;
    }

    @Override
    public double getRadiusPerCentisecond()
    {
        return this.radiusPerCentisecond;
    }

    @Override
    public void setRadiusPerCentisecond(final double radius)
    {
        this.radiusPerCentisecond = radius;
    }

    @Override
    public String getDefaultPotionEffect()
    {
        return this.defaultPotionEffect;
    }

    @Override
    public void setDefaultPotionEffect(final String effect)
    {
        this.defaultPotionEffect = effect;
    }

    @Override
    public double getRadius()
    {
        return this.metadata.getFloat(META_KEY_AREA_EFFECT_CLOUD_RADIUS);
    }

    @Override
    public void setRadius(final double r)
    {
        this.metadata.setFloat(META_KEY_AREA_EFFECT_CLOUD_RADIUS, r);
        final Vector3f size = this.getBoundingBox().getEntitySize();
        size.x = size.z = (float) r;
    }

    @Override
    public Color getColor()
    {
        return Color.fromRGB(this.metadata.getInt(META_KEY_AREA_EFFECT_CLOUD_COLOR));
    }

    @Override
    public void setColor(final Color color)
    {
        this.metadata.setInt(META_KEY_AREA_EFFECT_CLOUD_COLOR, (color == null) ? 0 : color.asRGB());
    }

    @Override
    public boolean isPointEffect()
    {
        return this.metadata.getBoolean(META_KEY_AREA_EFFECT_CLOUD_IS_POINT);
    }

    @Override
    public void setPointEffect(final boolean pointEffect)
    {
        this.metadata.setBoolean(META_KEY_AREA_EFFECT_CLOUD_IS_POINT, pointEffect);
    }

    @Override
    public Particle getParticle()
    {
        return Particle.getByParticleId(this.metadata.getInt(META_KEY_AREA_EFFECT_CLOUD_PARTICLE_ID));
    }

    @Override
    public void setParticle(final Particle particle)
    {
        this.metadata.setInt(META_KEY_AREA_EFFECT_CLOUD_PARTICLE_ID, particle.getParticleId());
    }

    @Override
    public Collection<StatusEffect> getStatusEffects()
    {
        return new CopyOnWriteArrayList<>(this.effects);
    }

    @Override
    public void addStatusEffect(final StatusEffect effect, final boolean allowDuplicates)
    {
        if (! allowDuplicates)
        {
            this.effects.removeIf(e -> e.getType().equals(effect.getType()));
        }
        this.effects.add(effect);
    }

    @Override
    public boolean removeStatusEffect(final StatusEffectType effectType)
    {
        return this.effects.removeIf(e -> e.getType().equals(effectType));
    }

    @Override
    public void clearStatusEffects()
    {
        this.effects.clear();
    }

    @Override
    public EntityType getType()
    {
        return EntityType.AREA_EFFECT_CLOUD;
    }

    @Override
    public int getEntityObjectData()
    {
        return 0;
    }
}

