package ddd.api.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServerInfoResponse {

	private String version;
	private String gitHash;
	private String buildDateTime;
	private String buildTimestamp;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getGitHash() {
		return gitHash;
	}

	public void setGitHash(String gitHash) {
		this.gitHash = gitHash;
	}

	public String getBuildDateTime() {
		return buildDateTime;
	}

	public void setBuildDateTime(String buildDateTime) {
		this.buildDateTime = buildDateTime;
	}

	public String getBuildTimestamp() {
		return buildTimestamp;
	}

	public void setBuildTimestamp(String buildTimestamp) {
		this.buildTimestamp = buildTimestamp;
	}

}
