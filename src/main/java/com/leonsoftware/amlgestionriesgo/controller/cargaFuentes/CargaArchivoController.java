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
import com.leonsoftware.amlgestionriesgo.model.ListaIdRestriccion;
import com.leonsoftware.amlgestionriesgo.model.ListaIdRestriccionPK;
import com.leonsoftware.amlgestionriesgo.model.ListaRestriccion;
import com.leonsoftware.amlgestionriesgo.model.ListaRestriccionPK;
import com.leonsoftware.amlgestionriesgo.model.Usuario;
import com.leonsoftware.amlgestionriesgo.util.ConstantesSisgri;
import com.leonsoftware.amlgestionriesgo.util.UtilitarioLeonSoftware;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author karol
 */
@Named
@ViewScoped
public class CargaArchivoController implements Serializable{
    
    private static final Logger LOGGER = Logger.getLogger("CargaArchivoController");     
    private static final long serialVersionUID = 970364273022644336L;
    @EJB
    private CatalogoFacadeLocal EJBcatalogo;
    @EJB
    private ArchivoFacadeLocal EJBArchivo;
    
    private ResourceBundle mensajes;
    private ArchivoFuente archivoFuente;
    private List<ListaCatalogo> listaAccion;
    private List<ListaCatalogo> listaFuente;
    private boolean ocultarBoton;
    private Usuario usuario;
    private Path rutaTemporal;
    private Path archivoTemporal;
    private Properties props; 

    
    /**
     * Constructor
     */
    public CargaArchivoController() {
        LOGGER.info("LOGGER :: CargaArchivoController :: Construct");
        this.mensajes = null;
        this.archivoFuente = null;
        this.listaAccion = null;
        this.listaFuente = null;
        this.ocultarBoton = true;
        this.usuario = null;
        this.rutaTemporal = null;
        this.archivoTemporal = null;
        this.props = null;
    }

