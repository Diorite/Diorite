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

package org.diorite.config.serialization.comments;

import javax.annotation.Nullable;
import javax.annotation.WillNotClose;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;

/**
 * Root comments entry.
 */
public interface DocumentComments extends CommentsNode
{
    /**
     * Returns footer of configuration file, empty string if none.
     *
     * @return footer of configuration file, empty string if none.
     */
    String getFooter();

    /**
     * Set new footer of configuration file, use null or empty string to disable.
     *
     * @param footer
     *         new footer value.
     */
    void setFooter(@Nullable String footer);

    /**
     * Set new footer of configuration file, use null, empty string or empty array to disable.
     *
     * @param footer
     *         new footer value.
     */
    default void setFooter(@Nullable String... footer)
    {
        if ((footer == null) || (footer.length == 0))
        {
            this.setFooter(StringUtils.EMPTY);
            return;
        }
        this.setFooter(StringUtils.join(footer, '\n'));
    }

    /**
     * Returns header of configuration file, empty string if none.
     *
     * @return header of configuration file, empty string if none.
     */
    String getHeader();

    /**
     * Set new header of configuration file, use null or empty string to disable.
     *
     * @param header
     *         new header value.
     */
    void setHeader(@Nullable String header);

    /**
     * Set new header of configuration file, use null, empty string or empty array to disable.
     *
     * @param header
     *         new header value.
     */
    default void setHeader(@Nullable String... header)
    {
        if ((header == null) || (header.length == 0))
        {
            this.setHeader(StringUtils.EMPTY);
            return;
        }
        this.setHeader(StringUtils.join(header, '\n'));
    }

    /**
     * Returns itself.
     *
     * @return itself.
     */
    @Override
    default DocumentComments getRoot()
    {
        return this;
    }

    /**
     * Always returns null, as this is root node.
     *
     * @return null, as this is root node.
     */
    @Nullable
    @Override
    default CommentsNode getParent()
    {
        return null;
    }

    /**
     * Write this comments data to given output.
     *
     * @param file
     *         output to use.
     *
     * @throws IOException
     *         if any operation on file will throw it.
     */
    default void writeTo(File file) throws IOException, CommentsParserException
    {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))
        {
            this.writeTo(writer);
        }
    }

    /**
     * Write this comments data to given output. <br>
     * Output object will NOT be closed.
     *
     * @param outputStream
     *         output to use.
     *
     * @throws IOException
     *         if any operation on stream will throw it.
     */
    default void writeTo(@WillNotClose OutputStream outputStream) throws IOException, CommentsParserException
    {
        this.writeTo(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
    }

    /**
     * Write this comments data to given output. <br>
     * Output object will NOT be closed.
     *
     * @param writer
     *         output to use.
     *
     * @throws IOException
     *         if any operation on writer will throw it.
     */
    void writeTo(@WillNotClose Writer writer) throws IOException, CommentsParserException;

    /**
     * Clone all comments nodes to new document object.
     *
     * @return cloned object.
     */
    DocumentComments copy();

    /**
     * Returns always empty instance of Document Comments
     *
     * @return always empty instance of Document Comments
     */
    static DocumentComments empty()
    {
        return EmptyDocumentComments.getEmpty();
    }

    /**
     * Returns new instance of document comments.
     *
     * @return new instance of document comments.
     */
    static DocumentComments create()
    {
        return new DocumentCommentsImpl();
    }

    /**
     * Read comments from given source.
     *
     * @param file
     *         source with comments.
     *
     * @return comments as {@link DocumentComments} object instance.
     *
     * @throws IOException
     *         if any operation on file will throw it.
     */
    static DocumentComments parse(File file) throws CommentsParserException, IOException
    {
        try
        {
            return parse(new FileInputStream(file));
        }
        catch (IOException | CommentsParserException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new CommentsParserException(e);
        }
    }

    /**
     * Read comments from given source.
     *
     * @param inputStream
     *         source with comments.
     *
     * @return comments as {@link DocumentComments} object instance.
     */
    static DocumentComments parse(InputStream inputStream) throws CommentsParserException
    {
        try
        {
            return parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        }
        catch (CommentsParserException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new CommentsParserException(e);
        }
    }

    /**
     * Read comments from given source.
     *
     * @param reader
     *         source with comments.
     *
     * @return comments as {@link DocumentComments} object instance.
     */
    static DocumentComments parse(Reader reader) throws CommentsParserException
    {
        try
        {
            CommentsFileParser fileParser = new CommentsFileParser(reader);
            return fileParser.parse();
        }
        catch (CommentsParserException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new CommentsParserException(e);
        }
    }

    /**
     * Parses comments from class by scanning annotations on class, super class, interfaces, fields and methods.
     *
     * @param clazz
     *         type to scan.
     *
     * @return scanned type.
     */
    static DocumentComments parseFromAnnotations(Class<?> clazz)
    {
        return TypeExtractor.getAnnotationData(clazz);
    }
}
