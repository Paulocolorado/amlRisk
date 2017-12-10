/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonsoftware.amlgestionriesgo.ejb;

import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.ArchivoClienteMasivo;
import com.leonsoftware.amlgestionriesgo.model.ArchivoFuente;
import com.leonsoftware.amlgestionriesgo.model.Catalogo;
import com.leonsoftware.amlgestionriesgo.model.CruceClienteLista;
import com.leonsoftware.amlgestionriesgo.model.ListaRestriccion;
import com.leonsoftware.amlgestionriesgo.util.ConstantesSisgri;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;



/**
 *
 * @author LeonSoftware 2017
 */
@Stateless
public class ArchivoFacade extends AbstractFacade<Catalogo> implements ArchivoFacadeLocal,Serializable {

    private static final Logger LOGGER = Logger.getLogger("CargaArchivoController");     
    
    @PersistenceContext(unitName = "sisgriPU")
    private EntityManager em;


    @Override
    protected EntityManager getEntityManager() {        
        return Persistence.createEntityManagerFactory("sisgriPU").createEntityManager();
    }
    
    public ArchivoFacade() {
        super(Catalogo.class);
    }
    
    /**
     * Metodo que almacena el encabezado del archivo  posteriormente ejecuta trogger llenado de listas de restriccion
     * @param pArchivoFuente
     * @throws SisgriException 
     */
    @Override
    @Transactional
     public void guardarArchivo(ArchivoFuente pArchivoFuente) throws SisgriException{             
        LOGGER.info("LOGGER :: ArchivoFacade :: guardarArchivo");
        try{ 
            this.em = this.getEntityManager();
            this.em.getTransaction().begin();
            this.borrarArchivoRestriccion(pArchivoFuente.getNombreFuente());
            this.em.persist(pArchivoFuente);              
            this.em.getTransaction().commit(); 
            this.em.close();
        }catch(Exception e){
            throw new SisgriException(e.getMessage());
        }
    }
     
    public void borrarArchivoRestriccion(String pNombreFuente) throws SisgriException{             
        LOGGER.info("LOGGER :: ArchivoFacade :: borrarArchivoRestriccion");
        String consulta; 
        try{     
            consulta = " DELETE l.* FROM tb_lista_restriccion l, tb_archivo_fuente  a WHERE a.id_archivo_fuente = l.tb_archivo_fuente_id_archivo_fuente AND a.nombre_fuente LIKE '%" + pNombreFuente + "%'";
            Query q = this.em.createNativeQuery(consulta);     
            q.executeUpdate();            
        }catch(NoResultException e) {
            throw new SisgriException(e.getMessage());            
        }              
    }
     
     /**
      * Metodo que permite obtener el Id siguiente de la tabla de archivo para insertar
      * @return
      * @throws SisgriException 
      */
     @Override
     public Integer obtnerIdSigArchivo () throws SisgriException{
        Integer sigIdArchivo =  null;      
        String consulta;        
        try{     
            consulta = "SELECT MAX(a.id_archivo_fuente) FROM tb_archivo_fuente a " ;
            Query q = this.getEntityManager().createNativeQuery(consulta);     
            sigIdArchivo = (Integer) q.getSingleResult();
            if(sigIdArchivo == null){
               sigIdArchivo = 0;
            }
        }catch(NoResultException e) {
            throw new SisgriException(e.getMessage());            
        }              
        return sigIdArchivo + 1;
                 
     }
     
    /**
     * metodo que permite buscar las coincidencias por criterio
     * @param parametros
     * @return
     * @throws SisgriException 
     */ 
    @Override
     public List<ListaRestriccion> buscarListaCoincidencia(String[] parametros, String pNumeroID) throws SisgriException{         
        String consulta; 
        boolean bandera = ConstantesSisgri.FALSO;
        String condicion = ConstantesSisgri.ESPACIO_BLANCO;

        List<ListaRestriccion> listaClienteCoincide = new ArrayList();         
        try{
            if(!pNumeroID.equals(ConstantesSisgri.VACIO)){
                consulta = " SELECT DISTINCT l FROM ListaRestriccion l, ListaIdRestriccion id  WHERE "+
                           " l.listaRestriccionPK.listaIdRegistro = id.listaIdRestriccionPK.tbListaRestriccionListaIdRegistro AND " +
                           " l.listaRestriccionPK.tbArchivoFuenteIdArchivoFuente = id.listaIdRestriccionPK.tbArchivoFuenteIdArchivoFuente AND " +
                           " id.numeroId LIKE trim(lower('%" + pNumeroID + "%')) ";                     
            }else{
                for (String parametro : parametros) {
                    if(bandera){
                        condicion = condicion + " OR ";
                    }
                    condicion = condicion + " trim(lower(l.listaPrimerNombre)) LIKE trim(lower('%" + parametro + "%')) OR " 
                                          + " trim(lower(l.listaUltimoNombre)) LIKE trim(lower('%" + parametro + "%')) ";                
                    bandera = ConstantesSisgri.VERDADERO;
                }
                consulta = "SELECT DISTINCT l FROM ListaRestriccion l  WHERE " + condicion;                     
            }            
            Query q = this.getEntityManager().createQuery(consulta);          
            listaClienteCoincide = q.getResultList();
        }catch(Exception e){
            throw new SisgriException(e.getMessage());  
        }       
        return listaClienteCoincide;
     }
    
