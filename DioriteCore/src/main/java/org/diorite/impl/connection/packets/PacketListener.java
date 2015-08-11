package org.diorite.impl.connection.packets;

import org.diorite.chat.component.BaseComponent;

@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface PacketListener
{
    void disconnect(BaseComponent message);
}
