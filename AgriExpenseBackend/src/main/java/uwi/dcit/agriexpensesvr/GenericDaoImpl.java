package uwi.dcit.agriexpensesvr;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

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
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    protected Class<T> entityClass;

    //Singleton entity manager
    @PersistenceContext(type= PersistenceContextType.TRANSACTION)
    private static EntityManager getEntityManager() {
        return EMF.getManagerInstance();
    }

    public GenericDaoImpl(Class<T> entityClass){
        this.entityClass = entityClass;
    }

    //Create a object and persist to the database
    @Override
    public T persist(T t) {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(t);
            getEntityManager().getTransaction().commit();
            return t;
        }catch (Exception e){
            getEntityManager().getTransaction().rollback();
        }
        return null;
    }

    @Override
    public void delete(T t) {
        try {
            getEntityManager().getTransaction().begin();
            this.getEntityManager().remove(t);
            getEntityManager().getTransaction().commit();
        }catch (Exception e){
            getEntityManager().getTransaction().rollback();
        }
    }

    public void delete(PK id){
        //        this.getEntityManager().remove(
//                this.getEntityManager().getReference(getEntityType(), id));
    }

    @Override
    public List<T> getAll() {
        return this.getEntityManager().createQuery("Select t from " + this.entityClass.getSimpleName() + " t").getResultList();
    }

    @Override
    public T update(T t) {
        try{
            getEntityManager().getTransaction().begin();
            this.getEntityManager().merge(t);
            getEntityManager().getTransaction().commit();
            return t;
        }catch (Exception e){
            getEntityManager().getTransaction().rollback();
        }
        return null;
    }

    @Override
    public T findById(PK id) {
        return this.getEntityManager().find(entityClass, id);
    }

}
