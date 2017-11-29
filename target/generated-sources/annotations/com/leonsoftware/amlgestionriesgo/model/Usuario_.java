package com.leonsoftware.amlgestionriesgo.model;

import com.leonsoftware.amlgestionriesgo.model.ArchivoClienteMasivo;
import com.leonsoftware.amlgestionriesgo.model.ArchivoFuente;
import com.leonsoftware.amlgestionriesgo.model.UsuarioPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-28T12:54:16")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile CollectionAttribute<Usuario, ArchivoClienteMasivo> archivoClienteMasivoCollection;
    public static volatile SingularAttribute<Usuario, Date> fechaModificacion;
    public static volatile SingularAttribute<Usuario, String> apellidoUsuario;
    public static volatile SingularAttribute<Usuario, String> correoUsuario;
    public static volatile SingularAttribute<Usuario, String> claveUsuario;
    public static volatile SingularAttribute<Usuario, Date> fechaCreacion;
    public static volatile SingularAttribute<Usuario, String> usuario;
    public static volatile SingularAttribute<Usuario, String> usuarioModificacion;
    public static volatile SingularAttribute<Usuario, String> nombreUsuario;
    public static volatile SingularAttribute<Usuario, UsuarioPK> usuarioPK;
    public static volatile CollectionAttribute<Usuario, ArchivoFuente> archivoFuenteCollection;
    public static volatile SingularAttribute<Usuario, String> usuarioCreacion;

}