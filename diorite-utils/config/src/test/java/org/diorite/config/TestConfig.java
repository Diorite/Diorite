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

package org.diorite.config;

import java.util.List;

import org.diorite.config.annotations.Comment;
import org.diorite.config.annotations.CustomKey;
import org.diorite.config.annotations.Footer;
import org.diorite.config.annotations.Header;
import org.diorite.config.annotations.PredefinedComment;

@Header("Test config")
@Footer("Footer!")
@PredefinedComment(path = {"nope", "more"}, value = "Some list!")
@PredefinedComment(path = {"nested", "bean", "intProperty"}, value = "This is very important settings!")
public interface TestConfig extends Config
{
    @Comment("Player money.")
    @CustomKey("player-money")
    default double getMoney()
    {
        return 0.1;
    }

    void setMoney(double money);

    double addMoney(double money);

    void subtractMoney(byte money);

    double multipleMoneyBy(float multi);

    double divideMoney(int div);
}
