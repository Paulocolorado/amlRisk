/*
Ejecutar sobre versión de base de datos optimizada
Ajuste a trigger solucionando defecto de carga de clientes para cruce masivo
11/12/2017
*/

USE bd_sisgri;

DELIMITER $$
DROP TRIGGER `bd_sisgri`.`tb_archivo_cliente_masivo_AFTER_INSERT`;
CREATE TRIGGER `bd_sisgri`.`tb_archivo_cliente_masivo_AFTER_INSERT` AFTER INSERT ON `tb_archivo_cliente_masivo` FOR EACH ROW
BEGIN

DECLARE i INT UNSIGNED DEFAULT 0;
DECLARE linea TEXT DEFAULT NULL;
DECLARE longitud double DEFAULT 1;
DECLARE posicion double DEFAULT 1;
DECLARE num_reg INT UNSIGNED;
DECLARE CONST_FINAL_LIN TEXT DEFAULT '*';
DECLARE CONST_NOM_CATALOGO TEXT DEFAULT 'TXT_CLIENTE';
DECLARE separador TEXT DEFAULT 'separador';
DECLARE archivoAux longblob;
 
SET num_reg 	   := LENGTH(NEW.archivo_cargado) - LENGTH(REPLACE(NEW.archivo_cargado, CONST_FINAL_LIN, ''));
SET separador 	   := fnc_obtenerEtiqueta(CONST_NOM_CATALOGO, separador);			

/*DELETE FROM  tb_cliente_masivo;
INSERT INTO tb_trazabilidad (fecha_trazabilidad, valor_trazabilidad) values
(now(), concat('num_reg:',num_reg,'separador',separador));
 */
set archivoAux := NEW.archivo_cargado;		

	WHILE i < num_reg DO 
		SET i := i + 1;    
        SET longitud := instr(archivoAux, CONST_FINAL_LIN); 
		SET linea := SUBSTR(archivoAux, posicion, longitud);
		


/*Auditoria 
INSERT INTO tb_trazabilidad (fecha_trazabilidad, valor_trazabilidad) values
(now(), concat('longitud:',longitud,'linea:',linea,'-posicion:',posicion,'archivoAux',archivoAux));
 */
		
		INSERT INTO tb_cliente_masivo
		(tipo_id_cliente,
		id_cliente,
		cliente_primer_nombre,
		cliente_ultimo_nombre,
		fecha_creacion,
		usuario_creacion,
		fecha_modificacion,
		usuario_modificacion, 
		id_archivo_cli_masivo)
		VALUES
		(SUBSTRING_INDEX(linea,separador,1),
		SUBSTRING_INDEX(SUBSTRING_INDEX(linea,separador,2),separador,-1), 
		SUBSTRING_INDEX(SUBSTRING_INDEX(linea,separador,-2),separador,1), 
        REPLACE (SUBSTRING_INDEX(linea,separador,-1),CONST_FINAL_LIN,''),
		now(),
		NEW.usuario_creacion,
		now(),
		NEW.usuario_modificacion,
		NEW.id_archivo_cli_masivo);  
        
        SET posicion := 1;
        SET archivoAux := REPLACE(archivoAux, linea, '');
                       
		
	 END WHILE;   

END$$

DELIMITER ;