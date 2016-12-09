package uwi.dcit.agriexpensesvr;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

/**
 * Created by Keisha Chickoree on 04/12/2016.
 */
/**
 * This interface is used to implement the generic DAO pattern, that will allow segragation of concerns
 * amongst the endpoints used throughout the application. It works for a generic class and a generic primary key
 * as primary keys can differ based on object: String, Int, Key, etc.
 * */
 interface GenericDao<T, PK extends Serializable> {

   T persist(T t);

    void delete(T id);

    List<T> getAll();

    T update(T t);

    T findById(PK id);

    T find(String className, PK k);

    String getEntityClassName();

    Class getEntityClass();

    Class getPKClass();

    Query createQuery(String s);
}
