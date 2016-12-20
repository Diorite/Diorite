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

package org.diorite.config;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

public class SimpleConfigTest
{
    @Test
    public void loadTest() throws Exception
    {
        ConfigTemplate<SimpleConfig> configFile = DioriteConfigs.getConfigFile(SimpleConfig.class);
        Assert.assertNotNull(configFile);
        Assert.assertEquals(SimpleConfig.class.getSimpleName(), configFile.getName());
        Assert.assertEquals(StandardCharsets.UTF_8, configFile.getDefaultDecoder().charset());
        Assert.assertEquals(StandardCharsets.UTF_8, configFile.getDefaultEncoder().charset());

        System.out.println("[SimpleConfigTest] loading simpleConfig.yml");
        try (InputStream stream = SimpleConfigTest.class.getResourceAsStream("/simpleConfig.yml"))
        {
            Assert.assertNotNull(stream);

            SimpleConfig config = configFile.load(stream);
            Assert.assertNotNull(config);
            Assert.assertEquals("def", config.get("invalid", "def", String.class));

            System.out.println("[SimpleConfigTest] testing basic values");
            Assert.assertEquals(10, config.getMoney(), 0.1);
            config.setMoney(20);
            Assert.assertEquals(20, config.get("money", 0.0, double.class), 0.1);
            config.set("money", 5);
            Assert.assertEquals(5, config.getMoney(), 0.1);

            System.out.println("[SimpleConfigTest] testing remove");
            config.remove("money");
            Assert.assertEquals(0, config.getMoney(), 0.1);

            config.setMoney(20);
            Assert.assertEquals(20, config.getMoney(), 0.1);
            config.setMoney(0);
            Assert.assertEquals("0", config.get("money", "0", String.class));
            System.out.println("[SimpleConfigTest] done\n");
        }
    }
}
