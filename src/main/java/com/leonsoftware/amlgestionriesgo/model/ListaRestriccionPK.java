/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonsoftware.amlgestionriesgo.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author karol
 */
@Embeddable
public class ListaRestriccionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "lista_id_registro")
    private double listaIdRegistro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tb_archivo_fuente_id_archivo_fuente")
    private int tbArchivoFuenteIdArchivoFuente;

    public ListaRestriccionPK() {
    }

    public ListaRestriccionPK(double listaIdRegistro, int tbArchivoFuenteIdArchivoFuente) {
        this.listaIdRegistro = listaIdRegistro;
        this.tbArchivoFuenteIdArchivoFuente = tbArchivoFuenteIdArchivoFuente;
    }

    public double getListaIdRegistro() {
        return listaIdRegistro;
    }

    public void setListaIdRegistro(double listaIdRegistro) {
        this.listaIdRegistro = listaIdRegistro;
    }

    public int getTbArchivoFuenteIdArchivoFuente() {
        return tbArchivoFuenteIdArchivoFuente;
    }

    public void setTbArchivoFuenteIdArchivoFuente(int tbArchivoFuenteIdArchivoFuente) {
        this.tbArchivoFuenteIdArchivoFuente = tbArchivoFuenteIdArchivoFuente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) listaIdRegistro;
        hash += (int) tbArchivoFuenteIdArchivoFuente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaRestriccionPK)) {
            return false;
        }
        ListaRestriccionPK other = (ListaRestriccionPK) object;
        if (this.listaIdRegistro != other.listaIdRegistro) {
            return false;
        }
        if (this.tbArchivoFuenteIdArchivoFuente != other.tbArchivoFuenteIdArchivoFuente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.amlrisktomcat.ListaRestriccionPK[ listaIdRegistro=" + listaIdRegistro + ", tbArchivoFuenteIdArchivoFuente=" + tbArchivoFuenteIdArchivoFuente + " ]";
    }
    
}
