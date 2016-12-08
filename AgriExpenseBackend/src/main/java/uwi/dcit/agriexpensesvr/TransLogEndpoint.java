package uwi.dcit.agriexpensesvr;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

@Api(name = "translogApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "agriexpensesvr.dcit.uwi",
                ownerName = "agriexpensesvr.dcit.uwi",
                packagePath = ""
        ))
public class TransLogEndpoint extends BaseEndpoint<TransLog,Key>{

    //for run time
    public TransLogEndpoint(){
        super.service = new GenericDaoImpl<TransLog,Key>(TransLog.class);
    }

    //for dependency injection
    public TransLogEndpoint(GenericDao service){
        super.service= service;
    }

    /**
     * This method lists all the entities inserted in datastore. It uses HTTP
     * GET method and paging support.
     *
     * @return A CollectionResponse class containing the list of all entities
     *         persisted and a cursor to the next page.
     */
    @SuppressWarnings({ "unchecked", "unused" })
    @ApiMethod(name = "listTransLog")
    public CollectionResponse<TransLog> listTransLog(
            @Nullable @Named("cursor") String cursorString,
            @Nullable @Named("limit") Integer limit) {

        EntityManager mgr = null;
        Cursor cursor = null;
        List<TransLog> execute = null;

        try {
            Query query = super.service.createQuery("select from TransLog as TransLog");
            if (cursorString != null && cursorString != "") {
                cursor = Cursor.fromWebSafeString(cursorString);
                query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
            }

            if (limit != null) {
                query.setFirstResult(0);
                query.setMaxResults(limit);
            }

            execute = (List<TransLog>) query.getResultList();
            cursor = JPACursorHelper.getCursor(execute);
            if (cursor != null)
                cursorString = cursor.toWebSafeString();
            for (TransLog obj : execute)
                System.out.println(obj.getAccount());
        } finally {
//            mgr.close();
        }

        return CollectionResponse.<TransLog> builder().setItems(execute)
                .setNextPageToken(cursorString).build();
    }

    @ApiMethod(name = "getAllTranslog")
    public List<TransLog> getAllTranslog(@Named("namespace") String namespace) {
        NamespaceManager.set(namespace);
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
        com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
                "TransLog");

        PreparedQuery pq = datastore.prepare(q);
        List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());
        Iterator<Entity> i = results.iterator();
        List<TransLog> tL = new ArrayList<>();
        while (i.hasNext()) {
            Entity e = i.next();
            TransLog t = new TransLog();

            t.setId(Integer.parseInt("" + e.getProperty("id")));
            t.setKeyrep((String) e.getProperty("keyrep"));
            t.setTableKind((String) e.getProperty("tableKind"));
            t.setRowId(Integer.parseInt("" + e.getProperty("rowId")));
            t.setOperation((String) e.getProperty("operation"));
            t.setTransTime((Long) e.getProperty("transTime"));
            tL.add(t);
        }
        return tL;
    }

    @ApiMethod(name = "deleteAll", httpMethod = HttpMethod.GET)
    public void deleteAll(@Named("namespace") String namespace) throws InternalServerErrorException {
        NamespaceManager.set(namespace);
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
        com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
                "TransLog");

        PreparedQuery pq = datastore.prepare(q);
        List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());
        Iterator<Entity> i = results.iterator();
        TransLog t;
        while (i.hasNext()) {
            long id = (Long) i.next().getProperty("id");
            t = super.Get(id);
            removeTransLog(t.getId(), namespace);
        }
    }

    /**
     * This method gets the entity having primary key id. It uses HTTP GET
     * method.
     *
     * //@param id
     *            the primary key of the java bean.
     * @return The entity with primary key id.
     */
    @ApiMethod(name = "Logs")
    public List<TransLog> Logs(@Named("time") Long time,
                               @Named("namespace") String namespace) {
        NamespaceManager.set(namespace);
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
        com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
                "TransLog");
        Filter timeFilter = new FilterPredicate("transTime",
                FilterOperator.GREATER_THAN_OR_EQUAL, time);
        q.setFilter(timeFilter);
        PreparedQuery pq = datastore.prepare(q);
        System.out.println("---------I LOVE LIFE 88------------");
        List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());
        Iterator<Entity> i = results.iterator();
        List<TransLog> tl = new ArrayList<TransLog>();
        while (i.hasNext()) {
            Entity e = i.next();
            TransLog tr = new TransLog();
            tr.setId(Integer.parseInt("" + e.getProperty("id")));
            tr.setKeyrep((String) e.getProperty("keyrep"));
            tr.setOperation((String) e.getProperty("operation"));
            tr.setTableKind((String) e.getProperty("tableKind"));
            tr.setRowId(Integer.parseInt("" + e.getProperty("rowId")));
            tr.setTransTime((Long) e.getProperty("transTime"));
            tl.add(tr);
        }
        return tl;
    }

    @ApiMethod(name = "getTransLog")
    public TransLog getTransLog(@Named ("NameSpace")String namespace, @Named("id") long id) throws InternalServerErrorException {
        NamespaceManager.set(namespace);
        return super.Get(id);
    }

    /**
     * This inserts a new entity into App Engine datastore. If the entity
     * already exists in the datastore, an exception is thrown. It uses HTTP
     * POST method.
     *
     * @param transLog
     *            the entity to be inserted.
     * @return The inserted entity.
     */
    @ApiMethod(name = "insertTransLog")
    public TransLog insertTransLog(TransLog transLog ) throws InternalServerErrorException {
        NamespaceManager.set(transLog.getAccount());
        Key k = super.createKey(transLog.getId());
        transLog.setKey(k);
        transLog.setKeyrep(KeyFactory.keyToString(k));
        transLog = super.insert(transLog,k);
        return transLog;
    }

    /**
     * This method is used for updating an existing entity. If the entity does
     * not exist in the datastore, an exception is thrown. It uses HTTP PUT
     * method.
     *
     * @param transLog
     *            the entity to be updated.
     * @return The updated entity.
     */
    @ApiMethod(name = "updateTransLog")
    public TransLog updateTransLog(TransLog transLog) throws InternalServerErrorException {
        NamespaceManager.set(transLog.getAccount());
        Key k = super.createKey(transLog.getId());
        transLog.setKey(k);
        transLog = super.update(transLog, k);
        if (transLog==null) {
            throw new InternalServerErrorException("There was an error processing this request.");
        }
        return transLog;
    }

    /**
     * This method removes the entity with primary key id. It uses HTTP DELETE
     * method.
     *
     * //@param id
     *            the primary key of the entity to be deleted.
     */
    @ApiMethod(name = "removeTransLog", httpMethod = HttpMethod.DELETE)
    public void removeTransLog(@Named("id") int id,
                               @Named("namespace") String namespace) {
        NamespaceManager.set(namespace);
        super.remove(id);
    }

    private boolean containsTransLog(TransLog translog) {
        NamespaceManager.set(translog.getAccount());
        Key key = translog.getKey();
        return super.contains(translog, key);
    }

}
