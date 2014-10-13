package ddd.api.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import ddd.api.model.DeviceInfo;

@XmlRootElement
public class LoginRequest {

	@NotNull
	private String token;

	@NotNull
	private String service;

	@NotNull
	@Valid
	private DeviceInfo deviceInfo;

	public String getService() {
		return service;
	}

	public String getToken() {
		return token;
	}

	public void setService(String service) {
		this.service = service;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
}
