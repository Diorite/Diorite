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
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.diorite.commons.io.StringBuilderWriter;
import org.diorite.config.exceptions.ValidationException;
import org.diorite.config.impl.groovy.GroovyImplementationProvider;
import org.diorite.config.serialization.BeanObject;

public class SimpleConfigTest
{
    private final ConfigManager configManager = ConfigManager.get();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void validatorsTest() throws Exception
    {
        GroovyImplementationProvider.getInstance().setPrintCode(true);
        ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);

        ConfigTemplate<TestConfig> configTemplate = this.configManager.getConfigFile(TestConfig.class);
        try (InputStream stream = SimpleConfigTest.class.getResourceAsStream("/simpleConfig.yml"))
        {
            Assert.assertNotNull(stream);

            TestConfig config = configTemplate.load(stream);
            this.testException("config.setMoney(3_000)", () -> config.setMoney(3_000), false);
            this.testException("config.setMoney(110_000)", () -> config.setMoney(110_000), true);
            this.testException("config.setMoney(5_000)", () -> config.setMoney(5_000), true);
            this.testException("config.setMoney(6_000)", () -> config.setMoney(6_000), false);
            Assert.assertEquals(7_000, config.getMoney(), 0.001);
            this.testException("config.setMoney(13_000)", () -> config.setMoney(13_000), true);
            this.testException("config.setMoney(14_000)", () -> config.setMoney(14_000), true);
            this.testException("config.setMoney(15_000)", () -> config.setMoney(15_000), true);
            this.testException("config.setMoney(16_000)", () -> config.setMoney(16_000), true);
            this.testException("config.setMoney(17_000)", () -> config.setMoney(17_000), false);
            this.testException("config.powMoneyBy(2)", () -> config.powMoneyBy(2), true);
        }
    }

    private void testException(String name, Runnable runnable, boolean shouldThrow)
    {
        try
        {
            runnable.run();
        }
        catch (ValidationException ignored)
        {
            System.out.println("[testException] `" + name + "` thrown exception: " + ignored.getMessage());
            Assert.assertTrue("Unexpected ValidationException", shouldThrow);
            return;
        }
        System.out.println("[testException] `" + name + "` didn't thrown exception.");
        Assert.assertFalse("Expected ValidationException", shouldThrow);
    }

    @Test
    public void loadTest() throws Exception
    {
        GroovyImplementationProvider.getInstance().setPrintCode(true);
        ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);

        ConfigTemplate<TestConfig> configTemplate = this.configManager.getConfigFile(TestConfig.class);
        Assert.assertNotNull(configTemplate);
        Assert.assertEquals(TestConfig.class.getSimpleName(), configTemplate.getName());
        Assert.assertEquals(StandardCharsets.UTF_8, configTemplate.getDefaultDecoder().charset());
        Assert.assertEquals(StandardCharsets.UTF_8, configTemplate.getDefaultEncoder().charset());

        System.out.println("[SimpleConfigTest] loading simpleConfig.yml");
        try (InputStream stream = SimpleConfigTest.class.getResourceAsStream("/simpleConfig.yml"))
        {
            Assert.assertNotNull(stream);

            TestConfig config = configTemplate.load(stream);
            Assert.assertNotNull(config);
            Assert.assertNotNull(config.template());
            Assert.assertSame(config.template(), configTemplate);
            Assert.assertNotNull(config.name());
            Assert.assertEquals("TestConfig", config.name());
            System.out.println("[SimpleConfigTest] loaded simpleConfig.yml (" + config.name() + ")");

            Assert.assertEquals("def", config.get("invalid", "def", String.class));

            System.out.println("[SimpleConfigTest] testing custom values: ");
            {
                Assert.assertSame(null, config.get("nope"));
                config.set("nope", 54);
                Assert.assertEquals(54, config.<Object>get("nope"));
                Assert.assertEquals(54, config.remove("nope"));
            }
            {
                BeanObject beanObject = new BeanObject();
                beanObject.setIntMap(new LinkedHashMap<>(5));
                beanObject.setList(new ArrayList<>(List.of("a", "b", "c")));
                beanObject.setIntProperty(12);
                Assert.assertSame(null, config.get("nested.bean"));
                config.set("nested.bean", beanObject);

                Assert.assertEquals(beanObject, config.get("nested.bean"));
                Assert.assertEquals(12, config.<Object>get("nested.bean.intProperty"));
                Assert.assertSame(null, config.get("nested.bean.stringProperty"));
                Assert.assertSame(null, config.get("nested.bean.list2"));

                config.set("nested.bean.stringProperty", "test");
                Assert.assertEquals("test", config.get("nested.bean.stringProperty"));
                config.set("nested.bean.stringProperty", null);
                Assert.assertSame(null, config.get("nested.bean.stringProperty"));

                config.set("nested.bean.list.0", "A");
                Assert.assertEquals("b", config.get("nested.bean.list.1"));
                Assert.assertEquals(List.of("A", "b", "c"), config.get("nested.bean.list"));
            }
            {
                Assert.assertSame(null, config.get("nope.more"));
                config.set("nope.more", new ArrayList<>(List.of("a", "b", "c")));
                Assert.assertEquals(List.of("a", "b", "c"), config.get("nope.more"));
                Assert.assertEquals("b", config.get("nope.more.1"));
                config.set("nope.more.3", "d");
                Assert.assertEquals("d", config.get("nope.more.3"));
                Assert.assertEquals("b", config.remove("nope.more.1"));
//                Assert.assertEquals(List.of("a", "c", "d"), config.remove("nope.more"));
//                config.remove("nope");
            }
            config.set("SomeString", "Some str");
            config.set("more.A", "Some str1");
            config.set("more.B", "Some str2");
            System.out.println("[SimpleConfigTest] to string: ");
            System.out.println(config.toString());
            System.out.println("[SimpleConfigTest] hashcode: " + config.hashCode());
            Assert.assertEquals(config.hashCode(), config.hashCode());
            System.out.println("[SimpleConfigTest] clone test...");
            config.save(System.out);
            Config clone = config.clone();
            Assert.assertEquals(config, clone);
            clone.set("more.C", "Some str3");
            Assert.assertNotEquals(config, clone);


            double configMoney = config.getMoney();
            System.out.println("\n[SimpleConfigTest] testing basic operations: " + configMoney);

            double oldMoney = config.addMoney(10);
            Assert.assertEquals(oldMoney, configMoney, 0.001);
            Assert.assertEquals(configMoney + 10, config.getMoney(), 0.001);
            System.out.println("[SimpleConfigTest] add test: " + config.getMoney());

            config.multipleMoneyBy(2);
            Assert.assertEquals((configMoney + 10) * 2, config.getMoney(), 0.001);
            System.out.println("[SimpleConfigTest] multiple test: " + config.getMoney());

            config.divideMoney(2);
            Assert.assertEquals(configMoney + 10, config.getMoney(), 0.001);
            System.out.println("[SimpleConfigTest] divide test: " + config.getMoney());

            config.subtractMoney((byte) 10);
            Assert.assertEquals(configMoney, config.getMoney(), 0.001);
            System.out.println("[SimpleConfigTest] subtract test: " + config.getMoney());

            config.powMoneyBy(2);
            Assert.assertEquals(configMoney * configMoney, config.getMoney(), 0.001);
            System.out.println("[SimpleConfigTest] power test: " + config.getMoney());

            config.powMoneyBy(1d / 2d);
            Assert.assertEquals(configMoney, config.getMoney(), 0.001);
            System.out.println("[SimpleConfigTest] square test: " + config.getMoney());

            System.out.println("[SimpleConfigTest] testing basic values: ");

            Assert.assertEquals(configMoney * 10, config.getMoreMoney(10), 0.001);
            Assert.assertEquals(10, config.getMoney(), 0.001);
            config.setMoney(20);
            Assert.assertEquals(20, config.get("player-money", 0.0, double.class), 0.001);
            config.set("player-money", 5);
            Assert.assertEquals(5, config.getMoney(), 0.001);

            System.out.print("[SimpleConfigTest] testing remove, default value of money: ");
            config.remove("player-money");
            System.out.println(config.getMoney());
            Assert.assertEquals(0.1, config.getMoney(), 0.001);
            config.addMoney(0.1);
            Assert.assertEquals(0.2, config.getMoney(), 0.001);

            config.setMoney(20);
            Assert.assertEquals(20, config.getMoney(), 0.001);
            config.setMoney(0);
            Assert.assertEquals(0.0, (Double) config.get("player-money"), 0.001);
            System.out.println("[SimpleConfigTest] done\n");

            StringBuilderWriter writer = new StringBuilderWriter(200);
            config.save(writer);
            Assert.assertEquals(config, configTemplate.load(new StringReader(writer.toString())));

            this.exception.expect(ValidationException.class);
            config.set("player-money", - 1);
        }
    }
}
