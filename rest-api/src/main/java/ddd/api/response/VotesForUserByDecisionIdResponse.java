package ddd.api.response;

import java.util.List;

import ddd.api.model.VoteDto;

public class VotesForUserByDecisionIdResponse {
	
	private Long decisionId;
	private List<VoteDto> votes;
	
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
