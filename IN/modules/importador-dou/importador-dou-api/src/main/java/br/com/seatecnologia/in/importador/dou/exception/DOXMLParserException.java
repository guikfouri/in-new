package br.com.seatecnologia.in.importador.dou.exception;

@SuppressWarnings("serial")
public class DOXMLParserException extends Exception {

	public DOXMLParserException() {
		super("DO XML Parser Exception");
	}

	public DOXMLParserException(String message) {
		super("DO XML Parser Exception: " + message);
	}
}
