package ddd.impl.websockets;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ddd.api.response.VotingChangedUpdateResponse;

public class DataEncoder implements Encoder.Text<VotingChangedUpdateResponse> {

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String encode(VotingChangedUpdateResponse object) throws EncodeException {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
