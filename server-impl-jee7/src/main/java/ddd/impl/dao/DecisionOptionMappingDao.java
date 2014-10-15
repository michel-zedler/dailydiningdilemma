package ddd.impl.dao;

import static ddd.impl.entity.QDecisionOptionMapping.decisionOptionMapping;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.jpa.impl.JPAQuery;

import ddd.impl.entity.DecisionOptionMapping;

@ApplicationScoped
public class DecisionOptionMappingDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public DecisionOptionMapping findDecisionOptionMapping(Long decisionId, Long optionId) {
		return new JPAQuery(entityManager)
				.from(decisionOptionMapping)
				.where(decisionOptionMapping.decision.id.eq(decisionId).and(
						decisionOptionMapping.option.id.eq(optionId)))
				.uniqueResult(decisionOptionMapping);
	}

	public List<DecisionOptionMapping> findDecisionOptionMapping(Long decisionId) {
		return new JPAQuery(entityManager)
		.from(decisionOptionMapping)
		.where(decisionOptionMapping.decision.id.eq(decisionId)).list(decisionOptionMapping);
	}

}
