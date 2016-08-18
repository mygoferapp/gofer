package com.gofer.services.twilio;

import java.util.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.*;
import com.twilio.sdk.resource.factory.*;
import com.twilio.sdk.resource.instance.*;

@Path("gofer/OTP")
public class TwilioOTPSender implements OTP {

	final String AUTH_TOKEN = "a9baad74ef5e3c3c6962bc013350f62e";
	final String ACCOUNT_SID = "AC1537bc98c6d75c537f1f1d8a2772eb60";
	final String TWILIO_NUMBER = "6506145368";
	final String OTP_MESSAGE = "Welcome To Gofer";

	@Override
	@GET
	public String sendOTP(@QueryParam("number") String toNumber) {

		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

		// Build the parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("To", toNumber));
		params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
		params.add(new BasicNameValuePair("Body", OTP_MESSAGE));

		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		String sid = null;
		try {
			Message message = null;
			message = messageFactory.create(params);
			sid = message.getSid();
		} catch (TwilioRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return sid;
	}
	
	public static void main(String[] args) {
		TwilioOTPSender obj = new TwilioOTPSender();
		System.out.println(obj.sendOTP("5103043927"));

	}

}
