/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.command.defaults;

import org.diorite.impl.command.CommandMapImpl;

public final class RegisterDefaultCommands
{
    private RegisterDefaultCommands()
    {
    }

    public static void init(final CommandMapImpl cmds)
    {
        cmds.registerCommand(new DevCmd()); // TODO: remove

        cmds.registerCommand(new SayCmd());
        cmds.registerCommand(new TpsCmd());
        cmds.registerCommand(new FlyCmd());
        cmds.registerCommand(new SaveCmd());
        cmds.registerCommand(new StopCmd());
        cmds.registerCommand(new KickCmd());
        cmds.registerCommand(new GiveCmd());
        cmds.registerCommand(new ItemCmd());
        cmds.registerCommand(new OnlineCmd());
        cmds.registerCommand(new SetTpsCmd());
        cmds.registerCommand(new GamemodeCmd());
        cmds.registerCommand(new ParticleCmd());
        cmds.registerCommand(new BroadcastCmd());
        cmds.registerCommand(new ColoredConsoleCmd());
        cmds.registerCommand(new PerformanceMonitorCmd());
        cmds.registerCommand(new PluginsCmd());
        cmds.registerCommand(new VersionCmd());
        cmds.registerCommand(new TimingsCommand());
    }
}
