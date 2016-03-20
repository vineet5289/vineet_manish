package actors;

import java.util.List;

import actors.SchoolRequestActorProtocol.ApprovedSchool;
import akka.actor.Props;
import akka.actor.UntypedActor;
import dao.SchoolRegistrationRequestDAO;

public class AlertActor  extends UntypedActor{

//	@Inject
//    private Injector injector;

	
	public static Props props = Props.create(AlertActor.class);
//	final ActorSystem actorSystem = ActorSystem.create("srp");
//	final ActorRef messageActor = actorSystem.actorOf(MessageActor.props());

	@Override
	public void onReceive(Object message) throws Exception {
		if(message != null && message instanceof String) {
			SchoolRegistrationRequestDAO schoolRegistrationRequestDAO = new SchoolRegistrationRequestDAO();
			try {
//				List<ApprovedSchool> schools = schoolRegistrationRequestDAO.getAllApprovedSchoolNeedToBeinformed();
//				if(schools != null && schools.size() > 0) {
//					for(ApprovedSchool approvedSchool : schools) {
//					System.out.println("school for approval alert" + approvedSchool.getPrincipleName());
////						messageActor.tell(approvedSchool, messageActor);
//					}
//				} else {
//					System.out.println("there is no school for approval alert");
//				}
				
			} catch(Exception exception) {
				exception.printStackTrace();
			}
		}
	}

}
