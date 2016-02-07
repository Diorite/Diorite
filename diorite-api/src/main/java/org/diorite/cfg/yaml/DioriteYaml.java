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

package org.diorite.cfg.yaml;

import org.yaml.snakeyaml.Yaml;

/**
 * Diorite extension of {@link Yaml} with more public methods.
 */
public class DioriteYaml extends Yaml
{
    /**
     * Create Yaml instance. It is safe to create a few instances and use them
     * in different Threads.
     */
    public DioriteYaml()
    {
        this(new DioriteYamlConstructor(), new DioriteYamlRepresenter(), new DioriteYamlDumperOptions(), new DioriteYamlResolver());
    }

    /**
     * Create Yaml instance.
     *
     * @param dumperOptions DioriteYamlDumperOptions to configure outgoing objects
     */
    public DioriteYaml(final DioriteYamlDumperOptions dumperOptions)
    {
        this(new DioriteYamlConstructor(), new DioriteYamlRepresenter(), dumperOptions);
    }

    /**
     * Create Yaml instance. It is safe to create a few instances and use them
     * in different Threads.
     *
     * @param representer DioriteYamlRepresenter to emit outgoing objects
     */
    public DioriteYaml(final DioriteYamlRepresenter representer)
    {
        this(new DioriteYamlConstructor(), representer);
    }

    /**
     * Create Yaml instance. It is safe to create a few instances and use them
     * in different Threads.
     *
     * @param constructor BaseDioriteYamlConstructor to construct incoming documents
     */
    public DioriteYaml(final DioriteYamlConstructor constructor)
    {
        this(constructor, new DioriteYamlRepresenter());
    }

    /**
     * Create Yaml instance. It is safe to create a few instances and use them
     * in different Threads.
     *
     * @param constructor BaseDioriteYamlConstructor to construct incoming documents
     * @param representer DioriteYamlRepresenter to emit outgoing objects
     */
    public DioriteYaml(final DioriteYamlConstructor constructor, final DioriteYamlRepresenter representer)
    {
        this(constructor, representer, new DioriteYamlDumperOptions());
    }

    /**
     * Create Yaml instance. It is safe to create a few instances and use them
     * in different Threads.
     *
     * @param representer   DioriteYamlRepresenter to emit outgoing objects
     * @param dumperOptions DioriteYamlDumperOptions to configure outgoing objects
     */
    public DioriteYaml(final DioriteYamlRepresenter representer, final DioriteYamlDumperOptions dumperOptions)
    {
        this(new DioriteYamlConstructor(), representer, dumperOptions, new DioriteYamlResolver());
    }

    /**
     * Create Yaml instance. It is safe to create a few instances and use them
     * in different Threads.
     *
     * @param constructor   BaseDioriteYamlConstructor to construct incoming documents
     * @param representer   DioriteYamlRepresenter to emit outgoing objects
     * @param dumperOptions DioriteYamlDumperOptions to configure outgoing objects
     */
    public DioriteYaml(final DioriteYamlConstructor constructor, final DioriteYamlRepresenter representer, final DioriteYamlDumperOptions dumperOptions)
    {
        this(constructor, representer, dumperOptions, new DioriteYamlResolver());
    }

    /**
     * Create Yaml instance. It is safe to create a few instances and use them
     * in different Threads.
     *
     * @param constructor   BaseConstructor to construct incoming documents
     * @param representer   Representer to emit outgoing objects
     * @param dumperOptions DumperOptions to configure outgoing objects
     * @param resolver      Resolver to detect implicit type
     */
    public DioriteYaml(final DioriteYamlConstructor constructor, final DioriteYamlRepresenter representer, final DioriteYamlDumperOptions dumperOptions, final DioriteYamlResolver resolver)
    {
        super(constructor, representer, dumperOptions, resolver);
    }

    /**
     * Returns dumper options of this yaml instance.
     *
     * @return dumper options of this yaml instance.
     */
    public DioriteYamlDumperOptions getDumperOptions()
    {
        return (DioriteYamlDumperOptions) this.dumperOptions;
    }

    /**
     * Set dumper options of this yaml instance.
     *
     * @param dumperOptions new dumper options.
     */
    public void setDumperOptions(final DioriteYamlDumperOptions dumperOptions)
    {
        this.dumperOptions = dumperOptions;
    }

    /**
     * Returns object representer of this yaml instance.
     *
     * @return object representer of this yaml instance.
     */
    public DioriteYamlRepresenter getRepresenter()
    {
        return (DioriteYamlRepresenter) this.representer;
    }

    /**
     * Set object representer of this yaml instance.
     *
     * @param representer new object representer.
     */
    public void setRepresenter(final DioriteYamlRepresenter representer)
    {
        this.representer = representer;
    }

    /**
     * Returns constructor handler of this yaml instance.
     *
     * @return constructor handler of this yaml instance.
     */
    public DioriteYamlConstructor getConstructor()
    {
        return (DioriteYamlConstructor) this.constructor;
    }

    /**
     * Set constructor handler of this yaml instance.
     *
     * @param constructor new constructor handler.
     */
    public void setConstructor(final DioriteYamlConstructor constructor)
    {
        this.constructor = constructor;
    }

    /**
     * Returns object resolver of this yaml instance.
     *
     * @return object resolver of this yaml instance.
     */
    public DioriteYamlResolver getResolver()
    {
        return (DioriteYamlResolver) this.resolver;
    }
}
