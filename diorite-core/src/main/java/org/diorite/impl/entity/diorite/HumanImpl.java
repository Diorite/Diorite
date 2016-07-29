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

package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientbound;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundAbilities;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundNamedEntitySpawn;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundPlayerInfo;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundAbilities;
import org.diorite.impl.entity.IEntity;
import org.diorite.impl.entity.IHuman;
import org.diorite.impl.entity.IItem;
import org.diorite.impl.entity.attrib.SimpleAttributeModifier;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataFloatEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.impl.inventory.PlayerCraftingInventoryImpl;
import org.diorite.impl.inventory.PlayerInventoryImpl;
import org.diorite.Diorite;
import org.diorite.GameMode;
import org.diorite.ImmutableLocation;
import org.diorite.auth.GameProfile;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.command.sender.MessageOutput;
import org.diorite.entity.EntityType;
import org.diorite.entity.Human;
import org.diorite.entity.Player;
import org.diorite.entity.attrib.AttributeModifier;
import org.diorite.entity.attrib.AttributeProperty;
import org.diorite.entity.attrib.AttributeType;
import org.diorite.entity.attrib.ModifierOperation;
import org.diorite.entity.data.HandSide;
import org.diorite.entity.data.HandType;
import org.diorite.inventory.EntityEquipment;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.permissions.GroupablePermissionsContainer;
import org.diorite.permissions.PlayerPermissionsContainer;
import org.diorite.utils.math.DioriteMathUtils;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;
import org.diorite.utils.math.geometry.LookupShape;
import org.diorite.utils.others.NamedUUID;

class HumanImpl extends LivingEntityImpl implements IHuman
{
    private static final float ITEM_DROP_MOD_VEL_Y = 0.02F;

    @SuppressWarnings("MagicNumber")
    private static final ImmutableEntityBoundingBox DIE_SIZE = new ImmutableEntityBoundingBox(0.2F, 0.2F);

    // TODO: move this
    private static final AttributeModifier tempSprintMod = new SimpleAttributeModifier(UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D"), null, MagicNumbers.ATTRIBUTES__MODIFIERS__SPRINT, ModifierOperation.ADD_PERCENTAGE, null, null);

    private final GameProfile         gameProfile;
    private final PlayerInventoryImpl inventory;

    private   PacketPlayClientboundAbilities abilities     = new PacketPlayClientboundAbilities(false, false, false, false, Player.WALK_SPEED, Player.FLY_SPEED);
    private   HandSide                       mainHand      = HandSide.RIGHT;
    private   int                            heldItemSlot  = 0;
    private   GameMode                       gameMode      = GameMode.SURVIVAL;
    protected MessageOutput                  messageOutput = MessageOutput.IGNORE;
    private NamedUUID namedUUID;

    private PlayerPermissionsContainer permissionContainer;

    HumanImpl(final DioriteCore core, final GameProfile gameProfile, final int id, final ImmutableLocation location)
    {
        super(gameProfile.getId(), core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
        this.gameProfile = gameProfile;
        this.permissionContainer = Diorite.getServerManager().getPermissionsManager().createPlayerContainer(this);
        this.gameMode = this.getWorld().getDefaultGameMode();
        this.inventory = new PlayerInventoryImpl(this, 0); // 0 because this is owner of this inventory, and we need this to update
        this.namedUUID = new NamedUUID(gameProfile.getName(), gameProfile.getId());
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IHuman.META_KEYS);
    }

    @Override
    public NamedUUID getNamedUUID()
    {
        return this.namedUUID;
    }

    @Override
    public void setNamedUUID(final NamedUUID namedUUID)
    {
        this.namedUUID = namedUUID;
    }

    @Override
    public float getHeadHeight()
    {
        return this.isCrouching() ? CROUCHING_HEAD_HEIGHT : BASE_HEAD_HEIGHT;
    }

    @Override
    public IItem dropItem(final ItemStack itemStack)
    {
        if ((itemStack == null) || (itemStack.getAmount() == 0))
        {
            return null;
        }
        final double newY = (this.getY() - ITEM_DROP_MOD_Y) + this.getHeadHeight();
        final ItemImpl item = new ItemImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), new ImmutableLocation(this.getX(), newY, this.getZ(), this.getWorld()));
        item.setItemStack(itemStack);
        item.setPickupDelay(IItem.DEFAULT_DROP_PICKUP_DELAY);
        item.setThrower(this.namedUUID);

