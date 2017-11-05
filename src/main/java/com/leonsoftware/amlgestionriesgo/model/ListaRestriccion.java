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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karol
 */
@Entity
@Table(name = "tb_lista_restriccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaRestriccion.findAll", query = "SELECT l FROM ListaRestriccion l")
    , @NamedQuery(name = "ListaRestriccion.findByListaIdRegistro", query = "SELECT l FROM ListaRestriccion l WHERE l.listaRestriccionPK.listaIdRegistro = :listaIdRegistro")
    , @NamedQuery(name = "ListaRestriccion.findByListaPrimerNombre", query = "SELECT l FROM ListaRestriccion l WHERE l.listaPrimerNombre = :listaPrimerNombre")
    , @NamedQuery(name = "ListaRestriccion.findByListaUltimoNombre", query = "SELECT l FROM ListaRestriccion l WHERE l.listaUltimoNombre = :listaUltimoNombre")
    , @NamedQuery(name = "ListaRestriccion.findByListaFechaReporte", query = "SELECT l FROM ListaRestriccion l WHERE l.listaFechaReporte = :listaFechaReporte")
    , @NamedQuery(name = "ListaRestriccion.findByListaObservacion", query = "SELECT l FROM ListaRestriccion l WHERE l.listaObservacion = :listaObservacion")
    , @NamedQuery(name = "ListaRestriccion.findByFechaCreacion", query = "SELECT l FROM ListaRestriccion l WHERE l.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "ListaRestriccion.findByUsuarioCreacion", query = "SELECT l FROM ListaRestriccion l WHERE l.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "ListaRestriccion.findByFechaModificacion", query = "SELECT l FROM ListaRestriccion l WHERE l.fechaModificacion = :fechaModificacion")
    , @NamedQuery(name = "ListaRestriccion.findByUsuarioModificacion", query = "SELECT l FROM ListaRestriccion l WHERE l.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "ListaRestriccion.findByTbArchivoFuenteIdArchivoFuente", query = "SELECT l FROM ListaRestriccion l WHERE l.listaRestriccionPK.tbArchivoFuenteIdArchivoFuente = :tbArchivoFuenteIdArchivoFuente")})
public class ListaRestriccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ListaRestriccionPK listaRestriccionPK;
    @Size(max = 500)
    @Column(name = "lista_primer_nombre")
    private String listaPrimerNombre;
    @Size(max = 500)
    @Column(name = "lista_ultimo_nombre")
    private String listaUltimoNombre;
    @Column(name = "lista_fecha_reporte")
    @Temporal(TemporalType.TIMESTAMP)
    private Date listaFechaReporte;
    @Size(max = 5000)
    @Column(name = "lista_observacion")
    private String listaObservacion;
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
    @JoinColumn(name = "tb_archivo_fuente_id_archivo_fuente", referencedColumnName = "id_archivo_fuente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ArchivoFuente archivoFuente;
    @Transient
    private Double porcentaje;    

    public ListaRestriccion() {
    }

    public ListaRestriccion(ListaRestriccionPK listaRestriccionPK) {
        this.listaRestriccionPK = listaRestriccionPK;
    }

    public ListaRestriccion(double listaIdRegistro, int tbArchivoFuenteIdArchivoFuente) {
        this.listaRestriccionPK = new ListaRestriccionPK(listaIdRegistro, tbArchivoFuenteIdArchivoFuente);
    }

    public ListaRestriccionPK getListaRestriccionPK() {
        return listaRestriccionPK;
    }

    public void setListaRestriccionPK(ListaRestriccionPK listaRestriccionPK) {
        this.listaRestriccionPK = listaRestriccionPK;
    }

    public String getListaPrimerNombre() {
        return listaPrimerNombre;
    }

    public void setListaPrimerNombre(String listaPrimerNombre) {
        this.listaPrimerNombre = listaPrimerNombre;
    }

    public String getListaUltimoNombre() {
        return listaUltimoNombre;
    }

    public void setListaUltimoNombre(String listaUltimoNombre) {
        this.listaUltimoNombre = listaUltimoNombre;
    }

    public Date getListaFechaReporte() {
        return listaFechaReporte;
    }

    public void setListaFechaReporte(Date listaFechaReporte) {
        this.listaFechaReporte = listaFechaReporte;
    }

    public String getListaObservacion() {
        return listaObservacion;
    }

    public void setListaObservacion(String listaObservacion) {
        this.listaObservacion = listaObservacion;
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

    public ArchivoFuente getArchivoFuente() {
        return archivoFuente;
    }

    public void setArchivoFuente(ArchivoFuente archivoFuente) {
        this.archivoFuente = archivoFuente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listaRestriccionPK != null ? listaRestriccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaRestriccion)) {
            return false;
        }
        ListaRestriccion other = (ListaRestriccion) object;
        if ((this.listaRestriccionPK == null && other.listaRestriccionPK != null) || (this.listaRestriccionPK != null && !this.listaRestriccionPK.equals(other.listaRestriccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.amlrisktomcat.ListaRestriccion[ listaRestriccionPK=" + listaRestriccionPK + " ]";
    }
    
    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
    
}
