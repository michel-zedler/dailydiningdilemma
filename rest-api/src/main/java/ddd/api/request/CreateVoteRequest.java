package ddd.api.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import ddd.api.model.VoteDto;

public class CreateVoteRequest {
	
	@NotNull
	private Long votingId;
	
	@NotNull
	private List<VoteDto> votes;

	public Long getVotingId() {
		return votingId;
	}

	public void setVotingId(Long votingId) {
		this.votingId = votingId;
	}

	public List<VoteDto> getVotes() {
		return votes;
	}

	public void setVotes(List<VoteDto> votes) {
		this.votes = votes;
	}
	
	

}
