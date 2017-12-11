package com.leonsoftware.amlgestionriesgo.model;

import com.leonsoftware.amlgestionriesgo.model.ArchivoFuente;
import com.leonsoftware.amlgestionriesgo.model.ListaIdRestriccion;
import com.leonsoftware.amlgestionriesgo.model.ListaRestriccionPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-11T02:48:35")
@StaticMetamodel(ListaRestriccion.class)
public class ListaRestriccion_ { 

    public static volatile SingularAttribute<ListaRestriccion, Date> fechaModificacion;
    public static volatile SingularAttribute<ListaRestriccion, String> listaPrimerNombre;
    public static volatile SingularAttribute<ListaRestriccion, Date> fechaCreacion;
    public static volatile SingularAttribute<ListaRestriccion, Date> listaFechaReporte;
    public static volatile SingularAttribute<ListaRestriccion, String> listaUltimoNombre;
    public static volatile SingularAttribute<ListaRestriccion, String> usuarioModificacion;
    public static volatile SingularAttribute<ListaRestriccion, ListaRestriccionPK> listaRestriccionPK;
    public static volatile CollectionAttribute<ListaRestriccion, ListaIdRestriccion> listaIdRestriccionCollection;
    public static volatile SingularAttribute<ListaRestriccion, ArchivoFuente> archivoFuente;
    public static volatile SingularAttribute<ListaRestriccion, String> usuarioCreacion;
    public static volatile SingularAttribute<ListaRestriccion, String> listaObservacion;

}