package org.diorite.material;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;
import org.diorite.utils.collections.maps.SimpleEnumMap;

import gnu.trove.map.TIntObjectMap;

/**
 * Represent material of armor.
 */
@SuppressWarnings({"ClassHasNoToStringMethod", "MagicNumber"})
public class ArmorMaterial extends ASimpleEnum<ArmorMaterial>
{
    public static final ArmorMaterial LEATHER;
    public static final ArmorMaterial IRON;
    public static final ArmorMaterial CHAINMAIL; // can't be crafted :<
    public static final ArmorMaterial GOLD;
    public static final ArmorMaterial DIAMOND;

    static
    {
        init(ArmorMaterial.class, 5);
        {
            final SimpleEnumMap<ArmorType, ArmorMaterialTypeData> properties = new SimpleEnumMap<>(4);
            LEATHER = new ArmorMaterial("LEATHER", Material.LEATHER, 15, properties);
            properties.put(ArmorType.HELMET, new ArmorMaterialTypeData(LEATHER, ArmorType.HELMET, 56, 1));
            properties.put(ArmorType.CHESTPLATE, new ArmorMaterialTypeData(LEATHER, ArmorType.CHESTPLATE, 81, 3));
            properties.put(ArmorType.LEGGINGS, new ArmorMaterialTypeData(LEATHER, ArmorType.LEGGINGS, 76, 2));
            properties.put(ArmorType.BOOTS, new ArmorMaterialTypeData(LEATHER, ArmorType.BOOTS, 66, 1));
            ArmorMaterial.register(LEATHER);
        }
        {
            final SimpleEnumMap<ArmorType, ArmorMaterialTypeData> properties = new SimpleEnumMap<>(4);
            IRON = new ArmorMaterial("IRON", Material.IRON_INGOT, 9, properties);
            properties.put(ArmorType.HELMET, new ArmorMaterialTypeData(IRON, ArmorType.HELMET, 166, 2));
            properties.put(ArmorType.CHESTPLATE, new ArmorMaterialTypeData(IRON, ArmorType.CHESTPLATE, 241, 6));
            properties.put(ArmorType.LEGGINGS, new ArmorMaterialTypeData(IRON, ArmorType.LEGGINGS, 226, 5));
            properties.put(ArmorType.BOOTS, new ArmorMaterialTypeData(IRON, ArmorType.BOOTS, 196, 2));
            ArmorMaterial.register(IRON);
        }
        {
            final SimpleEnumMap<ArmorType, ArmorMaterialTypeData> properties = new SimpleEnumMap<>(4);
            CHAINMAIL = new ArmorMaterial("CHAINMAIL", Material.FIRE, 12, properties);
            properties.put(ArmorType.HELMET, new ArmorMaterialTypeData(CHAINMAIL, ArmorType.HELMET, 166, 2));
            properties.put(ArmorType.CHESTPLATE, new ArmorMaterialTypeData(CHAINMAIL, ArmorType.CHESTPLATE, 241, 5));
            properties.put(ArmorType.LEGGINGS, new ArmorMaterialTypeData(CHAINMAIL, ArmorType.LEGGINGS, 226, 4));
            properties.put(ArmorType.BOOTS, new ArmorMaterialTypeData(CHAINMAIL, ArmorType.BOOTS, 196, 1));
            ArmorMaterial.register(CHAINMAIL);
        }
        {
            final SimpleEnumMap<ArmorType, ArmorMaterialTypeData> properties = new SimpleEnumMap<>(4);
            GOLD = new ArmorMaterial("GOLD", Material.GOLD_INGOT, 25, properties);
            properties.put(ArmorType.HELMET, new ArmorMaterialTypeData(GOLD, ArmorType.HELMET, 78, 2));
            properties.put(ArmorType.CHESTPLATE, new ArmorMaterialTypeData(GOLD, ArmorType.CHESTPLATE, 113, 5));
            properties.put(ArmorType.LEGGINGS, new ArmorMaterialTypeData(GOLD, ArmorType.LEGGINGS, 106, 3));
            properties.put(ArmorType.BOOTS, new ArmorMaterialTypeData(GOLD, ArmorType.BOOTS, 92, 1));
            ArmorMaterial.register(GOLD);
        }
        {
            final SimpleEnumMap<ArmorType, ArmorMaterialTypeData> properties = new SimpleEnumMap<>(4);
            DIAMOND = new ArmorMaterial("DIAMOND", Material.DIAMOND, 10, properties);
            properties.put(ArmorType.HELMET, new ArmorMaterialTypeData(DIAMOND, ArmorType.HELMET, 364, 3));
            properties.put(ArmorType.CHESTPLATE, new ArmorMaterialTypeData(DIAMOND, ArmorType.CHESTPLATE, 529, 8));
            properties.put(ArmorType.LEGGINGS, new ArmorMaterialTypeData(DIAMOND, ArmorType.LEGGINGS, 496, 6));
            properties.put(ArmorType.BOOTS, new ArmorMaterialTypeData(DIAMOND, ArmorType.BOOTS, 430, 3));
            ArmorMaterial.register(DIAMOND);
        }
    }

