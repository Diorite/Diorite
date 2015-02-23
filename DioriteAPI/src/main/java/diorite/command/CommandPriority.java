package diorite.command;

import java.util.Map;
import java.util.Objects;

import com.google.common.collect.ImmutableMap;

public final class CommandPriority
{
    public static final byte HIGHEST = 64;
    public static final byte HIGH    = 32;
    public static final byte NORMAL  = 0;
    public static final byte LOW     = - 32;
    public static final byte LOWEST  = - 64;

    private static final Map<String, Byte> valuesMap = ImmutableMap.<String, Byte>builder().put("HIGHEST", CommandPriority.HIGHEST).put("HIGH", CommandPriority.HIGH).put("NORMAL", CommandPriority.NORMAL).put("LOW", CommandPriority.LOW).put("LOWEST", CommandPriority.LOWEST).build();

    public static byte findByName(final String name)
    {
        Objects.requireNonNull(name, "name can't be null");
        final Byte b = valuesMap.get(name.toUpperCase());
        if (b != null)
        {
            return b;
        }
        try
        {
            return Byte.parseByte(name);
        } catch (final NumberFormatException ignored)
        {
        }
        throw new IllegalArgumentException("No CommandPriority value for '" + name + "'");
    }

    private CommandPriority()
    {
    }
}
