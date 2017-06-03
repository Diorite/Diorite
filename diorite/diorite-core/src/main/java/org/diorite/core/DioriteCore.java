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

package org.diorite.core;

import java.security.KeyPair;

import org.diorite.Diorite;
import org.diorite.core.event.EventManagerImpl;
import org.diorite.core.material.InternalBlockRegistry;
import org.diorite.core.material.InternalItemRegistry;
import org.diorite.core.protocol.Protocol;
import org.diorite.core.protocol.connection.ServerConnection;
import org.diorite.gameprofile.SessionService;
import org.diorite.plugin.Plugin;

public interface DioriteCore extends Diorite
{
    SessionService getSessionService();

    KeyPair getKeyPair();

    Protocol<?> getProtocol();

    ServerConnection getServerConnection();

    @Override
    InternalBlockRegistry getBlockRegistry();

    @Override
    InternalItemRegistry getItemRegistry();

    @Override
    EventManagerImpl getEventManager();

    /**
     * Returns internal diorite plugin instance.
     *
     * @return internal diorite plugin instance.
     */
    Plugin getDioritePlugin();

    static DioriteCore getDiorite()
    {
        return (DioriteCore) Diorite.getDiorite();
    }
}
