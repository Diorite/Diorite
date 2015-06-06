package org.diorite.cfg.system;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.DioriteConfig;
import org.diorite.cfg.annotations.defaults.CfgStringDefault;
import org.diorite.cfg.system.TemplateTest.SimpleTestConfig;
import org.diorite.cfg.system.TemplateTest.TestAdvConfig;
import org.diorite.cfg.system.TemplateTest.TestConfig;
import org.diorite.cfg.yaml.DioriteYaml;

import junit.framework.TestCase;

public class BaseTemplateTest extends TestCase
{
    private static final boolean PRINT_RESULT = true;

    @org.junit.Test
    public void testLoadDioriteCfg() throws Exception
    {
        final Template<DioriteConfig> template = TemplateCreator.getTemplate(DioriteConfig.class);
        final DioriteYaml d = new DioriteYaml(new TemplateYamlConstructor());

        final DioriteConfig c1 = template.fillDefaults(new DioriteConfig());
        final String s = template.dumpAsString(c1);
        final DioriteConfig c2 = d.loadAs(s, DioriteConfig.class);

        if (PRINT_RESULT)
        {
            System.out.println(c1);
            System.out.println(c2);
        }

        assertTrue(c1.equals(c2));
    }

    @org.junit.Test
    public void testLoadAdv() throws Exception
    {
        final Template<TestAdvConfig> template = TemplateCreator.getTemplate(TestAdvConfig.class);
        final DioriteYaml d = new DioriteYaml(new TemplateYamlConstructor());

        final TestAdvConfig c1 = new TestAdvConfig();
        final String s = template.dumpAsString(c1);
        final TestAdvConfig c2 = d.loadAs(s, TestAdvConfig.class);

        if (PRINT_RESULT)
        {
            System.out.println(c1);
            System.out.println(c2);
        }

        assertTrue(c1.equals(c2));
    }

    @org.junit.Test
    public void testLoadNormal() throws Exception
    {
        final Template<TestConfig> template = TemplateCreator.getTemplate(TestConfig.class);
        final DioriteYaml d = new DioriteYaml(new TemplateYamlConstructor());

        final TestConfig c1 = new TestConfig();
        final String s = template.dumpAsString(c1);
        final TestConfig c2 = d.loadAs(s, TestConfig.class);

        if (PRINT_RESULT)
        {
            System.out.println(c1);
            System.out.println(c2);
        }

        assertTrue(c1.equals(c2));
    }

    @org.junit.Test
    public void testLoadBase() throws Exception
    {
        final Template<SimpleTestConfig> template = TemplateCreator.getTemplate(SimpleTestConfig.class);
        final DioriteYaml d = new DioriteYaml(new TemplateYamlConstructor());

        final SimpleTestConfig c1 = new SimpleTestConfig();
        final String s = template.dumpAsString(c1);
        final SimpleTestConfig c2 = d.loadAs(s, SimpleTestConfig.class);

        if (PRINT_RESULT)
        {
            System.out.println(c1);
            System.out.println(c2);
        }

        assertTrue(c1.equals(c2));
    }

    @org.junit.Test
    public void testDefaults() throws Exception
    {
        final Template<TestCfg> template = TemplateCreator.getTemplate(TestCfg.class);
        final DioriteYaml d = new DioriteYaml(new TemplateYamlConstructor());

        final TestCfg c1 = new TestCfg();
        c1.meh = "Dzialaj!";
        final String s = template.dumpAsString(c1);
        final TestCfg c2 = d.loadAs("value: heh\n", TestCfg.class);

        if (PRINT_RESULT)
        {
            System.out.println(c1);
            System.out.println(c2);
        }

        assertTrue(c1.equals(c2));
    }

    public static class TestCfg
    {
        String value = "heh";

        @CfgStringDefault("Dzialaj!")
        String meh;

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).append("meh", this.meh).toString();
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (! (o instanceof TestCfg))
            {
                return false;
            }

            final TestCfg testCfg = (TestCfg) o;

            return ! ((this.value != null) ? ! this.value.equals(testCfg.value) : (testCfg.value != null)) && ! ((meh != null) ? ! meh.equals(testCfg.meh) : (testCfg.meh != null));

        }

        @Override
        public int hashCode()
        {
            int result = (this.value != null) ? this.value.hashCode() : 0;
            result = (31 * result) + ((this.meh != null) ? this.meh.hashCode() : 0);
            return result;
        }
    }

}