package ddd.api.request;

import java.util.List;

import ddd.api.model.VoteDto;

public class CreateVoteRequest {
	
	private Long userId;
	private Long decisionId;
	
	private List<VoteDto> votes;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getDecisionId() {
		return decisionId;
	}

	public void setDecisionId(Long decisionId) {
		this.decisionId = decisionId;
	}

	public List<VoteDto> getVotes() {
		return votes;
	}

	public void setVotes(List<VoteDto> votes) {
		this.votes = votes;
	}
	
	

}
