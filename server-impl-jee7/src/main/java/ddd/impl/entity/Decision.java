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
@Table(name = "decisions")
public class Decision {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "voting_planned_opening_date", nullable = false)
	private Date votingOpenDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "voting_planned_closing_date", nullable = false)
	private Date votingCloseDate;

	@Column(name = "title", nullable = false)
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
