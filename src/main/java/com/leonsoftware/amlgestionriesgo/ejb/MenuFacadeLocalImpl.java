package com.leonsoftware.amlgestionriesgo.ejb;

import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.Menu;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Carolina Colorado
 * Clase para el manejo dinámico del menú
 */
@Stateless
public class MenuFacadeLocalImpl extends AbstractFacade<Menu> implements MenuFacadeLocal, Serializable {

    private static final Logger LOGGER = Logger.getLogger("MenuFacadeLocalImpl");
    private EntityManager em;
    private EntityManagerFactory emf;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
            
    public MenuFacadeLocalImpl() {
        super(Menu.class);
        this.emf = Persistence.createEntityManagerFactory("sisgriPU");
        this.em = emf.createEntityManager();
    }
        
    @Override
    public List<Menu> buscarTodoMenu() throws SisgriException   {
        LOGGER.info("LOGGER :: MenuFacadeLocalImpl :: buscarTodoMenu");
        List <Menu> listaMenu = new ArrayList<Menu>();                        
        String consulta;        
        try{     
            consulta = "SELECT u FROM Menu u " ;
            Query q = em.createQuery(consulta);    
            listaMenu = q.getResultList();  
        }catch(Exception e) {
            throw new SisgriException("Error al consultar la base de datos" + e.getMessage());            
        }                      
        return listaMenu;
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
    
    
    /**
     * METODOS SET Y GET
     */
    
    
}
