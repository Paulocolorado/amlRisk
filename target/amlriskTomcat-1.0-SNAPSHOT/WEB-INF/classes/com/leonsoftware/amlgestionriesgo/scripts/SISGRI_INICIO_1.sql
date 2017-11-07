/*
Scripts de creacion inicializacion de datos AMLRisk
version : 1.0
creado: 07/11/2017
*/
CREATE DATABASE `bd_sisgri` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `tb_archivo_cliente_masivo` (
  `id_archivo_cli_masivo` int(11) NOT NULL COMMENT 'identificador interno de archivo',
  `nombre_archivo_cli_masivo` varchar(100) NOT NULL COMMENT 'nombre del archivo',
  `fecha_carga` datetime DEFAULT NULL COMMENT 'fecha por defecto del sistema al cargar el archivo',
  `archivo_cargado` longblob NOT NULL,
  `fecha_creacion` datetime DEFAULT NULL COMMENT 'auditoria - fecha de creaciòn del registro ',
  `usuario_creacion` varchar(15) DEFAULT NULL COMMENT 'auditoria - usuario de creaciòn del registro ',
  `fecha_modificacion` datetime DEFAULT NULL COMMENT 'auditoria - fecha de modificaciòn del registro ',
  `usuario_modificacion` varchar(15) DEFAULT NULL COMMENT 'auditoria - usuario de modificaciòn del registro ',
  `tb_usuario_id_usuario` int(11) NOT NULL,
  `tb_usuario_tipo_id_usuario` varchar(3) NOT NULL,
  PRIMARY KEY (`id_archivo_cli_masivo`),
  KEY `fk_tb_archivo_fuente_tb_usuario1_idx` (`tb_usuario_id_usuario`,`tb_usuario_tipo_id_usuario`),
  CONSTRAINT `fk_tb_archivo_cliente_masivo_tb_usuario` FOREIGN KEY (`tb_usuario_id_usuario`, `tb_usuario_tipo_id_usuario`) REFERENCES `tb_usuario` (`id_usuario`, `tipo_id_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_archivo_fuente` (
  `id_archivo_fuente` int(11) NOT NULL COMMENT 'identificador interno de archivo',
  `nombre_archivo_fuente` varchar(100) NOT NULL COMMENT 'nombre del archivo',
  `nombre_fuente` varchar(15) NOT NULL COMMENT 'lista de listas corresponde a FUENTE_RIESGO',
  `nombre_accion` varchar(5) NOT NULL COMMENT 'lista de listas corresponde a ACCION_ARCHIVO',
  `fecha_carga` datetime DEFAULT NULL COMMENT 'fecha por defecto del sistema al cargar el archivo',
  `archivo_cargado` longblob NOT NULL,
  `fecha_creacion` datetime DEFAULT NULL COMMENT 'auditoria - fecha de creaciòn del registro ',
  `usuario_creacion` varchar(15) DEFAULT NULL COMMENT 'auditoria - usuario de creaciòn del registro ',
  `fecha_modificacion` datetime DEFAULT NULL COMMENT 'auditoria - fecha de modificaciòn del registro ',
  `usuario_modificacion` varchar(15) DEFAULT NULL COMMENT 'auditoria - usuario de modificaciòn del registro ',
  `tb_usuario_id_usuario` int(11) NOT NULL,
  `tb_usuario_tipo_id_usuario` varchar(3) NOT NULL,
  PRIMARY KEY (`id_archivo_fuente`),
  KEY `fk_tb_archivo_fuente_tb_usuario1_idx` (`tb_usuario_id_usuario`,`tb_usuario_tipo_id_usuario`),
  CONSTRAINT `fk_tb_archivo_fuente_tb_usuario1` FOREIGN KEY (`tb_usuario_id_usuario`, `tb_usuario_tipo_id_usuario`) REFERENCES `tb_usuario` (`id_usuario`, `tipo_id_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_catalogo` (
  `id_catalogo` varchar(50) NOT NULL COMMENT 'codigo para recuperar lista',
  `fecha_creacion` datetime DEFAULT NULL COMMENT 'auditoria - fecha de creaciòn del registro',
  `usuario_creacion` varchar(15) DEFAULT NULL COMMENT 'auditoria - usuario de creaciòn del registro ',
  `fecha_modificacion` datetime DEFAULT NULL COMMENT 'auditoria - fecha de modificaciòn del registro ',
  `usuario_modificacion` varchar(15) DEFAULT NULL COMMENT 'auditoria - usuario de modificaciòn del registro ',
  PRIMARY KEY (`id_catalogo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla para el manejo de catalogos de la aplicación';

CREATE TABLE `tb_cliente_masivo` (
  `tipo_id_cliente` varchar(15) NOT NULL COMMENT 'Tipo de identificacion del cliente a verificar relacionado con la lista FUENTE_RIESGO',
  `id_cliente` varchar(45) NOT NULL COMMENT 'Identificacion del cliente',
  `cliente_primer_nombre` varchar(100) DEFAULT NULL COMMENT 'primer nombre del cliente y/o razon social',
  `cliente_ultimo_nombre` varchar(100) DEFAULT NULL COMMENT 'ultimo nombre del cliente y/o razon social',
  `fecha_creacion` datetime DEFAULT NULL COMMENT 'auditoria - fecha de creaciòn del registro ',
  `usuario_creacion` varchar(45) DEFAULT NULL COMMENT 'auditoria - usuario de creaciòn del registro ',
  `fecha_modificacion` varchar(45) DEFAULT NULL COMMENT 'auditoria - fecha de modificaciòn del registro ',
  `usuario_modificacion` varchar(45) DEFAULT NULL COMMENT 'auditoria - usuario de modificaciòn del registro ',
  `id_archivo_cli_masivo` int(11) DEFAULT NULL COMMENT 'Identificador del archivo de carga',
  PRIMARY KEY (`tipo_id_cliente`,`id_cliente`),
  KEY `fk_tb_archivo_cli_masivo_tb_cliente_masivo` (`id_archivo_cli_masivo`),
  CONSTRAINT `ffk_tb_archivo_cli_masivo_tb_cliente_masivo` FOREIGN KEY (`id_archivo_cli_masivo`) REFERENCES `tb_archivo_cliente_masivo` (`id_archivo_cli_masivo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_lista_catalogo` (
  `nombre_lista_catalogo` varchar(50) NOT NULL,
  `valor_lista_catalogo` varchar(50) NOT NULL,
  `fecha_creacion` datetime DEFAULT NULL COMMENT 'auditoria - fecha de creaciòn del registro ',
  `usuario_creacion` varchar(15) DEFAULT NULL COMMENT 'auditoria - usuario de creaciòn del registro ',
  `fecha_modificacion` datetime DEFAULT NULL COMMENT 'auditoria - fecha de modificaciòn del registro ',
  `usuario_modificacion` varchar(45) DEFAULT NULL COMMENT 'auditoria - usuario de modificaciòn del registro ',
  `tb_catalogo_id_catalogo` varchar(50) NOT NULL,
  PRIMARY KEY (`valor_lista_catalogo`,`nombre_lista_catalogo`,`tb_catalogo_id_catalogo`),
  KEY `fk_tb_lista_catalogo_tb_catalogo1_idx` (`tb_catalogo_id_catalogo`),
  CONSTRAINT `fk_tb_lista_catalogo_tb_catalogo1` FOREIGN KEY (`tb_catalogo_id_catalogo`) REFERENCES `tb_catalogo` (`id_catalogo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='tabla que almacena los registros individuales de cada catalo';

CREATE TABLE `tb_lista_restriccion` (
  `lista_id_registro` double NOT NULL COMMENT 'Onu: DATAID\nOfac: uid',
  `lista_primer_nombre` varchar(500) DEFAULT NULL COMMENT 'Onu: FIRST_NAME\nOfac: firstName',
  `lista_ultimo_nombre` varchar(500) DEFAULT NULL COMMENT 'Onu: SECOND_NAME\nOfac: lastName',
  `lista_fecha_reporte` datetime DEFAULT NULL COMMENT 'Onu: LISTED_ON\nOfac: n/a',
  `lista_observacion` varchar(5000) DEFAULT NULL COMMENT 'Onu: COMMENTS1\nOfac: title',
  `fecha_creacion` datetime DEFAULT NULL COMMENT 'auditoria - fecha de creaciòn del registro ',
  `usuario_creacion` varchar(15) DEFAULT NULL COMMENT 'auditoria - usuario de creaciòn del registro ',
  `fecha_modificacion` datetime DEFAULT NULL COMMENT 'auditoria - fecha de modificaciòn del registro ',
  `usuario_modificacion` varchar(15) DEFAULT NULL COMMENT 'auditoria - usuario de modificaciòn del registro ',
  `tb_archivo_fuente_id_archivo_fuente` int(11) NOT NULL,
  PRIMARY KEY (`lista_id_registro`,`tb_archivo_fuente_id_archivo_fuente`),
  KEY `IDX_NOMBRE` (`lista_primer_nombre`,`lista_ultimo_nombre`),
  KEY `fk_tb_lista_restriccion_tb_archivo_fuente1_idx` (`tb_archivo_fuente_id_archivo_fuente`),
  CONSTRAINT `fk_tb_lista_restriccion_tb_archivo_fuente1` FOREIGN KEY (`tb_archivo_fuente_id_archivo_fuente`) REFERENCES `tb_archivo_fuente` (`id_archivo_fuente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla que mantiene las listas restrictivas';

CREATE TABLE `tb_trazabilidad` (
  `fecha_trazabilidad` datetime NOT NULL,
  `valor_trazabilidad` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_usuario` (
  `tipo_id_usuario` varchar(3) NOT NULL COMMENT 'tipo de identificaciòn de usuario',
  `id_usuario` int(11) NOT NULL COMMENT 'identificacion del usuario',
  `nombre_usuario` varchar(45) NOT NULL COMMENT 'nombres del usuario',
  `apellido_usuario` varchar(45) NOT NULL COMMENT 'apellidos del usuario',
  `usuario` varchar(15) NOT NULL COMMENT 'nombre de usuario para el ingreso a la aplicaciòn',
  `clave_usuario` varchar(15) NOT NULL COMMENT 'contraseña de ingreso del usuario',
  `correo_usuario` varchar(45) DEFAULT NULL COMMENT 'correo electronico del usuario',
  `fecha_creacion` datetime NOT NULL COMMENT 'auditoria - fecha de creaciòn del registro ',
  `usuario_creacion` varchar(15) NOT NULL COMMENT 'auditoria - usuario de creaciòn del registro ',
  `fecha_modificacion` datetime NOT NULL COMMENT 'auditoria - fecha de modificaciòn del registro ',
  `usuario_modificacion` varchar(15) NOT NULL COMMENT 'auditoria - usuario de modificaciòn del registro ',
  PRIMARY KEY (`id_usuario`,`tipo_id_usuario`),
  UNIQUE KEY `usuario_UNIQUE` (`usuario`,`tipo_id_usuario`,`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla para el manejo de usuarios del Sistema SISGRI';



--Inserts basicos


/*
Tabla de usuario por defecto
*/

INSERT INTO `tb_usuario` VALUES ('GEN',12345,'GENERICO','ADMIN','adminSisgri','12345',NULL,'2017-08-30 16:10:26','root@localhost','2017-08-30 16:10:26','root@localhost');

/*
Tabla tb_catalogo
*/

INSERT INTO `tb_catalogo` VALUES ('ACCION_ARCHIVO','2017-10-09 14:59:50','adminSisgri','2017-10-09 14:59:50','adminSisgri');
INSERT INTO `tb_catalogo` VALUES ('FUENTE_RIESGO','2017-10-09 14:59:50','adminSisgri','2017-10-09 14:59:50','adminSisgri');
INSERT INTO `tb_catalogo` VALUES ('TIPO_ID','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSisgri');
INSERT INTO `tb_catalogo` VALUES ('TXT_EXTERNA','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSisgri');
INSERT INTO `tb_catalogo` VALUES ('XML_OFAC','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSisgri');
INSERT INTO `tb_catalogo` VALUES ('XML_ONU','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSisgri');


/*
Tabla tb_lista_catalogo
*/

INSERT INTO `tb_lista_catalogo` VALUES ('separador',',','2017-10-09 14:59:50','adminSisgri','2017-10-09 14:59:50','adminSIsgri','TXT_EXTERNA');
INSERT INTO `tb_lista_catalogo` VALUES ('nodo2','//ENTITIES/ENTITY','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSisgri','XML_ONU');
INSERT INTO `tb_lista_catalogo` VALUES ('nodo1','//INDIVIDUALS/INDIVIDUAL','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSisgri','XML_ONU');
INSERT INTO `tb_lista_catalogo` VALUES ('nodo1','//sdnEntry','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSisgri','XML_OFAC');
INSERT INTO `tb_lista_catalogo` VALUES ('observacion','/COMMENTS1','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSIsgri','XML_ONU');
INSERT INTO `tb_lista_catalogo` VALUES ('nombre_uno','/firstName','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSisgri','XML_OFAC');
INSERT INTO `tb_lista_catalogo` VALUES ('nombre_uno','/FIRST_NAME','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSIsgri','XML_ONU');
INSERT INTO `tb_lista_catalogo` VALUES ('nombre_dos','/lastName','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSisgri','XML_OFAC');
INSERT INTO `tb_lista_catalogo` VALUES ('fecha_reporte','/LISTED_ON','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSisgri','XML_OFAC');
INSERT INTO `tb_lista_catalogo` VALUES ('fecha_reporte','/LISTED_ON','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSIsgri','XML_ONU');
INSERT INTO `tb_lista_catalogo` VALUES ('nombre_dos','/SECOND_NAME','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSIsgri','XML_ONU');
INSERT INTO `tb_lista_catalogo` VALUES ('observacion','/title','2017-10-17 14:59:50','adminSisgri','2017-10-17 14:59:50','adminSisgri','XML_OFAC');
INSERT INTO `tb_lista_catalogo` VALUES ('CEDULA CIUDADANIA','CC','2017-10-09 14:59:50','adminSisgri','2017-10-09 14:59:50','adminSIsgri','TIPO_ID');
INSERT INTO `tb_lista_catalogo` VALUES ('CEDULA DE EXTRANJERIA','CE','2017-10-09 14:59:50','adminSisgri','2017-10-09 14:59:50','adminSIsgri','TIPO_ID');
INSERT INTO `tb_lista_catalogo` VALUES ('Carga','CRG','2017-10-09 14:59:50','adminSisgri','2017-10-09 14:59:50','adminSIsgri','ACCION_ARCHIVO');
INSERT INTO `tb_lista_catalogo` VALUES ('Externa','EXTERNA','2017-10-09 14:59:50','adminSisgri','2017-10-09 14:59:50','adminSIsgri','FUENTE_RIESGO');
INSERT INTO `tb_lista_catalogo` VALUES ('Ofac','OFAC','2017-10-09 14:59:50','adminSisgri','2017-10-09 14:59:50','adminSIsgri','FUENTE_RIESGO');
INSERT INTO `tb_lista_catalogo` VALUES ('Onu','ONU','2017-10-09 14:59:50','adminSisgri','2017-10-09 14:59:50','adminSIsgri','FUENTE_RIESGO');


--disparadores
--VERSION 1.0

CREATE DEFINER=`usuSisgri`@`localhost` TRIGGER tg_archivo_fuente AFTER INSERT ON tb_archivo_fuente FOR EACH ROW
BEGIN

 
 DECLARE i INT UNSIGNED DEFAULT 0;
 DECLARE j INT UNSIGNED DEFAULT 0;
 DECLARE row_count INT UNSIGNED DEFAULT 0;
 DECLARE num_reg INT UNSIGNED;
 DECLARE xpath TEXT;
 DECLARE nombre_uno TEXT DEFAULT 'nombre_uno';
 DECLARE nombre_dos TEXT DEFAULT 'nombre_dos';
 DECLARE fecha_reporte TEXT DEFAULT 'fecha_reporte';
 DECLARE observacion TEXT DEFAULT 'observacion';
 DECLARE nodo TEXT DEFAULT NULL;
 DECLARE contador int DEFAULT 0;
 DECLARE CONST_XML TEXT DEFAULT 'XML';
 DECLARE CONST_TXT TEXT DEFAULT 'TXT';
 DECLARE CONST_SEPARADOR TEXT;
 DECLARE CONST_FINAL_LIN TEXT DEFAULT '\n';
 DECLARE CONST_LIN_INF TEXT DEFAULT '_';
 DECLARE nombre_fuente TEXT;
 DECLARE posicion double DEFAULT 1;
 DECLARE longitud double DEFAULT 1;
 DECLARE separador TEXT DEFAULT 'separador';
 
 
 	 /*Auditoria*/
	 INSERT INTO tb_trazabilidad (fecha_trazabilidad, valor_trazabilidad) values
	 (now(), concat('nombre_archivo_fuente:',NEW.nombre_archivo_fuente));
	 
 
 IF (lower(NEW.nombre_archivo_fuente) LIKE lower(concat('%',CONST_XML,'%'))) THEN
 
	DELETE l.* 
    FROM  tb_archivo_fuente a, tb_lista_restriccion l 
    WHERE a.id_archivo_fuente = l.tb_archivo_fuente_id_archivo_fuente
    AND nombre_fuente = NEW.nombre_fuente;
 
	 SET nombre_fuente  := concat(CONST_XML,CONST_LIN_INF,NEW.nombre_fuente);
 
	 SET fecha_reporte  := fnc_obtenerEtiqueta(nombre_fuente, fecha_reporte);
	 SET nombre_uno 	:= fnc_obtenerEtiqueta(nombre_fuente, nombre_uno);
	 SET nombre_dos 	:= fnc_obtenerEtiqueta(nombre_fuente, nombre_dos); 
	 SET observacion 	:= fnc_obtenerEtiqueta(nombre_fuente, observacion);
	 SET num_reg 	    := fnc_obtenerNumEtiqueta(nombre_fuente);
	 
	 /*Auditoria
	 INSERT INTO tb_trazabilidad (fecha_trazabilidad, valor_trazabilidad) values
	 (now(), concat('fecha:',fecha_reporte,' nombre_uno',nombre_uno,' nombre_dos ',nombre_dos,' observacion', observacion ,' num_etiqueta ',num_etiqueta, ' NEW.nombre_fuente: ',NEW.nombre_fuente));
	 */
	 
	 WHILE j < num_reg DO
		SET j := j + 1;
		
		SET nodo := fnc_obtenerEtiqueta(nombre_fuente, concat('nodo',j));	
		SET row_count := extractValue(NEW.archivo_cargado, concat('count(',nodo,')'));

		 /* Auditoria
		 INSERT INTO tb_trazabilidad (fecha_trazabilidad, valor_trazabilidad) values
		 (now(), concat('NODO:',nodo,' CANTIDAD:',row_count,' J:',j,'num_etiqueta:',num_etiqueta ));
		 */
		 SET i := 0;
		 WHILE i < row_count DO        
			SET i := i + 1;
			SET contador := contador + 1;
			SET xpath := concat(nodo,'[', i, ']');
			
			/* Auditoria
			INSERT INTO tb_trazabilidad (fecha_trazabilidad, valor_trazabilidad) values
			(now(), concat('***xpath:',xpath,' I:',i));		*/
					
			INSERT INTO tb_lista_restriccion
			(lista_id_registro,
			 lista_primer_nombre,
			 lista_ultimo_nombre,
			 lista_fecha_reporte,
			 lista_observacion,
			 fecha_creacion,
			 usuario_creacion,
			 fecha_modificacion,
			 usuario_modificacion,
			 tb_archivo_fuente_id_archivo_fuente)
			VALUES
			(contador,
			extractValue(NEW.archivo_cargado, concat(xpath,nombre_uno)),  
			extractValue(NEW.archivo_cargado, concat(xpath,nombre_dos)), 
			IF(extractValue(NEW.archivo_cargado, concat(xpath,fecha_reporte)) = '',NULL,DATE_FORMAT(extractValue(NEW.archivo_cargado, concat(xpath,fecha_reporte)), '%Y-%m-%d')),       
			extractValue(NEW.archivo_cargado, concat(xpath,observacion)),
			now(),
			NEW.usuario_creacion,
			now(),
			NEW.usuario_modificacion,
			NEW.id_archivo_fuente);
		 
		 END WHILE;
		END WHILE; 
ELSE
		
        SET num_reg 	   := LENGTH(NEW.archivo_cargado) - LENGTH(REPLACE(NEW.archivo_cargado, CONST_FINAL_LIN, ''));
		SET nombre_fuente  := concat(CONST_TXT,CONST_LIN_INF,NEW.nombre_fuente);
		SET separador 	   := fnc_obtenerEtiqueta(nombre_fuente, separador);
		
        WHILE i < num_reg DO 
			SET i := i + 1;
            SET longitud := instr(NEW.archivo_cargado, CONST_FINAL_LIN);                        
            SET nodo := SUBSTR(NEW.archivo_cargado, posicion, longitud);
            
            /* Auditoria
			INSERT INTO tb_trazabilidad (fecha_trazabilidad, valor_trazabilidad) values
			(now(), concat('separador->',separador,'***nodo:',nodo,' I:',i,'longitud: ',longitud,'nombre_fuente',nombre_fuente));		
           */
            
			INSERT INTO tb_lista_restriccion
			(lista_id_registro ,
			lista_primer_nombre,
			lista_ultimo_nombre,
			lista_fecha_reporte,
			lista_observacion,
			fecha_creacion,
			usuario_creacion,
			fecha_modificacion,
			usuario_modificacion, 
			tb_archivo_fuente_id_archivo_fuente)
			VALUES
            (i,
			SUBSTRING_INDEX(nodo,separador,1),
            SUBSTRING_INDEX(SUBSTRING_INDEX(nodo,separador,2),separador,-1), 
            IF(SUBSTRING_INDEX(SUBSTRING_INDEX(nodo,separador,-2),separador,1) = '', NULL, DATE_FORMAT(SUBSTRING_INDEX(SUBSTRING_INDEX(nodo,separador,-2),separador,1), '%Y-%m-%d')), 
            SUBSTRING_INDEX(nodo,separador,-1),
			now(),
			NEW.usuario_creacion,
			now(),
			NEW.usuario_modificacion,
			NEW.id_archivo_fuente);  
            
            SET posicion := posicion + longitud;
            
		END WHILE;

END IF;		
		
 
END