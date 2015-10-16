/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.cfg.system;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import org.diorite.cfg.annotations.CfgClass;
import org.diorite.cfg.annotations.CfgComment;
import org.diorite.cfg.annotations.CfgFooterComment;
import org.diorite.cfg.annotations.CfgFooterComments;
import org.diorite.cfg.annotations.CfgFooterNoNewLine;
import org.diorite.cfg.annotations.CfgPriority;
import org.diorite.cfg.annotations.CfgStringArrayMultilineThreshold;
import org.diorite.cfg.annotations.CfgStringStyle;
import org.diorite.cfg.annotations.CfgStringStyle.StringStyle;
import org.diorite.cfg.annotations.defaults.CfgIntArrayDefault;

import junit.framework.TestCase;

@SuppressWarnings("MagicNumber")
public class TemplateTest extends TestCase
{
    private static final boolean PRINT_TEMPLATE = false;

    @org.junit.Test
    public void testAdvTemplate() throws Exception
    {
        final TestAdvConfig testObj = new TestAdvConfig();

        long s = System.nanoTime();
        final Template<TestAdvConfig> template = TemplateCreator.getTemplate(TestAdvConfig.class);
        long e = System.nanoTime();
        long d = e - s;

        System.out.println("[Adv] Temple generation time: " + d + "ns (" + (d / 1_000_000) + "ms)");

        s = System.nanoTime();
        final String str = template.dumpAsString(testObj);
        e = System.nanoTime();
        d = e - s;

        System.out.println("[Adv] dumpAsString time: " + d + "ns (" + (d / 1_000_000) + "ms)");
        if (PRINT_TEMPLATE)
        {
            System.out.println(str);
        }

        final Yaml yaml = new Yaml();
        final TestAdvConfig testObjNew = yaml.loadAs(str, TestAdvConfig.class);
        assertTrue("[Adv] Generated and reader object must be this same!", testObj.equals(testObjNew));
    }

    @org.junit.Test
    public void testNormalTemplate() throws Exception
    {
        final TestConfig testObj = new TestConfig();

        long s = System.nanoTime();
        final Template<TestConfig> template = TemplateCreator.getTemplate(TestConfig.class);
        long e = System.nanoTime();
        long d = e - s;

        System.out.println("[Normal] Temple generation time: " + d + "ns (" + (d / 1_000_000) + "ms)");

        s = System.nanoTime();
        final String str = template.dumpAsString(testObj);
        e = System.nanoTime();
        d = e - s;

        System.out.println("[Normal] dumpAsString time: " + d + "ns (" + (d / 1_000_000) + "ms)");
        if (PRINT_TEMPLATE)
        {
            System.out.println(str);
        }

        final Yaml yaml = new Yaml();
        final TestConfig testObjNew = yaml.loadAs(str, TestConfig.class);
        assertTrue("[Normal] Generated and reader object must be this same!", testObj.equals(testObjNew));
    }

    @org.junit.Test
    public void testSimpleTemplate() throws Exception
    {
        final SimpleTestConfig testObj = new SimpleTestConfig();

        long s = System.nanoTime();
        final Template<SimpleTestConfig> template = TemplateCreator.getTemplate(SimpleTestConfig.class);
        long e = System.nanoTime();
        long d = e - s;

        System.out.println("[Simple] Temple generation time: " + d + "ns (" + (d / 1_000_000) + "ms)");
        System.out.println();

        s = System.nanoTime();
        final String str = template.dumpAsString(testObj);
        e = System.nanoTime();
        d = e - s;

        System.out.println("[Simple] dumpAsString time: " + d + "ns (" + (d / 1_000_000) + "ms)");
        if (PRINT_TEMPLATE)
        {
            System.out.println(str);
        }

        final Yaml yaml = new Yaml();
        final SimpleTestConfig testObjNew = yaml.loadAs(str, SimpleTestConfig.class);
        assertTrue("[Simple] Generated and reader object must be this same!", testObj.equals(testObjNew));
    }

