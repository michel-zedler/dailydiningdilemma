package ddd.impl.dao;

import static ddd.impl.entity.QVotingOptionMapping.votingOptionMapping;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.jpa.impl.JPAQuery;

import ddd.impl.entity.VotingOptionMapping;

@ApplicationScoped
public class VotingOptionMappingDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public VotingOptionMapping findVotingOptionMapping(Long votingId, Long optionId) {
		return new JPAQuery(entityManager)
				.from(votingOptionMapping)
				.where(votingOptionMapping.voting.id.eq(votingId).and(
						votingOptionMapping.option.id.eq(optionId)))
				.uniqueResult(votingOptionMapping);
	}

	public List<VotingOptionMapping> findVotingOptionMapping(Long votingId) {
		return new JPAQuery(entityManager)
		.from(votingOptionMapping)
		.where(votingOptionMapping.voting.id.eq(votingId)).list(votingOptionMapping);
	}

}
