/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.command;

import java.util.Collection;
import java.util.regex.Pattern;

import org.diorite.Diorite;

/**
 * Simple builder for commands
 */
public interface PluginCommandBuilder
{
    /**
     * Adds alias to builder.
     *
     * @param str alias to be added.
     *
     * @return this same builder for method chains.
     */
    PluginCommandBuilder alias(String str);

    /**
     * Adds aliases to builder.
     *
     * @param str aliases to be added.
     *
     * @return this same builder for method chains.
     */
    PluginCommandBuilder alias(String... str);

    /**
     * Adds aliases to builder.
     *
     * @param str aliases to be added.
     *
     * @return this same builder for method chains.
     */
    PluginCommandBuilder alias(Collection<String> str);

    /**
     * Set pattern of this command, pattern is optional.
     *
     * @param pattern pattern to be used.
     *
     * @return this same builder for method chains.
     */
    PluginCommandBuilder pattern(String pattern);

    /**
     * Set pattern of this command, pattern is optional.
     *
     * @param pattern pattern to be used.
     *
     * @return this same builder for method chains.
     */
    PluginCommandBuilder pattern(String... pattern);

    /**
     * Set pattern of this command, pattern is optional.
     *
     * @param pattern pattern to be used.
     *
     * @return this same builder for method chains.
     */
    PluginCommandBuilder pattern(Pattern pattern);

    /**
     * Set executor of this command, you must set any executor.
     *
     * @param executor executor to be used.
     *
     * @return this same builder for method chains.
     */
    PluginCommandBuilder executor(CommandExecutor executor);

    /**
     * Set exception handler of this command.
     *
     * @param handler handler to be used.
     *
     * @return this same builder for method chains.
     */
    PluginCommandBuilder exceptionHandler(ExceptionHandler handler);

    /**
     * Set priority of this command,
     * commands with higher priority will be checked first,
     * priority can be changed in cfg. (and on runtime)
     *
     * @param priority priority to be used.
     *
     * @return this same builder for method chains.
     */
    PluginCommandBuilder priority(int priority);

    /**
     * Build this command and returns it.
     *
     * @return created command.
     */
    PluginCommand build();

    /**
     * Build this command, adds it to diorite by {@link CommandMap#registerCommand(PluginCommand)} and returns it.
     *
     * @return created command.
     */
    default PluginCommand register()
    {
        final PluginCommand cmd = this.build();
        Diorite.getCommandMap().registerCommand(cmd);
        return cmd;
    }
}
