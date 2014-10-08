package ddd.api.request;

import javax.validation.constraints.Null;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FindDecisionsRequest {

	@Null
	private Boolean open;
	
	public Boolean getOpen() {
		return open;
	}
	
	public void setOpen(Boolean open) {
		this.open = open;
	}
}
