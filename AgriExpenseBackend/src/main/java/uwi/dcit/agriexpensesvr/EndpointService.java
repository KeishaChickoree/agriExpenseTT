package uwi.dcit.agriexpensesvr;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Keisha Chickoree on 04/12/2016.
 */

 interface EndpointService<T, PK extends Serializable> {

    T persist(T t);

    void delete(T id);

    List<T> getAll();

    T update(T t);

    T findById(PK id);

}
