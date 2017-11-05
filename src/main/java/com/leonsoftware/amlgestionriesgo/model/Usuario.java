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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "tb_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByTipoIdUsuario", query = "SELECT u FROM Usuario u WHERE u.usuarioPK.tipoIdUsuario = :tipoIdUsuario")
    , @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.usuarioPK.idUsuario = :idUsuario")
    , @NamedQuery(name = "Usuario.findByNombreUsuario", query = "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    , @NamedQuery(name = "Usuario.findByApellidoUsuario", query = "SELECT u FROM Usuario u WHERE u.apellidoUsuario = :apellidoUsuario")
    , @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario")
    , @NamedQuery(name = "Usuario.findByClaveUsuario", query = "SELECT u FROM Usuario u WHERE u.claveUsuario = :claveUsuario")
    , @NamedQuery(name = "Usuario.findByCorreoUsuario", query = "SELECT u FROM Usuario u WHERE u.correoUsuario = :correoUsuario")
    , @NamedQuery(name = "Usuario.findByFechaCreacion", query = "SELECT u FROM Usuario u WHERE u.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Usuario.findByUsuarioCreacion", query = "SELECT u FROM Usuario u WHERE u.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "Usuario.findByFechaModificacion", query = "SELECT u FROM Usuario u WHERE u.fechaModificacion = :fechaModificacion")
    , @NamedQuery(name = "Usuario.findByUsuarioModificacion", query = "SELECT u FROM Usuario u WHERE u.usuarioModificacion = :usuarioModificacion")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuarioPK usuarioPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "apellido_usuario")
    private String apellidoUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "clave_usuario")
    private String claveUsuario;
    @Size(max = 45)
    @Column(name = "correo_usuario")
    private String correoUsuario;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<ArchivoClienteMasivo> archivoClienteMasivoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<ArchivoFuente> archivoFuenteCollection;

    public Usuario() {
    }

    public Usuario(UsuarioPK usuarioPK) {
        this.usuarioPK = usuarioPK;
    }

    public Usuario(UsuarioPK usuarioPK, String nombreUsuario, String apellidoUsuario, String usuario, String claveUsuario, Date fechaCreacion, String usuarioCreacion, Date fechaModificacion, String usuarioModificacion) {
        this.usuarioPK = usuarioPK;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.usuario = usuario;
        this.claveUsuario = claveUsuario;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
    }

    public Usuario(String tipoIdUsuario, int idUsuario) {
        this.usuarioPK = new UsuarioPK(tipoIdUsuario, idUsuario);
    }

    public UsuarioPK getUsuarioPK() {
        return usuarioPK;
    }

    public void setUsuarioPK(UsuarioPK usuarioPK) {
        this.usuarioPK = usuarioPK;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
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
    public Collection<ArchivoClienteMasivo> getArchivoClienteMasivoCollection() {
        return archivoClienteMasivoCollection;
    }

    public void setArchivoClienteMasivoCollection(Collection<ArchivoClienteMasivo> archivoClienteMasivoCollection) {
        this.archivoClienteMasivoCollection = archivoClienteMasivoCollection;
    }

    @XmlTransient
    public Collection<ArchivoFuente> getArchivoFuenteCollection() {
        return archivoFuenteCollection;
    }

    public void setArchivoFuenteCollection(Collection<ArchivoFuente> archivoFuenteCollection) {
        this.archivoFuenteCollection = archivoFuenteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioPK != null ? usuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuarioPK == null && other.usuarioPK != null) || (this.usuarioPK != null && !this.usuarioPK.equals(other.usuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.amlrisktomcat.Usuario[ usuarioPK=" + usuarioPK + " ]";
    }
    
}
