package be.faros.rest.security.factory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AbstractClientHttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;


public class SecureSimpleClientHttpRequest extends AbstractClientHttpRequest {
	protected final HttpURLConnection connection;
	protected SecureSimpleClientHttpRequestFactory requestFactory;
	protected HttpHeaders headers;
	protected byte[] bufferedOutput;

	SecureSimpleClientHttpRequest(HttpURLConnection connection, SecureSimpleClientHttpRequestFactory requestFactory) {
		this.connection = connection;
		this.requestFactory = requestFactory;
	}


	public HttpMethod getMethod() {
		return HttpMethod.valueOf(this.connection.getRequestMethod());
	}

	public URI getURI() {
		try {
			return this.connection.getURL().toURI();
		}
		catch (URISyntaxException ex) {
			throw new IllegalStateException("Could not get HttpURLConnection URI: " + ex.getMessage(), ex);
		}
	}

	@Override
	protected ClientHttpResponse executeInternal(HttpHeaders headers, byte[] bufferedOutput) throws IOException {
		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			String headerName = entry.getKey();
			for (String headerValue : entry.getValue()) {
				this.connection.addRequestProperty(headerName, headerValue);
			}
		}
		this.connection.connect();
		if (bufferedOutput.length > 0) {
			FileCopyUtils.copy(bufferedOutput, this.connection.getOutputStream());
		}
		this.headers = headers;
		this.bufferedOutput = bufferedOutput;
		return createResponse();
	}
	
	protected ClientHttpResponse createResponse() {
		return new SecureSimpleClientHttpResponse(this.connection, this);
	}
	
	public ClientHttpRequest createRequest() throws IOException {
		try {
			return requestFactory.createRequest(connection.getURL().toURI(), HttpMethod.valueOf(connection.getRequestMethod()));
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	public ClientHttpResponse replay() throws IOException {
		return ((SecureSimpleClientHttpRequest)createRequest()).executeInternal(headers, bufferedOutput);
	}


	public SecureSimpleClientHttpRequestFactory getRequestFactory() {
		return requestFactory;
	}


	public void setRequestFactory(SecureSimpleClientHttpRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}
		
	
}