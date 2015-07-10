package org.diorite.impl.entity;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.auth.GameProfile;
import org.diorite.impl.connection.NetworkManager;
import org.diorite.impl.connection.packets.play.in.PacketPlayInAbilities;
import org.diorite.impl.connection.packets.play.out.PacketPlayOut;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutAbilities;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutChat;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutEntityDestroy;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutGameStateChange;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutGameStateChange.ReasonCodes;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutNamedEntitySpawn;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutPlayerInfo;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutResourcePackSend;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutTabComplete;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutUpdateAttributes;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutWorldParticles;
import org.diorite.impl.entity.attrib.AttributeModifierImpl;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataFloatEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.impl.entity.tracker.BaseTracker;
import org.diorite.impl.inventory.PlayerCraftingInventoryImpl;
import org.diorite.impl.inventory.PlayerInventoryImpl;
import org.diorite.impl.world.chunk.PlayerChunksImpl;
import org.diorite.GameMode;
import org.diorite.ImmutableLocation;
import org.diorite.Particle;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.component.BaseComponent;
import org.diorite.entity.Entity;
import org.diorite.entity.EntityType;
import org.diorite.entity.Player;
import org.diorite.entity.attrib.AttributeModifier;
import org.diorite.entity.attrib.AttributeProperty;
import org.diorite.entity.attrib.AttributeType;
import org.diorite.entity.attrib.ModifierOperation;
import org.diorite.inventory.EntityEquipment;
import org.diorite.inventory.item.IItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;
import org.diorite.utils.math.pack.IntsToLong;

import gnu.trove.TIntCollection;
import gnu.trove.list.array.TIntArrayList;

