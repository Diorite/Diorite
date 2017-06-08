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

package org.diorite.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents id used by minecraft, like `minecraft:grass` <br>
 * There can be only one instance of GameId with given namespace and id at runtime.
 */
public final class GameId
{
    /**
     * Namespace used by minecraft.
     */
    public static final String MINECRAFT         = "minecraft";
    /**
     * Separator used to separate namespace and id
     */
    public static final char   SEPARATOR_CHAR    = ':';
    /**
     * Separator used to separate namespace and id
     */
    public static final String SEPARATOR         = String.valueOf(SEPARATOR_CHAR);
    /**
     * String used to represent unknown namespace
     */
    public static final String UNKNOWN_NAMESPACE = "";
    private final String namespace;
    private final String id;
    private final String fullName;

    private GameId(String namespace, String id)
    {
        this.namespace = namespace.intern();
        this.id = id.intern();
        if (namespace.isEmpty())
        {
            this.fullName = this.id;
        }
        else
        {
            this.fullName = (namespace + SEPARATOR + id).intern();
        }
    }

    /**
     * Returns namespace of this id. <br>
     * Namespace might be an empty string if it isn't known.
     *
     * @return namespace of this id.
     */
    public String getNamespace()
    {
        return this.namespace;
    }

    /**
     * Returns true if this game id has namespace.
     *
     * @return true if this game id has namespace.
     */
    public boolean hasNamespace()
    {
        return this.namespace != UNKNOWN_NAMESPACE;
    }

    /**
     * Returns string id.
     *
     * @return string id.
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * Returns name game id with different namespace. <br>
     * NOTE: it will return this same object if it is already in requested namespace.
     *
     * @param namespace
     *         namespace name.
     *
     * @return name game id with different namespace.
     */
    public GameId toNamespace(String namespace)
    {
        if (this.namespace.equals(namespace.toLowerCase()))
        {
            return this;
        }
        return of(namespace, this.id);
    }

    /**
     * Returns other game id but using minecraft namespace. <br>
     * NOTE: it will return this same object if it is already in requested namespace.
     *
     * @return other game id but using minecraft namespace.
     */
    public GameId toMinecraftNamespace()
    {
        if (this.namespace == MINECRAFT)
        {
            return this;
        }
        return of(MINECRAFT, this.id);
    }

    /**
     * Returns other game id but using unknown namespace. <br>
     * NOTE: it will return this same object if it is already in requested namespace.
     *
     * @return other game id but using unknown namespace.
     */
    public GameId toUnknownNamespace()
    {
        if (this.namespace == UNKNOWN_NAMESPACE)
        {
            return this;
        }
        return of(UNKNOWN_NAMESPACE, this.id);
    }

    /**
     * Ids are matching if they are equals or one of them is from unknown namespace and both have this same id.
     *
     * @param gameId
     *         other game id to check.
     *
     * @return true if this uuids is matching given one.
     */
    public boolean isMatching(GameId gameId)
    {
        if (this == gameId)
        {
            return true;
        }
        if (this.id != gameId.id)
        {
            return false;
        }
        return (this.namespace == UNKNOWN_NAMESPACE) || (gameId.namespace == UNKNOWN_NAMESPACE);
    }

    private static final Map<String, GameId> ids = new HashMap<>(200);

    /**
     * Returns id object of minecraft namespace.
     *
     * @param id
     *         id.
     *
     * @return id object of minecraft namespace.
     */
    public static GameId ofMinecraft(String id)
    {
        return of(MINECRAFT, id);
    }

    /**
     * Returns game id of given namespace and id. <br>
     * Both namespace and id will be changed to lower case.
     *
     * @param namespace
     *         namespace of id.
     * @param id
     *         id.
     *
     * @return game id of given namespace and id.
     */
    public static GameId of(String namespace, String id)
    {
        String fullName;
        if (namespace.isEmpty())
        {
            fullName = id;
        }
        else
        {
            fullName = namespace + SEPARATOR + id;
        }
        GameId gameId = ids.get(fullName);
        if (gameId != null)
        {
            return gameId;
        }
        namespace = namespace.toLowerCase();
        id = id.toLowerCase();
        fullName = namespace + SEPARATOR + id;
        gameId = ids.get(fullName);
        if (gameId != null)
        {
            return gameId;
        }
        if (namespace.indexOf(SEPARATOR_CHAR) != - 1)
        {
            throw new IllegalStateException("Namespace (" + namespace + ") can not contains separator char `" + SEPARATOR_CHAR + "`");
        }
        if (id.indexOf(SEPARATOR_CHAR) != - 1)
        {
            throw new IllegalStateException("Id (" + id + ") can not contains separator char `" + SEPARATOR_CHAR + "`");
        }
        if (id.isEmpty())
        {
            throw new IllegalStateException("Id can not be empty!");
        }
        synchronized (ids)
        {
            gameId = ids.get(fullName);
            if (gameId != null)
            {
                return gameId;
            }
            gameId = new GameId(namespace, id);
            ids.put(fullName, gameId);
        }
        return gameId;
    }

    /**
     * Returns game id of given string representation of game id.
     *
     * @param fullName
     *         full representation of game id.
     *
     * @return game id of given string representation of game id.
     */
    public static GameId of(String fullName)
    {
        int indexOf = fullName.indexOf(SEPARATOR_CHAR);
        if (indexOf == - 1)
        {
            return of(UNKNOWN_NAMESPACE, fullName);
        }
        return of(fullName.substring(0, indexOf), fullName.substring(indexOf + 1));
    }

    @Override
    public String toString()
    {
        return this.fullName;
    }
}
