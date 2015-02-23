package diorite.impl.connection;

import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import diorite.impl.Main;
import diorite.impl.connection.packets.Packet;

public enum EnumProtocol
{
    HANDSHAKING("HANDSHAKING", - 1),
    PLAY("PLAY", 0),
    STATUS("STATUS", 1),
    LOGIN("LOGIN", 2);

    private final        String                                               name;
    private final        int                                                  status;
    private final        Map<EnumProtocolDirection, BiMap<Integer, Class<?>>> packetsMap;
    private static final TIntObjectMap<EnumProtocol>                          enumProtocolMap;
    private static final Map<Class<?>, EnumProtocol>                          classEnumProtocolMap;

    EnumProtocol(final String name, final int status)
    {
        this.packetsMap = Maps.newEnumMap(EnumProtocolDirection.class);
        this.name = name;
        this.status = status;
    }

    public String getName()
    {
        return this.name;
    }

    public int getStatus()
    {
        return this.status;
    }

    public void init(final EnumProtocolDirection enumProtocolDirection, final int id, final Class<? extends Packet<?>> clazz)
    {
        BiMap<Integer, Class<?>> create = this.packetsMap.get(enumProtocolDirection);
        if (create == null)
        {
            create = HashBiMap.create();
            this.packetsMap.put(enumProtocolDirection, create);
        }
        if (create.containsValue(clazz))
        {
            final String string = enumProtocolDirection + " packet " + clazz + " is already known to ID " + create.inverse().get(clazz);
//            LogManager.getLogger().fatal(string);
            throw new IllegalArgumentException(string);
        }
        classEnumProtocolMap.put(clazz, this);
        create.put(id, clazz);
    }

    public Integer getPacketID(final EnumProtocolDirection protocolDirection, final Packet<?> packet)
    {
        return this.packetsMap.get(protocolDirection).inverse().get(packet.getClass());
    }

    public Packet<?> createPacket(final EnumProtocolDirection protocolDirection, final int id) throws IllegalAccessException, InstantiationException
    {
        Main.debug("create packet for: " + this + ", " + protocolDirection + ", " + id + ", " + this.packetsMap);
        final Class<?> clazz = this.packetsMap.get(protocolDirection).get(id);
        if (clazz == null)
        {
            return null;
        }
        try
        {
            return (Packet<?>) clazz.newInstance();
        } catch (final Exception e)
        {
            throw new RuntimeException("Can't create packet instance!");
        }
    }

    public static EnumProtocol getByPacketID(final int n)
    {
        return EnumProtocol.enumProtocolMap.get(n);
    }

    public static EnumProtocol getByPacketClass(final Packet<?> packet)
    {
        return EnumProtocol.classEnumProtocolMap.get(packet.getClass());
    }

    static
    {
        enumProtocolMap = new TIntObjectHashMap<>();
        classEnumProtocolMap = Maps.newHashMap();
        for (final EnumProtocol enumProtocol : values())
        {
            enumProtocolMap.put(enumProtocol.getStatus(), enumProtocol);
        }
    }
}