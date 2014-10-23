package ddd.api.response;

import java.util.ArrayList;
import java.util.List;

import ddd.api.model.VoteDto;

public class VotingChangedUpdateResponse {

	private Long numberOfParticipants;
	
	private List<VoteDto> votes = new ArrayList<VoteDto>();

	public Long getNumberOfParticipants() {
		return numberOfParticipants;
	}

	public void setNumberOfParticipants(Long numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}

	public List<VoteDto> getVotes() {
		return votes;
	}

	public void setVotes(List<VoteDto> votes) {
		this.votes = votes;
	}
}
