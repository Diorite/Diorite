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

package org.diorite.chat;

import org.diorite.nbt.NbtTag;

/**
 * Represent some message event.
 */
public interface ChatMessageEvent
{
    /**
     * Returns copy of this event.
     *
     * @return copy of this event.
     */
    ChatMessageEvent duplicate();

    /**
     * Returns type of this event.
     *
     * @return type of this event.
     */
    Action getAction();

    /**
     * Returns raw value of event, it might be a number, string, item or other object.
     *
     * @return raw value of event, it might be a number, string, item or other object.
     */
    Object getRawValue();

    /**
     * Create new event of {@link Action#APPEND_CHAT} type.
     *
     * @param value
     *         text to append into chat on shift click.
     *
     * @return created event instance.
     */
    static ChatEventAppendChat appendChat(String value)
    {
        return new ChatEventAppendChatImpl(value);
    }
    /**
     * Create new event of {@link Action#OPEN_FILE} type.
     *
     * @param value
     *         file to open.
     *
     * @return created event instance.
     */
    static ChatEventOpenFile openFile(String value)
    {
        return new ChatEventOpenFileImpl(value);
    }
    /**
     * Create new event of {@link Action#OPEN_URL} type.
     *
     * @param value
     *         url to open.
     *
     * @return created event instance.
     */
    static ChatEventOpenURL openURL(String value)
    {
        return new ChatEventOpenURLImpl(value);
    }
    /**
     * Create new event of {@link Action#SHOW_ACHIEVEMENT} type.
     *
     * @param value
     *         full name of achievement or statistic
     *
     * @return created event instance.
     */
    static ChatEventShowAchievement showAchievement(String value)
    {
        return new ChatEventShowAchievementImpl(value);
    }
    // TODO: add method to create ChatEventShowAchievement form achievement or statistic object.
    /**
     * Create new event of {@link Action#RUN_COMMAND} type.
     *
     * @param value
     *         command to run.
     *
     * @return created event instance.
     */
    static ChatEventRunCommand runCommand(String value)
    {
        return new ChatEventRunCommandImpl(value);
    }
    /**
     * Create new event of {@link Action#SUGGEST_COMMAND} type.
     *
     * @param value
     *         command to suggest.
     *
     * @return created event instance.
     */
    static ChatEventSuggestCommand suggestCommand(String value)
    {
        return new ChatEventSuggestCommandImpl(value);
    }
    /**
     * Create new event of {@link Action#SHOW_TEXT} type.
     *
     * @param value
     *         text to show on hover.
     *
     * @return created event instance.
     */
    static ChatEventShowText showText(ChatMessage value)
    {
        return new ChatEventShowTextImpl(value);
    }
    /**
     * Create new event of {@link Action#CHANGE_PAGE} type.
     *
     * @param value
     *         number of page.
     *
     * @return created event instance.
     */
    static ChatEventChangePage changePage(int value)
    {
        return new ChatEventChangePageImpl(value);
    }
    /**
     * Create new event of {@link Action#SHOW_ENTITY} type.
     *
     * @param value
     *         nbt tag with entity data.
     *
     * @return created event instance.
     */
    static ChatEventShowEntity showEntity(NbtTag value)
    {
        return new ChatEventShowEntityImpl(value);
    }
    /**
     * Create new event of {@link Action#SHOW_ITEM} type.
     *
     * @param value
     *         nbt tag with item data.
     *
     * @return created event instance.
     */
    static ChatEventShowItem showItem(NbtTag value)
    {
        return new ChatEventShowItemImpl(value);
    }

    /**
     * Enum of possible actions.
     */
    enum Action
    {
        /**
         * Show text in special box when hovered.
         */
        SHOW_TEXT(ActionType.HOVER),
        /**
         * Show achievement-like box when hovered.
         */
        SHOW_ACHIEVEMENT(ActionType.HOVER),
        /**
         * Show special box with item in it when hovered.
         */
        SHOW_ITEM(ActionType.HOVER),
        /**
         * Show special box with entity in it when hovered.
         */
        SHOW_ENTITY(ActionType.HOVER),

        /**
         * Can be used only in books, changes page if exist.
         */
        CHANGE_PAGE(ActionType.CLICK),
        /**
         * Try open url in client internet browser.
         */
        OPEN_URL(ActionType.CLICK),
        /**
         * Cannot be used within JSON chat. Opens a link to any protocol, but cannot be used in JSON chat for security reasons. Only exists to internally
         * implement links for screenshots.
         */
        OPEN_FILE(ActionType.CLICK),
        /**
         * Run selected command.
         */
        RUN_COMMAND(ActionType.CLICK),
        /**
         * Set selected string to player chat input box.
         */
        SUGGEST_COMMAND(ActionType.CLICK),

        /**
         * Append selected string to player chat input box, works with shift click.
         */
        APPEND_CHAT(ActionType.CLICK);

        private final ActionType type;

        Action(ActionType type) {this.type = type;}

        /**
         * Returns type of action.
         *
         * @return type of action.
         */
        public ActionType getType()
        {
            return this.type;
        }

        /**
         * Enum of action types.
         */
        public enum ActionType
        {
            /**
             * Hover events.
             */
            HOVER,
            /**
             * Click events.
             */
            CLICK
        }
    }
}
