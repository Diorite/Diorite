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

package org.diorite.config.impl.groovy

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.ToStringBuilder
import org.diorite.commons.io.StringBuilderWriter
import org.diorite.config.Config
import org.diorite.config.ConfigPropertyValue
import org.diorite.config.ConfigTemplate
import org.diorite.config.SimpleConfig
import org.diorite.config.exceptions.ConfigLoadException
import org.diorite.config.exceptions.ConfigSaveException
import org.diorite.config.impl.ConfigPropertyValueImpl
import org.diorite.config.impl.NestedNodesHelper
import org.diorite.config.serialization.Serialization

import javax.annotation.Nullable
import javax.annotation.WillNotClose
import java.nio.charset.CharsetDecoder
import java.nio.charset.CharsetEncoder
import java.nio.charset.StandardCharsets
import java.util.function.Supplier

class GroovyConfigInit
{
    public static void init()
    {
        GroovyImplementationProvider.nodeSupplier = { ConfigTemplate template -> new ConfigNodeGroovyImpl(template) }
    }
}

@PackageScope
abstract class AbstractConfigGroovyImpl extends AbstractConfigGroovy implements Config
{
    protected final    ConfigTemplate<? extends Config> template
    protected final    Map<String, Object>              dynamicValues = Collections.<String, Object> synchronizedMap(new LinkedHashMap<>(10))
    protected volatile Supplier<CharsetEncoder>         charsetEncoder
    protected volatile Supplier<CharsetDecoder>         charsetDecoder
    protected volatile File                             bindFile

    protected final Map<String, Object> metadata = Collections.<String, Object> synchronizedMap(new HashMap<>(3))

    protected AbstractConfigGroovyImpl(ConfigTemplate<? extends Config> configTemplate)
    {
        this.@template = configTemplate
        encoding(StandardCharsets.UTF_8)
    }

    @Override
    @CompileStatic
    ConfigTemplate<? extends Config> template()
    { return this.@template }

    @Override
    @CompileStatic
    void fillWithDefaults()
    {}

    @Override
    @CompileStatic
    protected Map<String, Object> dynamicValues$Internal$()
    {
        return this.@dynamicValues
    }

    @Override
    @CompileStatic
    protected Map<String, ConfigPropertyValueImpl<Object>> predefinedValues$Internal$()
    {
        return Collections.emptyMap()
    }

    @Override
    @CompileStatic
    void clear()
    {
        this.@dynamicValues.clear()
    }

