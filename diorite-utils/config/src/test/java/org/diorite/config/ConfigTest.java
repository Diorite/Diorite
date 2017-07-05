/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import java.io.File;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Test;

import org.diorite.commons.io.StringBuilderWriter;
import org.diorite.config.SomeConfig.TestEnum;
import org.diorite.config.impl.groovy.GroovyImplementationProvider;
import org.diorite.config.serialization.MetaObject;
import org.diorite.config.serialization.MetaValue;
import org.diorite.config.serialization.SerializationTest;

public class ConfigTest
{
    private final ConfigManager configManager = ConfigManager.get();

    static
    {
        GroovyImplementationProvider.getInstance().setPrintCode(true);
    }

    @Test
    public void testTypes() throws Exception
    {
        SerializationTest.prepareSerialization();
        ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);

        ConfigTemplate<TypeTestConfig> configTemplate = this.configManager.getConfigFile(TypeTestConfig.class);
        Assert.assertNotNull(configTemplate);
        Assert.assertEquals(TypeTestConfig.class.getSimpleName(), configTemplate.getName());
        Assert.assertEquals(StandardCharsets.UTF_8, configTemplate.getDefaultDecoder().charset());
        Assert.assertEquals(StandardCharsets.UTF_8, configTemplate.getDefaultEncoder().charset());

        System.out.println("[ConfigTypeTest] creating config instance.");
        TypeTestConfig cfg = configTemplate.create();
        Assert.assertNotNull(cfg);

        Assert.assertSame(1, cfg.getCopyTest()[0]);
        cfg.getCopyTest()[0] = 2;
        Assert.assertSame(1, cfg.getCopyTest()[0]);

        Assert.assertSame(1, cfg.getNonCopyTest()[0]);
        cfg.getNonCopyTest()[0] = 2;
        Assert.assertSame(2, cfg.getNonCopyTest()[0]);

        cfg.save(System.out);
        // check if all data is still valid after reload of config.
        StringBuilderWriter writer = new StringBuilderWriter(500);
        cfg.save(writer);
        Files.write(new File("target/yaml-test-file.yml").toPath(), writer.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        TypeTestConfig cfgCopy = configTemplate.create();
        cfg.forEach(cfgCopy::set);
        cfgCopy.bindFile(new File("target/yaml-test-file.yml"));
        cfgCopy.save();
        cfgCopy.load();
        cfgCopy.save();
        cfgCopy.load();
        cfgCopy.bindFile(null);
        Assert.assertEquals(cfg, cfgCopy);
    }

    @Test
    public void test() throws Exception
    {
        SerializationTest.prepareSerialization();
        ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);


        ConfigTemplate<SomeConfig> configTemplate = this.configManager.getConfigFile(SomeConfig.class);
        Assert.assertNotNull(configTemplate);
        Assert.assertEquals(SomeConfig.class.getSimpleName(), configTemplate.getName());
        Assert.assertEquals(StandardCharsets.UTF_8, configTemplate.getDefaultDecoder().charset());
        Assert.assertEquals(StandardCharsets.UTF_8, configTemplate.getDefaultEncoder().charset());

        System.out.println("[ConfigTest] creating config instance.");
        SomeConfig someConfig = configTemplate.create();
        Assert.assertNotNull(someConfig);

        Assert.assertNull(someConfig.metadata().get("meta"));
        Assert.assertEquals("1", someConfig.getSomething());
        Assert.assertNull(someConfig.get("something"));
        Assert.assertEquals("meta value", someConfig.metadata().get("meta"));

        Assert.assertNotNull(someConfig.getEnumValue());
        someConfig.setEnumValue(TestEnum.C);
        Assert.assertSame(TestEnum.C, someConfig.getEnumValue());
        this.testNicknames(someConfig);
        Assert.assertNotNull(someConfig.getSpecialData());
        someConfig.setStorage(SerializationTest.prepareObject());

        someConfig.save(System.out);

        try
        {
            someConfig.getSpecialData().clear();
            Assert.assertTrue("This should never happen, special data should be immutable.", false);
        }
        catch (UnsupportedOperationException e)
        {
        }
        MetaObject snowflake = new MetaObject("snowflake", new MetaValue("so special", 25));
        someConfig.putInSpecialData(snowflake);
        Assert.assertEquals(List.of(snowflake), someConfig.getSpecialData());

        UUID randomUUID = UUID.randomUUID();
        someConfig.putInEvenMoreSpecialData(randomUUID, snowflake);
        Assert.assertEquals(1, someConfig.getEvenMoreSpecialData().size());

        System.out.println("\n====================\n");
        someConfig.save(System.out);

