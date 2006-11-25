-- phpMyAdmin SQL Dump
-- version 2.8.0.3
-- http://www.phpmyadmin.net
-- 
-- Servidor: localhost
-- Tiempo de generación: 25-11-2006 a las 17:43:49
-- Versión del servidor: 5.0.21
-- Versión de PHP: 5.1.4
-- 
-- Base de datos: `dbclub`
-- 

-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `categoria`
-- 

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria` (
  `id_categoria` int(10) unsigned NOT NULL auto_increment,
  `cuota` float NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `edad_min` int(10) unsigned NOT NULL,
  `edad_max` int(10) unsigned NOT NULL,
  `cuota_str` varchar(45) NOT NULL,
  PRIMARY KEY  (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

-- 
-- Volcar la base de datos para la tabla `categoria`
-- 

INSERT INTO `categoria` VALUES (1, 40, 'Familiar', 0, 999, 'CUARENTA');
INSERT INTO `categoria` VALUES (2, 10, 'Menores', 0, 13, 'DIEZ');
INSERT INTO `categoria` VALUES (3, 15, 'Cadetes', 14, 20, 'QUINCE');
INSERT INTO `categoria` VALUES (4, 20, 'Mayores', 21, 999, 'VEINTE');
INSERT INTO `categoria` VALUES (5, 0, 'Vitalicio', 0, 999, 'LOS VITALICIOS NO PAGAN!!!');

-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `cobrador`
-- 

DROP TABLE IF EXISTS `cobrador`;
CREATE TABLE `cobrador` (
  `id_cobrador` int(10) unsigned NOT NULL auto_increment,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `dni` bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (`id_cobrador`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

-- 
-- Volcar la base de datos para la tabla `cobrador`
-- 

INSERT INTO `cobrador` VALUES (1, 'Emanuel', 'Banchio', 30839393);
INSERT INTO `cobrador` VALUES (2, 'Federico', 'Gomez', 28884933);
INSERT INTO `cobrador` VALUES (3, 'Harry', 'Portel', 22334554);
INSERT INTO `cobrador` VALUES (16, 'Pepe', 'NoExist', 31331331);

-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `liquidacion`
-- 

DROP TABLE IF EXISTS `liquidacion`;
CREATE TABLE `liquidacion` (
  `id_liq` int(10) unsigned NOT NULL auto_increment,
  `mes` int(10) unsigned NOT NULL,
  `anio` int(10) unsigned NOT NULL,
  `fecha` datetime NOT NULL,
  PRIMARY KEY  (`id_liq`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=42 ;

-- 
-- Volcar la base de datos para la tabla `liquidacion`
-- 


-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `recibo`
-- 

DROP TABLE IF EXISTS `recibo`;
CREATE TABLE `recibo` (
  `id_recibo` int(10) unsigned NOT NULL auto_increment,
  `numero_recibo` int(10) unsigned NOT NULL,
  `valor` float unsigned NOT NULL,
  `devuelto` tinyint(1) NOT NULL,
  `id_liq` int(10) unsigned default NULL,
  `id_socio` int(10) unsigned NOT NULL,
  `mes` int(10) unsigned NOT NULL,
  `anio` int(10) unsigned NOT NULL,
  `valor_str` varchar(45) NOT NULL,
  PRIMARY KEY  (`id_recibo`),
  KEY `FK_recibo_1` (`id_liq`),
  KEY `FK_recibo_2` (`id_socio`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=155 ;

-- 
-- Volcar la base de datos para la tabla `recibo`
-- 


-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `socio`
-- 

DROP TABLE IF EXISTS `socio`;
CREATE TABLE `socio` (
  `id_socio` int(10) unsigned NOT NULL auto_increment,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `dni` bigint(20) unsigned NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `edad_afiliacion` int(10) unsigned NOT NULL,
  `id_titular` int(10) unsigned default NULL,
  `id_zona` int(10) unsigned NOT NULL,
  `id_categoria` int(10) unsigned NOT NULL,
  `tipo_documento` varchar(5) NOT NULL,
  PRIMARY KEY  (`id_socio`),
  KEY `FK_socio1` (`id_titular`),
  KEY `FK_socio2` (`id_zona`),
  KEY `FK_socio3` (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 10240 kB' AUTO_INCREMENT=237 ;

-- 
-- Volcar la base de datos para la tabla `socio`
-- 


-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `zona`
-- 

DROP TABLE IF EXISTS `zona`;
CREATE TABLE `zona` (
  `id_zona` int(10) unsigned NOT NULL auto_increment,
  `id_cobrador` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`id_zona`),
  KEY `FK_zona_1` (`id_cobrador`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

-- 
-- Volcar la base de datos para la tabla `zona`
-- 

INSERT INTO `zona` VALUES (1, 1);
INSERT INTO `zona` VALUES (2, 1);
INSERT INTO `zona` VALUES (3, 1);
INSERT INTO `zona` VALUES (4, 2);
INSERT INTO `zona` VALUES (5, 3);

-- 
-- Filtros para las tablas descargadas (dump)
-- 

-- 
-- Filtros para la tabla `recibo`
-- 
ALTER TABLE `recibo`
  ADD CONSTRAINT `FK_recibo_1` FOREIGN KEY (`id_liq`) REFERENCES `liquidacion` (`id_liq`),
  ADD CONSTRAINT `FK_recibo_2` FOREIGN KEY (`id_socio`) REFERENCES `socio` (`id_socio`);

-- 
-- Filtros para la tabla `socio`
-- 
ALTER TABLE `socio`
  ADD CONSTRAINT `FK_socio1` FOREIGN KEY (`id_titular`) REFERENCES `socio` (`id_socio`),
  ADD CONSTRAINT `FK_socio2` FOREIGN KEY (`id_zona`) REFERENCES `zona` (`id_zona`),
  ADD CONSTRAINT `FK_socio3` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`);

-- 
-- Filtros para la tabla `zona`
-- 
ALTER TABLE `zona`
  ADD CONSTRAINT `FK_zona_1` FOREIGN KEY (`id_cobrador`) REFERENCES `cobrador` (`id_cobrador`);