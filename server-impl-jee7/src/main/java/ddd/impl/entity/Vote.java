package ddd.impl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "votes")
public class Vote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="voting_option_id")
	private VotingOptionMapping votingOptionMapping;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	@Column(name = "value", nullable=false)
	private Long value;
	
	@Column(name = "create_date")
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VotingOptionMapping getVotingOptionMapping() {
		return votingOptionMapping;
	}

	public void setVotingOptionMapping(VotingOptionMapping votingOptionMapping) {
		this.votingOptionMapping = votingOptionMapping;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	

}
