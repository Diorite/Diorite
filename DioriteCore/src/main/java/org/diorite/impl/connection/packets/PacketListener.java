package org.diorite.impl.connection.packets;

import org.slf4j.Logger;

import org.diorite.impl.DioriteCore;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;

@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface PacketListener
{
    void disconnect(BaseComponent message);

    default void disconnect(final String message)
    {
        this.disconnect(TextComponent.fromLegacyText(message));
    }

    default Logger getLogger()
    {
        return this.getCore().getLogger();
    }

    DioriteCore getCore();
}
