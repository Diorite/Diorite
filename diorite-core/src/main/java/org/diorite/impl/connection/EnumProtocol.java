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

package org.diorite.impl.connection;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

import org.diorite.impl.connection.packets.Packet;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.utils.reflections.DioriteReflectionUtils;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;

public enum EnumProtocol
{
    HANDSHAKING("HANDSHAKING", - 1),
    PLAY("PLAY", 0),
    STATUS("STATUS", 1),
    LOGIN("LOGIN", 2);

    private static final int    PLAY_PACKETS_SIZE  = 50;
    private static final int    OTHER_PACKETS_SIZE = 5;
    private static final String INNER_CLASS_NAME   = "<PacketInit>";

    private static final Int2ObjectMap<EnumProtocol>                                              enumProtocolMap;
    private static final Map<Class<?>, EnumProtocol>                                              classEnumProtocolMap;
    private final        String                                                                   name;
    private final        int                                                                      status;
    private final        Map<EnumProtocolDirection, BiMap<Integer, Class<?>>>                     packetsMap;
    private final        Map<EnumProtocolDirection, Int2ObjectMap<Supplier<? extends Packet<?>>>> packetsSupplierMap;

    EnumProtocol(final String name, final int status)
    {
        this.packetsMap = Maps.newEnumMap(EnumProtocolDirection.class);
        this.packetsSupplierMap = Maps.newEnumMap(EnumProtocolDirection.class);
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

    public synchronized <T extends Packet<?>> void init(final EnumProtocolDirection enumProtocolDirection, final int id, final Class<T> clazz, final Supplier<T> supplier)
    {
        BiMap<Integer, Class<?>> create = this.packetsMap.get(enumProtocolDirection);
        final Int2ObjectMap<Supplier<? extends Packet<?>>> suppMap;
        if (create == null)
        {
            create = HashBiMap.create();
            suppMap = new Int2ObjectOpenHashMap<>((this == PLAY) ? PLAY_PACKETS_SIZE : OTHER_PACKETS_SIZE, .1f);
            this.packetsMap.put(enumProtocolDirection, create);
            this.packetsSupplierMap.put(enumProtocolDirection, suppMap);
        }
        else
        {
            suppMap = this.packetsSupplierMap.get(enumProtocolDirection);
        }
        if (create.containsValue(clazz))
        {
            throw new IllegalArgumentException(enumProtocolDirection + " packet " + clazz.getName() + " is already known to ID " + Integer.toHexString(create.inverse().get(clazz)) + " (" + create.get(id) + ")");
        }
        if (create.containsKey(id))
        {
            throw new IllegalArgumentException(enumProtocolDirection + " packet (" + clazz.getName() + ") with id " + Integer.toHexString(id) + " is already known to packet " + create.get(id));
        }
        classEnumProtocolMap.put(clazz, this);
        create.put(id, clazz);
        suppMap.put(id, supplier);
    }

    public synchronized <T extends Packet<?>> void init(final EnumProtocolDirection enumProtocolDirection, final int id, final Class<T> clazz)
    {
        this.init(enumProtocolDirection, id, clazz, getOrCreateSuppiler(clazz));
    }

    @SuppressWarnings("unchecked")
    private static <T extends Packet<?>> Supplier<T> getOrCreateSuppiler(final Class<T> clazz)
    {
        try
        {
            final ClassPool pool = ClassPool.getDefault();
            final CtClass tempClass = pool.getOrNull(clazz.getName() + "$" + INNER_CLASS_NAME);
            if (tempClass != null)
            {
                return (Supplier<T>) Class.forName(clazz.getName() + "$" + INNER_CLASS_NAME).getFields()[0].get(null);
            }
            final CtClass packetClass = pool.get(clazz.getName());
            final CtClass packetInitClass = packetClass.makeNestedClass(INNER_CLASS_NAME, true);
            final CtClass suppCtClass = pool.get(Supplier.class.getName());
            packetInitClass.addInterface(suppCtClass);

            final CtMethod method = new CtMethod(pool.get(Object.class.getName()), "get", DioriteReflectionUtils.EMPTY_CLASSES, packetInitClass);
            method.setBody("{return new " + clazz.getName() + "();}");
            packetInitClass.addMethod(method);

            final CtField field = new CtField(suppCtClass, "INSTANCE", packetInitClass);
            field.setModifiers(Modifier.setPublic(field.getModifiers() | Modifier.STATIC));
            packetInitClass.addField(field, "Class.forName(\"" + packetInitClass.getName() + "\").newInstance();");

            final Class<?> createdClass = packetInitClass.toClass();
            return (Supplier<T>) createdClass.getFields()[0].get(null);
        } catch (final Throwable e)
        {
            throw new RuntimeException("Fatal error when creating packet suppliers.", e);
        }
    }

    public Integer getPacketID(final EnumProtocolDirection protocolDirection, final Packet<?> packet)
    {
        return this.packetsMap.get(protocolDirection).inverse().get(packet.getClass());
    }

    public Packet<?> createPacket(final EnumProtocolDirection protocolDirection, final int id)
    {
        final Supplier<? extends Packet<?>> supplier = this.packetsSupplierMap.get(protocolDirection).get(id);
        if (supplier == null)
        {
            return null;
        }
        try
        {
            return supplier.get();
        } catch (final Exception e)
        {
            throw new RuntimeException("Can't create packet instance!");
        }
    }

    public static void init(final Class<? extends Packet<?>> clazz)
    {
        if (! clazz.isAnnotationPresent(PacketClass.class))
        {
            throw new IllegalArgumentException("To use this method, class must be annotated with PacketClass");
        }
        final PacketClass packetData = clazz.getAnnotation(PacketClass.class);
        packetData.protocol().init(packetData.direction(), packetData.id(), clazz);
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
        enumProtocolMap = new Int2ObjectOpenHashMap<>();
        classEnumProtocolMap = Maps.newHashMap();
        for (final EnumProtocol enumProtocol : values())
        {
            enumProtocolMap.put(enumProtocol.getStatus(), enumProtocol);
        }
    }
}