package com.leonsoftware.amlgestionriesgo.model;

import com.leonsoftware.amlgestionriesgo.model.ArchivoClienteMasivo;
import com.leonsoftware.amlgestionriesgo.model.ClienteMasivoPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-10T10:12:05")
@StaticMetamodel(ClienteMasivo.class)
public class ClienteMasivo_ { 

    public static volatile SingularAttribute<ClienteMasivo, String> clientePrimerNombre;
    public static volatile SingularAttribute<ClienteMasivo, String> fechaModificacion;
    public static volatile SingularAttribute<ClienteMasivo, ClienteMasivoPK> clienteMasivoPK;
    public static volatile SingularAttribute<ClienteMasivo, Date> fechaCreacion;
    public static volatile SingularAttribute<ClienteMasivo, String> usuarioModificacion;
    public static volatile SingularAttribute<ClienteMasivo, ArchivoClienteMasivo> archivoClienteMasivo;
    public static volatile SingularAttribute<ClienteMasivo, String> clienteUltimoNombre;
    public static volatile SingularAttribute<ClienteMasivo, String> usuarioCreacion;

}