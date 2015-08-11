package org.diorite.impl.auth.properties;

import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProfileTexture
{
    private final String              url;
    private final Map<String, String> metadata;

    public ProfileTexture(final String url, final Map<String, String> metadata)
    {
        this.url = url;
        this.metadata = metadata;
    }

    public String getUrl()
    {
        return this.url;
    }

    public String getMetadata(final String key)
    {
        if (this.metadata == null)
        {
            return null;
        }
        return this.metadata.get(key);
    }

    public String getHash()
    {
        return FilenameUtils.getBaseName(this.url);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("url", this.url).append("metadata", this.metadata).toString();
    }
}
