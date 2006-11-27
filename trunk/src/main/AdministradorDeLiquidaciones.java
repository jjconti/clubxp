package main;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import utils.HibernateUtil;
import utils.ValidadorException;

public class AdministradorDeLiquidaciones {
	
	
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
		
	// Para que se pueda probar el sistema sin tener que cambiar la fecha
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
	
	/**
	 * Para cada socio, si no hay recibos anteriores, es porque el socio no debe nada.
	 * (el socio es nuevo, o todavía no se han hecho liquidaciones) 
	 * 
	 * Si SI hay recibos, se hará o no la liquidación del socio de acuerdo a la cantidad de recibos impagos.
	 */
	public static void HacerLiquidacion() {
		
		List socios = AdministradorDeSocios.getSocios();
		Iterator s = socios.iterator();
		int mesActual = getMesProximaLiq().intValue();
		int anioActual = getAnioProximaLiq().intValue();
		int nroRecibo = getNumeroUltimoRecibo() + 1;
		
		Liquidacion liquidacion = new Liquidacion(0, mesActual, anioActual, Calendar.getInstance().getTime());
		
		while (s.hasNext()){
			
			Socio socio = (Socio) s.next();
	
			if (socio.getCategoria().getIdCategoria() == Categoria.VITALICIO) 
				continue;
			if (socio.isAsociado()) 
				continue; 
			
			List recibos = getRecibos(socio);	//Trae todos los recibos para socio
			
			int debe = mesesQueDebe(recibos,mesActual,anioActual); 
			
			if (debe <= 1){
				//Crea el recibo para el mes actual.
				Recibo rActual = new Recibo(0,socio, liquidacion, mesActual, anioActual, nroRecibo,
						socio.getCategoria().getCuota(), false, socio.getCategoria().getCuotaStr(), 
						socio.getCategoria());
				
				nroRecibo++;
				liquidacion.getRecibos().add(rActual);
			}
			if (debe == 1){
				int anio = anioActual;
				int mes = mesActual - 1;
				if (mes == 0) {
					mes = 12;
					anio--;
				}
				
				//Crea el recibo para el mes anterior.
				Recibo rAnterior = new Recibo(0,socio, liquidacion, mes, anio, nroRecibo,
						socio.getCategoria().getCuota(), false, socio.getCategoria().getCuotaStr(),
						socio.getCategoria());
				
				nroRecibo++;
				liquidacion.getRecibos().add(rAnterior);
			}
				
		}//Fin while(i)
		
		//Ahora si, guardar la liquidacion...
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		
		session.save(liquidacion);
		
		session.getTransaction().commit();
	
	}

	private static int getNumeroUltimoRecibo() {
		Liquidacion ultima = getUltimaLiquidacion();
		int nroMax = 0;
		
		Iterator i = ultima.getRecibos().iterator();
		while (i.hasNext()){
			Recibo r = (Recibo) i.next();
			if (r.getNumeroRecibo() > nroMax) 
				nroMax = r.getNumeroRecibo();
		}
		
		return nroMax;
	}

	public static void eliminarLiquidaciones(){
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		//Primero elimina todos los recibos
		Query q = s.createQuery("delete from Recibo");
		q.executeUpdate();
		
		//Eliminamos todas las liquidaciones
		q = s.createQuery("delete from Liquidacion");
		q.executeUpdate();
		
		s.getTransaction().commit();
	}
	
	public static void guardarRecibo(Recibo recibo){
		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		
		s.save(recibo); 
		
		s.getTransaction().commit();
		
	}
	
	public static List getRecibos(Socio socio){
		Session s = HibernateUtil.getSession();
		
		Query q = s.createQuery("from Recibo r where r.socio.idSocio = " + socio.getIdSocio());
		List lista = q.list();
		
		return lista;
		
	}
	
	public static List getRecibos(){
		Session s = HibernateUtil.getSession();
		
		Query q = s.createQuery("from Recibo");
		List lista = q.list();
		
		return lista;
		
	}
	
//	public static List getPrimerReciboQueDebe(Socio socio){
//		Session s = HibernateUtil.getSession();
//		
//		Query q = s.createQuery("from Recibo r where r.socio.idSocio = " + socio.getIdSocio() +
//				"AND r.devuelto = true ORDER by r.numeroRecibo ASC"
//				);
//		List lista = q.list();
//		
//		return lista;
//		
//	}
	
