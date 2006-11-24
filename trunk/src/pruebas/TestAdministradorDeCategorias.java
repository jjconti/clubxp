package pruebas;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import main.AdministradorDeCategorias;
import main.Categoria;
import utils.DateUtil;

public class TestAdministradorDeCategorias extends TestCase {

	public TestAdministradorDeCategorias(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'main.AdministradorDeCategorias.getCategoriaFamiliar()'
	 */
	public void testGetCategoriaFamiliar() {

		assertTrue(AdministradorDeCategorias.getCategoriaFamiliar()
				.getIdCategoria() == Categoria.FAMILIAR);
		
	}

	/*
	 * Test method for 'main.AdministradorDeCategorias.getCategoria(Date)'
	 */
	public void testGetCategoria() {
	
		assertTrue(AdministradorDeCategorias.getCategoria(DateUtil.getDate(0))
				.getIdCategoria() == Categoria.MENOR);

		assertTrue(AdministradorDeCategorias.getCategoria(DateUtil.getDate(13))
				.getIdCategoria() == Categoria.MENOR);

		assertTrue(AdministradorDeCategorias.getCategoria(DateUtil.getDate(13, 5))
				.getIdCategoria() == Categoria.MENOR);
		
		assertTrue(AdministradorDeCategorias.getCategoria(DateUtil.getDate(14))
				.getIdCategoria() == Categoria.CADETE);
		
		assertTrue(AdministradorDeCategorias.getCategoria(DateUtil.getDate(14, 5))
				.getIdCategoria() == Categoria.CADETE);
		
		assertTrue(AdministradorDeCategorias.getCategoria(DateUtil.getDate(19, 5))
				.getIdCategoria() == Categoria.CADETE);
		
		assertTrue(AdministradorDeCategorias.getCategoria(DateUtil.getDate(20))
				.getIdCategoria() == Categoria.CADETE);

		assertTrue(AdministradorDeCategorias.getCategoria(DateUtil.getDate(20, 5))
				.getIdCategoria() == Categoria.CADETE);
		
		assertTrue(AdministradorDeCategorias.getCategoria(DateUtil.getDate(21))
				.getIdCategoria() == Categoria.MAYOR);

		assertTrue(AdministradorDeCategorias.getCategoria(DateUtil.getDate(25))
				.getIdCategoria() == Categoria.MAYOR);

		
	}

	/**
     * Assembles and returns a test suite for
     * all the test methods of this test case.
     * @return A non-null test suite.
     */
    public static Test suite() { 
        return new TestSuite(TestAdministradorDeCategorias.class);
    }
	
	
}
