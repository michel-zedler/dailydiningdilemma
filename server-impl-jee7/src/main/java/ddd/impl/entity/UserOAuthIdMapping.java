package ddd.impl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user_oauth_service", uniqueConstraints = @UniqueConstraint(columnNames = {
		UserOAuthIdMapping.COLUMN_USER_ID, UserOAuthIdMapping.COLUMN_SERVICE_ID }))
public class UserOAuthIdMapping {

	public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_SERVICE_ID = "service_id";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = COLUMN_USER_ID)
	private UserEntity user;

	@Column(name = "service_name")
	private String serviceName;

	@Column(name = COLUMN_SERVICE_ID)
	private String seriveId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSeriveId() {
		return seriveId;
	}

	public void setSeriveId(String seriveId) {
		this.seriveId = seriveId;
	}

}
