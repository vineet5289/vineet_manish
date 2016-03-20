package controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import play.mvc.Result;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

public class MessageController extends CustomController {
	public static final String ACCOUNT_SID = "ACa645de78a29eb92d5f71772776a9421e"; 
	public static final String AUTH_TOKEN = "5be5c101a91e1bf49e6924a96605e3ee"; 
	public Result sendMessage() throws TwilioRestException {
		System.out.println("********inside message controller******** 1");
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		System.out.println("********inside message controller******** 2");
		List<NameValuePair> params = new ArrayList<NameValuePair>();  
		System.out.println("********inside message controller******** 3");
		params.add(new BasicNameValuePair("To", "+918599894084")); // Replace with a valid phone number for your account.
		params.add(new BasicNameValuePair("From", "+18648320151")); // Replace with a valid phone number for your account.
		params.add(new BasicNameValuePair("Body", "Testing"));
		System.out.println("********inside message controller******** 4");
		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		System.out.println("********inside message controller******** 4");
		Message message = messageFactory.create(params); 
		System.out.println("********inside message controller********6");
		System.out.println(message.getSid());
		System.out.println("********inside message controller******** 7");
		return ok("done");
	}
}
