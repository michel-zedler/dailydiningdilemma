package ddd.impl.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ddd.impl.criteria.DecisionCriteria;
import ddd.impl.dao.DecisionDao;
import ddd.impl.entity.Decision;
import ddd.impl.model.DecisionModel;

@Stateless
public class DecisionService {

	@Inject
	private DecisionDao decisionDao;
	
	public List<DecisionModel> findByCriteria(DecisionCriteria criteria) {
		List<Decision> list = decisionDao.findByCriteria(criteria);
		return toModel(list);
	}
	
	private List<DecisionModel> toModel(List<Decision> decisions) {
		List<DecisionModel> result = new ArrayList<DecisionModel>();
		
		for (Decision d : decisions) {
			result.add(toModel(d));
		}
		
		return result;
	}
	private DecisionModel toModel(Decision entity) {
		DecisionModel model = new DecisionModel();
		
		model.setId(entity.getId());
		model.setTitle(entity.getTitle());
		model.setVotingOpenDate(entity.getVotingOpenDate());
		model.setVotingCloseDate(entity.getVotingCloseDate());
		
		return model;
	}

	public void createNew(DecisionModel model) {
		Decision decision = new Decision();
		
		decision.setTitle(model.getTitle());
		decision.setVotingOpenDate(model.getVotingOpenDate());
		decision.setVotingCloseDate(model.getVotingCloseDate());
		
		decisionDao.persist(decision);
		
		model.setId(decision.getId());
	}

	public void deleteAll() {
		decisionDao.deleteAll();
	}
}
