package org.diorite.material.items.tool.armor;

import org.diorite.material.ArmorMaterial;
import org.diorite.material.ArmorType;
import org.diorite.material.items.tool.ArmorMat;

@SuppressWarnings("JavaDoc")
public abstract class ChestplateMat extends ArmorMat
{
    protected ChestplateMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, ArmorType.CHESTPLATE);
    }

    protected ChestplateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, ArmorType.CHESTPLATE);
    }

    protected ChestplateMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected ChestplateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public abstract ChestplateMat[] types();

    @Override
    public abstract ChestplateMat getType(final String type);

    @Override
    public abstract ChestplateMat getType(final int type);

    @Override
    public abstract ChestplateMat increaseDurability();

    @Override
    public abstract ChestplateMat decreaseDurability();

    @Override
    public abstract ChestplateMat setDurability(final int durability);
}
