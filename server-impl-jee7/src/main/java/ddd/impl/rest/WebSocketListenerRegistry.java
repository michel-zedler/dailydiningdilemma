package ddd.impl.rest;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;
import javax.websocket.Session;

@Singleton
public class WebSocketListenerRegistry {

	private ConcurrentHashMap<Long, List<Session>> votingsRegistry = new ConcurrentHashMap<Long, List<Session>>();
	
	public ConcurrentHashMap<Long, List<Session>> getVotingsRegistry() {
		return votingsRegistry;
	}

}
