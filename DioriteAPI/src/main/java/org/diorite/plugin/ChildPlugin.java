package org.diorite.plugin;

import java.io.File;

public interface ChildPlugin extends BasePlugin
{
    String CHILD_SEPARATOR = "::";

    BasePlugin getParent();

    /**
     * Returns only last part of name, without parent plugins.
     *
     * @return simple name, without parent names.
     */
    default String getSimpleName()
    {
        final int i = this.getName().lastIndexOf(CHILD_SEPARATOR);
        if (i == - 1)
        {
            return this.getName();
        }
        return this.getName().substring(i + 2);
    }

    @Override
    default File getDataFolder()
    {
        return new File(this.getParent().getDataFolder(), this.getSimpleName());
    }

    @Override
    default String getFullName()
    {
        return this.getSimpleName() + " v" + this.getVersion() + " (" + this.getParent().getFullName() + ")";
    }
}
