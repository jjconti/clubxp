package utils;

public class ValidadorException extends Exception {
	
	private String mensaje;
	
	public ValidadorException(String m){
		mensaje = m;
	}

	public String getMensaje() {
		return mensaje;
	}
	
 

}
