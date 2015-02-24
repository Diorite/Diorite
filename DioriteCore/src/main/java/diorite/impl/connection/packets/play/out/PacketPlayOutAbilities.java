package diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.play.PacketPlayOutListener;

public class PacketPlayOutAbilities implements PacketPlayOut
{
    private boolean isInvulnerable;
    private boolean isFlying;
    private boolean canFly;
    private boolean canInstantlyBuild;
    private float   flyingSpeed;
    private float   walkingSpeed;

    public PacketPlayOutAbilities()
    {
    }

    public PacketPlayOutAbilities(final boolean isInvulnerable, final float walkingSpeed, final float flyingSpeed, final boolean canInstantlyBuild, final boolean canFly, final boolean isFlying)
    {
        this.isInvulnerable = isInvulnerable;
        this.walkingSpeed = walkingSpeed;
        this.flyingSpeed = flyingSpeed;
        this.canInstantlyBuild = canInstantlyBuild;
        this.canFly = canFly;
        this.isFlying = isFlying;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        final byte flags = data.readByte();
        this.isInvulnerable = ((flags & 1) > 0);
        this.isFlying = ((flags & 2) > 0);
        this.canFly = ((flags & 4) > 0);
        this.canInstantlyBuild = ((flags & 8) > 0);
        this.flyingSpeed = data.readFloat();
        this.walkingSpeed = data.readFloat();
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        byte flags = 0;
        if (this.isInvulnerable)
        {
            -- flags;
        }
        if (this.isFlying)
        {
            flags |= 2;
        }
        if (this.canFly)
        {
            flags |= 4;
        }
        if (this.canInstantlyBuild)
        {
            flags |= 8;
        }
        data.writeByte(flags);
        data.writeFloat(this.flyingSpeed);
        data.writeFloat(this.walkingSpeed);
    }

    public boolean isInvulnerable()
    {
        return this.isInvulnerable;
    }

    public void setInvulnerable(final boolean isInvulnerable)
    {
        this.isInvulnerable = isInvulnerable;
    }

    public float getWalkingSpeed()
    {
        return this.walkingSpeed;
    }

    public void setWalkingSpeed(final float walkingSpeed)
    {
        this.walkingSpeed = walkingSpeed;
    }

    public float getFlyingSpeed()
    {
        return this.flyingSpeed;
    }

    public void setFlyingSpeed(final float flyingSpeed)
    {
        this.flyingSpeed = flyingSpeed;
    }

    public boolean isCanInstantlyBuild()
    {
        return this.canInstantlyBuild;
    }

    public void setCanInstantlyBuild(final boolean canInstantlyBuild)
    {
        this.canInstantlyBuild = canInstantlyBuild;
    }

    public boolean isCanFly()
    {
        return this.canFly;
    }

    public void setCanFly(final boolean canFly)
    {
        this.canFly = canFly;
    }

    public boolean isFlying()
    {
        return this.isFlying;
    }

    public void setFlying(final boolean isFlying)
    {
        this.isFlying = isFlying;
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("isInvulnerable", this.isInvulnerable).append("isFlying", this.isFlying).append("canFly", this.canFly).append("canInstantlyBuild", this.canInstantlyBuild).append("flyingSpeed", this.flyingSpeed).append("walkingSpeed", this.walkingSpeed).toString();
    }
}
