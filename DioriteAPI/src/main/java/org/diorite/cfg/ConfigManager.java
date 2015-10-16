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

/**
 * Config manager for fast using template config system.
 */
public interface ConfigManager
{
    /**
     * Load given file as object using {@link org.diorite.cfg.system.Template} configuration system. <br>
     * This method will use UTF-8 as default encoding.
     *
     * @param file file to load.
     * @param <T>  type of returned object.
     *
     * @return loaded object.
     *
     * @throws IOException                   if any file-related operation fail.
     * @throws InvalidConfigurationException if config file is invalid.
     */
    default <T> T load(final File file) throws IOException, InvalidConfigurationException
    {
        return this.load(file, StandardCharsets.UTF_8);
    }

    /**
     * Load given file as object using {@link org.diorite.cfg.system.Template} configuration system. <br>
     * This method will use UTF-8 as default encoding.
     *
     * @param clazz type of object to load.
     * @param file  file to load.
     * @param <T>   type of returned object.
     *
     * @return loaded object.
     *
     * @throws IOException                   if any file-related operation fail.
     * @throws InvalidConfigurationException if config file is invalid.
     */
    default <T> T load(final Class<T> clazz, final File file) throws IOException, InvalidConfigurationException
    {
        return this.load(clazz, file, StandardCharsets.UTF_8);
    }

    /**
     * Load given file as {@link ConfigurationSection} (key-value map). <br>
     * This method will use UTF-8 as default encoding and {@link ConfigurationSection#DEFAULT_SEPARATOR} for key separator.
     *
     * @param file file to load.
     *
     * @return loaded {@link ConfigurationSection}.
     *
     * @throws IOException                   if any file-related operation fail.
     * @throws InvalidConfigurationException if config file is invalid.
     */
    default ConfigurationSection loadToSection(final File file) throws IOException, InvalidConfigurationException
    {
        return this.loadToSection(file, StandardCharsets.UTF_8, ConfigurationSection.DEFAULT_SEPARATOR);
    }

    /**
     * Load given file as {@link ConfigurationSection} (key-value map). <br>
     * This method will use UTF-8 as default encoding.
     *
     * @param file          file to load.
     * @param pathSeparator {@link ConfigurationSection} key separator.
     *
     * @return loaded {@link ConfigurationSection}.
     *
     * @throws IOException                   if any file-related operation fail.
     * @throws InvalidConfigurationException if config file is invalid.
     */
    default ConfigurationSection loadToSection(final File file, final char pathSeparator) throws IOException, InvalidConfigurationException
    {
        return this.loadToSection(file, StandardCharsets.UTF_8, pathSeparator);
    }

    /**
     * Save given object to given file using {@link org.diorite.cfg.system.Template} configuration system.<br>
     * This method will use UTF-8 as default encoding.
     *
     * @param file   file to use, file will be created if it don't exist yet. (including directory)
     * @param object object to save using templates, template will be created if it don't exist yet.
     *
     * @throws IOException if any file-related operation fail.
     */
    default void save(final File file, final Object object) throws IOException, InvalidConfigurationException
    {
        this.save(file, object, StandardCharsets.UTF_8);
    }

    /**
     * Save given {@link ConfigurationSection} to given file.<br>
     * This method will use UTF-8 as default encoding.
     *
     * @param file    file to use, file will be created if it don't exist yet. (including directory)
     * @param section {@link ConfigurationSection} to save.
     *
     * @throws IOException if any file-related operation fail.
     */
    default void saveToSection(final File file, final ConfigurationSection section) throws IOException, InvalidConfigurationException
    {
        this.saveToSection(file, section, StandardCharsets.UTF_8);
    }


    /**
     * Load given file as object using {@link org.diorite.cfg.system.Template} configuration system. <br>
     *
     * @param file    file to load.
     * @param charset charset to use.
     * @param <T>     type of returned object.
     *
     * @return loaded object.
     *
     * @throws IOException                   if any file-related operation fail.
     * @throws InvalidConfigurationException if config file is invalid.
     */
    default <T> T load(final File file, final Charset charset) throws IOException, InvalidConfigurationException
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

    /**
     * Load given file as object using {@link org.diorite.cfg.system.Template} configuration system. <br>
     *
     * @param clazz   type of object to load.
     * @param file    file to load.
     * @param charset charset to use.
     * @param <T>     type of returned object.
     *
     * @return loaded object.
     *
     * @throws IOException                   if any file-related operation fail.
     * @throws InvalidConfigurationException if config file is invalid.
     */
    default <T> T load(final Class<T> clazz, final File file, final Charset charset) throws IOException, InvalidConfigurationException
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

