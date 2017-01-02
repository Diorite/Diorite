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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Test;

public class SerializationTest
{
    static
    {
        ToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);
    }

    static Serialization prepareSerialization()
    {
        Serialization global = Serialization.getGlobal();
        global.registerSerializable(SomeProperties.class);
        global.registerSerializable(MetaObject.class);
        global.registerStringSerializable(MetaValue.class);
        global.registerSerializable(EntityStorage.class);
        global.registerSerializable(AbstractEntityData.class);
        return global;
    }

    static EntityStorage prepareObject()
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
