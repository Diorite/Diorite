package org.diorite.material.items.tool.armor;

import org.diorite.material.ArmorMaterial;
import org.diorite.material.ArmorType;
import org.diorite.material.items.tool.ArmorMat;

@SuppressWarnings("JavaDoc")
public abstract class BootsMat extends ArmorMat
{
    protected BootsMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, ArmorType.BOOTS);
    }

    protected BootsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, ArmorType.BOOTS);
    }

    protected BootsMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected BootsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public abstract BootsMat[] types();

    @Override
    public abstract BootsMat getType(final String type);

    @Override
    public abstract BootsMat getType(final int type);

    @Override
    public abstract BootsMat increaseDurability();

    @Override
    public abstract BootsMat decreaseDurability();

    @Override
    public abstract BootsMat setDurability(final int durability);
}
