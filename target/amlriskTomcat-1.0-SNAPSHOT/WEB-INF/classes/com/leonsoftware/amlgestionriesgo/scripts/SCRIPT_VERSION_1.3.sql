/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  pc
 * Created: 12/12/2017
 */

USE BD_SISGRI;

INSERT INTO `bd_sisgri`.`tb_catalogo` (`id_catalogo`, `fecha_creacion`, `usuario_creacion`, `fecha_modificacion`, `usuario_modificacion`) VALUES ('TIPO_USUARIO', '2017-10-17 14:59:50', 'adminSisgri', '2017-10-17 14:59:50', 'adminSisgri');
INSERT INTO `bd_sisgri`.`tb_catalogo` (`id_catalogo`, `fecha_creacion`, `usuario_creacion`, `fecha_modificacion`, `usuario_modificacion`) VALUES ('ESTADO_MENU', '2017-10-17 14:59:50', 'adminSisgri', '2017-10-17 14:59:50', 'adminSisgri');


INSERT INTO `bd_sisgri`.`tb_lista_catalogo` (`nombre_lista_catalogo`, `valor_lista_catalogo`, `fecha_creacion`, `usuario_creacion`, `fecha_modificacion`, `usuario_modificacion`, `tb_catalogo_id_catalogo`) VALUES ('OPERARIO', 'O', '2017-10-09 14:59:50', 'adminSisgri', '2017-10-09 14:59:50', 'adminSIsgri', 'TIPO_USUARIO');
INSERT INTO `bd_sisgri`.`tb_lista_catalogo` (`nombre_lista_catalogo`, `valor_lista_catalogo`, `fecha_creacion`, `usuario_creacion`, `fecha_modificacion`, `usuario_modificacion`, `tb_catalogo_id_catalogo`) VALUES ('ADMINISTRADOR', 'A', '2017-10-09 14:59:50', 'adminSisgri', '2017-10-09 14:59:50', 'adminSIsgri', 'TIPO_USUARIO');
INSERT INTO `bd_sisgri`.`tb_lista_catalogo` (`nombre_lista_catalogo`, `valor_lista_catalogo`, `fecha_creacion`, `usuario_creacion`, `fecha_modificacion`, `usuario_modificacion`, `tb_catalogo_id_catalogo`) VALUES ('DESARROLLO', 'GEN', '2017-10-09 14:59:50', 'adminSisgri', '2017-10-09 14:59:50', 'adminSIsgri', 'TIPO_USUARIO');
INSERT INTO `bd_sisgri`.`tb_lista_catalogo` (`nombre_lista_catalogo`, `valor_lista_catalogo`, `fecha_creacion`, `usuario_creacion`, `fecha_modificacion`, `usuario_modificacion`, `tb_catalogo_id_catalogo`) VALUES ('Activo', 'A', '2017-10-09 14:59:50', 'adminSisgri', '2017-10-09 14:59:50', 'adminSIsgri', 'ESTADO_MENU');
INSERT INTO `bd_sisgri`.`tb_lista_catalogo` (`nombre_lista_catalogo`, `valor_lista_catalogo`, `fecha_creacion`, `usuario_creacion`, `fecha_modificacion`, `usuario_modificacion`, `tb_catalogo_id_catalogo`) VALUES ('Inactivo', 'I', '2017-10-09 14:59:50', 'adminSisgri', '2017-10-09 14:59:50', 'adminSIsgri', 'ESTADO_MENU');


/*******/

UPDATE `bd_sisgri`.`tb_usuario` SET `tipo_id_usuario`='GEN', `correo_usuario`='leonsoftwareco.adm@gmail.com' WHERE `id_usuario`='12345' and`tipo_id_usuario`='GEN';

