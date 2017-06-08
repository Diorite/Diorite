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

package org.diorite.config.impl.groovy;

import javax.annotation.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.Config;
import org.diorite.config.ConfigManager;
import org.diorite.config.ConfigPropertyActionInstance;
import org.diorite.config.ConfigPropertyTemplate;
import org.diorite.config.ConfigTemplate;
import org.diorite.config.MethodSignature;
import org.diorite.config.SimpleConfig;
import org.diorite.config.impl.ConfigImplementationProvider;
import org.diorite.config.impl.ConfigTemplateImpl;
import org.diorite.config.serialization.Serialization;

import groovy.lang.GroovyClassLoader;

@SuppressWarnings("resource")
public class GroovyImplementationProvider implements ConfigImplementationProvider
{
    private boolean printCode = false;
    @SuppressWarnings("NullableProblems") private ConfigManager configManager;

    private static final GroovyImplementationProvider INSTANCE = new GroovyImplementationProvider();

    public void setPrintCode(boolean printCode)
    {
        this.printCode = printCode;
    }

    private final Map<Class<?>, Function<ConfigTemplate<?>, ? extends Config>> configs = new HashMap<>(20);

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Config> T createImplementation(ConfigTemplate<T> template)
    {
        Class<T> clazz = template.getConfigType();
        if (! clazz.isInterface())
        {
            throw new IllegalArgumentException("Class must be a interface!");
        }

        if (! Serialization.getInstance().isSerializable(clazz))
        {
            Serialization.getInstance().registerSerializer(new ConfigDeserializer<>(clazz));
        }

        if ((clazz == SimpleConfig.class) && (template.getConfigType() == SimpleConfig.class))
        {
            return (T) createNodeInstance();
        }
        Function<ConfigTemplate<?>, ? extends Config> configFunction = this.configs.get(clazz);
        if (configFunction != null)
        {
            return ((T) configFunction.apply(template));
        }

        // TODO: cleanup code
        Map<String, ? extends ConfigPropertyTemplate<?>> properties = template.getOrderedProperties();
        Collection<? extends ConfigPropertyTemplate<?>> props = properties.values();
        Map<? extends ConfigPropertyActionInstance, ? extends ConfigPropertyTemplate<?>> actionsMap = template.getOrderedActionsMap();

        // ==================================================
        // class header:
        StringBuilder implStr = new StringBuilder(2000);
        implStr.append("package org.diorite.config.impl.groovy.gen.$classPackage" +
                       "\n" +
                       "import groovy.transform.CompileStatic\n" +
                       "import org.diorite.config.Config\n" +
                       "import org.diorite.config.ConfigTemplate\n" +
                       "import org.diorite.config.impl.ConfigPropertyTemplateImpl\n" +
                       "import org.diorite.config.impl.ConfigPropertyValueImpl\n" +
                       "import org.diorite.config.impl.groovy.ConfigBaseGroovyImpl\n" +
                       "import org.diorite.config.impl.groovy.GroovyImplementationProvider\n" +
                       "\n" +
                       "// @CompileStatic\n" +
                       "class $className extends ConfigBaseGroovyImpl implements $classFullName\n" +
                       "{\n" +
                       "    @CompileStatic public static void register()\n" +
                       "    {\n" +
                       "         GroovyImplementationProvider.instance.addTypeSupplier($className.class, { ConfigTemplate template -> new $className" +
                       "(template) })\n" +
                       "         GroovyImplementationProvider.instance.addTypeSupplier($classFullName.class, { ConfigTemplate template -> new $className" +
                       "(template) })\n" +
                       "    }\n");

        // ==================================================
        // fields:
        for (ConfigPropertyTemplate<?> prop : props)
        {
            // private final ConfigPropertyValueImpl<Integer> huh;
            if (prop.getRawType().isPrimitive())
            {
                implStr.append("    private ConfigPropertyValueImpl<").append(DioriteReflectionUtils.getWrapperClass(prop.getRawType()).getCanonicalName())
                       .append("> ").append(prop.getOriginalName()).append(";\n");
            }
            else
            {
                implStr.append("    private ConfigPropertyValueImpl<").append(prop.getGenericType().getTypeName()).append("> ").append(prop.getOriginalName())
                       .append(";\n");
            }
        }

        // ==================================================
        // constructor
        implStr.append("\n    $className(ConfigTemplate<? extends Config> t)\n" +
                       "    {\n" +
                       "        super(t)\n");
        for (ConfigPropertyTemplate<?> prop : props)
        {
            String name = prop.getName();
            String property = prop.getOriginalName();
            if (prop.getRawType().isPrimitive())
            {
                implStr.append("        this.@").append(property).append(" = new ConfigPropertyValueImpl<")
                       .append(DioriteReflectionUtils.getWrapperClass(prop.getRawType()).getCanonicalName())
                       .append(">(this, (ConfigPropertyTemplateImpl) t.getTemplateFor('").append(name)
                       .append("'))\n");
            }
            else
            {
                implStr.append("        this.@").append(property).append(" = new ConfigPropertyValueImpl<")
                       .append(prop.getGenericType().getTypeName())
                       .append(">(this, (ConfigPropertyTemplateImpl) t.getTemplateFor('").append(name)
                       .append("'))\n");
            }
            implStr.append("        super.@predefinedValues.put('").append(name).append("', (ConfigPropertyValueImpl) this.@").append(
                    property).append(")\n");
        }
        implStr.append("        fillWithDefaults()\n");
        implStr.append("    }\n");


        // ==================================================
        // fillWithDefaults
        implStr.append("\n" +
                       "    @Override\n" +
                       "    void fillWithDefaults()\n" +
                       "    {\n");
        for (ConfigPropertyTemplate<?> prop : props)
        {
            // this.@huh.setPropertyValue(this.@huh.default)
            implStr.append("        this.@").append(prop.getOriginalName()).append(".setPropertyValue(this.@").append(prop.getOriginalName())
                   .append(".default)\n");
        }
        implStr.append("" +
                       "    }\n\n");


        // ==================================================
        // actions
        for (Entry<? extends ConfigPropertyActionInstance, ? extends ConfigPropertyTemplate<?>> entry : actionsMap.entrySet())
        {
            try
            {
                ConfigPropertyActionInstance action = entry.getKey();
                MethodSignature methodSignature = action.getMethodSignature();
                ConfigPropertyTemplate<?> prop = entry.getValue();
                MethodInvoker declaredMethod = new MethodInvoker(clazz.getDeclaredMethod(methodSignature.getName(), methodSignature.getArguments()));
                String groovyImplementation = action.getPropertyAction().getGroovyImplementation(declaredMethod, prop, action);
                implStr.append(addMethodIndent(groovyImplementation));
            }
            catch (Exception e)
            {
                throw new RuntimeException("can't generate action implementation: " + entry, e);
            }
        }

        // ==================================================
        // class end
        implStr.append("}");

        String classCode = implStr.toString();
        classCode = StringUtils.replace(classCode, "$classFullName", clazz.getCanonicalName());
        classCode = StringUtils.replace(classCode, "$className", clazz.getSimpleName());
        classCode = StringUtils.replace(classCode, "$classPackage", clazz.getPackageName());

        if (this.printCode)
        {
            System.out.println(GroovyTemplateException.addDebugLines(classCode));
        }
        try
        {
            GroovyClassLoader groovyClassLoader = this.configManager.getGroovyClassLoader();
            Class<? extends Tezd> eval =
                    groovyClassLoader.parseClass(classCode, GroovyImplementationProvider.class.getPackageName() + ".gen." + clazz.getCanonicalName());
            if (eval == null)
            {
                throw new GroovyTemplateException(classCode, "Unexpected error, class is null!");
            }
            DioriteReflectionUtils.getMethod(eval, "register").invokeWith();
        }
        catch (GroovyTemplateException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new GroovyTemplateException(classCode, "Can't generate class for: " + clazz.getCanonicalName(), e);
        }
        configFunction = this.configs.get(clazz);
        if (configFunction != null)
        {
            return ((T) configFunction.apply(template));
        }
        throw new GroovyTemplateException(classCode, "Code generated but still missing constructor function!");
    }

