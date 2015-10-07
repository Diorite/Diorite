package org.diorite.material.items.record;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Sound;
import org.diorite.material.ItemMaterialData;

public abstract class RecordMat extends ItemMaterialData
{
    protected final Sound sound;

    protected RecordMat(final String recordName, final int id)
    {
        super("RECORD_" + recordName.toUpperCase(), id, "minecraft:record_" + recordName.toLowerCase(), 1, recordName.toUpperCase(), (short) 0x00);
        this.sound = Sound.getByEnumName("RECORD_" + recordName.toUpperCase());
    }

    protected RecordMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, 1, typeName, type);
        this.sound = sound;
    }

    protected RecordMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.sound = sound;
    }

    public Sound getSound()
    {
        return this.sound;
    }

    @Override
    public abstract RecordMat getType(final int type);

    @Override
    public abstract RecordMat getType(final String type);

    @Override
    public abstract RecordMat[] types();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("sound", this.sound).toString();
    }
}
