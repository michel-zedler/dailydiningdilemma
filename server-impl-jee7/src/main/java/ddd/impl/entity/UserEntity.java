package ddd.impl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "displayName", nullable = false, length = 64)
	private String displayName;

	@Column(name = "facebook_id", nullable = true, length = 64)
	private String facebookId;

	@Column(name = "google_id", nullable = true, length = 64)
	private String googleId;

	@Column(name = "apikey", nullable = true, length = 64)
	private String apiKey;

	public String getApiKey() {
		return apiKey;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public Long getId() {
		return id;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGoogleId() {
		return googleId;
	}
	
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}
}
