package main;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import utils.HibernateUtil;

public class AdministradorDeCategorias {

	public static List getCategorias() {

		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		Query q = s.createQuery("from Categoria");
		List l = q.list();
		
		s.getTransaction().commit();
		//Desata todos los grupos de la base de datos
		//Iterator i = l.iterator();
		//while (i.hasNext())	s.evict(i.next());
	
		return l;
	
	}	

}
