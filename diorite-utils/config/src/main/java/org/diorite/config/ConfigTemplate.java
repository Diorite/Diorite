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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

import org.diorite.config.exceptions.ConfigLoadException;

/**
 * Represents config file template.
 *
 * @param <T>
 *         type of config.
 */
public interface ConfigTemplate<T extends Config>
{
    /**
     * Char used to separate nodes.
     */
    char SEPARATOR = '.';

    /**
     * Returns config type class.
     *
     * @return config type class.
     */
    Class<T> getConfigType();

    /**
     * Returns name of config, config class name if not provided. <br/>
     *
     * @return name of config, config class name if not provided.
     */
    String getName();

    /**
     * Select name of config.
     *
     * @param name
     *         new config name.
     */
    void setName(String name);

    /**
     * Returns encoder used by this config file.
     *
     * @return encoder used by this config file.
     */
    CharsetEncoder getDefaultEncoder();

    /**
     * Set encoder for this config file.
     *
     * @param encoder
     *         new encoder.
     */
    void setDefaultEncoder(CharsetEncoder encoder);

    /**
     * Returns decoder used by this config file.
     *
     * @return decoder used by this config file.
     */
    CharsetDecoder getDefaultDecoder();

    /**
     * Set decoder for this config file.
     *
     * @param decoder
     *         new decoder.
     */
    void setDefaultDecoder(CharsetDecoder decoder);

    /**
     * Select charset for loading this config file.
     *
     * @param charset
     *         charset to use.
     */
    default void setEncoding(Charset charset)
    {
        this.setDefaultEncoder(charset.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT));
        this.setDefaultDecoder(charset.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT));
    }

    /**
     * Load config from given file.
     *
     * @param file
     *         file to use.
     */
    default T load(File file)
    {
        return this.load(this.createInputStreamReader(file));
    }

    /**
     * Load config from stream.
     * Stream isn't automatically closed here!
     *
     * @param inputStream
     *         stream to use.
     */
    default T load(InputStream inputStream)
    {
        return this.load(new InputStreamReader(inputStream, this.getDefaultDecoder()));
    }

    /**
     * Load config from reader.
     * Reader isn't automatically closed here!
     *
     * @param reader
     *         reader to use.
     */
    T load(Reader reader);

    private InputStreamReader createInputStreamReader(File file)
    {
        try
        {
            if (! file.exists())
            {
                File absoluteFile = file.getAbsoluteFile();
                absoluteFile.getParentFile().mkdirs();
                absoluteFile.createNewFile();
            }
        }
        catch (IOException e)
        {
            throw new ConfigLoadException(this, file, "can't create a file.", e);
        }
        try
        {
            FileInputStream fileInputStream = new FileInputStream(file);
            return new InputStreamReader(fileInputStream, this.getDefaultDecoder());
        }
        catch (IOException e)
        {
            throw new ConfigLoadException(this, file, e.getMessage(), e);
        }
    }
}
