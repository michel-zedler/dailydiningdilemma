package ddd.impl.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ddd.api.model.VoteDto;
import ddd.impl.dao.DecisionOptionMappingDao;
import ddd.impl.dao.UserDao;
import ddd.impl.dao.VoteDao;
import ddd.impl.entity.DecisionOptionMapping;
import ddd.impl.entity.UserEntity;
import ddd.impl.entity.Vote;

@Stateless
public class VoteService {
	
	@Inject
	private UserDao userdao;
	
	@Inject
	private DecisionOptionMappingDao decisionOptionMappingDao;
	
	@Inject
	private VoteDao voteDao;

	public void addVotesForUser(String apikey, Long decisionId,
			List<VoteDto> votes) {
		UserEntity user = userdao.findByApiKey(apikey);
		removeVotesForDecision(user.getId(), decisionId);		
		
		for (VoteDto voteDto : votes) {
			Vote vote = new Vote();
			DecisionOptionMapping decisionOptionMapping = decisionOptionMappingDao.findDecisionOptionMapping(decisionId, voteDto.getOptionId());
			vote.setDecisionOptionMapping(decisionOptionMapping);
			vote.setCreateDate(new Date());
			vote.setValue(voteDto.getValue());
			vote.setUser(user);
			voteDao.saveVote(vote);
		}		
		
	}

	public void removeVotesForDecision(Long userId, Long decisionId) {
		List<DecisionOptionMapping> decisionOptionMappings = decisionOptionMappingDao.findDecisionOptionMapping(decisionId);
		voteDao.removeVotesForUser(userId,decisionOptionMappings);
	}

}
