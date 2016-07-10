package actors;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import actors.SchoolRequestActorProtocol.ApprovedSchool;
import actors.SchoolRequestActorProtocol.NewSchoolRequest;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

public class MessageActor extends UntypedActor {

	public static final String ACCOUNT_SID = "ACa78e99af8879645c514d4df93a5796ba"; 
	public static final String AUTH_TOKEN = "468129b20e3173b974e84098921296a3"; 
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
			params.add(new BasicNameValuePair("To", newSchoolRequest.getReceiverPhoneNumber()));
			params.add(new BasicNameValuePair("From", "+19205692172")); // Replace with a valid phone number for your account.
			String messageBody = String.format("Dear %s, your request has been received. Your request reference number is %s. "
					+ "We will get back to you as soon as possible.", newSchoolRequest.getReceiverName(), newSchoolRequest.getReferenceNumber());

			params.add(new BasicNameValuePair("Body", messageBody));
			MessageFactory messageFactory = twilioRestClient.getAccount().getMessageFactory();
			Message messageSend = messageFactory.create(params);
		}
		else if(message instanceof ApprovedSchool) {
			ApprovedSchool approvedSchool = (ApprovedSchool) message;
			params.add(new BasicNameValuePair("To", approvedSchool.getContract())); // Replace with a valid phone number for your account.
			params.add(new BasicNameValuePair("From", "+19205692172")); // Replace with a valid phone number for your account.
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
