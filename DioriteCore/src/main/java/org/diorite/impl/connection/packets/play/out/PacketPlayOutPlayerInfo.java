package org.diorite.impl.connection.packets.play.out;

import org.diorite.Diorite;
import org.diorite.GameMode;
import org.diorite.chat.component.TextComponent;
import org.diorite.entity.Player;
import org.diorite.impl.auth.GameProfile;
import org.diorite.impl.auth.properties.Property;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@PacketClass(id = 0x38, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutPlayerInfo implements PacketPlayOut
{
    private PlayerInfoAction action;
    private List<PlayerInfoData> players;

    public PacketPlayOutPlayerInfo(PlayerInfoAction action, List<GameProfile> players)
    {
        this.action = action;
        this.players = new ArrayList<>(5);
        for(GameProfile gp : players)
        {
            Player p = Diorite.getServer().getPlayer(gp.getId());
            this.players.add(new PlayerInfoData(gp, 0, GameMode.SURVIVAL, new TextComponent(p.getName())));
            // TODO Set latency (ping), and GameMode
        }
    }

    public PacketPlayOutPlayerInfo(PlayerInfoAction action, GameProfile... players)
    {
        this.action = action;
        this.players = new ArrayList<>(5);
        for(GameProfile gp : players)
        {
            Player p = Diorite.getServer().getPlayer(gp.getId());
            this.players.add(new PlayerInfoData(gp, 0, GameMode.SURVIVAL, new TextComponent(p.getName())));
            // TODO Set latency (ping), and GameMode
        }
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        // Useless
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(action.getActionId()); // VarInt z numerem akcji
        data.writeVarInt(players.size()); // Ilosc graczy
        for(PlayerInfoData pid : players)
        {
            data.writeUUID(pid.getGameProfile().getId()); // Zawsze wysylamy UUID, a pozniej to juz zalezy od akcji
            switch(action)
            {
                case ADD_PLAYER:
                    data.writeText(pid.getGameProfile().getName());
                    data.writeVarInt(pid.getGameProfile().getProperties().size());
                    for(Property p : pid.getGameProfile().getProperties().values())
                    {
                        data.writeText(p.getName());
                        data.writeText(p.getValue());
                        if (p.hasSignature())
                        {
                            data.writeBoolean(true);
                            data.writeText(p.getSignature());
                        }
                        else
                        {
                            data.writeBoolean(false);
                        }
                    }
                    data.writeVarInt(pid.getGameMode().getId());
                    data.writeVarInt(pid.getLatency());
                    if(pid.getDisplayName() == null)
                    {
                        data.writeBoolean(false);
                    }
                    else
                    {
                        data.writeBoolean(true);
                        data.writeBaseComponent(pid.getDisplayName());
                    }
                    break;
                case REMOVE_PLAYER:
                    // No fields
                    break;
                case UPDATE_LATENCY:
                    data.writeVarInt(pid.getLatency());
                    break;
                case UPDATE_GAMEMODE:
                    data.writeVarInt(pid.getGameMode().getId());
                    break;
                case UPDATE_DISPLAY_NAME:
                    if(pid.getDisplayName() == null)
                    {
                        data.writeBoolean(false);
                    }
                    else
                    {
                        data.writeBoolean(true);
                        data.writeBaseComponent(pid.getDisplayName());
                    }
                    break;
            }
        }
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        // Useless
    }

    public class PlayerInfoData
    {
        private int latency;
        private GameProfile gameProfile;
        private GameMode gameMode;
        private TextComponent displayName;

        public PlayerInfoData(GameProfile gameProfile, int latency, GameMode gameMode, TextComponent displayName)
        {
            this.latency = latency;
            this.gameProfile = gameProfile;
            this.gameMode = gameMode;
            this.displayName = displayName;
        }

        public GameProfile getGameProfile()
        {
            return gameProfile;
        }

        public int getLatency()
        {
            return latency;
        }

        public GameMode getGameMode()
        {
            return gameMode;
        }

        public TextComponent getDisplayName()
        {
            return displayName;
        }
    }

    public enum PlayerInfoAction
    {
        ADD_PLAYER(0),
        REMOVE_PLAYER(4),
        UPDATE_GAMEMODE(1),
        UPDATE_LATENCY(2),
        UPDATE_DISPLAY_NAME(3);

        private int actionId;

        PlayerInfoAction(int actionId)
        {
            this.actionId = actionId;
        }

        public int getActionId()
        {
            return actionId;
        }
    }
}
