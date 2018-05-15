package com.leonsoftware.amlgestionriesgo.model;

import com.leonsoftware.amlgestionriesgo.model.ListaRestriccion;
import com.leonsoftware.amlgestionriesgo.model.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-15T10:08:07")
@StaticMetamodel(ArchivoFuente.class)
public class ArchivoFuente_ { 

    public static volatile SingularAttribute<ArchivoFuente, Date> fechaCarga;
    public static volatile SingularAttribute<ArchivoFuente, Date> fechaModificacion;
    public static volatile SingularAttribute<ArchivoFuente, byte[]> archivoCargado;
    public static volatile CollectionAttribute<ArchivoFuente, ListaRestriccion> listaRestriccionCollection;
    public static volatile SingularAttribute<ArchivoFuente, Integer> idArchivoFuente;
    public static volatile SingularAttribute<ArchivoFuente, String> nombreFuente;
    public static volatile SingularAttribute<ArchivoFuente, Date> fechaCreacion;
    public static volatile SingularAttribute<ArchivoFuente, String> usuarioModificacion;
    public static volatile SingularAttribute<ArchivoFuente, Usuario> usuario;
    public static volatile SingularAttribute<ArchivoFuente, String> nombreArchivoFuente;
    public static volatile SingularAttribute<ArchivoFuente, String> nombreAccion;
    public static volatile SingularAttribute<ArchivoFuente, String> usuarioCreacion;

}