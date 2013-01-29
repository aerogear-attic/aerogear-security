/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors
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

package org.jboss.aerogear.security.auth;

import org.jboss.aerogear.security.model.AeroGearUser;

/**
 * A <i>AuthenticationManager</i> executes the basic authentication operations for {@link AeroGearUser}
 */
public interface AuthenticationManager {

    /**
     * Logs in the specified {@link AeroGearUser}.
     * @param aeroGearUser represents a simple implementation that holds user's credentials.
     * @throws org.jboss.aerogear.security.exception.AeroGearSecurityException on login failure.
     */
    void login(AeroGearUser aeroGearUser);

    /**
     * Logs out the specified {@link AeroGearUser} from the system.
     * @throws org.jboss.aerogear.security.exception.AeroGearSecurityException on logout failure.
     */
    void logout();
    
    /**
     * Determines if the is currently a Subject logged in.
     * @return {@code true} if there is currently a logged in subject.
     */
    boolean isSubjectLoggedIn();
}
