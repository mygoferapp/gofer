package com.gofer.services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;


@Path("/status")
public class ServerStatus {

    @GET
    @Path("/")
    @Produces("text/json")
    public ServerStatusResponse ping(@PathParam("input") String input, @Context HttpServletRequest req) {
        String remoteHost = req.getServerName();
        int remotePort = req.getServerPort();
        ServerStatusResponse helloResponse = new ServerStatusResponse();
        helloResponse.setStatus("running");
        String host = "http://" + remoteHost + ":" + remotePort;
        helloResponse.setUberUrl(host + "/gofer/servlet");
        helloResponse.setUberRedirectUrl(host + "/gofer/servlet/ride/redirect");

        return helloResponse;
    }
}

