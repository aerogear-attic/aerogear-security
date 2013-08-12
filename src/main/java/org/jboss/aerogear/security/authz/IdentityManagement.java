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
package org.jboss.aerogear.security.authz;

import java.util.List;
import java.util.Set;

/**
 * <i>IdentityManagement</i> allows to assign a set of roles to User on Identity Manager provider
 */
public interface IdentityManagement<T> {

    /**
     * This method allows to specify which <i>roles</i> must be assigned to User
     *
     * @param roles A list of roles.
     * @return {@link GrantMethods} is a builder which a allows to apply a list of roles to the specified User.
     */
    GrantMethods grant(String... roles);

    /**
     * This method allows to revoke which <i>roles</i> must be revoked to User
     *
     * @param roles A list of roles.
     * @return {@link GrantMethods} is a builder which a allows to apply a list of roles to the specified User.
     */

    GrantMethods revoke(String... roles);

    /**
     * Reset user' password
     * @param user User credential
     * @param currentPassword current password already registered
     * @param newPassword new password
     * @throws Exception
     */
    void reset(T user, String currentPassword, String newPassword);

    /**
     * Find an User by the username specified
     *
     * @param username
     * @return <T> where the generic type represents a User into the system
     */
    T findByUsername(String username) throws RuntimeException;

    /**
     * Find an User by the id specified
     *
     * @param id
     * @return <T> where the generic type represents a User into the system
     */
    T findById(long id) throws RuntimeException;

    /**
     * Remove an User
     *
     * @param username
     */
    void remove(String username);

    /**
     * Get All the users
     *
     * @param roleName
     * @return Users by roles
     */
    List<T> findAllByRole(String roleName);

    /**
     * This method creates a new User
     *
     * @param user where the generic type represents a User into the system
     * @param password input provided by User
     */
    void create(T user, String password);

    /**
     * <i>GrantMethods</i> is a builder to apply roles to User
     */
    static interface GrantMethods<T> {
        /**
         * This method applies roles specified on {@link IdentityManagement#grant(String...)}
         *
         * @param username represents a simple user's implementation to hold credentials.
         */
        void to(String username);

        /**
         * This method revokes roles specified on {@link IdentityManagement#revoke(String...)}
         *
         * @param user represents a simple user's implementation to hold credentials.
         */
        public void to(T user);

        /**
         * This method specifies which roles will be applied to User
         *
         * @param roles Array of roles
         * @return builder implementation
         */
        GrantMethods<T> roles(String[] roles);

        /**
         * This method allows to revoke which <i>roles</i> must be revoked to User
         * @param roles List of roles to be revoked
         */
        GrantMethods<T> revoke(String[] roles);
    }

    /**
     * Represents the generated TOTP secret for the current User logged in.
     * @return shared secret
     */
    String getSecret();

    /**
     * Retrieve the logged user name
     * @return user name
     */
    String getLogin();

    /**
     * Check if a logged in user has the roles provided
     *
     * @param roles roles to be checked
     * @return returns true if the current logged in has roles at the IDM, false otherwise
     */
    boolean hasRoles(Set<String> roles);
}
