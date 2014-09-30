package ddd.impl.dao;

import it.eckertpartner.jeeutils.persistence.CriteriaHelper;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ddd.impl.entity.Configuration;
import ddd.impl.entity.Configuration_;

@ApplicationScoped
public class ConfigurationDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Configuration findByName(String name) {
		CriteriaHelper<Configuration> helper = new CriteriaHelper<Configuration>(entityManager, Configuration.class);

		helper.addEqual(Configuration_.name, name);
		
		return helper.getSingleResultOrNull();
	}
	
	
}
