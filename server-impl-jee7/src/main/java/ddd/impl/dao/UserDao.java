package ddd.impl.dao;

import it.eckertpartner.jeeutils.persistence.CriteriaHelper;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.SingularAttribute;

import ddd.impl.entity.UserEntity;
import ddd.impl.entity.UserEntity_;

@ApplicationScoped
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	public UserEntity findByApiKey(String apiKey) {
		return findUserBySingleCriterion(UserEntity_.apiKey, apiKey);
	}

	public UserEntity findByFacebookId(String facebookId) {
		return findUserBySingleCriterion(UserEntity_.facebookId, facebookId);
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

	public UserEntity findByGoogleId(String googleId) {
		return findUserBySingleCriterion(UserEntity_.googleId, googleId);
	}
}
