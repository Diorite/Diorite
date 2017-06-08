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

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

/**
 * Represent score of objective on chat
 */
public interface ChatScore
{
    // TODO: helper method for scoreboard API
    /**
     * Returns name or selector of player to display.
     *
     * @return name or selector of player to display.
     */
    String getName();

    /**
     * Returns name of objective to display.
     *
     * @return name of objective to display.
     */
    String getObjective();

    /**
     * Returns value to display. Optional, if provided real value will be ignored.
     *
     * @return value to display.
     */
    @Nullable
    String getValue();

    /**
     * Returns score representation as json.
     *
     * @return score representation as json.
     */
    default String asJson()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", this.getName());
        jsonObject.addProperty("objective", this.getObjective());
        String value = this.getValue();
        if (value != null)
        {
            jsonObject.addProperty("value", value);
        }
        return jsonObject.toString();
    }

    /**
     * Returns copy of this score. Might return this same instance if object is immutable and does not require creating of copy.
     *
     * @return copy of this score.
     */
    ChatScore duplicate();

    /**
     * Returns new instance if chat score with given value.
     *
     * @param value
     *         score value to use.
     *
     * @return new instance if chat score with given value.
     */
    default ChatScore withValue(@Nullable String value)
    {
        return new SimpleChatScore(this.getName(), this.getObjective(), value);
    }

    /**
     * Returns new instance of chat score with given data.
     *
     * @param name
     *         name/selector of player to display.
     * @param objective
     *         objective to display.
     * @param value
     *         value to display. Optional, if provided real value will be ignored.
     *
     * @return new instance of chat score with given data.
     */
    static ChatScore of(String name, String objective, @Nullable String value)
    {
        return new SimpleChatScore(name, objective, value);
    }

    /**
     * Returns new instance of chat score with given data.
     *
     * @param name
     *         name/selector of player to display.
     * @param objective
     *         objective to display.
     *
     * @return new instance of chat score with given data.
     */
    static ChatScore of(String name, String objective)
    {
        return new SimpleChatScore(name, objective, null);
    }
}
