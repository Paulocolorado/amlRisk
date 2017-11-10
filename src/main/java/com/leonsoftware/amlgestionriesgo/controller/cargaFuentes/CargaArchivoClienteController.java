/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonsoftware.amlgestionriesgo.controller.cargaFuentes;

import com.leonsoftware.amlgestionriesgo.ejb.ArchivoFacade;
import com.leonsoftware.amlgestionriesgo.ejb.ArchivoFacadeLocal;
import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.ArchivoClienteMasivo;
import com.leonsoftware.amlgestionriesgo.model.Usuario;
import com.leonsoftware.amlgestionriesgo.util.ConstantesSisgri;
import com.leonsoftware.amlgestionriesgo.util.UtilitarioLeonSoftware;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
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

/**
 *
 * @author karol
 */
@Named
@ViewScoped
public class CargaArchivoClienteController implements Serializable{
    
    private static final Logger LOGGER = Logger.getLogger("CargaArchivoClienteController");     
    @EJB
    private ArchivoFacadeLocal EJBArchivo;    
    private ResourceBundle mensajes;
    private  ArchivoClienteMasivo archivoClienteMasivo;
    private boolean ocultarBoton;
    
    /**
     * Constructor
     */
    public CargaArchivoClienteController() {
        LOGGER.info("LOGGER :: CargaArchivoClienteController :: Construct");
        this.mensajes = null;
        this.archivoClienteMasivo = new ArchivoClienteMasivo();
        this.ocultarBoton = true;
    }

    /**
     * Post Constructor
     */
    @PostConstruct
    public void init(){
        LOGGER.info("LOGGER :: CargaArchivoClienteController :: init");
        this.mensajes = new UtilitarioLeonSoftware().cargarMensajes();
        this.EJBArchivo = new ArchivoFacade();
        this.ocultarBoton = true;
    }       
    
    /**
     * Método que permite realizar la carga a memoria del archivo fuente
     * @param e 
     */
    public void cargarArchivoClienteListener(FileUploadEvent e){
        try {
            LOGGER.info("LOGGER :: CargaArchivoClienteController :: cargarArchivoClienteListener");
            this.archivoClienteMasivo.setNombreArchivoCliMasivo(e.getFile().getFileName());
            this.archivoClienteMasivo.setArchivoCargado(IOUtils.toByteArray(e.getFile().getInputstream()));
            if(e.getFile() != null) {
                this.ocultarBoton = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, this.archivoClienteMasivo.getNombreArchivoCliMasivo() + "->" + this.mensajes.getString(ConstantesSisgri.MSJ_CARGA_OK), null));
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), null));            
            }
        }catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoClienteMasivo.getNombreArchivoCliMasivo() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_LECTURA_ARCHIVO), ex.getMessage()));
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    /**
     * Metodo que oermite almacenar el archivo de clientes para cruce masivo
     */
    public void procesarArchivoMasivo(){
        LOGGER.info("LOGGER :: CargaArchivoClienteController :: procesarArchivoMasivo"); 
        Calendar fechaActual = Calendar.getInstance();          
        Usuario usuario =  (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        fechaActual.get(Calendar.YEAR);
        fechaActual.get(Calendar.MONTH);
        fechaActual.get(Calendar.DAY_OF_MONTH); 
        try{   
            this.archivoClienteMasivo.setProcesado(ConstantesSisgri.PROCESADO_NO);
            this.archivoClienteMasivo.setFechaCarga(fechaActual.getTime());
            this.archivoClienteMasivo.setFechaCreacion(fechaActual.getTime());
            this.archivoClienteMasivo.setFechaModificacion(fechaActual.getTime());            
            this.archivoClienteMasivo.setUsuario(usuario);
            this.archivoClienteMasivo.setUsuarioCreacion(usuario.getNombreUsuario());
            this.archivoClienteMasivo.setUsuarioModificacion(usuario.getNombreUsuario());
            this.EJBArchivo.guardarArchivoCliente(this.archivoClienteMasivo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,this.mensajes.getString(ConstantesSisgri.MSJ_GUARDA_ARCHIVO_OK), null));
        }catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoClienteMasivo.getNombreArchivoCliMasivo() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_GUARDA_ARCHIVO), ex.getMessage()));
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
    /*
    * Metodos SET y GET
    */

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public ResourceBundle getMensajes() {
        return mensajes;
    }

    public void setMensajes(ResourceBundle mensajes) {
        this.mensajes = mensajes;
    }

    public ArchivoFacadeLocal getEJBArchivo() {
        return EJBArchivo;
    }

    public void setEJBArchivo(ArchivoFacadeLocal EJBArchivo) {
        this.EJBArchivo = EJBArchivo;
    }

    public ArchivoClienteMasivo getArchivoClienteMasivo() {
        return archivoClienteMasivo;
    }

    public void setArchivoClienteMasivo(ArchivoClienteMasivo archivoClienteMasivo) {
        this.archivoClienteMasivo = archivoClienteMasivo;
    }
    
    
    public boolean isOcultarBoton() {
        return ocultarBoton;
    }

    public void setOcultarBoton(boolean ocultarBoton) {
        this.ocultarBoton = ocultarBoton;
    }
}