        float yMod = ITEM_DROP_MOD_Y;
        final float yaw = this.getYaw();
        final float pitch = this.getPitch();
        item.velocityX = (- DioriteMathUtils.sin((yaw / DioriteMathUtils.HALF_CIRCLE_DEGREES) * DioriteMathUtils.F_PI) * DioriteMathUtils.cos((pitch / DioriteMathUtils.HALF_CIRCLE_DEGREES) * DioriteMathUtils.F_PI) * yMod);
        item.velocityZ = (DioriteMathUtils.cos((yaw / DioriteMathUtils.HALF_CIRCLE_DEGREES) * DioriteMathUtils.F_PI) * DioriteMathUtils.cos((pitch / DioriteMathUtils.HALF_CIRCLE_DEGREES) * DioriteMathUtils.F_PI) * yMod);
        item.velocityY = ((- DioriteMathUtils.sin((pitch / DioriteMathUtils.HALF_CIRCLE_DEGREES) * DioriteMathUtils.F_PI) * yMod) + DioriteMathUtils.F_ONE_OF_10);
        final float randomAngle = this.random.nextFloat() * DioriteMathUtils.F_PI * 2;
        yMod = ITEM_DROP_MOD_VEL_Y * this.random.nextFloat();
        item.velocityX += Math.cos(randomAngle) * yMod;
        item.velocityY += (this.random.nextFloat() - this.random.nextFloat()) * DioriteMathUtils.F_ONE_OF_10;
        item.velocityZ += Math.sin(randomAngle) * yMod;

        // TODO: event/pipeline etc.

