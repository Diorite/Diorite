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

package org.diorite.chat;

import org.apache.commons.lang3.builder.Builder;

public final class ParserSettings implements Cloneable
{
    public static final ParserSettings ALL_ALLOWED;
    public static final ParserSettings SAFE;
    public static final ParserSettings SAFE_AND_CLEAN;

    static
    {
        ParserSettingsBuilder builder = new ParserSettingsBuilder();
        ALL_ALLOWED = builder.build();
        SAFE = builder.withoutLinks()
                      .withoutFileLinks()
                      .withoutCommandInvoke()
                      .withoutSelector()
                      .withoutHoverShowEntity()
                      .withoutScore().build();
        SAFE_AND_CLEAN = builder.withoutObfuscate().build();
    }

    boolean useOptimizer              = true;
    char    alternateColorChar        = '&';
    boolean alternateColorCharEnabled = true;
    boolean colorCharEnabled          = true;
    boolean underlineEnabled          = true;
    boolean italicEnabled             = true;
    boolean boldEnabled               = true;
    boolean strikethroughEnabled      = true;
    boolean obfuscateEnabled          = true;
    boolean linksEnabled              = true;
    boolean autoLinksEnabled          = true;
    boolean pageLinksEnabled          = true;
    boolean fileLinksEnabled          = true;
    boolean commandSuggestionEnabled  = true;
    boolean commandInvokeEnabled      = true;
    boolean insertionEnabled          = true;
    boolean hoverTextEnabled          = true;
    boolean hoverAchievementEnabled   = true;
    boolean hoverStatisticsEnabled    = true;
    boolean hoverShowItemEnabled      = true;
    boolean hoverShowEntityEnabled    = true;
    boolean translatableEnabled       = true;
    boolean scoreEnabled              = true;
    boolean selectorEnabled           = true;
    boolean keyBindEnabled            = true;

    public static ParserSettingsBuilder builder()
    {
        return new ParserSettingsBuilder();
    }

    @Override
    protected ParserSettings clone()
    {
        try
        {
            return (ParserSettings) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new InternalError(e);
        }
    }

    public static class ParserSettingsBuilder implements Builder<ParserSettings>
    {
        final ParserSettings settings = new ParserSettings();

        ParserSettingsBuilder() {}

        @Override
        public ParserSettings build()
        {
            return this.settings.clone();
        }

        public void setUseOptimizer(boolean useOptimizer)
        {
            this.settings.useOptimizer = useOptimizer;
        }

        public void setAlternateColorChar(char alternateColorChar)
        {
            this.settings.alternateColorChar = alternateColorChar;
        }

        public void setAlternateColorCharEnabled(boolean alternateColorCharEnabled)
        {
            this.settings.alternateColorCharEnabled = alternateColorCharEnabled;
        }

        public void setColorCharEnabled(boolean colorCharEnabled)
        {
            this.settings.colorCharEnabled = colorCharEnabled;
        }

        public void setUnderlineEnabled(boolean underlineEnabled)
        {
            this.settings.underlineEnabled = underlineEnabled;
        }

        public void setItalicEnabled(boolean italicEnabled)
        {
            this.settings.italicEnabled = italicEnabled;
        }

        public void setBoldEnabled(boolean boldEnabled)
        {
            this.settings.boldEnabled = boldEnabled;
        }

        public void setStrikethroughEnabled(boolean strikethroughEnabled)
        {
            this.settings.strikethroughEnabled = strikethroughEnabled;
        }

        public void setObfuscateEnabled(boolean obfuscateEnabled)
        {
            this.settings.obfuscateEnabled = obfuscateEnabled;
        }

        public void setLinksEnabled(boolean linksEnabled)
        {
            this.settings.linksEnabled = linksEnabled;
        }

        public void setAutoLinksEnabled(boolean autoLinksEnabled)
        {
            this.settings.autoLinksEnabled = autoLinksEnabled;
        }

        public void setPageLinksEnabled(boolean pageLinksEnabled)
        {
            this.settings.pageLinksEnabled = pageLinksEnabled;
        }

        public void setFileLinksEnabled(boolean fileLinksEnabled)
        {
            this.settings.fileLinksEnabled = fileLinksEnabled;
        }

        public void setCommandSuggestionEnabled(boolean commandSuggestionEnabled)
        {
            this.settings.commandSuggestionEnabled = commandSuggestionEnabled;
        }

        public void setCommandInvokeEnabled(boolean commandInvokeEnabled)
        {
            this.settings.commandInvokeEnabled = commandInvokeEnabled;
        }

        public void setInsertionEnabled(boolean insertionEnabled)
        {
            this.settings.insertionEnabled = insertionEnabled;
        }

