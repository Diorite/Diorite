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

package org.diorite;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.diorite.commons.math.DioriteRandomUtils;
import org.diorite.sender.PlayerCommandSender;

/**
 * Represents players online on server, contains some helper methods.
 */
public interface PlayersManager<T extends PlayerCommandSender> extends Iterable<T>
{
    /**
     * Returns this manager as read-only collection of players.
     *
     * @return this manager as read-only collection of players.
     */
    Collection<? extends T> asCollection();

    /**
     * Returns player with exact name as given, or null.
     *
     * @param name
     *         name of player.
     * @param ignoreCase
     *         if method should ignore case of name.
     *
     * @return player with exact name as given, or null.
     */
    @Nullable
    T getPlayer(String name, boolean ignoreCase);

    /**
     * Returns player with name starting with given string and ignoring case of it. <br>
     * If multiple players are found, best match wil be returned.
     *
     * @param name
     *         name of player.
     *
     * @return found player or null.
     */
    @Nullable
    T findPlayer(String name);

    /**
     * Returns player with given uuid.
     *
     * @param uuid
     *         uuid of player to find.
     *
     * @return player with given uuid.
     */
    @Nullable
    T getPlayer(UUID uuid);

    /**
     * Returns true if this manager contains given player.
     *
     * @param player
     *         player to check.
     *
     * @return true if this manager contains given player.
     */
    default boolean contains(T player)
    {
        return this.asCollection().contains(player);
    }

    /**
     * Returns amount of players.
     *
     * @return amount of players.
     */
    default int size()
    {
        return this.asCollection().size();
    }

    /**
     * Returns player with exact name as given, or null.
     *
     * @param name
     *         name of player.
     *
     * @return player with exact name as given, or null.
     */
    @Nullable
    default T getPlayer(String name)
    {
        return this.getPlayer(name, false);
    }

    /**
     * Creates stream of players.
     *
     * @return created stream of players.
     */
    default Stream<? extends T> stream()
    {
        return this.asCollection().stream();
    }

    /**
     * Perform action for each player matching given criteria.
     *
     * @param predicate
     *         criteria to match.
     * @param consumer
     *         action to perform.
     *
     * @return amount of invoked actions. (players)
     */
    default int forEach(Predicate<T> predicate, Consumer<T> consumer)
    {
        int result = 0;
        for (T t : this)
        {
            if (predicate.test(t))
            {
                consumer.accept(t);
                result += 1;
            }
        }
        return result;
    }

    /**
     * Returns collection containing players matching given predicate.
     *
     * @param predicate
     *         predicate to match.
     *
     * @return collection containing players matching given predicate.
     */
    default Collection<? extends T> getPlayers(Predicate<T> predicate)
    {
        Collection<? extends T> asCollection = this.asCollection();
        Set<T> result = new HashSet<>(20);
        for (T t : asCollection)
        {
            if (predicate.test(t))
            {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Returns collection containing players with names starting from given string.
     *
     * @param startsWith
     *         prefix to lookup.
     * @param ignoreCase
     *         if method should ignore case of names.
     *
     * @return collection containing players with names starting from given string.
     */
    default Collection<? extends T> getPlayers(String startsWith, boolean ignoreCase)
    {
        if (ignoreCase)
        {
            return this.getPlayers(p -> p.getName().toLowerCase().startsWith(startsWith.toLowerCase()));
        }
        return this.getPlayers(p -> p.getName().startsWith(startsWith));
    }

    /**
     * Returns collection containing players with names starting from given string.
     *
     * @param startsWith
     *         prefix to lookup.
     *
     * @return collection containing players with names starting from given string.
     */
    default Collection<? extends T> getPlayers(String startsWith)
    {
        return this.getPlayers(startsWith, false);
    }

    /**
     * Returns random player. May return null if server is empty.
     *
     * @return random player.
     */
    @Nullable
    default T random()
    {
        return DioriteRandomUtils.getRandom(this.asCollection());
    }

    /**
     * Returns list contains randomly picked players, note that list size may be smaller than given amount if there is no enough players on server.
     *
     * @param amount
     *         amount of player to pick.
     *
     * @return list containing random players.
     */
    default List<? extends T> random(int amount)
    {
        return this.random(new ArrayList<>(amount), amount);
    }

    /**
     * Adds randomly picked players to given collection, note that list size may be smaller than given amount if there is no enough players on server.
     *
     * @param targetCollection
     *         target collection, players will be added here.
     * @param amount
     *         amount of players to pick.
     * @param <E>
     *         type of collection.
     *
     * @return this same collection as given.
     */
    default <E extends Collection<? super T>> E random(E targetCollection, int amount)
    {
        return DioriteRandomUtils.getRandom(this.asCollection(), targetCollection, amount, true);
    }
}
