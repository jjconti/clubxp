package pruebas;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;

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
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class TestControlDeRecibos extends TestCase {

	Socio socios[];
	
	
	protected void setUp() throws Exception {
		super.setUp();
		
		socios = new Socio[14];
		
		//Elimina recibos y liquidaciones
		AdministradorDeLiquidaciones.eliminarLiquidaciones();
		
		//Crea socios (TODOS EN ZONA 1)
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
		//AdministradorDeLiquidaciones.eliminarLiquidaciones();
	}

	
	
	public void testSetDevuelto() throws ValidadorException{
		AdministradorDeLiquidaciones.HacerLiquidacion();
		
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		Object recibos[] = ultimaLiq.getRecibos().toArray();
			
		AdministradorDeLiquidaciones.setDevuelto(((Recibo) recibos[0]).getNumeroRecibo(), 1, true);
		AdministradorDeLiquidaciones.setDevuelto(((Recibo) recibos[1]).getNumeroRecibo(), 1, true);
		
		//Ahora comprueba que realmente se hayan guardado en la base de datos 
		Session s = HibernateUtil.getSession();
		
		Recibo r0 = (Recibo) s.load(Recibo.class, new Integer(((Recibo) recibos[0]).getIdRecibo()));
		Recibo r1 = (Recibo) s.load(Recibo.class, new Integer(((Recibo) recibos[1]).getIdRecibo()));
		Recibo r2 = (Recibo) s.load(Recibo.class, new Integer(((Recibo) recibos[2]).getIdRecibo()));
		
		assertTrue(r0.isDevuelto());
		assertTrue(r1.isDevuelto());
		assertFalse(r2.isDevuelto());
		
	}
	
	public void testSetNoDevuelto() throws ValidadorException{
		Session s = HibernateUtil.getSession();
		
		AdministradorDeLiquidaciones.HacerLiquidacion();
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		Object recibos[] = ultimaLiq.getRecibos().toArray();
		
		AdministradorDeLiquidaciones.setDevuelto(((Recibo) recibos[0]).getNumeroRecibo(), 1, true);
		
		//Comprueba que se haya guardado en la base de datos
		Recibo r0 = (Recibo) s.load(Recibo.class, new Integer(((Recibo) recibos[0]).getIdRecibo()));
		assertTrue(r0.isDevuelto());
		
		AdministradorDeLiquidaciones.setDevuelto(((Recibo) recibos[0]).getNumeroRecibo(), 1, false);
		AdministradorDeLiquidaciones.setDevuelto(((Recibo) recibos[1]).getNumeroRecibo(), 1, false);

		//Ahora comprueba que realmente se hayan guardado en la base de datos
		r0 = (Recibo) s.load(Recibo.class, new Integer(((Recibo) recibos[0]).getIdRecibo()));
		Recibo r1 = (Recibo) s.load(Recibo.class, new Integer(((Recibo) recibos[1]).getIdRecibo()));
		Recibo r2 = (Recibo) s.load(Recibo.class, new Integer(((Recibo) recibos[2]).getIdRecibo()));
		
		assertFalse(r0.isDevuelto());
		assertFalse(r1.isDevuelto());
		assertFalse(r2.isDevuelto());	
	}
	
	public void testSetDevuelto2Veces() throws ValidadorException{
		AdministradorDeLiquidaciones.HacerLiquidacion();
		
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		Object recibos[] = ultimaLiq.getRecibos().toArray();
			
		AdministradorDeLiquidaciones.setDevuelto(((Recibo) recibos[0]).getNumeroRecibo(), 1, true);
		AdministradorDeLiquidaciones.setDevuelto(((Recibo) recibos[0]).getNumeroRecibo(), 1, true);
		
		//Ahora comprueba que realmente se hayan guardado en la base de datos 
		Session s = HibernateUtil.getSession();
		
		Recibo r0 = (Recibo) s.load(Recibo.class, new Integer(((Recibo) recibos[0]).getIdRecibo()));
		
		assertTrue(r0.isDevuelto());
		
	}
	
	public void testSetDevueltoZonaIncorrecta(){
		AdministradorDeLiquidaciones.HacerLiquidacion();
		
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		Object recibos[] = ultimaLiq.getRecibos().toArray();
	
		try {
			//Trata de setear como devuelto un recibo de otra zona (zona 2)
			AdministradorDeLiquidaciones.setDevuelto(((Recibo) recibos[0]).getNumeroRecibo(), 2, true);
			fail();
		} catch (ValidadorException e) {
			
		}	
	}
	
	public void testSetDevueltoLiqIncorrecta(){
		AdministradorDeLiquidaciones.HacerLiquidacion();
		Liquidacion liquidacion1 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		AdministradorDeLiquidaciones.HacerLiquidacion();

		Object recibos1[] = liquidacion1.getRecibos().toArray();
		
		try {
			//Trata de setear como devuelto un recibo de la liquidación anterior
			AdministradorDeLiquidaciones.setDevuelto(((Recibo) recibos1[0]).getNumeroRecibo(),	1, true);
			fail();
		} catch (ValidadorException e) {
			
		}
		
		
	}
	
	public void testSetDevueltoLiqZonaIncorrecta(){
		AdministradorDeLiquidaciones.HacerLiquidacion();
		Liquidacion liquidacion1 = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		AdministradorDeLiquidaciones.HacerLiquidacion();

		Object recibos1[] = liquidacion1.getRecibos().toArray();
		
		try {
			//Trata de setear como devuelto un recibo de la liquidación anterior, y 
			//con la zona incorrecta
			AdministradorDeLiquidaciones.setDevuelto(((Recibo) recibos1[0]).getNumeroRecibo(),	2, true);
			fail();
		} catch (ValidadorException e) {
			
		}
		
	}
	
	/**
	 * Comprueba que no se devuelvan recibos de liquidaciones anteriores,
	 * ni de otras zonas.
	 * @throws ValidadorException 
	 */
	public void testGetRecibosDevueltosPorZona() throws ValidadorException{
		
		
		//Crea algunos socios en zona 3
		AdministradorDeSocios.CrearSocio(3, Categoria.MAYOR, "SocioEnZona3A", "ApellidoA", "DNI", 43222333, DateUtil.getDate(25), 1);
		AdministradorDeSocios.CrearSocio(3, Categoria.CADETE, "SocioEnZona3B", "ApellidoB", "DNI", 43222334, DateUtil.getDate(19), 2);
		AdministradorDeSocios.CrearSocio(3, Categoria.CADETE, "SocioEnZona3C", "ApellidoC", "DNI", 43222335, DateUtil.getDate(218), 3);
		AdministradorDeSocios.CrearSocio(3, Categoria.MAYOR, "SocioEnZona3D", "ApellidoD", "DNI", 43222336, DateUtil.getDate(25), 4);

		//Hace dos liquidaciones para asegurarse que los resultados son de la última
		AdministradorDeLiquidaciones.HacerLiquidacion();
		AdministradorDeLiquidaciones.HacerLiquidacion();
		
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		
		//Pone como devueltos todos los de la zona 3, menos uno
		Iterator i = ultimaLiq.getRecibos().iterator();
		while (i.hasNext()){
			Recibo r = (Recibo) i.next();
			if (r.getSocio().getZona().getIdZona() == 3 && r.getSocio().getDni() != 43222333)
				AdministradorDeLiquidaciones.setDevuelto(r.getNumeroRecibo(), r.getSocio().getZona().getIdZona(), true);
		}
		
		
		//Recibos para zona 3
		List zona3 = AdministradorDeLiquidaciones.getRecibosDevueltos(3);
		
		//Tiene que haber solo 4 recibos
		assertEquals(3, zona3.size());
		
		i = zona3.iterator();
		while (i.hasNext()){
			Recibo r = (Recibo) i.next();
			//Tiene que ser de la zona 3
			assertEquals(3, r.getSocio().getZona().getIdZona());
			//Tiene que ser de esta liquidación
			assertEquals(ultimaLiq.getIdLiq(), r.getLiquidacion().getIdLiq());
			//Tiene que estar devuelto
			assertTrue(r.isDevuelto());
		}
		
	}
	
	
	public void testCalcularMontoRecibos() throws ValidadorException{
		//Crea algunos socios en zona 3
		AdministradorDeSocios.CrearSocio(3, Categoria.MAYOR, "SocioEnZona3A", "ApellidoA", "DNI", 43222333, DateUtil.getDate(25), 1);
		AdministradorDeSocios.CrearSocio(3, Categoria.CADETE, "SocioEnZona3B", "ApellidoB", "DNI", 43222334, DateUtil.getDate(19), 2);
		AdministradorDeSocios.CrearSocio(3, Categoria.CADETE, "SocioEnZona3C", "ApellidoC", "DNI", 43222335, DateUtil.getDate(218), 3);
		AdministradorDeSocios.CrearSocio(3, Categoria.MAYOR, "SocioEnZona3D", "ApellidoD", "DNI", 43222336, DateUtil.getDate(25), 4);

		//Hace dos liquidaciones para asegurarse que los resultados son de la última 
		AdministradorDeLiquidaciones.HacerLiquidacion();
		AdministradorDeLiquidaciones.HacerLiquidacion();
		
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		//Calcula la suma para despues compararla con la que queremos probar
		float suma = 0;
		Iterator i = ultimaLiq.getRecibos().iterator();
		while (i.hasNext()){
			Recibo r = (Recibo) i.next();
			if (!r.isDevuelto() && r.getSocio().getZona().getIdZona() == 1)
				suma += r.getValor();
		}
		
		//Comprueba la suma
		assertTrue(suma == 
				AdministradorDeLiquidaciones.calcularMontoRecibosNoDevueltos(1));
		
	}
	
	public void testCalcularMontoRecibos2() throws ValidadorException {
		AdministradorDeSocios.eliminarSocios();
		AdministradorDeLiquidaciones.HacerLiquidacion();
		//Crea algunos socios en zona 3
		Socio socio1 = AdministradorDeSocios.CrearSocio(3, Categoria.MAYOR, "SocioEnZona3A", "ApellidoA", "DNI", 43222393, DateUtil.getDate(25), 1);
		Socio socio2 = AdministradorDeSocios.CrearSocio(3, Categoria.CADETE, "SocioEnZona3B", "ApellidoB", "DNI", 43222394, DateUtil.getDate(19), 2);
		//Socio en zona 1
		Socio socio3 = AdministradorDeSocios.CrearSocio(1, Categoria.CADETE, "SocioEnZona3B", "ApellidoB", "DNI", 43222395, DateUtil.getDate(19), 2);
		
		Liquidacion liq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		Recibo r1 = new Recibo(0,socio1,liq,liq.getMes(),liq.getAnio(),1,25,false, "Veinticinco", socio1.getCategoria());
		Recibo r2 = new Recibo(0,socio2,liq,liq.getMes(),liq.getAnio(),2,20,true, "Veinte", socio2.getCategoria());
		Recibo r3 = new Recibo(0,socio3,liq,liq.getMes(),liq.getAnio(),2,30,true, "Treinta", socio3.getCategoria());
		liq.getRecibos().add(r1);
		liq.getRecibos().add(r2);
		liq.getRecibos().add(r3);

		//Comprueba la suma
		assertTrue(25.0 == 
				AdministradorDeLiquidaciones.calcularMontoRecibosNoDevueltos(3));
		
		
	}
	
	
	
	/**
     * Assembles and returns a test suite for
     * all the test methods of this test case.
     * @return A non-null test suite.
     */
    public static Test suite() { 

        return new TestSuite(TestControlDeRecibos.class);
    }
}
