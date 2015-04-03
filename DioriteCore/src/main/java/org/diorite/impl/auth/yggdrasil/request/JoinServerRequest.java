package org.diorite.impl.auth.yggdrasil.request;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JoinServerRequest
{
    public final String accessToken;
    public final UUID   selectedProfile;
    public final String serverId;

    public JoinServerRequest(final String accessToken, final UUID selectedProfile, final String serverId)
    {
        this.accessToken = accessToken;
        this.selectedProfile = selectedProfile;
        this.serverId = serverId;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("accessToken", this.accessToken).append("selectedProfile", this.selectedProfile).append("serverId", this.serverId).toString();
    }
}
