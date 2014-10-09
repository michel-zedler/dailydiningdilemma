package ddd.api.model;

import javax.validation.constraints.NotNull;

public class DeviceInfo {
	@NotNull
	private String platform;

	@NotNull
	private String model;

	@NotNull
	private String uuid;

	public String getModel() {
		return model;
	}
	
	public String getPlatform() {
		return platform;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