    /**
     * Post Constructor
     */
    @PostConstruct
    public void init(){
        LOGGER.info("LOGGER :: CargaArchivoController :: init");
        this.mensajes = new UtilitarioLeonSoftware().cargarMensajes();
        this.listaFuente = new ArrayList<ListaCatalogo>(); 
        this.listaAccion = new ArrayList<ListaCatalogo>();
        this.EJBcatalogo = new CatalogoFacade();
        this.EJBArchivo = new ArchivoFacade();
        this.ocultarBoton = true;
        this.usuario =  (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.props = new Properties();
        this.archivoFuente = new ArchivoFuente();
    }
    
    /**
     * Metodo que permite iniciar las listas de la página
     */
    public void iniciarListas (){
        LOGGER.info("LOGGER :: CargaArchivoController :: iniciarListas");        
        try {   
            this.listaAccion = EJBcatalogo.obtenerListaCatalogo(ConstantesSisgri.LISTA_ACCION);
            this.listaFuente = EJBcatalogo.obtenerListaCatalogo(ConstantesSisgri.LISTA_FUENTE);
            this.props = new UtilitarioLeonSoftware().cargarConfiguracion();
            this.rutaTemporal = Paths.get(this.props.getProperty(ConstantesSisgri.RUTA_TEMP)) ;
        } catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_LISTA), ex.getMessage()));
        }        
    }
        
    /**
     * Método que permite realizar la carga a memoria del archivo fuente
     * @param e 
     */
    public void cargarArchivoListener(FileUploadEvent e){
        LOGGER.info("LOGGER :: CargaArchivoController :: cargarArchivoListener");
        try {
            UploadedFile uploadedFile = e.getFile();             
            if(uploadedFile != null) {  
                String filename = FilenameUtils.getName(uploadedFile.getFileName());
                String extension = FilenameUtils.getExtension(uploadedFile.getFileName());                
                this.archivoFuente.setNombreArchivoFuente(filename.toLowerCase());
                this.archivoFuente.setArchivoCargado(IOUtils.toByteArray(uploadedFile.getInputstream()));               
                this.ocultarBoton = false;
                this.archivoTemporal = Files.createTempFile(this.rutaTemporal, filename + "-", "." + extension);                
                Files.copy(uploadedFile.getInputstream(), this.archivoTemporal, StandardCopyOption.REPLACE_EXISTING);                               
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,this.archivoFuente.getNombreArchivoFuente() + "->" + this.mensajes.getString(ConstantesSisgri.MSJ_CARGA_OK), null));
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), null));            
            }
        }catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoFuente.getNombreArchivoFuente() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_LECTURA_ARCHIVO) + "->" + ex.getMessage(), ex.getMessage()));
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }    

    
    /**
     * Metodo que permite recorrer el xml de OFAC
     * 
     * @param archivoFuente 
     */
    private void recorrerArchivoFuenteOFAC(Calendar fechaActual){
        LOGGER.info("LOGGER :: recorrerArchivoFuente :: recorrerArchivoFuenteOFAC");
        ListaRestriccion  listaRestriccion = null;
        ListaIdRestriccion listaIdRestriccion = null;
        List<ListaRestriccion> listaRestriccionCollection = new ArrayList<ListaRestriccion>();
        List<ListaIdRestriccion> listaIdRestriccionCollection = new ArrayList<ListaIdRestriccion>();
        try {            
            Integer idArchivo = EJBArchivo.obtenerIdSigArchivo();
            File inputFile = new File(this.archivoTemporal.toString());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList listaNodo = doc.getElementsByTagName("sdnEntry");            
            for(int temp = 0; temp < listaNodo.getLength(); temp++){                
                Node nodo = listaNodo.item(temp);
                listaRestriccion = new ListaRestriccion();
                listaRestriccion.setListaRestriccionPK(new ListaRestriccionPK());
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;     
                    listaRestriccion.getListaRestriccionPK().setListaIdRegistro(temp);
                    listaRestriccion.getListaRestriccionPK().setTbArchivoFuenteIdArchivoFuente(idArchivo);
                    listaRestriccion.setListaPrimerNombre(element.getElementsByTagName("firstName").item(0) == null ? ConstantesSisgri.VACIO : element.getElementsByTagName("firstName").item(0).getTextContent());
                    listaRestriccion.setListaUltimoNombre(element.getElementsByTagName("lastName").item(0) == null ? ConstantesSisgri.VACIO : element.getElementsByTagName("lastName").item(0).getTextContent());
                    listaRestriccion.setListaObservacion(element.getElementsByTagName("title").item(0) == null ? ConstantesSisgri.VACIO : element.getElementsByTagName("title").item(0).getTextContent());
                    listaRestriccion.setUsuarioCreacion(this.usuario.getNombreUsuario());
                    listaRestriccion.setFechaCreacion(fechaActual.getTime());
                    listaRestriccion.setUsuarioModificacion(this.usuario.getNombreUsuario());
                    listaRestriccion.setFechaModificacion(fechaActual.getTime());
                    
                    NodeList sublistaID = element.getElementsByTagName("id");
                    for(int j = 0; j < sublistaID.getLength(); j++){      
                        Node nodoID = sublistaID.item(j);
                        listaIdRestriccion = new ListaIdRestriccion();
                        Element elementID = (Element) nodoID;                                  
                        listaIdRestriccion.setListaIdRestriccionPK(new ListaIdRestriccionPK());
                        listaIdRestriccion.getListaIdRestriccionPK().setListaIdRestriccionId(j);
                        listaIdRestriccion.getListaIdRestriccionPK().setTbArchivoFuenteIdArchivoFuente(idArchivo);
                        listaIdRestriccion.getListaIdRestriccionPK().setTbListaRestriccionListaIdRegistro(temp);
                        listaIdRestriccion.setTipoId(elementID.getElementsByTagName("idType").item(0) == null ? ConstantesSisgri.VACIO : elementID.getElementsByTagName("idType").item(0).getTextContent());
                        listaIdRestriccion.setNumeroId(elementID.getElementsByTagName("idNumber").item(0) == null ? ConstantesSisgri.VACIO : elementID.getElementsByTagName("idNumber").item(0).getTextContent());
                        listaIdRestriccion.setPaisId(elementID.getElementsByTagName("idCountry").item(0) == null ? ConstantesSisgri.VACIO : elementID.getElementsByTagName("idCountry").item(0).getTextContent()); 
                        listaIdRestriccion.setUsuarioCreacion(this.usuario.getNombreUsuario());
                        listaIdRestriccion.setFechaCreacion(fechaActual.getTime());
                        listaIdRestriccion.setUsuarioModificacion(this.usuario.getNombreUsuario());
                        listaIdRestriccion.setFechaModificacion(fechaActual.getTime());
                        listaIdRestriccionCollection.add(listaIdRestriccion);
                    }
                }  
                listaRestriccion.setListaIdRestriccionCollection(listaIdRestriccionCollection);
                listaRestriccionCollection.add(listaRestriccion);
            }
            this.archivoFuente.setListaRestriccionCollection(listaRestriccionCollection);
            this.archivoFuente.setIdArchivoFuente(idArchivo);
        } catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoFuente.getNombreArchivoFuente() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), ex.getMessage()));            
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        }                 
    }   
    
    /**
     * Metodo que permite recorrer el xml de OFAC
     * 
     * @param fechaActual 
     */
    private void recorrerArchivoFuenteONU(Calendar fechaActual){
        LOGGER.info("LOGGER :: recorrerArchivoFuente :: recorrerArchivoFuenteONU");
        ListaRestriccion  listaRestriccion = null;
        List<ListaRestriccion> listaRestriccionCollection = new ArrayList<ListaRestriccion>();
        int sigIdLista = 0;
        try {            
            Integer idArchivo = EJBArchivo.obtenerIdSigArchivo();
            File inputFile = new File(this.archivoTemporal.toString());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList listaNodo1 = doc.getElementsByTagName("INDIVIDUAL");            
            NodeList listaNodo2 = doc.getElementsByTagName("ENTITY");   
            sigIdLista = listaNodo1.getLength();
            for(int temp = 0; temp < sigIdLista; temp++){                
                Node nodo1 = listaNodo1.item(temp);
                listaRestriccion = new ListaRestriccion();
                listaRestriccion.setListaRestriccionPK(new ListaRestriccionPK());
                if (nodo1.getNodeType() == Node.ELEMENT_NODE) {
                    Element element1 = (Element) nodo1;     
                    listaRestriccion.getListaRestriccionPK().setListaIdRegistro(temp);
                    listaRestriccion.getListaRestriccionPK().setTbArchivoFuenteIdArchivoFuente(idArchivo);
                    listaRestriccion.setListaPrimerNombre(element1.getElementsByTagName("FIRST_NAME").item(0) == null ? ConstantesSisgri.VACIO : element1.getElementsByTagName("FIRST_NAME").item(0).getTextContent());
                    listaRestriccion.setListaUltimoNombre(element1.getElementsByTagName("SECOND_NAME").item(0) == null ? ConstantesSisgri.VACIO : element1.getElementsByTagName("SECOND_NAME").item(0).getTextContent());
                    listaRestriccion.setListaObservacion(element1.getElementsByTagName("COMMENTS1").item(0) == null ? ConstantesSisgri.VACIO : element1.getElementsByTagName("COMMENTS1").item(0).getTextContent());
                    listaRestriccion.setUsuarioCreacion(this.usuario.getNombreUsuario());
                    listaRestriccion.setFechaCreacion(fechaActual.getTime());
                    listaRestriccion.setUsuarioModificacion(this.usuario.getNombreUsuario());
                    listaRestriccion.setFechaModificacion(fechaActual.getTime());
                }
                listaRestriccionCollection.add(listaRestriccion);
            }                       
            for(int j = 0; j < listaNodo2.getLength(); j++){      
                Node nodo2 = listaNodo2.item(j);
                listaRestriccion = new ListaRestriccion();
                listaRestriccion.setListaRestriccionPK(new ListaRestriccionPK());
                sigIdLista = sigIdLista + 1;
                if (nodo2.getNodeType() == Node.ELEMENT_NODE) {           
                    Element element2 = (Element) nodo2;                                     
                    listaRestriccion.getListaRestriccionPK().setListaIdRegistro(sigIdLista);
                    listaRestriccion.getListaRestriccionPK().setTbArchivoFuenteIdArchivoFuente(idArchivo);
                    listaRestriccion.setListaPrimerNombre(element2.getElementsByTagName("FIRST_NAME").item(0) == null ? ConstantesSisgri.VACIO : element2.getElementsByTagName("FIRST_NAME").item(0).getTextContent());
                    listaRestriccion.setListaUltimoNombre(element2.getElementsByTagName("SECOND_NAME").item(0) == null ? ConstantesSisgri.VACIO : element2.getElementsByTagName("SECOND_NAME").item(0).getTextContent());
                    listaRestriccion.setListaObservacion(element2.getElementsByTagName("COMMENTS1").item(0) == null ? ConstantesSisgri.VACIO : element2.getElementsByTagName("COMMENTS1").item(0).getTextContent());
                    listaRestriccion.setUsuarioCreacion(this.usuario.getNombreUsuario());
                    listaRestriccion.setFechaCreacion(fechaActual.getTime());
                    listaRestriccion.setUsuarioModificacion(this.usuario.getNombreUsuario());
                    listaRestriccion.setFechaModificacion(fechaActual.getTime());
                }   
            listaRestriccionCollection.add(listaRestriccion);    
            }
            this.archivoFuente.setListaRestriccionCollection(listaRestriccionCollection);
            this.archivoFuente.setIdArchivoFuente(idArchivo);
        } catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoFuente.getNombreArchivoFuente() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), ex.getMessage()));            
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        }                 
    } 
    
    /**
     * Metodo que permite recorrer el xml de externo
     * 
     * @param fechaActual 
     */
    private void recorrerArchivoExternoTexto(Calendar fechaActual){
        LOGGER.info("LOGGER :: recorrerArchivoFuente :: recorrerArchivoExterno");
        ListaRestriccion  listaRestriccion = null;
        ListaIdRestriccion listaIdRestriccion = null;
        List<ListaRestriccion> listaRestriccionCollection = new ArrayList<ListaRestriccion>();
        List<ListaIdRestriccion> listaIdRestriccionCollection = new ArrayList<ListaIdRestriccion>();
        Calendar fechaReporte = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {  
            Integer idArchivo = EJBArchivo.obtenerIdSigArchivo();
            FileInputStream entrada = new FileInputStream(this.archivoTemporal.toString());
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            int sigIdLista = 0;
            fechaReporte = Calendar.getInstance();  
            while ((strLinea = buffer.readLine()) != null){
                listaRestriccion = new ListaRestriccion();
                listaIdRestriccion = new ListaIdRestriccion();
                listaRestriccion.setListaRestriccionPK(new ListaRestriccionPK());
                listaIdRestriccion.setListaIdRestriccionPK(new ListaIdRestriccionPK());
                String[] campo = strLinea.split(ConstantesSisgri.SEPARADOR_ARCH_TEXTO);
                listaRestriccion.getListaRestriccionPK().setListaIdRegistro(sigIdLista);
                listaRestriccion.getListaRestriccionPK().setTbArchivoFuenteIdArchivoFuente(idArchivo);                                
                listaRestriccion.setUsuarioCreacion(this.usuario.getNombreUsuario());
                listaRestriccion.setFechaCreacion(fechaActual.getTime());
                listaRestriccion.setUsuarioModificacion(this.usuario.getNombreUsuario());
                listaRestriccion.setFechaModificacion(fechaActual.getTime());
                
                if (!campo[1].isEmpty()){
                    listaIdRestriccion.getListaIdRestriccionPK().setListaIdRestriccionId(ConstantesSisgri.INDICE_INICIAL);
                    listaIdRestriccion.getListaIdRestriccionPK().setTbArchivoFuenteIdArchivoFuente(idArchivo);
                    listaIdRestriccion.getListaIdRestriccionPK().setTbListaRestriccionListaIdRegistro(sigIdLista);
                    listaIdRestriccion.setTipoId(campo[0] == null ? ConstantesSisgri.VACIO : campo[0]);
                    listaIdRestriccion.setNumeroId(campo[1] == null ? ConstantesSisgri.VACIO : campo[1]);
                    listaIdRestriccion.setPaisId(campo[2] == null ? ConstantesSisgri.VACIO : campo[2]); 
                    listaIdRestriccion.setUsuarioCreacion(this.usuario.getNombreUsuario());
                    listaIdRestriccion.setFechaCreacion(fechaActual.getTime());
                    listaIdRestriccion.setUsuarioModificacion(this.usuario.getNombreUsuario());
                    listaIdRestriccion.setFechaModificacion(fechaActual.getTime());
                    listaIdRestriccionCollection.add(listaIdRestriccion);   
                }
                
                listaRestriccion.setListaPrimerNombre(campo[3]);
                listaRestriccion.setListaUltimoNombre(campo[4]);
                if(!campo[5].isEmpty()){
                    fechaReporte.setTime(sdf.parse(campo[5]));                    
                }
                listaRestriccion.setListaFechaReporte(fechaReporte.getTime());
                listaRestriccion.setListaObservacion(campo[6]);
                listaRestriccion.setListaIdRestriccionCollection(listaIdRestriccionCollection);
                listaRestriccionCollection.add(listaRestriccion); 
                sigIdLista = sigIdLista + 1;
            }
            entrada.close();
            this.archivoFuente.setListaRestriccionCollection(listaRestriccionCollection);
            this.archivoFuente.setIdArchivoFuente(idArchivo);
        }catch (SisgriException e){ 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoFuente.getNombreArchivoFuente() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), e.getMessage()));            
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoFuente.getNombreArchivoFuente() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), e.getMessage()));            
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, e);
        } catch (ParseException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoFuente.getNombreArchivoFuente() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), e.getMessage()));            
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, e);
        }                
    } 
    
    /**
     * Metodo que permite recorrer el excel de la fuente externa
     * 
     * @param fechaActual 
     */
    private void recorrerArchivoExternoExcel(Calendar fechaActual){   
        LOGGER.info("LOGGER :: recorrerArchivoFuente :: recorrerArchivoExternoExcel");
        ListaRestriccion  listaRestriccion = null;
        ListaIdRestriccion listaIdRestriccion = null;
        List<ListaRestriccion> listaRestriccionCollection = new ArrayList<ListaRestriccion>();
        List<ListaIdRestriccion> listaIdRestriccionCollection = new ArrayList<ListaIdRestriccion>();
        Calendar fechaReporte = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        int sigIdLista = 0;
        fechaReporte = Calendar.getInstance(); 
        boolean tieneID = false;
        try{
            Integer idArchivo = EJBArchivo.obtenerIdSigArchivo();
            FileInputStream entrada = new FileInputStream(this.archivoTemporal.toString());             
            XSSFWorkbook workbook = new XSSFWorkbook(entrada);
            XSSFSheet sheet = workbook.getSheetAt(ConstantesSisgri.INDICE_INICIAL);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                listaRestriccion = new ListaRestriccion();
                listaIdRestriccion = new ListaIdRestriccion();
                listaRestriccion.setListaRestriccionPK(new ListaRestriccionPK());
                listaIdRestriccion.setListaIdRestriccionPK(new ListaIdRestriccionPK());
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell celda;
                while (cellIterator.hasNext()) {
                    celda = cellIterator.next();
                    switch(celda.getColumnIndex()) {
                        case 0:  
                            if(celda != null){
                                listaIdRestriccion.setTipoId(celda.toString());
                                tieneID = true;
                            }
                            break;
                        case 1:
                            if(celda != null){
                                listaIdRestriccion.setNumeroId(celda == null ? ConstantesSisgri.VACIO : celda.toString());
                                tieneID = true;
                            }    
                            break;
                        case 2: 
                            if(celda != null){
                                listaIdRestriccion.setPaisId(celda == null ? ConstantesSisgri.VACIO : celda.toString());
                                tieneID = true;
                            }    
                            break;    
                        case 3: 
                            listaRestriccion.setListaPrimerNombre(celda == null ? ConstantesSisgri.VACIO : celda.toString());
                            break;    
                        case 4: 
                            listaRestriccion.setListaUltimoNombre(celda == null ? ConstantesSisgri.VACIO : celda.toString());
                            break;        
                        case 5: 
                            if(celda != null){
                                fechaReporte.setTime(sdf.parse((celda.toString())));                    
                            }
                            listaRestriccion.setListaFechaReporte(fechaReporte.getTime());
                            break;        
                        case 6:                             
                            listaRestriccion.setListaObservacion(celda.getStringCellValue());                            
                            break;      
                    }
                }                
                if(tieneID){
                    listaIdRestriccion.getListaIdRestriccionPK().setTbListaRestriccionListaIdRegistro(sigIdLista);
                    listaIdRestriccion.getListaIdRestriccionPK().setTbArchivoFuenteIdArchivoFuente(idArchivo);
                    listaIdRestriccion.setUsuarioCreacion(this.usuario.getNombreUsuario());
                    listaIdRestriccion.setFechaCreacion(fechaActual.getTime());
                    listaIdRestriccion.setUsuarioModificacion(this.usuario.getNombreUsuario());
                    listaIdRestriccion.setFechaModificacion(fechaActual.getTime());
                    listaIdRestriccionCollection.add(listaIdRestriccion); 
                }
                listaRestriccion.getListaRestriccionPK().setListaIdRegistro(sigIdLista);
                listaRestriccion.getListaRestriccionPK().setTbArchivoFuenteIdArchivoFuente(idArchivo);  
                listaRestriccion.setUsuarioCreacion(this.usuario.getNombreUsuario());
                listaRestriccion.setFechaCreacion(fechaActual.getTime());
                listaRestriccion.setUsuarioModificacion(this.usuario.getNombreUsuario());
                listaRestriccion.setFechaModificacion(fechaActual.getTime());
                                  
                listaRestriccion.setListaIdRestriccionCollection(listaIdRestriccionCollection);
                listaRestriccionCollection.add(listaRestriccion); 
                sigIdLista = sigIdLista + 1;
            }
            this.archivoFuente.setListaRestriccionCollection(listaRestriccionCollection);
            this.archivoFuente.setIdArchivoFuente(idArchivo);
            entrada.close();
        }catch (FileNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoFuente.getNombreArchivoFuente() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), ex.getMessage()));            
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoFuente.getNombreArchivoFuente() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), ex.getMessage()));            
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoFuente.getNombreArchivoFuente() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), ex.getMessage()));            
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoFuente.getNombreArchivoFuente() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), ex.getMessage()));            
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
    /**
     * Metodo que permite identificar el tipo de recorrido para la fuente externa
     * 
     * @param fechaActual 
     */
    private void recorrerArchivoExterno(Calendar fechaActual){
        if(this.archivoFuente.getNombreArchivoFuente().contains(ConstantesSisgri.EXTENSION_EXCEL)){
            this.recorrerArchivoExternoExcel(fechaActual); 
        }else{
            this.recorrerArchivoExternoTexto(fechaActual);
        }        
    }
    
    
    /**
     * Metodo que permite almacenar el archivo en la base de datos
     */
    public void procesarArchivo(){
        LOGGER.info("LOGGER :: CargaArchivoController :: procesarArchivo");  
        Calendar fechaActual = Calendar.getInstance();                  
        fechaActual.get(Calendar.YEAR);
        fechaActual.get(Calendar.MONTH);
        fechaActual.get(Calendar.DAY_OF_MONTH); 
        try{
            this.archivoFuente.setUsuario(this.usuario);
            this.archivoFuente.setFechaCarga(fechaActual.getTime());
            this.archivoFuente.setFechaCreacion(fechaActual.getTime());
            this.archivoFuente.setFechaModificacion(fechaActual.getTime());                        
            this.archivoFuente.setUsuarioCreacion(this.usuario.getNombreUsuario());
            this.archivoFuente.setUsuarioModificacion(this.usuario.getNombreUsuario());  
            if(this.archivoFuente.getNombreFuente().equals(ConstantesSisgri.FUENTE_OFAC)){
                this.recorrerArchivoFuenteOFAC(fechaActual);
            }else if(this.archivoFuente.getNombreFuente().equals(ConstantesSisgri.FUENTE_ONU)){
                this.recorrerArchivoFuenteONU(fechaActual);
            }else{
                this.recorrerArchivoExterno(fechaActual);
            }
            this.EJBArchivo.guardarArchivo(this.archivoFuente);
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,this.mensajes.getString(ConstantesSisgri.MSJ_GUARDA_ARCHIVO_OK), null));
        }catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoFuente.getNombreArchivoFuente() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_GUARDA_ARCHIVO), ex.getMessage()));
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
    
    public boolean isOcultarBoton() {
        return ocultarBoton;
    }

    public void setOcultarBoton(boolean ocultarBoton) {
        this.ocultarBoton = ocultarBoton;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Path getRutaTemporal() {
        return rutaTemporal;
    }

    public void setRutaTemporal(Path rutaTemporal) {
        this.rutaTemporal = rutaTemporal;
    }

    
    
    
}
