/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.aerogear.security.rest.service;

import org.jboss.aerogear.security.auth.AuthenticationManager;
import org.jboss.aerogear.security.auth.LoggedUser;
import org.jboss.aerogear.security.auth.Secret;
import org.jboss.aerogear.security.auth.Token;
import org.jboss.aerogear.security.authz.IdentityManagement;
import org.jboss.aerogear.security.model.AeroGearUser;
import org.jboss.aerogear.security.otp.Totp;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Stateless
@TransactionAttribute
public class AuthenticationServiceImpl implements AuthenticationService {

    //TODO will be moved to SecurityServletFilter
    private static final String AUTH_TOKEN = "Auth-Token";
    private static final String AUTH_SECRET = "Auth-Secret";

    //TODO it must be replaced by some admin page
    public static final String DEFAULT_ROLE = "admin";

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private IdentityManagement configuration;

    @Inject
    @Token
    private Instance<String> token;

    @Inject
    @Secret
    private Instance<String> secret;

    @Inject
    @LoggedUser
    private Instance<String> loggedUser;

    private static final Logger LOGGER = Logger.getLogger(AuthenticationServiceImpl.class.getName());

    public Response login(final AeroGearUser aeroGearUser) {

        authenticationManager.login(aeroGearUser);
        return Response.ok(aeroGearUser).header(AUTH_TOKEN, token.get()).build();
    }

    public Response otpLogin(final AeroGearUser aeroGearUser) {

        //TODO include some validation here
        authenticationManager.login(aeroGearUser);
        return Response.ok(aeroGearUser)
                .header(AUTH_TOKEN, token.get())
                .header(AUTH_SECRET, secret.get()).build();
    }

    //TODO
    public void register(AeroGearUser aeroGearUser) {
        configuration.grant(DEFAULT_ROLE).to(aeroGearUser);
        authenticationManager.login(aeroGearUser);
    }

    public void logout() {
        authenticationManager.logout();
    }

    //TODO token will be provided by servlet filters
    public Response getSecret() {
        Totp totp = new Totp(secret.get());
        AeroGearUser userInfo = new AeroGearUser();
        userInfo.setUri(totp.uri(loggedUser.get()));

        return Response.ok(userInfo).build();
    }

    public Response getUserInfo() {
        Totp totp = new Totp(secret.get());
        AeroGearUser userInfo = new AeroGearUser();
        userInfo.setUri(totp.uri(loggedUser.get()));

        return Response.ok(userInfo).build();
    }
}