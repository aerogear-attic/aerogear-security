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

package org.jboss.aerogear.security.rest.filter;

import org.jboss.aerogear.security.auth.Token;
import org.jboss.aerogear.security.authz.AuthorizationManager;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Token validation filter
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = "/auth/*")
public class SecurityServletFilter implements Filter {

    private static final String AUTH_TOKEN = "Auth-Token";

    @Inject
    private AuthorizationManager manager;

    @Inject
    @Token
    private Instance<String> credential;

    @Override
    public void init(FilterConfig config) throws ServletException {
        //TODO
    }

    /**
     * Validates the provided token on headers against the Security Provider
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String token = httpServletRequest.getHeader(AUTH_TOKEN);

        if (token != null && token.isEmpty() && !manager.validate(token)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //TODO
    }
}