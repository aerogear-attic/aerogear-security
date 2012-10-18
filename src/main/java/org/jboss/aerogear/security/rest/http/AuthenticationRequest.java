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
package org.jboss.aerogear.security.rest.http;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jboss.aerogear.security.model.AeroGearUser;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@JsonSerialize
public class AuthenticationRequest extends AeroGearUser implements Serializable {

    private static final long serialVersionUID = 2637023097272776078L;

    private String id;
    private String password;
    private String otp;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the One Time Password Value
     *
     * @return
     */
    public String getOtp() {
        return otp;
    }

    /**
     * Set the One Time Password Value
     *
     * @param otp
     */
    public void setOtp(String otp) {
        this.otp = otp;
    }
}