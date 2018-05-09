/**
 *
 * @author LeonSoftware 2017
 */
package com.leonsoftware.amlgestionriesgo.util;

import com.leonsoftware.amlgestionriesgo.ejb.CatalogoFacade;
import com.leonsoftware.amlgestionriesgo.ejb.CatalogoFacadeLocal;
import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


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
   
    /**
     * Metodo que carga las variables de configuracion del sistema
     * @return Properties
     * @throws SisgriException 
     */
    public Properties cargarConfiguracion() throws SisgriException{
        LOGGER.info("LOGGER :: UtilitarioLeonSoftware :: cargarConfiguracionProperties");
        Properties p = new Properties();
        try {
            InputStream in = this.getClass().getResourceAsStream(ConstantesSisgri.ARCHIVO_CONFIGURACION);
            p.load(in);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UtilitarioLeonSoftware.class.getName()).log(Level.SEVERE, null, ex);
            throw new SisgriException(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(UtilitarioLeonSoftware.class.getName()).log(Level.SEVERE, null, ex);
            throw new SisgriException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(UtilitarioLeonSoftware.class.getName()).log(Level.SEVERE, null, ex);
            throw new SisgriException(ex.getMessage());
        }  
        return p;
    }
    /**
     * Este metodo envia correo
     * @param pCorreo
     * @param pMensaje
     * @throws SisgriException 
     */
    public void enviarCorreo(String pCorreo, String pMensaje) throws SisgriException{
        
        try{
            Properties props = cargarConfiguracion();
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(Boolean.valueOf(props.getProperty(ConstantesSisgri.CORREO_SESSIONDEBUG)));
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(props.getProperty(ConstantesSisgri.CORREO_MAILCORREO)));
            InternetAddress[] address = {new InternetAddress(pCorreo)};//este se debe cambiar por this.correoUsuario se uso uno prueba
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(props.getProperty(ConstantesSisgri.CORREO_MAILDESDE));
            msg.setSentDate(new Date());
            msg.setText(pMensaje);

            Transport transport=mailSession.getTransport("smtp");
            transport.connect(props.getProperty(ConstantesSisgri.CORREO_MAILHOST), props.getProperty(ConstantesSisgri.CORREO_MAILCORREO), props.getProperty(ConstantesSisgri.CORREO_MAILCLAVE));
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        }catch(SisgriException ex){
            Logger.getLogger(UtilitarioLeonSoftware.class.getName()).log(Level.SEVERE, null, ex);
            throw new SisgriException(ex.getMessage());
        } catch (MessagingException ex) {
            Logger.getLogger(UtilitarioLeonSoftware.class.getName()).log(Level.SEVERE, null, ex);
            throw new SisgriException(ex.getMessage());
        }

}
}