    @Override
    @CompileStatic
    boolean contains(String key)
    {
        return this.contains(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR))
    }

    @Override
    @CompileStatic
    boolean contains(String... keys)
    {
        if (keys.length == 0)
        {
            throw new IllegalStateException("Empty key given")
        }
        String key = keys[0]
        if (keys.length == 1)
        {
            return this.@dynamicValues.containsKey(key)
        }
        String[] newPath = new String[keys.length - 1]
        System.arraycopy(keys, 1, newPath, 0, keys.length - 1)

        Object o = this.@dynamicValues.get(key)
        if (o instanceof SimpleConfig)
        {
            return ((SimpleConfig) o).contains(newPath)
        }
        if (o == null)
        {
            return false
        }
        try
        {
            NestedNodesHelper.get(o, newPath)
            return true
        }
        catch (Exception ignored)
        {
            return false
        }
    }

    @Override
    @CompileStatic
    Set<String> keys()
    {
        return Collections.unmodifiableSet(this.@dynamicValues.keySet())
    }

    @Override
    @CompileStatic
    Collection<Object> values()
    {
        return Collections.unmodifiableCollection(this.@dynamicValues.values())
    }

    @Override
    @CompileStatic
    Set<Map.Entry<String, Object>> entries()
    {
        return Collections.unmodifiableSet(this.@dynamicValues.entrySet())
    }

    @Override
    @CompileStatic
    Map<String, Object> asMap()
    {
        return Collections.unmodifiableMap(this.@dynamicValues)
    }

    @Override
    @CompileStatic
    boolean isEmpty()
    {
        return this.@dynamicValues.isEmpty()
    }

    @Override
    @CompileStatic
    int size()
    {
        return this.@dynamicValues.size()
    }

    @Override
    @CompileStatic
    Map<String, Object> metadata()
    {
        return this.@metadata
    }

    @Override
    @CompileStatic
    public <T> T get(String key)
    {
        return this.get(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR), null, null)
    }

    @Override
    @CompileStatic
    public <T> T get(String[] key)
    {
        return this.get(key, null, null)
    }

    @Override
    @CompileStatic
    public <T> T get(String key, @Nullable T defValue)
    {
        return this.get(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR), defValue, null)
    }

    @Override
    @CompileStatic
    public <T> T get(String[] key, @Nullable T defValue)
    {
        return this.get(key, defValue, null)
    }

    @Override
    @CompileStatic
    public <T> T get(String key, @Nullable T defValue, @Nullable Class<T> type)
    {
        return this.get(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR), defValue, type)
    }

    @Override
    @CompileStatic
    public <T> T get(String[] keys, @Nullable T defValue, @Nullable Class<T> type)
    {
        if (keys.length == 0)
        {
            throw new IllegalStateException("Empty key given")
        }

        String key = keys[0]
        if (keys.length == 1)
        {
            Object o = this.@dynamicValues.get(key)
            if (o == null)
            {
                if (this.@dynamicValues.containsKey(key))
                {
                    return null
                }
                return defValue
            }
            return (T) o
        }
        String[] newPath = new String[keys.length - 1]
        System.arraycopy(keys, 1, newPath, 0, keys.length - 1)

        Object o = this.@dynamicValues.get(key)
        if (o == null)
        {
            if (this.@dynamicValues.containsKey(key))
            {
                return null
            }
            return defValue
        }
        if (o instanceof SimpleConfig)
        {
            return ((SimpleConfig) o).get(newPath, defValue, type)
        }
        return (T) NestedNodesHelper.get(o, newPath)
    }

    @Override
    @CompileStatic
    public <T> T get(String key, Class<T> type)
    {
        return this.get(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR), null, type)
    }

    @Override
    @CompileStatic
    public <T> T get(String[] key, Class<T> type)
    {
        return this.get(key, null, type)
    }

    @Override
    @CompileStatic
    void set(String key, @Nullable Object value)
    {
        this.set(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR), value)
    }

    @Override
    @CompileStatic
    void set(String[] keys, @Nullable Object value)
    {
        if (keys.length == 0)
        {
            throw new IllegalStateException("Empty key given")
        }

        String key = keys[0]
        if (keys.length == 1)
        {
            this.@dynamicValues.put(key, value)
            return
        }
        String[] newPath = new String[keys.length - 1]
        System.arraycopy(keys, 1, newPath, 0, keys.length - 1)

        Object o = this.@dynamicValues.get(key)
        if (o != null)
        {
            NestedNodesHelper.set(o, newPath, value)
            this.@dynamicValues.put(key, o)
            return
        }
        Config configNode = GroovyImplementationProvider.createNodeInstance()
        this.@dynamicValues.put(key, configNode)
        configNode.set(newPath, value)
    }

    @Override
    @CompileStatic
    Object remove(String key)
    {
        return this.remove(StringUtils.splitPreserveAllTokens(key, ConfigTemplate.SEPARATOR))
    }

    @Override
    @CompileStatic
    Object remove(String... keys)
    {
        if (keys.length == 0)
        {
            throw new IllegalStateException("Empty key given")
        }

        String key = keys[0]
        if (keys.length == 1)
        {
            return this.@dynamicValues.remove(key)
        }
        String[] newPath = new String[keys.length - 1]
        System.arraycopy(keys, 1, newPath, 0, keys.length - 1)

        Object o = this.@dynamicValues.get(key)
        if (o == null)
        {
            return null
        }
        Object removed = NestedNodesHelper.remove(o, newPath)
        this.@dynamicValues.put(key, o)
        return removed
    }

    @Override
    @CompileStatic
    CharsetEncoder encoder()
    {
        return this.@charsetEncoder.get()
    }

    @Override
    @CompileStatic
    void encoder(Supplier<CharsetEncoder> encoder)
    {
        this.@charsetEncoder = encoder
    }

    @Override
    @CompileStatic
    CharsetDecoder decoder()
    {
        return this.@charsetDecoder.get()
    }

    @Override
    @CompileStatic
    void decoder(Supplier<CharsetDecoder> decoder)
    {
        this.@charsetDecoder = decoder
    }

    @Override
    @CompileStatic
    File bindFile()
    {
        return this.@bindFile
    }

    @Override
    @CompileStatic
    void bindFile(@Nullable File file)
    {
        this.@bindFile = file
    }

    @Override
    void save()
    {
        File bindFile = this.@bindFile
        if (bindFile == null)
        {
            throw new ConfigSaveException(this.@template, null, "Config isn't bound to file!")
        }
        this.save(bindFile)
    }

    @Override
    @CompileStatic
    void save(@WillNotClose Writer writer)
    {
        Serialization.getInstance().toYamlWithComments(this, writer, this.@template.getComments())
    }

    @Override
    void load()
    {
        File bindFile = this.@bindFile
        if (bindFile == null)
        {
            throw new ConfigLoadException(this.@template, null, "Config isn't bound to file!")
        }
        this.load(bindFile)
    }

    @Override
    @CompileStatic
    void load(@WillNotClose Reader reader)
    {
        Config fromYaml = Serialization.getInstance().fromYaml(reader, this.@template.getConfigType()) as Config
        if (fromYaml == null)
        {
            return
        }
        fromYaml.asMap().forEach({ String key, Object value ->
            this.setProperty(key, value)
        })
    }

    @Override
    @CompileStatic
    abstract Config clone()

    @Override
    void setProperty(String propertyName, Object newValue)
    {
        this.set(propertyName, newValue)
    }

    @Override
    Object getProperty(String propertyName)
    {
        return this.get(propertyName)
    }

    @Override
    @CompileStatic
    int hashCode()
    {
        return this.asMap().hashCode()
    }

    @Override
    @CompileStatic
    boolean equals(Object object)
    {
        if (object.is(this))
        {
            return true
        }
        if (!(object instanceof Config))
        {
            return false
        }
        Config config = (Config) object
        if (this.@template != config.template())
        {
            return false
        }
        if (config instanceof ConfigBaseGroovyImpl)
        {
            return config == this
        }
        Map thisMap = this.asMap()
        Map otherMap = config.asMap()
        if (thisMap.size() != otherMap.size())
        {
            return false
        }
        EqualsBuilder equalsBuilder = new EqualsBuilder()
        equalsBuilder.append(thisMap.keySet(), otherMap.keySet())
        if (!equalsBuilder.isEquals())
        {
            return false
        }
        return equalsBuilder.append(thisMap.values().toArray(), otherMap.values().toArray()).isEquals()
    }

    @Override
    @CompileStatic
    String toString()
    {
        ToStringBuilder builder = new ToStringBuilder(this)
        builder.append(this.@template.getConfigType().getName())
        builder.append(this.@bindFile)
        for (Map.Entry<String, Object> entry : this.asMap())
        {
            builder.append(entry.getKey(), entry.getValue())
        }
        return builder.build()
    }
}

