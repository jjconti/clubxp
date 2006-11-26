package pruebas;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import main.Cobrador;
import utils.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class TestDB extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'main.HibernateUtil.getSessionFactory()'
	 * Al crear la session factory, se inicializa hibernate, y en caso de que 
	 * el mismo tenga algun problema, lanzará una excepción.
	 */
	public void testGetSessionFactory() {
		 SessionFactory sf = HibernateUtil.getSessionFactory();
		
		 assertNotNull(sf);
	}

	/*
	 * Test method for 'main.HibernateUtil.getSession()'
	 * Se obtiene una sesion y se prueba guardar en la  
	 * base de datos un Cobrador a través de la misma. Luego se recupera y se 
	 * comparan ambos objetos. Finalmente este objeto se borra de la base de
	 * datos para dejarla como estaba al comienzo de la prueba.
	 */
	public void testGetSession() {
		Session s = HibernateUtil.getSession();
		
		Cobrador cobrador = new Cobrador(0,"Pepe","NoExist",31331331);
		
		s.beginTransaction();
		s.save(cobrador);
		s.getTransaction().commit();
		
		int id = cobrador.getIdCobrador();
		
		s.beginTransaction();
		Cobrador nuevo = (Cobrador) s.get(Cobrador.class, new Integer(id));
		
		assertEquals(cobrador.getIdCobrador(),nuevo.getIdCobrador());
		
		s.delete(nuevo);
		s.getTransaction().commit();
		s.flush();
	}

	  /**
     * Assembles and returns a test suite for
     * all the test methods of this test case.
     * @return A non-null test suite.
     */
    public static Test suite() { 
        return new TestSuite(TestDB.class);
    }

}
