package ddd.impl.dao;

import static ddd.impl.entity.QUserEntity.userEntity;
import static ddd.impl.entity.QUserOAuthIdMapping.userOAuthIdMapping;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;

import ddd.impl.entity.UserEntity;
import ddd.impl.entity.UserOAuthIdMapping;

@ApplicationScoped
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	public UserEntity findByApiKey(String apiKey) {
		return findUserBySingleCriterion(userEntity.apiKey.eq(apiKey));
	}

	public UserEntity findByServiceNameAndId(String serviceName,
			String serviceId) {
		BooleanBuilder builder = new BooleanBuilder();

		builder.and(userOAuthIdMapping.serviceName.eq(serviceName));
		builder.and(userOAuthIdMapping.seriveId.eq(serviceId));

		JPAQuery query = new JPAQuery(entityManager);

		return query.from(userOAuthIdMapping)
				.where(builder)
				.singleResult(userOAuthIdMapping.user);
	}

	private <E> UserEntity findUserBySingleCriterion(Predicate p) {
		JPAQuery query = new JPAQuery(entityManager);

		return query
				.from(userEntity)
				.where(p)
				.singleResult(userEntity);
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
