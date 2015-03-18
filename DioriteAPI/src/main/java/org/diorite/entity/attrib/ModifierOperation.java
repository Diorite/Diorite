package org.diorite.entity.attrib;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.UnaryOperator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class ModifierOperation implements SimpleEnum<ModifierOperation>
{
    public static final  ModifierOperation                ADD_NUMBER          = new ModifierOperation("ADD_NUMBER", 0, ModifierValue::addX, mod -> mod.setY(mod.getX()));
    public static final  ModifierOperation                MULTIPLY_PERCENTAGE = new ModifierOperation("MULTIPLY_PERCENTAGE", 1, (value, d) -> value.addY(value.getX() * d));
    public static final  ModifierOperation                ADD_PERCENTAGE      = new ModifierOperation("ADD_PERCENTAGE", 2, (value, d) -> value.multipleY(1 + d));
    private static final Map<String, ModifierOperation>   byName              = new SimpleStringHashMap<>(3, .1f);
    @SuppressWarnings("MagicNumber")
    private static final TIntObjectMap<ModifierOperation> byID                = new TIntObjectHashMap<>(3, .1f);
    private final String enumName;
    private final int    id;
    private static final SortedSet<ModifierOperation> sortedByID = new TreeSet<>((e1, e2) -> Integer.compare(e1.id, e2.id));
    private final ModifierOperationAction      action;
    private final UnaryOperator<ModifierValue> onEnd;

    public ModifierOperation(final String enumName, final int id, final ModifierOperationAction action, final UnaryOperator<ModifierValue> onEnd)
    {
        this.enumName = enumName;
        this.id = id;
        this.action = action;
        this.onEnd = onEnd;
    }

    public ModifierOperation(final String enumName, final int id, final ModifierOperationAction action)
    {
        this.enumName = enumName;
        this.id = id;
        this.action = action;
        this.onEnd = m -> m;
    }

    @Override
    public String name()
    {
        return this.enumName;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    @Override
    public ModifierOperation byId(final int id)
    {
        return byID.get(id);
    }

    @Override
    public ModifierOperation byName(final String name)
    {
        return byName.get(name);
    }

    public ModifierOperationAction getAction()
    {
        return this.action;
    }

    public UnaryOperator<ModifierValue> getOnEnd()
    {
        return this.onEnd;
    }

    public ModifierValue use(final ModifierValue value, final double modValue)
    {
        return this.action.use(value, modValue);
    }

    public ModifierValue onEnd(final ModifierValue value)
    {
        return this.onEnd.apply(value);
    }

    @Override
    public int hashCode()
    {
        return this.id;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ModifierOperation))
        {
            return false;
        }

        final ModifierOperation that = (ModifierOperation) o;

        return this.id == that.id;

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).append("enumName", this.enumName).toString();
    }

    @FunctionalInterface
    public interface ModifierOperationAction
    {
        ModifierValue use(ModifierValue value, double modValue);
    }

    public static ModifierOperation getByID(final int id)
    {
        return byID.get(id);
    }

    public static ModifierOperation getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static ArrayList<ModifierOperation> getSortedByID()
    {
        return new ArrayList<>(sortedByID);
    }

    public static void register(final ModifierOperation element)
    {
        byID.put(element.getId(), element);
        byName.put(element.name(), element);
        sortedByID.add(element);
    }

    static
    {
        register(ADD_NUMBER);
        register(MULTIPLY_PERCENTAGE);
        register(ADD_PERCENTAGE);
    }
}
