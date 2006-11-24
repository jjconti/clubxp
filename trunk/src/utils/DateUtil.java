package utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * @return La fecha actual a las 12 de la noche, del dia de hoy, menos
	 * aniosAtras años 
	 */
	public static Date getDate(int aniosAtras){
		
		Date d = new Date(Calendar.getInstance().getTime().getYear(),
				Calendar.getInstance().getTime().getMonth(),
				Calendar.getInstance().getTime().getDate());
		
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		
		now.add(Calendar.YEAR, - aniosAtras);

		return now.getTime();
		
	}
	
	public static Date getDate(int aniosAtras, int mesesAtras){
		
		Date d = new Date(Calendar.getInstance().getTime().getYear(),
				Calendar.getInstance().getTime().getMonth(),
				Calendar.getInstance().getTime().getDate());
		
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		
		now.add(Calendar.YEAR, - aniosAtras);
		now.add(Calendar.MONTH, - mesesAtras);

		return now.getTime();
		
	}
	
}
