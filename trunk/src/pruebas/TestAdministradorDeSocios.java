package pruebas;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import utils.ValidadorException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import main.AdministradorDeFamilias;
import main.AdministradorDeLiquidaciones;
import main.AdministradorDeSocios;
import main.Categoria;
import main.Socio;

public class TestAdministradorDeSocios extends TestCase {

	public TestAdministradorDeSocios(){
		AdministradorDeSocios.eliminarSocios();
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		AdministradorDeSocios.eliminarSocios();
	}
	
	
	public void testCrearObtenerSocio() throws ValidadorException {
		
		Date fechaNac = new Date("10/15/1990");
		
		//Crea el socio
		Socio socio = AdministradorDeSocios.CrearSocio(1,2,"Nombre","Apellido","DNI",33222333, fechaNac, 5);
		
		//Lo obtiene
		socio = AdministradorDeSocios.ObtenerSocio(socio.getIdSocio());
		
		//Comprueba que esté todo bien
		assertEquals(socio.getApellido(),"APELLIDO");
		assertEquals(socio.getNombre(),"NOMBRE");
		assertEquals(socio.getZona().getIdZona(),1);
		assertEquals(socio.getCategoria().getIdCategoria(),2);
		assertEquals(socio.getTipoDocumento(),"DNI");
		assertEquals(socio.getDni(),33222333);
		assertEquals(socio.getFechaNacimiento(),fechaNac);
		assertEquals(socio.getEdadAfiliacion(),5);
		assertFalse(socio.isAsociado());	
	}
	
	public void testCrearSocioCategoriaFamiliar(){
		Date fecha = new Date("9/12/1983");
		try {
			AdministradorDeSocios.CrearSocio(1,Categoria.FAMILIAR,"Nombre","Apellido","DNI",31234345,fecha,10);
			fail();
		} catch (ValidadorException e) {
			
		}
		
	}
	
	public void testEliminarSocio() throws ValidadorException {
		
		//Crea un socio
		Date fechaNac = new Date("10/15/1990");
		Socio socio = AdministradorDeSocios.CrearSocio(1,2,"Nombre","Apellido","DNI",33222333, fechaNac, 5);
			
		//Lo elimina
		try {
			AdministradorDeSocios.EliminarSocio(socio.getIdSocio());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Se asegura que se haya eliminado
		assertNull(AdministradorDeSocios.ObtenerSocio(socio.getIdSocio()));
		
	}
	
	public void testEliminarSocioConRecibos() throws Exception{
		//Crea un socio
		Date fechaNac = new Date("10/15/1990");
		Socio socio1 = AdministradorDeSocios.CrearSocio(1, Categoria.CADETE, "Socio1", "Apellido1", "DNI", 33222333, fechaNac, 1);
		Socio socio2 = AdministradorDeSocios.CrearSocio(1, Categoria.CADETE, "Socio2", "Apellido2", "DNI", 33222334, fechaNac, 2);

		AdministradorDeLiquidaciones.HacerLiquidacion();
		
		AdministradorDeSocios.EliminarSocio(socio1.getIdSocio());
		AdministradorDeSocios.EliminarSocio(socio2.getIdSocio());

		assertNull(AdministradorDeSocios.ObtenerSocio(socio1.getIdSocio()));
		assertNull(AdministradorDeSocios.ObtenerSocio(socio2.getIdSocio()));
		
	}
	
	public void testEliminarJefeFamilia() throws ValidadorException {
		
		//Crea un socio titular de una familia
		Date fechaNac = new Date("10/15/1980");
		Socio socio1 = AdministradorDeSocios.CrearSocio(1,2,"Nombre","Apellido","DNI",33222333, fechaNac, 5);
		Socio socio2 = AdministradorDeSocios.CrearSocio(1,2,"Nombre","Apellido","DNI",33222333, fechaNac, 5);
		Vector asociados = new Vector();
		asociados.add(socio2.getIdSocioI());
		AdministradorDeFamilias.CrearFamilia(socio1.getIdSocio(), asociados);
		
		//Lo elimina
		try {
			AdministradorDeSocios.EliminarSocio(socio1.getIdSocio());
			fail();
		} catch (Exception e) {		}
		
		AdministradorDeFamilias.EliminarFamilia(socio1.getIdSocio());
		
	}
	
	public void testModificarSocio() throws ValidadorException{
		//Crea un socio
		Date fechaNac = new Date("10/15/1990");
		Socio socio = AdministradorDeSocios.CrearSocio(1,2,"Nombre","Apellido","DNI",33222333, fechaNac, 5);
		
		//Lo modifica
		Date fechaNac2 = new Date("10/30/1980");
		AdministradorDeSocios.ModificarSocio(socio.getIdSocio(), 2 , 3,
								"NuevoNombre","NuevoApellido", "LC", 33222334, fechaNac2,6);
		
		
		//Obtiene el socio modificado
		socio = AdministradorDeSocios.ObtenerSocio(socio.getIdSocio());
		
		assertEquals("NUEVONOMBRE", socio.getNombre());
		assertEquals("NUEVOAPELLIDO", socio.getApellido());
		assertEquals(33222334, socio.getDni());		
		assertEquals(2, socio.getZona().getIdZona());
		assertEquals(3, socio.getCategoria().getIdCategoria());
		assertEquals("LC", socio.getTipoDocumento());
		assertEquals(fechaNac2, socio.getFechaNacimiento());
		assertEquals(6, socio.getEdadAfiliacion());
		assertFalse(socio.isAsociado());
		
	}
	
	
	public void testGetSocios() throws ValidadorException{
		
		//Crea un socio
		Date fechaNac = new Date("10/15/1990");
		Socio socio1 = AdministradorDeSocios.CrearSocio(1, Categoria.CADETE, "Socio1", "Apellido1", "DNI", 33222333, fechaNac, 1);
		Socio socio2 = AdministradorDeSocios.CrearSocio(1, Categoria.CADETE, "Socio2", "Apellido2", "DNI", 33222334, fechaNac, 2);
		Socio socio3 = AdministradorDeSocios.CrearSocio(1, Categoria.CADETE, "Socio3", "Apellido3", "DNI", 33222335, fechaNac, 3);
		Socio socio4 = AdministradorDeSocios.CrearSocio(1, Categoria.CADETE, "Socio4", "Apellido4", "DNI", 33222336, fechaNac, 4);
			
		List l = AdministradorDeSocios.getSocios();
		Socio socio5 = AdministradorDeSocios.CrearSocio(1, 2, "Socio5", "Apellido5", "DNI", 33222337, fechaNac, 5);
		
		assertTrue(l.indexOf(socio1) >= 0);
		assertTrue(l.indexOf(socio2) >= 0);
		assertTrue(l.indexOf(socio3) >= 0);
		assertTrue(l.indexOf(socio4) >= 0);
		assertTrue(l.indexOf(socio5) == -1);
		
	}
	
	  /**
     * Assembles and returns a test suite for
     * all the test methods of this test case.
     * @return A non-null test suite.
     */
    public static Test suite() { 

        return new TestSuite(TestAdministradorDeSocios.class);
    }
	

}
