/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.core.protocol;

import javax.annotation.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.diorite.core.protocol.connection.internal.Packet;
import org.diorite.core.protocol.connection.internal.PacketType;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;

public abstract class Protocol<T extends ProtocolVersion<?>>
{
    protected final String name;
    protected final Int2ObjectMap<T> versions       = new Int2ObjectOpenHashMap<>(5);
    protected final Map<String, T>   versionsByName = new HashMap<>(5);

    public Protocol(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public IntSet getAvailableVersions()
    {
        return IntSets.unmodifiable(this.versions.keySet());
    }

    public Set<String> getAvailableVersionNames()
    {
        return Collections.unmodifiableSet(this.versionsByName.keySet());
    }

    @Nullable
    public T getVersion(int ver)
    {
        return this.versions.get(ver);
    }

    @Nullable
    public T getVersion(String ver)
    {
        return this.versionsByName.get(ver);
    }

    public PacketType getPacketType(Class<? extends Packet> packet)
    {
        for (T ver : this.versions.values())
        {
            if (ver.isPacket(packet))
            {
                return ver.getPackets().getType(packet);
            }
        }
        throw new IllegalStateException("Unknown packet!");
    }

    public T getVersionByPacketClass(Class<? extends Packet> packet)
    {
        for (T ver : this.versions.values())
        {
            if (ver.isPacket(packet))
            {
                return ver;
            }
        }
        throw new IllegalStateException("Unknown packet!");
    }

    public void addVersion(T version)
    {
        this.versions.put(version.version, version);
        this.versionsByName.put(version.versionName, version);
        for (String ver : version.getAliases())
        {
            this.versionsByName.put(ver, version);
        }
    }

    public abstract T getDefault();
}
