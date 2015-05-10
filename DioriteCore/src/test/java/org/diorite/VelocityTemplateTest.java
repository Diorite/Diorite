package org.diorite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.VelocityEngine;
import org.apache.velocity.runtime.resource.ClasspathResourceLoader;
import org.yaml.snakeyaml.Yaml;

import junit.framework.TestCase;

public class VelocityTemplateTest extends TestCase
{
    @org.junit.Test
    public void testTemplateAdv() throws Exception
    {
        final VelocityContext context = new VelocityContext();
        final MyBean bean = this.createBeanAdv();
        context.put("bean", bean);
        final Yaml yaml = new Yaml();
        context.put("list", yaml.dump(bean.getList()));
        final VelocityEngine ve = new VelocityEngine();
        ve.setProperty("file.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        final Template t = ve.getTemplate("mybean1.vm", "UTF-8");
        final StringWriter writer = new StringWriter();
        t.merge(context, writer);
        final String output = writer.toString().trim().replaceAll("\\r\\n", "\n");
//        System.out.println(output);
        final String etalon = getLocalResource("etalon2-templateAdv.yaml").trim();
//        System.out.println(etalon);
        assertEquals(etalon.length(), output.length());
        assertEquals(etalon, output);
        // parse the YAML document
        final Yaml loader = new Yaml();
        final MyBean parsedBean = loader.loadAs(etalon, MyBean.class);
        assertEquals(bean, parsedBean);
    }

    @org.junit.Test
    public void testTemplate() throws Exception
    {
        final VelocityContext context = new VelocityContext();
        final MyBean bean = this.createBean();
        context.put("bean", bean);
        final Yaml yaml = new Yaml();
        context.put("list", yaml.dump(bean.getList()));
        final VelocityEngine ve = new VelocityEngine();
        ve.setProperty("file.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        final Template t = ve.getTemplate("mybean1.vm", "UTF-8");
        final StringWriter writer = new StringWriter();
        t.merge(context, writer);
        final String output = writer.toString().trim().replaceAll("\\r\\n", "\n");
//        System.out.println(output);
        final String etalon = getLocalResource("etalon2-template.yaml").trim();
        assertEquals(etalon.length(), output.length());
        assertEquals(etalon, output);
        // parse the YAML document
        final Yaml loader = new Yaml();
        final MyBean parsedBean = loader.loadAs(etalon, MyBean.class);
        assertEquals(bean, parsedBean);
    }

    private MyBean createBeanAdv()
    {
        final MyBean bean = new MyBean();
        bean.setId("id123");
        final List<String> list = new ArrayList<>(3);
        list.add("\"N*45N^I$#B\"\"\"\"kHJUljdtrkA$'¶◙▲(2<Z*TV©§'''yregerg'''''''''''''erg7:U☺☻♥♠♣♦•'''◘9○↑*4A ♀-↑UA-☼JYA$¶◙▲(2<Z*TV©§\"");
        list.add("\"N*45N^I$#B\"\"\"\"kHJUG;lkA$¶''''''◙▲(2<Z*TV©§';'tljdtrkA$¶◙▲(2<Z*TV©§''y7:U☺☻♥♠♣♦•◘9○↑*4A''' ♀-↑UA-☼JYA$¶◙▲(2<Z*TV©§\"");
        list.add("\"N*45N^I$#B\"\"\"\"kHJUG;'lkA$¶◙▲(2<Z*T''''''''''V©§gergergre''gerg';tljdtrkA$¶◙▲(2<'''Z*TV©§y7:U☺☻♥♠♣♦•◘9○↑*4A ♀-↑UA-☼JYA$¶◙▲(2<Z*TV©§\"");
        bean.setList(list);
        final Point p = new Point(1.0, 2.0);
        bean.setPoint(p);
        return bean;
    }

    private MyBean createBean()
    {
        final MyBean bean = new MyBean();
        bean.setId("id123");
        final List<String> list = new ArrayList<>(3);
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        bean.setList(list);
        final Point p = new Point(1.0, 2.0);
        bean.setPoint(p);
        return bean;
    }

    public static String getLocalResource(final String theName) throws IOException
    {
        final InputStream input;
        input = VelocityTemplateTest.class.getClassLoader().getResourceAsStream(theName);
        if (input == null)
        {
            throw new RuntimeException("Can not find " + theName);
        }
        final StringBuilder buf = new StringBuilder(3000);
        int i;
        try (BufferedReader is = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));)
        {
            while ((i = is.read()) != - 1)
            {
                buf.append((char) i);
            }
        }
        final String resource = buf.toString();
        // convert EOLs
        final String[] lines = resource.split("\\r?\\n");
        final StringBuilder buffer = new StringBuilder();
        for (final String line : lines)
        {
            buffer.append(line);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public static class MyBean
    {
        private Point        point;
        private List<String> list;
        private List<Integer> empty = new ArrayList<>(5);
        private String id;

        public Point getPoint()
        {
            return this.point;
        }

        public void setPoint(final Point point)
        {
            this.point = point;
        }

        public List<String> getList()
        {
            return this.list;
        }

        public void setList(final List<String> list)
        {
            this.list = list;
        }

        public List<Integer> getEmpty()
        {
            return this.empty;
        }

        public void setEmpty(final List<Integer> empty)
        {
            this.empty = empty;
        }

        public String getId()
        {
            return this.id;
        }

        public void setId(final String id)
        {
            this.id = id;
        }

        @Override
        public int hashCode()
        {
            return this.id.hashCode();
        }

        @Override
        public boolean equals(final Object obj)
        {
            if (obj instanceof MyBean)
            {
                final MyBean bean = (MyBean) obj;
                return this.id.equals(bean.id) && this.point.equals(bean.point) && this.list.equals(bean.list) && this.empty.equals(bean.empty);
            }
            else
            {
                return false;
            }
        }


        @Override
        public String toString()
        {
            return this.id;
        }
    }

    public static class Point
    {
        private final double x;
        private final double y;

        public Point(final Double x, final Double y)
        {
            this.x = x;
            this.y = y;
        }

        public double getX()
        {
            return this.x;
        }

        public double getY()
        {
            return this.y;
        }

        @Override
        public String toString()
        {
            return "<Point x=" + String.valueOf(this.x) + " y=" + String.valueOf(this.y) + ">";
        }

        @Override
        public boolean equals(final Object obj)
        {
            return (obj instanceof Point) && this.toString().equals(obj.toString());
        }

        @Override
        public int hashCode()
        {
            return this.toString().hashCode();
        }
    }

}
