package uwi.dcit.agriexpensesvr;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;


/**
 * Created by Matthew on 05/12/2016.
 */

public abstract class BaseEndpoint<T, PK extends Serializable> {

    //dependency injection the interface for services common to all endpoints.
    protected GenericDao<T, Key> service;

    protected T GetByKey(String keyrep){
        Key key =  KeyFactory.stringToKey(keyrep);
        return (T) service.findById(key);
    }

    protected List<T> getAll() {
        try {
            return service.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void remove(String keyRep){
        try{
            T foundCycle = (T) GetByKey(keyRep);
            if(foundCycle!=null)service.delete(foundCycle);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