        Assert.assertEquals(snowflake, someConfig.removeFromEvenMoreSpecialData(randomUUID));
        Assert.assertTrue(someConfig.getEvenMoreSpecialData().isEmpty());
        someConfig.putInEvenMoreSpecialData(randomUUID, snowflake);
        Assert.assertEquals(1, someConfig.getEvenMoreSpecialData().size());

        Assert.assertTrue(someConfig.removeFromEvenMoreSpecialDataIf((key, value) -> key.equals(randomUUID)));
        Assert.assertTrue(someConfig.getEvenMoreSpecialData().isEmpty());
        someConfig.putInEvenMoreSpecialData(randomUUID, snowflake);
        Assert.assertEquals(1, someConfig.getEvenMoreSpecialData().size());

        Assert.assertTrue(someConfig.removeFromEvenMoreSpecialDataIf((key, value) -> value.equals(snowflake)));
        Assert.assertTrue(someConfig.getEvenMoreSpecialData().isEmpty());
        someConfig.putInEvenMoreSpecialData(randomUUID, snowflake);
        Assert.assertEquals(1, someConfig.getEvenMoreSpecialData().size());

        Assert.assertTrue(someConfig.removeFromEvenMoreSpecialDataIf((entry) -> entry.getValue().equals(snowflake)));
        Assert.assertTrue(someConfig.getEvenMoreSpecialData().isEmpty());
        someConfig.putInEvenMoreSpecialData(randomUUID, snowflake);
        Assert.assertEquals(1, someConfig.getEvenMoreSpecialData().size());

        someConfig.putInEvenMoreSpecialData(randomUUID, snowflake);
        Assert.assertEquals(1, someConfig.getEvenMoreSpecialData().size());


        // check if all data is still valid after reload of config.
        StringBuilderWriter writer = new StringBuilderWriter(500);
        someConfig.save(writer);
        Assert.assertEquals(someConfig, configTemplate.load(new StringReader(writer.toString())));
    }

    private void testNicknames(SomeConfig someConfig)
    {
        Assert.assertEquals(2, someConfig.sizeOfNicknames());
        Assert.assertFalse(someConfig.isNicknamesEmpty());
        Assert.assertEquals("GotoFinal", someConfig.getFromNicknames(0));
        Assert.assertEquals("NorthPL", someConfig.getFromNicknames(1));
        Assert.assertTrue(someConfig.isEqualsToNicknames(List.of("GotoFinal", "NorthPL")));
        Assert.assertFalse(someConfig.isEqualsToNicknames(List.of("GotoFinal", "NorthPL", "huh")));

        someConfig.addToNicknames("skprime");
        someConfig.putInNicknames("Dzikoysk", "joda17");
        Assert.assertEquals("skprime", someConfig.getNicknamesBy(2));
        Assert.assertEquals("Dzikoysk", someConfig.getNicknamesBy(3));
        Assert.assertEquals("joda17", someConfig.getNicknamesBy(4));
        Assert.assertEquals(5, someConfig.nicknamesSize());

        Assert.assertFalse(someConfig.isInNicknames("Someone"));
        Assert.assertTrue(someConfig.isInNicknames("GotoFinal"));
        Assert.assertFalse(someConfig.containsNicknames("GotoFinal", "NorthPL", "Someone"));
        Assert.assertTrue(someConfig.containsInNicknames("GotoFinal", "NorthPL", "skprime"));
        Assert.assertTrue(someConfig.excludesInNicknames("nope"));
        Assert.assertTrue(someConfig.excludesInNicknames("nope", "nope2"));
        Assert.assertFalse(someConfig.excludesInNicknames("nope", "nope2", "NorthPL"));

        Assert.assertTrue(someConfig.removeFromNicknames("Dzikoysk"));
        Assert.assertEquals(4, someConfig.nicknamesSize());
        someConfig.removeFromNicknamesIf(s -> s.contains("17"));
        Assert.assertEquals(3, someConfig.nicknamesSize());
        Assert.assertTrue(someConfig.removeFromNicknames("skprime", "GotoFinal"));
        Assert.assertEquals(1, someConfig.nicknamesSize());
        Assert.assertEquals("NorthPL", someConfig.getFromNicknames(0));
        someConfig.removeFromNicknamesIfNot(s -> s.contains("PL"));
        Assert.assertEquals(1, someConfig.nicknamesSize());
        Assert.assertEquals("NorthPL", someConfig.getFromNicknames(0));
        someConfig.removeFromNicknamesIfNot(s -> s.contains("17"));
        Assert.assertEquals(0, someConfig.nicknamesSize());
        Assert.assertTrue(someConfig.isNicknamesEmpty());
    }
}
