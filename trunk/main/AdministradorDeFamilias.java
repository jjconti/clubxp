package main;

import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;

import utils.DateUtil;
import utils.HibernateUtil;
import utils.ValidadorException;

public class AdministradorDeFamilias {

	static public List getTitulares() {
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		Query q = s.createQuery("select distinct s.socio from Socio s where s.asociado = true");
		List l = q.list();
		
		s.getTransaction().commit();
		return l;
	}
	
	static public List getAsociados(int idTitular) {
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		Query q = s.createQuery("from Socio s where s.socio.idSocio = " + idTitular);
		List l = q.list();
		
		s.getTransaction().commit();
		return l;		
	}
	
	static public void CrearFamilia(int idTitular, List asociados) throws ValidadorException {
		Socio titular = AdministradorDeSocios.ObtenerSocio(idTitular);
		Categoria familiar = AdministradorDeCategorias.getCategoriaFamiliar();
		
		if (!isValidTitular(titular)) 
			throw new ValidadorException("El titular debe ser mayor de edad.");
		
		Iterator i = asociados.iterator();
		while(i.hasNext()){
			Socio asociado = AdministradorDeSocios.ObtenerSocio(((Integer)i.next()).intValue());
			//auxiliar.addElement(asociado);
			asociado.setSocio(titular);
			asociado.setAsociado(true);
			asociado.setCategoria(familiar);
			titular.getSocios().add(asociado);
		}
		titular.setCategoria(familiar);
		
		//Para que grabe los cambios a la base de datos
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		s.update(titular);
		s.getTransaction().commit();
		
	}

	
	static public boolean isValidTitular(Socio titular) {
		
		Date now = DateUtil.getDate(21);
		
		return (titular.getFechaNacimiento().compareTo(now) <= 0);
		
	}
	
	static public List getSociosSinGrupoFamiliar() {
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		Query q = s.createQuery("from Socio s where s.categoria != 1 AND s.categoria != 5");
		List l = q.list();
		
		s.getTransaction().commit();
		return l;		
	}
	
	static public void EliminarFamilia(int idTitular){
		Socio titular = AdministradorDeSocios.ObtenerSocio(idTitular);
		
		Iterator i = titular.getSocios().iterator();
		while (i.hasNext()){
			Socio asociado = (Socio) i.next();
			asociado.setAsociado(false);
			asociado.setSocio(null);
			asociado.setCategoria(AdministradorDeCategorias
					.getCategoria(asociado.getFechaNacimiento()));
		}
		
		titular.setCategoria(AdministradorDeCategorias
				.getCategoria(titular.getFechaNacimiento()));
		
		titular.getSocios().clear();
		
		//Para que grabe los cambios a la base de datos
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		s.update(titular);
		s.getTransaction().commit();
		
	}
	
	
}
