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

import javax.annotation.Nullable;
import javax.script.ScriptEngine;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;

import org.diorite.commons.arrays.DioriteArrayUtils;
import org.diorite.commons.classes.DynamicClassLoader;
import org.diorite.config.impl.ConfigImplementationProvider;
import org.diorite.config.impl.ConfigTemplateImpl;
import org.diorite.config.impl.actions.ActionsRegistry;
import org.diorite.config.impl.groovy.GroovyImplementationProvider;

import groovy.lang.GroovyClassLoader;

/**
 * Root class of library.
 */
public final class ConfigManager
{
    private ConfigManager()
    {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        ImportCustomizer importCustomizer = new ImportCustomizer();
        importCustomizer.addStarImports("org.diorite.config", "org.diorite.config.serialization", "org.diorite", "org.diorite.config.exceptions");
        compilerConfiguration.addCompilationCustomizers(importCustomizer);
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader(this.getClass().getClassLoader(), compilerConfiguration);
        this.groovy = new GroovyScriptEngineImpl(groovyClassLoader);
        DynamicClassLoader classLoader = DynamicClassLoader.injectAsSystemClassLoader();
        classLoader.addClassLoader(groovyClassLoader, 0);
        this.setImplementationProvider(GroovyImplementationProvider.getInstance());
    }

    @Nullable
    private static ConfigManager          configManager;
    private        GroovyScriptEngineImpl groovy;

    /**
     * Returns config manager instance.
     *
     * @return config manager instance.
     */
    public static ConfigManager get()
    {
        if (configManager == null)
        {
            configManager = new ConfigManager();
        }
        return configManager;
    }

    public ScriptEngine getGroovy()
    {
        return this.groovy;
    }

    public GroovyClassLoader getGroovyClassLoader()
    {
        return this.groovy.getClassLoader();
    }

    private final Map<List<Object>, ConfigTemplate<?>> configs = new ConcurrentHashMap<>(20);

    private ConfigImplementationProvider implementationProvider;

    /**
     * Change config implementation provider.
     *
     * @param implementationProvider
     *         implementation provider to be used.
     */
    public void setImplementationProvider(ConfigImplementationProvider implementationProvider)
    {
        this.implementationProvider = implementationProvider;
        implementationProvider.init(this);
    }

    /**
     * Register new property action that can be used by any new created config file.
     *
     * @param action
     *         action to register.
     * @param priority
     *         priority of action.
     */
    public void registerPropertyAction(ConfigPropertyAction action, int priority)
    {
        ActionsRegistry.registerAction(action, priority);
    }

    /**
     * Get or create config file configuration for given config class.
     *
     * @param type
     *         type of config class.
     * @param <T>
     *         type of config class.
     *
     * @return config file configuration for given config class.
     */
    @SuppressWarnings("unchecked")
    public <T extends Config> ConfigTemplate<T> getConfigFile(Class<T> type)
    {
        return getConfigFileVariant(type);
    }

    /**
     * Get or create config file configuration for given config class and qualifiers, qualifiers can be used to create separate configs for different languages
     * etc.
     *
     * @param type
     *         type of config class.
     * @param variantQualifiers
     *         qualifiers of config variant, this same qualifiers in different order define different variant.
     * @param <T>
     *         type of config class.
     *
     * @return config file configuration for given config class.
     */
    @SuppressWarnings("unchecked")
    public <T extends Config> ConfigTemplate<T> getConfigFileVariant(Class<T> type, Object... variantQualifiers)
    {
        List<Object> key = Arrays.asList(DioriteArrayUtils.prepend(variantQualifiers, type));
        ConfigTemplate<T> configTemplate = (ConfigTemplate<T>) this.configs.get(key);
        if (configTemplate == null)
        {
            synchronized (this.configs)
            {
                configTemplate = (ConfigTemplate<T>) this.configs.get(key);
                if (configTemplate != null)
                {
                    return configTemplate;
                }
                configTemplate = new ConfigTemplateImpl<>(type, this.implementationProvider);
                this.configs.put(key, configTemplate);
            }
        }
        return configTemplate;
    }

    /**
     * Create instance of given config type.
     *
     * @param type
     *         type of config class.
     * @param <T>
     *         type of config class.
     *
     * @return created instance.
     */
    public static <T extends Config> T createInstance(Class<T> type)
    {
        return get().getConfigFile(type).create();
    }
}
