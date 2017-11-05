/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonsoftware.amlgestionriesgo.ejb;

import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@Stateless
public class UsuarioFacadeLocalImpl extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {
    
    private EntityManager em;
    private EntityManagerFactory emf;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacadeLocalImpl() {
        super(Usuario.class);        
        this.emf= Persistence.createEntityManagerFactory("sisgriPU");
        this.em = emf.createEntityManager();
    }
    
    /**
     * Metodo de autenticacion
     * @param pUsuario
     * @return
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
