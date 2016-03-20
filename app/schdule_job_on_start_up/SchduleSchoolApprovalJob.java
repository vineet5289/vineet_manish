package schdule_job_on_start_up;

import javax.inject.Inject;

import play.Configuration;
import jobs.SchoolApproval;
import jobs.SchoolApprovalImpl;

import com.google.inject.AbstractModule;

public class SchduleSchoolApprovalJob extends AbstractModule{

	@Inject Configuration conf;
	
	@Override
	protected void configure() {
		bind(SchoolApproval.class)
		.to(SchoolApprovalImpl.class)
		.asEagerSingleton();
	}

}
