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
package org.jboss.aerogear.security.exception;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

public enum HttpStatus {

    AUTHENTICATION_FAILED("User authentication failed", UNAUTHORIZED.getStatusCode()),
    PASSWORD_RESET_FAILED("Password reset failed", UNAUTHORIZED.getStatusCode()),
    CREDENTIAL_NOT_FOUND("Credentials could not be found", FORBIDDEN.getStatusCode()),
    ALREADY_LOGGED_IN("Credentials already logged in", NOT_ACCEPTABLE.getStatusCode());

    private String message;
    private int status;

    HttpStatus(String message) {
        this.message = message;
    }

    HttpStatus(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public int getCode() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("{message : %s }", message);
    }

    public String getMessage() {
        return message;
    }
}