    @Override
    public void init(ConfigManager configManager)
    {
        this.configManager = configManager;
        try
        {
            Class<?> groovyInitClass = Class.forName("org.diorite.config.impl.groovy.GroovyConfigInit", true, this.configManager.getGroovyClassLoader());
            MethodInvoker init = DioriteReflectionUtils.getMethod(groovyInitClass, "init");
            init.invokeWith();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("MagicNumber")
    private static String addMethodIndent(String classCode)
    {
        StringBuilder implStr = new StringBuilder(classCode.length() + (classCode.length() / 30));
        for (String s : StringUtils.splitPreserveAllTokens(classCode, '\n'))
        {
            implStr.append("    ").append(s).append("\n");
        }
        return implStr.toString();
    }

    public static GroovyImplementationProvider getInstance()
    {
        return INSTANCE;
    }

    @Nullable
    private static ConfigTemplateImpl<SimpleConfig>                         nodeTemplate;
    @Nullable
    private static Function<ConfigTemplateImpl<SimpleConfig>, SimpleConfig> nodeSupplier;

    static void setNodeSupplier(Function<ConfigTemplateImpl<SimpleConfig>, SimpleConfig> nodeSupplier)
    {
        GroovyImplementationProvider.nodeSupplier = nodeSupplier;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends Config> void addTypeSupplier(Class<T> template, Function<ConfigTemplate<T>, T> supplier)
    {
        this.configs.put(template, (Function) supplier);
    }

    static SimpleConfig createNodeInstance()
    {
        if (nodeTemplate == null)
        {
            nodeTemplate = new ConfigTemplateImpl<>(SimpleConfig.class, INSTANCE);
        }
        if (nodeSupplier == null)
        {
            throw new IllegalStateException("Groovy not loaded");
        }
        SimpleConfig apply = nodeSupplier.apply(nodeTemplate);
        if (! Serialization.getInstance().isSerializable(SimpleConfig.class))
        {
            Serialization.getInstance().registerSerializer(new ConfigDeserializer<>(SimpleConfig.class));
        }
        return apply;
    }

    public interface Tezd extends Config
    {
        int getHuh();

        void setHuh(int huh);

        Locale getLocale();

        default List<? extends Number> getList()
        {
            return List.of(1, 2, 3);
        }
    }
}
