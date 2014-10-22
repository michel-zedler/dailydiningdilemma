package ddd.api.response;

import java.util.List;

import ddd.api.model.VoteDto;

public class VotesForUserByVotingIdResponse {
	
	private Long votingId;
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
