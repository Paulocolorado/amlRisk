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
@Table(name = "tb_archivo_cliente_masivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArchivoClienteMasivo.findAll", query = "SELECT a FROM ArchivoClienteMasivo a")
    , @NamedQuery(name = "ArchivoClienteMasivo.findByIdArchivoCliMasivo", query = "SELECT a FROM ArchivoClienteMasivo a WHERE a.idArchivoCliMasivo = :idArchivoCliMasivo")
    , @NamedQuery(name = "ArchivoClienteMasivo.findByNombreArchivoCliMasivo", query = "SELECT a FROM ArchivoClienteMasivo a WHERE a.nombreArchivoCliMasivo = :nombreArchivoCliMasivo")
    , @NamedQuery(name = "ArchivoClienteMasivo.findByFechaCarga", query = "SELECT a FROM ArchivoClienteMasivo a WHERE a.fechaCarga = :fechaCarga")
    , @NamedQuery(name = "ArchivoClienteMasivo.findByFechaCreacion", query = "SELECT a FROM ArchivoClienteMasivo a WHERE a.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "ArchivoClienteMasivo.findByUsuarioCreacion", query = "SELECT a FROM ArchivoClienteMasivo a WHERE a.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "ArchivoClienteMasivo.findByFechaModificacion", query = "SELECT a FROM ArchivoClienteMasivo a WHERE a.fechaModificacion = :fechaModificacion")
    , @NamedQuery(name = "ArchivoClienteMasivo.findByUsuarioModificacion", query = "SELECT a FROM ArchivoClienteMasivo a WHERE a.usuarioModificacion = :usuarioModificacion")})
public class ArchivoClienteMasivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_archivo_cli_masivo")
    private Integer idArchivoCliMasivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_archivo_cli_masivo")
    private String nombreArchivoCliMasivo;
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
    @OneToMany(mappedBy = "idArchivoCliMasivo")
    private Collection<ClienteMasivo> clienteMasivoCollection;

    public ArchivoClienteMasivo() {
    }

    public ArchivoClienteMasivo(Integer idArchivoCliMasivo) {
        this.idArchivoCliMasivo = idArchivoCliMasivo;
    }

    public ArchivoClienteMasivo(Integer idArchivoCliMasivo, String nombreArchivoCliMasivo, byte[] archivoCargado) {
        this.idArchivoCliMasivo = idArchivoCliMasivo;
        this.nombreArchivoCliMasivo = nombreArchivoCliMasivo;
        this.archivoCargado = archivoCargado;
    }

    public Integer getIdArchivoCliMasivo() {
        return idArchivoCliMasivo;
    }

    public void setIdArchivoCliMasivo(Integer idArchivoCliMasivo) {
        this.idArchivoCliMasivo = idArchivoCliMasivo;
    }

    public String getNombreArchivoCliMasivo() {
        return nombreArchivoCliMasivo;
    }

    public void setNombreArchivoCliMasivo(String nombreArchivoCliMasivo) {
        this.nombreArchivoCliMasivo = nombreArchivoCliMasivo;
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
    public Collection<ClienteMasivo> getClienteMasivoCollection() {
        return clienteMasivoCollection;
    }

    public void setClienteMasivoCollection(Collection<ClienteMasivo> clienteMasivoCollection) {
        this.clienteMasivoCollection = clienteMasivoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArchivoCliMasivo != null ? idArchivoCliMasivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArchivoClienteMasivo)) {
            return false;
        }
        ArchivoClienteMasivo other = (ArchivoClienteMasivo) object;
        if ((this.idArchivoCliMasivo == null && other.idArchivoCliMasivo != null) || (this.idArchivoCliMasivo != null && !this.idArchivoCliMasivo.equals(other.idArchivoCliMasivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.amlrisktomcat.ArchivoClienteMasivo[ idArchivoCliMasivo=" + idArchivoCliMasivo + " ]";
    }
    
}
