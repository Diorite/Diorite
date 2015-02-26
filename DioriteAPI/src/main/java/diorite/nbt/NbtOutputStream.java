package diorite.nbt;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class NbtOutputStream extends DataOutputStream
{
    public NbtOutputStream(final OutputStream out)
    {
        super(out);
    }

    public void write(final NbtAbstractTag<?> tag) throws IOException
    {
        tag.write(this, true);
    }
}