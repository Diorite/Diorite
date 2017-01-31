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

package org.diorite.config.serialization;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Test;

import org.diorite.config.ConfigManager;
import org.diorite.config.ConfigTemplate;
import org.diorite.config.SomeConfig;

public class SerializationTest
{
    static
    {
        ToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);
    }

    public static Serialization prepareSerialization()
    {
        Serialization global = Serialization.getInstance();
        global.registerSerializable(SomeProperties.class);
        global.registerSerializable(MetaObject.class);
        global.registerStringSerializable(MetaValue.class);
        global.registerSerializable(EntityStorage.class);
        global.registerSerializable(AbstractEntityData.class);
        return global;
    }

    public static EntityStorage prepareObject()
    {
        EntityStorage entityStorage = new EntityStorage();
        {
            BeanObject beanObject = new BeanObject();
            beanObject.setIntProperty(53);
            beanObject.setStringProperty("some str\nnew line");
            LinkedHashMap<int[], int[]> map = new LinkedHashMap<>(3);
            map.put(new int[]{1, 2, 3}, new int[]{11, 12, 13});
            map.put(new int[]{4, 5, 6}, new int[]{14, 15, 16});
            map.put(new int[]{7, 8, 9}, new int[]{17, 18, 19});
            beanObject.setIntMap(map);
            beanObject.setList(List.of("some string", "more element", "and more", "dfihefk  fdspbwptn  ifd", "  dju jms jhkror ifjw  ", "du fjfne"));
            beanObject.setList2(List.of("some string", "more element", "and more", "some string", "more element", "and more", "dfihefk  fdspbwptn  ifd",
                                        "  dju jms jhkror ifjw  ", "du fjfne", "dfihefk  fdspbwptn  ifd", "  dju jms jhkror ifjw  ", "du fjfne"));
            entityStorage.beanObject = beanObject;
        }
        {
            AbstractEntityData entity = new CreeperEntityData("creeperName", 12, false, true, null);
            entity.metaObjects.add(new MetaObject("someMeta", new MetaValue("yup", 21)));
            entity.metaObjects.add(new MetaObject("someMeta2", new MetaValue("yup", 54)));
            SomeProperties someProperties = new SomeProperties();
            someProperties.prop.put("propertyName", "propertyValue");
            entity.propertiesCollection.add(someProperties);
            entity.uuidMetaObjectMap.put(UUID.randomUUID(), new MetaObject("specialMeta", new MetaValue("val", 12)));
            entityStorage.entityData.add(entity);
        }
        {
            AbstractEntityData entity = new SheepEntityData("sheepName", 12, false, 3, null);
            entity.metaObjects.add(new MetaObject("someMeta", new MetaValue("yup", 21)));
            entity.metaObjects.add(new MetaObject("someMeta2", new MetaValue("yup", 54)));
            SomeProperties someProperties = new SomeProperties();
            entity.propertiesCollection.add(someProperties);
            entity.uuidMetaObjectMap.put(UUID.randomUUID(), new MetaObject("specialMeta", new MetaValue("val", 12)));
            entityStorage.entityData.add(entity);
        }
        {
            AbstractEntityData entity = new CreeperEntityData("creeperName", 12, false, true, null);
            entityStorage.entityData.add(entity);
        }
        return entityStorage;
    }

    @Test
    public void jsonTest()
    {
        Serialization global = prepareSerialization();

        EntityStorage entityStorage = prepareObject();

        System.out.println("[JSON] Deserializing javabean: \n");
        {
            String toJson = global.toJson(entityStorage.beanObject);
            System.out.println(toJson);
            Assert.assertEquals(entityStorage.beanObject, global.fromJson(toJson, BeanObject.class));
        }

        System.out.println("\n[JSON] Deserializing object: \n" + entityStorage + "\n");
        String toJson = global.toJson(entityStorage);
        System.out.println(toJson);
        System.out.println();

        EntityStorage storage = global.fromJson(toJson, EntityStorage.class);
        System.out.println("\n[JSON] Deserialized object: \n" + storage);
        Assert.assertEquals(entityStorage.toString(), storage.toString());
        Assert.assertEquals(entityStorage, storage);

        System.out.println("\n[JSON] Objects equals!");
    }

    private final ConfigManager configManager = ConfigManager.get();

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
//
        this.testNicknames(someConfig);
        Assert.assertNotNull(someConfig.getSpecialData());
        someConfig.setStorage(SerializationTest.prepareObject());
//
        someConfig.save(System.out);
//
//        try
//        {
//            someConfig.getSpecialData().clear();
//            Assert.assertTrue("This should never happen, special data should be immutable.", false);
//        }
//        catch (UnsupportedOperationException e)
//        {
//        }
//        MetaObject snowflake = new MetaObject("snowflake", new MetaValue("so special", 25));
//        someConfig.putInSpecialData(snowflake);
//        Assert.assertEquals(List.of(snowflake), someConfig.getSpecialData());
//
//        UUID randomUUID = UUID.randomUUID();
//        someConfig.putInEvenMoreSpecialData(randomUUID, snowflake);
//        Assert.assertEquals(1, someConfig.getEvenMoreSpecialData().size());
//
//        System.out.println("\n====================\n");
//        someConfig.save(System.out);
//
//        Assert.assertEquals(snowflake, someConfig.removeFromEvenMoreSpecialData(randomUUID));
//        Assert.assertTrue(someConfig.getEvenMoreSpecialData().isEmpty());
//
//
//        // check if all data is still valid after reload of config.
//        StringBuilderWriter writer = new StringBuilderWriter(500);
//        someConfig.save(writer);
//        Assert.assertEquals(someConfig, configTemplate.load(new StringReader(writer.toString())));
    }

    private void testNicknames(SomeConfig someConfig)
    {
        Assert.assertEquals(2, someConfig.sizeOfNicknames());
        Assert.assertEquals("GotoFinal", someConfig.getFromNicknames(0));
        Assert.assertEquals("NorthPL", someConfig.getFromNicknames(1));

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

        Assert.assertTrue(someConfig.removeFromNicknames("Dzikoysk"));
        Assert.assertEquals(4, someConfig.nicknamesSize());
        someConfig.removeFromNicknamesIf(s -> s.contains("17"));
        Assert.assertEquals(3, someConfig.nicknamesSize());
        Assert.assertTrue(someConfig.removeFromNicknames("skprime", "GotoFinal"));
        Assert.assertEquals(1, someConfig.nicknamesSize());
        Assert.assertEquals("NorthPL", someConfig.getFromNicknames(0));
    }

    @Test
    public void yamlTest()
    {
        Serialization global = prepareSerialization();

        EntityStorage entityStorage = prepareObject();

        System.out.println("[YAML] Deserializing javabean: \n");
        {
            String toYaml = global.toYaml(entityStorage.beanObject);
            System.out.println(toYaml);
            Assert.assertEquals(entityStorage.beanObject, global.fromYaml(toYaml, BeanObject.class));
        }

        System.out.println("\n[YAML] Deserializing object: \n" + entityStorage + "\n");
        String toYaml = global.toYaml(entityStorage);
        System.out.println(toYaml);
        System.out.println();

        EntityStorage storage = global.fromYaml(toYaml, EntityStorage.class);
        System.out.println("\n[YAML] Deserialized object: \n" + storage);
        Assert.assertEquals(entityStorage.toString(), storage.toString());
        Assert.assertEquals(entityStorage, storage);

        System.out.println("\n[YAML] Objects equals!");
    }
}