    @CfgClass(name = "TestAdvConfig", excludeFields = {"money"})
    @CfgComment("Welcome in test confgiuration file!")
    @CfgComment("This epic code make configuration simple.")
    @CfgComment("You will love it!")
    @CfgFooterComments({"End of file", "I hope you liked it!"})
    public static class TestAdvConfig
    {
        @CfgComment("This option make you happy")
        @CfgFooterComment("End of happy option :<")
        @CfgStringStyle(StringStyle.ALWAYS_QUOTED)
        private String       playerName    = "someName";
        @CfgComment("This option make you even more happy")
        @CfgStringStyle(StringStyle.ALWAYS_MULTI_LINE)
        private String       playerSubName = "someOtherName";
        private int          money         = 634;
        private String       desc          = "Some bigger\n        amount\nof text\n  to test\n    multiline";
        @CfgStringArrayMultilineThreshold(3)
        @CfgStringStyle(StringStyle.ALWAYS_MULTI_LINE)
        @CfgFooterComment("Nope, you don't have friends...")
        @CfgFooterNoNewLine
        private List<String> friends       = Arrays.asList("player1", "player2", "player3");
        private List<String> strings       = Arrays.asList("This text is just to took some\n    space \nspaaaaaaaaaace", "more text", "and even more\n    fucking textjust to \ntook moreeeeeespaceeeeeeee");
        @CfgPriority(200)
        @CfgIntArrayDefault({5, 8, 4, 2, 5, 4, 6})
        private List<Integer> ints;//          = Arrays.asList(1, 2, 3, 4, 7, 5, 3);
        private Map<String, Map<Integer, Double>> map      = new HashMap<>(10);
        private Map<Integer, Map<String, String>> otherMap = new HashMap<>(10);

        @CfgComment("Sub-class test")
        private TestConfig subClass = new TestConfig();

        private Map<Collection<Integer>, Collection<Integer>> superMap = new HashMap<>(10);

        public String getPlayerSubName()
        {
            return this.playerSubName;
        }

        public TestConfig getSubClass()
        {
            return this.subClass;
        }

        public void setSubClass(final TestConfig subClass)
        {
            this.subClass = subClass;
        }

        public void setPlayerSubName(final String playerSubName)
        {
            this.playerSubName = playerSubName;
        }

        public String getPlayerName()
        {
            return this.playerName;
        }

        public Map<Collection<Integer>, Collection<Integer>> getSuperMap()
        {
            return this.superMap;
        }

        public void setSuperMap(final Map<Collection<Integer>, Collection<Integer>> superMap)
        {
            this.superMap = superMap;
        }
//        public Map<int[], int[]> getSuperMap()
//        {
//            return this.superMap;
//        }
//
//        public void setSuperMap(final Map<int[], int[]> superMap)
//        {
//            this.superMap = superMap;
//        }

        public void setPlayerName(final String playerName)
        {
            this.playerName = playerName;
        }

        public int getMoney()
        {
            return this.money;
        }

        public void setMoney(final int money)
        {
            this.money = money;
        }

        public String getDesc()
        {
            return this.desc;
        }

        public void setDesc(final String desc)
        {
            this.desc = desc;
        }

        public List<String> getFriends()
        {
            return this.friends;
        }

        public void setFriends(final List<String> friends)
        {
            this.friends = friends;
        }

        public List<String> getStrings()
        {
            return this.strings;
        }

        public void setStrings(final List<String> strings)
        {
            this.strings = strings;
        }

        public List<Integer> getInts()
        {
            return this.ints;
        }

        public void setInts(final List<Integer> ints)
        {
            this.ints = ints;
        }

        public Map<String, Map<Integer, Double>> getMap()
        {
            return this.map;
        }

        public void setMap(final Map<String, Map<Integer, Double>> map)
        {
            this.map = map;
        }

        public Map<Integer, Map<String, String>> getOtherMap()
        {
            return this.otherMap;
        }

