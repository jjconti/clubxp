package main;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;

import utils.DateUtil;
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

	public static Categoria getCategoriaFamiliar() {
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
	
		Categoria familiar = (Categoria) s.get(Categoria.class, new Integer(Categoria.FAMILIAR));
		
		s.getTransaction().commit();
	
		return familiar;
	}
	
	public static Categoria getCategoria(Date fechaNacimiento) {
		
		List categorias = getCategorias();
		Iterator i = categorias.iterator();
		while(i.hasNext()){
			Categoria cat = (Categoria) i.next();
			if (cat.getIdCategoria() == Categoria.FAMILIAR ||
					cat.getIdCategoria() == Categoria.VITALICIO)
				continue;
			
			//Fecha mas cercana
			Date max = DateUtil.getDate(cat.getEdadMin());
			//Fecha mas vieja
			Date min = DateUtil.getDate(cat.getEdadMax() + 1);
			
			if(fechaNacimiento.compareTo(min) > 0 &&
					fechaNacimiento.compareTo(max) <= 0){
				//Encontramos la categoría a la que pertenece
				return cat;
			}
			
		}

		return null;
	
	}
	
	
}