INSERT INTO `bd_sisgri`.`tb_usuario` (`tipo_id_usuario`, `id_usuario`, `nombre_usuario`, `apellido_usuario`, `usuario`, `clave_usuario`, `correo_usuario`, `fecha_creacion`, `usuario_creacion`, `fecha_modificacion`, `usuario_modificacion`) VALUES ('A', '1', 'ADMINISTRADOR', 'ADM', 'amlRisk', 'adm1n1str4d0r', 'atencion.cliente@amlrisk.net', '2017-12-12 16:10:26', 'usuSisgri', '2017-12-12 16:10:26', 'usuSisgri');
INSERT INTO `bd_sisgri`.`tb_usuario` (`tipo_id_usuario`, `id_usuario`, `nombre_usuario`, `apellido_usuario`, `usuario`, `clave_usuario`, `correo_usuario`, `fecha_creacion`, `usuario_creacion`, `fecha_modificacion`, `usuario_modificacion`) VALUES ('O', '2', 'OPERARIO1', 'OPR1', 'opr1', '0p3r4r10', 'atencion.cliente@amlrisk.net', '2017-12-12 16:10:26', 'usuSisgri', '2017-12-12 16:10:26', 'usuSisgri');


CREATE TABLE `tb_menu` (
  `codigo` tinyint(4) NOT NULL,
  `nombre_menu` varchar(50) DEFAULT NULL,
  `url_menu` varchar(45) DEFAULT NULL,
  `tipo_menu` varchar(5) DEFAULT NULL,
  `tipo_usuario` varchar(5) DEFAULT NULL,
  `codigo_submenu` tinyint(5) DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_menu_submenu_idx` (`codigo_submenu`),
  CONSTRAINT `fk_menu_submenu` FOREIGN KEY (`codigo_submenu`) REFERENCES `tb_menu` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='tabla que gestiona la funcionalidad del menu dinamico';


INSERT INTO `tb_menu` (`codigo`,`nombre_menu`,`url_menu`,`tipo_menu`,`tipo_usuario`,`codigo_submenu`,`estado`) VALUES (1,'Carga de Fuentes','/cargaFuentes/cargaFuente.xhtml','I','A',NULL,'A');
INSERT INTO `tb_menu` (`codigo`,`nombre_menu`,`url_menu`,`tipo_menu`,`tipo_usuario`,`codigo_submenu`,`estado`) VALUES (2,'Consulta de Listas','/consultaListas/consultaParticularLista.xhtml','I','A',NULL,'A');
INSERT INTO `tb_menu` (`codigo`,`nombre_menu`,`url_menu`,`tipo_menu`,`tipo_usuario`,`codigo_submenu`,`estado`) VALUES (3,'Consulta de Listas','/consultaListas/consultaParticularLista.xhtml','I','O',NULL,'A');
INSERT INTO `tb_menu` (`codigo`,`nombre_menu`,`url_menu`,`tipo_menu`,`tipo_usuario`,`codigo_submenu`,`estado`) VALUES (4,'Cruce Clientes','','S','A',NULL,'A');
INSERT INTO `tb_menu` (`codigo`,`nombre_menu`,`url_menu`,`tipo_menu`,`tipo_usuario`,`codigo_submenu`,`estado`) VALUES (5,'Carga Archivo Clientes','/cargaFuentes/cruceMasivoLista.xhtml','I','A',4,'A');
INSERT INTO `tb_menu` (`codigo`,`nombre_menu`,`url_menu`,`tipo_menu`,`tipo_usuario`,`codigo_submenu`,`estado`) VALUES (6,'Consulta Clientes','/consultaListas/consultaMasivoLista.xhtml','I','A',4,'A');
INSERT INTO `tb_menu` (`codigo`,`nombre_menu`,`url_menu`,`tipo_menu`,`tipo_usuario`,`codigo_submenu`,`estado`) VALUES (7,'Cruce Clientes',NULL,'S','O',NULL,'A');
INSERT INTO `tb_menu` (`codigo`,`nombre_menu`,`url_menu`,`tipo_menu`,`tipo_usuario`,`codigo_submenu`,`estado`) VALUES (8,'Consulta Clientes','/consultaListas/consultaMasivoLista.xhtml','I','O',7,'A');

