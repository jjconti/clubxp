package pruebas;

import utils.Validador;
import utils.ValidadorException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.*;

import main.Categoria;

public class TestValidador extends TestCase {

	public TestValidador(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'utils.Validador.isInt(String)'
	 */
	public void testIsInt() {
		
		assertTrue(Validador.isInt("1"));
		assertTrue(Validador.isInt("0"));
		assertTrue(Validador.isInt("-1"));
		assertTrue(Validador.isInt("2147483647")); 	//ultimo int
		assertTrue(Validador.isInt("40000"));
		assertFalse(Validador.isInt("")); 			//0
		assertFalse(Validador.isInt("2147483648")); //primer long
		assertFalse(Validador.isInt("10000000000"));
		assertFalse(Validador.isInt("1.5"));
		assertFalse(Validador.isInt("1,5"));
		assertFalse(Validador.isInt("1A"));
		assertFalse(Validador.isInt("A1"));
		assertFalse(Validador.isInt("A1A"));
		assertFalse(Validador.isInt("1A1"));
		assertFalse(Validador.isInt("1e3"));
		assertFalse(Validador.isInt("+12"));
		assertFalse(Validador.isInt("#123"));
		assertFalse(Validador.isInt("123#"));
		assertFalse(Validador.isInt("@12"));
		assertFalse(Validador.isInt("(12)"));
		assertFalse(Validador.isInt("12'22"));
		assertFalse(Validador.isInt("1+12"));
		assertFalse(Validador.isInt("1\\2")); 		//1\2
		assertFalse(Validador.isInt("1-2/4"));
		assertFalse(Validador.isInt("a"));
	}

	/*
	 * Test method for 'utils.Validador.isAlpha(String)'
	 */
	public void testIsAlpha() {
		
	assertTrue(Validador.isAlpha("a"));
	assertTrue(Validador.isAlpha(""));
	assertTrue(Validador.isAlpha("abc"));
	assertTrue(Validador.isAlpha("A"));
	assertTrue(Validador.isAlpha("MNB"));
	assertTrue(Validador.isAlpha("MMvvCC"));
	assertTrue(Validador.isAlpha("qqWWee"));
	assertTrue(Validador.isAlpha(" EE "));
	assertTrue(Validador.isAlpha("A A"));
	assertTrue(Validador.isAlpha("v v"));
	assertTrue(Validador.isAlpha("EE gg HH"));
	assertTrue(Validador.isAlpha("Mamá"));
	assertTrue(Validador.isAlpha("Pingüino"));
	assertTrue(Validador.isAlpha("áéíóú"));
	assertTrue(Validador.isAlpha("ÄËÏÖÜäëïöü"));
	assertTrue(Validador.isAlpha("ÁÉÍÓÚ"));
	assertTrue(Validador.isAlpha("D' allesandro"));
	assertTrue(Validador.isAlpha("R'' muri"));
	assertFalse(Validador.isAlpha("1"));
	assertFalse(Validador.isAlpha("$a"));
	assertFalse(Validador.isAlpha("sss.ff"));
	assertFalse(Validador.isAlpha("ff. r"));
	assertFalse(Validador.isAlpha("tt?rr"));
	assertFalse(Validador.isAlpha("pp_"));
	assertFalse(Validador.isAlpha("#"));
	assertFalse(Validador.isAlpha(","));
	assertFalse(Validador.isAlpha("ww,ww"));
	assertFalse(Validador.isAlpha("*"));
	

	}

	/*
	 * Test method for 'utils.Validador.isValidDocumento(String, String)'
	 */
	public void testIsValidDocumento() {
		assertTrue(Validador.isValidDocumento("DNI","1000000"));
		assertTrue(Validador.isValidDocumento("DNI","6999999"));
		assertTrue(Validador.isValidDocumento("DNI","32000000"));
		
		assertTrue(Validador.isValidDocumento("LC","6000000"));
		assertTrue(Validador.isValidDocumento("LE","3000000"));
		assertTrue(Validador.isValidDocumento("LE","6000000"));
		assertTrue(Validador.isValidDocumento("EXT","31223234"));
		
		assertFalse(Validador.isValidDocumento("DNI","345"));
		assertFalse(Validador.isValidDocumento("DNI","399999"));
		assertFalse(Validador.isValidDocumento("DNI","asdads"));
		assertFalse(Validador.isValidDocumento("DNI","-34"));
		assertFalse(Validador.isValidDocumento("DNI","345a"));
		assertFalse(Validador.isValidDocumento("DNI","5000.0000"));
		assertFalse(Validador.isValidDocumento("DNI","31.123.223"));
		assertFalse(Validador.isValidDocumento("DNI","31.123223"));
		assertFalse(Validador.isValidDocumento("DNI","31,123,223"));
		assertFalse(Validador.isValidDocumento("DNI","31,123223"));
		assertFalse(Validador.isValidDocumento("LC","3.000.000"));
		assertFalse(Validador.isValidDocumento("LC","223aAsd"));
		assertFalse(Validador.isValidDocumento("LE","#1231as"));
		assertFalse(Validador.isValidDocumento("DE","&213assd"));
		assertFalse(Validador.isValidDocumento("LE","15000000"));
		assertFalse(Validador.isValidDocumento("LC","15000000"));
		
	}

	/*
	 * Test method for 'utils.Validador.isValidNombre(String)'
	 */
	public void testIsValidNombre() {
		assertTrue(Validador.isValidNombre("alfon"));
		assertTrue(Validador.isValidNombre("D'aless"));
		assertTrue(Validador.isValidNombre("D' aless"));
		assertTrue(Validador.isValidNombre("Pingüino"));
		assertTrue(Validador.isValidNombre("De La Fuente"));
		assertTrue(Validador.isValidNombre("Nombre comp"));
		assertTrue(Validador.isValidNombre("Nom bre comp"));
		assertTrue(Validador.isValidNombre(" axzzxdu "));
		assertTrue(Validador.isValidNombre("Páblo"));
		assertTrue(Validador.isValidNombre("Dós Acéntos"));
		assertTrue(Validador.isValidNombre("Düe Pengüins"));		
		assertTrue(Validador.isValidNombre("Nuñez"));
		
	
		assertFalse(Validador.isValidNombre("a"));
		assertFalse(Validador.isValidNombre("Dos''App"));
		assertFalse(Validador.isValidNombre("Dos'Ap'p"));
		assertFalse(Validador.isValidNombre("Dos\"css"));
		assertFalse(Validador.isValidNombre("coma,aa"));
		assertFalse(Validador.isValidNombre("Pepe1"));
		assertFalse(Validador.isValidNombre("1Pepe"));
		assertFalse(Validador.isValidNombre("Pepe & Company"));
		assertFalse(Validador.isValidNombre("páblító"));
		assertFalse(Validador.isValidNombre("páblíto"));
		assertFalse(Validador.isValidNombre("Láurá"));
		assertFalse(Validador.isValidNombre("Güigüi"));
	}

	/*
	 * Test method for 'utils.Validador.isValidApellido(String)'
	 */
	public void testIsValidApellido() {

	}

	/*
	 * Test method for 'utils.Validador.isValidCategoria(int, String, String, String)'
	 */
	public void testIsValidCategoria() {

				
		Calendar calendar = Calendar.getInstance();
		
		int dia = calendar.getTime().getDate();
		int mes = calendar.getTime().getMonth();
		int anio0 = calendar.getTime().getYear() + 1900;
		
		calendar.add(Calendar.YEAR,1);
		int anioNeg = calendar.getTime().getYear() + 1900;
		
		calendar.add(Calendar.YEAR,-5);
		int anio4 = calendar.getTime().getYear() + 1900;
		
		calendar.add(Calendar.YEAR,-7);
		int anio11 = calendar.getTime().getYear() + 1900;

		calendar.add(Calendar.YEAR,-1);
		int anio12 = calendar.getTime().getYear() + 1900;

		calendar.add(Calendar.YEAR,-1);
		int anio13 = calendar.getTime().getYear() + 1900;

		calendar.add(Calendar.YEAR,-1);
		int anio14 = calendar.getTime().getYear() + 1900;

		calendar.add(Calendar.YEAR,-5);
		int anio19 = calendar.getTime().getYear() + 1900;

		calendar.add(Calendar.YEAR,-1);
		int anio20 = calendar.getTime().getYear() + 1900;

		calendar.add(Calendar.YEAR,-1);
		int anio21 = calendar.getTime().getYear() + 1900;

		calendar.add(Calendar.YEAR,-1);
		int anio22 = calendar.getTime().getYear() + 1900;

		calendar.add(Calendar.YEAR,-60);
		int anio82 = calendar.getTime().getYear() + 1900;
		
		//Los socios vitalicios pueden tener cualquier edad
		assertTrue(Validador.isValidCategoria(Categoria.VITALICIO, dia, mes, anio0));
		assertTrue(Validador.isValidCategoria(Categoria.VITALICIO, dia, mes, anio4));
		assertTrue(Validador.isValidCategoria(Categoria.VITALICIO, dia, mes, anio11));
		assertTrue(Validador.isValidCategoria(Categoria.VITALICIO, dia, mes, anio12));
		assertTrue(Validador.isValidCategoria(Categoria.VITALICIO, dia, mes, anio13));
		assertTrue(Validador.isValidCategoria(Categoria.VITALICIO, dia, mes, anio14));
		assertTrue(Validador.isValidCategoria(Categoria.VITALICIO, dia, mes, anio19));
		assertTrue(Validador.isValidCategoria(Categoria.VITALICIO, dia, mes, anio20));
		assertTrue(Validador.isValidCategoria(Categoria.VITALICIO, dia, mes, anio21));
		assertTrue(Validador.isValidCategoria(Categoria.VITALICIO, dia, mes, anio22));
		assertTrue(Validador.isValidCategoria(Categoria.VITALICIO, dia, mes, anio82));
		
		//Los menores deben tener entre 0 y 13 incluido
		assertTrue(Validador.isValidCategoria(Categoria.MENOR, dia, mes, anio0));
		assertTrue(Validador.isValidCategoria(Categoria.MENOR, dia, mes, anio4));
		assertTrue(Validador.isValidCategoria(Categoria.MENOR, dia, mes, anio11));
		assertTrue(Validador.isValidCategoria(Categoria.MENOR, dia, mes, anio12));
		assertTrue(Validador.isValidCategoria(Categoria.MENOR, dia, mes, anio13));
		assertFalse(Validador.isValidCategoria(Categoria.MENOR, dia, mes, anio14));
		assertFalse(Validador.isValidCategoria(Categoria.MENOR, dia, mes, anio19));
		assertFalse(Validador.isValidCategoria(Categoria.MENOR, dia, mes, anio20));
		assertFalse(Validador.isValidCategoria(Categoria.MENOR, dia, mes, anio21));
		assertFalse(Validador.isValidCategoria(Categoria.MENOR, dia, mes, anio22));
		assertFalse(Validador.isValidCategoria(Categoria.MENOR, dia, mes, anio82));
		
		//Los cadetes deben tener entre 14 y 20 incluido		
		assertFalse(Validador.isValidCategoria(Categoria.CADETE, dia, mes, anio0));
		assertFalse(Validador.isValidCategoria(Categoria.CADETE, dia, mes, anio4));
		assertFalse(Validador.isValidCategoria(Categoria.CADETE, dia, mes, anio11));
		assertFalse(Validador.isValidCategoria(Categoria.CADETE, dia, mes, anio12));
		assertFalse(Validador.isValidCategoria(Categoria.CADETE, dia, mes, anio13));
		assertTrue(Validador.isValidCategoria(Categoria.CADETE, dia, mes, anio14));
		assertTrue(Validador.isValidCategoria(Categoria.CADETE, dia, mes, anio19));
		assertTrue(Validador.isValidCategoria(Categoria.CADETE, dia, mes, anio20));
		assertFalse(Validador.isValidCategoria(Categoria.CADETE, dia, mes, anio21));
		assertFalse(Validador.isValidCategoria(Categoria.CADETE, dia, mes, anio22));
		assertFalse(Validador.isValidCategoria(Categoria.CADETE, dia, mes, anio82));
		
		//Los mayores deben tener 21 o mas.
		assertFalse(Validador.isValidCategoria(Categoria.MAYOR, dia, mes, anio0));
		assertFalse(Validador.isValidCategoria(Categoria.MAYOR, dia, mes, anio4));
		assertFalse(Validador.isValidCategoria(Categoria.MAYOR, dia, mes, anio11));
		assertFalse(Validador.isValidCategoria(Categoria.MAYOR, dia, mes, anio12));
		assertFalse(Validador.isValidCategoria(Categoria.MAYOR, dia, mes, anio13));
		assertFalse(Validador.isValidCategoria(Categoria.MAYOR, dia, mes, anio14));
		assertFalse(Validador.isValidCategoria(Categoria.MAYOR, dia, mes, anio19));
		assertFalse(Validador.isValidCategoria(Categoria.MAYOR, dia, mes, anio20));
		assertTrue(Validador.isValidCategoria(Categoria.MAYOR, dia, mes, anio21));
		assertTrue(Validador.isValidCategoria(Categoria.MAYOR, dia, mes, anio22));
		assertTrue(Validador.isValidCategoria(Categoria.MAYOR, dia, mes, anio82));
				
		//Los socios familiares pueden tener cualquier edad
		assertTrue(Validador.isValidCategoria(Categoria.FAMILIAR, dia, mes, anio0));
		assertTrue(Validador.isValidCategoria(Categoria.FAMILIAR, dia, mes, anio4));
		assertTrue(Validador.isValidCategoria(Categoria.FAMILIAR, dia, mes, anio11));
		assertTrue(Validador.isValidCategoria(Categoria.FAMILIAR, dia, mes, anio12));
		assertTrue(Validador.isValidCategoria(Categoria.FAMILIAR, dia, mes, anio13));
		assertTrue(Validador.isValidCategoria(Categoria.FAMILIAR, dia, mes, anio14));
		assertTrue(Validador.isValidCategoria(Categoria.FAMILIAR, dia, mes, anio19));
		assertTrue(Validador.isValidCategoria(Categoria.FAMILIAR, dia, mes, anio20));
		assertTrue(Validador.isValidCategoria(Categoria.FAMILIAR, dia, mes, anio21));
		assertTrue(Validador.isValidCategoria(Categoria.FAMILIAR, dia, mes, anio22));
		assertTrue(Validador.isValidCategoria(Categoria.FAMILIAR, dia, mes, anio82));
		
	}
	
	/* 
	 * Test method for 'utils.Validador.isValidDate(String dia, String mes, String anio)'
	 */
	public void testIsValidDate(){
		assertTrue(Validador.isValidDate(1, 0, 2000));
		assertTrue(Validador.isValidDate(31, 0, 2000));
		assertTrue(Validador.isValidDate(31, 11, 1999));
		assertTrue(Validador.isValidDate(29, 1, 2004));	//Año bisiesto
		assertTrue(Validador.isValidDate(1, 10, 1890));
		assertFalse(Validador.isValidDate(29, 1, 2005));
		assertFalse(Validador.isValidDate(0, 0, 2004));
		assertFalse(Validador.isValidDate(1, 15, 2004));
		assertFalse(Validador.isValidDate(1, 12, 2004));
		assertFalse(Validador.isValidDate(1, -10, 2004));
		
	}
	
	public void testIsValidFechaNacimiento(){
		
Calendar calendar = Calendar.getInstance();

		int dia = calendar.getTime().getDate();
		int mes = calendar.getTime().getMonth();
		int anio0 = calendar.getTime().getYear() + 1900;
		
		calendar.add(Calendar.YEAR,1);
		int anioNeg = calendar.getTime().getYear() + 1900;
		
		calendar.add(Calendar.YEAR,-5);
		int anio4 = calendar.getTime().getYear() + 1900;
		
		calendar.add(Calendar.YEAR,-7);
		int anio11 = calendar.getTime().getYear() + 1900;

		calendar.add(Calendar.YEAR,-1);
		calendar.add(Calendar.YEAR,-1);
		calendar.add(Calendar.YEAR,-1);
		calendar.add(Calendar.YEAR,-5);
		calendar.add(Calendar.YEAR,-1);
		int anio20 = calendar.getTime().getYear() + 1900;

		calendar.add(Calendar.YEAR,-1);
		calendar.add(Calendar.YEAR,-1);
		calendar.add(Calendar.YEAR,-60);
		int anio82 = calendar.getTime().getYear() + 1900;
		
		assertTrue(Validador.isValidFechaNacimiento(dia, mes, anio20, 10));
		assertTrue(Validador.isValidFechaNacimiento(dia, mes, anio4, 4));
		assertTrue(Validador.isValidFechaNacimiento(dia, mes, anio4, 3));
		assertTrue(Validador.isValidFechaNacimiento(dia, mes, anio82, 81));
		assertTrue(Validador.isValidFechaNacimiento(dia, mes, anio11, 0));
		
		assertFalse(Validador.isValidFechaNacimiento(dia, mes, anio11, 13));
		assertFalse(Validador.isValidFechaNacimiento(dia, mes, anio0, 5));
		assertFalse(Validador.isValidFechaNacimiento(dia, mes, anio4, 5));
		assertFalse(Validador.isValidFechaNacimiento(dia, mes, anio82, 83));
		assertFalse(Validador.isValidFechaNacimiento(dia, mes, anioNeg, 2));
		assertFalse(Validador.isValidFechaNacimiento(dia, mes, anioNeg, 1));
		assertFalse(Validador.isValidFechaNacimiento(dia, mes, anioNeg, 0));
		
	}
	 
	/**
     * Assembles and returns a test suite for
     * all the test methods of this test case.
     * @return A non-null test suite.
     */
    public static Test suite() { 
        return new TestSuite(TestValidador.class);
    }
}
