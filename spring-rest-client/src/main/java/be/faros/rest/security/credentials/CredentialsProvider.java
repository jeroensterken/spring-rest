package be.faros.rest.security.credentials;

public interface CredentialsProvider {
	Credentials getCredentials(String realm);
}
