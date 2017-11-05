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
@Table(name = "tb_cliente_masivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClienteMasivo.findAll", query = "SELECT c FROM ClienteMasivo c")
    , @NamedQuery(name = "ClienteMasivo.findByTipoIdCliente", query = "SELECT c FROM ClienteMasivo c WHERE c.clienteMasivoPK.tipoIdCliente = :tipoIdCliente")
    , @NamedQuery(name = "ClienteMasivo.findByIdCliente", query = "SELECT c FROM ClienteMasivo c WHERE c.clienteMasivoPK.idCliente = :idCliente")
    , @NamedQuery(name = "ClienteMasivo.findByClientePrimerNombre", query = "SELECT c FROM ClienteMasivo c WHERE c.clientePrimerNombre = :clientePrimerNombre")
    , @NamedQuery(name = "ClienteMasivo.findByClienteUltimoNombre", query = "SELECT c FROM ClienteMasivo c WHERE c.clienteUltimoNombre = :clienteUltimoNombre")
    , @NamedQuery(name = "ClienteMasivo.findByFechaCreacion", query = "SELECT c FROM ClienteMasivo c WHERE c.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "ClienteMasivo.findByUsuarioCreacion", query = "SELECT c FROM ClienteMasivo c WHERE c.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "ClienteMasivo.findByFechaModificacion", query = "SELECT c FROM ClienteMasivo c WHERE c.fechaModificacion = :fechaModificacion")
    , @NamedQuery(name = "ClienteMasivo.findByUsuarioModificacion", query = "SELECT c FROM ClienteMasivo c WHERE c.usuarioModificacion = :usuarioModificacion")})
public class ClienteMasivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClienteMasivoPK clienteMasivoPK;
    @Size(max = 100)
    @Column(name = "cliente_primer_nombre")
    private String clientePrimerNombre;
    @Size(max = 100)
    @Column(name = "cliente_ultimo_nombre")
    private String clienteUltimoNombre;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 45)
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Size(max = 45)
    @Column(name = "fecha_modificacion")
    private String fechaModificacion;
    @Size(max = 45)
    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;
    @JoinColumn(name = "id_archivo_cli_masivo", referencedColumnName = "id_archivo_cli_masivo")
    @ManyToOne
    private ArchivoClienteMasivo idArchivoCliMasivo;

    public ClienteMasivo() {
    }

    public ClienteMasivo(ClienteMasivoPK clienteMasivoPK) {
        this.clienteMasivoPK = clienteMasivoPK;
    }

    public ClienteMasivo(String tipoIdCliente, String idCliente) {
        this.clienteMasivoPK = new ClienteMasivoPK(tipoIdCliente, idCliente);
    }

    public ClienteMasivoPK getClienteMasivoPK() {
        return clienteMasivoPK;
    }

    public void setClienteMasivoPK(ClienteMasivoPK clienteMasivoPK) {
        this.clienteMasivoPK = clienteMasivoPK;
    }

    public String getClientePrimerNombre() {
        return clientePrimerNombre;
    }

    public void setClientePrimerNombre(String clientePrimerNombre) {
        this.clientePrimerNombre = clientePrimerNombre;
    }

    public String getClienteUltimoNombre() {
        return clienteUltimoNombre;
    }

    public void setClienteUltimoNombre(String clienteUltimoNombre) {
        this.clienteUltimoNombre = clienteUltimoNombre;
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

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public ArchivoClienteMasivo getIdArchivoCliMasivo() {
        return idArchivoCliMasivo;
    }

    public void setIdArchivoCliMasivo(ArchivoClienteMasivo idArchivoCliMasivo) {
        this.idArchivoCliMasivo = idArchivoCliMasivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteMasivoPK != null ? clienteMasivoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteMasivo)) {
            return false;
        }
        ClienteMasivo other = (ClienteMasivo) object;
        if ((this.clienteMasivoPK == null && other.clienteMasivoPK != null) || (this.clienteMasivoPK != null && !this.clienteMasivoPK.equals(other.clienteMasivoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.amlrisktomcat.ClienteMasivo[ clienteMasivoPK=" + clienteMasivoPK + " ]";
    }
    
}
