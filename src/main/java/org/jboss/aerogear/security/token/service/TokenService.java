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
package org.jboss.aerogear.security.token.service;

/**
 * <i>IdentityManagement</i> allows to manage a Token.
 */
public interface TokenService {

    /**
     * Destroys the Token.
     * <pre>
     * {@code
     * try {
     *    Token token = em.find(Token.class, id);
     *    em.remove(token);
     *    em.flush();
     * } catch (Exception e) {
     *    e.printStackTrace();
     * }
     * }
     * </pre>
     * @param id id of the Token used to retrieve the Token.
     */
    void destroy(String id);

    /**
     * Checks the validity of the Token based on the identifier passed as parameter.
     * Usually, an id will be passed in order to be able to retrieve the Token.
     * <pre>
     * {@code
     * Token token = null;
     *
     *  try {
     *      token = em.createQuery("SELECT t FROM Token t WHERE t.id = :id", Token.class)
     *      .setParameter("id", id)
     *      .getSingleResult();
     *
     *  } catch (NoResultException e) {
     *      //Do nothing because we don't want to give any clue to an attacker
     *  }
     *  return (token != null && !expirationTime.isExpired(token.getExpiration()));
     *}
     * </pre>
     * @param id is the token provided for password reset.
     * @return
     */
    boolean isValid(String id);

    /**
     * Method to generate the Token. Is recommended to the implementer to make use of AeroGear Crypto
     * which already implements cryptographic algorithms for password reset.
     * For example to generate a secure token:
     *
     * <pre>
     * {@code
     *
     * if (userExists(email)) {
     *
     *    String secret = Configuration.getSecret();
     *
     *    //Secret is the secret_key included into config.properties file
     *    Hmac hmac = new Hmac(secret);
     *    //Persists the temporary token
     *    token = save(hmac.digest());
     *    return token;
     * }
     * }
     * </pre>
     * @param email to be checked. E-mail must be provided to validate if it exists into the database
     * @return a String representing the token or an identifier of the Token.
     */
    String generate(String email);
}

