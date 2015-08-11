package org.diorite.impl.auth;

import org.diorite.impl.auth.exceptions.AuthenticationException;
import org.diorite.impl.auth.exceptions.AuthenticationUnavailableException;

public interface SessionService
{
    void joinServer(GameProfile gameProfile, final String authenticationToken, final String serverId) throws AuthenticationException;

    GameProfile hasJoinedServer(GameProfile gameProfile, String serverID) throws AuthenticationUnavailableException;
}
