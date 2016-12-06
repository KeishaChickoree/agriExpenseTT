package uwi.dcit.agriexpensesvr;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import java.io.Serializable;

/**
 * Created by Matthew on 05/12/2016.
 */

public abstract class BaseEndpoint<T, PK extends Serializable> {

    //dependency injection the interface for services common to all endpoints.
    protected GenericDao<Cycle, Key> service;

    protected T GetByKey(String keyrep){
        Key key =  KeyFactory.stringToKey(keyrep);
        return (T) service.findById(key);
    }

}
