package com.leonsoftware.amlgestionriesgo.model;

import com.leonsoftware.amlgestionriesgo.model.ListaCatalogo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-03T16:24:35")
@StaticMetamodel(Catalogo.class)
public class Catalogo_ { 

    public static volatile SingularAttribute<Catalogo, String> idCatalogo;
    public static volatile SingularAttribute<Catalogo, Date> fechaModificacion;
    public static volatile CollectionAttribute<Catalogo, ListaCatalogo> listaCatalogoCollection;
    public static volatile SingularAttribute<Catalogo, Date> fechaCreacion;
    public static volatile SingularAttribute<Catalogo, String> usuarioModificacion;
    public static volatile SingularAttribute<Catalogo, String> usuarioCreacion;

}