/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonsoftware.amlgestionriesgo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karol
 */
@Entity
@Table(name = "tb_lista_catalogo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaCatalogo.findAll", query = "SELECT l FROM ListaCatalogo l")
    , @NamedQuery(name = "ListaCatalogo.findByNombreListaCatalogo", query = "SELECT l FROM ListaCatalogo l WHERE l.listaCatalogoPK.nombreListaCatalogo = :nombreListaCatalogo")
    , @NamedQuery(name = "ListaCatalogo.findByValorListaCatalogo", query = "SELECT l FROM ListaCatalogo l WHERE l.listaCatalogoPK.valorListaCatalogo = :valorListaCatalogo")
    , @NamedQuery(name = "ListaCatalogo.findByFechaCreacion", query = "SELECT l FROM ListaCatalogo l WHERE l.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "ListaCatalogo.findByUsuarioCreacion", query = "SELECT l FROM ListaCatalogo l WHERE l.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "ListaCatalogo.findByFechaModificacion", query = "SELECT l FROM ListaCatalogo l WHERE l.fechaModificacion = :fechaModificacion")
    , @NamedQuery(name = "ListaCatalogo.findByUsuarioModificacion", query = "SELECT l FROM ListaCatalogo l WHERE l.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "ListaCatalogo.findByTbCatalogoIdCatalogo", query = "SELECT l FROM ListaCatalogo l WHERE l.listaCatalogoPK.tbCatalogoIdCatalogo = :tbCatalogoIdCatalogo")})
public class ListaCatalogo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ListaCatalogoPK listaCatalogoPK;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 15)
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Size(max = 45)
    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;
    @JoinColumn(name = "tb_catalogo_id_catalogo", referencedColumnName = "id_catalogo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Catalogo catalogo;

    public ListaCatalogo() {
    }

    public ListaCatalogo(ListaCatalogoPK listaCatalogoPK) {
        this.listaCatalogoPK = listaCatalogoPK;
    }

    public ListaCatalogo(String nombreListaCatalogo, String valorListaCatalogo, String tbCatalogoIdCatalogo) {
        this.listaCatalogoPK = new ListaCatalogoPK(nombreListaCatalogo, valorListaCatalogo, tbCatalogoIdCatalogo);
    }

    public ListaCatalogoPK getListaCatalogoPK() {
        return listaCatalogoPK;
    }

    public void setListaCatalogoPK(ListaCatalogoPK listaCatalogoPK) {
        this.listaCatalogoPK = listaCatalogoPK;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listaCatalogoPK != null ? listaCatalogoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaCatalogo)) {
            return false;
        }
        ListaCatalogo other = (ListaCatalogo) object;
        if ((this.listaCatalogoPK == null && other.listaCatalogoPK != null) || (this.listaCatalogoPK != null && !this.listaCatalogoPK.equals(other.listaCatalogoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.amlrisktomcat.ListaCatalogo[ listaCatalogoPK=" + listaCatalogoPK + " ]";
    }
    
}
