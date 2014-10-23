package ddd.impl.event;


public class VotingChangedEvent {
	private Long votingId;
	
	public VotingChangedEvent(Long id) {
		this.votingId = id;
	}

	public Long getVotingId() {
		return votingId;
	}

	public void setVotingId(Long votingId) {
		this.votingId = votingId;
	}

}
