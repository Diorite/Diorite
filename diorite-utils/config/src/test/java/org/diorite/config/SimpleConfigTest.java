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
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.diorite.config.serialization.BeanObject;

public class SimpleConfigTest
{
    private final ConfigManager configManager = ConfigManager.create();

    @Test
    public void loadTest() throws Exception
    {
        ConfigTemplate<SimpleConfig> configTemplate = this.configManager.getConfigFile(SimpleConfig.class);
        Assert.assertNotNull(configTemplate);
        Assert.assertEquals(SimpleConfig.class.getSimpleName(), configTemplate.getName());
        Assert.assertEquals(StandardCharsets.UTF_8, configTemplate.getDefaultDecoder().charset());
        Assert.assertEquals(StandardCharsets.UTF_8, configTemplate.getDefaultEncoder().charset());

        System.out.println("[SimpleConfigTest] loading simpleConfig.yml");
        try (InputStream stream = SimpleConfigTest.class.getResourceAsStream("/simpleConfig.yml"))
        {
            Assert.assertNotNull(stream);

            SimpleConfig config = configTemplate.load(stream);
            Assert.assertNotNull(config);
            Assert.assertNotNull(config.template());
            Assert.assertSame(config.template(), configTemplate);
            Assert.assertNotNull(config.name());
            Assert.assertEquals("SimpleConfig", config.name());
            System.out.println("[SimpleConfigTest] loaded simpleConfig.yml (" + config.name() + ")");

            Assert.assertEquals("def", config.get("invalid", "def", String.class));

            System.out.println("[SimpleConfigTest] testing custom values: ");
            {
                Assert.assertSame(null, config.get("nope"));
                config.set("nope", 54);
                Assert.assertEquals(54, config.get("nope"));
                Assert.assertEquals(54, config.remove("nope"));
            }
            {
                BeanObject beanObject = new BeanObject();
                beanObject.setIntMap(new LinkedHashMap<>(5));
                beanObject.setList(List.of("a", "b", "c"));
                beanObject.setIntProperty(12);
                Assert.assertSame(null, config.get("bean"));
                config.set("bean", beanObject);

                Assert.assertEquals(beanObject, config.get("bean"));
                Assert.assertEquals(12, config.get("bean.intProperty"));
                Assert.assertSame(null, config.get("bean.stringProperty"));
                Assert.assertSame(null, config.get("bean.list2"));

                config.set("bean.stringProperty", "test");
                Assert.assertEquals("test", config.get("bean.stringProperty"));
                config.set("bean.stringProperty", null);
                Assert.assertSame(null, config.get("bean.stringProperty"));

                config.set("bean.list.0", "A");
                Assert.assertEquals("b", config.get("bean.list.1"));
                Assert.assertEquals(List.of("A", "b", "c"), config.get("bean.list"));

                Assert.assertEquals(beanObject, config.remove("bean"));
                Assert.assertSame(null, config.get("bean"));
            }
            {
                Assert.assertSame(null, config.get("nope.more"));
                config.set("nope.more", List.of("a", "b", "c"));
                Assert.assertEquals(List.of("a", "b", "c"), config.get("nope.more"));
                Assert.assertEquals("b", config.get("nope.more.1"));
                config.set("nope.more.3", "d");
                Assert.assertEquals("d", config.get("nope.more.3"));
                Assert.assertEquals("b", config.remove("nope.more.1"));
                Assert.assertEquals(List.of("a", "c", "d"), config.remove("nope.more"));
            }


            double configMoney = config.getMoney();
            System.out.println("[SimpleConfigTest] testing basic operations: " + configMoney);

            double oldMoney = config.addMoney(10);
            Assert.assertEquals(oldMoney, configMoney, 0.1);
            Assert.assertEquals(configMoney + 10, config.getMoney(), 0.1);
            System.out.println("[SimpleConfigTest] add test: " + config.getMoney());

            config.multipleMoneyBy(2);
            Assert.assertEquals((configMoney + 10) * 2, config.getMoney(), 0.1);
            System.out.println("[SimpleConfigTest] multiple test: " + config.getMoney());

            config.divideMoney(2);
            Assert.assertEquals(configMoney + 10, config.getMoney(), 0.1);
            System.out.println("[SimpleConfigTest] divide test: " + config.getMoney());

            config.subtractMoney((byte) 10);
            Assert.assertEquals(configMoney, config.getMoney(), 0.1);
            System.out.println("[SimpleConfigTest] subtract test: " + config.getMoney());

            System.out.println("[SimpleConfigTest] testing basic values: ");

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
