package ddd.impl.dao;

import it.eckertpartner.jeeutils.persistence.CriteriaHelper;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ddd.impl.entity.Decision;
import ddd.impl.entity.DecisionCriteria;
import ddd.impl.entity.Decision_;

@ApplicationScoped
public class DecisionDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Decision> findByCriteria(DecisionCriteria decisionCriteria) {
		CriteriaHelper<Decision> helper = new CriteriaHelper<Decision>(entityManager, Decision.class);
		
		Date now = new Date();

		if (decisionCriteria.getOpen() != null) {
			helper.addLessThanOrEqualTo(Decision_.votingCloseDate, now);
			helper.addGreaterThanOrEqualTo(Decision_.votingOpenDate, now);
		}
		
		return helper.getResultList();
	}

	public void persist(Decision decision) {
		entityManager.persist(decision);
	}
	
	
}
