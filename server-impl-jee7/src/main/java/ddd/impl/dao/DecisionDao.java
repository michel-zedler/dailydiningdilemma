package ddd.impl.dao;

import static ddd.impl.entity.QDecision.decision;
import static ddd.impl.entity.QOption.option;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.BooleanUtils;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

import ddd.impl.criteria.DecisionCriteria;
import ddd.impl.entity.Decision;

@ApplicationScoped
public class DecisionDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Decision> findByCriteria(DecisionCriteria decisionCriteria) {
		Date now = new Date();
		BooleanBuilder builder = new BooleanBuilder();

		if (BooleanUtils.isTrue(decisionCriteria.getOpen())) {
			builder.and(decision.actualClosingDate.isNull());
			builder.and(decision.votingOpenDate.loe(now));
		} else if (BooleanUtils.isFalse(decisionCriteria.getOpen())) {
			builder.and(decision.actualClosingDate.isNotNull());
			builder.or(decision.votingOpenDate.gt(now));
		}
		
		JPAQuery query = new JPAQuery(entityManager);
		query.from(decision);
		query.where(builder);

		if (BooleanUtils.isFalse(decisionCriteria.getOpen())) {
			query.orderBy(decision.votingCloseDate.desc());
		} else {
			query.orderBy(decision.votingCloseDate.asc());
		}

		return query.list(decision);
	}

	public void persist(Decision decision) {
		entityManager.persist(decision);
	}

	public void deleteAll() {
		new JPADeleteClause(entityManager, option).execute();
		new JPADeleteClause(entityManager, decision).execute();
	}

	public Decision findById(Long decisionId) {
		return entityManager.find(Decision.class, decisionId);

	}

}
