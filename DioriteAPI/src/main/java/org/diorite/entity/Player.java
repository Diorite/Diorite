/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.entity;

import java.util.List;

import org.diorite.Core;
import org.diorite.GameMode;
import org.diorite.OfflinePlayer;
import org.diorite.Particle;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;
import org.diorite.command.sender.PlayerCommandSender;
import org.diorite.inventory.InventoryHolder;
import org.diorite.inventory.PlayerInventory;
import org.diorite.permissions.GroupablePermissionsContainer;
import org.diorite.utils.math.DioriteRandom;
import org.diorite.world.World;

public interface Player extends Human, PlayerCommandSender, InventoryHolder, OfflinePlayer, ArmoredEntity
{
    float WALK_SPEED         = 0.1f;
    float FLY_SPEED          = 0.05f;
    float SPRINT_SPEED_BOOST = 0.30000001192092896f;

    @Override
    default Player getPlayer()
    {
        return this;
    }

    @Override
    default boolean isOnline()
    {
        return this.getCore().getPlayer(this.getUniqueID()) != null;
    }

    @Override
    PlayerInventory getInventory();

    /**
     * Send tab complete packet to player.
     *
     * @param strs strings to send.
     */
    void sendTabCompletes(List<String> strs);

    /**
     * Gets this player current {@link GameMode}
     *
     * @return Current game mode
     */
    GameMode getGameMode();

    /**
     * Sets this player's {@link GameMode}
     *
     * @param gameMode New gamemode
     */
    void setGameMode(GameMode gameMode);

    /**
     * Gets this player's ping
     *
     * @return Ping
     */
    int getPing(); // may not be accurate.

    /**
     * Disconnect player from server with specified reason
     *
     * @param reason The reason which should be displayed to player
     */
    void kick(BaseComponent reason);

    /**
     * Gets whether the player is crouching or not
     *
     * @return True if player crouching
     */
    boolean isCrouching();

    /**
     * Sets whether the player is crouching or not
     *
     * @param isCrouching New crouch status
     */
    void setCrouching(boolean isCrouching);

    /**
     * Gets whether the player is sprinting or not
     *
     * @return True if player sprinting
     */
    boolean isSprinting();

    /**
     * Sets whether the player is sprinting or not.
     *
     * @param isSprinting New sprint status
     */
    void setSprinting(boolean isSprinting);

    /**
     * Gets player's view distance
     * This value is defined by client
     * Determines what distance (chunks) will see the player
     *
     * @return View distance
     */
    byte getViewDistance();

    /**
     * Get the slot number of the currently held item
     *
     * @return Held item slot number
     */
    int getHeldItemSlot();

    /**
     * Set the slot number of the currently held item
     *
     * @param slot new slot id, from 0 to 8.
     */
    void setHeldItemSlot(final int slot);

    /**
     * Gets player's render distance
     *
     * @return Player's render distance
     *
     * @see Player#getViewDistance()
     */
    byte getRenderDistance();

    /**
     * Sets player's render distance
     *
     * @param viewDistance New player's view distance
     */
    void setRenderDistance(byte viewDistance);

    /**
     * Request that the player's client download and switch resource packs.
     *
     * @param resourcePack URL to resource pack
     */
    void setResourcePack(String resourcePack);

    /**
     * Request that the player's client download and switch resource packs.
     *
     * @param resourcePack URL to resource pack
     * @param hash         A 40 character lowercase SHA-1 hash of resource pack file
     */
    void setResourcePack(String resourcePack, String hash);

    /**
     * Gets whether player can fly or not
     *
     * @return True, if player can fly
     */
    boolean canFly();

    /**
     * Sets whether player can fly
     *
     * @param value True if player should be able to fly
     */
    void setCanFly(boolean value);

    /**
     * Sets whether player can fly and speed
     *
     * @param value    True if player should be able to fly
     * @param flySpeed New speed
     */
    void setCanFly(boolean value, double flySpeed);

    /**
     * Gets player's current fly speed
     *
     * @return Current fly speed
     */
    float getFlySpeed();

    /**
     * Sets player's fly speed
     *
     * @param flySpeed New fly speed
     */
    void setFlySpeed(double flySpeed);

    /**
     * Gets this player's walk speed
     *
     * @return Walk speed
     */
    float getWalkSpeed();

    /**
     * Sets this player's walk speed
     *
     * @param walkSpeed Walk speed
     */
    void setWalkSpeed(double walkSpeed);

    /**
     * Creates particle <b>only</b> to this player
     *
     * @param particle       Particle which should be shown
     * @param isLongDistance If you set this to false, you can show particles only in 256 blocks from player
     * @param x              Location of particle
     * @param y              Location of particle
     * @param z              Location of particle
     * @param offsetX        Offset X
     * @param offsetY        Offset Y
     * @param offsetZ        Offset Z
     * @param particleData   Particle data
     * @param particleCount  Count of particles to display
     * @param data           Special particle data, it should be used only to: ICON_CRACK, BLOCK_CRACK, BLOCK_DUST
     *
     * @see org.diorite.world.World#showParticle(Particle, boolean, int, int, int, int, int, int, int, int, int...)
     */
    void showParticle(Particle particle, boolean isLongDistance, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float particleData, int particleCount, int... data);

    /**
     * Sending a inventory content to player
     */
    void updateInventory();

    /**
     * Returns the world on which is player
     *
     * @return World
     */
    default World getWorld()
    {
        return this.getLocation().getWorld();
    }

    /**
     * Updates text on tab header/footer for this player
     * If you want remove header or footer use the blank BaseComponent (NOT NULL!)
     *
     * @param header Text which should be displayed in TAB header, shouldn't be null
     * @param footer Text which should be displayed in TAB footer, shouldn't be null
     *
     * @see Core#updatePlayerListHeaderAndFooter(BaseComponent, BaseComponent)
     * @see Core#updatePlayerListHeaderAndFooter(BaseComponent, BaseComponent, Player)
     */
    default void updatePlayerListHeaderAndFooter(final BaseComponent header, final BaseComponent footer)
    {
        this.getCore().updatePlayerListHeaderAndFooter(header, footer, this);
    }

    /**
     * Send title to player (a big text in center of screen)
     *
     * @param title    Title
     * @param subtitle Sub-title
     * @param fadeIn   Time in which text should appear
     * @param stay     Time in which text should stay on screen
     * @param fadeOut  Time in which text should disappear
     */
    default void sendTitle(final BaseComponent title, final BaseComponent subtitle, final int fadeIn, final int stay, final int fadeOut)
    {
        this.getCore().sendTitle(title, subtitle, fadeIn, stay, fadeOut, this);
    }

    /**
     * Makes title disappear
     */
    default void removeTitle()
    {
        this.getCore().removeTitle(this);
    }

    /**
     * Disconnect player from server with specified reason
     *
     * @param reason The reason which should be displayed to player
     */
    default void kick(final String reason)
    {
        this.kick(TextComponent.join(TextComponent.fromLegacyText(reason)));
    }

    /**
     * Returns this player random instance;
     *
     * @return this player random instance
     */
    DioriteRandom getRandom();

    @Override
    GroupablePermissionsContainer getPermissionsContainer();
}
