package ddd.impl.dao;

import it.eckertpartner.jeeutils.persistence.CriteriaHelper;
import it.eckertpartner.jeeutils.persistence.QueryUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import ddd.impl.entity.UserEntity;
import ddd.impl.entity.UserEntity_;
import ddd.impl.entity.UserOAuthIdMapping;
import ddd.impl.entity.UserOAuthIdMapping_;

@ApplicationScoped
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	public UserEntity findByApiKey(String apiKey) {
		return findUserBySingleCriterion(UserEntity_.apiKey, apiKey);
	}

	public UserEntity findByServiceNameAndId(String serviceName,
			String serviceId) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserEntity> criteriaQuery = builder.createQuery(UserEntity.class);

		Root<UserOAuthIdMapping> from = criteriaQuery.from(UserOAuthIdMapping.class);

		criteriaQuery.select(from.get(UserOAuthIdMapping_.user));
		
		Predicate namePredicate = builder.equal(from.get(UserOAuthIdMapping_.serviceName), serviceName);
		Predicate idPredicate = builder.equal(from.get(UserOAuthIdMapping_.seriveId), serviceId);
		
		criteriaQuery.where(namePredicate, idPredicate);

		TypedQuery<UserEntity> typedQuery = entityManager.createQuery(criteriaQuery);
		
		return QueryUtils.getSingleResultOrNull(typedQuery);
	}

	private <E> UserEntity findUserBySingleCriterion(
			SingularAttribute<UserEntity, E> expression, E value) {
		CriteriaHelper<UserEntity> criteriaHelper = new CriteriaHelper<UserEntity>(
				entityManager, UserEntity.class);

		criteriaHelper.addEqual(expression, value);

		return criteriaHelper.getSingleResultOrNull();
	}

	public UserEntity findById(Long id) {
		return entityManager.find(UserEntity.class, id);
	}

	public void persist(UserEntity user) {
		entityManager.persist(user);
	}

	public void persist(UserOAuthIdMapping mapping) {
		entityManager.persist(mapping);
	}
}
