/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonsoftware.amlgestionriesgo.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author karol
 */
@Entity
@Table(name = "tb_catalogo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Catalogo.findAll", query = "SELECT c FROM Catalogo c")
    , @NamedQuery(name = "Catalogo.findByIdCatalogo", query = "SELECT c FROM Catalogo c WHERE c.idCatalogo = :idCatalogo")
    , @NamedQuery(name = "Catalogo.findByFechaCreacion", query = "SELECT c FROM Catalogo c WHERE c.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Catalogo.findByUsuarioCreacion", query = "SELECT c FROM Catalogo c WHERE c.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "Catalogo.findByFechaModificacion", query = "SELECT c FROM Catalogo c WHERE c.fechaModificacion = :fechaModificacion")
    , @NamedQuery(name = "Catalogo.findByUsuarioModificacion", query = "SELECT c FROM Catalogo c WHERE c.usuarioModificacion = :usuarioModificacion")})
public class Catalogo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "id_catalogo")
    private String idCatalogo;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 15)
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Size(max = 15)
    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catalogo")
    private Collection<ListaCatalogo> listaCatalogoCollection;

    public Catalogo() {
    }

    public Catalogo(String idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public String getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(String idCatalogo) {
        this.idCatalogo = idCatalogo;
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

    @XmlTransient
    public Collection<ListaCatalogo> getListaCatalogoCollection() {
        return listaCatalogoCollection;
    }

    public void setListaCatalogoCollection(Collection<ListaCatalogo> listaCatalogoCollection) {
        this.listaCatalogoCollection = listaCatalogoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatalogo != null ? idCatalogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Catalogo)) {
            return false;
        }
        Catalogo other = (Catalogo) object;
        if ((this.idCatalogo == null && other.idCatalogo != null) || (this.idCatalogo != null && !this.idCatalogo.equals(other.idCatalogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.amlrisktomcat.Catalogo[ idCatalogo=" + idCatalogo + " ]";
    }
    
}
