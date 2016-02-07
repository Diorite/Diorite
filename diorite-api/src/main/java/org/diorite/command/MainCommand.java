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

/**
 * Represent main command, that isn't any subcommand.
 */
public interface MainCommand extends Command
{
    /**
     * Returns full name of this command, with unique prefix, like diorite:gamemode
     *
     * @return full name of this command, with unique prefix, like diorite:gamemode
     */
    String getFullName();

    /**
     * Returns priority of this command. <br>
     * Commands with higher priority will be checked first,
     * priority can be changed in cfg. (and on runtime)
     *
     * @return priority of this command.
     */
    int getPriority();

    /**
     * Set priority of this command. <br>
     * Commands with higher priority will be checked first,
     * priority can be changed in cfg. (and on runtime)
     *
     * @param priority priority to be used.
     */
    void setPriority(int priority);
}
