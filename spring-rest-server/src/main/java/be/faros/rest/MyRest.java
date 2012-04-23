package be.faros.rest;

public class MyRest {

	private String string_field;

	MyRest(String s) {
		string_field = s;
	}
	
	public String getString_field() {
		return string_field;
	}

	public void setString_field(String string_field) {
		this.string_field = string_field;
	}
}
