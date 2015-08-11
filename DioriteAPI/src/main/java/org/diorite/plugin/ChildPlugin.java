package org.diorite.plugin;

public interface ChildPlugin extends BasePlugin
{
    BasePlugin getParent();

    /**
     * Returns only last part of name, without parent plugins.
     *
     * @return simple name, without parent names.
     */
    default String getSimpleName()
    {
        final int i = this.getName().lastIndexOf("::");
        if (i == - 1)
        {
            return this.getName();
        }
        return this.getName().substring(i + 2);
    }

    @Override
    default String getFullName()
    {
        return this.getSimpleName() + " v" + this.getVersion() + " (" + this.getParent().getFullName() + ")";
    }
}
