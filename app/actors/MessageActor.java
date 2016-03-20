package actors;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import play.libs.mailer.MailerClient;
import play.mvc.Result;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import actors.SchoolRequestActorProtocol.ApprovedSchool;
import actors.SchoolRequestActorProtocol.NewSchoolRequest;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

public class MessageActor extends UntypedActor {

	public static final String ACCOUNT_SID = "ACa645de78a29eb92d5f71772776a9421e"; 
	public static final String AUTH_TOKEN = "5be5c101a91e1bf49e6924a96605e3ee"; 
	private TwilioRestClient twilioRestClient;
	private List<NameValuePair> params;

	public MessageActor() {
		twilioRestClient = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		twilioRestClient.setNumRetries(4);
		params = new ArrayList<NameValuePair>();
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof NewSchoolRequest) {
			NewSchoolRequest newSchoolRequest = (NewSchoolRequest)message;
			params.add(new BasicNameValuePair("To", newSchoolRequest.getReceiverPhoneNumber())); // Replace with a valid phone number for your account.
			params.add(new BasicNameValuePair("From", "+18648320151")); // Replace with a valid phone number for your account.
			String messageBody = String.format("Dear %s, your request has been received. Your request reference number is %s. "
					+ "We we get back to you as soon as possible.", newSchoolRequest.getReceiverName(), newSchoolRequest.getReferenceNumber());

			params.add(new BasicNameValuePair("Body", messageBody));
			MessageFactory messageFactory = twilioRestClient.getAccount().getMessageFactory();
			Message messageSend = messageFactory.create(params);
			System.out.println("NewSchoolRequest=" + messageSend.getSid());
		}
		else if(message instanceof ApprovedSchool) {
			ApprovedSchool approvedSchool = (ApprovedSchool) message;
			params.add(new BasicNameValuePair("To", approvedSchool.getContract())); // Replace with a valid phone number for your account.
			params.add(new BasicNameValuePair("From", "+18648320151")); // Replace with a valid phone number for your account.
			String messageBody = String.format("Dear %s, your request has been approved. Your request reference number is %s. "
					+ "And your OTP is %s", approvedSchool.getPrincipleName(), approvedSchool.getReferenceNumber(), approvedSchool.getAuthToke());
			params.add(new BasicNameValuePair("Body", messageBody));
			MessageFactory messageFactory = twilioRestClient.getAccount().getMessageFactory();
			Message messageSend = messageFactory.create(params);
			System.out.println("ApprovedSchool=" +messageSend.getSid());
		}
	}

	public static Props props() {
		return Props.create(new Creator<MessageActor>() {
			private static final long serialVersionUID = 1L;
			@Override
			public MessageActor create() throws Exception {
				return new MessageActor();
			}
		});
	}
}
