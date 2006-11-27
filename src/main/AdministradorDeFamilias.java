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
		//s.beginTransaction();
		
		Query q = s.createQuery("select distinct s.socio from Socio s");
		List l = q.list();
		
		//s.getTransaction().commit();
		return l;
	}
	
	static public List getAsociados(int idTitular) {
		Session s = HibernateUtil.getSession();
		//s.beginTransaction();
		
		Query q = s.createQuery("from Socio s where s.socio.idSocio = " + idTitular);
		List l = q.list();
		
		//s.getTransaction().commit();
		return l;		
	}
	
	static public void CrearFamilia(int idTitular, List asociados) throws ValidadorException {
		Socio titular = AdministradorDeSocios.ObtenerSocio(idTitular);
		Categoria familiar = AdministradorDeCategorias.getCategoriaFamiliar();
		
		if (!esMayorDeEdad(titular)) 
			throw new ValidadorException("El titular debe ser mayor de edad.");
		
		//Comprueba que los asociados no tengan deudas, y que haya como mucho un asociado mayor de edad
		Iterator i = asociados.iterator();
		int mayores = 0;
		while(i.hasNext()){
			Integer idSocio = (Integer)i.next();
			int deuda = AdministradorDeLiquidaciones.mesesQueDebe(idSocio.intValue());
			if (deuda > 0 )
				throw new ValidadorException("El socio número " + idSocio + " adeuda " 
						+ deuda + ((deuda == 1) ? " mes." : " meses."));
			
			Socio asociado = AdministradorDeSocios.ObtenerSocio(idSocio.intValue());
			if (esMayorDeEdad(asociado))
				mayores++;
			
			if (mayores > 1)
				throw new ValidadorException("Solo puede haber 2 socios mayores de edad en un " +
						"grupo familiar.");
			
		}
		
		//Comprueba que el titular no tenga deudas
		int deuda = AdministradorDeLiquidaciones.mesesQueDebe(idTitular);
		if (deuda > 0 )
			throw new ValidadorException("El socio número " + idTitular + " adeuda " 
					+ deuda + ((deuda == 1) ? "mes." : " meses."));
		
		
		i = asociados.iterator();
		while(i.hasNext()){
			Socio asociado = AdministradorDeSocios.ObtenerSocio(((Integer)i.next()).intValue());
			//auxiliar.addElement(asociado);
			asociado.setSocio(titular);
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

	
	static public boolean esMayorDeEdad(Socio titular) {
		
		Date now = DateUtil.getDate(21);
		
		return (titular.getFechaNacimiento().compareTo(now) <= 0);
		
	}
	
	static public List getSociosSinGrupoFamiliar() {
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		//Todos los que no categoria familiar o vitalicio
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
	
	public static void eliminarFamilias(){
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		//Primero eliminamos a los asociados
		Query q = s.createQuery("update Socio s set s.asociado = false, s.socio = null");
		q.executeUpdate();
		
		s.getTransaction().commit();

	}
	
	
}