abstract class ConfigBaseGroovyImpl extends AbstractConfigGroovyImpl implements ConfigBaseGroovy
{
    protected final Map<String, ConfigPropertyValueImpl<Object>> predefinedValues = new LinkedHashMap<>(20)

    ConfigBaseGroovyImpl(ConfigTemplate<? extends Config> configTemplate)
    {
        super(configTemplate)
    }

    @Override
    @CompileStatic
    abstract void fillWithDefaults()

    @Override
    @CompileStatic
    protected Map<String, ConfigPropertyValueImpl<Object>> predefinedValues$Internal$()
    {
        return this.@predefinedValues
    }

    @Override
    @CompileStatic
    void clear()
    {
        for (ConfigPropertyValueImpl<Object> propertyValue : this.@predefinedValues.values())
        {
            propertyValue.setPropertyValue(null)
        }
        super.clear()
    }

    @Override
    @CompileStatic
    boolean contains(String... keys)
    {
        if (keys.length == 0)
        {
            throw new IllegalStateException("Empty key given")
        }
        String key = keys[0]
        if (keys.length == 1)
        {
            ConfigPropertyValueImpl<Object> propertyValue = this.@predefinedValues.get(key)
            if (propertyValue != null)
            {
                return true
            }
            return super.@dynamicValues.containsKey(key)
        }
        String[] newPath = new String[keys.length - 1]
        System.arraycopy(keys, 1, newPath, 0, keys.length - 1)

        ConfigPropertyValueImpl<Object> propertyValue = this.predefinedValues.get(key)
        if (propertyValue != null)
        {
            return true
        }
        Object o = super.@dynamicValues.get(key)
        if (o instanceof SimpleConfig)
        {
            return ((SimpleConfig) o).contains(newPath)
        }
        if (o == null)
        {
            return false
        }
        try
        {
            NestedNodesHelper.get(o, newPath)
            return true
        }
        catch (Exception ignored)
        {
            return false
        }
    }

