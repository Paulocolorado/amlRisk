/**
 *
 * @author LeonSoftware 2017
 */
package com.leonsoftware.amlgestionriesgo.controller.consultaListas;

import com.leonsoftware.amlgestionriesgo.ejb.ArchivoFacade;
import com.leonsoftware.amlgestionriesgo.ejb.ArchivoFacadeLocal;
import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.ArchivoClienteMasivo;
import com.leonsoftware.amlgestionriesgo.model.CruceClienteLista;
import com.leonsoftware.amlgestionriesgo.util.ConstantesSisgri;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


/**
 * Clase para la gestion del formulario de consulta de listas
 * @author Carolina Colorado
 * @since 27/09/2017
 * @version 1.1
 */
@Named
@ViewScoped   
public class ConsultaListaMasivoController implements Serializable{
        
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger("ConsultaListaMasivoController"); 
    @EJB
    private ArchivoFacadeLocal EJBArchivo;  
    private ResourceBundle mensajes;
    private List<ArchivoClienteMasivo> listaArchivoClienteMasivo;
    private List<CruceClienteLista> listaCruceClienteLista;

    /**
     * Constructor
     */
    public ConsultaListaMasivoController() {
        LOGGER.info("LOGGER :: ConsultaListaMasivoController :: ConsultaListaMasivoController");
        this.listaArchivoClienteMasivo = null;
        this.EJBArchivo = null;
        this.listaCruceClienteLista = null;
    }
    
    /**
     * Post Constructor
     */
    @PostConstruct
    public void init() {
        LOGGER.info("LOGGER :: ConsultaListaMasivoController :: init");        
        this.listaArchivoClienteMasivo = new ArrayList<ArchivoClienteMasivo>();
        this.EJBArchivo = new ArchivoFacade(); 
        this.listaCruceClienteLista = new ArrayList<CruceClienteLista>();
    }
    
    /**
     * Método que permite consultar archivo masivo de cliente
     */
    
    public void consultaArchivoMasivo(){
        LOGGER.info("LOGGER :: ConsultaListaMasivoController :: consultaArchivoMasivo");
        try {            
            this.listaArchivoClienteMasivo = EJBArchivo.consultaArchivoMasivo();
        } catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,this.getMensajes().getString(ConstantesSisgri.MSJ_ERROR_CONSULTA), ex.getMessage()));
        }
    }
    
    /**
     * Metodo que permite lanzar el proceso de cruce
     * @param pArchivoClienteMasivo 
     */
    public void cruzarArchivoCliente(ArchivoClienteMasivo pArchivoClienteMasivo){    
        LOGGER.info("LOGGER :: ConsultaListaMasivoController :: init");
        try {
            EJBArchivo.cruzarClientes(pArchivoClienteMasivo);
        } catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,this.getMensajes().getString(ConstantesSisgri.MSJ_ERROR_CARGA), ex.getMessage()));
        }
    }
    
    /**
     * Método que permite consultar el resultado del cruce
     * @param pArchivoClienteMasivo 
     */
    public void consultarCruceClienteLista(ArchivoClienteMasivo pArchivoClienteMasivo){
        LOGGER.info("LOGGER :: ConsultaListaMasivoController :: init"); 
        try{
            this.listaCruceClienteLista = EJBArchivo.consultarCruceClienteLista(pArchivoClienteMasivo);
        }catch(SisgriException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,this.getMensajes().getString(ConstantesSisgri.MSJ_ERROR_CONSULTA), e.getMessage()));
        }        
    }

    
    /*
     * METODOS SET Y GET     
     */

    public List<ArchivoClienteMasivo> getListaArchivoClienteMasivo() {
        return listaArchivoClienteMasivo;
    }

    public void setListaArchivoClienteMasivo(List<ArchivoClienteMasivo> listaArchivoClienteMasivo) {
        this.listaArchivoClienteMasivo = listaArchivoClienteMasivo;
    }

    public ArchivoFacadeLocal getEJBArchivo() {
        return EJBArchivo;
    }

    public void setEJBArchivo(ArchivoFacadeLocal EJBArchivo) {
        this.EJBArchivo = EJBArchivo;
    }
    
    public ResourceBundle getMensajes() {
        return mensajes;
    }

    public void setMensajes(ResourceBundle mensajes) {
        this.mensajes = mensajes;
    }

    public List<CruceClienteLista> getListaCruceClienteLista() {
        return listaCruceClienteLista;
    }

    public void setListaCruceClienteLista(List<CruceClienteLista> listaCruceClienteLista) {
        this.listaCruceClienteLista = listaCruceClienteLista;
    }
}

