package pruebas;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.Session;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import main.AdministradorDeFamilias;
import main.AdministradorDeLiquidaciones;
import main.AdministradorDeSocios;
import main.Categoria;
import main.Liquidacion;
import main.Recibo;
import main.Socio;
import utils.DateUtil;
import utils.HibernateUtil;
import utils.ValidadorException;

public class TestAdministradorDeLiquidaciones extends TestCase {

	Socio socios[];
	
	public TestAdministradorDeLiquidaciones(String arg0) throws ValidadorException {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
		socios = new Socio[10];
		
		//Elimina recibos y liquidaciones
		AdministradorDeLiquidaciones.eliminarLiquidaciones();
		
		//Crea socios
		socios[0] = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "SocioA", "ApellidoA", "DNI", 33222333, DateUtil.getDate(25), 1);
		socios[1] = AdministradorDeSocios.CrearSocio(1, Categoria.CADETE, "SocioB", "ApellidoB", "DNI", 33222334, DateUtil.getDate(19), 2);
		socios[2] = AdministradorDeSocios.CrearSocio(1, Categoria.CADETE, "SocioC", "ApellidoC", "DNI", 33222335, DateUtil.getDate(218), 3);
		socios[3] = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "SocioD", "ApellidoD", "DNI", 33222336, DateUtil.getDate(25), 4);
		socios[4] = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "SocioE", "ApellidoE", "DNI", 33222337, DateUtil.getDate(25), 4);
		socios[5] = AdministradorDeSocios.CrearSocio(1, Categoria.MENOR, "SocioF", "ApellidoF", "DNI", 33222338, DateUtil.getDate(25), 4);
		socios[6] = AdministradorDeSocios.CrearSocio(1, Categoria.VITALICIO, "SocioG", "ApellidoG", "DNI", 33222339, DateUtil.getDate(10), 4);
		
		//Socios que van a estar en un grupo familiar
		socios[7] = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "JefeFamilia", "Familiota", "DNI", 33222444, DateUtil.getDate(30), 4);
		socios[8] = AdministradorDeSocios.CrearSocio(1, Categoria.MENOR, "familiar1", "Familiota", "DNI", 33222445, DateUtil.getDate(10), 4);
		socios[9] = AdministradorDeSocios.CrearSocio(1, Categoria.MENOR, "familiar2", "Familiota", "DNI", 33222446, DateUtil.getDate(10), 4);
		
		//Crea el grupo familiar
		Vector asociados = new Vector();
		asociados.add(socios[8].getIdSocioI());
		asociados.add(socios[9].getIdSocioI());
		AdministradorDeFamilias.CrearFamilia(socios[7].getIdSocio(), asociados);	

	}

	protected void tearDown() throws Exception {
		super.tearDown();
		AdministradorDeSocios.eliminarSocios();
		AdministradorDeLiquidaciones.eliminarLiquidaciones();
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
		
//		fecha.set(2006, 0, 25);
//		Date fecha25Ene06 = fecha.getTime();
		
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
		
		Recibo r1 = new Recibo(0,socios[0],liq,liq.getMes(),liq.getAnio(),1,20,false, "Veinte");
		Recibo r2 = new Recibo(0,socios[1],liq,liq.getMes(),liq.getAnio(),2,20,false, "Veinte");
		Recibo r3 = new Recibo(0,socios[2],liq,liq.getMes(),liq.getAnio(),3,20,false, "Veinte");
		liq.getRecibos().add(r1);
		liq.getRecibos().add(r2);
		liq.getRecibos().add(r3);
		
		assertTrue(liq.getRecibosFor(socios[0]).contains(r1));
		assertTrue(liq.getRecibosFor(socios[1]).contains(r2));
		assertTrue(liq.getRecibosFor(socios[2]).contains(r3));
		assertTrue(liq.getRecibosFor(socios[3]).isEmpty());
	}

	public void testHacerLiquidacionCuotaCorrecta(){
		
		//En la base de datos ya hay socios cargados
		AdministradorDeLiquidaciones.HacerLiquidacion();
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		Recibo r = (Recibo) ultimaLiq.getRecibosFor(socios[0]).get(0);
		assertTrue(r.getValor() == socios[0].getCategoria().getCuota());
	
		r = (Recibo) ultimaLiq.getRecibosFor(socios[1]).get(0);
		assertTrue(r.getValor() == socios[1].getCategoria().getCuota());
	
		r = (Recibo) ultimaLiq.getRecibosFor(socios[7]).get(0);
		assertTrue(r.getValor() == socios[7].getCategoria().getCuota());
	
	}

	
	public void testMesesQueDebe(){
		
		Recibo r1 = new Recibo(0,socios[0],null,10,2002,1,20,false, "Veinte");
		Recibo r2 = new Recibo(0,socios[0],null,11,2002,2,20,false, "Veinte");
		Recibo r3 = new Recibo(0,socios[0],null,12,2002,3,20,false, "Veinte");
		Recibo r4 = new Recibo(0,socios[0],null,1,2003,4,20,false, "Veinte");
		Recibo r5 = new Recibo(0,socios[0],null,2,2003,5,20,true, "Veinte");
		Recibo r5b = new Recibo(0,socios[0],null,2,2003,6,20,false, "Veinte");

		Recibo r6 = new Recibo(0,socios[0],null,10,2003,7,20,true, "Veinte");
		Recibo r7 = new Recibo(0,socios[0],null,11,2003,8,20,true, "Veinte");
		Recibo r8 = new Recibo(0,socios[0],null,12,2003,9,20,true, "Veinte");
		
		Vector recibos = new Vector();
		recibos.add(r1);
		recibos.add(r2);
		
		assertEquals(0, AdministradorDeLiquidaciones.mesesQueDebe(recibos,12,2002));
		
		recibos.add(r3);
		assertEquals(0, AdministradorDeLiquidaciones.mesesQueDebe(recibos,1,2003));
				
		recibos.add(r4);
		assertEquals(0, AdministradorDeLiquidaciones.mesesQueDebe(recibos,2,2003));

		recibos.add(r5);
		recibos.add(r5b);
		assertEquals(0, AdministradorDeLiquidaciones.mesesQueDebe(recibos,3,2003));
		
		r5b.setDevuelto(true);
		r4.setDevuelto(true);
		assertEquals(2, AdministradorDeLiquidaciones.mesesQueDebe(recibos,3,2003));

		r3.setDevuelto(true);
		recibos.add(r3);
		recibos.add(r3);
		recibos.add(r4);
		assertEquals(3, AdministradorDeLiquidaciones.mesesQueDebe(recibos,3,2003));

		
		recibos.clear();
		recibos.add(r6);
		recibos.add(r7);
		recibos.add(r8);
		assertEquals(3, AdministradorDeLiquidaciones.mesesQueDebe(recibos,1,2004));
		
	}
	
	
	/**
	 * Comprueba que los atributos de la liquidación sean correcto
	 *
	 */
	public void testHacerLiquidacionFechas(){
		
		int mesLiq = AdministradorDeLiquidaciones.getMesProximaLiq().intValue();
		int anioLiq = AdministradorDeLiquidaciones.getAnioProximaLiq().intValue();
		
		//En la base de datos ya hay socios cargados
		AdministradorDeLiquidaciones.HacerLiquidacion();
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		assertEquals(mesLiq, ultimaLiq.getMes());
		assertEquals(anioLiq, ultimaLiq.getAnio());
		assertTrue((Calendar.getInstance().getTime().getTime() - ultimaLiq.getFecha().getTime()) < 20000);
	
	//	mesLiq = AdministradorDeLiquidaciones.getMesProximaLiq().intValue();
	//	anioLiq = AdministradorDeLiquidaciones.getAnioProximaLiq().intValue();
		
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
		}
				
	}
	
	public void testHacerLiquidacionNumeroRecibo(){
		
		//En la base de datos ya hay socios cargados
		AdministradorDeLiquidaciones.HacerLiquidacion();
		//El socio 0 no pagó el primer mes
		((Recibo) AdministradorDeLiquidaciones.getUltimaLiquidacion().getRecibosFor(socios[0]).get(0)).setDevuelto(true);
		AdministradorDeLiquidaciones.HacerLiquidacion();
		
		
		SortedSet numeros = new TreeSet();
		Iterator i = AdministradorDeLiquidaciones.getRecibos().iterator(); 
		while(i.hasNext()){
			Recibo r = (Recibo) i.next();
			numeros.add(new Integer(r.getNumeroRecibo()));
		}
		
		i = numeros.iterator();
		int n = 1;
		while (i.hasNext()){
			Integer num = (Integer) i.next();
			assertEquals(n, num.intValue());
			n++;
		}
		
				
	}
	
	/***
	 * Prueba que se hagan recibos para los socios correctos, considerando
	 * que solo se adeuda la cuota del mes.
	 * En la base de datos NO hay recibos de meses anteriores, porque se supone
	 * que es la primera vez que se pone en marcha el sistema, o bien los socios
	 * son nuevos.
	 */
	public void testHacerLiquidacionCreaRecibosNadieDebe(){
		
		//En la base de datos ya hay socios cargados
		AdministradorDeLiquidaciones.HacerLiquidacion();
		
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		
		//Tiene que haber un recibo para los socios comunes
		assertEquals(1, ultimaLiq.getRecibosFor(socios[0]).size());
		assertEquals(1, ultimaLiq.getRecibosFor(socios[1]).size());
		assertEquals(1, ultimaLiq.getRecibosFor(socios[2]).size());
		assertEquals(1, ultimaLiq.getRecibosFor(socios[3]).size());
		assertEquals(1, ultimaLiq.getRecibosFor(socios[4]).size());
		assertEquals(1, ultimaLiq.getRecibosFor(socios[5]).size());
		
		//No tiene que haber recibo para los socios vitalicios
		assertTrue(ultimaLiq.getRecibosFor(socios[6]).isEmpty());
		
		//Tiene que haber un recibo para el titular del grupo familiar
		assertEquals(1, ultimaLiq.getRecibosFor(socios[7]).size());
		
		//No tiene que haber recibos para los integrantes del grupo familiar
		assertTrue(ultimaLiq.getRecibosFor(socios[8]).isEmpty());
		assertTrue(ultimaLiq.getRecibosFor(socios[9]).isEmpty());
		
		//En total, debe haber solo 7 recibos.. Ni mas ni menos!
		assertEquals(ultimaLiq.getRecibos().size(), 7);
		
	}
	
	
	/***
	 * Prueba que se hagan recibos para los socios correctos, considerando
	 * que solo se adeuda la cuota del mes.
	 * Se supone que HAY UNA LIQUIDACION ANTERIOR.
	 */
	public void testHacerLiquidacionCreaRecibosNadieDebe2(){
		
		//En la base de datos ya hay socios cargados
		AdministradorDeLiquidaciones.HacerLiquidacion();	//Primer liquidacion. Recibos "no devueltos"
		AdministradorDeLiquidaciones.HacerLiquidacion();	//Segunda liquidacion. 
		
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		//Tiene que haber un recibo para los socios comunes
		assertEquals(1, ultimaLiq.getRecibosFor(socios[0]).size());
		assertEquals(1, ultimaLiq.getRecibosFor(socios[1]).size());
		assertEquals(1, ultimaLiq.getRecibosFor(socios[2]).size());
		assertEquals(1, ultimaLiq.getRecibosFor(socios[3]).size());
		assertEquals(1, ultimaLiq.getRecibosFor(socios[4]).size());
		assertEquals(1, ultimaLiq.getRecibosFor(socios[5]).size());
		
		//No tiene que haber recibo para los socios vitalicios
		assertTrue(ultimaLiq.getRecibosFor(socios[6]).isEmpty());
		
		//Tiene que haber un recibo para el titular del grupo familiar
		assertEquals(1, ultimaLiq.getRecibosFor(socios[7]).size());
		
		//No tiene que haber recibos para los integrantes del grupo familiar
		assertTrue(ultimaLiq.getRecibosFor(socios[8]).isEmpty());
		assertTrue(ultimaLiq.getRecibosFor(socios[9]).isEmpty());
		
		//En total, debe haber solo 7 recibos.. Ni mas ni menos!
		assertEquals(ultimaLiq.getRecibos().size(), 7);
		
	}
	
	/***
	 * Prueba que se hagan recibos para los socios correctos, considerando
	 * que algun socio adeuda una cuota de solo UN MES
	 * Se supone que HAY UNA LIQUIDACION ANTERIOR.
	 */
	public void testHacerLiquidacionCreaRecibosAlguienDebe(){
		
		//En la base de datos ya hay socios cargados
		AdministradorDeLiquidaciones.HacerLiquidacion();	//Primer liquidacion. Recibos "no devueltos"
		Liquidacion liquidacion1 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		((Recibo) liquidacion1.getRecibosFor(socios[0]).get(0)).setDevuelto(true); //El socio0 debe el mes anterior
		((Recibo) liquidacion1.getRecibosFor(socios[1]).get(0)).setDevuelto(true); //El socio1 debe el mes anterior
		
		
		AdministradorDeLiquidaciones.HacerLiquidacion();	//Segunda liquidacion. 
		Liquidacion liquidacion2 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		

		//Tiene que haber dos recibos para los socios 0 y 1
		assertEquals(2, liquidacion2.getRecibosFor(socios[0]).size());
		assertEquals(2, liquidacion2.getRecibosFor(socios[1]).size());
		//Tiene que haber un recibo para el resto de los socios comunes
		assertEquals(1, liquidacion2.getRecibosFor(socios[2]).size());
		assertEquals(1, liquidacion2.getRecibosFor(socios[3]).size());
		assertEquals(1, liquidacion2.getRecibosFor(socios[4]).size());
		assertEquals(1, liquidacion2.getRecibosFor(socios[5]).size());
		
		//No tiene que haber recibo para los socios vitalicios
		assertTrue(liquidacion2.getRecibosFor(socios[6]).isEmpty());
		
		//Tiene que haber un recibo para el titular del grupo familiar
		assertEquals(1, liquidacion2.getRecibosFor(socios[7]).size());
		
		//No tiene que haber recibos para los integrantes del grupo familiar
		assertTrue(liquidacion2.getRecibosFor(socios[8]).isEmpty());
		assertTrue(liquidacion2.getRecibosFor(socios[9]).isEmpty());
		
		//En total, debe haber solo 9 recibos.. Ni mas ni menos!
		assertEquals(liquidacion2.getRecibos().size(), 9);
		
		//El mes para los dos recibos de un socio que debe, tiene que ser el mes de la última liq. 
		//y el de la anterior.
		List recibos = liquidacion2.getRecibosFor(socios[0]);
		Vector meses = new Vector();
		
		Iterator i = recibos.iterator();
		while(i.hasNext()){
			meses.add(new Integer(((Recibo) i.next()).getMes()));
		}
		
		assertTrue(meses.contains(new Integer(liquidacion1.getMes())));
		assertTrue(meses.contains(new Integer(liquidacion2.getMes())));
				
	}
	
	/***
	 * Prueba que se hagan recibos para los socios correctos, considerando
	 * que algun socio adeuda cuotas de MÁS de UN MES
	 * Se supone que HAY LIQUIDACIONES ANTERIORES.
	 */
	public void testHacerLiquidacionCreaRecibosAlguienDebe2(){
		
		//En la base de datos ya hay socios cargados
		AdministradorDeLiquidaciones.HacerLiquidacion();	//Primer liquidacion. Recibos "no devueltos"
		Liquidacion liquidacion1 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		((Recibo) liquidacion1.getRecibosFor(socios[0]).get(0)).setDevuelto(true); //El socio0 debe el mes 1
		((Recibo) liquidacion1.getRecibosFor(socios[1]).get(0)).setDevuelto(true); //El socio1 debe el mes 1

		AdministradorDeLiquidaciones.HacerLiquidacion();	//Primer liquidacion. Recibos "no devueltos"
		Liquidacion liquidacion2 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		((Recibo) liquidacion2.getRecibosFor(socios[0]).get(0)).setDevuelto(true); //El socio0 debe el mes 1
		((Recibo) liquidacion2.getRecibosFor(socios[0]).get(1)).setDevuelto(true); //El socio0 debe el mes 2
		((Recibo) liquidacion2.getRecibosFor(socios[1]).get(0)).setDevuelto(true); //El socio1 debe el mes 1
		((Recibo) liquidacion2.getRecibosFor(socios[1]).get(1)).setDevuelto(true); //El socio1 debe el mes 2
		((Recibo) liquidacion2.getRecibosFor(socios[2]).get(0)).setDevuelto(true); //El socio2 debe solo el mes 2

		HibernateUtil.getSession().beginTransaction().commit();
		
		AdministradorDeLiquidaciones.HacerLiquidacion();	//Segunda liquidacion. 
		Liquidacion liquidacion3 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		//No tiene que haber recibos para los socios 0 y 1
		assertEquals(0, liquidacion3.getRecibosFor(socios[0]).size());
		assertEquals(0, liquidacion3.getRecibosFor(socios[1]).size());

		//Tiene que haber dos recibos para el socio 2
		assertEquals(2, liquidacion3.getRecibosFor(socios[2]).size());
		
		//Tiene que haber un recibo para el resto de los socios comunes
		assertEquals(1, liquidacion3.getRecibosFor(socios[3]).size());
		assertEquals(1, liquidacion3.getRecibosFor(socios[4]).size());
		assertEquals(1, liquidacion3.getRecibosFor(socios[5]).size());
		
		//No tiene que haber recibo para los socios vitalicios
		assertTrue(liquidacion3.getRecibosFor(socios[6]).isEmpty());
		
		//Tiene que haber un recibo para el titular del grupo familiar
		assertEquals(1, liquidacion3.getRecibosFor(socios[7]).size());
		
		//No tiene que haber recibos para los integrantes del grupo familiar
		assertTrue(liquidacion3.getRecibosFor(socios[8]).isEmpty());
		assertTrue(liquidacion3.getRecibosFor(socios[9]).isEmpty());
	 
		//En total, debe haber solo 6 recibos.. Ni mas ni menos!
		assertEquals(liquidacion3.getRecibos().size(), 6);
				
	}
	
	/***
	 * 
	 * Prueba que para un socio que adeuda mas de dos cuotas, y el mes pasado no se le emitió recibo,
	 * este més tampoco se le haga recibo.
	 * 
	 * Se supone que HAY LIQUIDACIONES ANTERIORES.
	 */
	public void testHacerLiquidacionCreaRecibosAlguienNoRecibioRecibos(){
		
		//En la base de datos ya hay socios cargados
		AdministradorDeLiquidaciones.HacerLiquidacion();	//Primer liquidacion. Recibos "no devueltos"
		Liquidacion liquidacion1 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		((Recibo) liquidacion1.getRecibosFor(socios[0]).get(0)).setDevuelto(true); //El socio0 debe el mes 1
		((Recibo) liquidacion1.getRecibosFor(socios[1]).get(0)).setDevuelto(true); //El socio1 debe el mes 1

		AdministradorDeLiquidaciones.HacerLiquidacion();	//Primer liquidacion. Recibos "no devueltos"
		Liquidacion liquidacion2 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		((Recibo) liquidacion2.getRecibosFor(socios[0]).get(0)).setDevuelto(true); //El socio0 debe el mes 1
		((Recibo) liquidacion2.getRecibosFor(socios[0]).get(1)).setDevuelto(true); //El socio0 debe el mes 2
		((Recibo) liquidacion2.getRecibosFor(socios[1]).get(0)).setDevuelto(true); //El socio1 debe el mes 1
		((Recibo) liquidacion2.getRecibosFor(socios[1]).get(1)).setDevuelto(true); //El socio1 debe el mes 2
		((Recibo) liquidacion2.getRecibosFor(socios[2]).get(0)).setDevuelto(true); //El socio2 debe solo el mes 2

		AdministradorDeLiquidaciones.HacerLiquidacion();	//Tercera liquidacion. 
		Liquidacion liquidacion3 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		//Se supone que no hay recibos para el socio 0 y 1
		((Recibo) liquidacion3.getRecibosFor(socios[2]).get(0)).setDevuelto(true); //El socio2 debe el mes 2
		((Recibo) liquidacion3.getRecibosFor(socios[2]).get(1)).setDevuelto(true); //El socio2 debe el mes 3
		
		AdministradorDeLiquidaciones.HacerLiquidacion();	//Cuarta liquidacion. 
		Liquidacion liquidacion4 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		
		//No tiene que haber recibos para los socios 0, 1 y 2 
		assertEquals(0, liquidacion4.getRecibosFor(socios[0]).size());
		assertEquals(0, liquidacion4.getRecibosFor(socios[1]).size());
		assertEquals(0, liquidacion4.getRecibosFor(socios[2]).size());
		
		//Tiene que haber un recibo para el resto de los socios comunes
		assertEquals(1, liquidacion4.getRecibosFor(socios[3]).size());
		assertEquals(1, liquidacion4.getRecibosFor(socios[4]).size());
		assertEquals(1, liquidacion4.getRecibosFor(socios[5]).size());
		
		//No tiene que haber recibo para los socios vitalicios
		assertTrue(liquidacion4.getRecibosFor(socios[6]).isEmpty());
		
		//Tiene que haber un recibo para el titular del grupo familiar
		assertEquals(1, liquidacion4.getRecibosFor(socios[7]).size());
		
		//No tiene que haber recibos para los integrantes del grupo familiar
		assertTrue(liquidacion4.getRecibosFor(socios[8]).isEmpty());
		assertTrue(liquidacion4.getRecibosFor(socios[9]).isEmpty());
	 
		//En total, debe haber solo 4 recibos.. Ni mas ni menos!
		assertEquals(liquidacion4.getRecibos().size(), 4);
				
	}
	
	/***
	 * 
	 * Prueba la situación en que un socio que adeuda mas de dos cuotas y el mes pasado no se le 
	 * emitió recibo, nos vino a pagar a la caja.
	 * 
	 * Se supone que HAY LIQUIDACIONES ANTERIORES.
	 */
	public void testHacerLiquidacionAlguienPagoEnCaja(){
		
		//En la base de datos ya hay socios cargados
		AdministradorDeLiquidaciones.HacerLiquidacion();	//Primer liquidacion. Recibos "no devueltos"
		Liquidacion liquidacion1 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		((Recibo) liquidacion1.getRecibosFor(socios[0]).get(0)).setDevuelto(true); //El socio0 debe el mes 1

		AdministradorDeLiquidaciones.HacerLiquidacion();	//Primer liquidacion. Recibos "no devueltos"
		Liquidacion liquidacion2 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		((Recibo) liquidacion2.getRecibosFor(socios[0]).get(0)).setDevuelto(true); //El socio0 debe el mes 1
		((Recibo) liquidacion2.getRecibosFor(socios[0]).get(1)).setDevuelto(true); //El socio0 debe el mes 2

		AdministradorDeLiquidaciones.HacerLiquidacion();	//Tercera liquidacion. 
		Liquidacion liquidacion3 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		//Al socio0 no se le generaron recibos.
		//Ahora el socio0 pagó en la caja todo lo que debia.
		Recibo r1 = new Recibo(0, socios[0], null, liquidacion1.getMes(), liquidacion1.getAnio(),997 , 20, false, "veinte");
		Recibo r2 = new Recibo(0, socios[0], null, liquidacion2.getMes(), liquidacion2.getAnio(),998 , 20, false, "veinte");
		Recibo r3 = new Recibo(0, socios[0], null, liquidacion3.getMes(), liquidacion3.getAnio(),999 , 20, false, "veinte");
		
		AdministradorDeLiquidaciones.guardarRecibo(r1);
		AdministradorDeLiquidaciones.guardarRecibo(r2);
		AdministradorDeLiquidaciones.guardarRecibo(r3);
		
		AdministradorDeLiquidaciones.HacerLiquidacion();	//Cuarta liquidacion. 
		Liquidacion liquidacion4 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
			
		//Tiene que haber solo un recibo todos los socios comunes
		assertEquals(1, liquidacion4.getRecibosFor(socios[0]).size());
		assertEquals(1, liquidacion4.getRecibosFor(socios[1]).size());
		assertEquals(1, liquidacion4.getRecibosFor(socios[2]).size());
		assertEquals(1, liquidacion4.getRecibosFor(socios[3]).size());
		assertEquals(1, liquidacion4.getRecibosFor(socios[4]).size());
		assertEquals(1, liquidacion4.getRecibosFor(socios[5]).size());
		
		//No tiene que haber recibo para los socios vitalicios
		assertTrue(liquidacion4.getRecibosFor(socios[6]).isEmpty());
		
		//Tiene que haber un recibo para el titular del grupo familiar
		assertEquals(1, liquidacion4.getRecibosFor(socios[7]).size());
		
		//No tiene que haber recibos para los integrantes del grupo familiar
		assertTrue(liquidacion4.getRecibosFor(socios[8]).isEmpty());
		assertTrue(liquidacion4.getRecibosFor(socios[9]).isEmpty());
	 
		//En total, debe haber solo 7 recibos.. Ni mas ni menos!
		assertEquals(liquidacion4.getRecibos().size(), 7);
				
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
