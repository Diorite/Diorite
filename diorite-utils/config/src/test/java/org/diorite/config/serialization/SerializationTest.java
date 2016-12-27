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

import java.util.Map;
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

    private Serialization prepareSerialization()
    {
        Serialization global = Serialization.getGlobal();
        global.registerSerializable(SomeProperties.class);
        global.registerSerializable(MetaObject.class);
        global.registerStringSerializable(MetaValue.class);
        global.registerSerializable(EntityStorage.class);
        global.registerSerializable(AbstractEntityData.class);
//        global.registerSerializer(Serializer.of(EntityData.class, Serializable::serialize, data ->
//        {
//            EntityType type = data.getOrThrow("type", EntityType.class);
//            switch (type)
//            {
//                case CREEPER:
//                    return new CreeperEntityData(data);
//                case SHEEP:
//                    return new SheepEntityData(data);
//                default:
//                    throw new AssertionError();
//            }
//        }));

        return global;
    }

    private EntityStorage prepareObject()
    {
        EntityStorage entityStorage = new EntityStorage();
        {
            BeanObject beanObject = new BeanObject();
            beanObject.setIntProperty(53);
            beanObject.setStringProperty("some str\nnew line\n  more lines\n    lel\nend.");
            beanObject.setIntMap(
                    Map.of(new int[]{1, 2, 3}, new int[]{11, 12, 13}, new int[]{4, 5, 6}, new int[]{14, 15, 16}, new int[]{7, 8, 9}, new int[]{17, 18, 19}));
            entityStorage.beanObject = beanObject;
        }
        {
            AbstractEntityData entity = new CreeperEntityData("creeperName", 12, false, true);
            entity.metaObjects.add(new MetaObject("someMeta", new MetaValue("yup", 21)));
            entity.metaObjects.add(new MetaObject("someMeta2", new MetaValue("yup", 54)));
            SomeProperties someProperties = new SomeProperties();
            someProperties.prop.put("propertyName", "propertyValue");
            entity.propertiesCollection.add(someProperties);
            entity.uuidMetaObjectMap.put(UUID.randomUUID(), new MetaObject("specialMeta", new MetaValue("val", 12)));
            entityStorage.entityData.add(entity);
        }
        {
            AbstractEntityData entity = new SheepEntityData("creeperName", 12, false, 3);
            entity.metaObjects.add(new MetaObject("someMeta", new MetaValue("yup", 21)));
            entity.metaObjects.add(new MetaObject("someMeta2", new MetaValue("yup", 54)));
            SomeProperties someProperties = new SomeProperties();
            entity.propertiesCollection.add(someProperties);
            entity.uuidMetaObjectMap.put(UUID.randomUUID(), new MetaObject("specialMeta", new MetaValue("val", 12)));
            entityStorage.entityData.add(entity);
        }
        {
            AbstractEntityData entity = new CreeperEntityData("creeperName", 12, false, true);
            entityStorage.entityData.add(entity);
        }
        return entityStorage;
    }

    @Test
    public void jsonTest()
    {
        Serialization global = this.prepareSerialization();

        EntityStorage entityStorage = this.prepareObject();

        System.out.println("[JSON] Deserializing object: \n" + entityStorage + "\n");
        String toJson = global.toJson(entityStorage);
        System.out.println(toJson);
        System.out.println();

        EntityStorage storage = global.fromJson(toJson, EntityStorage.class);
        System.out.println("[JSON] Deserialized object: \n" + storage);
        Assert.assertEquals(entityStorage.toString(), storage.toString());
        Assert.assertEquals(entityStorage, storage);

        System.out.println("\n[JSON] Objects equals!");
    }

    @Test
    public void yamlTest()
    {
        Serialization global = this.prepareSerialization();

        EntityStorage entityStorage = this.prepareObject();

        System.out.println("[YAML] Deserializing object: \n" + entityStorage + "\n");
        String toJson = global.toYaml(entityStorage);
        System.out.println(toJson);
        System.out.println();

        EntityStorage storage = global.fromYaml(toJson, EntityStorage.class);
        System.out.println("[YAML] Deserialized object: \n" + storage);
        Assert.assertEquals(entityStorage.toString(), storage.toString());
        Assert.assertEquals(entityStorage, storage);

        System.out.println("\n[YAML] Objects equals!");
    }
}
