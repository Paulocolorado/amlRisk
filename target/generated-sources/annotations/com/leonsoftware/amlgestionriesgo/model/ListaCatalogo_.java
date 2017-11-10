package com.leonsoftware.amlgestionriesgo.model;

import com.leonsoftware.amlgestionriesgo.model.Catalogo;
import com.leonsoftware.amlgestionriesgo.model.ListaCatalogoPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-10T01:39:51")
@StaticMetamodel(ListaCatalogo.class)
public class ListaCatalogo_ { 

    public static volatile SingularAttribute<ListaCatalogo, Date> fechaModificacion;
    public static volatile SingularAttribute<ListaCatalogo, Date> fechaCreacion;
    public static volatile SingularAttribute<ListaCatalogo, ListaCatalogoPK> listaCatalogoPK;
    public static volatile SingularAttribute<ListaCatalogo, String> usuarioModificacion;
    public static volatile SingularAttribute<ListaCatalogo, Catalogo> catalogo;
    public static volatile SingularAttribute<ListaCatalogo, String> usuarioCreacion;

}