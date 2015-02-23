package diorite.impl.connection.listeners;

import diorite.chat.BaseComponent;

@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface PacketListener
{
    void disconnect(BaseComponent message);
}
