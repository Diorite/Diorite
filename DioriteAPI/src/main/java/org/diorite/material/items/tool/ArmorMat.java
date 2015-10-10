package org.diorite.material.items.tool;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.ArmorData;
import org.diorite.material.ArmorMaterial;
import org.diorite.material.ArmorType;
import org.diorite.material.ItemMaterialData;

/**
 * Represents an armor item that have durability and can break when it go above {@link #getBaseDurability()} <br>
 * Armor durability types should be cached.
 */
@SuppressWarnings("JavaDoc")
public abstract class ArmorMat extends ItemMaterialData implements BreakableItemMat
{
    /**
     * Material of armor.
     */
    protected final ArmorMaterial armorMaterial;
    /**
     * Type of armor.
     */
    protected final ArmorType     armorType;

    protected ArmorMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, 1, typeName, type);
        this.armorMaterial = armorMaterial;
        this.armorType = armorType;
    }

    protected ArmorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.armorMaterial = armorMaterial;
        this.armorType = armorType;
    }

    /**
     * Retruns {@link ArmorMaterial} defined by this item.
     *
     * @return {@link ArmorMaterial} defined by this item.
     */
    public ArmorMaterial getArmorMaterial()
    {
        return this.armorMaterial;
    }

    /**
     * Retruns {@link ArmorType} defined by this item.
     *
     * @return {@link ArmorType} defined by this item.
     */
    public ArmorType getArmorType()
    {
        return this.armorType;
    }

    /**
     * Returns properties of armor defined by this item.
     *
     * @return properties of this armor.
     */
    public ArmorData getProperties()
    {
        return this.armorMaterial.getProperties(this.armorType);
    }

    @Override
    public int getBaseDurability()
    {
        return this.getProperties().getBaseDurability();
    }

    @Override
    public int getDurability()
    {
        return this.getType();
    }

    @Override
    public abstract ArmorMat[] types();

    @Override
    public abstract ArmorMat getType(final String type);

    @Override
    public abstract ArmorMat getType(final int type);

    @Override
    public abstract ArmorMat increaseDurability();

    @Override
    public abstract ArmorMat decreaseDurability();

    @Override
    public abstract ArmorMat setDurability(final int durability);

    @Override
    public boolean isArmor()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("properties", this.getProperties()).toString();
    }
}
