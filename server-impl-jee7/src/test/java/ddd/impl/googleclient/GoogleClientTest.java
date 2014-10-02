package ddd.impl.googleclient;

import org.junit.Test;


public class GoogleClientTest {

	GoogleClient googleClient = new GoogleClient();
	
	@Test
	public void testAccess() throws Exception {
		googleClient.getMe("ya29.kgCCTwGrVp8kS8kS6kzlDMLKjseKzR_CqigQP-KPm_TXCmOcnTi3EGAp");
	}
}
