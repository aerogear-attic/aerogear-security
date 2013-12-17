package org.jboss.aerogear.security.token.util;

import javax.servlet.FilterConfig;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * Utility class for the Token handling containing configuration locations.
 * Please note that the Class expects in META-INF/config.properties that contains your secret.
 *
 */
public class Configuration {


    private static final String CONFIG_FILE = "META-INF/config.properties";
    private static final Properties props;

    private static String applicationUrl;
    private static String redirectPage;

    static {
        props = new Properties();
        InputStream in = Configuration.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
        try {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the {org.jboss.aerogear.security.web.filter.PasswordHandler} {FilterConfig} configuration.
     *
     * @param config
     */
    public static void loadFilterConfig(FilterConfig config) {
        Configuration.applicationUrl = config.getInitParameter("url");
        Configuration.redirectPage = config.getInitParameter("redirect-page");
    }

    /**
     * Returns the an uri based on the Token ID and the base URL provided in the Web Descriptor
     *
     * @param id
     * @return
     */
    public static String uri(String id) {

        try {
            return String.format(applicationUrl + "%s%s", "reset?id=", URLEncoder.encode(id, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    /**
     *
     * @return a String representing the secret
     */
    public static String getSecret() {
        return props.getProperty("config.secret");
    }

    /**
     * Returns the page to which the intercepted request has to be redirected if the Token is valid
     * @return
     */
    public static String getRedirectPage() {
        return redirectPage;
    }


}
