/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonsoftware.amlgestionriesgo.ejb;

import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author karol
 */
@Local
public interface UsuarioFacadeLocal {
     Usuario iniciarSesion (Usuario pUsuario) throws SisgriException;
     
     Usuario recuperarUsuarioPorNombre (Usuario pUsuario) throws SisgriException;
     
     List<Usuario> buscarUsuario(Usuario pUsuario) throws SisgriException;
     
     public void guardarUsuario(Usuario pUsuario) throws SisgriException;
     

}