        this.getWorld().addEntity(item);
        return item;
    }

    @Override
    public void doTick(final int tps)
    {
        super.doTick(tps);
        this.pickupItems();
    }

    @Override
    public void pickupItems()
    {
        // TODO: maybe don't pickup every tick?
        for (final IItem entity : this.getNearbyEntities(2, 2, 2, ItemImpl.class, LookupShape.RECTANGLE))
        {
            if (entity.canPickup())
            {
                entity.pickUpItem(this);
                entity.remove(true);
            }
        }
    }

    @Override
    public PacketPlayClientbound getSpawnPacket()
    {
        return new PacketPlayClientboundNamedEntitySpawn(this);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.getMetadata().add(new EntityMetadataByteEntry(META_KEY_SKIN_FLAGS, 0));
        this.getMetadata().add(new EntityMetadataByteEntry(META_KEY_CAPE, 0));
        this.getMetadata().add(new EntityMetadataFloatEntry(META_KEY_ABSORPTION_HEARTS, 0));
        this.getMetadata().add(new EntityMetadataIntEntry(META_KEY_SCORE, 0));
    }

    @Override
    public GameProfile getGameProfile()
    {
        return this.gameProfile;
    }

    @Override
    public HandSide getHandSide(final HandType type)
    {
        switch (type)
        {
            case MAIN:
                return this.mainHand;
            case OFF:
                return this.mainHand.getOpposite();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public HandType getHandSide(final HandSide side)
    {
        return (this.mainHand == side) ? HandType.MAIN : HandType.OFF;
    }

    @Override
    public void setMainHand(final HandSide side)
    {
        Validate.notNull(side, "Side of hand can't be null!");
        this.mainHand = side;
    }

    @Override
    public GameMode getGameMode()
    {
        return this.gameMode;
    }

    @Override
    public void setGameMode(final GameMode gameMode)
    {
        if (this.gameMode.equals(gameMode))
        {
            return;
        }
        this.gameMode = gameMode;
        this.core.getPlayersManager().forEach(new PacketPlayClientboundPlayerInfo(PacketPlayClientboundPlayerInfo.PlayerInfoAction.UPDATE_GAMEMODE, new PacketPlayClientboundPlayerInfo.PlayerInfoData(this.getUniqueID(), gameMode)));
    }

    @Override
    public boolean isCrouching()
    {
        return this.getMetadata().getBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.CROUCHED);
    }

    @Override
    public void setCrouching(final boolean isCrouching)
    {
        this.getMetadata().setBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.CROUCHED, isCrouching);
    }

    @Override
    public boolean isSprinting()
    {
        return this.getMetadata().getBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.SPRINTING);
    }

    @Override
    public void setSprinting(final boolean isSprinting)
    {
        final AttributeProperty attrib = this.getProperty(AttributeType.GENERIC_MOVEMENT_SPEED, MagicNumbers.PLAYER__MOVE_SPEED);
        attrib.removeModifier(tempSprintMod.getUuid());
        if (isSprinting)
        {
            attrib.addModifier(tempSprintMod);
        }
        this.getMetadata().setBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.SPRINTING, isSprinting);
    }

    @Override
    public int getHeldItemSlot()
    {
        return this.heldItemSlot;
    }

    @Override
    public void setHeldItemSlot(final int slot)
    {
        this.heldItemSlot = slot;
    }

    @Override
    public boolean canFly()
    {
        return this.abilities.isCanFly();
    }

    @Override
    public void setCanFly(final boolean value)
    {
        this.abilities.setCanFly(value);
        if (! value)
        {
            this.abilities.setFlying(false);
        }
    }

    @Override
    public void setCanFly(final boolean value, final double flySpeed)
    {
        this.abilities.setCanFly(value);
        this.abilities.setFlyingSpeed((float) flySpeed);
        if (! value)
        {
            this.abilities.setFlying(false);
        }
    }

    @Override
    public float getFlySpeed()
    {
        return this.abilities.getFlyingSpeed();
    }

    @Override
    public void setFlySpeed(final double flySpeed)
    {
        this.abilities.setFlyingSpeed((float) flySpeed);
    }

    @Override
    public float getWalkSpeed()
    {
        return this.abilities.getWalkingSpeed();
    }

    @Override
    public void setWalkSpeed(final double walkSpeed)
    {
        this.abilities.setWalkingSpeed((float) walkSpeed);
    }

    @Override
    public PacketPlayClientboundAbilities getAbilities()
    {
        return this.abilities;
    }

    @Override
    public void setAbilities(final PacketPlayClientboundAbilities abilities)
    {
        this.abilities = abilities;
    }

    @Override
    public void setAbilities(final PacketPlayServerboundAbilities abilities)
    {
        // TOOD: I should check what player want change here (cheats)
        this.abilities.setCanFly(abilities.isCanFly());
        this.abilities.setFlying(abilities.isFlying());
        this.abilities.setWalkingSpeed(abilities.getWalkingSpeed());
        this.abilities.setFlyingSpeed(abilities.getFlyingSpeed());
        this.abilities.setInvulnerable(abilities.isInvulnerable());
        this.abilities.setCanInstantlyBuild(abilities.isCanInstantlyBuild());
    }

    @Override
    public PlayerInventoryImpl getInventory()
    {
        return this.inventory;
    }

    @Override
    public GroupablePermissionsContainer getPermissionsContainer()
    {
        return this.permissionContainer;
    }

    @Override
    public EntityType getType()
    {
        return EntityType.PLAYER;
    }

    @Override
    public EntityEquipment getEquipment()
    {
        return this.inventory.getArmorInventory();
    }

    @Override
    public void closeInventory(final int id)
    {
        final PlayerCraftingInventoryImpl ci = this.inventory.getCraftingInventory();
        for (int i = 0, s = ci.size(); i < s; i++)
        {
            final ItemStack itemStack = ci.setItem(i, null);
            if (itemStack != null)
            {
                this.dropItem(new BaseItemStack(itemStack.getMaterial(), itemStack.getAmount()));
            }
        }
        final ItemStack cur = this.inventory.setCursorItem(null);
        if (cur != null)
        {
            this.dropItem(new BaseItemStack(cur.getMaterial(), cur.getAmount()));
        }
    }

    @Override
    public Human getSenderEntity()
    {
        return this;
    }

    @Override
    public String getName()
    {
        return this.gameProfile.getName();
    }

    @Override
    public MessageOutput getMessageOutput()
    {
        return this.messageOutput;
    }

    @Override
    public void setMessageOutput(final MessageOutput messageOutput)
    {
        this.messageOutput = messageOutput;
    }

    @Override
    public UUID getUniqueID()
    {
        return this.gameProfile.getId();
    }

    @Override
    public boolean isOp()
    {
        return this.permissionContainer.isOp();
    }

    @Override
    public boolean setOp(final boolean op)
    {
        return this.permissionContainer.setOp(op);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("gameProfile", this.gameProfile).toString();
    }
}
