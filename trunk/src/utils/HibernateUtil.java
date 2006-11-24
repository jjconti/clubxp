package utils;

import org.hibernate.*;
import org.hibernate.cfg.*;
import main.*;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final Configuration config;
    private static Session session;

    
    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	config = new Configuration().configure();
        	config.addClass(Categoria.class);
        	config.addClass(Cobrador.class);
        	config.addClass(Liquidacion.class);
        	config.addClass(Recibo.class);
        	config.addClass(Socio.class);
        	config.addClass(Zona.class);
        	
            sessionFactory = config.configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Session getSession() {

    	// [3.1] La crea si es nula o está cerrada
    	if (session == null || !session.isOpen()) {
	    	try {
	    	session = getSessionFactory().openSession();
	    	} catch (HibernateException e) {
	    // Actuamos en consecuencia
	    	}
    	}
    		return session;
    	}
    
    public static void closeSession(){
     	if (session != null && session.isOpen()) {
	    	try {
	    		session.close();
	    	} catch (HibernateException e) {
	 
	    	}
    	} 
    }

}

