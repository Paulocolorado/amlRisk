/*
 * @author LeonSoftware 2017
 */
package com.leonsoftware.amlgestionriesgo.controller.gestionUsuario;

import com.leonsoftware.amlgestionriesgo.ejb.UsuarioFacadeLocalImpl;
import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.Usuario;
import com.leonsoftware.amlgestionriesgo.util.ConstantesSisgri;
import com.leonsoftware.amlgestionriesgo.util.UtilitarioLeonSoftware;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Clase para la gestion del formulario de recordar contrasena
 * @author Paulo Colorado
 * @since 26/04/2018
 * @version 1.0
 */
@Named
@ViewScoped    
public class RecordarClaveController implements Serializable{
    
    @EJB
    private UsuarioFacadeLocalImpl EJBusuario;
    private Usuario usuario;
    private ResourceBundle mensajes;
    private Usuario usuarioRecuperado;
    private StringBuffer correoEncriptado;



    @PostConstruct
    public void init(){
        this.usuario = new Usuario();
        this.EJBusuario = new UsuarioFacadeLocalImpl();
        this.mensajes = new UtilitarioLeonSoftware().cargarMensajes();
        this.usuarioRecuperado = new Usuario();
        this.correoEncriptado = new StringBuffer();
    }



    /**
     * Método que recupera el correo relacionado al nombre de usuario ingresado
     */
    public void recuperarUsuario(){        
        try{
            this.usuarioRecuperado = this.EJBusuario.recuperarUsuarioPorNombre(this.usuario) ;
            if (this.usuarioRecuperado == null){
                throw new SisgriException("Usuario inválido verifique"); 
            }
        }catch(SisgriException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getMessage()));
        }  
    }
    

    
    /**
     * Método que muestra el correo relacionado con el nombre de usuario
     */    
    public void recordarClave(){
        try {
            UtilitarioLeonSoftware utilitario = new UtilitarioLeonSoftware();
            this.recuperarUsuario();
            StringBuffer sb = new StringBuffer(this.usuarioRecuperado.getCorreoUsuario());
            sb.replace(ConstantesSisgri.INDICE_INICIAL, ConstantesSisgri.INDICE_FINAL, "****");
            this.correoEncriptado = sb;
            utilitario.enviarCorreo(this.usuarioRecuperado.getCorreoUsuario(), this.mensajes.getString(ConstantesSisgri.CORREO_MAILMENSAJE).concat(this.usuarioRecuperado.getClaveUsuario()));
        } catch (SisgriException ex) {
            Logger.getLogger(RecordarClaveController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    * Metodos SET y GET
    */
     
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    public UsuarioFacadeLocalImpl getEJBusuario() {
        return EJBusuario;
    }

    public void setEJBusuario(UsuarioFacadeLocalImpl EJBusuario) {
        this.EJBusuario = EJBusuario;
    }
    
    
    public Usuario getUsuarioRecuperado() {
        return usuarioRecuperado;
    }

    public void setUsuarioRecuperado(Usuario usuarioRecuperado) {
        this.usuarioRecuperado = usuarioRecuperado;
    }
    
    public StringBuffer getCorreoEncriptado() {
        return correoEncriptado;
    }

    public void setCorreoEncriptado(StringBuffer correoEncriptado) {
        this.correoEncriptado = correoEncriptado;
    }
    
}
