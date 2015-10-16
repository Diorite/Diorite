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

package org.diorite;

import java.util.Map.Entry;

import org.diorite.utils.pipeline.BasePipeline;

import junit.framework.TestCase;

@SuppressWarnings("MagicNumber")
public class PipelineTest extends TestCase
{
    @org.junit.Test
    public void testPipeline() throws Exception
    {
        final BasePipeline<String> pipeline = new BasePipeline<>();
        pipeline.addAfterIfContainsFromHead("key#0", "key#1", "value#1");
        pipeline.addAfterIfContainsFromTail("key#2", "key#3", "value#3");
        pipeline.addBeforeIfContainsFromHead("key#4", "key#5", "value#5");
        pipeline.addBeforeIfContainsFromTail("key#6", "key#7", "value#7");
        pipeline.add("key#8", "value#8");

        assertTrue("pipeline should have 1 elements, found: " + pipeline.size(), pipeline.size() == 1);
        assertTrue("1 element isn't key#8 ", pipeline.getNameOfFirst().equals("key#8"));
        assertTrue("1 element isn't value#8 ", pipeline.getLast().equals("value#8"));

        pipeline.addAfterIfContainsFromHead("key#9", "key#10", "value#10");
        pipeline.addAfterIfContainsFromTail("key#11", "key#12", "value#12");
        pipeline.addBeforeIfContainsFromHead("key#13", "key#14", "value#14");
        pipeline.addBeforeIfContainsFromTail("key#15", "key#16", "value#16");
        pipeline.addBefore("key#8", "key#17", "value#17");
        pipeline.addFirst("key#18", "value#18");
        pipeline.addLast("key#19", "value#19");
        pipeline.addAfterFromTail("key#19", "key#20", "value#20");
        pipeline.addAfterFromTail("key#19", "key#21", "value#21");
        pipeline.addAfterFromHead("key#17", "key#22", "value#22");
        pipeline.addAfterFromHead("key#17", "key#23", "value#23");
        pipeline.addAfterFromTail("key#22", "key#24", "value#24");
        pipeline.addAfterFromTail("key#22", "key#25", "value#25");
        pipeline.addAfterFromHead("key#18", "key#26", "value#26");
        pipeline.addAfterFromHead("key#18", "key#27", "value#27");
        pipeline.addAfterFromHead("key#18", "key#28", "value#28");
        pipeline.addAfterFromHead("key#28", "key#29", "value#29");
        pipeline.addAfterFromTail("key#18", "key#30", "value#30");
        pipeline.addAfterFromTail("key#18", "key#31", "value#31");
        pipeline.addBeforeFromTail("key#18", "key#32", "value#32");
        pipeline.addBeforeFromTail("key#18", "key#33", "value#33");
        pipeline.addBeforeFromHead("key#18", "key#34", "value#34");
        pipeline.addBeforeFromHead("key#34", "key#35", "value#35");
        pipeline.addBeforeFromTail("key#35", "key#36", "value#36");
        pipeline.addBeforeFromHead("key#20", "key#37", "value#37");
        pipeline.addBeforeFromTail("key#37", "key#38", "value#38");
        pipeline.addBeforeFromTail("key#20", "key#39", "value#39");
        pipeline.set("key#30", "value#300");
        pipeline.replace("key#33", "key#333", "value#333");

        assertTrue("pipeline should have 24 elements, found: " + pipeline.size(), pipeline.size() == 24);
        final int[] keys = {32, 333, 36, 35, 34, 18, 31, 30, 28, 29, 27, 26, 17, 23, 22, 25, 24, 8, 19, 21, 38, 37, 39, 20};
        final int[] values = {32, 333, 36, 35, 34, 18, 31, 300, 28, 29, 27, 26, 17, 23, 22, 25, 24, 8, 19, 21, 38, 37, 39, 20};
        int i = 0;
        for (final Entry<String, String> e : pipeline.entrySet())
        {
            final String key = e.getKey();
            final String value = e.getValue();
            assertTrue("excepted key: 'key#" + keys[i] + "' key: '" + key + "'", key.equals("key#" + keys[i]));
            assertTrue("excepted value: 'value#" + values[i] + "' value: '" + value + "'", value.equals("value#" + values[i]));
            i++;
        }
    }
}