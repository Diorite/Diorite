package org.diorite.impl.pipelines.system;

import java.net.Proxy;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.pipelines.system.CoreInitPipeline.InitData;

import joptsimple.OptionSet;

public class CoreInitPipeline extends SystemPipeline<InitData>
{
    @Override
    public void reset_()
    {

    }

    public static class InitData
    {
        public final OptionSet options;
        public final Proxy     proxy;
        public final boolean   isClient;

        public InitData(final OptionSet options, final Proxy proxy, final boolean isClient)
        {
            this.options = options;
            this.proxy = proxy;
            this.isClient = isClient;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("options", this.options).append("proxy", this.proxy).append("isClient", this.isClient).toString();
        }
    }
}
