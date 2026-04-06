package com.safeway.tech.auth.core.port;

import com.safeway.tech.auth.core.model.AuthUser;
import com.safeway.tech.auth.core.model.IssuedToken;

public interface TokenIssuerPort {

    IssuedToken issueFor(AuthUser authUser);
}
