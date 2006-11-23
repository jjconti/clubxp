/**
 * 
 */
package main;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import utils.HibernateUtil;

/**
 * 
 * AdministradorDeSocios se encarga de mapear instancias de socios entre la 
 * interfaz gráfica y la base de datos.
 *
 */
public class AdministradorDeSocios {

	
	public static Socio CrearSocio(int idZona, int idCategoria, String nombre, 
					 String apellido, String tipoDocumento, long documento,
					 Date fechaNacimiento, int edadAfiliacion) {
	
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		Zona zona = (Zona) s.get(Zona.class,new Integer(idZona)); 
		Categoria cat = (Categoria) s.get(Categoria.class, new Integer(idCategoria));
		boolean asociado;
		
		if (idCategoria == 1) {
			asociado = true;  
		} else {
			asociado = false;
		}
		
		Socio socio = new Socio(0,zona,cat,nombre.toUpperCase(),apellido.toUpperCase(),documento, fechaNacimiento, 
								edadAfiliacion, asociado, tipoDocumento );
		
		s.save(socio);
		s.getTransaction().commit();
	
		return socio;
	}
	
	public static Socio ModificarSocio(int idSocio, int idZona, int idCategoria, String nombre, 
			 String apellido, String tipoDocumento, long documento,
			 Date fechaNacimiento, int edadAfiliacion) {

		Session s = HibernateUtil.getSession();
		
		Socio socio = AdministradorDeSocios.ObtenerSocio(idSocio);
		
		s.beginTransaction();
	
		Zona zona = (Zona) s.get(Zona.class,new Integer(idZona)); 
		Categoria cat = (Categoria) s.get(Categoria.class, new Integer(idCategoria));
		boolean asociado;
	
		if (idCategoria == 1) {
			asociado = true;  
		} else {
			asociado = false;
		}
		
		socio.setZona(zona);
		socio.setApellido(apellido.trim().toUpperCase());
		socio.setNombre(nombre.trim().toUpperCase());
		socio.setCategoria(cat);
		socio.setDni(documento);
		socio.setEdadAfiliacion(edadAfiliacion);
		socio.setTipoDocumento(tipoDocumento);
		socio.setFechaNacimiento(fechaNacimiento);
		socio.setAsociado(asociado);
		
		s.saveOrUpdate(socio);
		s.getTransaction().commit();
	
	return socio;
}
	
	public static void EliminarSocio(int idSocio) throws Exception {
		Session s = HibernateUtil.getSession();
		Socio socio = ObtenerSocio(idSocio);
		
		try {
			s.beginTransaction();
			
			s.delete(socio);
			
			s.getTransaction().commit();
		} catch(Exception e){
			throw new Exception("No se puede eliminar el socio. " +
					"Compruebe que no sea Titular de un Grupo Familiar.");
		}
		
	}
	
	/***
	 * 
	 * @param idSocio
	 * @return socio o null
	 */
	public static Socio ObtenerSocio(int idSocio) {
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		Socio socio;
		try{
			socio = (Socio) s.get(Socio.class, new Integer(idSocio));
		}
		catch (Exception e){
			socio = null;
		}
		s.getTransaction().commit();
		
		return socio;
	}
	
	public static List getSocios(){
		
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		Query q = s.createQuery("from Socio");
		List l = q.list();
		
		s.getTransaction().commit();
		//Desata todos los grupos de la base de datos
		//Iterator i = l.iterator();
		//while (i.hasNext())	s.evict(i.next());
	
		return l;

	}
	
	public static void eliminarSocios(){
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		//Primero eliminamos a los asociados
		Query q = s.createQuery("delete from Socio s where s.asociado = true");
		q.executeUpdate();
		
		q = s.createQuery("delete from Socio");
		q.executeUpdate();
		
		s.getTransaction().commit();

	}
	
}
