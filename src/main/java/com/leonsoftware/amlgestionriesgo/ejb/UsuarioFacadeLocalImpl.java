/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonsoftware.amlgestionriesgo.ejb;

import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.Usuario;
import com.leonsoftware.amlgestionriesgo.util.ConstantesSisgri;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

@Stateless
public class UsuarioFacadeLocalImpl extends AbstractFacade<Usuario> implements UsuarioFacadeLocal, Serializable {
    
	private static final Logger LOGGER = Logger.getLogger("UsuarioFacadeLocalImpl"); 
	private EntityManager em;
    private EntityManagerFactory emf;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacadeLocalImpl() {
        super(Usuario.class);        
        this.emf = Persistence.createEntityManagerFactory("sisgriPU");
        this.em = emf.createEntityManager();
    }
    
    /**
     * Metodo de autenticacion
     * @param pUsuario
     * @return Usuario
     * @throws SisgriException 
     */


    @Override
    public Usuario iniciarSesion(Usuario pUsuario) throws SisgriException{
        Usuario usuario = null; 
        List<Usuario> listaUsuario;         
        String consulta;        
        try{     
            consulta = "SELECT u FROM Usuario u WHERE u.usuario = :usuario AND u.claveUsuario = :claveUsuario " ;
            Query q = em.createQuery(consulta);
            q.setParameter("usuario", pUsuario.getUsuario().trim());
            q.setParameter("claveUsuario", pUsuario.getClaveUsuario().trim());         
            listaUsuario = q.getResultList();    
            if(!listaUsuario.isEmpty()){
                usuario = listaUsuario.get(0);
            }
        }catch(Exception e) {
            throw new SisgriException("Error al consultar la base de datos" + e.getMessage());            
        }      
       return usuario;       
    }
    /**
     * Metodo que busca por nombre de Usuario
     * @param pUsuario
     * @return Usuario
     * @throws SisgriException 
     */
        @Override
    public Usuario  recuperarUsuarioPorNombre(Usuario pUsuario) throws SisgriException{
        Usuario usuario = null; 
        List<Usuario> listaUsuario;         
        String consulta;        
        try{     
            consulta = "SELECT u FROM Usuario u WHERE u.usuario = :usuario" ;
            Query q = em.createQuery(consulta);
            q.setParameter("usuario", pUsuario.getUsuario().trim());
         
            listaUsuario = q.getResultList();    
            if(!listaUsuario.isEmpty()){
                usuario = listaUsuario.get(0);
            }
        }catch(Exception e) {
            throw new SisgriException("Error al consultar la base de datos" + e.getMessage());            
        }      
       return usuario;       
    }
    
    /**
     * Metodo que almacena el guarda el nuevo usuario creado
     * @param pUsuario
     * @throws SisgriException 
     */
    @Override    
    public void guardarUsuario(Usuario pUsuario) throws SisgriException{             
        LOGGER.info("LOGGER :: UsuarioFacadeLocalImpl :: guardarUsuario");
        EntityTransaction tx = em.getTransaction();
        try{  
            tx.begin();            
            this.em.getEntityManagerFactory().getCache().evictAll();
            this.em.persist(pUsuario);            
            tx.commit();            
        }catch(Exception e){
            throw new SisgriException(e.getMessage());
        }        
    }
        
    /**
     * Metodo que recupera el usuario
     * @param pUsuario
     * @return ClaveUsuario
     * @throws SisgriException 
     */
    @Override
    public List<Usuario> buscarUsuario(Usuario pUsuario) throws SisgriException{
        List<Usuario> listaUsuario;         
        String consulta;        //" id.numeroId LIKE trim(lower('%" + pNumeroID + "%'))"
        try{      //SELECT * FROM bd_sisgri.tb_usuario where (nombre_usuario like '%ad%' OR apellido_usuario like '%ad%') and usuario like '%amlRisk%'
            consulta = "SELECT u FROM Usuario u "; //WHERE u.usuario LIKE :usuario  OR u.nombreUsuario LIKE :nombreUsuario OR u.apellidoUsuario LIKE :nombreUsuario
           
			if(pUsuario.getUsuario().equals(ConstantesSisgri.VACIO)) { 
            	consulta = consulta.concat("WHERE u.nombreUsuario LIKE '%" +  pUsuario.getNombreUsuario().trim().toLowerCase() + "%' OR u.apellidoUsuario LIKE '%" + pUsuario.getNombreUsuario().trim().toLowerCase() +"%'" );
            } else {
            	consulta = consulta.concat("WHERE u.usuario LIKE '%" +  pUsuario.getUsuario().trim().toLowerCase() + "%'" );
            }
  			 Query q = em.createQuery(consulta);
            listaUsuario = q.getResultList();    
        }catch(Exception e) {
            throw new SisgriException("Error al consultar la base de datos" + e.getMessage());            
        }      
       return listaUsuario;       
    }
    

    public Usuario recuperarClave(Usuario pUsuario) throws SisgriException{
        Usuario usuario = null;
        Usuario ClaveUsuario = null;
        List<Usuario> listaUsuario;         
        String consulta;        
        try{     
            consulta = "SELECT u FROM Usuario u WHERE u.usuario = :usuario" ;
            Query q = em.createQuery(consulta);
            q.setParameter("usuario", pUsuario.getUsuario().trim());
            q.setParameter("claveUsuario", pUsuario.getClaveUsuario().trim());         
            listaUsuario = q.getResultList();    
            if(!listaUsuario.isEmpty()){
                usuario = listaUsuario.get(0);
            }
        }catch(Exception e) {
            throw new SisgriException("Error al consultar la base de datos" + e.getMessage());            
        }      
       return ClaveUsuario;       
    }
    
    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
}
