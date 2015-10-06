package org.diorite.chat.component;

/**
 * Class used extended by chat components supporting replace methods.
 */
public abstract class ReplacableComponent
{

    /**
     * Replace for chat components, it will replace given string in any component with given {@link BaseComponent}.
     *
     * @param text      string to find.
     * @param component component to use instead of replaced text.
     * @param limit     max amount of replaces, use -1 for no limit.
     *
     * @return rest of limit after executing this method.
     */
    public int replace(final String text, final BaseComponent component, final int limit)
    {
        return this.replace_(text, component, limit);
    }

    /**
     * Replace for chat components, it will replace given string in any component with given {@link BaseComponent}.
     *
     * @param text      string to find.
     * @param component component to use instead of replaced text.
     * @param limit     max amount of replaces, use -1 for no limit.
     *
     * @return rest of limit after executing this method.
     */
    protected abstract int replace_(final String text, final BaseComponent component, int limit);

    /**
     * Replace for chat components, it will replace given string in any component with given {@link BaseComponent}.
     *
     * @param text      string to find.
     * @param component component to use instead of replaced text.
     *
     * @return rest of limit after executing this method.
     */
    public int replace(final String text, final BaseComponent component)
    {
        return this.replace(text, component, - 1);
    }

    /**
     * Replace for chat components, it will replace ONCE given string in any component with given {@link BaseComponent}.
     *
     * @param text      string to find.
     * @param component component to use instead of replaced text.
     *
     * @return if any string was replaced.
     */
    public boolean replaceOnce(final String text, final BaseComponent component)
    {
        return this.replace(text, component, 1) != 1;
    }

    /**
     * Simple replace for chat components, it will replace given string in any component with given string.
     *
     * @param text  string to find.
     * @param repl  string to use instead of replaced text.
     * @param limit max amount of replaces, use -1 for no limit.
     *
     * @return rest of limit after executing this method.
     */
    public int replace(final String text, final String repl, final int limit)
    {
        return this.replace_(text, repl, limit);
    }

    /**
     * Simple replace for chat components, it will replace given string in any component with given string.
     *
     * @param text  string to find.
     * @param repl  string to use instead of replaced text.
     * @param limit max amount of replaces, use -1 for no limit.
     *
     * @return rest of limit after executing this method.
     */
    protected abstract int replace_(final String text, final String repl, int limit);

    /**
     * Simple replace for chat components, it will replace given string in any component with given string.
     *
     * @param text string to find.
     * @param repl string to use instead of replaced text.
     *
     * @return rest of limit after executing this method.
     */
    public int replace(final String text, final String repl)
    {
        return this.replace(text, repl, - 1);
    }

    /**
     * Simple replace for chat components, it will replace ONCE given string in any component with given string.
     *
     * @param text string to find.
     * @param repl string to use instead of replaced text.
     *
     * @return if any string was replaced.
     */
    public boolean replaceOnce(final String text, final String repl)
    {
        return this.replace(text, repl, 1) != 1;
    }

    /**
     * Returns copy of this chat component element.
     *
     * @return copy of this chat component element.
     */
    public abstract ReplacableComponent duplicate();
}
