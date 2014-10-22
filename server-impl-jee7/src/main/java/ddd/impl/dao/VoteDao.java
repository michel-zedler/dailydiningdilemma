package ddd.impl.dao;

import static ddd.impl.entity.QVote.vote;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

import ddd.impl.entity.VotingOptionMapping;
import ddd.impl.entity.Vote;

@ApplicationScoped
public class VoteDao {
	
	@PersistenceContext
	private EntityManager entityManager;	

	public void saveVote(Vote vote) {
		entityManager.persist(vote);		
	}

	public void removeVotesForUser(Long userId, List<VotingOptionMapping> votingOptionMappings) {
		new JPADeleteClause(entityManager, vote).where(vote.user.id.eq(userId)
				.and(vote.votingOptionMapping.in(votingOptionMappings))).execute();
	}

	public List<Vote> getVotesForVotingFromUser(Long userId, List<VotingOptionMapping> votingOptionMappings) {
		return new JPAQuery(entityManager)
				.from(vote)
				.where(vote.user.id.eq(userId).and(
						vote.votingOptionMapping.in(votingOptionMappings)))
				.list(vote);
	}

}
