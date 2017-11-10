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
import javax.persistence.Entity;
import javax.persistence.Id;
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
 * @author karol
 */
@Entity
@Table(name = "tb_cruce_cliente_lista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CruceClienteLista.findAll", query = "SELECT c FROM CruceClienteLista c")
    , @NamedQuery(name = "CruceClienteLista.findByIdRegistro", query = "SELECT c FROM CruceClienteLista c WHERE c.idRegistro = :idRegistro")
    , @NamedQuery(name = "CruceClienteLista.findByIdCliente", query = "SELECT c FROM CruceClienteLista c WHERE c.idCliente = :idCliente")
    , @NamedQuery(name = "CruceClienteLista.findByIdArchivoCliMasivo", query = "SELECT c FROM CruceClienteLista c WHERE c.idArchivoCliMasivo = :idArchivoCliMasivo")
    , @NamedQuery(name = "CruceClienteLista.findByIdArchivoFuente", query = "SELECT c FROM CruceClienteLista c WHERE c.idArchivoFuente = :idArchivoFuente")
    , @NamedQuery(name = "CruceClienteLista.findByTipoIdCliente", query = "SELECT c FROM CruceClienteLista c WHERE c.tipoIdCliente = :tipoIdCliente")
    , @NamedQuery(name = "CruceClienteLista.findByClientePrimerNombre", query = "SELECT c FROM CruceClienteLista c WHERE c.clientePrimerNombre = :clientePrimerNombre")
    , @NamedQuery(name = "CruceClienteLista.findByClienteUltimoNombre", query = "SELECT c FROM CruceClienteLista c WHERE c.clienteUltimoNombre = :clienteUltimoNombre")
    , @NamedQuery(name = "CruceClienteLista.findByListaPrimerNombre", query = "SELECT c FROM CruceClienteLista c WHERE c.listaPrimerNombre = :listaPrimerNombre")
    , @NamedQuery(name = "CruceClienteLista.findByListaUltimoNombre", query = "SELECT c FROM CruceClienteLista c WHERE c.listaUltimoNombre = :listaUltimoNombre")
    , @NamedQuery(name = "CruceClienteLista.findByListaFechaReporte", query = "SELECT c FROM CruceClienteLista c WHERE c.listaFechaReporte = :listaFechaReporte")
    , @NamedQuery(name = "CruceClienteLista.findByListaObservacion", query = "SELECT c FROM CruceClienteLista c WHERE c.listaObservacion = :listaObservacion")
    , @NamedQuery(name = "CruceClienteLista.findByNombreFuente", query = "SELECT c FROM CruceClienteLista c WHERE c.nombreFuente = :nombreFuente")
    , @NamedQuery(name = "CruceClienteLista.findByPorcentaje", query = "SELECT c FROM CruceClienteLista c WHERE c.porcentaje = :porcentaje")})
public class CruceClienteLista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_registro")
    private Integer idRegistro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "id_cliente")
    private String idCliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_archivo_cli_masivo")
    private int idArchivoCliMasivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_archivo_fuente")
    private int idArchivoFuente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "tipo_id_cliente")
    private String tipoIdCliente;
    @Size(max = 100)
    @Column(name = "cliente_primer_nombre")
    private String clientePrimerNombre;
    @Size(max = 100)
    @Column(name = "cliente_ultimo_nombre")
    private String clienteUltimoNombre;
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
    @Size(max = 15)
    @Column(name = "nombre_fuente")
    private String nombreFuente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "porcentaje")
    private int porcentaje;

    public CruceClienteLista() {
    }

    public CruceClienteLista(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public CruceClienteLista(Integer idRegistro, String idCliente, int idArchivoCliMasivo, int idArchivoFuente, String tipoIdCliente, int porcentaje) {
        this.idRegistro = idRegistro;
        this.idCliente = idCliente;
        this.idArchivoCliMasivo = idArchivoCliMasivo;
        this.idArchivoFuente = idArchivoFuente;
        this.tipoIdCliente = tipoIdCliente;
        this.porcentaje = porcentaje;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
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

    public int getIdArchivoFuente() {
        return idArchivoFuente;
    }

    public void setIdArchivoFuente(int idArchivoFuente) {
        this.idArchivoFuente = idArchivoFuente;
    }

    public String getTipoIdCliente() {
        return tipoIdCliente;
    }

    public void setTipoIdCliente(String tipoIdCliente) {
        this.tipoIdCliente = tipoIdCliente;
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

    public String getNombreFuente() {
        return nombreFuente;
    }

    public void setNombreFuente(String nombreFuente) {
        this.nombreFuente = nombreFuente;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistro != null ? idRegistro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CruceClienteLista)) {
            return false;
        }
        CruceClienteLista other = (CruceClienteLista) object;
        if ((this.idRegistro == null && other.idRegistro != null) || (this.idRegistro != null && !this.idRegistro.equals(other.idRegistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.model.CruceClienteLista[ idRegistro=" + idRegistro + " ]";
    }
    
}
