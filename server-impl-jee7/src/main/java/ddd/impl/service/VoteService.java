package ddd.impl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ddd.api.model.VoteDto;
import ddd.impl.dao.VotingOptionMappingDao;
import ddd.impl.dao.UserDao;
import ddd.impl.dao.VoteDao;
import ddd.impl.entity.VotingOptionMapping;
import ddd.impl.entity.UserEntity;
import ddd.impl.entity.Vote;

@Stateless
public class VoteService {
	
	@Inject
	private UserDao userdao;
	
	@Inject
	private VotingOptionMappingDao votingOptionMappingDao;
	
	@Inject
	private VoteDao voteDao;

	public void addVotesForUser(String apikey, Long votingId,
			List<VoteDto> votes) {
		UserEntity user = userdao.findByApiKey(apikey);
		removeVotesForVoting(user.getId(), votingId);		
		
		for (VoteDto voteDto : votes) {
			Vote vote = new Vote();
			VotingOptionMapping votingOptionMapping = votingOptionMappingDao.findVotingOptionMapping(votingId, voteDto.getOptionId());
			vote.setVotingOptionMapping(votingOptionMapping);
			vote.setCreateDate(new Date());
			vote.setValue(voteDto.getValue());
			vote.setUser(user);
			voteDao.saveVote(vote);
		}		
		
	}

	private void removeVotesForVoting(Long userId, Long votingId) {
		List<VotingOptionMapping> votingOptionMappings = votingOptionMappingDao.findVotingOptionMapping(votingId);
		voteDao.removeVotesForUser(userId, votingOptionMappings);
	}
	
	public void removeVotesForVoting(String apikey, Long votingId) {
		UserEntity user = userdao.findByApiKey(apikey);
		removeVotesForVoting(user.getId(), votingId);		
	}

	public List<VoteDto> getVotesForUserByVotingId(String apikey, Long votingId) {
		List<VoteDto> voteDtos = new ArrayList<VoteDto>();
		UserEntity user = userdao.findByApiKey(apikey);
		List<VotingOptionMapping> votingOptionMappings = votingOptionMappingDao.findVotingOptionMapping(votingId);
		List<Vote> votes = voteDao.getVotesForVotingFromUser(user.getId(), votingOptionMappings);
		for (Vote vote: votes) {
			VoteDto voteDto = new VoteDto();
			voteDto.setOptionId(vote.getVotingOptionMapping().getOption().getId());
			voteDto.setValue(vote.getValue());
			voteDtos.add(voteDto);			
		}
		
		return voteDtos;
	}

}
