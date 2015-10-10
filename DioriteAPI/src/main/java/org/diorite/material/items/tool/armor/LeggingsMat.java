package org.diorite.material.items.tool.armor;

import org.diorite.material.ArmorMaterial;
import org.diorite.material.ArmorType;
import org.diorite.material.items.tool.ArmorMat;

/**
 * Abstract class for all leggings-based armors.
 */
public abstract class LeggingsMat extends ArmorMat
{
    protected LeggingsMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, ArmorType.LEGGINGS);
    }

    protected LeggingsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, ArmorType.LEGGINGS);
    }

    protected LeggingsMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected LeggingsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public abstract LeggingsMat[] types();

    @Override
    public abstract LeggingsMat getType(final String type);

    @Override
    public abstract LeggingsMat getType(final int type);

    @Override
    public abstract LeggingsMat increaseDurability();

    @Override
    public abstract LeggingsMat decreaseDurability();

    @Override
    public abstract LeggingsMat setDurability(final int durability);
}
