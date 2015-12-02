package org.diorite.inventory.item.builder;

import java.util.Collection;
import java.util.Set;

import org.diorite.inventory.item.meta.ToolMeta;
import org.diorite.material.BlockMaterialData;

/**
 * Interface of builder of tool item meta data.
 */
public interface IToolMetaBuilder<B extends IToolMetaBuilder<B, M>, M extends ToolMeta> extends IRepairableMetaBuilder<B, M>
{
    /**
     * Set if this tool is using CanDestory tag,
     * when you set it to false, add saved materials will be removed.
     *
     * @param useCanDestoryTag if this tool should use CanDestory tag.
     *
     * @return builder for method chains.
     */
    default B setUseCanDestoryTag(final boolean useCanDestoryTag)
    {
        this.meta().setUseCanDestoryTag(useCanDestoryTag);
        return this.getBuilder();
    }

    /**
     * Set if this tool is using CanDestory tag,
     * when you set it to false, add saved materials will be removed.
     *
     * @param src source item meta to copy state from it.
     *
     * @return builder for method chains.
     */
    default B setUseCanDestoryTag(final ToolMeta src)
    {
        this.meta().setUseCanDestoryTag(src.useCanDestoryTag());
        return this.getBuilder();
    }

    /**
     * Set materials that can be destroyed by this tool. <br>
     * Minecraft don't support subtypes here, but diorite will save subtypes in separate tag.
     * (Vanilla client can't see them)
     *
     * @param materials new collection of materials.
     *
     * @return builder for method chains.
     */
    default B setCanDestoryMaterials(final Collection<BlockMaterialData> materials)
    {
        this.meta().setCanDestoryMaterials(materials);
        return this.getBuilder();
    }

    /**
     * Set materials that can be destroyed by this tool. <br>
     * Minecraft don't support subtypes here, but diorite will save subtypes in separate tag.
     * (Vanilla client can't see them)
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B setCanDestoryMaterials(final ToolMeta src)
    {
        final Set<BlockMaterialData> data = src.getCanDestoryMaterials();
        if (data == null)
        {
            return this.getBuilder();
        }
        this.meta().setCanDestoryMaterials(data);
        return this.getBuilder();
    }

    /**
     * Add new material that can be destroyed by this tool,
     * this method will automatically enable CanDestory tag if needed. <br>
     * Minecraft don't support subtypes here.
     *
     * @param material material to add.
     *
     * @return builder for method chains.
     */
    default B addCanDestoryMaterial(final BlockMaterialData material)
    {
        this.meta().addCanDestoryMaterial(material);
        return this.getBuilder();
    }

    /**
     * Add new material that can be destroyed by this tool,
     * this method will automatically enable CanDestory tag if needed. <br>
     * Minecraft don't support subtypes here.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B addCanDestoryMaterial(final ToolMeta src)
    {
        final Set<BlockMaterialData> data = src.getCanDestoryMaterials();
        if ((data == null) || data.isEmpty())
        {
            return this.getBuilder();
        }
        final M meta = this.meta();
        data.forEach(meta::addCanDestoryMaterial);
        return this.getBuilder();
    }

    /**
     * Remove material that can be destroyed by this tool,
     * this method will automatically enable CanDestory tag if needed. <br>
     * Minecraft don't support subtypes here.
     *
     * @param material material to add.
     *
     * @return builder for method chains.
     */
    default B removeCanDestoryMaterial(final BlockMaterialData material)
    {
        this.meta().removeCanDestoryMaterial(material);
        return this.getBuilder();
    }

    /**
     * Clear list of materials that can be destroyed by this tool,
     * this method will automatically enable CanDestory tag if needed. <br>
     * Use {@link #setUseCanDestoryTag(boolean)} if you want disable CanDestory tag.
     *
     * @return builder for method chains.
     */
    default B removeCanDestoryMaterials()
    {
        this.meta().removeCanDestoryMaterials();
        return this.getBuilder();
    }
}
