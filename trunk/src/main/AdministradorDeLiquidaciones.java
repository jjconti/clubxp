package main;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import utils.HibernateUtil;

public class AdministradorDeLiquidaciones {
	
	public static boolean existenLiquidaciones = false;
	
	
	//Este método es utilizado en las pruebas
	public static Liquidacion setUltimaLiquidacion(int mes, int anio){
		
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		Liquidacion liq = new Liquidacion();
		
		liq.setMes(mes);
		liq.setAnio(anio);
		
		Calendar fecha = Calendar.getInstance();
		fecha.set(anio, mes, 1);
		Date fecha1mesanio = fecha.getTime();
		liq.setFecha(fecha1mesanio);
		
		s.save(liq);
		s.getTransaction().commit();
		
		return liq;
	}

	//Este método es utilizado en las pruebas
	public static void eliminarLiquidacion(Liquidacion l) {
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
	
		s.delete(l);
		s.getTransaction().commit();
	}
	
	public static Liquidacion getUltimaLiquidacion(){
		
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		Query q = s.createQuery("Select idLiq from Liquidacion order by idLiq asc)");
		List lista = q.list();
		
		Liquidacion liq;
		
		// Si no existen liquidaciones en la base de datos, 
		// se crea una liquidacion con el mes y año actual 
		// permitiendo liquidar el mes próximo
		if (lista.isEmpty()){
			
			Date hoy = Calendar.getInstance().getTime(); 
			liq = setUltimaLiquidacion(hoy.getMonth() + 1, hoy.getYear() + 1900);
			
		} else {

			Integer id = (Integer)lista.get(lista.size()-1);
			liq = (Liquidacion)s.get(Liquidacion.class, id);
		
			s.getTransaction().commit();
		}
		return liq;
	}
	
	public static boolean sePuedeLiquidar(Date hoy) {
		
		Liquidacion ultimaLiq = getUltimaLiquidacion();
		
		int mesUltimaLiq = ultimaLiq.getMes() - 1;
		int anioUltimaLiq = ultimaLiq.getAnio();
		
		int diaHoy = hoy.getDate();
		int mesHoy = hoy.getMonth();
		int anioHoy = hoy.getYear() + 1900;
		
		if (anioUltimaLiq == anioHoy){
			
			if (mesUltimaLiq < mesHoy){
				return true;
			} else if (mesUltimaLiq == mesHoy){
				if (diaHoy >= 20){
					return true;
				}
			} 
			return false;
			
		} else if (anioUltimaLiq < anioHoy){
					return true;
		}else {
			return false;
		}
	}
		

	public static Integer getAnioProximaLiq() {
		
		Liquidacion l = getUltimaLiquidacion();
				
		if (l.getMes() == 12){
			return new Integer(l.getAnio() + 1);
		} else {
			return new Integer(l.getAnio());
		}
	}

	public static Integer getMesProximaLiq() {
		
		Liquidacion l = getUltimaLiquidacion();
		
		if (l.getMes() == 12){
			return new Integer(1);
		} else {
			return new Integer(l.getMes() + 1);
		}
		
	}
	
	public static void HacerLiquidacion() {
		
		
	}



}
