package ddd.api.response;

import java.util.ArrayList;
import java.util.List;

import ddd.api.model.VoteDto;
import ddd.api.model.VotingDto;

public class VotingStatusResponse {
	
	private VotingDto details;
	
	private List<VoteDto> currentVoteDistribution = new ArrayList<VoteDto>();	
	
	private Long numberOfParticipants;

	public VotingDto getDetails() {
		return details;
	}
	
	public void setDetails(VotingDto details) {
		this.details = details;
	}
	
	public List<VoteDto> getCurrentVoteDistribution() {
		return currentVoteDistribution;
	}

	public void setCurrentVoteDistribution(List<VoteDto> currentVoteDistribution) {
		this.currentVoteDistribution = currentVoteDistribution;
	}

	public Long getNumberOfParticipants() {
		return numberOfParticipants;
	}

	public void setNumberOfParticipants(Long numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}
}
