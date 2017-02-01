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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import org.diorite.config.annotations.AsList;
import org.diorite.config.annotations.Comment;
import org.diorite.config.annotations.HelperMethod;
import org.diorite.config.annotations.Mapped;
import org.diorite.config.annotations.ToStringMapperFunction;
import org.diorite.config.annotations.Unmodifiable;
import org.diorite.config.serialization.EntityStorage;
import org.diorite.config.serialization.MetaObject;

public interface SomeConfig extends Config
{
    @Comment("Nicknames of some weird people.")
    @Property
    private List<? extends String> nicknames() // test private properties
    {
        return new ArrayList<>(Arrays.asList("GotoFinal", "NorthPL"));
    }

    int nicknamesSize();
    int sizeOfNicknames();

    @HelperMethod
    default String getSomething()
    {
        System.out.println("just helper");
        return "1";
    }

    String getFromNicknames(int index);
    String getNicknamesBy(int index);

    void addToNicknames(String name);
    void putInNicknames(String... names);

    boolean isInNicknames(String name);
    boolean containsNicknames(String... names);
    boolean containsInNicknames(String... names);

    boolean removeFromNicknames(String name);
    boolean removeFromNicknames(String... names);
    boolean removeFromNicknamesIf(Predicate<String> predicate);

    EntityStorage getStorage();
    void setStorage(EntityStorage storage);

    default String someCustomMethod()
    {
        return this.getStorage().toString();
    }

    @Unmodifiable
    @Comment("Just some special comment!")
    @Mapped
    Collection<? extends MetaObject> getSpecialData();
    void putInSpecialData(MetaObject metaObject);
    boolean removeFromSpecialData(MetaObject metaObject);

    @Unmodifiable
    @Comment("Just some even more special comment!!")
    @AsList(keyProperty = "uuid")
    Map<UUID, ? extends MetaObject> getEvenMoreSpecialData();
    void putInEvenMoreSpecialData(UUID uuid, MetaObject metaObject);
    MetaObject removeFromEvenMoreSpecialData(UUID uuid);

    @ToStringMapperFunction(property = "specialData")
    private String metaToKey(MetaObject metaObject)
    {
        return metaObject.getName();
    }

    default TestEnum getEnumValue() {return TestEnum.A;}

    void setEnumValue(TestEnum testEnum);

    enum TestEnum
    {
        A,
        B,
        C
    }
}
