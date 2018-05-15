package com.leonsoftware.amlgestionriesgo.model;

import com.leonsoftware.amlgestionriesgo.model.Menu;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-15T14:14:40")
@StaticMetamodel(Menu.class)
public class Menu_ { 

    public static volatile SingularAttribute<Menu, Menu> codigoSubmenu;
    public static volatile SingularAttribute<Menu, String> nombreMenu;
    public static volatile SingularAttribute<Menu, Short> codigo;
    public static volatile SingularAttribute<Menu, String> estado;
    public static volatile SingularAttribute<Menu, String> tipoMenu;
    public static volatile SingularAttribute<Menu, String> urlMenu;
    public static volatile SingularAttribute<Menu, String> tipoUsuario;
    public static volatile CollectionAttribute<Menu, Menu> menuCollection;

}