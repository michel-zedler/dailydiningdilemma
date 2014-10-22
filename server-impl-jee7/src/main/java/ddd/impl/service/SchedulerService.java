package ddd.impl.service;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import ddd.impl.dao.VotingDao;

@Stateless
public class SchedulerService {	
	
	@Inject
	private VotingDao votingDao;
	
	@Schedule(second="0", minute="*", hour="*", persistent = false )
	public void closeVotes() {		
		votingDao.closeElapsedVotings();
	}

}
