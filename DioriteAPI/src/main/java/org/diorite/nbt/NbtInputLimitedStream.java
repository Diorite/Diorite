package org.diorite.nbt;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtInputLimitedStream extends FilterInputStream
{
    private final NbtLimiter limiter;

    public NbtInputLimitedStream(final InputStream in, final NbtLimiter limiter)
    {
        super(in);
        this.limiter = limiter;
    }

    @Override
    public int read() throws IOException
    {
        this.limiter.countBytes(1);
        return super.read();
    }

    @Override
    public int read(final byte[] b) throws IOException
    {
        this.limiter.countBytes(b.length);
        return super.read(b);
    }

    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException
    {
        this.limiter.countBytes(len);
        return super.read(b, off, len);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("limiter", this.limiter).toString();
    }
}
