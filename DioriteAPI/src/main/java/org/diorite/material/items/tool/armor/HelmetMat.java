package org.diorite.material.items.tool.armor;

import org.diorite.material.ArmorMaterial;
import org.diorite.material.ArmorType;
import org.diorite.material.items.tool.ArmorMat;

public abstract class HelmetMat extends ArmorMat
{
    protected HelmetMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, ArmorType.HELMET);
    }

    protected HelmetMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, ArmorType.HELMET);
    }

    protected HelmetMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected HelmetMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public abstract HelmetMat[] types();

    @Override
    public abstract HelmetMat getType(final String type);

    @Override
    public abstract HelmetMat getType(final int type);

    @Override
    public abstract HelmetMat increaseDurability();

    @Override
    public abstract HelmetMat decreaseDurability();

    @Override
    public abstract HelmetMat setDurability(final int durability);
}
