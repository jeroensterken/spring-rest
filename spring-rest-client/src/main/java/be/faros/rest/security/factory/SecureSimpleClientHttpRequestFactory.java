package be.faros.rest.security.factory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import be.faros.rest.security.credentials.CredentialsProvider;


abstract public class SecureSimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {
	protected CredentialsProvider credentialsProvider;
	protected String realm;
	
	public SecureSimpleClientHttpRequestFactory() {
	}


	public CredentialsProvider getCredentialsProvider() {
		return credentialsProvider;
	}


	public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	@Override
	public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
		HttpURLConnection connection = openConnection(uri.toURL(), null);
		prepareConnection(connection, httpMethod.name());
		prepareSecureConnection(connection);
		return createRequest(connection);
	}
	
	abstract protected void prepareSecureConnection(HttpURLConnection connection);
	
	protected ClientHttpRequest createRequest(HttpURLConnection connection) {
		return new SecureSimpleClientHttpRequest(connection, this);
	}
}
