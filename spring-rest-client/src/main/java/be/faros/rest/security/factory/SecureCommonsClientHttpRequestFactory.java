package be.faros.rest.security.factory;

import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.springframework.http.client.CommonsClientHttpRequestFactory;

import be.faros.rest.security.credentials.Credentials;
import be.faros.rest.security.credentials.CredentialsProvider;


public class SecureCommonsClientHttpRequestFactory extends CommonsClientHttpRequestFactory {
	protected CredentialsProvider credentialsProvider;
	
	public SecureCommonsClientHttpRequestFactory() {
	}

	public CredentialsProvider getCredentialsProvider() {
		return credentialsProvider;
	}

	public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
		Credentials cred = credentialsProvider.getCredentials(null);
		UsernamePasswordCredentials defaultcreds = new UsernamePasswordCredentials(cred.getUserName(), cred.getPassword());
		getHttpClient().getState().setCredentials(AuthScope.ANY, defaultcreds);
	}
}
