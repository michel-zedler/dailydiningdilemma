package ddd.impl.dao;

import static ddd.impl.entity.QDecision.decision;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.BooleanUtils;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

import ddd.impl.entity.Decision;
import ddd.impl.entity.DecisionCriteria;

@ApplicationScoped
public class DecisionDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Decision> findByCriteria(DecisionCriteria decisionCriteria) {

		Date now = new Date();

		BooleanBuilder builder = new BooleanBuilder();

		if (BooleanUtils.isTrue(decisionCriteria.getOpen())) {
			builder.and(decision.votingCloseDate.loe(now));
			builder.and(decision.votingOpenDate.goe(now));
		} else if (BooleanUtils.isFalse(decisionCriteria.getOpen())) {
			builder.and(decision.votingCloseDate.gt(now));
			builder.and(decision.votingOpenDate.lt(now));
		}

		JPAQuery query = new JPAQuery(entityManager);
		return query.from(decision)
				.where(builder)
				.list(decision);
	}

	public void persist(Decision decision) {
		entityManager.persist(decision);
	}

}