        public void setHoverTextEnabled(boolean hoverTextEnabled)
        {
            this.settings.hoverTextEnabled = hoverTextEnabled;
        }

        public void setHoverAchievementEnabled(boolean hoverAchievementEnabled)
        {
            this.settings.hoverAchievementEnabled = hoverAchievementEnabled;
        }

        public void setHoverStatisticsEnabled(boolean hoverStatisticsEnabled)
        {
            this.settings.hoverStatisticsEnabled = hoverStatisticsEnabled;
        }

        public void setHoverShowItemEnabled(boolean hoverShowItemEnabled)
        {
            this.settings.hoverShowItemEnabled = hoverShowItemEnabled;
        }

        public void setHoverShowEntityEnabled(boolean hoverShowEntityEnabled)
        {
            this.settings.hoverShowEntityEnabled = hoverShowEntityEnabled;
        }

        public void setTranslatableEnabled(boolean translatableEnabled)
        {
            this.settings.translatableEnabled = translatableEnabled;
        }

        public void setScoreEnabled(boolean scoreEnabled)
        {
            this.settings.scoreEnabled = scoreEnabled;
        }

        public void setSelectorEnabled(boolean selectorEnabled)
        {
            this.settings.selectorEnabled = selectorEnabled;
        }

        public void setKeyBindEnabled(boolean keyBindEnabled)
        {
            this.settings.keyBindEnabled = keyBindEnabled;
        }

        public ParserSettingsBuilder withOptimizer()
        {
            this.settings.useOptimizer = true;
            return this;
        }

        public ParserSettingsBuilder withoutOptimizer()
        {
            this.settings.useOptimizer = false;
            return this;
        }

        public ParserSettingsBuilder withAlternateColorChar()
        {
            this.settings.alternateColorCharEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutAlternateColorChar()
        {
            this.settings.alternateColorCharEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withColorChar()
        {
            this.settings.colorCharEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutColorChar()
        {
            this.settings.colorCharEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withUnderline()
        {
            this.settings.underlineEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutUnderline()
        {
            this.settings.underlineEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withItalic()
        {
            this.settings.italicEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutItalic()
        {
            this.settings.italicEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withBold()
        {
            this.settings.boldEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutBold()
        {
            this.settings.boldEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withStrikethrough()
        {
            this.settings.strikethroughEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutStrikethrough()
        {
            this.settings.strikethroughEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withObfuscate()
        {
            this.settings.obfuscateEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutObfuscate()
        {
            this.settings.obfuscateEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withLinks()
        {
            this.settings.linksEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutLinks()
        {
            this.settings.linksEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withAutoLinks()
        {
            this.settings.autoLinksEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutAutoLinks()
        {
            this.settings.autoLinksEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withPageLinks()
        {
            this.settings.pageLinksEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutPageLinks()
        {
            this.settings.pageLinksEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withFileLinks()
        {
            this.settings.fileLinksEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutFileLinks()
        {
            this.settings.fileLinksEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withCommandSuggestion()
        {
            this.settings.commandSuggestionEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutCommandSuggestion()
        {
            this.settings.commandSuggestionEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withCommandInvoke()
        {
            this.settings.commandInvokeEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutCommandInvoke()
        {
            this.settings.commandInvokeEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withInsertion()
        {
            this.settings.insertionEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutInsertion()
        {
            this.settings.insertionEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withHoverText()
        {
            this.settings.hoverTextEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutHoverText()
        {
            this.settings.hoverTextEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withHoverAchievement()
        {
            this.settings.hoverAchievementEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutHoverAchievement()
        {
            this.settings.hoverAchievementEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withHoverStatistics()
        {
            this.settings.hoverStatisticsEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutHoverStatistics()
        {
            this.settings.hoverStatisticsEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withHoverShowItem()
        {
            this.settings.hoverShowItemEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutHoverShowItem()
        {
            this.settings.hoverShowItemEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withHoverShowEntity()
        {
            this.settings.hoverShowEntityEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutHoverShowEntity()
        {
            this.settings.hoverShowEntityEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withTranslatable()
        {
            this.settings.translatableEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutTranslatable()
        {
            this.settings.translatableEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withScore()
        {
            this.settings.scoreEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutScore()
        {
            this.settings.scoreEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withSelector()
        {
            this.settings.selectorEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutSelector()
        {
            this.settings.selectorEnabled = false;
            return this;
        }

        public ParserSettingsBuilder withKeyBind()
        {
            this.settings.keyBindEnabled = true;
            return this;
        }

        public ParserSettingsBuilder withoutKeyBind()
        {
            this.settings.keyBindEnabled = false;
            return this;
        }
    }
}
