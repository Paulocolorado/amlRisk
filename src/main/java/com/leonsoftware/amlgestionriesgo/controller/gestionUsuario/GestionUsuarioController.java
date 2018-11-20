/*
 * @author LeonSoftware 2017
 */
package com.leonsoftware.amlgestionriesgo.controller.gestionUsuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.leonsoftware.amlgestionriesgo.ejb.CatalogoFacade;
import com.leonsoftware.amlgestionriesgo.ejb.CatalogoFacadeLocal;
import com.leonsoftware.amlgestionriesgo.ejb.UsuarioFacadeLocalImpl;
import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.ListaCatalogo;
import com.leonsoftware.amlgestionriesgo.model.Usuario;
import com.leonsoftware.amlgestionriesgo.model.UsuarioPK;
import com.leonsoftware.amlgestionriesgo.util.ConstantesSisgri;
import com.leonsoftware.amlgestionriesgo.util.UtilitarioLeonSoftware;


/**
 * Clase para la gestion del formulario de recordar contrasena
 * @author Paulo Colorado
 * @since 26/04/2018
 * @version 1.0
 */
@Named 
@ViewScoped
public class GestionUsuarioController implements Serializable{
    
    private static final Logger LOGGER = Logger.getLogger("GestionUsuarioController");  
    @EJB
    private CatalogoFacadeLocal EJBcatalogo;
    @EJB
    private UsuarioFacadeLocalImpl EJBusuario;
    private Usuario usuario;
    private ResourceBundle mensajes;
    private Usuario usuarioRecuperado;
    private StringBuffer correoEncriptado;
    private List<Usuario> listaUsuario;
    private boolean visualizaResultado;
    private List<ListaCatalogo> listaTipoIdUsuario;
    private List<ListaCatalogo> listaTipoUsuario; 
    private Usuario usuarioSeleccion;


    public GestionUsuarioController(){
        this.usuario = new Usuario();
        this.usuario.setUsuarioPK(new UsuarioPK());
        this.EJBusuario = new UsuarioFacadeLocalImpl();
    }

    @PostConstruct
    public void init(){
        this.usuario = new Usuario();
        this.usuario.setUsuarioPK(new UsuarioPK());
        this.EJBusuario = new UsuarioFacadeLocalImpl();
        this.mensajes = new UtilitarioLeonSoftware().cargarMensajes();
        this.usuarioRecuperado = new Usuario();
        this.correoEncriptado = new StringBuffer();
        this.listaUsuario = new ArrayList<Usuario>();
        this.visualizaResultado = false;
        this.listaTipoIdUsuario = new ArrayList();
        this.listaTipoUsuario = new ArrayList();
        this.EJBcatalogo = new CatalogoFacade();
        this.usuarioSeleccion = new Usuario();


              
    }


    
    
    public void guardarUsuario(){
        LOGGER.info("LOGGER :: GestionUsuarioC :: procesarArchivoMasivo"); 
        Calendar fechaActual = Calendar.getInstance();          
        Usuario usuarioSesion =  (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        fechaActual.get(Calendar.YEAR);
        fechaActual.get(Calendar.MONTH);
        fechaActual.get(Calendar.DAY_OF_MONTH); 
        try{
            this.usuario.setFechaModificacion(fechaActual.getTime());            
            this.usuario.setFechaCreacion(fechaActual.getTime());
            this.usuario.setUsuarioCreacion(usuarioSesion.getNombreUsuario());
            this.usuario.setUsuarioModificacion(usuarioSesion.getNombreUsuario());
            EJBusuario.guardarUsuario(this.usuario);
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,this.mensajes.getString(ConstantesSisgri.MSJ_USUARIOCREADO), null));
        }catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.usuario.getNombreUsuario() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_GUARDA_ARCHIVO), ex.getMessage()));//cambiar
            Logger.getLogger(GestionUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }


    /**
     * Método que recupera el correo relacionado al nombre de usuario ingresado
     */
    public void recuperarUsuario(){
        this.listaTipoIdUsuario = null;
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
            Logger.getLogger(GestionUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buscarCoincidencia(){

        LOGGER.info("LOGGER :: GestionUsuarioController :: buscarCoincidencia");
        this.visualizaResultado = true;
        try {
            this.listaUsuario = this.EJBusuario.buscarUsuario(this.usuario);
        
        }   catch (SisgriException ex) {
            Logger.getLogger(GestionUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        
        }
    }

    
        /**
     * Metodo que permite iniciar las listas de la página
     */
    public void iniciarListas(){
        LOGGER.info("LOGGER :: ConsultaListaController :: iniciarListas");
        try {
             this.listaTipoIdUsuario = EJBcatalogo.obtenerListaCatalogo(ConstantesSisgri.LISTA_ID);
             this.listaTipoUsuario = EJBcatalogo.obtenerListaCatalogo(ConstantesSisgri.LISTA_TIPOUSUARIO);
        } catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_LISTA), ex.getMessage()));
        }        
    }
    
        /**
     * Metodo que permite limpiar los filtros y la tabla de formulario
     */
    public void limpiarFormulario(){
        LOGGER.info("LOGGER :: GestionUsuarioController :: limpiarFormulario"); 
        this.listaUsuario.clear();
        this.visualizaResultado = false;
        this.usuario = new Usuario();
    }
       
    public String editarUsuario (){
        return "editarUsuario.xhtml";
       
    }
    
    public void eliminarUsuario (){

        EJBusuario.remove(this.usuarioSeleccion);
                
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
    
    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }
    
    public boolean isVisualizaResultado() {
        return visualizaResultado;
    }

    public void setVisualizaResultado(boolean visualizaResultado) {
        this.visualizaResultado = visualizaResultado;
    }
    
    public CatalogoFacadeLocal getEJBcatalogo() {
        return EJBcatalogo;
    }

    public void setEJBcatalogo(CatalogoFacadeLocal EJBcatalogo) {
        this.EJBcatalogo = EJBcatalogo;
    }
    
    public List<ListaCatalogo> getListaTipoIdUsuario() {
        return listaTipoIdUsuario;
    }

    public void setListaTipoIdUsuario(List<ListaCatalogo> listaTipoIdUsuario) {
        this.listaTipoIdUsuario = listaTipoIdUsuario;
    }
    
    public List<ListaCatalogo> getListaTipoUsuario() {
        return listaTipoUsuario;
    }

    public void setListaTipoUsuario(List<ListaCatalogo> listaTipoUsuario) {
        this.listaTipoUsuario = listaTipoUsuario;
    }
    
    public Usuario getUsuarioSeleccion() {
        return usuarioSeleccion;
    }

    public void setUsuarioSeleccion(Usuario usuarioSeleccion) {
        this.usuarioSeleccion = usuarioSeleccion;
    }

}
