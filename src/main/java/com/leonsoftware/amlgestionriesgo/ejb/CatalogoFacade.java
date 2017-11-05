/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonsoftware.amlgestionriesgo.ejb;

import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.Catalogo;
import com.leonsoftware.amlgestionriesgo.model.ListaCatalogo;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author LeonSoftware 2017
 */
@Stateless
public class CatalogoFacade extends AbstractFacade<Catalogo> implements CatalogoFacadeLocal,Serializable {

        
    @PersistenceContext(unitName = "sisgriPU")
    private EntityManagerFactory entityManagerFactory;

    @Override
    protected EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory("sisgriPU").createEntityManager();
    }
    
    public CatalogoFacade() {
        super(Catalogo.class);
    }
    /**
     * Metodo para acceder a los registros de los catalogos
     * @param pNombreCatalogo
     * @return listaCatalogo
     * @throws SisgriException 
     */
    @Override
  public List<ListaCatalogo> obtenerListaCatalogo(String pNombreCatalogo) throws SisgriException{
        Catalogo catalogo = null; 
        List<ListaCatalogo> listaCatalogo = null;        
        try{   
            Query q = (TypedQuery<Catalogo>) getEntityManager().createNamedQuery("Catalogo.findByIdCatalogo").setParameter("idCatalogo", pNombreCatalogo.trim());           
            catalogo = (Catalogo) q.getSingleResult();                
            if(catalogo != null){
                listaCatalogo = (List<ListaCatalogo>) catalogo.getListaCatalogoCollection();
            }
        }catch(Exception e) {
            throw new SisgriException(e.getMessage());            
        }      
       return listaCatalogo;       
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
}
