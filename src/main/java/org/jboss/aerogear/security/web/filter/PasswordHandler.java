/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.security.web.filter;


import org.jboss.aerogear.security.token.service.TokenService;
import org.jboss.aerogear.security.token.util.Configuration;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A filter that intercepts requests that matches the url-mapping defined in the web descriptor.
 * This filter can be used for "token-based" flows, i.e like password reset link creation
 * The filter does 2 checks :
 *  - Checks if the token is valid by using the tokenId from the parameters
 *  - Checks if the incoming method is a GET
 * If these 2 checks pass, then the Filter redirects the Request to the URL provided by the Configuration object.
 * Be sure to configure correctly your filter in the web.xml :
 * <pre>
 * {@code
 * <filter>
 *   <filter-name>PasswordHandler</filter-name>
 *   <filter-class>org.jboss.aerogear.security.web.filter.PasswordHandler</filter-class>
 *   <init-param>
 *     <param-name>url</param-name>
 *     <param-value>http://myhost.com/</param-value>
 *   </init-param>
 *   <init-param>
 *     <param-name>redirect-page</param-name>
 *     <param-value>reset/update.html</param-value>
 *   </init-param>
 * </filter>
 * <filter-mapping>
 *   <filter-name>PasswordHandler</filter-name>
 *   <url-pattern>/reset/*</url-pattern>
 *   <url-pattern>/forgot/*</url-pattern>
 * </filter-mapping>
 *}
 * </pre>
 *
 */
public class PasswordHandler implements Filter {

    public static final String TOKEN_ID_PARAM = "id";

    @Inject
    private TokenService tokenService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        Configuration.loadFilterConfig(config);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String tokenId = httpServletRequest.getParameter(TOKEN_ID_PARAM);

        String method = httpServletRequest.getMethod();

        if (tokenService.isValid(tokenId) && method.equalsIgnoreCase("GET")) {
            redirectPage(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
            return;
        }
    }

    private void redirectPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        httpServletRequest.getRequestDispatcher(Configuration.getRedirectPage()).forward(httpServletRequest, httpServletResponse);

    }

    @Override
    public void destroy() {
    }

}

