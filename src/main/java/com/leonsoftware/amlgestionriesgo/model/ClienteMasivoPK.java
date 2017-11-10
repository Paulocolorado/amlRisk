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
public class ClienteMasivoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "tipo_id_cliente")
    private String tipoIdCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "id_cliente")
    private String idCliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_archivo_cli_masivo")
    private int idArchivoCliMasivo;

    public ClienteMasivoPK() {
    }

    public ClienteMasivoPK(String tipoIdCliente, String idCliente, int idArchivoCliMasivo) {
        this.tipoIdCliente = tipoIdCliente;
        this.idCliente = idCliente;
        this.idArchivoCliMasivo = idArchivoCliMasivo;
    }

    public String getTipoIdCliente() {
        return tipoIdCliente;
    }

    public void setTipoIdCliente(String tipoIdCliente) {
        this.tipoIdCliente = tipoIdCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdArchivoCliMasivo() {
        return idArchivoCliMasivo;
    }

    public void setIdArchivoCliMasivo(int idArchivoCliMasivo) {
        this.idArchivoCliMasivo = idArchivoCliMasivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoIdCliente != null ? tipoIdCliente.hashCode() : 0);
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        hash += (int) idArchivoCliMasivo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteMasivoPK)) {
            return false;
        }
        ClienteMasivoPK other = (ClienteMasivoPK) object;
        if ((this.tipoIdCliente == null && other.tipoIdCliente != null) || (this.tipoIdCliente != null && !this.tipoIdCliente.equals(other.tipoIdCliente))) {
            return false;
        }
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        if (this.idArchivoCliMasivo != other.idArchivoCliMasivo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.model.ClienteMasivoPK[ tipoIdCliente=" + tipoIdCliente + ", idCliente=" + idCliente + ", idArchivoCliMasivo=" + idArchivoCliMasivo + " ]";
    }
    
}
