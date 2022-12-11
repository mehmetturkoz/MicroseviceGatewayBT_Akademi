package com.bt_akademi.user_management.model.service;

import com.bt_akademi.user_management.model.entity.User;
import com.bt_akademi.user_management.security.jwt.JWTProvidable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

// **** 10 -> AuthenticationService
public abstract class AbstractAuthenticationService {

    @Autowired
    protected JWTProvidable providable;

    @Autowired
    protected AuthenticationManager authenticationManager;

    /*
        JSON WEB TOKEN üreten metot
        JWT -> oturum(Session) yerine geçecek.
     */
    public abstract String GenerateJWT(User signInUser);
}
