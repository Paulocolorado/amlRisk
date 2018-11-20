/**
 *
 * @author LeonSoftware 2017
 */
package com.leonsoftware.amlgestionriesgo.controller.consultaListas;

import com.leonsoftware.amlgestionriesgo.ejb.ArchivoFacade;
import com.leonsoftware.amlgestionriesgo.ejb.ArchivoFacadeLocal;
import com.leonsoftware.amlgestionriesgo.ejb.CatalogoFacade;
import com.leonsoftware.amlgestionriesgo.ejb.CatalogoFacadeLocal;
import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.ListaCatalogo;
import com.leonsoftware.amlgestionriesgo.model.ListaRestriccion;
import com.leonsoftware.amlgestionriesgo.util.ConstantesSisgri;
import com.leonsoftware.amlgestionriesgo.util.UtilitarioLeonSoftware;
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
 * @version 1.0
 */
@Named
@ViewScoped   //aplicationscoped
public class ConsultaListaController implements Serializable{
    
    private static final Logger LOGGER = Logger.getLogger("ConsultaListaController"); 
    @EJB
    private ArchivoFacadeLocal EJBArchivo;
    @EJB
    private CatalogoFacadeLocal EJBcatalogo;
    //TODO pojo cargaArchivo
    private String nombreApCliente;
    private String numeroID;
    private List<ListaRestriccion> listaClienteCoincide;
    private List listaArchivoCoincidencia;
    private ResourceBundle mensajes;
    private boolean visualizaResultado;
    private List<ListaCatalogo> listaPorcentajeCoincidencia;
    private Double porcentaje;
    
    public ConsultaListaController() {
        LOGGER.info("LOGGER :: ConsultaListaController :: ConsultaListaController");
        this.nombreApCliente = null;
        this.numeroID = null;
        this.listaClienteCoincide = null;        
        this.listaArchivoCoincidencia = null;
        this.mensajes = null;
        this.visualizaResultado = false;
        this.listaPorcentajeCoincidencia = null;
     }

    @PostConstruct
    public void init() {
        LOGGER.info("LOGGER :: ConsultaListaController :: init");        
        this.nombreApCliente = new String();
        this.numeroID = new String();
        this.listaClienteCoincide = new ArrayList<ListaRestriccion>();
        this.listaArchivoCoincidencia = new ArrayList();
        this.mensajes = new UtilitarioLeonSoftware().cargarMensajes();
        this.listaPorcentajeCoincidencia = new ArrayList();
        this.EJBcatalogo = new CatalogoFacade();
        this.EJBArchivo = new ArchivoFacade();
        this.porcentaje = ConstantesSisgri.PORCENTAJE_DEFECTO;
    }
    /**
     * Metodo que permite iniciar las listas de la página
     */
    public void iniciarListas (){
        LOGGER.info("LOGGER :: ConsultaListaController :: iniciarListas");        
        try {
             this.listaPorcentajeCoincidencia = EJBcatalogo.obtenerListaCatalogo(ConstantesSisgri.LISTA_PORCENTAJE);
        } catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_LISTA), ex.getMessage()));
        }        
    }
    /**
     * Encuentra coincidencias con el criterio ingresado
     */
    public void buscarCoincidencia(){
        LOGGER.info("LOGGER :: ConsultaListaController :: buscarCoincidencia");            
        this.visualizaResultado = true;
        this.listaClienteCoincide.clear();
        String[] parametros = this.nombreApCliente.split(ConstantesSisgri.ESPACIO_BLANCO);  
        Double porcPorPalabra = (ConstantesSisgri.PORCENTAJE_CIEN / parametros.length);
        Double porcentanjeAux;
        try {
            List<ListaRestriccion> listaClienteCoincideAux = (List<ListaRestriccion>) this.EJBArchivo.buscarListaCoincidencia(parametros, this.numeroID.trim());            
            for(ListaRestriccion  listaRestriccion : listaClienteCoincideAux){
                porcentanjeAux = new Double(0);                
                for(String parametro : parametros){
                    if (((listaRestriccion.getListaPrimerNombre() == null ? ConstantesSisgri.ESPACIO_BLANCO : listaRestriccion.getListaPrimerNombre().toLowerCase()).concat((listaRestriccion.getListaUltimoNombre()== null ? ConstantesSisgri.ESPACIO_BLANCO : listaRestriccion.getListaUltimoNombre().toLowerCase())).contains(parametro.toLowerCase())) || (!this.numeroID.equals(ConstantesSisgri.VACIO) && (listaRestriccion.getIdCompleta().contains(this.numeroID))))  {
                        porcentanjeAux = porcentanjeAux + porcPorPalabra;
                    }
                }
                if(porcentanjeAux >= this.porcentaje){
                    listaRestriccion.setPorcentaje(porcentanjeAux); 
                    this.listaClienteCoincide.add(listaRestriccion);
                }

            }
        } catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,this.getMensajes().getString(ConstantesSisgri.MSJ_ERROR_CONSULTA), ex.getMessage()));
        }
        
    }   
    
    /**
     * Metodo que permite limpiar los filtros y la tabla de formulario
     */
    public void limpiarFormulario(){
        LOGGER.info("LOGGER :: ConsultaListaController :: limpiarFormulario"); 
        this.listaClienteCoincide.clear();
        this.visualizaResultado = false;
        this.nombreApCliente = new String();
        this.numeroID = new String();
    }
   
    /**
     * METODOS SET Y GET     
     * @return 
     */
    
    public String getNombreApCliente() {
        return nombreApCliente;
    }

    public void setNombreApCliente(String nombreApCliente) {
        this.nombreApCliente = nombreApCliente;
    }

    public List getListaClienteCoincide() {
        return listaClienteCoincide;
    }

    public void setListaClienteCoincide(List listaClienteCoincide) {
        this.listaClienteCoincide = listaClienteCoincide;
    }
       
    public List getListaArchivoCoincidencia() {
        return listaArchivoCoincidencia;
    }

    public void setListaArchivoCoincidencia(List listaArchivoCoincidencia) {
        this.listaArchivoCoincidencia = listaArchivoCoincidencia;
    }

    public ResourceBundle getMensajes() {
        return mensajes;
    }

    public void setMensajes(ResourceBundle mensajes) {
        this.mensajes = mensajes;
    }
       
    public boolean isVisualizaResultado() {
        return visualizaResultado;
    }

    public void setVisualizaResultado(boolean visualizaResultado) {
        this.visualizaResultado = visualizaResultado;
    }
     
    public ArchivoFacadeLocal getEJBArchivo() {
        return EJBArchivo;
    }
    
    public void setEJBArchivo(ArchivoFacadeLocal EJBArchivo) {
        this.EJBArchivo = EJBArchivo;
    }
    
    public String getNumeroID() {
        return numeroID;
    }

    public void setNumeroID(String numeroID) {
        this.numeroID = numeroID;
    }
 
    public List getListaPorcentajeCoincidencia() {
        return listaPorcentajeCoincidencia;
    }

    public void setListaPorcentajeCoincidencia(List listaPorcentajeCoincidencia) {
        this.listaPorcentajeCoincidencia = listaPorcentajeCoincidencia;
    }
    
    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
    
}

