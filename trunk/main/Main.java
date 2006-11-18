package main;

import java.util.Date;
import org.hibernate.Session;
import utils.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Session s = HibernateUtil.getSession();
	
		System.out.println("Okey...");
		
		Date fechaNac = new Date("10/15/1990");
		Socio socio1 = AdministradorDeSocios.CrearSocio(1, 2, "Socio1", "Apel'lido1", "DNI", 33222333, fechaNac, 1);
		
		socio1.setApellido("CAMBI'OOOOO");
		socio1.setNombre("oeoe");
		
		s.saveOrUpdate(socio1);
		s.flush();
		
		//s.beginTransaction().commit();
		
	}

}