// TODO: add Human or other entity not fully a player class for bots/npcs
public class PlayerImpl extends LivingEntityImpl implements Player
{
    @SuppressWarnings("MagicNumber")
    public static final ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 1.8F);
    @SuppressWarnings("MagicNumber")
    public static final ImmutableEntityBoundingBox DIE_SIZE  = new ImmutableEntityBoundingBox(0.2F, 0.2F);

    /**
     * byte entry with visible skin parts flags.
     */
    protected static final byte META_KEY_SKIN_FLAGS = 10;

    /**
     * byte entry, 0x02 bool cape hidden
     */
    protected static final byte META_KEY_CAPE = 16;

    /**
     * float entry, amount of absorption hearts (yellow/gold ones)
     */
    protected static final byte META_KEY_ABSORPTION_HEARTS = 17;

    /**
     * int entry, amount of player points
     */
    protected static final byte META_KEY_SCORE = 18;


    // TODO: move this
    private static final AttributeModifier tempSprintMod = new AttributeModifierImpl(UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D"), MagicNumbers.ATTRIBUTES__MODIFIERS__SPRINT, ModifierOperation.ADD_PERCENTAGE);

    protected final TIntCollection removeQueue = new TIntArrayList(5, - 1);

    protected final GameProfile         gameProfile;
    protected final NetworkManager      networkManager;
    protected final PlayerChunksImpl    playerChunks;
    protected       byte                viewDistance;
    protected       byte                renderDistance;
    protected       GameMode            gameMode;
    protected       PlayerInventoryImpl inventory;

    protected PacketPlayOutAbilities abilities = new PacketPlayOutAbilities(false, false, false, false, Player.WALK_SPEED, Player.FLY_SPEED);

    // TODO: add saving/loading data to/from NBT
    public PlayerImpl(final ServerImpl server, final int id, final GameProfile gameProfile, final NetworkManager networkManager, final ImmutableLocation location)
    {
        super(gameProfile.getId(), server, id, location);
        this.aabb = BASE_SIZE.create(this);
        this.gameProfile = gameProfile;
        this.networkManager = networkManager;
        this.renderDistance = server.getRenderDistance();
        this.gameMode = this.world.getDefaultGameMode();
        this.playerChunks = new PlayerChunksImpl(this);
        this.inventory = new PlayerInventoryImpl(this, 0); // 0 because this is owner of this inventory, and we need this to update
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_SKIN_FLAGS, 0));
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_CAPE, 0));
        this.metadata.add(new EntityMetadataFloatEntry(META_KEY_ABSORPTION_HEARTS, 0));
        this.metadata.add(new EntityMetadataIntEntry(META_KEY_SCORE, 0));
    }

    @Override
    public boolean isValidSynchronizable()
    {
        return this.isOnline() && super.isValidSynchronizable();
    }

    @Override
    public void doTick(final int tps)
    {
        super.doTick(tps);
        if (this.playerChunks == null) // sometimes it is null on first tick o.O
        {
            return;
        }
        this.playerChunks.doTick(tps);
        this.inventory.softUpdate();


        // send remove entity packets
        if (! this.removeQueue.isEmpty())
        {
            final int[] ids;
            synchronized (this.removeQueue)
            {
                ids = this.removeQueue.toArray();
                this.removeQueue.clear();
            }
            this.networkManager.sendPacket(new PacketPlayOutEntityDestroy(ids));
        }

        // TODO: maybe don't pickup every tick?
        for (final ItemImpl entity : this.getNearbyEntities(1, 2, 1, ItemImpl.class))
        {
            entity.pickUpItem(this);
        }
    }

    @Override
    public PacketPlayOut getSpawnPacket()
    {
        return new PacketPlayOutNamedEntitySpawn(this);
    }

    @SuppressWarnings("ObjectEquality")
    public void removeEntityFromView(final BaseTracker<?> e)
    {
        final int id = e.getId();
        if (id == - 1)
        {
            return;
        }
        if ((e.getTracker() instanceof PlayerImpl) || (this.lastTickThread == Thread.currentThread()))
        {
            this.networkManager.sendPacket(new PacketPlayOutEntityDestroy(id));
        }
        else
        {
            this.removeQueue.add(id);
        }
    }

    @SuppressWarnings("ObjectEquality")
    public void removeEntityFromView(final Entity e)
    {
        final int id = e.getId();
        if (id == - 1)
        {
            return;
        }
        if ((e instanceof PlayerImpl) || (this.lastTickThread == Thread.currentThread()))
        {
            this.networkManager.sendPacket(new PacketPlayOutEntityDestroy(id));
        }
        else
        {
            this.removeQueue.add(id);
        }
    }

    public GameProfile getGameProfile()
    {
        return this.gameProfile;
    }

    public NetworkManager getNetworkManager()
    {
        return this.networkManager;
    }

    public PlayerChunksImpl getPlayerChunks()
    {
        return this.playerChunks;
    }

    public boolean isVisibleChunk(final int x, final int z)
    {
        return this.playerChunks.getVisibleChunks().contains(IntsToLong.pack(x, z));
    }

    @Override
    public String getName()
    {
        return this.gameProfile.getName();
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
        this.server.getPlayersManager().forEach(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.PlayerInfoAction.UPDATE_GAMEMODE, new PacketPlayOutPlayerInfo.PlayerInfoData(this.getUniqueID(), gameMode)));
        this.networkManager.sendPacket(new PacketPlayOutGameStateChange(ReasonCodes.CHANGE_GAME_MODE, gameMode.ordinal()));
    }

    @Override
    public int getPing()
    {
        return this.networkManager.getPing();
    }

    @Override
    public void kick(final BaseComponent s)
    {
        this.server.sync(() -> this.networkManager.close(s, false));
    }

    @Override
    public void sendMessage(final ChatPosition position, final BaseComponent component)
    {
        this.networkManager.sendPacket(new PacketPlayOutChat(component, position));
    }

    @Override
    public boolean isCrouching()
    {
        return this.metadata.getBoolean(META_KEY_BASIC_FLAGS, BasicFlags.CROUCHED);
    }

    @Override
    public void setCrouching(final boolean isCrouching)
    {
        this.metadata.setBoolean(META_KEY_BASIC_FLAGS, BasicFlags.CROUCHED, isCrouching);
    }

    @Override
    public boolean isSprinting()
    {
        return this.metadata.getBoolean(META_KEY_BASIC_FLAGS, BasicFlags.SPRINTING);
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
        this.networkManager.sendPacket(new PacketPlayOutUpdateAttributes(0, this.attributes));
        this.metadata.setBoolean(META_KEY_BASIC_FLAGS, BasicFlags.SPRINTING, isSprinting);
    }

    @Override
    public byte getViewDistance()
    {
        return this.viewDistance;
    }

    private int heldItemSlot;

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

    public void setViewDistance(final byte viewDistance)
    {
        this.viewDistance = viewDistance;
    }

    @Override
    public byte getRenderDistance()
    {
        return this.renderDistance;
    }

    @Override
    public void setRenderDistance(final byte renderDistance)
    {
        this.renderDistance = renderDistance;
    }

    @Override
    public void setResourcePack(final String resourcePack)
    {
        this.networkManager.sendPacket(new PacketPlayOutResourcePackSend(resourcePack, "DIORITE"));
    }

    @Override
    public void setResourcePack(final String resourcePack, final String hash)
    {
        this.networkManager.sendPacket(new PacketPlayOutResourcePackSend(resourcePack, hash));
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
        this.updateAbilities();
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
        this.updateAbilities();
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
        this.updateAbilities();
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
        this.updateAbilities();
    }

    @Override
    public void showParticle(final Particle particle, final boolean isLongDistance, final float x, final float y, final float z, final float offsetX, final float offsetY, final float offsetZ, final float particleData, final int particleCount, final int... data)
    {
        this.networkManager.sendPacket(new PacketPlayOutWorldParticles(particle, isLongDistance, x, y, z, offsetX, offsetY, offsetZ, particleData, particleCount, data));
    }

    public PacketPlayOutAbilities getAbilities()
    {
        return this.abilities;
    }

    public void setAbilities(final PacketPlayOutAbilities abilities)
    {
        this.abilities = abilities;
    }

    public void setAbilities(final PacketPlayInAbilities abilities)
    {
        // TOOD: I should check what player want change here (cheats)
        this.abilities.setCanFly(abilities.isCanFly());
        this.abilities.setFlying(abilities.isFlying());
        this.abilities.setWalkingSpeed(abilities.getWalkingSpeed());
        this.abilities.setFlyingSpeed(abilities.getFlyingSpeed());
        this.abilities.setInvulnerable(abilities.isInvulnerable());
        this.abilities.setCanInstantlyBuild(abilities.isCanInstantlyBuild());
    }

    private void updateAbilities()
    {
        this.abilities.send(this.networkManager);
    }

    @Override
    public UUID getUniqueID()
    {
        return this.gameProfile.getId();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.gameProfile.getName()).append("uuid", this.gameProfile.getId()).toString();
    }

    @Override
    public EntityType getType()
    {
        return EntityType.PLAYER;
    }

    @Override
    public PlayerInventoryImpl getInventory()
    {
        return this.inventory;
    }

    @Override
    public void sendTabCompletes(final List<String> strs)
    {
        this.networkManager.sendPacket(new PacketPlayOutTabComplete(strs));
    }

    @Override
    public void updateInventory()
    {
        this.inventory.update(this);
    }

    public void onLogout()
    {
        this.remove(true);

        this.server.broadcastSimpleColoredMessage(ChatPosition.ACTION, "&3&l" + this.getName() + "&7&l left from the server!");
        this.server.broadcastSimpleColoredMessage(ChatPosition.SYSTEM, "&3" + this.getName() + "&7 left from the server!");
    }

    @Override
    public EntityEquipment getEquipment()
    {
        return this.inventory.getArmorInventory();
    }

    public void closeInventory(final int id)
    {
        final PlayerCraftingInventoryImpl ci = this.inventory.getCraftingInventory();
        for (int i = 0, s = ci.size(); i < s; i++)
        {
            final IItemStack itemStack = ci.setItem(i, null);
            if (itemStack != null)
            {
                final ItemImpl item = new ItemImpl(UUID.randomUUID(), this.getServer(), EntityImpl.getNextEntityID(), this.getLocation().addX(2));  // TODO:velocity + some .spawnEntity method
                item.setItemStack(new ItemStack(itemStack.getMaterial(), itemStack.getAmount()));
                this.getWorld().addEntity(item);
            }
        }
        final IItemStack cur = this.inventory.setCursorItem(null);
        if (cur != null)
        {
            final ItemImpl item = new ItemImpl(UUID.randomUUID(), this.getServer(), EntityImpl.getNextEntityID(), this.getLocation().addX(2));  // TODO:velocity + some .spawnEntity method
            item.setItemStack(new ItemStack(cur.getMaterial(), cur.getAmount()));
            this.getWorld().addEntity(item);
        }
    }
}
