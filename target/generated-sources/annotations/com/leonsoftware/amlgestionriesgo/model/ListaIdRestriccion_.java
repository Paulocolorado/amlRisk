package com.leonsoftware.amlgestionriesgo.model;

import com.leonsoftware.amlgestionriesgo.model.ListaIdRestriccionPK;
import com.leonsoftware.amlgestionriesgo.model.ListaRestriccion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-23T12:31:23")
@StaticMetamodel(ListaIdRestriccion.class)
public class ListaIdRestriccion_ { 

    public static volatile SingularAttribute<ListaIdRestriccion, String> tipoId;
    public static volatile SingularAttribute<ListaIdRestriccion, String> paisId;
    public static volatile SingularAttribute<ListaIdRestriccion, Date> fechaModificacion;
    public static volatile SingularAttribute<ListaIdRestriccion, ListaRestriccion> listaRestriccion;
    public static volatile SingularAttribute<ListaIdRestriccion, ListaIdRestriccionPK> listaIdRestriccionPK;
    public static volatile SingularAttribute<ListaIdRestriccion, String> numeroId;
    public static volatile SingularAttribute<ListaIdRestriccion, Date> fechaCreacion;
    public static volatile SingularAttribute<ListaIdRestriccion, String> usuarioModificacion;
    public static volatile SingularAttribute<ListaIdRestriccion, String> usuarioCreacion;

}