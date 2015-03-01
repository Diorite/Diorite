package diorite.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.impl.entity.EntityImpl;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class EntityManagerImpl
{
    private final ServerImpl server;
    private final AtomicInteger             entityCount = new AtomicInteger();
    private final TIntObjectMap<EntityImpl> map         = new TIntObjectHashMap<>(1000, .4f);

    public EntityManagerImpl(final ServerImpl server)
    {
        this.server = server;
    }

    public EntityImpl getEntity(final int id)
    {
        return this.map.get(id);
    }

    public void addEntity(final EntityImpl entity)
    {
        this.map.put(entity.getId(), entity);
    }

    public void removeEntity(final EntityImpl entity)
    {
        this.removeEntity(entity.getId());
    }

    public void removeEntity(final int id)
    {
        this.map.remove(id);
    }

    public int getNextID()
    {
        return this.entityCount.getAndIncrement();
    }

    public int getCurrentID()
    {
        return this.entityCount.get();
    }

    public ServerImpl getServer()
    {
        return this.server;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server).append("entityCount", this.entityCount).toString();
    }
}
