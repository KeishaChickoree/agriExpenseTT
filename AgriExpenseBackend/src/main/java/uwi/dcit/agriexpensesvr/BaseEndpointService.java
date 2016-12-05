package uwi.dcit.agriexpensesvr;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import java.lang.reflect.*;
import java.lang.*;
import java.util.List;


/**
 * Created by Keisha Chickoree on 04/12/2016.
 */

/*
* Description: This service uses generics to DRY the solution for the purpose of use in various API endpoints.
* T - denotes the generic class, this can be Cycle, CycleUse, TransLog, ResourcePurchase etc.
* This class makes the code highly extensible, as the code is quite reusable, it also allows testability as the class
* will be injected into endpoints, resulting in inversion of control and dependency injection.
* */

@SuppressWarnings("unchecked")
public class BaseEndpointService<T, PK extends Serializable> implements EndpointService<T, PK> {

    protected Class<T> entityClass;

    //Singleton entity manager
    @PersistenceContext(type= PersistenceContextType.TRANSACTION)
    private static EntityManager getEntityManager() {
        return EMF.getManagerInstance();
    }

    public BaseEndpointService(Class<T> entityClass){
        this.entityClass = entityClass;
    }

    //Create a object and persist to the database
    @Override
    public T persist(T t) {
        getEntityManager().persist(t);
        return t;
    }

    @Override
    public void delete(T t) {
        t = this.getEntityManager().merge(t);
        this.getEntityManager().remove(t);
    }

    public void delete(PK id){
        //        this.getEntityManager().remove(
//                this.getEntityManager().getReference(getEntityType(), id));
    }

    @Override
    public List<T> getAll() {
        System.out.print("GetAll");
        return this.getEntityManager().createQuery("Select t from " + this.entityClass.getSimpleName() + " t").getResultList();
    }

    @Override
    public T update(T t) {
        return this.getEntityManager().merge(t);
    }

    @Override
    public T findById(PK id) {
        System.out.print("findByID");
        return this.getEntityManager().find(entityClass, id);
    }

}
