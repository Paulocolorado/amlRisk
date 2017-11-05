/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonsoftware.amlgestionriesgo.controller.cargaFuentes;

import com.leonsoftware.amlgestionriesgo.ejb.ArchivoFacade;
import com.leonsoftware.amlgestionriesgo.ejb.ArchivoFacadeLocal;
import com.leonsoftware.amlgestionriesgo.ejb.CatalogoFacade;
import com.leonsoftware.amlgestionriesgo.ejb.CatalogoFacadeLocal;
import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.ArchivoFuente;
import com.leonsoftware.amlgestionriesgo.model.ListaCatalogo;
import com.leonsoftware.amlgestionriesgo.model.Usuario;
import com.leonsoftware.amlgestionriesgo.util.ConstantesSisgri;
import com.leonsoftware.amlgestionriesgo.util.UtilitarioLeonSoftware;
import java.io.IOException;
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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.commons.io.IOUtils;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author karol
 */
@Named
@ViewScoped
public class CargaArchivoController implements Serializable{
    
    private static final Logger LOGGER = Logger.getLogger("CargaArchivoController");     
    @EJB
    private CatalogoFacadeLocal EJBcatalogo;
    @EJB
    private ArchivoFacadeLocal EJBArchivo;
    
    private ResourceBundle mensajes;
    private ArchivoFuente archivoFuente;
    private UploadedFile archivo;
    private List<ListaCatalogo> listaAccion;
    private List<ListaCatalogo> listaFuente;
    
    /**
     * Constructor
     */
    public CargaArchivoController() {
        LOGGER.info("LOGGER :: CargaArchivoController :: Construct");
        this.mensajes = null;
        this.archivoFuente = new ArchivoFuente();
        this.archivo = null;
        this.listaAccion = null;
        this.listaFuente = null;
    }

    /**
     * Post Constructor
     */
    @PostConstruct
    public void init(){
        LOGGER.info("LOGGER :: CargaArchivoController :: init");
        this.archivo = new DefaultUploadedFile(); 
        this.mensajes = new UtilitarioLeonSoftware().cargarMensajes();
        this.listaFuente = new ArrayList<ListaCatalogo>(); 
        this.listaAccion = new ArrayList<ListaCatalogo>();
        this.EJBcatalogo = new CatalogoFacade();
        this.EJBArchivo = new ArchivoFacade();
    }
    
    /**
     * Metodo que permite iniciar las listas de la página
     */
    public void iniciarListas (){
        LOGGER.info("LOGGER :: CargaArchivoController :: iniciarListas");        
        try {   
            this.listaAccion = EJBcatalogo.obtenerListaCatalogo(ConstantesSisgri.LISTA_ACCION);
            this.listaFuente = EJBcatalogo.obtenerListaCatalogo(ConstantesSisgri.LISTA_FUENTE);
        } catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_LISTA), ex.getMessage()));
        }        
    }
        
    /**
     * Método que permite realizar la carga a memoria del archivo fuente
     * @param e 
     */
    public void cargarArchivoListener(FileUploadEvent e){
        try {
            LOGGER.info("LOGGER :: CargaArchivoController :: cargarArchivoListener");
            this.archivo = e.getFile();
            this.archivoFuente.setArchivoCargado(IOUtils.toByteArray(e.getFile().getInputstream()));
            if(this.archivo != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,this.archivo.getFileName() + this.mensajes.getString(ConstantesSisgri.MSJ_CARGA_OK), null));
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), null));            
            }
        }catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivo.getFileName() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_LECTURA_ARCHIVO), ex.getMessage()));
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    /**
     * Metodo que permite almacenar el archivo en la base de datos
     */
    public void procesarArchivo(){
        LOGGER.info("LOGGER :: CargaArchivoController :: procesarArchivo");  
        Calendar fechaActual = Calendar.getInstance();          
        Usuario usuario =  (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        fechaActual.get(Calendar.YEAR);
        fechaActual.get(Calendar.MONTH);
        fechaActual.get(Calendar.DAY_OF_MONTH); 
        try{            
            this.archivoFuente.setNombreArchivoFuente(this.archivo.getFileName());
            this.archivoFuente.setFechaCarga(fechaActual.getTime());
            this.archivoFuente.setFechaCreacion(fechaActual.getTime());
            this.archivoFuente.setFechaModificacion(fechaActual.getTime());            
            this.archivoFuente.setUsuario(usuario);
            this.archivoFuente.setUsuarioCreacion(usuario.getNombreUsuario());
            this.archivoFuente.setUsuarioModificacion(usuario.getNombreUsuario());
            this.EJBArchivo.guardarArchivo(this.archivoFuente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,this.mensajes.getString(ConstantesSisgri.MSJ_GUARDA_ARCHIVO_OK), null));
        }catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivo.getFileName() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_GUARDA_ARCHIVO), ex.getMessage()));
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }    
    
    
    /*
    * Metodos SET y GET
    */

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public ResourceBundle getMensajes() {
        return mensajes;
    }

    public void setMensajes(ResourceBundle mensajes) {
        this.mensajes = mensajes;
    }

    public List<ListaCatalogo> getListaAccion() {
        return listaAccion;
    }

    public void setListaAccion(List<ListaCatalogo> listaAccion) {
        this.listaAccion = listaAccion;
    }

    public List<ListaCatalogo> getListaFuente() {
        return listaFuente;
    }

    public void setListaFuente(List<ListaCatalogo> listaFuente) {
        this.listaFuente = listaFuente;
    }

    public ArchivoFuente getArchivoFuente() {
        return archivoFuente;
    }

    public void setArchivoFuente(ArchivoFuente archivoFuente) {
        this.archivoFuente = archivoFuente;
    }

    public CatalogoFacadeLocal getEJBcatalogo() {
        return EJBcatalogo;
    }

    public void setEJBcatalogo(CatalogoFacadeLocal EJBcatalogo) {
        this.EJBcatalogo = EJBcatalogo;
    }

    public ArchivoFacadeLocal getEJBArchivo() {
        return EJBArchivo;
    }

    public void setEJBArchivo(ArchivoFacadeLocal EJBArchivo) {
        this.EJBArchivo = EJBArchivo;
    }
}
