package be.faros.rest.security.factory;

import java.net.HttpURLConnection;

import be.faros.rest.security.credentials.Credentials;

import sun.misc.BASE64Encoder;

public class BasicSecureSimpleClientHttpRequestFactory extends SecureSimpleClientHttpRequestFactory {

	public BasicSecureSimpleClientHttpRequestFactory() {
	}

	@Override
	protected void prepareSecureConnection(HttpURLConnection connection) {
		if (credentialsProvider==null) {
			return;
		}

		Credentials credentials = credentialsProvider.getCredentials(realm);
	    String token = credentials.getUserName() + ":" + credentials.getPassword();
		BASE64Encoder enc = new sun.misc.BASE64Encoder();
	    String encodedAuthorization = enc.encode(token.getBytes());
	    connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);		
	}
}