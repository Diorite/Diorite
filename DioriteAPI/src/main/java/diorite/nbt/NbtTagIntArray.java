package diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagIntArray extends NbtAbstractTag<NbtTagIntArray>
{
    protected int[] value;

    public NbtTagIntArray()
    {
    }

    public NbtTagIntArray(final String name)
    {
        super(name);
    }

    public NbtTagIntArray(final String name, final int[] value)
    {
        super(name);
        this.value = value;
    }

    public NbtTagIntArray(final String name, final NbtTagCompound parent, final int[] value)
    {
        super(name, parent);
        this.value = value;
    }

    @Override
    public NbtTagIntArray read(final NbtInputStream inputStream, final boolean hasName) throws IOException
    {
        super.read(inputStream, hasName);
        final int size = inputStream.readInt();
        final int[] ints = new int[size];
        for (int i = 0; i < size; i++)
        {
            ints[i] = inputStream.readInt();
        }
        this.value = ints;
        return this;
    }

    @Override
    public NbtTagIntArray write(final NbtOutputStream outputStream, final boolean hasName) throws IOException
    {
        super.write(outputStream, hasName);
        outputStream.writeInt(this.value.length);
        for (final int i : this.value)
        {
            outputStream.writeInt(i);
        }
        return this;
    }

    @Override
    public NbtTagType getType()
    {
        return NbtTagType.INTEGER_ARRAY;
    }

    public int[] getValue()
    {
        return this.value;
    }

    public void setValue(final int[] value)
    {
        Validate.notNull(value, "ints can't be null");
        this.value = value;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}