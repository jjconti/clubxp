package main;

import utils.DateUtil;
import utils.ValidadorException;

public class CargadorDB {

	/**
	 * @param args
	 * @throws ValidadorException 
	 */
	public static void main(String[] args) throws ValidadorException {

		//Elimina socios, recibos y liquidaciones
		AdministradorDeSocios.eliminarSocios();
		
		//Socios en zona 1
		AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "Andrea Gabriela", "D'Angelo", "DNI", 31445670, DateUtil.getDate(25), 1);
		AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "Juan Ignacio", "Díaz", "DNI", 31445671, DateUtil.getDate(25), 1);
		AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "José", "De La Fuente", "DNI", 31445672, DateUtil.getDate(25), 1);
		AdministradorDeSocios.CrearSocio(1, Categoria.MENOR, "Cecilia", "Gimenez", "DNI", 31445673, DateUtil.getDate(5), 1);
		AdministradorDeSocios.CrearSocio(1, Categoria.MENOR, "Silvana", "Nuñez", "DNI", 31445674, DateUtil.getDate(4), 1);
		//Socis en zona 2
		AdministradorDeSocios.CrearSocio(2, Categoria.MAYOR, "Gabriel", "Iturraspe Freyre", "DNI", 31445675, DateUtil.getDate(25), 1);
		AdministradorDeSocios.CrearSocio(2, Categoria.MAYOR, "Bruno", "Lürig", "DNI", 31445676, DateUtil.getDate(25), 1);
		AdministradorDeSocios.CrearSocio(2, Categoria.MAYOR, "Ana", "Fernandez", "DNI", 31445677, DateUtil.getDate(25), 1);
		AdministradorDeSocios.CrearSocio(2, Categoria.MAYOR, "Andrés", "Perez", "DNI", 31445678, DateUtil.getDate(25), 1);
		//Socios en zona 3
		AdministradorDeSocios.CrearSocio(3, Categoria.MAYOR, "Lautaro", "Santucci", "DNI", 31445679, DateUtil.getDate(25), 1);
		AdministradorDeSocios.CrearSocio(3, Categoria.MAYOR, "Exequiel", "Kreig", "DNI", 31445680, DateUtil.getDate(25), 1);
		AdministradorDeSocios.CrearSocio(3, Categoria.MAYOR, "Gimena", "Dominguez", "DNI", 31445681, DateUtil.getDate(25), 1);
		AdministradorDeSocios.CrearSocio(3, Categoria.MAYOR, "Lucia", "Gomez", "DNI", 31445682, DateUtil.getDate(25), 1);
		
		//Socios en zona 4
		AdministradorDeSocios.CrearSocio(4, Categoria.MAYOR, "Nicolás", "Gutierrez", "DNI", 33222383, DateUtil.getDate(25), 1);
		
		
		
	}

}
