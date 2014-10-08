package ddd.api.model;

import java.util.Date;

public class DecisionDto {
	private Long id;

	private Date votingOpenDate;

	private Date votingCloseDate;

	private String title;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getVotingOpenDate() {
		return votingOpenDate;
	}

	public void setVotingOpenDate(Date votingOpenDate) {
		this.votingOpenDate = votingOpenDate;
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

}
