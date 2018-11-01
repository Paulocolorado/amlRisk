/**
 *
 * @author LeonSoftware 2017
 */
package com.leonsoftware.amlgestionriesgo.controller;

import com.leonsoftware.amlgestionriesgo.ejb.MenuFacadeLocalImpl;
import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.Menu;
import com.leonsoftware.amlgestionriesgo.model.Usuario;
import com.leonsoftware.amlgestionriesgo.util.ConstantesSisgri;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 * Clase para la gestion parautilidades generales de autenticacion y autorizacion
 * @author Carolina Colorado
 * @since 28/09/2017
 * @version 1.0
 */
@Named
@SessionScoped
public class PlantillaConMenuController implements Serializable{
    private Usuario usuario;
    private MenuFacadeLocalImpl EJBMenu;
    private List<Menu> listaMenu;
    private MenuModel model;
    private final FacesContext contexto;
    
    /**
     * Creates a new instance of PlantillaConMenuController
     */
    public PlantillaConMenuController() {
        this.usuario = null;
        this.contexto = FacesContext.getCurrentInstance();
        this.usuario =  (Usuario) this.contexto.getExternalContext().getSessionMap().get("usuario");
    }

    @PostConstruct
    public void init(){
        this.EJBMenu = new MenuFacadeLocalImpl();
        this.model = new DefaultMenuModel();
        this.listarMenu();
        this.establecerPermisos();
    }
    
    public void verificaSesion(){
        try{            
            if(this.usuario == null){
                this.contexto.getExternalContext().redirect("../error.xhtml");
            }
        }catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getMessage()));
            Logger.getLogger(PlantillaConMenuController.class.getName()).log(Level.SEVERE, null, e);
        }
    }           
    
    
    public void listarMenu(){
        try {
            this.listaMenu = EJBMenu.buscarTodoMenu();
        } catch (SisgriException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getMessage()));
            Logger.getLogger(PlantillaConMenuController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void establecerPermisos(){
        for(Menu menu : this.listaMenu){
            if(menu.getTipoMenu().equals(ConstantesSisgri.MENU_SUBMENU) && menu.getTipoUsuario().equals(this.usuario.getTipoUsuario())){
                DefaultSubMenu firstSubMenu = new DefaultSubMenu(menu.getNombreMenu()); 
                for(Menu i : this.listaMenu){
                    Menu submenu = i.getCodigoSubmenu();
                    if(submenu != null){
                        if(submenu.getCodigo().equals(menu.getCodigo())){
                            DefaultMenuItem item  = new DefaultMenuItem(i.getNombreMenu());
                            item.setUrl(i.getUrlMenu());
                            firstSubMenu.addElement(item);
                        }
                    }                   
                }
                model.addElement(firstSubMenu);
            }else{
                if(menu.getCodigoSubmenu() == null && menu.getTipoUsuario().equals(this.usuario.getTipoUsuario())){
                    DefaultMenuItem item = new DefaultMenuItem(menu.getNombreMenu());
                    item.setUrl(menu.getUrlMenu());
                    this.model.addElement(item);
                }    
            }
        }
    }
    
    public void cerrarSesion(){        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
   
    /*
    Metodo SET y GET
     */   

    public List<Menu> getListaMenu() {
        return listaMenu;
    }

    public void setListaMenu(List<Menu> listaMenu) {
        this.listaMenu = listaMenu;
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public MenuFacadeLocalImpl getEJBMenu() {
        return EJBMenu;
    }

    public void setEJBMenu(MenuFacadeLocalImpl EJBMenu) {
        this.EJBMenu = EJBMenu;
    }        
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    
}

