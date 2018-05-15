package com.leonsoftware.amlgestionriesgo.model;

import com.leonsoftware.amlgestionriesgo.model.ClienteMasivo;
import com.leonsoftware.amlgestionriesgo.model.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-15T14:14:39")
@StaticMetamodel(ArchivoClienteMasivo.class)
public class ArchivoClienteMasivo_ { 

    public static volatile SingularAttribute<ArchivoClienteMasivo, Date> fechaCarga;
    public static volatile CollectionAttribute<ArchivoClienteMasivo, ClienteMasivo> clienteMasivoCollection;
    public static volatile SingularAttribute<ArchivoClienteMasivo, Date> fechaModificacion;
    public static volatile SingularAttribute<ArchivoClienteMasivo, byte[]> archivoCargado;
    public static volatile SingularAttribute<ArchivoClienteMasivo, Integer> idArchivoCliMasivo;
    public static volatile SingularAttribute<ArchivoClienteMasivo, String> procesado;
    public static volatile SingularAttribute<ArchivoClienteMasivo, Date> fechaCreacion;
    public static volatile SingularAttribute<ArchivoClienteMasivo, String> usuarioModificacion;
    public static volatile SingularAttribute<ArchivoClienteMasivo, Usuario> usuario;
    public static volatile SingularAttribute<ArchivoClienteMasivo, String> nombreArchivoCliMasivo;
    public static volatile SingularAttribute<ArchivoClienteMasivo, String> usuarioCreacion;

}