package ddd.impl.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ddd.api.model.VoteDto;

public class VotingModel {

	private Long id;

	private Date votingCloseDate;

	private String title;
	
	private String description;
	
	private Date actualCloseDate;
	
	private List<VoteDto> currentVoteDistribution = new ArrayList<VoteDto>();	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getVotingCloseDate() {
		return votingCloseDate;
	}

	public void setVotingCloseDate(Date votingCloseDate) {
		this.votingCloseDate = votingCloseDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getActualCloseDate() {
		return actualCloseDate;
	}
	
	public void setActualCloseDate(Date actualCloseDate) {
		this.actualCloseDate = actualCloseDate;
	}

	public List<VoteDto> getCurrentVoteDistribution() {
		return currentVoteDistribution;
	}

	public void setCurrentVoteDistribution(List<VoteDto> currentVoteDistribution) {
		this.currentVoteDistribution = currentVoteDistribution;
	}	
	
}
