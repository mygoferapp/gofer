package com.gofer.services.uber;

/**
 * Created by rjaraja on 8/11/16.
 */


import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.rides.auth.OAuth2Credentials;
import com.uber.sdk.rides.client.SessionConfiguration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

/**
 * Sample server. Before starting, make sure to add the {@code REDIRECT_URI} to your application
 * on developer.uber.com and fill in {@code resources/secrets.properties} with your client
 * ID and secret.
 */
public class UberBuilder {

    public static final String USER_SESSION_ID = "userSessionId";

    // Set your server's port and callback URL.
    private static final int PORT = 8080;
    private static final String CALLBACK_URL = "snaphelp/servlet/ride/redirect";

    // IMPORTANT: Before starting the server, make sure to add this redirect URI to your
    // application at developers.uber.com.
    private static final String REDIRECT_URI = "http://localhost:8080/snaphelp/servlet/ride/redirect";


    /**
     * Creates an {@link OAuth2Credentials} object that can be used by any of the servlets.
     * <p>
     * Throws an {@throws IOException} when no client ID or secret found in secrets.properties
     */
    static OAuth2Credentials createOAuth2Credentials(SessionConfiguration config) throws IOException {


        return new OAuth2Credentials.Builder()
                .setCredentialDataStoreFactory(MemoryDataStoreFactory.getDefaultInstance())
                .setRedirectUri(config.getRedirectUri())
                .setScopes(config.getScopes())
                .setClientSecrets(config.getClientId(), config.getClientSecret())
                .build();
    }

    static SessionConfiguration createSessionConfiguration() throws IOException {
        // Load the client ID and secret from a secrets properties file.
        Properties secrets = loadSecretProperties();

        String clientId = secrets.getProperty("clientId");
        String clientSecret = secrets.getProperty("clientSecret");

        if (clientId.equals("INSERT_CLIENT_ID_HERE") || clientSecret.equals("INSERT_CLIENT_SECRET_HERE")) {
            throw new IllegalArgumentException(
                    "Please enter your client ID and secret in the resoures/secrets.properties file.");
        }
        return new SessionConfiguration.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(REDIRECT_URI)
                .setScopes(Collections.singletonList(Scope.PROFILE))
                .build();
    }

    /**
     * Loads the application's secrets.
     */
    private static Properties loadSecretProperties() throws IOException {
        Properties properties = new Properties();
        InputStream propertiesStream = UberBuilder.class.getClassLoader().getResourceAsStream("secrets.properties");
        if (propertiesStream == null) {
            // Fallback to file access in the case of running from certain IDEs.
            File buildPropertiesFile = new File("src/main/resources/secrets.properties");
            if (buildPropertiesFile.exists()) {
                properties.load(new FileReader(buildPropertiesFile));
            } else {
                buildPropertiesFile = new File("smsserver/src/main/resources/secrets.properties");
                if (buildPropertiesFile.exists()) {
                    properties.load(new FileReader(buildPropertiesFile));
                } else {
                    throw new IllegalStateException("Could not find secrets.properties");
                }
            }
        } else {
            properties.load(propertiesStream);
        }
        return properties;
    }
}