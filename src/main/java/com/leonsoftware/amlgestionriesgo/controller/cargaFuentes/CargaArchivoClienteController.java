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
import com.leonsoftware.amlgestionriesgo.model.ClienteMasivo;
import com.leonsoftware.amlgestionriesgo.model.ClienteMasivoPK;
import com.leonsoftware.amlgestionriesgo.model.Usuario;
import com.leonsoftware.amlgestionriesgo.util.ConstantesSisgri;
import com.leonsoftware.amlgestionriesgo.util.UtilitarioLeonSoftware;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
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
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * Clase para la gestion de la carga masiva de clientes
 * 
 * @author Carolina Colorado
 */
@Named
@ViewScoped
public class CargaArchivoClienteController implements Serializable{
    
    private static final long serialVersionUID = -4742747586951232647L;
    private static final Logger LOGGER = Logger.getLogger("CargaArchivoClienteController");         
    @EJB
    private ArchivoFacadeLocal EJBArchivo;    
    private ResourceBundle mensajes;
    private ArchivoClienteMasivo archivoClienteMasivo;    
    private boolean ocultarBoton;
    private Path archivoTemporal;
    private Usuario usuario;
    private List<ClienteMasivo> listaClienteMasivo; 
    private Path rutaTemporal;
    private Properties props; 
    
    /**
     * Constructor
     */
    public CargaArchivoClienteController() {
        LOGGER.info("LOGGER :: CargaArchivoClienteController :: Construct");
        this.mensajes = null;
        this.archivoClienteMasivo = new ArchivoClienteMasivo();
        this.ocultarBoton = true;
        this.usuario = null;
        this.listaClienteMasivo = null;  
        this.rutaTemporal = null;
        this.archivoTemporal = null;
        this.props = null;
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
        this.usuario = new Usuario();        
        this.props = new Properties();
        this.listaClienteMasivo = new ArrayList<ClienteMasivo>(); 
    }       
    
