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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_archivo_fuente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArchivoFuente.findAll", query = "SELECT a FROM ArchivoFuente a")
    , @NamedQuery(name = "ArchivoFuente.findByIdArchivoFuente", query = "SELECT a FROM ArchivoFuente a WHERE a.idArchivoFuente = :idArchivoFuente")
    , @NamedQuery(name = "ArchivoFuente.findByNombreArchivoFuente", query = "SELECT a FROM ArchivoFuente a WHERE a.nombreArchivoFuente = :nombreArchivoFuente")
    , @NamedQuery(name = "ArchivoFuente.findByNombreFuente", query = "SELECT a FROM ArchivoFuente a WHERE a.nombreFuente = :nombreFuente")
    , @NamedQuery(name = "ArchivoFuente.findByNombreAccion", query = "SELECT a FROM ArchivoFuente a WHERE a.nombreAccion = :nombreAccion")
    , @NamedQuery(name = "ArchivoFuente.findByFechaCarga", query = "SELECT a FROM ArchivoFuente a WHERE a.fechaCarga = :fechaCarga")
    , @NamedQuery(name = "ArchivoFuente.findByFechaCreacion", query = "SELECT a FROM ArchivoFuente a WHERE a.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "ArchivoFuente.findByUsuarioCreacion", query = "SELECT a FROM ArchivoFuente a WHERE a.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "ArchivoFuente.findByFechaModificacion", query = "SELECT a FROM ArchivoFuente a WHERE a.fechaModificacion = :fechaModificacion")
    , @NamedQuery(name = "ArchivoFuente.findByUsuarioModificacion", query = "SELECT a FROM ArchivoFuente a WHERE a.usuarioModificacion = :usuarioModificacion")})
public class ArchivoFuente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_archivo_fuente")
    private Integer idArchivoFuente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_archivo_fuente")
    private String nombreArchivoFuente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "nombre_fuente")
    private String nombreFuente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "nombre_accion")
    private String nombreAccion;
    @Column(name = "fecha_carga")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCarga;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "archivo_cargado")
    private byte[] archivoCargado;
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
    @JoinColumns({
        @JoinColumn(name = "tb_usuario_id_usuario", referencedColumnName = "id_usuario")
        , @JoinColumn(name = "tb_usuario_tipo_id_usuario", referencedColumnName = "tipo_id_usuario")})
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "archivoFuente")
    private Collection<ListaRestriccion> listaRestriccionCollection;

    public ArchivoFuente() {
    }

    public ArchivoFuente(Integer idArchivoFuente) {
        this.idArchivoFuente = idArchivoFuente;
    }

    public ArchivoFuente(Integer idArchivoFuente, String nombreArchivoFuente, String nombreFuente, String nombreAccion, byte[] archivoCargado) {
        this.idArchivoFuente = idArchivoFuente;
        this.nombreArchivoFuente = nombreArchivoFuente;
        this.nombreFuente = nombreFuente;
        this.nombreAccion = nombreAccion;
        this.archivoCargado = archivoCargado;
    }

    public Integer getIdArchivoFuente() {
        return idArchivoFuente;
    }

    public void setIdArchivoFuente(Integer idArchivoFuente) {
        this.idArchivoFuente = idArchivoFuente;
    }

    public String getNombreArchivoFuente() {
        return nombreArchivoFuente;
    }

    public void setNombreArchivoFuente(String nombreArchivoFuente) {
        this.nombreArchivoFuente = nombreArchivoFuente;
    }

    public String getNombreFuente() {
        return nombreFuente;
    }

    public void setNombreFuente(String nombreFuente) {
        this.nombreFuente = nombreFuente;
    }

    public String getNombreAccion() {
        return nombreAccion;
    }

    public void setNombreAccion(String nombreAccion) {
        this.nombreAccion = nombreAccion;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public byte[] getArchivoCargado() {
        return archivoCargado;
    }

    public void setArchivoCargado(byte[] archivoCargado) {
        this.archivoCargado = archivoCargado;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public Collection<ListaRestriccion> getListaRestriccionCollection() {
        return listaRestriccionCollection;
    }

    public void setListaRestriccionCollection(Collection<ListaRestriccion> listaRestriccionCollection) {
        this.listaRestriccionCollection = listaRestriccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArchivoFuente != null ? idArchivoFuente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArchivoFuente)) {
            return false;
        }
        ArchivoFuente other = (ArchivoFuente) object;
        if ((this.idArchivoFuente == null && other.idArchivoFuente != null) || (this.idArchivoFuente != null && !this.idArchivoFuente.equals(other.idArchivoFuente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.amlrisktomcat.ArchivoFuente[ idArchivoFuente=" + idArchivoFuente + " ]";
    }
    
}
