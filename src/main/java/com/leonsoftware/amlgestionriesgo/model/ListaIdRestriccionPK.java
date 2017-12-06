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
 * @author pc
 */
@Embeddable
public class ListaIdRestriccionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "lista_id_restriccion_id")
    private double listaIdRestriccionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tb_lista_restriccion_lista_id_registro")
    private double tbListaRestriccionListaIdRegistro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tb_archivo_fuente_id_archivo_fuente")
    private int tbArchivoFuenteIdArchivoFuente;

    public ListaIdRestriccionPK() {
    }

    public ListaIdRestriccionPK(double listaIdRestriccionId, double tbListaRestriccionListaIdRegistro, int tbArchivoFuenteIdArchivoFuente) {
        this.listaIdRestriccionId = listaIdRestriccionId;
        this.tbListaRestriccionListaIdRegistro = tbListaRestriccionListaIdRegistro;
        this.tbArchivoFuenteIdArchivoFuente = tbArchivoFuenteIdArchivoFuente;
    }

    public double getListaIdRestriccionId() {
        return listaIdRestriccionId;
    }

    public void setListaIdRestriccionId(double listaIdRestriccionId) {
        this.listaIdRestriccionId = listaIdRestriccionId;
    }

    public double getTbListaRestriccionListaIdRegistro() {
        return tbListaRestriccionListaIdRegistro;
    }

    public void setTbListaRestriccionListaIdRegistro(double tbListaRestriccionListaIdRegistro) {
        this.tbListaRestriccionListaIdRegistro = tbListaRestriccionListaIdRegistro;
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
        hash += (int) listaIdRestriccionId;
        hash += (int) tbListaRestriccionListaIdRegistro;
        hash += (int) tbArchivoFuenteIdArchivoFuente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaIdRestriccionPK)) {
            return false;
        }
        ListaIdRestriccionPK other = (ListaIdRestriccionPK) object;
        if (this.listaIdRestriccionId != other.listaIdRestriccionId) {
            return false;
        }
        if (this.tbListaRestriccionListaIdRegistro != other.tbListaRestriccionListaIdRegistro) {
            return false;
        }
        if (this.tbArchivoFuenteIdArchivoFuente != other.tbArchivoFuenteIdArchivoFuente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.model.ListaIdRestriccionPK[ listaIdRestriccionId=" + listaIdRestriccionId + ", tbListaRestriccionListaIdRegistro=" + tbListaRestriccionListaIdRegistro + ", tbArchivoFuenteIdArchivoFuente=" + tbArchivoFuenteIdArchivoFuente + " ]";
    }
    
}
