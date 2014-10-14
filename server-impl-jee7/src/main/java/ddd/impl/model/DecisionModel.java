package ddd.impl.model;

import java.util.Date;

public class DecisionModel {

	private Long id;


	private Date votingCloseDate;

	private String title;
	
	private String description;
	
	private Date actualCloseDate;

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
}
