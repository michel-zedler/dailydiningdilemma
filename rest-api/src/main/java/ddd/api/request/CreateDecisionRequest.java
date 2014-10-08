package ddd.api.request;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateDecisionRequest {

	@NotNull
	private String title;

	@NotNull
	private String description;

	@NotNull
	@Future
	private Date votingOpenDate;

	@NotNull
	@Future
	private Date votingCloseDate;

	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

	public Date getVotingCloseDate() {
		return votingCloseDate;
	}

	public Date getVotingOpenDate() {
		return votingOpenDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public void setVotingCloseDate(Date votingCloseDate) {
		this.votingCloseDate = votingCloseDate;
	}

	public void setVotingOpenDate(Date votingOpenDate) {
		this.votingOpenDate = votingOpenDate;
	}

}
