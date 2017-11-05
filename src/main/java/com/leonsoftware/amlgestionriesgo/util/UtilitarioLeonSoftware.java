/**
 *
 * @author LeonSoftware 2017
 */
package com.leonsoftware.amlgestionriesgo.util;

import com.leonsoftware.amlgestionriesgo.ejb.CatalogoFacade;
import com.leonsoftware.amlgestionriesgo.ejb.CatalogoFacadeLocal;
import java.util.ResourceBundle;
import java.util.logging.Logger;


/**
 * Clase de métodos utilitarios
 * @since 02/10/2017
 * @author Carolina Colorado
 * @version 1.0
 */
public class UtilitarioLeonSoftware {
    
    
    private final CatalogoFacadeLocal EJBcatalogo = new CatalogoFacade();

    public UtilitarioLeonSoftware() {
  
    }
        
    private static final Logger LOGGER = Logger.getLogger("UtilitarioLeonSoftware"); 


    /**
     * Método que carga archivo de mensajes
     * 
     * @return ResourceBundle
     */
    public ResourceBundle cargarMensajes(){
        LOGGER.info("LOGGER :: UtilitarioLeonSoftware :: cargarMensajes");           
        ResourceBundle mensajes = ResourceBundle.getBundle(ConstantesSisgri.ARCHIVO_MENSAJES);                
        return  mensajes; 
    }       
    
}
