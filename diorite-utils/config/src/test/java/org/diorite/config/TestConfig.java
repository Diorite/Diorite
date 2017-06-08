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

import org.diorite.config.annotations.Comment;
import org.diorite.config.annotations.CustomKey;
import org.diorite.config.annotations.Footer;
import org.diorite.config.annotations.GroovyValidator;
import org.diorite.config.annotations.Header;
import org.diorite.config.annotations.PredefinedComment;
import org.diorite.config.annotations.Validator;

@Header("Test config")
@Footer("Footer!")
@PredefinedComment(path = {"nope", "more"}, value = "Some list!")
@PredefinedComment(path = {"nested", "bean", "intProperty"}, value = "This is very important settings!")
public interface TestConfig extends Config
{
    @Comment("Player money.")
    @CustomKey("player-money")
    @GroovyValidator(isTrue = "x >= 0", elseThrow = "money ($x) can't be lower than 0.")
    @GroovyValidator(isTrue = "x < 100_000", elseThrow = "huh")
    default double getMoney()
    {
        return 0.1;
    }

    @Validator
    default void moneyValidator(double money)
    {
        if (money == 5_000)
        {
            throw new RuntimeException("5_000 is magic number!");
        }
    }

    @Validator("money")
    private double moneyValidator2(double money)
    {
        if (money == 6_000)
        {
            return 7_000;
        }
        return money;
    }

    @Validator("money")
    static double moneyValidator3(double money)
    {
        if (money == 13_000)
        {
            throw new RuntimeException("13_000 is magic number!");
        }
        return money;
    }
    @Validator("money")
    static void moneyValidator4(double money)
    {
        if (money == 14_000)
        {
            throw new RuntimeException("14_000 is magic number!");
        }
    }
    @Validator("money")
    static void moneyValidator5(double money, TestConfig testConfig)
    {
        if (money == 15_000)
        {
            throw new RuntimeException("15_000 is magic number! (" + testConfig.name() + ")");
        }
    }
    @Validator("money")
    static double moneyValidator6(Config config, double money)
    {
        if (money == 16_000)
        {
            throw new RuntimeException("16_000 is magic number! (" + config.name() + ")");
        }
        return money;
    }

    void setMoney(double money);

    double addMoney(double money);

    void subtractMoney(byte money);

    double multipleMoneyBy(float multi);

    double divideMoney(int div);

    double powMoneyBy(double exp);

    default double getMoreMoney(int more)
    {
        return this.getMoney() * more;
    }
}
