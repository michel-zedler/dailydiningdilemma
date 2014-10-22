package ddd.impl.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ddd.impl.criteria.VotingCriteria;
import ddd.impl.dao.VotingDao;
import ddd.impl.entity.Voting;
import ddd.impl.model.VotingModel;

@Stateless
public class VotingService {

	@Inject
	private VotingDao votingDao;
	
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
		return toModel(voting);
	}
}
