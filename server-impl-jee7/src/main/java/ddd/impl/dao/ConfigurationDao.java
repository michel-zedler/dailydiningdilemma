package ddd.impl.dao;

import static ddd.impl.entity.QConfiguration.configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.jpa.impl.JPAQuery;

import ddd.impl.entity.Configuration;
import ddd.impl.entity.QConfiguration;

@ApplicationScoped
public class ConfigurationDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Configuration findByName(String name) {
		JPAQuery query = new JPAQuery(entityManager);

		return query
				.from(configuration)
				.where(QConfiguration.configuration.name.eq(name))
				.singleResult(QConfiguration.configuration);
	}
}
