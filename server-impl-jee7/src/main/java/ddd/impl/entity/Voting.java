package ddd.impl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "votings")
public class Voting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "planned_closing_date", nullable = false)
	private Date votingCloseDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "actual_closing_date", nullable = true)
	private Date actualClosingDate;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;
	
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
	
	public Date getActualClosingDate() {
		return actualClosingDate;
	}
	
	public void setActualClosingDate(Date actualClosingDate) {
		this.actualClosingDate = actualClosingDate;
	}
}
