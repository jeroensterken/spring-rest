package be.faros.rest.domain;

public class MyRestResult {

	private String string_field;

	MyRestResult() {
	}

	MyRestResult(String s) {
		string_field = s;
	}
	
	public String getString_field() {
		return string_field;
	}

	public void setString_field(String string_field) {
		this.string_field = string_field;
	}
}