package ddd.impl.facebookclient;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
@XmlRootElement
public class FacebookMeResponse {

	@XmlAttribute(name="id")
	private String id;
	
	@XmlAttribute(name="email")
	private String email;
	
	@XmlAttribute(name="first_name")
	private String firstName;
	
	@XmlAttribute(name="gender")
	private String gender;
	
	@XmlAttribute(name="last_name")
	private String lastName;
	
	@XmlAttribute(name="name")
	private String name;
	
	@XmlAttribute(name="verified")
	private Boolean verified;
	
	@XmlAttribute(name="link")
	private String link;
	
	@XmlAttribute(name="locale")
	private String locale;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
}
