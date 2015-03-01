package diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.Difficulty;
import diorite.Dimension;
import diorite.GameMode;
import diorite.WorldType;
import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.EnumProtocolDirection;
import diorite.impl.connection.packets.PacketClass;
import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.play.PacketPlayOutListener;

@PacketClass(id = 0x01, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutLogin implements PacketPlayOut
{
    public static final int MAX_WORLD_NAME_SIZE = 32; // should be 16?

    private int        entityID;
    private GameMode   gameMode;
    private boolean    hardcore;
    private Dimension  dimension;
    private Difficulty difficulty;
    private int        maxPlayers;
    private WorldType  worldType;
    private boolean    debug;

    public PacketPlayOutLogin()
    {
    }

    public PacketPlayOutLogin(final int entityID, final GameMode gameMode, final boolean hardcore, final Dimension dimension, final Difficulty difficulty, final int maxPlayers, final WorldType worldType)
    {
        this.entityID = entityID;
        this.gameMode = gameMode;
        this.hardcore = hardcore;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.worldType = worldType;
    }

    public PacketPlayOutLogin(final int entityID, final GameMode gameMode, final boolean hardcore, final Dimension dimension, final Difficulty difficulty, final int maxPlayers, final WorldType worldType, final boolean debug)
    {
        this.entityID = entityID;
        this.gameMode = gameMode;
        this.hardcore = hardcore;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.worldType = worldType;
        this.debug = debug;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityID = data.readInt();
        final int gmFlags = data.readUnsignedByte();
        this.hardcore = (gmFlags & 8) == 8;
        this.gameMode = GameMode.getByID(gmFlags & - 9);
        this.dimension = Dimension.getByID(data.readByte());
        this.difficulty = Difficulty.getByLevel(data.readUnsignedByte());
        this.maxPlayers = data.readUnsignedByte();
        this.worldType = WorldType.getType(data.readText(MAX_WORLD_NAME_SIZE));
        this.debug = data.readBoolean();
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeInt(this.entityID);
        int gmFlags = this.gameMode.getId();
        if (this.hardcore)
        {
            gmFlags |= 8;
        }
        data.writeByte(gmFlags);
        data.writeByte(this.dimension.getId());
        data.writeByte(this.difficulty.getLevel());
        data.writeByte(this.maxPlayers);
        data.writeText(this.worldType.getName());
        data.writeBoolean(this.debug);
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    public boolean isDebug()
    {
        return this.debug;
    }

    public void setDebug(final boolean debug)
    {
        this.debug = debug;
    }

    public WorldType getWorldType()
    {
        return this.worldType;
    }

    public void setWorldType(final WorldType worldType)
    {
        this.worldType = worldType;
    }

    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    public void setMaxPlayers(final int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public Difficulty getDifficulty()
    {
        return this.difficulty;
    }

    public void setDifficulty(final Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }

    public Dimension getDimension()
    {
        return this.dimension;
    }

    public void setDimension(final Dimension dimension)
    {
        this.dimension = dimension;
    }

    public GameMode getGameMode()
    {
        return this.gameMode;
    }

    public void setGameMode(final GameMode gameMode)
    {
        this.gameMode = gameMode;
    }

    public boolean isHardcore()
    {
        return this.hardcore;
    }

    public void setHardcore(final boolean hardcore)
    {
        this.hardcore = hardcore;
    }

    public int getEntityID()
    {
        return this.entityID;
    }

    public void setEntityID(final int entityID)
    {
        this.entityID = entityID;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityID", this.entityID).append("gameMode", this.gameMode).append("hardcore", this.hardcore).append("dimension", this.dimension).append("difficulty", this.difficulty).append("maxPlayers", this.maxPlayers).append("worldType", this.worldType).append("debug", this.debug).toString();
    }
}
