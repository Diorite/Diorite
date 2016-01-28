package org.diorite.chat.placeholder;

/**
 * Represent single placeholder item, like "name" in player.name placeholder.
 *
 * @param <T> type of object needed to get data for placeholder.
 */
public interface PlaceholderItem<T>
{
    /**
     * Returns type of placeholder item, like that "player" in player.name.
     *
     * @return type of placeholder.
     */
    PlaceholderType<T> getType();

    /**
     * Returns id/name of placeholder item, like that "name" in player.name.
     *
     * @return id of placeholder item.
     */
    String getId();

    /**
     * Get data for this placeholder item as String or BaseComponent, like player.name should return here name of given player. <br>
     * If method return other object than String or BaseComponent it will be changed to string by {@link Object#toString()} method.
     *
     * @param obj  object to fetch the data needed for placeholder.
     * @param args arguments of item
     *
     * @return String of BaseComponent to use instead of placeholder.
     */
    Object apply(T obj, Object[] args);
}
