package com.isc.servlet;

/**
 * Created by rjaraja on 8/11/16.
 */

import com.google.api.client.auth.oauth2.Credential;
import com.uber.sdk.rides.auth.OAuth2Credentials;
import com.uber.sdk.rides.client.CredentialsSession;
import com.uber.sdk.rides.client.SessionConfiguration;
import com.uber.sdk.rides.client.UberRidesApi;
import com.uber.sdk.rides.client.model.UserProfile;
import com.uber.sdk.rides.client.services.RidesService;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Demonstrates how to authenticate the user and load their profile via a servlet.
 */
public class UberAuthServlet extends HttpServlet

{

    private OAuth2Credentials oAuth2Credentials;
    private Credential credential;
    private RidesService uberRidesService;

    /**
     * Clear the in memory credential and Uber API service once a call has ended.
     */
    @Override
    public void destroy() {
        uberRidesService = null;
        credential = null;

        super.destroy();
    }

    /**
     * Before each request, fetch an OAuth2 credential for the user or redirect them to the
     * OAuth2 login page instead.
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        SessionConfiguration config = UberBuilder.createSessionConfiguration();
        if (oAuth2Credentials == null) {
            oAuth2Credentials = UberBuilder.createOAuth2Credentials(config);
        }

        // Load the user from their user ID (derived from the request).
        HttpSession httpSession = req.getSession(true);
        if (httpSession.getAttribute(UberBuilder.USER_SESSION_ID) == null) {
            httpSession.setAttribute(UberBuilder.USER_SESSION_ID, new Random().nextLong());
        }
        credential = oAuth2Credentials.loadCredential(httpSession.getAttribute(UberBuilder.USER_SESSION_ID).toString());

        if (credential != null && credential.getAccessToken() != null) {

            if (uberRidesService == null) {

                CredentialsSession session = new CredentialsSession(config, credential);

//                Set up the Uber API Service once the user is authenticated.
                UberRidesApi api = UberRidesApi.with(session).build();
                uberRidesService = api.createService();
            }

            super.service(req, resp);
        } else {
            resp.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Fetch the user's profile.
        UserProfile userProfile = uberRidesService.getUserProfile().execute().body();

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().printf("Logged in as %s%n", userProfile.getEmail());
    }
}