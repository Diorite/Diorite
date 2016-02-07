/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import org.diorite.GameMode;
import org.diorite.auth.GameProfile;
import org.diorite.command.sender.HumanCommandSender;
import org.diorite.entity.data.HandSide;
import org.diorite.entity.data.HandType;
import org.diorite.inventory.InventoryHolder;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.permissions.GroupablePermissionsContainer;
import org.diorite.utils.others.NamedUUID;

/**
 * Represent human entity, player or npc etc...
 */
public interface Human extends LivingEntity, InventoryHolder, ArmoredEntity, HumanCommandSender
{
    float WALK_SPEED         = 0.1f;
    float FLY_SPEED          = 0.05f;
    float SPRINT_SPEED_BOOST = 0.30000001192092896f;

    /**
     * Returns object that contains UUID of entity and name of it (if any exist).
     *
     * @return object that contains UUID of entity and name of it (if any exist).
     */
    NamedUUID getNamedUUID();

    /**
     * Simulate player dropping an item from inventory.
     *
     * @param itemStack item stack to drop. (it don't need to be in inventory, no items consumed)
     *
     * @return created entity or null if drop failed.
     */
    Item dropItem(ItemStack itemStack);

    /**
     * Returns game profile for this human entity.
     *
     * @return game profile for this human entity.
     */
    GameProfile getGameProfile();

    /**
     * Returns side of given hand type.
     *
     * @param type type of hand.
     *
     * @return side of given hand type.
     */
    HandSide getHandSide(HandType type);

    /**
     * Returns type of hand for given hand side.
     *
     * @param side side of hand.
     *
     * @return type of hand for given hand side.
     */
    HandType getHandSide(HandSide side);

    /**
     * Set main hand to given side hand type.
     *
     * @param side side of new main hand.
     */
    void setMainHand(HandSide side);

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

    @Override
    PlayerInventory getInventory();

    @Override
    GroupablePermissionsContainer getPermissionsContainer();
}