     /**
      * Metodo que permite guiardar el archivo cliente a cruzar de forma masiva
      * @param parchivoClienteMasivo
      * @throws SisgriException 
      */
    @Override
    @Transactional
     public void guardarArchivoCliente(ArchivoClienteMasivo parchivoClienteMasivo) throws SisgriException{             
        LOGGER.info("LOGGER :: ArchivoFacade :: guardarArchivo");
        try{
            EntityManager em = this.getEntityManager();
            parchivoClienteMasivo.setIdArchivoCliMasivo(obtnerIdSigArchivoCliente()); 
            em.getTransaction().begin();
            em.persist(parchivoClienteMasivo);  
            em.getTransaction().commit();         
        }catch(Exception e){
            throw new SisgriException(e.getMessage());
        }
    }
     
      /**
      * Metodo que permite obtener el Id siguiente de la tabla de tb_archivo_cliente_masivo para insertar
      * @return
      * @throws SisgriException 
      */
     public Integer obtnerIdSigArchivoCliente() throws SisgriException{
        Integer sigIdArchivoCliente =  null;      
        String consulta;        
        try{     
            consulta = "SELECT MAX(a.id_archivo_cli_masivo) FROM tb_archivo_cliente_masivo a " ;
            Query q = this.getEntityManager().createNativeQuery(consulta);     
            sigIdArchivoCliente = (Integer) q.getSingleResult();
            if(sigIdArchivoCliente == null){
               sigIdArchivoCliente = 0;
            }
        }catch(NoResultException e) {
            throw new SisgriException(e.getMessage());            
        }              
        return sigIdArchivoCliente + 1;
                 
     }
    
     /**
      * Metodo que consulta los archivos para cruce maasivo cargados
      * @return lista de ArchivoClienteMasivo
      * @throws SisgriException 
      */
    @Override
    public List<ArchivoClienteMasivo> consultaArchivoMasivo() throws SisgriException{
        String consulta; 
        List<ArchivoClienteMasivo> listaArchivoClienteMasivo = new ArrayList();  
        try{
            consulta = "SELECT DISTINCT a FROM ArchivoClienteMasivo a ORDER BY a.fechaCarga";                     
            Query q = this.getEntityManager().createQuery(consulta);          
            listaArchivoClienteMasivo = q.getResultList();            
        }catch(Exception e){
            throw new SisgriException(e.getMessage());
        }
        return listaArchivoClienteMasivo;
    }
     
    
    
        @Override
    public void cruzarClientes(ArchivoClienteMasivo parchivoClienteMasivo) throws SisgriException{
        try{           
            StoredProcedureQuery query = this.getEntityManager().createStoredProcedureQuery("prc_cruzar_listas_clientes")
                                        .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                                        .setParameter(1, parchivoClienteMasivo.getIdArchivoCliMasivo());
 
            query.execute();            
        }catch(Exception e){
            throw new SisgriException(e.getMessage()); 
        }        
    }
    
    @Override
    public List<CruceClienteLista> consultarCruceClienteLista(ArchivoClienteMasivo parchivoClienteMasivo) throws SisgriException{
        List<CruceClienteLista>  listaCruceClienteLista = new ArrayList<CruceClienteLista>();
        String consulta; 
        try{
            consulta = "SELECT DISTINCT c FROM CruceClienteLista c WHERE c.idArchivoCliMasivo = :idArchivoCliente ";                                 
            Query q = this.getEntityManager().createQuery(consulta);          
            q.setParameter("idArchivoCliente", parchivoClienteMasivo.getIdArchivoCliMasivo());
            listaCruceClienteLista = q.getResultList();  
        }catch(Exception e){
            throw new SisgriException(e.getMessage());
        }        
        return listaCruceClienteLista;
    }
        
    
    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
}
