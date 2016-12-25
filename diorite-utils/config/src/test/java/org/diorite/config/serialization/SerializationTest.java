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

import java.util.UUID;

import com.google.gson.Gson;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Test;

public class SerializationTest
{
    @Test
    public void basicTest()
    {
        ToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);

        Serialization global = Serialization.getGlobal();
        global.registerSerializable(SomeProperties.class);
        global.registerSerializable(MetaObject.class);
        global.registerStringSerializable(MetaValue.class);
        global.registerSerializable(EntityStorage.class);
        global.registerSerializer(Serializer.of(EntityData.class, Serializable::serialize, data ->
        {
            EntityType type = data.getOrThrow("type", EntityType.class);
            switch (type)
            {
                case CREEPER:
                    return new CreeperEntityData(data);
                case SHEEP:
                    return new SheepEntityData(data);
                default:
                    throw new AssertionError();
            }
        }));
        Gson gson = global.gsonBuilder.create();

        EntityStorage entityStorage = new EntityStorage();
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
        System.out.println("Deserializing object: \n" + entityStorage + "\n");
        String toJson = gson.toJson(entityStorage);
        System.out.println(toJson);
        System.out.println();
        EntityStorage storage = gson.fromJson(toJson, EntityStorage.class);
        System.out.println("Deserialized object: \n" + storage);
        Assert.assertEquals(entityStorage.toString(), storage.toString());
        Assert.assertEquals(entityStorage, storage);
        System.out.println("\nObjects equals!");
    }
}
