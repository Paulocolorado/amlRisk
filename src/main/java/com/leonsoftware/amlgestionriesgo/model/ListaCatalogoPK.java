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
import javax.validation.constraints.Size;

/**
 *
 * @author karol
 */
@Embeddable
public class ListaCatalogoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre_lista_catalogo")
    private String nombreListaCatalogo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "valor_lista_catalogo")
    private String valorListaCatalogo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tb_catalogo_id_catalogo")
    private String tbCatalogoIdCatalogo;

    public ListaCatalogoPK() {
    }

    public ListaCatalogoPK(String nombreListaCatalogo, String valorListaCatalogo, String tbCatalogoIdCatalogo) {
        this.nombreListaCatalogo = nombreListaCatalogo;
        this.valorListaCatalogo = valorListaCatalogo;
        this.tbCatalogoIdCatalogo = tbCatalogoIdCatalogo;
    }

    public String getNombreListaCatalogo() {
        return nombreListaCatalogo;
    }

    public void setNombreListaCatalogo(String nombreListaCatalogo) {
        this.nombreListaCatalogo = nombreListaCatalogo;
    }

    public String getValorListaCatalogo() {
        return valorListaCatalogo;
    }

    public void setValorListaCatalogo(String valorListaCatalogo) {
        this.valorListaCatalogo = valorListaCatalogo;
    }

    public String getTbCatalogoIdCatalogo() {
        return tbCatalogoIdCatalogo;
    }

    public void setTbCatalogoIdCatalogo(String tbCatalogoIdCatalogo) {
        this.tbCatalogoIdCatalogo = tbCatalogoIdCatalogo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreListaCatalogo != null ? nombreListaCatalogo.hashCode() : 0);
        hash += (valorListaCatalogo != null ? valorListaCatalogo.hashCode() : 0);
        hash += (tbCatalogoIdCatalogo != null ? tbCatalogoIdCatalogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaCatalogoPK)) {
            return false;
        }
        ListaCatalogoPK other = (ListaCatalogoPK) object;
        if ((this.nombreListaCatalogo == null && other.nombreListaCatalogo != null) || (this.nombreListaCatalogo != null && !this.nombreListaCatalogo.equals(other.nombreListaCatalogo))) {
            return false;
        }
        if ((this.valorListaCatalogo == null && other.valorListaCatalogo != null) || (this.valorListaCatalogo != null && !this.valorListaCatalogo.equals(other.valorListaCatalogo))) {
            return false;
        }
        if ((this.tbCatalogoIdCatalogo == null && other.tbCatalogoIdCatalogo != null) || (this.tbCatalogoIdCatalogo != null && !this.tbCatalogoIdCatalogo.equals(other.tbCatalogoIdCatalogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.amlrisktomcat.ListaCatalogoPK[ nombreListaCatalogo=" + nombreListaCatalogo + ", valorListaCatalogo=" + valorListaCatalogo + ", tbCatalogoIdCatalogo=" + tbCatalogoIdCatalogo + " ]";
    }
    
}
