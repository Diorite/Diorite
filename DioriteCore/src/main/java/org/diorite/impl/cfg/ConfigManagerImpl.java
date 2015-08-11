package org.diorite.impl.cfg;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.ConfigManager;
import org.diorite.cfg.simple.ConfigurationSection;
import org.diorite.cfg.simple.MemorySection;
import org.diorite.cfg.yaml.DioriteYaml;

public class ConfigManagerImpl implements ConfigManager
{
    protected final DioriteYaml yaml;

    public ConfigManagerImpl()
    {
        this.yaml = new DioriteYaml();
    }

    public ConfigManagerImpl(final DioriteYaml yaml)
    {
        this.yaml = yaml;
    }

    @Override
    public DioriteYaml getYaml()
    {
        return this.yaml;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T load(final Reader reader) throws IOException
    {
        return (T) this.yaml.load(reader);
    }

    @Override
    public <T> T load(final Class<T> clazz, final Reader reader) throws IOException
    {
        return this.yaml.loadAs(reader, clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ConfigurationSection loadToSection(final Reader reader, final char pathSeparator) throws IOException
    {
        return new MemorySection((Map<String, Object>) this.yaml.load(reader), pathSeparator);
    }

    @Override
    public void save(final Writer reader, final Object object) throws IOException
    {
        this.yaml.dump(object, reader);
    }

    @Override
    public void saveFromSection(final Writer reader, final ConfigurationSection section) throws IOException
    {
        this.yaml.dump(section.getValues(true));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("yaml", this.yaml).toString();
    }
}