    @Override
    @CompileStatic
    Set<String> keys()
    {
        Set<String> keys = new LinkedHashSet<>(this.size())
        keys.addAll(this.@predefinedValues.keySet())
        keys.addAll(super.@dynamicValues.keySet())
        return keys
    }

    @Override
    @CompileStatic
    Collection<Object> values()
    {
        Collection<Object> values = new ArrayList<>(this.size())
        for (ConfigPropertyValueImpl<Object> value : this.@predefinedValues.values())
        {
            values.add(value.getPropertyValue())
        }
        values.addAll(super.@dynamicValues.values())
        return values
    }

    @Override
    @CompileStatic
    Set<Map.Entry<String, Object>> entries()
    {
        Set<Map.Entry<String, Object>> entries = new LinkedHashSet<>(this.size())
        for (Map.Entry<String, ConfigPropertyValueImpl<Object>> entry : this.@predefinedValues.entrySet())
        {
            entries.add(new AbstractMap.SimpleEntry(entry.getKey(), entry.getValue().getPropertyValue()))
        }
        entries.addAll(super.@dynamicValues.entrySet())
        return entries
    }

    @Override
    @CompileStatic
    Map<String, Object> asMap()
    {
        Map<String, Object> map = new LinkedHashMap<>(this.size())
        for (Map.Entry<String, ConfigPropertyValueImpl<Object>> entry : this.@predefinedValues.entrySet())
        {
            def val = entry.getValue()
            map.put(entry.getKey(), val.getPropertyValue())
        }
        map.putAll(super.@dynamicValues)
        return map
    }

    @CompileStatic
    protected Map<String, Object> asMap$Internal()
    {
        Map<String, Object> map = new LinkedHashMap<>(this.size())
        for (Map.Entry<String, ConfigPropertyValueImpl<Object>> entry : this.@predefinedValues.entrySet())
        {
            def val = entry.getValue()
            map.put(entry.getKey(), val.getRawValue())
        }
        map.putAll(super.@dynamicValues)
        return map
    }

    @Override
    @CompileStatic
    boolean isEmpty()
    {
        return super.isEmpty() && this.@predefinedValues.isEmpty()
    }

    @Override
    @CompileStatic
    int size()
    {
        return super.size() + this.@predefinedValues.size()
    }

    @Override
    @CompileStatic
    public <T> T get(String[] keys, @Nullable T defValue, @Nullable Class<T> type)
    {
        if (keys.length == 0)
        {
            throw new IllegalStateException("Empty key given")
        }
        String key = keys[0]
        if (keys.length == 1)
        {
            ConfigPropertyValue<T> preDef = (ConfigPropertyValue<T>) this.@predefinedValues.get(key)
            if (preDef != null)
            {
                return preDef.getPropertyValue()
            }
            Object o = super.@dynamicValues.get(key)
            if (o == null)
            {
                if (super.@dynamicValues.containsKey(key))
                {
                    return null
                }
                return defValue
            }
            return (T) o
        }
        String[] newPath = new String[keys.length - 1]
        System.arraycopy(keys, 1, newPath, 0, keys.length - 1)

        ConfigPropertyValue<Object> propertyValue = this.@predefinedValues.get(key)
        if (propertyValue != null)
        {
            return (T) propertyValue.get(newPath)
        }
        Object o = super.@dynamicValues.get(key)
        if (o == null)
        {
            if (super.@dynamicValues.containsKey(key))
            {
                return null
            }
            return defValue
        }
        if (o instanceof SimpleConfig)
        {
            return ((SimpleConfig) o).get(newPath, defValue, type)
        }
        return (T) NestedNodesHelper.get(o, newPath)
    }

