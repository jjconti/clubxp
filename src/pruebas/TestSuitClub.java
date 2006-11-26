package pruebas;

import junit.framework.*;

public class TestSuitClub extends TestCase {

	/**
     * Assembles and returns a test suite
     * containing all known tests.
     *
     * New tests should be added here!
     *
     * @return A non-null test suite.
     */
    public static Test suite() {

        TestSuite suite = new TestSuite();
    
        suite.addTest(TestAdministradorDeSocios.suite());
        suite.addTest(TestDB.suite());
        suite.addTest(TestValidador.suite());
        suite.addTest(TestAdministradorDeFamilias.suite());
        suite.addTest(TestAdministradorDeCategorias.suite());
        suite.addTest(TestAdministradorDeLiquidaciones.suite());
        suite.addTest(TestControlDeRecibos.suite());
        return suite;
    }
	
}
