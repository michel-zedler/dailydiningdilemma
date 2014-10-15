package ddd.api.request;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateOptionsForDecisionRequest {
		
	@NotNull
	private Long decisionId;
	
	@NotNull	
	private List<CreateOptionRequest> options;	
	
	public List<CreateOptionRequest> getOptions() {
		return options;
	}
	public void setOptions(List<CreateOptionRequest> options) {
		this.options = options;
	}
	public Long getDecisionId() {
		return decisionId;
	}
	public void setDecisionId(Long decisionId) {
		this.decisionId = decisionId;
	}
	
	
	

}
