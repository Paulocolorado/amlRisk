package com.leonsoftware.amlgestionriesgo.ejb;

import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.Menu;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author pc
 */
@Local
public interface MenuFacadeLocal {
    
    public List<Menu> buscarTodoMenu() throws SisgriException;
}
