/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonsoftware.amlgestionriesgo.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "tb_menu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")
    , @NamedQuery(name = "Menu.findByCodigo", query = "SELECT m FROM Menu m WHERE m.codigo = :codigo")
    , @NamedQuery(name = "Menu.findByNombreMenu", query = "SELECT m FROM Menu m WHERE m.nombreMenu = :nombreMenu")
    , @NamedQuery(name = "Menu.findByUrlMenu", query = "SELECT m FROM Menu m WHERE m.urlMenu = :urlMenu")
    , @NamedQuery(name = "Menu.findByTipoMenu", query = "SELECT m FROM Menu m WHERE m.tipoMenu = :tipoMenu")
    , @NamedQuery(name = "Menu.findByTipoUsuario", query = "SELECT m FROM Menu m WHERE m.tipoUsuario = :tipoUsuario")
    , @NamedQuery(name = "Menu.findByEstado", query = "SELECT m FROM Menu m WHERE m.estado = :estado")})
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo")
    private Short codigo;
    @Size(max = 50)
    @Column(name = "nombre_menu")
    private String nombreMenu;
    @Size(max = 45)
    @Column(name = "url_menu")
    private String urlMenu;
    @Size(max = 5)
    @Column(name = "tipo_menu")
    private String tipoMenu;
    @Size(max = 5)
    @Column(name = "tipo_usuario")
    private String tipoUsuario;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "codigoSubmenu")
    private Collection<Menu> menuCollection;
    @JoinColumn(name = "codigo_submenu", referencedColumnName = "codigo")
    @ManyToOne
    private Menu codigoSubmenu;

    public Menu() {
    }

    public Menu(Short codigo) {
        this.codigo = codigo;
    }

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    public String getNombreMenu() {
        return nombreMenu;
    }

    public void setNombreMenu(String nombreMenu) {
        this.nombreMenu = nombreMenu;
    }

    public String getUrlMenu() {
        return urlMenu;
    }

    public void setUrlMenu(String urlMenu) {
        this.urlMenu = urlMenu;
    }

    public String getTipoMenu() {
        return tipoMenu;
    }

    public void setTipoMenu(String tipoMenu) {
        this.tipoMenu = tipoMenu;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Menu> getMenuCollection() {
        return menuCollection;
    }

    public void setMenuCollection(Collection<Menu> menuCollection) {
        this.menuCollection = menuCollection;
    }

    public Menu getCodigoSubmenu() {
        return codigoSubmenu;
    }

    public void setCodigoSubmenu(Menu codigoSubmenu) {
        this.codigoSubmenu = codigoSubmenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.leonsoftware.amlgestionriesgo.model.Menu[ codigo=" + codigo + " ]";
    }
    
}
