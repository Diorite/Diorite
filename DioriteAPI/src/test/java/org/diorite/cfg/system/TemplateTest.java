package org.diorite.cfg.system;

import java.util.List;

import junit.framework.TestCase;

public class TemplateTest extends TestCase
{
    @org.junit.Test
    public void testTemplate() throws Exception
    {

    }

    public static class TestConfig
    {
        private String playerName;
        private int money;
        private String desc;
        private List<String> friends;
        private List<String> strings;
        private List<Integer> ints;
    }
}