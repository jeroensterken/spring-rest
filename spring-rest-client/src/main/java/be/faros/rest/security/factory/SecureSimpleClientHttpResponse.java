package be.faros.rest.security.factory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

public class SecureSimpleClientHttpResponse implements ClientHttpResponse {
	SecureSimpleClientHttpRequest request;
	
	protected final HttpURLConnection connection;

	private HttpHeaders headers;
	
	SecureSimpleClientHttpResponse replayResponse;


	SecureSimpleClientHttpResponse(HttpURLConnection connection, SecureSimpleClientHttpRequest request) {
		this.connection = connection;
		this.request = request;
	}


	public HttpStatus getStatusCode() throws IOException {
		return replayResponse== null ? HttpStatus.valueOf(this.connection.getResponseCode()) : replayResponse.getStatusCode(); 
	}

	public String getStatusText() throws IOException {
		return replayResponse==null ? this.connection.getResponseMessage() : replayResponse.getStatusText();
	}

	public HttpHeaders getHeaders() {
		if (replayResponse!=null) {
			return replayResponse.getHeaders();
		}
		if (this.headers == null) {
			this.headers = new HttpHeaders();
			// Header field 0 is the status line for most HttpURLConnections, but not on GAE
			String name = this.connection.getHeaderFieldKey(0);
			if (StringUtils.hasLength(name)) {
				this.headers.add(name, this.connection.getHeaderField(0));
			}
			int i = 1;
			while (true) {
				name = this.connection.getHeaderFieldKey(i);
				if (!StringUtils.hasLength(name)) {
					break;
				}
				this.headers.add(name, this.connection.getHeaderField(i));
				i++;
			}
		}
		return this.headers;
	}

	public InputStream getBody() throws IOException {
		if (replayResponse!=null) {
			return replayResponse.getBody();
		}
		InputStream errorStream = this.connection.getErrorStream();
		return (errorStream != null ? errorStream : this.connection.getInputStream());
	}

	public void close() {
		if (replayResponse!=null) {
			replayResponse.close();
		}
		this.connection.disconnect();
	}


	public SecureSimpleClientHttpResponse getReplayResponse() {
		return replayResponse;
	}


	public void setReplayResponse(SecureSimpleClientHttpResponse replayResponse) {
		this.replayResponse = replayResponse;
	}

	
}
