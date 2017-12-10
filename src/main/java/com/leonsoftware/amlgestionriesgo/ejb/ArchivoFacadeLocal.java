/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonsoftware.amlgestionriesgo.ejb;

import com.leonsoftware.amlgestionriesgo.exception.SisgriException;
import com.leonsoftware.amlgestionriesgo.model.ArchivoClienteMasivo;
import com.leonsoftware.amlgestionriesgo.model.ArchivoFuente;
import com.leonsoftware.amlgestionriesgo.model.CruceClienteLista;
import com.leonsoftware.amlgestionriesgo.model.ListaRestriccion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LeonSoftware 2017
 */
@Local
public interface ArchivoFacadeLocal {
    
    public void guardarArchivo(ArchivoFuente pArchivoFuente) throws SisgriException;
    
    public Integer obtnerIdSigArchivo () throws SisgriException;
    
    public List<ListaRestriccion> buscarListaCoincidencia(String[] parametros, String pNumeroID) throws SisgriException;
    
    public void guardarArchivoCliente(ArchivoClienteMasivo parchivoClienteMasivo) throws SisgriException;
    
    public List<ArchivoClienteMasivo> consultaArchivoMasivo() throws SisgriException;
    
    public void cruzarClientes(ArchivoClienteMasivo parchivoClienteMasivo) throws SisgriException;    
    
    public List<CruceClienteLista> consultarCruceClienteLista(ArchivoClienteMasivo parchivoClienteMasivo) throws SisgriException;
}
