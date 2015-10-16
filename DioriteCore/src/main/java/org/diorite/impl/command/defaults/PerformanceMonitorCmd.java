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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.command.CommandPriority;
import org.diorite.command.sender.CommandSender;

public class PerformanceMonitorCmd extends SystemCommandImpl
{
    public static final  int     ONE_MiB    = 1_048_576;
    private static final Pattern SPACES_PAT = Pattern.compile("(\\u0020)+");

    public PerformanceMonitorCmd()
    {
        super("performanceMonitor", Pattern.compile("(performance(Monitor|)|perf)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            final Runtime rt = Runtime.getRuntime();
            if (args.has(0) && args.asString(0).equalsIgnoreCase("-dump"))
            {
                int classSize = 0;
                int methodSize = 0;
                int threadSize = 0;
                Map<Thread, StackTraceElement[]> allStackTraces = new LinkedHashMap<>(Thread.getAllStackTraces());

                for (Iterator<Entry<Thread, StackTraceElement[]>> iterator = allStackTraces.entrySet().iterator(); iterator.hasNext(); )
                {
                    final Entry<Thread, StackTraceElement[]> entry = iterator.next();
                    Thread t = entry.getKey();
                    boolean add = true;
                    if (args.length() > 1)
                    {
                        add = false;
                        for (int i = 1; i < args.length(); i++)
                        {
                            if (t.getName().toLowerCase().contains(args.asString(i).toLowerCase()))
                            {
                                add = true;
                                break;
                            }
                        }
                    }
                    if (! add)
                    {
                        iterator.remove();
                        continue;
                    }

                    if (t.getName().length() > threadSize)
                    {
                        threadSize = t.getName().length();
                    }

                    StackTraceElement[] sts = entry.getValue();
                    for (StackTraceElement st : sts)
                    {
                        if (st.getClassName().length() > classSize)
                        {
                            classSize = st.getClassName().length();
                        }
                        if (st.getMethodName().length() > methodSize)
                        {
                            methodSize = st.getMethodName().length();
                        }
                    }
                }
                final String[] strings = new String[allStackTraces.size()];
                int i = 0;
                StringBuilder sb = new StringBuilder(2048);
                sb.append("\n&c====== &3Thread dump &c======\n");
                for (Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet())
                {

                    // WARN: shit code here.
                    Thread t = entry.getKey();
                    StackTraceElement[] sts = entry.getValue();
                    sb.append("\n&7[&3").append(t.getName()).append("&7]");
                    for (int j = t.getName().length(); j < methodSize; j++)
                    {
                        sb.append(' ');
                    }
                    sb.append(" (&3").append(t.getId()).append("&7)");
                    for (int j = Long.toString(t.getId()).length(); j < 5; j++)
                    {
                        sb.append(' ');
                    }
                    sb.append(" State: &3").append(t.getState()).append(" &7 priority=").append(t.getPriority()).append(" \n");
                    for (StackTraceElement st : sts)
                    {
                        boolean isOwn = st.getClassName().contains("diorite");
                        if (isOwn)
                        {
                            sb.append("    &c&l== &r&3");
                        }
                        else
                        {
                            sb.append("    &7&l-- &r&3");
                        }
                        sb.append(st.getClassName());
                        for (int j = st.getClassName().length(); j < classSize; j++)
                        {
                            sb.append(' ');
                        }
                        sb.append(" &c->&3 ").append(st.getMethodName());
                        for (int j = st.getMethodName().length(); j < methodSize; j++)
                        {
                            sb.append(' ');
                        }
                        sb.append("&7[&3");
                        if (st.isNativeMethod())
                        {
                            sb.append("NATIVE");
                        }
                        else
                        {
                            int line = st.getLineNumber();
                            if (line == - 1)
                            {
                                sb.append(" NULL ");
                            }
                            else
                            {
                                // don't look at that, please.
                                String lineStr = Integer.toString(line);
                                if (lineStr.length() == 1)
                                {
                                    sb.append("  0").append(lineStr).append("  ");
                                }
                                else if (lineStr.length() == 2)
                                {
                                    sb.append("  ").append(lineStr).append("  ");
                                }
                                else if (lineStr.length() == 3)
                                {
                                    sb.append(" 0").append(lineStr).append(" ");
                                }
                                else if (lineStr.length() == 4)
                                {
                                    sb.append(" ").append(lineStr).append(" ");
                                }
                                else if (lineStr.length() == 5)
                                {
                                    sb.append("0").append(lineStr);
                                }
                                else
                                {
                                    sb.append(lineStr);
                                }
                            }
                        }
                        sb.append("&7]").append(" \n");
                    }
                    strings[i++] = sb.toString();
                    sb = new StringBuilder(2048);
//                    sb.append("&c=========================\n\n");
                }
                sb.append(" ");
                if (sender.isConsole())
                {
                    sender.sendSimpleColoredMessage(strings);
                }
                else
                {
                    sender.getCore().sendConsoleSimpleColoredMessage(strings);
                    for (int i1 = 0, stringsLength = strings.length; i1 < stringsLength; i1++)
                    {
                        strings[i1] = SPACES_PAT.matcher(strings[i1]).replaceAll(" ");
                    }
                    sender.sendSimpleColoredMessage(strings);
                }
                return;
            }
            boolean gc = args.has(0) && args.asString(0).equalsIgnoreCase("-gc");
            boolean memOnly = args.has(0) && args.asString(0).equalsIgnoreCase("-mem");
            if (gc)
            {
                this.display(sender, true);
                rt.gc();
            }
            this.display(sender, memOnly || gc);
        });
    }

    private void display(final CommandSender sender, final boolean onlyMem)
    {
        final Runtime rt = Runtime.getRuntime();
        final long maxMemTemp = rt.maxMemory();
        final long allocated = rt.totalMemory();
        final long free = rt.freeMemory();
        final StringBuilder sb = new StringBuilder(512);
        sb.append("&7====== &3Performance Monitor &7======\n");
        sb.append("&7  == &3Memory &7==\n");
        sb.append("&7    Used: &3").append(((allocated - free) / ONE_MiB)).append(" &7MiB");
        sb.append("&7    (Free: &3").append((free / ONE_MiB)).append(" &7MiB)\n");
        sb.append("&7    Allocated: &3").append((allocated / ONE_MiB)).append(" &7MiB\n");
        if (maxMemTemp != Long.MAX_VALUE)
        {
            sb.append("&7    Max: &3").append((maxMemTemp / ONE_MiB)).append(" &7MiB");
            sb.append("&7    (Not Allocated: &3").append(((maxMemTemp - allocated) / ONE_MiB)).append(" &7MiB)");
        }
        else
        {
            sb.append("&7    No memory limit.");
        }
        if (! onlyMem)
        {
            sb.append('\n');
//            sb.append("&7  == &3CPU &7==\n");
//            sb.append("&7    Available Processors: &3").append(rt.availableProcessors()).append("\n");
            sb.append("&7  == &3Diorite &7==\n");
            sb.append("&7    Waiting input actions: &3").append(DioriteCore.getInstance().getInputThread().getActionsSize());
        }
        sb.append('\n');
        sender.sendSimpleColoredMessage(sb.toString());
    }
}
