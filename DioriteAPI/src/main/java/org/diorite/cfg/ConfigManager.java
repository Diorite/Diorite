package org.diorite.cfg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.diorite.cfg.simple.ConfigurationSection;
import org.diorite.cfg.yaml.DioriteYaml;

public interface ConfigManager
{
    default <T> T load(final File file) throws IOException
    {
        return this.load(file, StandardCharsets.UTF_8);
    }

    default <T> T load(final Class<T> clazz, final File file) throws IOException
    {
        return this.load(clazz, file, StandardCharsets.UTF_8);
    }

    default ConfigurationSection loadToSection(final File file) throws IOException
    {
        return this.loadToSection(file, StandardCharsets.UTF_8, ConfigurationSection.DEFAULT_SEPARATOR);
    }

    default ConfigurationSection loadToSection(final File file, final char pathSeparator) throws IOException
    {
        return this.loadToSection(file, StandardCharsets.UTF_8, pathSeparator);
    }

    default void save(final File file, final Object object) throws IOException
    {
        this.save(file, object, StandardCharsets.UTF_8);
    }

    default void saveFromSection(final File file, final ConfigurationSection section) throws IOException
    {
        this.saveFromSection(file, section, StandardCharsets.UTF_8);
    }


    default <T> T load(final File file, final Charset charset) throws IOException
    {
        if (! file.exists())
        {
            throw new InvalidConfigurationException("File not found \"" + file.getAbsolutePath() + "\"");
        }
        try (final FileInputStream input = new FileInputStream(file))
        {
            return this.load(input, charset);
        } catch (final FileNotFoundException ignored)
        {
            throw new RuntimeException("Impossible exception.", ignored);
        }
    }

    default <T> T load(final Class<T> clazz, final File file, final Charset charset) throws IOException
    {
        if (! file.exists())
        {
            throw new InvalidConfigurationException("File not found \"" + file.getAbsolutePath() + "\"");
        }
        try (final FileInputStream input = new FileInputStream(file))
        {
            return this.load(clazz, input, charset);
        } catch (final FileNotFoundException ignored)
        {
            throw new RuntimeException("Impossible exception.", ignored);
        }
    }

    default ConfigurationSection loadToSection(final File file, final Charset charset) throws IOException
    {
        if (! file.exists())
        {
            throw new InvalidConfigurationException("File not found \"" + file.getAbsolutePath() + "\"");
        }
        try (final FileInputStream input = new FileInputStream(file))
        {
            return this.loadToSection(input, charset, ConfigurationSection.DEFAULT_SEPARATOR);
        } catch (final FileNotFoundException ignored)
        {
            throw new RuntimeException("Impossible exception.", ignored);
        }
    }

    default ConfigurationSection loadToSection(final File file, final Charset charset, final char pathSeparator) throws IOException
    {
        if (! file.exists())
        {
            throw new InvalidConfigurationException("File not found \"" + file.getAbsolutePath() + "\"");
        }
        try (final FileInputStream input = new FileInputStream(file))
        {
            return this.loadToSection(input, charset, pathSeparator);
        } catch (final FileNotFoundException ignored)
        {
            throw new RuntimeException("Impossible exception.", ignored);
        }
    }

    default void save(final File file, final Object object, final Charset charset) throws IOException
    {
        if (! file.exists())
        {
            file.getAbsoluteFile().getParentFile().mkdirs();
            try
            {
                file.createNewFile();
            } catch (final IOException e)
            {
                throw new RuntimeException("Can't create file: \"" + file.getAbsolutePath() + "\"", e);
            }
        }
        try (final FileOutputStream output = new FileOutputStream(file))
        {
            this.save(output, object, charset);
        } catch (final FileNotFoundException ignored)
        {
            throw new RuntimeException("Impossible exception.", ignored);
        }
    }

    default void saveFromSection(final File file, final ConfigurationSection section, final Charset charset) throws IOException
    {
        if (! file.exists())
        {
            file.getAbsoluteFile().getParentFile().mkdirs();
            try
            {
                file.createNewFile();
            } catch (final IOException e)
            {
                throw new RuntimeException("Can't create file: \"" + file.getAbsolutePath() + "\"", e);
            }
        }
        try (final FileOutputStream output = new FileOutputStream(file))
        {
            this.saveFromSection(output, section, charset);
        } catch (final FileNotFoundException ignored)
        {
            throw new RuntimeException("Impossible exception.", ignored);
        }
    }


    default <T> T load(final InputStream input, final Charset charset) throws IOException
    {
        return this.load(new InputStreamReader(input, charset));
    }

    default <T> T load(final Class<T> clazz, final InputStream input, final Charset charset) throws IOException
    {
        return this.load(clazz, new InputStreamReader(input, charset));
    }

    default ConfigurationSection loadToSection(final InputStream input, final Charset charset) throws IOException
    {
        return this.loadToSection(new InputStreamReader(input, charset), ConfigurationSection.DEFAULT_SEPARATOR);
    }

    default ConfigurationSection loadToSection(final InputStream input, final Charset charset, final char pathSeparator) throws IOException
    {
        return this.loadToSection(new InputStreamReader(input, charset), pathSeparator);
    }

    default void save(final OutputStream output, final Object object, final Charset charset) throws IOException
    {
        this.save(new OutputStreamWriter(output, charset), object);
    }

    default void saveFromSection(final OutputStream output, final ConfigurationSection section, final Charset charset) throws IOException
    {
        this.saveFromSection(new OutputStreamWriter(output, charset), section);
    }


    default <T> T load(final InputStream input) throws IOException
    {
        return this.load(new InputStreamReader(input, StandardCharsets.UTF_8));
    }

    default <T> T load(final Class<T> clazz, final InputStream input) throws IOException
    {
        return this.load(clazz, new InputStreamReader(input, StandardCharsets.UTF_8));
    }

    default ConfigurationSection loadToSection(final InputStream input) throws IOException
    {
        return this.loadToSection(new InputStreamReader(input, StandardCharsets.UTF_8), ConfigurationSection.DEFAULT_SEPARATOR);
    }

    default ConfigurationSection loadToSection(final InputStream input, final char pathSeparator) throws IOException
    {
        return this.loadToSection(new InputStreamReader(input, StandardCharsets.UTF_8), pathSeparator);
    }

    default void save(final OutputStream output, final Object object) throws IOException
    {
        this.save(new OutputStreamWriter(output, StandardCharsets.UTF_8), object);
    }

    default void saveFromSection(final OutputStream output, final ConfigurationSection section) throws IOException
    {
        this.saveFromSection(new OutputStreamWriter(output, StandardCharsets.UTF_8), section);
    }


    DioriteYaml getYaml();

    <T> T load(Reader reader) throws IOException;

    <T> T load(Class<T> clazz, Reader reader) throws IOException;

    default ConfigurationSection loadToSection(final Reader reader) throws IOException
    {
        return this.loadToSection(reader, ConfigurationSection.DEFAULT_SEPARATOR);
    }

    ConfigurationSection loadToSection(Reader reader, char pathSeparator) throws IOException;

    void save(Writer reader, Object object) throws IOException;

    void saveFromSection(Writer reader, ConfigurationSection section) throws IOException;
}