    /**
     * Load given file as {@link ConfigurationSection} (key-value map). <br>
     * This method will use {@link ConfigurationSection#DEFAULT_SEPARATOR} for key separator.
     *
     * @param file    file to load.
     * @param charset charset to use.
     *
     * @return loaded {@link ConfigurationSection}.
     *
     * @throws IOException                   if any file-related operation fail.
     * @throws InvalidConfigurationException if config file is invalid.
     */
    default ConfigurationSection loadToSection(final File file, final Charset charset) throws IOException, InvalidConfigurationException
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

    /**
     * Load given file as {@link ConfigurationSection} (key-value map). <br>
     *
     * @param file          file to load.
     * @param charset       charset to use.
     * @param pathSeparator {@link ConfigurationSection} key separator.
     *
     * @return loaded {@link ConfigurationSection}.
     *
     * @throws IOException                   if any file-related operation fail.
     * @throws InvalidConfigurationException if config file is invalid.
     */
    default ConfigurationSection loadToSection(final File file, final Charset charset, final char pathSeparator) throws IOException, InvalidConfigurationException
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

    /**
     * Save given object to given file using {@link org.diorite.cfg.system.Template} configuration system.<br>
     *
     * @param file    file to use, file will be created if it don't exist yet. (including directory)
     * @param object  object to save using templates, template will be created if it don't exist yet.
     * @param charset charset to use.
     *
     * @throws IOException if any file-related operation fail.
     */
    default void save(final File file, final Object object, final Charset charset) throws IOException, InvalidConfigurationException
    {
        if (! file.exists())
        {
            file.getAbsoluteFile().getParentFile().mkdirs();
            try
            {
                file.createNewFile();
            } catch (final IOException e)
            {
                throw new IOException("Can't create file: \"" + file.getAbsolutePath() + "\"", e);
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

    /**
     * Save given {@link ConfigurationSection} to given file.<br>
     *
     * @param file    file to use, file will be created if it don't exist yet. (including directory)
     * @param charset charset to use.
     * @param section {@link ConfigurationSection} to save.
     *
     * @throws IOException if any file-related operation fail.
     */
    default void saveToSection(final File file, final ConfigurationSection section, final Charset charset) throws IOException, InvalidConfigurationException
    {
        if (! file.exists())
        {
            file.getAbsoluteFile().getParentFile().mkdirs();
            try
            {
                file.createNewFile();
            } catch (final IOException e)
            {
                throw new IOException("Can't create file: \"" + file.getAbsolutePath() + "\"", e);
            }
        }
        try (final FileOutputStream output = new FileOutputStream(file))
        {
            this.saveToSection(output, section, charset);
        } catch (final FileNotFoundException ignored)
        {
            throw new RuntimeException("Impossible exception.", ignored);
        }
    }


    /**
     * Load object from given {@link InputStream} using {@link org.diorite.cfg.system.Template} configuration system. <br>
     *
     * @param input   {@link InputStream} to load.
     * @param charset charset to use.
     * @param <T>     type of returned object.
     *
     * @return loaded object.
     *
     * @throws IOException                   if any stream-related operation fail.
     * @throws InvalidConfigurationException if config is invalid.
     */
    default <T> T load(final InputStream input, final Charset charset) throws IOException, InvalidConfigurationException
    {
        return this.load(new InputStreamReader(input, charset));
    }

    /**
     * Load object from given {@link InputStream} using {@link org.diorite.cfg.system.Template} configuration system. <br>
     *
     * @param clazz   type of object to load.
     * @param input   {@link InputStream} to load.
     * @param charset charset to use.
     * @param <T>     type of returned object.
     *
     * @return loaded object.
     *
     * @throws IOException                   if any stream-related operation fail.
     * @throws InvalidConfigurationException if config is invalid.
     */
    default <T> T load(final Class<T> clazz, final InputStream input, final Charset charset) throws IOException, InvalidConfigurationException
    {
        return this.load(clazz, new InputStreamReader(input, charset));
    }

    /**
     * Load given {@link InputStream} as {@link ConfigurationSection} (key-value map). <br>
     * This method will use {@link ConfigurationSection#DEFAULT_SEPARATOR} for key separator.
     *
     * @param input   {@link InputStream} to load.
     * @param charset charset to use.
     *
     * @return loaded {@link ConfigurationSection}.
     *
     * @throws IOException                   if any stream-related operation fail.
     * @throws InvalidConfigurationException if config is invalid.
     */
    default ConfigurationSection loadToSection(final InputStream input, final Charset charset) throws IOException, InvalidConfigurationException
    {
        return this.loadToSection(new InputStreamReader(input, charset), ConfigurationSection.DEFAULT_SEPARATOR);
    }

    /**
     * Load given {@link InputStream} as {@link ConfigurationSection} (key-value map). <br>
     *
     * @param input         {@link InputStream} to load.
     * @param charset       charset to use.
     * @param pathSeparator {@link ConfigurationSection} key separator.
     *
     * @return loaded {@link ConfigurationSection}.
     *
     * @throws IOException                   if any stream-related operation fail.
     * @throws InvalidConfigurationException if config is invalid.
     */
    default ConfigurationSection loadToSection(final InputStream input, final Charset charset, final char pathSeparator) throws IOException, InvalidConfigurationException
    {
        return this.loadToSection(new InputStreamReader(input, charset), pathSeparator);
    }

    /**
     * Save given object to given {@link OutputStream} using {@link org.diorite.cfg.system.Template} configuration system.<br>
     *
     * @param output  output stream to use.
     * @param object  object to save using templates, template will be created if it don't exist yet.
     * @param charset charset to use.
     *
     * @throws IOException if any stream-related operation fail.
     */
    default void save(final OutputStream output, final Object object, final Charset charset) throws IOException
    {
        this.save(new OutputStreamWriter(output, charset), object);
    }

    /**
     * Save given {@link ConfigurationSection} to given {@link OutputStream}.<br>
     *
     * @param output  output stream to use.
     * @param section {@link ConfigurationSection} to save.
     * @param charset charset to use.
     *
     * @throws IOException if any stream-related operation fail.
     */
    default void saveToSection(final OutputStream output, final ConfigurationSection section, final Charset charset) throws IOException
    {
        this.saveToSection(new OutputStreamWriter(output, charset), section);
    }


    /**
     * Load object from given {@link InputStream} using {@link org.diorite.cfg.system.Template} configuration system. <br>
     * This method will use UTF-8 as default encoding.
     *
     * @param input {@link InputStream} to load.
     * @param <T>   type of returned object.
     *
     * @return loaded object.
     *
     * @throws IOException                   if any stream-related operation fail.
     * @throws InvalidConfigurationException if config is invalid.
     */
    default <T> T load(final InputStream input) throws IOException, InvalidConfigurationException
    {
        return this.load(new InputStreamReader(input, StandardCharsets.UTF_8));
    }

    /**
     * Load object from given {@link InputStream} using {@link org.diorite.cfg.system.Template} configuration system. <br>
     * This method will use UTF-8 as default encoding.
     *
     * @param clazz type of object to load.
     * @param input {@link InputStream} to load.
     * @param <T>   type of returned object.
     *
     * @return loaded object.
     *
     * @throws IOException                   if any stream-related operation fail.
     * @throws InvalidConfigurationException if config is invalid.
     */
    default <T> T load(final Class<T> clazz, final InputStream input) throws IOException, InvalidConfigurationException
    {
        return this.load(clazz, new InputStreamReader(input, StandardCharsets.UTF_8));
    }

    /**
     * Load given {@link InputStream} as {@link ConfigurationSection} (key-value map). <br>
     * This method will use UTF-8 as default encoding and {@link ConfigurationSection#DEFAULT_SEPARATOR} for key separator.
     *
     * @param input {@link InputStream} to load.
     *
     * @return loaded {@link ConfigurationSection}.
     *
     * @throws IOException                   if any stream-related operation fail.
     * @throws InvalidConfigurationException if config is invalid.
     */
    default ConfigurationSection loadToSection(final InputStream input) throws IOException, InvalidConfigurationException
    {
        return this.loadToSection(new InputStreamReader(input, StandardCharsets.UTF_8), ConfigurationSection.DEFAULT_SEPARATOR);
    }

    /**
     * Load given {@link InputStream} as {@link ConfigurationSection} (key-value map). <br>
     * This method will use UTF-8 as default encoding.
     *
     * @param input         {@link InputStream} to load.
     * @param pathSeparator {@link ConfigurationSection} key separator.
     *
     * @return loaded {@link ConfigurationSection}.
     *
     * @throws IOException                   if any stream-related operation fail.
     * @throws InvalidConfigurationException if config is invalid.
     */
    default ConfigurationSection loadToSection(final InputStream input, final char pathSeparator) throws IOException, InvalidConfigurationException
    {
        return this.loadToSection(new InputStreamReader(input, StandardCharsets.UTF_8), pathSeparator);
    }

    /**
     * Save given object to given {@link OutputStream} using {@link org.diorite.cfg.system.Template} configuration system.<br>
     * This method will use UTF-8 as default encoding.
     *
     * @param output output stream to use.
     * @param object object to save using templates, template will be created if it don't exist yet.
     *
     * @throws IOException if any stream-related operation fail.
     */
    default void save(final OutputStream output, final Object object) throws IOException
    {
        this.save(new OutputStreamWriter(output, StandardCharsets.UTF_8), object);
    }

    /**
     * Save given {@link ConfigurationSection} to given {@link OutputStream}.<br>
     * This method will use UTF-8 as default encoding.
     *
     * @param output  output stream to use.
     * @param section {@link ConfigurationSection} to save.
     *
     * @throws IOException if any stream-related operation fail.
     */
    default void saveToSection(final OutputStream output, final ConfigurationSection section) throws IOException
    {
        this.saveToSection(new OutputStreamWriter(output, StandardCharsets.UTF_8), section);
    }


    /**
     * Returns yaml instance used by this config manager.
     *
     * @return yaml instance used by this config manager.
     */
    DioriteYaml getYaml();

    /**
     * Load object from given {@link Reader} using {@link org.diorite.cfg.system.Template} configuration system. <br>
     *
     * @param reader {@link Reader} to use.
     * @param <T>    type of returned object.
     *
     * @return loaded object.
     *
     * @throws IOException                   if any reader-related operation fail.
     * @throws InvalidConfigurationException if config is invalid.
     */
    <T> T load(Reader reader) throws IOException, InvalidConfigurationException;

    /**
     * Load object from given {@link Reader} using {@link org.diorite.cfg.system.Template} configuration system. <br>
     *
     * @param clazz  type of object to load.
     * @param reader {@link Reader} to use.
     * @param <T>    type of returned object.
     *
     * @return loaded object.
     *
     * @throws IOException                   if any reader-related operation fail.
     * @throws InvalidConfigurationException if config is invalid.
     */
    <T> T load(Class<T> clazz, Reader reader) throws IOException, InvalidConfigurationException;

    /**
     * Load given {@link Reader} as {@link ConfigurationSection} (key-value map). <br>
     * This method will use {@link ConfigurationSection#DEFAULT_SEPARATOR} for key separator.
     *
     * @param reader {@link Reader} to use.
     *
     * @return loaded {@link ConfigurationSection}.
     *
     * @throws IOException                   if any reader-related operation fail.
     * @throws InvalidConfigurationException if config is invalid.
     */
    default ConfigurationSection loadToSection(final Reader reader) throws IOException, InvalidConfigurationException
    {
        return this.loadToSection(reader, ConfigurationSection.DEFAULT_SEPARATOR);
    }

    /**
     * Load given {@link Reader} as {@link ConfigurationSection} (key-value map). <br>
     *
     * @param reader        {@link Reader} to use.
     * @param pathSeparator {@link ConfigurationSection} key separator.
     *
     * @return loaded {@link ConfigurationSection}.
     *
     * @throws IOException                   if any reader-related operation fail.
     * @throws InvalidConfigurationException if config is invalid.
     */
    ConfigurationSection loadToSection(Reader reader, char pathSeparator) throws IOException, InvalidConfigurationException;

    /**
     * Save given object to given {@link Writer} using {@link org.diorite.cfg.system.Template} configuration system.<br>
     *
     * @param writer {@link Writer} to use.
     * @param object object to save using templates, template will be created if it don't exist yet.
     *
     * @throws IOException if any writer-related operation fail.
     */
    void save(Writer writer, Object object) throws IOException;

    /**
     * Save given {@link ConfigurationSection} to given {@link Writer}.<br>
     *
     * @param writer  {@link Writer} to use.
     * @param section {@link ConfigurationSection} to save.
     *
     * @throws IOException if any writer-related operation fail.
     */
    void saveToSection(Writer writer, ConfigurationSection section) throws IOException;
}
