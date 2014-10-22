package ddd.api.request;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateOptionsForVotingRequest {
		
	@NotNull
	private Long votingId;
	
	@NotNull	
	private List<CreateOptionRequest> options;	
	
	public List<CreateOptionRequest> getOptions() {
		return options;
	}
	public void setOptions(List<CreateOptionRequest> options) {
		this.options = options;
	}
	public Long getVotingId() {
		return votingId;
	}
	public void setVotingId(Long votingId) {
		this.votingId = votingId;
	}

}
