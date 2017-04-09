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

package org.diorite.config.serialization.snakeyaml.emitter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;

import org.yaml.snakeyaml.emitter.EmitterException;
import org.yaml.snakeyaml.events.DocumentStartEvent;
import org.yaml.snakeyaml.events.StreamEndEvent;

class ExpectDocumentStart implements EmitterState
{
    private final boolean first;

    ExpectDocumentStart(boolean first)
    {
        this.first = first;
    }

    @Override
    public void expect(Emitter emitter) throws IOException
    {
        expect(emitter, this.first);
    }

    public static void expect(Emitter emitter, boolean first) throws IOException
    {
        if (emitter.event instanceof DocumentStartEvent)
        {
            DocumentStartEvent ev = (DocumentStartEvent) emitter.event;
            if (((ev.getVersion() != null) || (ev.getTags() != null)) && emitter.openEnded)
            {
                emitter.writeIndicator("...", true, false, false);
                emitter.writeIndent();
            }
            if (ev.getVersion() != null)
            {
                String versionText = emitter.prepareVersion(ev.getVersion());
                emitter.writeVersionDirective(versionText);
            }
            emitter.tagPrefixes = new LinkedHashMap<>(Emitter.DEFAULT_TAG_PREFIXES);
            if (ev.getTags() != null)
            {
                Set<String> handles = new TreeSet<>(ev.getTags().keySet());
                for (String handle : handles)
                {
                    String prefix = ev.getTags().get(handle);
                    emitter.tagPrefixes.put(prefix, handle);
                    String handleText = emitter.prepareTagHandle(handle);
                    String prefixText = emitter.prepareTagPrefix(prefix);
                    emitter.writeTagDirective(handleText, prefixText);
                }
            }
            boolean implicit =
                    first && ! ev.getExplicit() && ! emitter.canonical && (ev.getVersion() == null) && ((ev.getTags() == null) || ev.getTags().isEmpty()) &&
                    ! emitter.checkEmptyDocument();
            if (! implicit)
            {
                emitter.writeIndent();
                emitter.writeIndicator("---", true, false, false);
                if (emitter.canonical)
                {
                    emitter.writeIndent();
                }
            }
            emitter.state = new ExpectDocumentRoot();
        }
        else if (emitter.event instanceof StreamEndEvent)
        {
            // TODO fix 313 PyYAML changeset
            // if (openEnded) {
            // writeIndicator("...", true, false, false);
            // writeIndent();
            // }
            emitter.writeStreamEnd();
            emitter.state = new ExpectNothing();
        }
        else
        {
            throw new EmitterException("expected DocumentStartEvent, but got " + emitter.event);
        }
    }
}
