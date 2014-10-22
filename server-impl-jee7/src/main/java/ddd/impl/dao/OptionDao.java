package ddd.impl.dao;

import static ddd.impl.entity.QVotingOptionMapping.votingOptionMapping;
import static ddd.impl.entity.QOption.option;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

import ddd.impl.entity.Voting;
import ddd.impl.entity.VotingOptionMapping;
import ddd.impl.entity.Option;

@ApplicationScoped
public class OptionDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void addOptionToVoting(Voting voting, Option option) {
		VotingOptionMapping mapping = new VotingOptionMapping();
		mapping.setVoting(voting);
		mapping.setOption(option);
		entityManager.persist(mapping);
	}

	public void persistOption(Option option) {
		entityManager.persist(option);		
	}
	
	public void deleteOption(Long id) {
		new JPADeleteClause(entityManager, votingOptionMapping).where(votingOptionMapping.option.id.eq(id)).execute();
		new JPADeleteClause(entityManager, option).where(option.id.eq(id)).execute();	

	}

	public List<Option> getOptionsForVoting(Long votingId) {
		List<Option> options = new JPAQuery(entityManager)
				.from(votingOptionMapping)
				.where(votingOptionMapping.voting.id.eq(votingId))
				.orderBy(votingOptionMapping.id.asc())
				.list(votingOptionMapping.option);
		return options;
	}
	
	

}
