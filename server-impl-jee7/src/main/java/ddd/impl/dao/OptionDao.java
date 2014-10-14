package ddd.impl.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
	

}
