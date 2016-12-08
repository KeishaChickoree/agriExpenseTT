package uwi.dcit.agriexpensesvr;

import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.repackaged.com.google.api.client.http.HttpResponseException;
import com.google.appengine.repackaged.com.google.api.client.http.HttpStatusCodes;
import com.google.appengine.repackaged.com.google.api.services.datastore.DatastoreV1;
import com.google.cloud.sql.jdbc.internal.Exceptions;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;


/**
 * Created by Matthew on 05/12/2016.
 */

public abstract class BaseEndpoint<T, PK extends Serializable> {

    //dependency injection the interface for services common to all endpoints.
    protected GenericDao<T, Key> service;
    private String className;

    protected T GetByKey(String keyrep){
        return (T) service.findById(this.getKey(keyrep));
    }

    protected T Get(int id) throws InternalServerErrorException {
        Key k = this.createKey(id);
        if(k==null)throw new InternalServerErrorException("There was an error processing this request.");
        return this.service.find(service.getEntityClassName(),k);
    }

    protected T Get(long id) throws InternalServerErrorException {
        Key k = this.createKey(id);
        if (k != null) {
            return this.service.find(service.getEntityClassName(),k);
        }
        else throw new InternalServerErrorException("There was an error processing this request.");
    }

    protected List<T> getAll() {
        try {
            return service.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void deleteAll(){

    }

    protected void remove(int id){
        Key key = this.createKey(id);
        T item = this.service.findById(key);
        try {
            if(item!=null) {
                this.service.delete(item);
            }
            else {
                throw new EntityNotFoundException("Object does not exist");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean contains(T t, PK k){
        try {
            T item = service.find(service.getEntityClassName(), k);
            if (item != null) {return true;}
            else {return false;}
        } catch (Exception e) {return false;}
    }

    protected T create(T t){

    }

    protected T insert(T t, PK k) throws InternalServerErrorException, EntityExistsException {
        try {
            if (this.contains(t,k)) {
                throw new EntityExistsException("Object already exists");
            }
            else {
               t = this.service.persist(t);
                return t;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw new InternalServerErrorException("There was an error processing this request.");
        }
    }

    protected T update(T t, PK k) {
        if (t == null) return null;
        T foundItem = this.service.find(service.getEntityClassName(), k);
        try {
            if (foundItem != null) {
                throw new EntityNotFoundException("Object does not exist");
            } else {
                this.service.update(t);
            }
        } catch (Exception e) {
            return null;
        }
        return t;
    }
    /*
    * */
    protected Key createKey(int id){
        return KeyFactory.createKey(service.getEntityClassName(), id);
    }

    protected Key createKey(Long id){
        return KeyFactory.createKey(service.getEntityClassName(),id);
    }

    protected Key getKey(String keyrep){
        return KeyFactory.stringToKey(keyrep);
    }

}
