package pruebas;

import java.util.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import utils.DateUtil;
import utils.ValidadorException;

import main.AdministradorDeFamilias;
import main.AdministradorDeLiquidaciones;
import main.AdministradorDeSocios;
import main.Categoria;
import main.Liquidacion;
import main.Recibo;
import main.Socio;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestAdministradorDeLiquidaciones extends TestCase {

	Socio socios[];
	
	public TestAdministradorDeLiquidaciones(String arg0) throws ValidadorException {
		super(arg0);
		socios = new Socio[10];
		
		//Crea socios
		socios[0] = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "SocioA", "ApellidoA", "DNI", 33222333, DateUtil.getDate(25), 1);
		socios[1] = AdministradorDeSocios.CrearSocio(1, Categoria.CADETE, "SocioB", "ApellidoB", "DNI", 33222334, DateUtil.getDate(19), 2);
		socios[2] = AdministradorDeSocios.CrearSocio(1, Categoria.CADETE, "SocioC", "ApellidoC", "DNI", 33222335, DateUtil.getDate(21), 3);
		socios[3] = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "SocioD", "ApellidoD", "DNI", 33222336, DateUtil.getDate(25), 4);
		socios[4] = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "SocioE", "ApellidoE", "DNI", 33222337, DateUtil.getDate(25), 4);
		socios[5] = AdministradorDeSocios.CrearSocio(1, Categoria.MENOR, "SocioF", "ApellidoF", "DNI", 33222338, DateUtil.getDate(25), 4);
		socios[6] = AdministradorDeSocios.CrearSocio(1, Categoria.VITALICIO, "SocioG", "ApellidoG", "DNI", 33222339, DateUtil.getDate(10), 4);
		
		//Socios que van a estar en un grupo familiar
		/*socios[7] = AdministradorDeSocios.CrearSocio(1, Categoria.FAMILIAR, "JefeFamilia", "Familiota", "DNI", 33222444, DateUtil.getDate(30), 4);
		socios[8] = AdministradorDeSocios.CrearSocio(1, Categoria.FAMILIAR, "familiar1", "Familiota", "DNI", 33222445, DateUtil.getDate(10), 4);
		socios[9] = AdministradorDeSocios.CrearSocio(1, Categoria.FAMILIAR, "familiar2", "Familiota", "DNI", 33222446, DateUtil.getDate(10), 4);*/
		
		socios[7] = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "JefeFamilia", "Familiota", "DNI", 33222444, DateUtil.getDate(30), 4);
		socios[8] = AdministradorDeSocios.CrearSocio(1, Categoria.MENOR, "familiar1", "Familiota", "DNI", 33222445, DateUtil.getDate(10), 4);
		socios[9] = AdministradorDeSocios.CrearSocio(1, Categoria.MENOR, "familiar2", "Familiota", "DNI", 33222446, DateUtil.getDate(10), 4);
		
		//Crea el grupo familiar
		Vector asociados = new Vector();
		asociados.add(socios[8].getIdSocioI());
		asociados.add(socios[9].getIdSocioI());
		AdministradorDeFamilias.CrearFamilia(socios[7].getIdSocio(), asociados);	

	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'main.AdministradorDeLiquidaciones.sePuedeLiquidar()'
	 */
	public final void testSePuedeLiquidar() {
		
		//VERIFICAR QUE NO SE HAYA HECHO LA DEVOLUCIÓN DE LA LIQUIDACIÓN ANTERIOR
			
		Calendar fecha = Calendar.getInstance();
		
		fecha.set(2005, 11, 10);
		Date fecha10Nov05 = fecha.getTime();
		
		fecha.set(2005, 11, 5);
		Date fecha5Dic05 = fecha.getTime();
		
		fecha.set(2005, 11, 19);
		Date fecha19Dic05 = fecha.getTime();
		
		fecha.set(2005, 11, 20);
		Date fecha20Dic05 = fecha.getTime();
		
		fecha.set(2005, 11, 25);
		Date fecha25Dic05 = fecha.getTime();
		
		fecha.set(2006, 0, 1);
		Date fecha1Ene06 = fecha.getTime();
		
		fecha.set(2006, 0, 19);
		Date fecha19Ene06 = fecha.getTime();
	
		fecha.set(2006, 0, 20);
		Date fecha20Ene06 = fecha.getTime();
		
		fecha.set(2006, 0, 25);
		Date fecha25Ene06 = fecha.getTime();
		
		fecha.set(2006, 1, 1);
		Date fecha1Feb06 = fecha.getTime();
		
		fecha.set(2006, 1, 15);
		Date fecha15Feb06 = fecha.getTime();
		
		Liquidacion l1 = AdministradorDeLiquidaciones.setUltimaLiquidacion(12, 2005);
		
		assertFalse(AdministradorDeLiquidaciones.sePuedeLiquidar(fecha10Nov05));
		assertFalse(AdministradorDeLiquidaciones.sePuedeLiquidar(fecha5Dic05));
		assertFalse(AdministradorDeLiquidaciones.sePuedeLiquidar(fecha19Dic05));
		assertTrue(AdministradorDeLiquidaciones.sePuedeLiquidar(fecha20Dic05));
		assertTrue(AdministradorDeLiquidaciones.sePuedeLiquidar(fecha25Dic05));
		assertTrue(AdministradorDeLiquidaciones.sePuedeLiquidar(fecha1Ene06));
		assertTrue(AdministradorDeLiquidaciones.sePuedeLiquidar(fecha20Ene06));
			
		Liquidacion l2 = AdministradorDeLiquidaciones.setUltimaLiquidacion(1, 2006);
		
		assertFalse(AdministradorDeLiquidaciones.sePuedeLiquidar(fecha25Dic05));
		assertFalse(AdministradorDeLiquidaciones.sePuedeLiquidar(fecha25Dic05));
		assertFalse(AdministradorDeLiquidaciones.sePuedeLiquidar(fecha19Ene06));
		assertTrue(AdministradorDeLiquidaciones.sePuedeLiquidar(fecha20Ene06));
		assertTrue(AdministradorDeLiquidaciones.sePuedeLiquidar(fecha1Feb06));
		assertTrue(AdministradorDeLiquidaciones.sePuedeLiquidar(fecha15Feb06));
		
		AdministradorDeLiquidaciones.eliminarLiquidacion(l1);
		AdministradorDeLiquidaciones.eliminarLiquidacion(l2);
	}

	/*
	 * Test method for 'main.AdministradorDeLiquidaciones.getAnioProximaLiq()'
	 */
	public final void testGetAnioProximaLiq() {
		
		Liquidacion ultimaLiq1 = AdministradorDeLiquidaciones.setUltimaLiquidacion(12,2005);
		assertEquals(new Integer(2006),AdministradorDeLiquidaciones.getAnioProximaLiq());
		
		Liquidacion ultimaLiq2 = AdministradorDeLiquidaciones.setUltimaLiquidacion(1,2006);
		assertEquals(new Integer(2006),AdministradorDeLiquidaciones.getAnioProximaLiq());
		
		AdministradorDeLiquidaciones.eliminarLiquidacion(ultimaLiq1);
		AdministradorDeLiquidaciones.eliminarLiquidacion(ultimaLiq2);
	}

	/*
	 * Test method for 'main.AdministradorDeLiquidaciones.getMesProximaLiq()'
	 */
	public final void testGetMesProximaLiq() {

		Liquidacion ultimaLiq1 = AdministradorDeLiquidaciones.setUltimaLiquidacion(12,2005);
		assertEquals(new Integer(1),AdministradorDeLiquidaciones.getMesProximaLiq());
		
		Liquidacion ultimaLiq2 = AdministradorDeLiquidaciones.setUltimaLiquidacion(1,2006);
		assertEquals(new Integer(2),AdministradorDeLiquidaciones.getMesProximaLiq());
		
		AdministradorDeLiquidaciones.eliminarLiquidacion(ultimaLiq1);
		AdministradorDeLiquidaciones.eliminarLiquidacion(ultimaLiq2);
		
	}
	
	public final void testGetUltimaLiquidacion(){
		
		Date hoy = Calendar.getInstance().getTime();
		Liquidacion l0 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		assertEquals(new Integer(hoy.getYear() + 1900), new Integer(l0.getAnio()));
		assertEquals(new Integer(hoy.getMonth() + 1), new Integer(l0.getMes()));
		
		Liquidacion l1 = AdministradorDeLiquidaciones.setUltimaLiquidacion(11,2006);
		Liquidacion l2 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		assertEquals(l1,l2);
		
		AdministradorDeLiquidaciones.eliminarLiquidacion(l1);
		AdministradorDeLiquidaciones.eliminarLiquidacion(l0);
		
		
	}

	public void testGetReciboFor(){
		
		Liquidacion liq = new Liquidacion();
		
		Recibo r1 = new Recibo(0,socios[0],liq,1,20,false);
		Recibo r2 = new Recibo(1,socios[1],liq,2,20,false);
		Recibo r3 = new Recibo(2,socios[2],liq,3,20,false);
		liq.getRecibos().add(r1);
		liq.getRecibos().add(r2);
		liq.getRecibos().add(r3);
		
		assertEquals(liq.getReciboFor(socios[0]), r1);
		assertEquals(liq.getReciboFor(socios[0]), r2);
		assertEquals(liq.getReciboFor(socios[0]), r3);
	}
	
	/***
	 * Prueba que se hagan recibos para los socios correctos.
	 */
	public void testHacerLiquidacionCreaRecibos(){
		
		//EN ESTA PRUEBA SE CONSIDERA QUE NADIE DEBE MÁS DE 2 MESES CUANDO SE HACE LA LIQUIDACIÓN
		
		
		//En la base de datos ya hay socios cargados
		AdministradorDeLiquidaciones.HacerLiquidacion();
		
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		//Tiene que haber un recibo para los socios comunes
		assertNotNull(ultimaLiq.getReciboFor(socios[0]));
		assertNotNull(ultimaLiq.getReciboFor(socios[1]));
		assertNotNull(ultimaLiq.getReciboFor(socios[2]));
		assertNotNull(ultimaLiq.getReciboFor(socios[3]));
		assertNotNull(ultimaLiq.getReciboFor(socios[4]));
		assertNotNull(ultimaLiq.getReciboFor(socios[5]));
		//No tiene que haber recibo para los socios vitalicios
		assertNull(ultimaLiq.getReciboFor(socios[6]));
		
		//Tiene que haber un recibo para el titular del grupo familiar
		assertNotNull(ultimaLiq.getReciboFor(socios[7]));
		//No tiene que haber recibos para los integrantes del grupo familiar
		assertNull(ultimaLiq.getReciboFor(socios[8]));
		assertNull(ultimaLiq.getReciboFor(socios[9]));
		
		//En total, debe haber solo 7 recibos.. Ni mas ni menos!
		assertEquals(ultimaLiq.getRecibos().size(), 7);
		
	}
	
	public void testHacerLiquidacionCuotaCorrecta(){
		
		//En la base de datos ya hay socios cargados
		AdministradorDeLiquidaciones.HacerLiquidacion();
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		Recibo r = ultimaLiq.getReciboFor(socios[0]);
		assertTrue(r.getValor() == socios[0].getCategoria().getCuota());

		r = ultimaLiq.getReciboFor(socios[1]);
		assertTrue(r.getValor() == socios[1].getCategoria().getCuota());

		r = ultimaLiq.getReciboFor(socios[7]);
		assertTrue(r.getValor() == socios[7].getCategoria().getCuota());

	}
	
	public void testHacerLiquidacionFechas(){
		
		int mesLiq = AdministradorDeLiquidaciones.getMesProximaLiq().intValue();
		int anioLiq = AdministradorDeLiquidaciones.getAnioProximaLiq().intValue();
		
		//En la base de datos ya hay socios cargados
		AdministradorDeLiquidaciones.HacerLiquidacion();
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		assertEquals(mesLiq, ultimaLiq.getMes());
		assertEquals(anioLiq, ultimaLiq.getMes());
		assertEquals(Calendar.getInstance().getTime(), ultimaLiq.getFecha()); //OJO, CAMBIAN LOS SEGUNDOS DEL DATE?
		
		//OTRA PRUEBA?
		mesLiq = AdministradorDeLiquidaciones.getMesProximaLiq().intValue();
		anioLiq = AdministradorDeLiquidaciones.getAnioProximaLiq().intValue();
		
	}
	
	public void testHacerLiquidacionRecibos(){
		
		//En la base de datos ya hay socios cargados
		AdministradorDeLiquidaciones.HacerLiquidacion();
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		Iterator i = ultimaLiq.getRecibos().iterator();
		while(i.hasNext()){
			Recibo r = (Recibo) i.next();
			assertFalse(r.isDevuelto());
			assertEquals(r.getLiquidacion(), ultimaLiq);
			//VALIDAR EL CAMPO NUMERO DEL RECIBO!
		}
				
	}
	
	
	/**
     * Assembles and returns a test suite for
     * all the test methods of this test case.
     * @return A non-null test suite.
     */
    public static Test suite() { 
        return new TestSuite(TestAdministradorDeLiquidaciones.class);
    }
	
	
}
