package utils;

import java.util.Calendar;
import java.util.Date;

import main.Categoria;

public class Validador {

	/**
	 * @param dia va de 1 a 31
	 * @param mes va de 0 a 11
	 * @param anio es año (Ej: 1990, 2005 .. )
	 */
	static public boolean isValidDate(int dia, int mes, int anio){
		
		try{
		
			int iAnio = anio - 1900;
			
			Date d = new Date(iAnio, mes, dia);
			if (d.getDate() != dia || d.getMonth() != mes || d.getYear() != iAnio){ 
				return false;
			}
			
			return true;
		}
		catch(Exception e){
			return false;			
		}		
	}
	
	static public void validateFechaNacimiento(int dia, int mes, int anio, int edadAfiliacion) throws ValidadorException{
		if (!isValidFechaNacimiento(dia,mes,anio,edadAfiliacion)) 
			throw new ValidadorException("Fecha de nacimiento invalida"); 
	}
	
	static public void validateEdadAfiliacion(String edad) throws ValidadorException{
		if (isInt(edad)){
			if (Integer.valueOf(edad).intValue() >= 0) return;
		}
		throw new ValidadorException("Edad de afiliación invalida.");
	}
	
	static public boolean isValidFechaNacimiento(int dia, int mes, int anio, int edadAfiliacion){
		
		if (!isValidDate(dia, mes, anio)) return false;
		Date d = new Date(anio - 1900,  mes, dia);

		Calendar nacimiento = Calendar.getInstance();
		nacimiento.setTime(d);
		
		Date now = new Date(Calendar.getInstance().getTime().getYear(),
							Calendar.getInstance().getTime().getMonth(),
							Calendar.getInstance().getTime().getDate());
		
		
		
		nacimiento.add(Calendar.YEAR, edadAfiliacion);
		return (now.compareTo(nacimiento.getTime()) >= 0);
		
	}
	
		
	static public boolean isInt(String s){
		try{
			Integer.valueOf(s);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	static public boolean isAlphaChar(char c){
		
		if ((c >= 'a' && c <= 'z' ) || (c >= 'A' && c <= 'Z' ) ||
			(" 'ÁÉÍÓÚáéíóúäëïöüÄËÏÖÜñÑçÇ".indexOf(String.valueOf(c)) >= 0)){	
			return true;
		}
		return false;			  
	}
	
	static public boolean isAlpha(String s){
		
		if (s == null) return true;
		for (int n=0; n != s.length(); n++){
			if (!isAlphaChar(s.charAt(n))) return false;
		}
		return true;
	}
	
	static public boolean isEmpty(String s){
		
		return (s == null || s.trim() == "");		
			
	}
	
	

	static public void validateDocumento(String tipo, String documento) throws ValidadorException{
		if (!isValidDocumento(tipo,documento)) throw new ValidadorException("Documento inválido");		
	}

	static public boolean isValidDocumento(String tipo, String documento){
		long numero;
		
		try {
			numero = Long.valueOf(documento).longValue();
		}
		catch (Exception e){
			return false;
		}
		
		if (tipo.equalsIgnoreCase("DNI")){
			if (numero > 70000000) return false;
			if (numero < 1000000) return false;
			return true;
		} else if (tipo.equalsIgnoreCase("LE")){
			if (numero > 10000000) return false;
			if (numero <= 0) return false;
			return true;			
		} else if (tipo.equalsIgnoreCase("LC")){
			if (numero > 10000000) return false;
			if (numero <= 0) return false;
			return true;			
		} else  if (tipo.equalsIgnoreCase("EXT")){
			return true;
		}
		
		return false;
	}
	
	static public void validateNombre(String nombre) throws ValidadorException{
		if (!isValidNombre(nombre)) throw new ValidadorException("Nombre invalido");
	}
	
	static public boolean isValidNombre(String nombre){
		
		if (isEmpty(nombre)) return false;
		nombre = nombre.trim();
		if (!isAlpha(nombre)) return false;
		if (nombre.length() < 2) return false;
		if (nombre.length() > 45) return false;
		if (countOf('\'', nombre) > 1) return false;
		
		String split[] = nombre.toLowerCase().split(" ");
		for (int n=0; n != split.length; n++){
			if (countOf("áéíóú", split[n]) > 1) return false;			
			if (countOf("äëïöü", split[n]) > 1) return false;
			if (n > 0 && countOf("aeiou", split[n]) < 1) return false;
			if (n > 0 && countOf("bcdfghjklmnñpqrstvwxyz", split[n]) < 1) return false;
		}
		
		return true;
	}
	
	static public void validateApellido(String nombre) throws ValidadorException{
		if (!isValidApellido(nombre)) throw new ValidadorException("Apellido invalido");		
	}
	
	static public boolean isValidApellido(String nombre){
		return isValidNombre(nombre);
	}
	
	static public void validateCategoria(int categoria, boolean titular, int dia, int mes, int anio) throws ValidadorException{
		if (!isValidCategoria(categoria, titular, dia, mes, anio)) throw new ValidadorException("La categoría no corresponde a la edad del socio");
	}
	

	static public boolean isValidCategoria(int categoria, boolean titular, int dia, int mes, int anio){
								
		if (categoria == Categoria.VITALICIO) return true;
			
		//La fecha recibida es valida.
		Date d = new Date(anio - 1900,  mes, dia);

		Calendar nacimiento = Calendar.getInstance();
		nacimiento.setTime(d);
		
		if (categoria == Categoria.FAMILIAR){
			if (titular){
				return (d.compareTo(DateUtil.getDate(21)) <= 0);
			}
			else {
				return true;
			}
		}
		
		Date now = DateUtil.getDate(0);
		
		if (categoria == Categoria.MENOR){
			nacimiento.add(Calendar.YEAR,14);
			return (now.compareTo(nacimiento.getTime()) < 0);
		}
		else if (categoria == Categoria.CADETE){
			nacimiento.add(Calendar.YEAR,14);
			if (now.compareTo(nacimiento.getTime()) < 0) return false;
			
			nacimiento.add(Calendar.YEAR,7);
			return (now.compareTo(nacimiento.getTime()) < 0);
		}
		else if (categoria == Categoria.MAYOR){
			nacimiento.add(Calendar.YEAR,21);
			return (now.compareTo(nacimiento.getTime()) >= 0);			
		}

		
		return false;
	}
	
	/***
	 * Devuelve la cantidad de caracteres <i>c</i> que hay en una cadena
	 */
	static private int countOf(char c, String cadena){
		
		int cuenta = 0;
		for (int n=0; n != cadena.length(); n++){
			if (cadena.charAt(n) == c) cuenta++;
		}
		return cuenta;
		
	}
	
	/***
	 * Cuenta cuantas veces aparece alguno de los caracteres de la cadena 
	 * <i>chars</i>, dentro de la cadena <i>cadena</i>
	 * 
	 */
	static private int countOf(String chars, String cadena){
		
		int cuenta = 0;
		for (int n=0; n != cadena.length(); n++){
			if (chars.indexOf(String.valueOf(cadena.charAt(n))) >= 0) cuenta++;
		}
		return cuenta;
		
	}
	
	
	
	
}