    /**
     * Metodo que permite iniciar los elementos y objetos necesarios para el buen funcionamiento de 
     * la página de carga de de clientes masivo
     */
    public void iniciarObjetos(){
        try {
            LOGGER.info("LOGGER :: CargaArchivoClienteController :: iniciarObjetos");
            this.usuario =  (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            this.props = new UtilitarioLeonSoftware().cargarConfiguracion();
        } catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoClienteMasivo.getNombreArchivoCliMasivo() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_LECTURA_ARCHIVO) + "->" + ex.getMessage(), ex.getMessage()));
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    } 
    
    /**
     * Método que permite realizar la carga a memoria del archivo fuente
     * @param e 
     */
    public void cargarArchivoClienteListener(FileUploadEvent e){
        LOGGER.info("LOGGER :: CargaArchivoClienteController :: cargarArchivoClienteListener");
        try {
            iniciarObjetos();
            this.rutaTemporal = Paths.get(this.props.getProperty(ConstantesSisgri.RUTA_TEMP)) ;
            UploadedFile uploadedFile = e.getFile();             
            if(uploadedFile != null) {  
                String filename = FilenameUtils.getName(uploadedFile.getFileName());
                String extension = FilenameUtils.getExtension(uploadedFile.getFileName());                
                this.archivoClienteMasivo.setNombreArchivoCliMasivo(filename.toLowerCase());
                this.archivoClienteMasivo.setArchivoCargado(IOUtils.toByteArray(uploadedFile.getInputstream()));               
                this.ocultarBoton = false;
                this.archivoTemporal = Files.createTempFile(this.rutaTemporal, filename + "-", "." + extension);                
                Files.copy(uploadedFile.getInputstream(), this.archivoTemporal, StandardCopyOption.REPLACE_EXISTING);                               
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,this.archivoClienteMasivo.getNombreArchivoCliMasivo() + "->" + this.mensajes.getString(ConstantesSisgri.MSJ_CARGA_OK), null));
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), null));            
            }
        }catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoClienteMasivo.getNombreArchivoCliMasivo() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_LECTURA_ARCHIVO) + "->" + ex.getMessage(), ex.getMessage()));
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }   
    
    /**
     * Metodo que permite recorrer el excel de la carga masiva de clientes
     * 
     * @param fechaActual 
     */
    private void recorrerArchivoMasivoExcel(Calendar fechaActual){   
        LOGGER.info("LOGGER :: recorrerArchivoClienteController :: recorrerArchivoMasivoExcel");                 
        try{
            Integer idArchivoCliente = EJBArchivo.obtenerIdSigArchivoCliente();
            FileInputStream entrada = new FileInputStream(this.archivoTemporal.toString());             
            XSSFWorkbook workbook = new XSSFWorkbook(entrada);
            XSSFSheet sheet = workbook.getSheetAt(ConstantesSisgri.INDICE_INICIAL);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            while (rowIterator.hasNext()) {
                row = rowIterator.next();  
                ClienteMasivo clienteMasivo = new ClienteMasivo();
                clienteMasivo.setClienteMasivoPK(new ClienteMasivoPK());
                clienteMasivo.getClienteMasivoPK().setIdArchivoCliMasivo(idArchivoCliente);
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell celda;
                while (cellIterator.hasNext()) {
                    celda = cellIterator.next();
                    switch(celda.getColumnIndex()) {
                        case 0:  
                            if(celda != null){
                                clienteMasivo.getClienteMasivoPK().setTipoIdCliente(celda.toString());                          
                            }
                            break;
                        case 1:
                            if(celda != null){
                                clienteMasivo.getClienteMasivoPK().setIdCliente(celda.toString());
                            }    
                            break;
                        case 2: 
                            if(celda != null){
                               clienteMasivo.setClienteUltimoNombre(celda.toString());
                            }    
                            break;    
                        case 3: 
                            clienteMasivo.setClientePrimerNombre(celda.toString());
                            break;                                
                    }
                }                
                clienteMasivo.setUsuarioCreacion(this.usuario.getNombreUsuario());
                clienteMasivo.setFechaCreacion(fechaActual.getTime());
                clienteMasivo.setUsuarioModificacion(this.usuario.getNombreUsuario());
                clienteMasivo.setFechaModificacion(fechaActual.getTime());
                this.listaClienteMasivo.add(clienteMasivo);
            }  
            this.archivoClienteMasivo.setIdArchivoCliMasivo(idArchivoCliente);
            entrada.close();
        }catch (FileNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoClienteMasivo.getNombreArchivoCliMasivo() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), ex.getMessage()));            
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoClienteMasivo.getNombreArchivoCliMasivo() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), ex.getMessage()));            
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SisgriException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoClienteMasivo.getNombreArchivoCliMasivo() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), ex.getMessage()));            
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    /**
     * Metodo que permite recorrer el archivo de tezto de clientes masivo
     * 
     * @param fechaActual 
     */
    private void recorrerArchivoMasivoTexto(Calendar fechaActual){
        LOGGER.info("LOGGER :: recorrerArchivoCLienteController :: recorrerArchivoMasivoTexto");
        try { 
            Integer idArchivoCliente = EJBArchivo.obtenerIdSigArchivoCliente();
            FileInputStream entrada = new FileInputStream(this.archivoTemporal.toString());
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            while ((strLinea = buffer.readLine()) != null){
                ClienteMasivo clienteMasivo = new ClienteMasivo();
                clienteMasivo.setClienteMasivoPK(new ClienteMasivoPK());
                String[] campo = strLinea.split(ConstantesSisgri.SEPARADOR_ARCH_TEXTO);                                            
                clienteMasivo.getClienteMasivoPK().setIdArchivoCliMasivo(idArchivoCliente);
                clienteMasivo.getClienteMasivoPK().setTipoIdCliente(campo[0] == null ? ConstantesSisgri.VACIO : campo[0]);
                clienteMasivo.getClienteMasivoPK().setIdCliente(campo[1] == null ? ConstantesSisgri.VACIO : campo[1]);
                clienteMasivo.setClienteUltimoNombre(campo[2] == null ? ConstantesSisgri.VACIO : campo[2]); 
                clienteMasivo.setClientePrimerNombre(campo[3]);                               
                clienteMasivo.setUsuarioCreacion(this.usuario.getNombreUsuario());
                clienteMasivo.setFechaCreacion(fechaActual.getTime());
                clienteMasivo.setUsuarioModificacion(this.usuario.getNombreUsuario());
                clienteMasivo.setFechaModificacion(fechaActual.getTime());  
                this.listaClienteMasivo.add(clienteMasivo);
            }
            this.archivoClienteMasivo.setIdArchivoCliMasivo(idArchivoCliente);
            entrada.close();              
        }catch (SisgriException e){ 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoClienteMasivo.getNombreArchivoCliMasivo() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), e.getMessage()));            
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,this.archivoClienteMasivo.getNombreArchivoCliMasivo() + this.mensajes.getString(ConstantesSisgri.MSJ_ERROR_CARGA), e.getMessage()));            
            Logger.getLogger(CargaArchivoController.class.getName()).log(Level.SEVERE, null, e);
        }                
    }
        
    /**
     * Metodo que permite almacenar el archivo de clientes para cruce masivo
     */
    public void procesarArchivoMasivo(){
        LOGGER.info("LOGGER :: CargaArchivoClienteController :: procesarArchivoMasivo"); 
        Calendar fechaActual = Calendar.getInstance();          
        Usuario usuarioSesion =  (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        fechaActual.get(Calendar.YEAR);
        fechaActual.get(Calendar.MONTH);
        fechaActual.get(Calendar.DAY_OF_MONTH); 
        try{   
            this.archivoClienteMasivo.setProcesado(ConstantesSisgri.PROCESADO_NO);
            this.archivoClienteMasivo.setFechaCarga(fechaActual.getTime());
            this.archivoClienteMasivo.setFechaCreacion(fechaActual.getTime());
            this.archivoClienteMasivo.setFechaModificacion(fechaActual.getTime());            
            this.archivoClienteMasivo.setUsuario(usuarioSesion);
            this.archivoClienteMasivo.setUsuarioCreacion(usuarioSesion.getNombreUsuario());
            this.archivoClienteMasivo.setUsuarioModificacion(usuarioSesion.getNombreUsuario());
            if(this.archivoClienteMasivo.getNombreArchivoCliMasivo().contains(ConstantesSisgri.EXTENSION_EXCEL)){
                this.recorrerArchivoMasivoExcel(fechaActual);
            }else{
                this.recorrerArchivoMasivoTexto(fechaActual);
            }
            this.archivoClienteMasivo.setClienteMasivoCollection(this.listaClienteMasivo);
            this.EJBArchivo.guardarArchivoCliente(this.archivoClienteMasivo);
            init();
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