        public void setOtherMap(final Map<Integer, Map<String, String>> otherMap)
        {
            this.otherMap = otherMap;
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (! (o instanceof TestAdvConfig))
            {
                return false;
            }

            final TestAdvConfig that = (TestAdvConfig) o;

            if ((this.playerName != null) ? ! this.playerName.equals(that.playerName) : (that.playerName != null))
            {
                return false;
            }
            if ((this.playerSubName != null) ? ! this.playerSubName.equals(that.playerSubName) : (that.playerSubName != null))
            {
                return false;
            }
            if ((this.desc != null) ? ! this.desc.equals(that.desc) : (that.desc != null))
            {
                return false;
            }
            if ((this.friends != null) ? ! this.friends.equals(that.friends) : (that.friends != null))
            {
                return false;
            }
            if ((this.strings != null) ? ! this.strings.equals(that.strings) : (that.strings != null))
            {
                return false;
            }
//            if (this.ints != null ? ! this.ints.equals(that.ints) : that.ints != null)
//            {
//                return false;
//            }
            if ((this.map != null) ? ! this.map.equals(that.map) : (that.map != null))
            {
                return false;
            }
            if ((this.otherMap != null) ? ! this.otherMap.equals(that.otherMap) : (that.otherMap != null))
            {
                return false;
            }
            if ((this.subClass != null) ? ! this.subClass.equals(that.subClass) : (that.subClass != null))
            {
                return false;
            }
            return ! ((this.superMap != null) ? ! this.superMap.equals(that.superMap) : (that.superMap != null));

        }

        @Override
        public int hashCode()
        {
            int result = (this.playerName != null) ? this.playerName.hashCode() : 0;
            result = (31 * result) + ((this.playerSubName != null) ? this.playerSubName.hashCode() : 0);
            result = (31 * result) + ((this.desc != null) ? this.desc.hashCode() : 0);
            result = (31 * result) + ((this.friends != null) ? this.friends.hashCode() : 0);
            result = (31 * result) + ((this.strings != null) ? this.strings.hashCode() : 0);
//            result = 31 * result + (this.ints != null ? this.ints.hashCode() : 0);
            result = (31 * result) + ((this.map != null) ? this.map.hashCode() : 0);
            result = (31 * result) + ((this.otherMap != null) ? this.otherMap.hashCode() : 0);
            result = (31 * result) + ((this.subClass != null) ? this.subClass.hashCode() : 0);
            result = (31 * result) + ((this.superMap != null) ? this.superMap.hashCode() : 0);
            return result;
        }

