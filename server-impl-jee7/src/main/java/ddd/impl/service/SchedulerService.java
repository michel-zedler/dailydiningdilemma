package ddd.impl.service;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import ddd.impl.dao.DecisionDao;

@Stateless
public class SchedulerService {	
	
	@Inject
	private DecisionDao decisionDao;
	
	@Schedule(second="0", minute="*", hour="*", persistent = false )
	public void closeVotes() {		
		decisionDao.closeElapsedDecisions();
	}

}
