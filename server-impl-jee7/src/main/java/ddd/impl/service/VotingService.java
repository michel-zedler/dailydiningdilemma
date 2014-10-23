package ddd.impl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ddd.api.model.VoteDto;
import ddd.impl.criteria.VotingCriteria;
import ddd.impl.dao.VoteDao;
import ddd.impl.dao.VotingDao;
import ddd.impl.entity.Vote;
import ddd.impl.entity.Voting;
import ddd.impl.model.VotingModel;

@Stateless
public class VotingService {

	@Inject
	private VotingDao votingDao;
	
	@Inject
	private VoteDao voteDao;	
	
	public List<VotingModel> findByCriteria(VotingCriteria criteria) {
		List<Voting> list = votingDao.findByCriteria(criteria);
		return toModel(list);
	}
	
	private List<VotingModel> toModel(List<Voting> votings) {
		List<VotingModel> result = new ArrayList<VotingModel>();
		
		for (Voting d : votings) {
			result.add(toModel(d));
		}
		
		return result;
	}
	private VotingModel toModel(Voting entity) {
		VotingModel model = new VotingModel();
		
		model.setId(entity.getId());
		model.setTitle(entity.getTitle());
		model.setDescription(entity.getDescription());
		model.setVotingCloseDate(entity.getVotingCloseDate());
		model.setActualCloseDate(entity.getActualClosingDate());
		
		return model;
	}

	public void createNew(VotingModel model) {
		Voting voting = new Voting();
		
		voting.setTitle(model.getTitle());
		voting.setDescription(model.getDescription());
		voting.setVotingCloseDate(model.getVotingCloseDate());
		
		votingDao.persist(voting);
		
		model.setId(voting.getId());
	}

	public void deleteAll() {
		votingDao.deleteAll();
	}

	public VotingModel findById(Long votingId) {
		Voting voting = votingDao.findById(votingId);
		VotingModel model = toModel(voting);
		return model;
	}
	
	public List<VoteDto> getVotingDistribution(Long votingId) {
		List<Vote> votesForVoting = voteDao.getVotesForVoting(votingId);
		return aggregateVotesAndAddToModel(votesForVoting);
	}
	
	public Long getNumberOfParticipants(Long votingId) {
		return votingDao.getNumberOfParticipants(votingId);
	}

	private List<VoteDto> aggregateVotesAndAddToModel(List<Vote> votesForVoting) {
		Map<Long, Long> aggregatedVotes = new TreeMap<Long,Long>();
		for (Vote vote : votesForVoting) {			
			Long optionId = vote.getVotingOptionMapping().getOption().getId();
			Long aggregatedValue = vote.getValue();
			if (aggregatedVotes.containsKey(optionId)) {
				aggregatedValue += aggregatedVotes.get(optionId);
			}
			
			aggregatedVotes.put(optionId, aggregatedValue);			
		}
		
		
		List<VoteDto> currentVoteDistribution = new ArrayList<VoteDto>();
		for (Entry<Long,Long> entry : aggregatedVotes.entrySet()) {
			VoteDto voteDto = new VoteDto();
			voteDto.setOptionId(entry.getKey());
			voteDto.setValue(entry.getValue());
			currentVoteDistribution.add(voteDto);
		}
		
		return currentVoteDistribution;
	}
}
