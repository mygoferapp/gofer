package com.gofer.services.uber;


import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.uber.sdk.rides.auth.OAuth2Credentials;
import com.uber.sdk.rides.client.SessionConfiguration;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Demonstrates how to make oauth callbacks via a servlet.
 */
public class OAuth2CallbackServlet extends HttpServlet {

    private OAuth2Credentials oAuth2Credentials;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (oAuth2Credentials == null) {
            SessionConfiguration config = UberBuilder.createSessionConfiguration();
            oAuth2Credentials = UberBuilder.createOAuth2Credentials(config);
        }

        HttpSession httpSession = req.getSession(true);
        if (httpSession.getAttribute(UberBuilder.USER_SESSION_ID) == null) {
            httpSession.setAttribute(UberBuilder.USER_SESSION_ID, new Random().nextLong());
        }
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = req.getRequestURL().append('?').append(req.getQueryString()).toString();
        AuthorizationCodeResponseUrl authorizationCodeResponseUrl =
                new AuthorizationCodeResponseUrl(requestUrl);

        if (authorizationCodeResponseUrl.getError() != null) {
            throw new IOException("Received error: " + authorizationCodeResponseUrl.getError());
        } else {
            // Authenticate the user and store their credential with their user ID (derived from
            // the request).
            HttpSession httpSession = req.getSession(true);
            if (httpSession.getAttribute(UberBuilder.USER_SESSION_ID) == null) {
                httpSession.setAttribute(UberBuilder.USER_SESSION_ID, new Random().nextLong());
            }
            String authorizationCode = authorizationCodeResponseUrl.getCode();
            oAuth2Credentials.authenticate(authorizationCode, httpSession.getAttribute(UberBuilder.USER_SESSION_ID).toString());
        }
        resp.sendRedirect("/");
    }
}