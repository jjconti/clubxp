package pruebas;

import java.util.*;

import utils.DateUtil;
import utils.ValidadorException;


import main.AdministradorDeFamilias;
import main.AdministradorDeSocios;
import main.Categoria;
import main.Socio;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestAdministradorDeFamilias extends TestCase {

	Socio socio1;
	Socio socio2;
	Socio socio3;
	Socio socio4;
	Socio socio5;
	Socio socio6;
	Socio socio7;
	Socio socio8;
	
	
	public TestAdministradorDeFamilias(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
		//Crea socios
		Date fechaNac1 = new Date("11/20/1980"); //Mayor de edad
		Date fechaNac2 = new Date("10/15/1990"); //Menor de edad
		Date cumpleHoy = DateUtil.getDate(21);
		
		socio1 = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "SocioA", "ApellidoA", "DNI", 33222333, fechaNac1, 1);
		socio2 = AdministradorDeSocios.CrearSocio(1, Categoria.CADETE, "SocioB", "ApellidoB", "DNI", 33222334, fechaNac2, 2);
		socio3 = AdministradorDeSocios.CrearSocio(1, Categoria.CADETE, "SocioC", "ApellidoC", "DNI", 33222335, fechaNac2, 3);
		socio4 = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "SocioD", "ApellidoD", "DNI", 33222336, fechaNac1, 4);
		socio5 = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "SocioE", "ApellidoE", "DNI", 33222337, fechaNac1, 4);
		socio6 = AdministradorDeSocios.CrearSocio(1, Categoria.CADETE, "SocioF", "ApellidoF", "DNI", 33222338, fechaNac2, 4);
		socio7 = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "SocioG", "ApellidoG", "DNI", 33222339, cumpleHoy, 4);		
		socio8 = AdministradorDeSocios.CrearSocio(1, Categoria.VITALICIO, "Vitalicio", "ApellidoV", "DNI", 33222340, cumpleHoy, 4);
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		AdministradorDeSocios.eliminarSocios();
	}
	
	public void testCrearFamilia() throws ValidadorException{
		Vector asociados1 = new Vector();
		Vector asociados2 = new Vector();
		
		
		asociados1.add(socio2.getIdSocioI());
		asociados1.add(socio3.getIdSocioI());
		asociados1.add(socio4.getIdSocioI());
		
		asociados2.add(socio6.getIdSocioI());
		asociados2.add(socio7.getIdSocioI());

		AdministradorDeFamilias.CrearFamilia(socio1.getIdSocio(), asociados1);
		AdministradorDeFamilias.CrearFamilia(socio5.getIdSocio(), asociados2);
		
		List titulares = AdministradorDeFamilias.getTitulares();
		//El Socio 1 tiene que estar entre los titulares
		assertTrue(titulares.indexOf(socio1) >= 0);
		//El Socio 5 tiene que estar entre los titulares
		assertTrue(titulares.indexOf(socio5) >= 0);
		
		List asociados = AdministradorDeFamilias.getAsociados(socio1.getIdSocio());
		assertTrue(asociados.indexOf(socio2) >= 0);
		assertTrue(asociados.indexOf(socio3) >= 0);
		assertTrue(asociados.indexOf(socio4) >= 0);
		assertFalse(asociados.indexOf(socio1) >= 0);
		assertFalse(asociados.indexOf(socio5) >= 0);
		
		assertTrue(socio2.isAsociado());
		assertTrue(socio3.isAsociado());
		assertFalse(socio1.isAsociado());
		
		//socio1 = AdministradorDeSocios.ObtenerSocio(socio1.getIdSocio());
		assertTrue(socio1.getSocios().contains(socio2));
		assertTrue(socio1.getSocios().contains(socio3));
		assertTrue(socio1.getSocios().contains(socio4));
		assertFalse(socio1.getSocios().contains(socio1));
		assertFalse(socio1.getSocios().contains(socio5));

	}
	
	public void testGetTitulares() throws ValidadorException{
		Vector asociados1 = new Vector();
		Vector asociados2 = new Vector();
		
		
		asociados1.add(socio2.getIdSocioI());
		asociados1.add(socio3.getIdSocioI());
		asociados1.add(socio4.getIdSocioI());
		
		asociados2.add(socio6.getIdSocioI());
		asociados2.add(socio7.getIdSocioI());

		AdministradorDeFamilias.CrearFamilia(socio1.getIdSocio(), asociados1);
		AdministradorDeFamilias.CrearFamilia(socio5.getIdSocio(), asociados2);
		
		List titulares = AdministradorDeFamilias.getTitulares();
		//El Socio 1 tiene que estar entre los titulares
		assertTrue(titulares.indexOf(socio1) >= 0);
		//El Socio 5 tiene que estar entre los titulares
		assertTrue(titulares.indexOf(socio5) >= 0);
		//El Socio 7 NO tiene que estar entre los titulares
		assertFalse(titulares.indexOf(socio7) >= 0);
		//No tiene que haber nulls
		assertFalse(titulares.indexOf(null) >= 0);
		
	
	}
	
	public void testCrearFamiliaTitularMenor(){
		Vector asociados1 = new Vector();
		
		asociados1.add(socio3.getIdSocioI());
		asociados1.add(socio4.getIdSocioI());
		
		try{
			AdministradorDeFamilias.CrearFamilia(socio2.getIdSocio(), asociados1);
		} catch(ValidadorException e){
			return;
		}
		
		fail();
		
	}
	
	public void testCrearFamiliaTitularCumpleHoy() throws ValidadorException{
		//El socio 7 hoy cumple los 21 años
		Vector asociados1 = new Vector();
		
		asociados1.add(socio3.getIdSocioI());
		asociados1.add(socio4.getIdSocioI());
		
		AdministradorDeFamilias.CrearFamilia(socio7.getIdSocio(), asociados1);	
		
	}
	
	public void testEliminarFamilia() throws ValidadorException {
		Vector asociados = new Vector();
		
		asociados.add(socio2.getIdSocioI());
		asociados.add(socio3.getIdSocioI());
		asociados.add(socio4.getIdSocioI());
		
		AdministradorDeFamilias.CrearFamilia(socio1.getIdSocio(), asociados);	
		
		AdministradorDeFamilias.EliminarFamilia(socio1.getIdSocio());
		
		//El titular no debe estar mas en la lista de titulares
		List titulares = AdministradorDeFamilias.getTitulares();
		assertFalse(titulares.contains(socio1));
		assertFalse(socio1.isAsociado());
		assertFalse(socio2.isAsociado());
		assertFalse(socio3.isAsociado());
		assertFalse(socio4.isAsociado());
		
		//Los asociados no deben estar en la lista del titular
		assertFalse(socio1.getSocios().contains(socio2));
		assertFalse(socio1.getSocios().contains(socio3));
		assertFalse(socio1.getSocios().contains(socio4));
		
	}
	
	public void testCambioCategoriaAlCrear() throws ValidadorException {
		Vector asociados1 = new Vector();
		
		asociados1.add(socio2.getIdSocioI());
		asociados1.add(socio3.getIdSocioI());
		asociados1.add(socio4.getIdSocioI());

		assertFalse(socio1.getCategoria().getIdCategoria() == Categoria.FAMILIAR);
		assertFalse(socio2.getCategoria().getIdCategoria() == Categoria.FAMILIAR);
		
		AdministradorDeFamilias.CrearFamilia(socio1.getIdSocio(), asociados1);
		
		assertTrue(socio1.getCategoria().getIdCategoria() == Categoria.FAMILIAR);
		assertTrue(socio2.getCategoria().getIdCategoria() == Categoria.FAMILIAR);

	}
	
	public void testCambioCategoriaAlEliminar() throws ValidadorException {
		Vector asociados1 = new Vector();
		
		asociados1.add(socio2.getIdSocioI());
		asociados1.add(socio3.getIdSocioI());
		asociados1.add(socio4.getIdSocioI());

		AdministradorDeFamilias.CrearFamilia(socio1.getIdSocio(), asociados1);
		AdministradorDeFamilias.EliminarFamilia(socio1.getIdSocio());
		
		assertTrue(socio1.getCategoria().getIdCategoria() == Categoria.MAYOR);
		assertTrue(socio2.getCategoria().getIdCategoria() == Categoria.CADETE);
		assertTrue(socio3.getCategoria().getIdCategoria() == Categoria.CADETE);
		assertTrue(socio4.getCategoria().getIdCategoria() == Categoria.MAYOR);
		

	}
	
	public void testGetSociosSinGrupoFamiliar() throws ValidadorException{
		Vector asociados1 = new Vector();
		asociados1.add(socio2.getIdSocioI());
		asociados1.add(socio3.getIdSocioI());
		asociados1.add(socio4.getIdSocioI());

		AdministradorDeFamilias.CrearFamilia(socio1.getIdSocio(), asociados1);
		List l = AdministradorDeFamilias.getSociosSinGrupoFamiliar();
		
		assertFalse(l.contains(socio1));
		assertFalse(l.contains(socio2));
		assertFalse(l.contains(socio3));
		assertFalse(l.contains(socio4));
		//No debe devolver socios vitalicios,
		//Ya que estos no pueden pertenecer a un Grupo familiar
		assertFalse(l.contains(socio8)); 
		
		assertTrue(l.contains(socio5));
		assertTrue(l.contains(socio6));
		assertTrue(l.contains(socio7));
		
	}
	
	
	/**
     * Assembles and returns a test suite for
     * all the test methods of this test case.
     * @return A non-null test suite.
     */
    public static Test suite() { 
        return new TestSuite(TestAdministradorDeFamilias.class);
    }
	
}
