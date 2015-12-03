package org.diorite.inventory.item.builder;

import java.util.UUID;

import org.diorite.auth.GameProfile;
import org.diorite.inventory.item.meta.SkullMeta;

/**
 * Interface of builder of skull item meta data.
 *
 * @param <B> type of builder.
 * @param <M> type of item meta.
 */
public interface ISkullMetaBuilder<B extends ISkullMetaBuilder<B, M>, M extends SkullMeta> extends IMetaBuilder<B, M>
{
    /**
     * Sets the owner of the skull. <br>
     * Method will try to get cached skin for this player or download new skin from Mojang if needed,
     * downloading skin will not block current thread.
     *
     * @param nickname the new owner of the skull.
     *
     * @return builder for method chains.
     */
    default B setOwner(final String nickname)
    {
        this.meta().setOwner(nickname);
        return this.getBuilder();
    }

    /**
     * Sets the owner of the skull. <br>
     * Method will try to get cached skin for this player or download new skin from Mojang if needed,
     * downloading skin will not block current thread.
     *
     * @param uuid the new owner of the skull.
     *
     * @return builder for method chains.
     */
    default B setOwner(final UUID uuid)
    {
        this.meta().setOwner(uuid);
        return this.getBuilder();
    }

    /**
     * Sets the owner of the skull. <br>
     * Method will trust data in {@link GameProfile} even if it is fake or outdated.
     *
     * @param gameProfile the new owner of the skull.
     *
     * @return builder for method chains.
     */
    default B setOwner(final GameProfile gameProfile)
    {
        this.meta().setOwner(gameProfile);
        return this.getBuilder();
    }

    /**
     * Sets the owner of the skull. <br>
     * Method will trust data in {@link GameProfile} even if it is fake or outdated.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B setOwner(final SkullMeta src)
    {
        this.meta().setOwner(src.getOwner());
        return this.getBuilder();
    }

    /**
     * Removes owner of this head.
     *
     * @return builder for method chains.
     */
    default B removeOwner()
    {
        this.meta().removeOwner();
        return this.getBuilder();
    }
}
