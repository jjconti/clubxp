/**
 * 
 */
package main;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import utils.HibernateUtil;
import utils.ValidadorException;

/**
 * 
 * AdministradorDeSocios se encarga de mapear instancias de socios entre la 
 * interfaz gráfica y la base de datos.
 *
 */
public class AdministradorDeSocios {

	
	public static Socio CrearSocio(int idZona, int idCategoria, String nombre, 
					 String apellido, String tipoDocumento, long documento,
					 Date fechaNacimiento, int edadAfiliacion) throws ValidadorException {
	
		if (idCategoria == Categoria.FAMILIAR) 
			throw new ValidadorException("No se pueden crear socios con categoría Familiar");
		
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		Zona zona = (Zona) s.get(Zona.class,new Integer(idZona)); 
		Categoria cat = (Categoria) s.get(Categoria.class, new Integer(idCategoria));
		
		Socio socio = new Socio(0,zona,cat,nombre.toUpperCase(),apellido.toUpperCase(),documento, fechaNacimiento, 
								edadAfiliacion, tipoDocumento );
		
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
		
		socio.setZona(zona);
		socio.setApellido(apellido.trim().toUpperCase());
		socio.setNombre(nombre.trim().toUpperCase());
		socio.setCategoria(cat);
		socio.setDni(documento);
		socio.setEdadAfiliacion(edadAfiliacion);
		socio.setTipoDocumento(tipoDocumento);
		socio.setFechaNacimiento(fechaNacimiento);
		
		s.saveOrUpdate(socio);
		s.getTransaction().commit();
	
	return socio;
}
	
	public static void EliminarSocio(int idSocio) throws Exception {
		Session s = HibernateUtil.getSession();
		Socio socio = ObtenerSocio(idSocio);
		
		try {
			s.beginTransaction();
			
			//Primero eliminamos los recibos que apuntan a este socio.
			Query q = s.createQuery("delete from Recibo r where r.socio.idSocio = " + idSocio);
			q.executeUpdate();
			 
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
	
		return (Socio) s.get(Socio.class, new Integer(idSocio));
			
	}
	
	public static List getSocios(){
		
		Session s = HibernateUtil.getSession();
		
		Query q = s.createQuery("from Socio");
		List l = q.list();
	
		return l;

	}
	
	public static void eliminarSocios(){
		
		AdministradorDeLiquidaciones.eliminarLiquidaciones();
		
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		//Primero eliminamos a los asociados
		Query q = s.createQuery("delete from Socio s where s.socio != null");
		q.executeUpdate();
		
		q = s.createQuery("delete from Socio");
		q.executeUpdate();
		
		s.getTransaction().commit();

	}
	
}
