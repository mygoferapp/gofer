package com.gofer.services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.gofer.services.uber.HelloResponse;


@Path("/hello")
public class HelloWorld {

    @GET
    @Path("/echo/{input}")
    @Produces("text/json")
    public HelloResponse ping(@PathParam("input") String input, @Context HttpServletRequest req) {
        String remoteHost = req.getServerName();
        int remotePort = req.getServerPort();
        HelloResponse helloResponse = new HelloResponse();
        helloResponse.setStatus("running");
        String host = "http://" + remoteHost + ":" + remotePort;
        helloResponse.setUberUrl(host + "/snaphelp/servlet");
        helloResponse.setUberRedirectUrl(host + "/snaphelp/servlet/ride/redirect");

        return helloResponse;
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/jsonBean")
    public Response modifyJson(JsonBean input) {
        input.setVal2(input.getVal1());
        return Response.ok().entity(input).build();
    }
}

