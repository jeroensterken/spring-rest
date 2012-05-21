package be.faros.rest.security.credentials;

public class SimpleCrendentialsProvider implements CredentialsProvider {
	Credentials defaultCredentials;
	
	public SimpleCrendentialsProvider() {
	}

	public SimpleCrendentialsProvider(Credentials defaultCredentials) {
		this.defaultCredentials = defaultCredentials;
	}
	
	public Credentials getCredentials(String realm) {
		return defaultCredentials;
	}

}
