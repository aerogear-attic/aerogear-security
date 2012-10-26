package org.jboss.aerogear.security.impl.auth;

import org.jboss.aerogear.security.util.Hex;
import org.picketbox.cdi.PicketBoxIdentity;
import org.picketbox.core.util.Base32;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

@SessionScoped
public class AuthenticationKeyProvider implements Serializable {

    @Inject
    private IdentityManager identityManager;

    @Inject
    private PicketBoxIdentity identity;

    private String secret;
    private User idmuser;

    @PostConstruct
    public void init(){
        User user = identity.getUserContext().getUser();
        idmuser = identityManager.getUser(user.getKey());

        secret = idmuser.getAttribute("serial");
    }

    public String getSecret() {

        if (secret == null) {
            secret = AuthenticationSecretKeyCode.create();
            idmuser.setAttribute("serial", secret);
        }
        return secret;
    }

    public String getToken() {
        String token = null;
        if (identity.isLoggedIn())
            token = identity.getUserContext().getSession().getId().getId().toString();

        return token;
    }

    public String getB32(){
        return Base32.encode(Hex.hexToAscii(getSecret()).getBytes());
    }
}
