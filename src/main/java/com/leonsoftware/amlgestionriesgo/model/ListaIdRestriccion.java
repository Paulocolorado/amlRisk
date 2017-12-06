/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonsoftware.amlgestionriesgo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "tb_lista_id_restriccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaIdRestriccion.findAll", query = "SELECT l FROM ListaIdRestriccion l")
    , @NamedQuery(name = "ListaIdRestriccion.findByListaIdRestriccionId", query = "SELECT l FROM ListaIdRestriccion l WHERE l.listaIdRestriccionPK.listaIdRestriccionId = :listaIdRestriccionId")
    , @NamedQuery(name = "ListaIdRestriccion.findByTipoId", query = "SELECT l FROM ListaIdRestriccion l WHERE l.tipoId = :tipoId")
    , @NamedQuery(name = "ListaIdRestriccion.findByNumeroId", query = "SELECT l FROM ListaIdRestriccion l WHERE l.numeroId = :numeroId")
    , @NamedQuery(name = "ListaIdRestriccion.findByPaisId", query = "SELECT l FROM ListaIdRestriccion l WHERE l.paisId = :paisId")
    , @NamedQuery(name = "ListaIdRestriccion.findByFechaCreacion", query = "SELECT l FROM ListaIdRestriccion l WHERE l.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "ListaIdRestriccion.findByUsuarioCreacion", query = "SELECT l FROM ListaIdRestriccion l WHERE l.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "ListaIdRestriccion.findByFechaModificacion", query = "SELECT l FROM ListaIdRestriccion l WHERE l.fechaModificacion = :fechaModificacion")
    , @NamedQuery(name = "ListaIdRestriccion.findByUsuarioModificacion", query = "SELECT l FROM ListaIdRestriccion l WHERE l.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "ListaIdRestriccion.findByTbListaRestriccionListaIdRegistro", query = "SELECT l FROM ListaIdRestriccion l WHERE l.listaIdRestriccionPK.tbListaRestriccionListaIdRegistro = :tbListaRestriccionListaIdRegistro")
    , @NamedQuery(name = "ListaIdRestriccion.findByTbArchivoFuenteIdArchivoFuente", query = "SELECT l FROM ListaIdRestriccion l WHERE l.listaIdRestriccionPK.tbArchivoFuenteIdArchivoFuente = :tbArchivoFuenteIdArchivoFuente")})
public class ListaIdRestriccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ListaIdRestriccionPK listaIdRestriccionPK;
    @Size(max = 50)
    @Column(name = "tipo_id")
    private String tipoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "numero_id")
    private String numeroId;
    @Size(max = 50)
    @Column(name = "pais_id")
    private String paisId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;
    @JoinColumns({
        @JoinColumn(name = "tb_lista_restriccion_lista_id_registro", referencedColumnName = "lista_id_registro", insertable = false, updatable = false)
        , @JoinColumn(name = "tb_archivo_fuente_id_archivo_fuente", referencedColumnName = "tb_archivo_fuente_id_archivo_fuente", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private ListaRestriccion listaRestriccion;

    public ListaIdRestriccion() {
    }

    public ListaIdRestriccion(ListaIdRestriccionPK listaIdRestriccionPK) {
        this.listaIdRestriccionPK = listaIdRestriccionPK;
    }

    public ListaIdRestriccion(ListaIdRestriccionPK listaIdRestriccionPK, String numeroId, Date fechaCreacion, String usuarioCreacion, Date fechaModificacion, String usuarioModificacion) {
        this.listaIdRestriccionPK = listaIdRestriccionPK;
        this.numeroId = numeroId;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
    }

    public ListaIdRestriccion(double listaIdRestriccionId, double tbListaRestriccionListaIdRegistro, int tbArchivoFuenteIdArchivoFuente) {
        this.listaIdRestriccionPK = new ListaIdRestriccionPK(listaIdRestriccionId, tbListaRestriccionListaIdRegistro, tbArchivoFuenteIdArchivoFuente);
    }

    public ListaIdRestriccionPK getListaIdRestriccionPK() {
        return listaIdRestriccionPK;
    }

    public void setListaIdRestriccionPK(ListaIdRestriccionPK listaIdRestriccionPK) {
        this.listaIdRestriccionPK = listaIdRestriccionPK;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public String getNumeroId() {
        return numeroId;
    }

    public void setNumeroId(String numeroId) {
        this.numeroId = numeroId;
    }

    public String getPaisId() {
        return paisId;
    }

    public void setPaisId(String paisId) {
        this.paisId = paisId;
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

    public ListaRestriccion getListaRestriccion() {
        return listaRestriccion;
    }

    public void setListaRestriccion(ListaRestriccion listaRestriccion) {
        this.listaRestriccion = listaRestriccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listaIdRestriccionPK != null ? listaIdRestriccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaIdRestriccion)) {
            return false;
        }
        ListaIdRestriccion other = (ListaIdRestriccion) object;
        if ((this.listaIdRestriccionPK == null && other.listaIdRestriccionPK != null) || (this.listaIdRestriccionPK != null && !this.listaIdRestriccionPK.equals(other.listaIdRestriccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.model.ListaIdRestriccion[ listaIdRestriccionPK=" + listaIdRestriccionPK + " ]";
    }
    
    
}
