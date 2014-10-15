package ddd.impl.dao;

import static ddd.impl.entity.QVote.vote;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.jpa.impl.JPADeleteClause;

import ddd.impl.entity.DecisionOptionMapping;
import ddd.impl.entity.Vote;

@ApplicationScoped
public class VoteDao {
	
	@PersistenceContext
	private EntityManager entityManager;	

	public void saveVote(Vote vote) {
		entityManager.persist(vote);		
	}

	public void removeVotesForUser(Long userId, List<DecisionOptionMapping> decisionOptionMappings) {
		new JPADeleteClause(entityManager, vote).where(vote.user.id.eq(userId)
				.and(vote.decisionOptionMapping.in(decisionOptionMappings))).execute();
	}

}
