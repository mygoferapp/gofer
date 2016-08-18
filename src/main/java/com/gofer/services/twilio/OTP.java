package com.gofer.services.twilio;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("gofer/OTP")
public interface OTP {
	@GET
	public String sendOTP(@QueryParam("number") String toNumber);

}