        {
            {
                final Map<Integer, Double> subMap1 = new HashMap<>(5);
                subMap1.put(1, 3.44);
                subMap1.put(5, 1.22);
                final Map<Integer, Double> subMap2 = new HashMap<>(5);
                subMap2.put(15, 35.454);
                subMap2.put(35, 13.242);

                this.map.put("map1", subMap1);
                this.map.put("map2", subMap2);
                this.map.put("map3", null);
            }

            {
                final Map<String, String> subMap1 = new HashMap<>(5);
                subMap1.put("dosc\ndizwny\nklucz\'\"heh\nTestujemy możliwoścci", "prosta wartość");
//                subMap1.put("dosc\ndizwny\nklucz\'\"heh\nTestujemy możliwoścci", "wartosc\njest\ntez\ndziwna\nme\"h\"");
                subMap1.put("'meh'", "\"heh\"");
                final Map<String, String> subMap2 = new HashMap<>(5);
                subMap2.put("33", "hehe");
                subMap2.put("67", "32");

                this.otherMap.put(69, subMap1);
                this.otherMap.put(100, subMap2);
            }
            {
                this.superMap.put(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6));
//                superMap.put(new int[]{1, 2, 3}, new int[]{4, 5, 6});
//                superMap.put(new int[]{7, 8, 9}, new int[]{10, 11, 12});
            }
        }
    }


    @CfgClass(name = "TestConfig", excludeFields = {"money"})
    @CfgComment("Welcome in test confgiuration file!")
    @CfgComment("This epic code make configuration simple.")
    @CfgComment("You will love it!")
    @CfgFooterComments({"End of file", "I hope you liked it!"})
    public static class TestConfig
    {
        @CfgComment("This option make you happy")
        @CfgFooterComment("End of happy option :<")
        @CfgStringStyle(StringStyle.ALWAYS_QUOTED)
        private String                                        playerName    = "someName";
        @CfgComment("This option make you even more happy")
        @CfgStringStyle(StringStyle.ALWAYS_MULTI_LINE)
        private String                                        playerSubName = "someOtherName";
        private int                                           money         = 634;
        private String                                        desc          = "Some bigger\n        amount\nof text\n  to test\n    multiline";
        @CfgStringArrayMultilineThreshold(3)
        @CfgStringStyle(StringStyle.ALWAYS_MULTI_LINE)
        @CfgFooterComment("Nope, you don't have friends...")
        @CfgFooterNoNewLine
        private List<String>                                  friends       = Arrays.asList("player1", "player2", "player3");
        private List<String>                                  strings       = Arrays.asList("This text is just to took some\n    space \nspaaaaaaaaaace", "more text", "and even more\n    fucking textjust to \ntook moreeeeeespaceeeeeeee");
        @CfgPriority(200)
        private List<Integer>                                 ints          = Arrays.asList(1, 2, 3, 4, 7, 5, 3);
        private Map<String, Map<Integer, Double>>             map           = new HashMap<>(10);
        private Map<Integer, Map<String, String>>             otherMap      = new HashMap<>(10);
        private Map<Collection<Integer>, Collection<Integer>> superMap      = new HashMap<>(10);

        public String getPlayerSubName()
        {
            return this.playerSubName;
        }

        public void setPlayerSubName(final String playerSubName)
        {
            this.playerSubName = playerSubName;
        }

        public String getPlayerName()
        {
            return this.playerName;
        }

        public Map<Collection<Integer>, Collection<Integer>> getSuperMap()
        {
            return this.superMap;
        }

        public void setSuperMap(final Map<Collection<Integer>, Collection<Integer>> superMap)
        {
            this.superMap = superMap;
        }
//        public Map<int[], int[]> getSuperMap()
//        {
//            return this.superMap;
//        }
//
//        public void setSuperMap(final Map<int[], int[]> superMap)
//        {
//            this.superMap = superMap;
//        }

        public void setPlayerName(final String playerName)
        {
            this.playerName = playerName;
        }

        public int getMoney()
        {
            return this.money;
        }

        public void setMoney(final int money)
        {
            this.money = money;
        }

        public String getDesc()
        {
            return this.desc;
        }

        public void setDesc(final String desc)
        {
            this.desc = desc;
        }

        public List<String> getFriends()
        {
            return this.friends;
        }

        public void setFriends(final List<String> friends)
        {
            this.friends = friends;
        }

        public List<String> getStrings()
        {
            return this.strings;
        }

        public void setStrings(final List<String> strings)
        {
            this.strings = strings;
        }

        public List<Integer> getInts()
        {
            return this.ints;
        }

        public void setInts(final List<Integer> ints)
        {
            this.ints = ints;
        }

        public Map<String, Map<Integer, Double>> getMap()
        {
            return this.map;
        }

        public void setMap(final Map<String, Map<Integer, Double>> map)
        {
            this.map = map;
        }

        public Map<Integer, Map<String, String>> getOtherMap()
        {
            return this.otherMap;
        }

        public void setOtherMap(final Map<Integer, Map<String, String>> otherMap)
        {
            this.otherMap = otherMap;
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (! (o instanceof TestConfig))
            {
                return false;
            }

            final TestConfig that = (TestConfig) o;

//            if (this.money != that.money)
//            {
//                return false;
//            }
            if ((this.playerName != null) ? ! this.playerName.equals(that.playerName) : (that.playerName != null))
            {
                return false;
            }
            if ((this.desc != null) ? ! this.desc.equals(that.desc) : (that.desc != null))
            {
                return false;
            }
            if ((this.friends != null) ? ! this.friends.equals(that.friends) : (that.friends != null))
            {
                return false;
            }
            if ((this.strings != null) ? ! this.strings.equals(that.strings) : (that.strings != null))
            {
                return false;
            }
            if ((this.ints != null) ? ! this.ints.equals(that.ints) : (that.ints != null))
            {
                return false;
            }
            if ((this.map != null) ? ! this.map.equals(that.map) : (that.map != null))
            {
                return false;
            }
            return ! ((this.otherMap != null) ? ! this.otherMap.equals(that.otherMap) : (that.otherMap != null));

        }

        @Override
        public int hashCode()
        {
            int result = (this.playerName != null) ? this.playerName.hashCode() : 0;
//            result = 31 * result + this.money;
            result = (31 * result) + ((this.desc != null) ? this.desc.hashCode() : 0);
            result = (31 * result) + ((this.friends != null) ? this.friends.hashCode() : 0);
            result = (31 * result) + ((this.strings != null) ? this.strings.hashCode() : 0);
            result = (31 * result) + ((this.ints != null) ? this.ints.hashCode() : 0);
            result = (31 * result) + ((this.map != null) ? this.map.hashCode() : 0);
            result = (31 * result) + ((this.otherMap != null) ? this.otherMap.hashCode() : 0);
            return result;
        }

        {
            {
                final Map<Integer, Double> subMap1 = new HashMap<>(5);
                subMap1.put(1, 3.44);
                subMap1.put(5, 1.22);
                final Map<Integer, Double> subMap2 = new HashMap<>(5);
                subMap2.put(15, 35.454);
                subMap2.put(35, 13.242);

                this.map.put("map1", subMap1);
                this.map.put("map2", subMap2);
                this.map.put("map3", null);
            }

            {
                final Map<String, String> subMap1 = new HashMap<>(5);
                subMap1.put("dosc\ndizwny\nklucz\'\"heh\nTestujemy możliwoścci", "prosta wartość");
//                subMap1.put("dosc\ndizwny\nklucz\'\"heh\nTestujemy możliwoścci", "wartosc\njest\ntez\ndziwna\nme\"h\"");
                subMap1.put("'meh'", "\"heh\"");
                final Map<String, String> subMap2 = new HashMap<>(5);
                subMap2.put("33", "hehe");
                subMap2.put("67", "32");

                this.otherMap.put(69, subMap1);
                this.otherMap.put(100, subMap2);
            }
            {
                this.superMap.put(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6));
//                superMap.put(new int[]{1, 2, 3}, new int[]{4, 5, 6});
//                superMap.put(new int[]{7, 8, 9}, new int[]{10, 11, 12});
            }
        }
    }

    public static class SimpleTestConfig
    {
        private String                                        playerName = "someName";
        private int                                           money      = 634;
        private String                                        desc       = "Some bigger\n        amount\nof text\n  to test\n    multiline";
        private List<String>                                  friends    = Arrays.asList("player1", "player2", "player3");
        private List<String>                                  strings    = Arrays.asList("This text is just to took some\n    space \nspaaaaaaaaaace", "more text", "and even more\n    fucking textjust to \ntook moreeeeeespaceeeeeeee");
        private List<Integer>                                 ints       = Arrays.asList(1, 2, 3, 4, 7, 5, 3);
        private Map<String, Map<Integer, Double>>             map        = new HashMap<>(10);
        private Map<Integer, Map<String, String>>             otherMap   = new HashMap<>(10);
        private Map<Collection<Integer>, Collection<Integer>> superMap   = new HashMap<>(10);

        public String getPlayerName()
        {
            return this.playerName;
        }

        public Map<Collection<Integer>, Collection<Integer>> getSuperMap()
        {
            return this.superMap;
        }

        public void setSuperMap(final Map<Collection<Integer>, Collection<Integer>> superMap)
        {
            this.superMap = superMap;
        }
//        public Map<int[], int[]> getSuperMap()
//        {
//            return this.superMap;
//        }
//
//        public void setSuperMap(final Map<int[], int[]> superMap)
//        {
//            this.superMap = superMap;
//        }

        public void setPlayerName(final String playerName)
        {
            this.playerName = playerName;
        }

        public int getMoney()
        {
            return this.money;
        }

        public void setMoney(final int money)
        {
            this.money = money;
        }

        public String getDesc()
        {
            return this.desc;
        }

        public void setDesc(final String desc)
        {
            this.desc = desc;
        }

        public List<String> getFriends()
        {
            return this.friends;
        }

        public void setFriends(final List<String> friends)
        {
            this.friends = friends;
        }

        public List<String> getStrings()
        {
            return this.strings;
        }

        public void setStrings(final List<String> strings)
        {
            this.strings = strings;
        }

        public List<Integer> getInts()
        {
            return this.ints;
        }

        public void setInts(final List<Integer> ints)
        {
            this.ints = ints;
        }

        public Map<String, Map<Integer, Double>> getMap()
        {
            return this.map;
        }

        public void setMap(final Map<String, Map<Integer, Double>> map)
        {
            this.map = map;
        }

        public Map<Integer, Map<String, String>> getOtherMap()
        {
            return this.otherMap;
        }

        public void setOtherMap(final Map<Integer, Map<String, String>> otherMap)
        {
            this.otherMap = otherMap;
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (! (o instanceof SimpleTestConfig))
            {
                return false;
            }

            final SimpleTestConfig that = (SimpleTestConfig) o;

            if (this.money != that.money)
            {
                return false;
            }
            if ((this.playerName != null) ? ! this.playerName.equals(that.playerName) : (that.playerName != null))
            {
                return false;
            }
            if ((this.desc != null) ? ! this.desc.equals(that.desc) : (that.desc != null))
            {
                return false;
            }
            if ((this.friends != null) ? ! this.friends.equals(that.friends) : (that.friends != null))
            {
                return false;
            }
            if ((this.strings != null) ? ! this.strings.equals(that.strings) : (that.strings != null))
            {
                return false;
            }
            if ((this.ints != null) ? ! this.ints.equals(that.ints) : (that.ints != null))
            {
                return false;
            }
            if ((this.map != null) ? ! this.map.equals(that.map) : (that.map != null))
            {
                return false;
            }
            return ! ((this.otherMap != null) ? ! this.otherMap.equals(that.otherMap) : (that.otherMap != null));

        }

        @Override
        public int hashCode()
        {
            int result = (this.playerName != null) ? this.playerName.hashCode() : 0;
            result = (31 * result) + this.money;
            result = (31 * result) + ((this.desc != null) ? this.desc.hashCode() : 0);
            result = (31 * result) + ((this.friends != null) ? this.friends.hashCode() : 0);
            result = (31 * result) + ((this.strings != null) ? this.strings.hashCode() : 0);
            result = (31 * result) + ((this.ints != null) ? this.ints.hashCode() : 0);
            result = (31 * result) + ((this.map != null) ? this.map.hashCode() : 0);
            result = (31 * result) + ((this.otherMap != null) ? this.otherMap.hashCode() : 0);
            return result;
        }

        {
            {
                final Map<Integer, Double> subMap1 = new HashMap<>(5);
                subMap1.put(1, 3.44);
                subMap1.put(5, 1.22);
                final Map<Integer, Double> subMap2 = new HashMap<>(5);
                subMap2.put(15, 35.454);
                subMap2.put(35, 13.242);

                this.map.put("map1", subMap1);
                this.map.put("map2", subMap2);
                this.map.put("map3", null);
            }

            {
                final Map<String, String> subMap1 = new HashMap<>(5);
                subMap1.put("dosc\ndizwny\nklucz\'\"heh\nTestujemy możliwoścci", "prosta wartość");
//                subMap1.put("dosc\ndizwny\nklucz\'\"heh\nTestujemy możliwoścci", "wartosc\njest\ntez\ndziwna\nme\"h\"");
                subMap1.put("'meh'", "\"heh\"");
                final Map<String, String> subMap2 = new HashMap<>(5);
                subMap2.put("33", "hehe");
                subMap2.put("67", "32");

                this.otherMap.put(69, subMap1);
                this.otherMap.put(100, subMap2);
            }
            {
                this.superMap.put(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6));
//                superMap.put(new int[]{1, 2, 3}, new int[]{4, 5, 6});
//                superMap.put(new int[]{7, 8, 9}, new int[]{10, 11, 12});
            }
        }
    }
}