    @Override
    void set(String[] keys, @Nullable Object value)
    {
        if (keys.length == 0)
        {
            throw new IllegalStateException("Empty key given")
        }
        String key = keys[0]
        if (keys.length == 1)
        {
            ConfigPropertyValueImpl<Object> propertyValue = this.@predefinedValues.get(key)
            if (propertyValue != null)
            {
                propertyValue.setPropertyValue(value)
                return
            }
            super.@dynamicValues.put(key, value)
            return
        }
        String[] newPath = new String[keys.length - 1]
        System.arraycopy(keys, 1, newPath, 0, keys.length - 1)

        ConfigPropertyValueImpl<Object> propertyValue = this.@predefinedValues.get(key)
        if (propertyValue != null)
        {
            propertyValue.set(newPath, value)
            return
        }
        Object o = super.@dynamicValues.get(key)
        if (o != null)
        {
            NestedNodesHelper.set(o, newPath, value)
            super.@dynamicValues.put(key, o)
            return
        }
        Config configNode = GroovyImplementationProvider.createNodeInstance()
        super.@dynamicValues.put(key, configNode)
        configNode.set(newPath, value)
    }

    @Override
    @CompileStatic
    Object remove(String... keys)
    {
        if (keys.length == 0)
        {
            throw new IllegalStateException("Empty key given")
        }
        String key = keys[0]
        if (keys.length == 1)
        {
            ConfigPropertyValueImpl<Object> propertyValue = this.@predefinedValues.get(key)
            if (propertyValue != null)
            {
                Object rawValue = propertyValue.getPropertyValue()
                propertyValue.setPropertyValue(propertyValue.getDefault())
                return rawValue
            }
            return super.@dynamicValues.remove(key)
        }
        String[] newPath = new String[keys.length - 1]
        System.arraycopy(keys, 1, newPath, 0, keys.length - 1)

        ConfigPropertyValueImpl<Object> propertyValue = this.@predefinedValues.get(key)
        if (propertyValue != null)
        {
            return propertyValue.remove(newPath)
        }
        Object o = super.@dynamicValues.get(key)
        if (o == null)
        {
            return null
        }
        Object removed = NestedNodesHelper.remove(o, newPath)
        super.@dynamicValues.put(key, o)
        return removed
    }

    @Override
    @CompileStatic
    Config clone()
    {
        AbstractConfigGroovyImpl copy = GroovyImplementationProvider.instance.createImplementation(super.@template) as AbstractConfigGroovyImpl
        copy.@charsetDecoder = super.@charsetDecoder
        copy.@charsetEncoder = super.@charsetEncoder
        copy.@bindFile = super.@bindFile

        // ensure deep clone
        StringBuilderWriter writer = new StringBuilderWriter(copy.size() * 400)
        this.save(writer)
        copy.load(new StringReader(writer.toString()))
        return copy
    }

    @Override
    @CompileStatic
    int hashCode()
    {
        return this.asMap$Internal().hashCode()
    }

    @Override
    @CompileStatic
    boolean equals(Object object)
    {
        if (object.is(this))
        {
            return true
        }
        if (!(object instanceof Config))
        {
            return false
        }
        Config config = (Config) object
        if (super.@template != config.template())
        {
            return false
        }
        Map thisMap
        Map otherMap
        if (config instanceof ConfigBaseGroovyImpl)
        {
            thisMap = this.asMap$Internal()
            otherMap = ((ConfigBaseGroovyImpl) config).asMap$Internal()
        }
        else
        {
            thisMap = this.asMap()
            otherMap = config.asMap()
        }
        if (thisMap.size() != otherMap.size())
        {
            return false
        }
        EqualsBuilder equalsBuilder = new EqualsBuilder()
        equalsBuilder.append(thisMap.keySet(), otherMap.keySet())
        if (!equalsBuilder.isEquals())
        {
            return false
        }
        return equalsBuilder.append(thisMap.values().toArray(), otherMap.values().toArray()).isEquals()
    }
}

@CompileStatic
@PackageScope
class ConfigNodeGroovyImpl extends AbstractConfigGroovyImpl implements SimpleConfig
{
    protected ConfigNodeGroovyImpl(ConfigTemplate<? extends SimpleConfig> configTemplate)
    {
        super(configTemplate)
    }

    @Override
    SimpleConfig clone()
    {
        ConfigNodeGroovyImpl copy = (ConfigNodeGroovyImpl) GroovyImplementationProvider.createNodeInstance()
        copy.@charsetDecoder = super.@charsetDecoder
        copy.@charsetEncoder = super.@charsetEncoder
        copy.@bindFile = super.@bindFile

        // ensure deep clone
        StringBuilderWriter writer = new StringBuilderWriter(copy.size() * 400)
        this.save(writer)
        copy.load(new StringReader(writer.toString()))
        return copy
    }
}
