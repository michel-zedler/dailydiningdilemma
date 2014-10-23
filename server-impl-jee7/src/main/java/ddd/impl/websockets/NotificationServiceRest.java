package ddd.impl.websockets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;

import ddd.impl.event.VotingChangedEvent;
import ddd.impl.model.VotingModel;
import ddd.impl.rest.WebSocketListenerRegistry;
import ddd.impl.service.VotingService;

@ApplicationScoped
@ServerEndpoint(value = "/websocket/votings/{id}", encoders=DataEncoder.class)
public class NotificationServiceRest {

	@Inject
	private WebSocketListenerRegistry registry;
	
	@Inject
	private VotingService votingService;
	
	@Inject
	private Logger logger;
	
	@OnOpen
	public void onOpen(Session session) {
		String idParam = session.getPathParameters().get("id");

		Long id = Long.valueOf(idParam);

		synchronized (idParam.intern()) {
			List<Session> sessions = getSessions(id);
			sessions.add(session);
		}
	}

	@OnClose
	public void onClose(Session session) {
		String idParam = session.getPathParameters().get("id");

		Long id = Long.valueOf(idParam);
		
		synchronized (idParam.intern()) {
			List<Session> sessions = getSessions(id);
			
			sessions.remove(session);
			
			if (sessions.isEmpty()) {
				registry.getVotingsRegistry().remove(id);
			}
		}

	}

	public void notify(@Observes VotingChangedEvent event) {
		List<Session> sessions = registry.getVotingsRegistry().get(event.getVotingId());

		if (sessions == null) {
			return;
		}
		
		VotingModel result = votingService.getVotingsStatusForVoting(event.getVotingId());

		for (Session s : sessions) {
			if (s.isOpen()) {
				send(s, result.getCurrentVoteDistribution());
			}
		}
	}

	private void send(Session session, Object object) {
		try {
			session.getBasicRemote().sendObject(object);
		} catch (Exception ex) {
			logger.warn("error sending", ex);
		}
	}

	private List<Session> getSessions(Long id) {
		ConcurrentHashMap<Long, List<Session>> votingsRegistry = registry.getVotingsRegistry();
		
		votingsRegistry.putIfAbsent(id, Collections.synchronizedList(new ArrayList<Session>()));

		return votingsRegistry.get(id);
	}
}
