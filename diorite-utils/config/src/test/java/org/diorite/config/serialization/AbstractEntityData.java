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

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.config.annotations.Comment;
import org.diorite.config.annotations.PredefinedComment;
import org.diorite.config.annotations.SerializableAs;
import org.diorite.config.serialization.comments.CommentsNode;

@PredefinedComment(path = "age", value = "age of entity")
@PredefinedComment(path = {"metaObjects", CommentsNode.ANY},
                   value = "asterisk means that any key may appear here, you can use (*) too to still use valid yaml syntax.")
@PredefinedComment(path = {"uuidMetaObjectMap", "value"}, value = "value!")
@SerializableAs(EntityData.class)
public abstract class AbstractEntityData implements EntityData
{
    EntityType type;
    @Comment(name = "name", value = {"Name of entity",
                                     "   Multiline test",
                                     "test test"})
    String namee; // test for custom name
    int age;
    @Comment("special enum!")
    DynamicEnumType specialEnum;
    @Comment("is it special?")
    boolean         special;
    @Comment("meta objects!")
    Collection<MetaObject>     metaObjects          = new ArrayList<>();
    @Comment("second list example")
    Map<UUID, MetaObject>      uuidMetaObjectMap    = new HashMap<>();
    @Comment("ugh, uh")
    Collection<SomeProperties> propertiesCollection = new ArrayList<>();
    @Nullable
    String displayName;

    protected AbstractEntityData(EntityType type, String namee, int age, boolean special, @Nullable String displayName)
    {
        this.type = type;
        this.namee = namee;
        this.age = age;
        this.special = special;
        this.displayName = displayName;
        this.specialEnum = DynamicEnumType.SOME_ENUM_CONSTANT;
    }

    protected AbstractEntityData(DeserializationData data)
    {
        this.type = data.getOrThrow("type", EntityType.class);
        this.namee = data.getOrThrow("name", String.class);
        this.age = data.getAsInt("age");
        this.special = data.getOrThrow("special", Boolean.class);
        this.displayName = data.get("displayName", String.class);
        this.specialEnum = data.getOrThrow("specialEnum", DynamicEnumType.class);

        data.getAsCollection("metaObjects", MetaObject.class, this.metaObjects);
        data.getAsMapWithKeys("uuidMetaObjectMap", UUID.class, MetaObject.class, "uuid", this.uuidMetaObjectMap);
        data.getAsCollection("propertiesCollection", SomeProperties.class, this.propertiesCollection);
    }

    public static AbstractEntityData deserialize(DeserializationData data)
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
    }

    @OverridingMethodsMustInvokeSuper
    @Override
    public void serialize(SerializationData data)
    {
        data.add("type", this.type);
        data.add("name", this.namee);
        data.addNumber("age", this.age, 3);
        data.add("special", this.special);
        data.add("displayName", this.displayName);
        data.add("specialEnum", this.specialEnum);
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
               Objects.equals(this.namee, that.namee) &&
               Objects.equals(this.metaObjects, that.metaObjects) &&
               Objects.equals(this.uuidMetaObjectMap, that.uuidMetaObjectMap) &&
               Objects.equals(this.propertiesCollection, that.propertiesCollection);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.type, this.namee, this.age, this.special, this.metaObjects, this.uuidMetaObjectMap, this.propertiesCollection);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("type", this.type).append("name", this.namee)
                                        .append("age", this.age).append("special", this.special)
                                        .append("metaObjects", this.metaObjects)
                                        .append("uuidMetaObjectMap", this.uuidMetaObjectMap)
                                        .append("propertiesCollection", this.propertiesCollection).toString();
    }
}
