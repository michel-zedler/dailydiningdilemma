package ddd.impl.dao;

import static ddd.impl.entity.QDecisionOptionMapping.decisionOptionMapping;
import static ddd.impl.entity.QOption.option;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

import ddd.impl.entity.Decision;
import ddd.impl.entity.DecisionOptionMapping;
import ddd.impl.entity.Option;

@ApplicationScoped
public class OptionDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void addOptionToDecision(Decision decision, Option option) {
		DecisionOptionMapping mapping = new DecisionOptionMapping();
		mapping.setDecision(decision);
		mapping.setOption(option);
		entityManager.persist(mapping);
	}

	public void persistOption(Option option) {
		entityManager.persist(option);		
	}
	
	public void deleteOption(Long id) {
		new JPADeleteClause(entityManager, decisionOptionMapping).where(decisionOptionMapping.option.id.eq(id)).execute();
		new JPADeleteClause(entityManager, option).where(option.id.eq(id)).execute();	

	}

	public List<Option> getOptionsForDecision(Long decisionId) {
		List<Option> options = new JPAQuery(entityManager)
				.from(decisionOptionMapping)
				.where(decisionOptionMapping.decision.id.eq(decisionId))
				.orderBy(decisionOptionMapping.id.asc())
				.list(decisionOptionMapping.option);
		return options;
	}
	
	

}
