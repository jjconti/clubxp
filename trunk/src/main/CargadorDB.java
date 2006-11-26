package main;

import java.util.Vector;

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
		AdministradorDeSocios.CrearSocio(4, Categoria.MAYOR, "Nicolás", "Gutierrez", "DNI", 31445683, DateUtil.getDate(25), 1);
		
		
		//Socios que van a estar en grupos familiares
		Socio socio1 = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "Pepe", "Argento", "DNI", 31445684, DateUtil.getDate(25), 1);
		Socio socio2 = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "Mónica", "Argento", "DNI", 31445685, DateUtil.getDate(25), 1);
		Socio socio3 = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "Coqui", "Argento", "DNI", 31445686, DateUtil.getDate(25), 1);
		Socio socio4 = AdministradorDeSocios.CrearSocio(1, Categoria.MAYOR, "NoSeComoSeLlamaLaRubia", "Argento", "DNI", 31445687, DateUtil.getDate(25), 1);
		
		//Crea el grupo familiar
		Vector asociados = new Vector();
		asociados.add(socio2.getIdSocioI());
		asociados.add(socio3.getIdSocioI());
		asociados.add(socio4.getIdSocioI());
		AdministradorDeFamilias.CrearFamilia(socio1.getIdSocio(), asociados);	
		
		
		System.out.println("Okey, todo cargado!");
		
	}

}