    protected final Material                                        material;
    protected final int                                             enchantability;
    protected final SimpleEnumMap<ArmorType, ArmorMaterialTypeData> properties;

    protected ArmorMaterial(final String enumName, final int enumId, final Material material, final int enchantability, final SimpleEnumMap<ArmorType, ArmorMaterialTypeData> properties)
    {
        super(enumName, enumId);
        this.material = material;
        this.enchantability = enchantability;
        this.properties = properties;
    }

    protected ArmorMaterial(final String enumName, final Material material, final int enchantability, final SimpleEnumMap<ArmorType, ArmorMaterialTypeData> properties)
    {
        super(enumName);
        this.material = material;
        this.enchantability = enchantability;
        this.properties = properties;
    }

    /**
     * Returns base material item of armor, like iron ingot for iron armor. <br>
     * Crafting don't needs to match this material.
     *
     * @return base material item of armor.
     */
    public Material getMaterial()
    {
        return this.material;
    }

    /**
     * Returns enchantability level of armor, used to get possible enchantemnts using enchanting table.
     *
     * @return enchantability level of armor.
     */
    public int getEnchantability()
    {
        return this.enchantability;
    }

    /**
     * Returns properties of armor for given armor element type.
     *
     * @param armorType type of armor element.
     *
     * @return properties of armor.
     */
    public ArmorMaterialTypeData getProperties(final ArmorType armorType)
    {
        return this.properties.get(armorType);
    }

    /**
     * Register new {@link ArmorMaterial} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ArmorMaterial element)
    {
        ASimpleEnum.register(ArmorMaterial.class, element);
    }

    /**
     * Get one of {@link ArmorMaterial} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ArmorMaterial getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ArmorMaterial.class, ordinal);
    }

    /**
     * Get one of ToolMaterial entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ArmorMaterial getByEnumName(final String name)
    {
        return getByEnumName(ArmorMaterial.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ArmorMaterial[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(ArmorMaterial.class);
        return (ArmorMaterial[]) map.values(new ArmorMaterial[map.size()]);
    }

    /**
     * Represents armor properties.
     */
    public static class ArmorMaterialTypeData
    {
        protected final ArmorMaterial armorMaterial;
        protected final ArmorType     armorType;
        protected final int           baseDurability;
        protected final int           defense;

        /**
         * Construct new armor properties.
         *
         * @param armorMaterial  material of armor.
         * @param armorType      type of armor.
         * @param baseDurability base durability of armor.
         * @param defense        defence points of armor.
         */
        public ArmorMaterialTypeData(final ArmorMaterial armorMaterial, final ArmorType armorType, final int baseDurability, final int defense)
        {
            Validate.notNull(armorMaterial, "Armor material can't be null.");
            Validate.notNull(armorType, "Armor type can't be null.");
            this.armorMaterial = armorMaterial;
            this.armorType = armorType;
            this.baseDurability = baseDurability;
            this.defense = defense;
        }

        /**
         * Get {@link ArmorMaterial} defined by this properties.
         *
         * @return {@link ArmorMaterial} defined by this properties.
         */
        public ArmorMaterial getArmorMaterial()
        {
            return this.armorMaterial;
        }

        /**
         * Get {@link ArmorType} defined by this properties.
         *
         * @return {@link ArmorType} defined by this properties.
         */
        public ArmorType getArmorType()
        {
            return this.armorType;
        }

        /**
         * Returns base durability of this armor.
         *
         * @return base durability of this armor.
         */
        public int getBaseDurability()
        {
            return this.baseDurability;
        }

        /**
         * Returns defense points added by this armor.
         *
         * @return defense points added by this armor.
         */
        public int getDefense()
        {
            return this.defense;
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (! (o instanceof ArmorMaterialTypeData))
            {
                return false;
            }

            final ArmorMaterialTypeData that = (ArmorMaterialTypeData) o;

            return (this.baseDurability == that.baseDurability) && (this.defense == that.defense) && this.armorMaterial.equals(that.armorMaterial) && this.armorType.equals(that.armorType);

        }

        @Override
        public int hashCode()
        {
            int result = this.armorMaterial.hashCode();
            result = (31 * result) + this.armorType.hashCode();
            result = (31 * result) + this.baseDurability;
            result = (31 * result) + this.defense;
            return result;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("armorMaterial", this.armorMaterial).append("armorType", this.armorType).append("baseDurability", this.baseDurability).append("defense", this.defense).toString();
        }
    }
}