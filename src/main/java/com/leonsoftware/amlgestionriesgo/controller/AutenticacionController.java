/*
 * @author LeonSoftware 2017
 */
package com.leonsoftware.amlgestionriesgo.controller;

import com.leonsoftware.amlgestionriesgo.ejb.UsuarioFacadeLocalImpl;
import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.Usuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * Clase para la gestion del formulario de autenticacion
 * @author Carolina Colorado
 * @since 31/08/2017
 * @version 1.0
 */
@Named
@ViewScoped    
public class AutenticacionController implements Serializable{
    
    @EJB
    private UsuarioFacadeLocalImpl EJBusuario;
    private Usuario usuario;
    
    @PostConstruct
    public void init(){
        this.usuario = new Usuario();
        this.EJBusuario = new UsuarioFacadeLocalImpl();
    }
    /**
     * Método que invoca la autenticacion de usuario
     * @return regla de navegación
     */
    public String iniciaSesion(){        
        String urlAutentica = null;
        Usuario usuarioAux;        
        try{
            usuarioAux = this.EJBusuario.iniciarSesion(this.usuario) ;
            if (usuarioAux != null){
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuarioAux);
                urlAutentica = "/bienvenida";
            }else{
                throw new SisgriException("Combinación Clave/Usuario inválida verifique"); 
            }
        }catch(SisgriException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getMessage()));
        }  
        return urlAutentica;
    }
    
    public String loggout (){
        return "/autenticacion";
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
}
