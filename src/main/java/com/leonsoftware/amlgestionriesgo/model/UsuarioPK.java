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
public class UsuarioPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "tipo_id_usuario")
    private String tipoIdUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_usuario")
    private int idUsuario;

    public UsuarioPK() {
    }

    public UsuarioPK(String tipoIdUsuario, int idUsuario) {
        this.tipoIdUsuario = tipoIdUsuario;
        this.idUsuario = idUsuario;
    }

    public String getTipoIdUsuario() {
        return tipoIdUsuario;
    }

    public void setTipoIdUsuario(String tipoIdUsuario) {
        this.tipoIdUsuario = tipoIdUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoIdUsuario != null ? tipoIdUsuario.hashCode() : 0);
        hash += (int) idUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioPK)) {
            return false;
        }
        UsuarioPK other = (UsuarioPK) object;
        if ((this.tipoIdUsuario == null && other.tipoIdUsuario != null) || (this.tipoIdUsuario != null && !this.tipoIdUsuario.equals(other.tipoIdUsuario))) {
            return false;
        }
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.amlrisktomcat.UsuarioPK[ tipoIdUsuario=" + tipoIdUsuario + ", idUsuario=" + idUsuario + " ]";
    }
    
}
