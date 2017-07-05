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

package org.diorite.script;

import javax.annotation.Nullable;

/**
 * Represent diorite script manager, scripts are used by diorite for many purposes, like messages, default command arguments, and much more.
 */
public interface ScriptManager
{
    String     COMMON_GROOVY_ENGINE_KEY = "diorite-groovy-engine";
    FactoryKey GROOVY_FACTORY_KEY       = FactoryKey.implementedBy("diorite", "groovy");

    /**
     * Returns common groovy engine, used for message system, default command system, and might be used to create plugins.
     *
     * @return common groovy engine, used for message system, default command system, and might be used to create plugins.
     */
    default ScriptEngine getCommonGroovyEngine()
    {
        ScriptEngine groovyEngine = this.getGroovyEngine(COMMON_GROOVY_ENGINE_KEY);
        if (groovyEngine == null)
        {
            throw new RuntimeException("common groovy engine isn't initialized!");
        }
        return groovyEngine;
    }

    /**
     * Returns groovy engine that was created with given key.
     *
     * @return groovy engine instance or null.
     */
    @Nullable
    default ScriptEngine getGroovyEngine(String key)
    {
        ScriptEngineFactory factoryFor = this.getFactoryFor(GROOVY_FACTORY_KEY);
        ScriptEngine scriptEngine = factoryFor.create();
        this.registerScriptEngine(key, scriptEngine);
        return scriptEngine;
    }

    /**
     * Create new ScriptEngine with given language and key.
     *
     * @param language
     *         language to use
     * @param key
     *         engine key used later to access created engine.
     *
     * @return
     *
     * @exception IllegalArgumentException
     */
    default ScriptEngine createEngine(String language, String key) throws MissingLanguageException
    {
        ScriptEngineFactory factoryFor = this.getFactoryFor(FactoryKey.any(language));
        ScriptEngine scriptEngine = factoryFor.create();
        this.registerScriptEngine(key, scriptEngine);
        return scriptEngine;
    }

    /**
     * Register new script engine with given name/key.
     *
     * @param key
     *         name of engine.
     * @param engine
     *         engine implementation to register.
     */
    void registerScriptEngine(String key, ScriptEngine engine);

    /**
     * Register new script engine factory.
     *
     * @param factory
     *         factory to register.
     */
    void registerScriptEngineFactory(ScriptEngineFactory factory);

    /**
     * Returns factory for given language, if more than one plugin is providing factory for this language, then highest priority will be returned, in case of
     * priority collision, first registered factory will be returned.
     *
     * @param language
     *         language of given factory.
     *
     * @return factory for given language.
     *
     * @exception MissingLanguageException
     *         if there isn't any factory for given language.
     */
    ScriptEngineFactory getFactoryFor(String language) throws MissingLanguageException;
    /**
     * Returns factory for given factory key.
     *
     * @param factoryKey
     *         key of given factory.
     *
     * @return factory for given key.
     *
     * @exception MissingLanguageException
     *         if there isn't any factory for given key.
     */
    ScriptEngineFactory getFactoryFor(FactoryKey factoryKey) throws MissingLanguageException;
}
