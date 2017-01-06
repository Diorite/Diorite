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

import org.yaml.snakeyaml.events.MappingEndEvent;

class ExpectFirstFlowMappingKey implements EmitterState
{
    @Override
    public void expect(Emitter emitter) throws IOException
    {
        if (emitter.event instanceof MappingEndEvent)
        {
            emitter.indent = emitter.indents.pop();
            emitter.flowLevel--;
            emitter.writeIndicator("}", false, false, false);
            emitter.state = emitter.states.pop();
        }
        else
        {
            if (emitter.canonical || ((emitter.column > emitter.bestWidth) && emitter.splitLines) || emitter.prettyFlow)
            {
                emitter.writeIndent();
            }
            if (! emitter.canonical && emitter.checkSimpleKey())
            {
                emitter.states.push(new ExpectFlowMappingSimpleValue());
                emitter.expectNode(false, true, true);
            }
            else
            {
                emitter.writeIndicator("?", true, false, false);
                emitter.states.push(new ExpectFlowMappingValue());
                emitter.expectNode(false, true, false);
            }
        }
    }
}
