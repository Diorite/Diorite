package diorite.impl.connection.packets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.EnumProtocolDirection;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketClass
{
    byte id();

    EnumProtocol protocol() default EnumProtocol.PLAY;

    EnumProtocolDirection direction() default EnumProtocolDirection.CLIENTBOUND;
}
