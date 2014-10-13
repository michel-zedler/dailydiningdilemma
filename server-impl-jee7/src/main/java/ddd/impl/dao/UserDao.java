package ddd.impl.dao;

import static ddd.impl.entity.QDeviceEntity.deviceEntity;
import static ddd.impl.entity.QUserOAuthIdMapping.userOAuthIdMapping;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

import ddd.impl.entity.DeviceEntity;
import ddd.impl.entity.UserEntity;
import ddd.impl.entity.UserOAuthIdMapping;

@ApplicationScoped
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	public UserEntity findByApiKey(String apiKey) {
		JPAQuery query = new JPAQuery(entityManager);

		return query.from(deviceEntity)
				.where(deviceEntity.apiKey.eq(apiKey))
				.singleResult(deviceEntity.user);
	}
	
	public void deleteApiKey(String apiKey) {
		JPADeleteClause clause = new JPADeleteClause(entityManager, deviceEntity);
		clause.where(deviceEntity.apiKey.eq(apiKey)).execute();
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

	public void deleteExistingDeviceEntity(UserEntity user, String model, String platform, String uuid) {
		JPADeleteClause query = new JPADeleteClause(entityManager, deviceEntity);

		query
			.where(
				deviceEntity.user.eq(user),
				deviceEntity.model.eq(model),
				deviceEntity.platform.eq(platform),
				deviceEntity.uuid.eq(uuid)
		).execute();
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

	public void persist(DeviceEntity deviceEntity) {
		entityManager.persist(deviceEntity);
	}
}
