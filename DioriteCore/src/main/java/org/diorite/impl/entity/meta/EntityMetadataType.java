package org.diorite.impl.entity.meta;

import org.diorite.impl.entity.meta.entry.EntityMetadataBlockLocationEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataFloatEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataItemStackEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataShortEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataStringEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataVector3FEntry;

public enum EntityMetadataType
{
    /**
     * byte value type, {@link EntityMetadataByteEntry}
     */
    BYTE(0, EntityMetadataByteEntry.class),
    /**
     * short value type, {@link EntityMetadataShortEntry}
     */
    SHORT(1, EntityMetadataShortEntry.class),
    /**
     * integer value type, {@link EntityMetadataIntEntry}
     */
    INT(2, EntityMetadataIntEntry.class),
    /**
     * float value type, {@link EntityMetadataFloatEntry}
     */
    FLOAT(3, EntityMetadataFloatEntry.class),
    /**
     * String value type, {@link EntityMetadataStringEntry}
     * UTF-8 String (VarInt prefixed)
     */
    STRING(4, EntityMetadataStringEntry.class),
    /**
     * ItemStack value type, {@link EntityMetadataItemStackEntry}
     */
    ITEM_STACK(5, EntityMetadataItemStackEntry.class),
    /**
     * BlockLocation value type, {@link EntityMetadataBlockLocationEntry}
     * Int, Int, Int (x, y, z)
     */
    LOCATION(6, EntityMetadataBlockLocationEntry.class),
    /**
     * string value type, {@link EntityMetadataVector3FEntry}
     * Float, Float, Float (pitch, yaw, roll)
     */
    ROTATION(7, EntityMetadataVector3FEntry.class);

    private final int                                     id;
    private final Class<? extends EntityMetadataEntry<?>> clazz;

    EntityMetadataType(final int id, final Class<? extends EntityMetadataEntry<?>> clazz)
    {
        this.id = id;
        this.clazz = clazz;
    }

    public int getId()
    {
        return this.id;
    }

    public Class<? extends EntityMetadataEntry<?>> getClazz()
    {
        return this.clazz;
    }
}