	private static boolean tienePagadoMes(List recibos, int mes, int anio){
		
		Iterator i = recibos.iterator();
		while (i.hasNext()){
			Recibo r = (Recibo) i.next();
			if (r.getMes() == mes && r.getAnio() == anio && !r.isDevuelto())
				return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param recibos Recibos del socio en cuestion
	 * @param mesActual Mes de la nueva liquidacion
	 * @param anioActual Año de la nueva liquidacion
	 * @return cantidad de meses que adeuda el socio del cual se pasaron los recibos. 
	 * (Sin contar el mes de la liquidacion) 
	 */
	public static int mesesQueDebe(List recibos, int mesActual, int anioActual){
		int cuenta = 0;

		if (recibos.isEmpty()) return 0;
		Recibo reciboMin = primerReciboLiquidado(recibos);
		
		
		do {
			mesActual--;
			if (mesActual == 0) {
				mesActual = 12;
				anioActual--;
			}

			if (!tienePagadoMes(recibos, mesActual, anioActual)){ 
				cuenta++;
			} else {
				return cuenta;
			}
				
		} while (anioActual > reciboMin.getAnio() ||
				(anioActual == reciboMin.getAnio() &&  mesActual > reciboMin.getMes()));
			
		return cuenta;
		
	}
	
	public static int mesesQueDebe(int idSocio){
		int mesActual = getMesProximaLiq().intValue();
		int anioActual = getAnioProximaLiq().intValue();
		
		Socio socio = AdministradorDeSocios.ObtenerSocio(idSocio);
		return mesesQueDebe(getRecibos(socio), mesActual, anioActual);
		
	}
	
	private static Recibo primerReciboLiquidado(List recibos){
		Recibo reciboMin = new Recibo();
		reciboMin.setAnio(Integer.MAX_VALUE);
		reciboMin.setMes(Integer.MAX_VALUE);
		
		Iterator i = recibos.iterator();
		while (i.hasNext()){
			Recibo r = (Recibo) i.next();
			if (r.getAnio() < reciboMin.getAnio() && r.getMes() < reciboMin.getMes()){
				reciboMin = r;
			}
		}
		
		return reciboMin;
		
	}

	public static boolean existenLiquidaciones() {
		return !getRecibos().isEmpty();
	}

	/* Control de recibos */
	
	/**
	 * Establece como devuelto un recibo, perteneciente a la liquidación
	 * actual. 
	 * @param numeroRecibo El recibo que se va a setear devuelto
	 * @param idZona Zona a la que pertenece el recibo. Se utiliza solo para
	 * validar el mismo.
	 */
	public static void setDevuelto(int numeroRecibo, int idZona, boolean valor) throws ValidadorException{
		Liquidacion ultimaLiq = AdministradorDeLiquidaciones.getUltimaLiquidacion();
		
		Session s = HibernateUtil.getSession();
		
		Query q = s.createQuery("from Recibo r where r.numeroRecibo = " + numeroRecibo);
		Recibo recibo = (Recibo) q.uniqueResult();
		
		if (recibo == null)
			throw new ValidadorException("No existe un recibo con ese número en la base de datos");
		
		if (recibo.getLiquidacion().getIdLiq() != ultimaLiq.getIdLiq())
			throw new ValidadorException("El recibo no pertenece a la última liquidación.");
		
		if (recibo.getSocio().getZona().getIdZona() != idZona)
			throw new ValidadorException("El recibo no pertenece a la zona seleccionada.");
		
		recibo.setDevuelto(valor);
		
		s.beginTransaction();
		s.update(recibo);
		s.getTransaction().commit();
		
	}
	/**
	 * 
	 * @param idZona Id de la zona de la cual se quieren los recibos
	 * @return Recibos de la ultima liquidacion, que pertenecen a idZona 
	 */
	public static List getRecibosDevueltos(int idZona){
		Session s = HibernateUtil.getSession();
		
		Liquidacion ultimaLiq = getUltimaLiquidacion();
		
		Query q = s.createQuery("from Recibo r where r.socio.zona.idZona = " + idZona +
				" AND r.liquidacion.idLiq = " + ultimaLiq.getIdLiq() +
				" AND r.devuelto = true ORDER BY r.numeroRecibo ASC" );
		List lista = q.list();
		
		return lista;
	}
	
	/**
	 * 
	 * @param idZona Id de la zona de la cual se quiere calcular el monto
	 * @return La suma del valor de los recibos no devueltos
	 */
	public static float calcularMontoRecibosNoDevueltos(int idZona){
		
		Session s = HibernateUtil.getSession();
		Liquidacion ultimaLiq = getUltimaLiquidacion();
		
		Query q = s.createQuery("select sum(r.valor) from Recibo r where r.socio.zona.idZona = " + idZona +
				" AND r.liquidacion.idLiq = " + ultimaLiq.getIdLiq() + 
				" AND r.devuelto = false");
		
		Object result = q.uniqueResult();
		
		if (result == null){
			result = new Float(0);
		}
		return ((Float) result).floatValue() ;
	}
	

	

}
