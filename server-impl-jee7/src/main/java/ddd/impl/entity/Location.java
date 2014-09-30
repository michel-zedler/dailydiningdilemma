package ddd.impl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="locations")
public class Location {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="description", nullable = true)
	private String description = "";
	
	@Column(name="url", nullable = true)
	private String url;
	
	@Column(name="phone", nullable = true)
	private String phoneNumber;
	
	@Column(name="coordinates", nullable = false)
	private String coordinates;
	
	@Column(name="time_added", nullable=false)
	private Date created;
	
	@Column(name="time_last_usage")
	private Date lastUsed;

	public String getCoordinates() {
		return coordinates;
	}

	public Date getCreated() {
		return created;
	}

	
	public String getDescription() {
		return description;
	}
	
	public Long getId() {
		return id;
	}
	public Date getLastUsed() {
		return lastUsed;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getUrl() {
		return url;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
