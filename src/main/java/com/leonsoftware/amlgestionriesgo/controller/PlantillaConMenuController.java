/**
 *
 * @author LeonSoftware 2017
 */
package com.leonsoftware.amlgestionriesgo.controller;

import com.leonsoftware.amlgestionriesgo.model.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 * Clase para la gestion parautilidades generales de autenticacion y autorizacion
 * @author Carolina Colorado
 * @since 28/09/2017
 * @version 1.0
 */
@Named
@ViewScoped
public class PlantillaConMenuController implements Serializable{
    private Usuario usuario;
    /**
     * Creates a new instance of PlantillaConMenuController
     */
    public PlantillaConMenuController() {
        this.usuario = null;
    }
    
    public void verificaSesion(){
        try{
            FacesContext contexto = FacesContext.getCurrentInstance();
            this.usuario =  (Usuario) contexto.getExternalContext().getSessionMap().get("usuario");
            if(this.usuario == null){
                contexto.getExternalContext().redirect("../error.xhtml");
            }
        }catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getMessage()));
            Logger.getLogger(PlantillaConMenuController.class.getName()).log(Level.SEVERE, null, e);
        }
    }   

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

