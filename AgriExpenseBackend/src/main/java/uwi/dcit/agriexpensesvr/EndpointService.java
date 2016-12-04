package uwi.dcit.agriexpensesvr;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Matthew on 04/12/2016.
 */

 interface EndpointService<T, PK extends Serializable> {

    T create(T t);

    void delete(T id);

    List<T> fetchAll();

    T fetchByName(String name);

    T update(T t);

    T read(PK id);

}
