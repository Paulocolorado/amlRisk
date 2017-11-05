/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonsoftware.amlgestionriesgo.ejb;

import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.ListaCatalogo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LeonSoftware 2017
 */
@Local
public interface CatalogoFacadeLocal {
    public List<ListaCatalogo> obtenerListaCatalogo(String pNombreCatalogo) throws SisgriException;
    
}
