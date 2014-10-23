package ddd.impl.dao;

import static ddd.impl.entity.QOption.option;
import static ddd.impl.entity.QVote.vote;
import static ddd.impl.entity.QVoting.voting;
import static ddd.impl.entity.QVotingOptionMapping.votingOptionMapping;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.BooleanUtils;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;

import ddd.impl.criteria.VotingCriteria;
import ddd.impl.entity.Voting;

@ApplicationScoped
public class VotingDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Voting> findByCriteria(VotingCriteria votingCriteria) {
		BooleanBuilder builder = new BooleanBuilder();

		if (BooleanUtils.isTrue(votingCriteria.getOpen())) {
			builder.and(voting.actualClosingDate.isNull());
		} else if (BooleanUtils.isFalse(votingCriteria.getOpen())) {
			builder.and(voting.actualClosingDate.isNotNull());
		}

		JPAQuery query = new JPAQuery(entityManager);
		query.from(voting);
		query.where(builder);

		if (BooleanUtils.isFalse(votingCriteria.getOpen())) {
			query.orderBy(voting.votingCloseDate.desc());
		} else {
			query.orderBy(voting.votingCloseDate.asc());
		}

		return query.list(voting);
	}

	public Long getNumberOfParticipants(Long votingId) {
		JPAQuery query = new JPAQuery(entityManager);

		query.from(vote);
		query.where(vote.votingOptionMapping.voting.id.eq(votingId));

		try {
			return query.singleResult(vote.user.countDistinct());
		} catch (NoResultException exception) {
			return 0L;
		}
	}

	public void persist(Voting voting) {
		entityManager.persist(voting);
	}

	public void deleteAll() {
		new JPADeleteClause(entityManager, vote).execute();
		new JPADeleteClause(entityManager, votingOptionMapping).execute();
		new JPADeleteClause(entityManager, option).execute();
		new JPADeleteClause(entityManager, voting).execute();
	}

	public Voting findById(Long votingId) {
		return entityManager.find(Voting.class, votingId);

	}

	public void closeElapsedVotings() {
		new JPAUpdateClause(entityManager, voting)
				.where(
						voting.votingCloseDate.before(new Date()),
						voting.actualClosingDate.isNull())
				.set(
						voting.actualClosingDate, new Date()
				).execute();
	}

}
