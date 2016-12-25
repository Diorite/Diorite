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

import javax.annotation.OverridingMethodsMustInvokeSuper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class AbstractEntityData implements EntityData
{
    EntityType type;
    String     name;
    int        age;
    boolean    special;
    Collection<MetaObject>     metaObjects          = new ArrayList<>();
    Map<UUID, MetaObject>      uuidMetaObjectMap    = new HashMap<>();
    Collection<SomeProperties> propertiesCollection = new ArrayList<>();

    protected AbstractEntityData(EntityType type, String name, int age, boolean special)
    {
        this.type = type;
        this.name = name;
        this.age = age;
        this.special = special;
    }

    protected AbstractEntityData(DeserializationData data)
    {
        this.type = data.getOrThrow("type", EntityType.class);
        this.name = data.getOrThrow("name", String.class);
        this.age = data.getAsInt("age");
        this.special = data.getOrThrow("special", Boolean.class);

        data.getAsCollection("metaObjects", MetaObject.class, this.metaObjects);
        data.getAsMapWithKeys("uuidMetaObjectMap", UUID.class, MetaObject.class, "uuid", this.uuidMetaObjectMap);
        data.getAsCollection("propertiesCollection", SomeProperties.class, this.propertiesCollection);
    }

    @OverridingMethodsMustInvokeSuper
    @Override
    public void serialize(SerializationData data)
    {
        data.add("type", this.type);
        data.add("name", this.name);
        data.addNumber("age", this.age, 3);
        data.add("special", this.special);
        data.addMappedList("metaObjects", MetaObject.class, this.metaObjects, o -> o.name);
        data.addMapAsListWithKeys("uuidMetaObjectMap", this.uuidMetaObjectMap, MetaObject.class, "uuid");
        data.addCollection("propertiesCollection", this.propertiesCollection, SomeProperties.class);
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (! (object instanceof AbstractEntityData))
        {
            return false;
        }
        AbstractEntityData that = (AbstractEntityData) object;
        return (this.age == that.age) &&
               (this.special == that.special) &&
               (this.type == that.type) &&
               Objects.equals(this.name, that.name) &&
               Objects.equals(this.metaObjects, that.metaObjects) &&
               Objects.equals(this.uuidMetaObjectMap, that.uuidMetaObjectMap) &&
               Objects.equals(this.propertiesCollection, that.propertiesCollection);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.type, this.name, this.age, this.special, this.metaObjects, this.uuidMetaObjectMap, this.propertiesCollection);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("type", this.type).append("name", this.name)
                                        .append("age", this.age).append("special", this.special)
                                        .append("metaObjects", this.metaObjects)
                                        .append("uuidMetaObjectMap", this.uuidMetaObjectMap)
                                        .append("propertiesCollection", this.propertiesCollection).toString();
    }
}
