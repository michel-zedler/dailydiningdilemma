package ddd.impl.facebookclient;

import org.junit.Test;


public class FacebookClientTest {

	FacebookClient facebookClient = new FacebookClient();
	
	@Test
	public void testConnection() throws Exception {
		facebookClient.getMe("CAAENxrpQIT4BAIfxpCoZBYN2I6EAFChn5AYx2On2tWvWhLA8mO2UwfUf6wIvejKVNzey52bsMMxeohtqQg12KR0ZBXqVLy2EgdOUUYJqswSbNlk0TRxWAEW9fjEVI41Gb8mQRgI8yzt0CeWLMg7rsHrRwaVb8iw3vNdJJJYG1jMLrgb4oIerZBVDa5EZBOnZCZB69wHZA7fqI4pa0VppCEvU0rNblJp96wZD");
	}
}
