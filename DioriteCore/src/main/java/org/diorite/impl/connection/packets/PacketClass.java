package org.diorite.impl.connection.packets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketClass
{
    int id();

    int size() default Packet.INITIAL_CAPACITY; // estimated size of packet, default to 512

    EnumProtocol protocol() default EnumProtocol.PLAY;

    EnumProtocolDirection direction() default EnumProtocolDirection.CLIENTBOUND;
}
