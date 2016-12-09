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
import com.google.appengine.datanucleus.query.JPACursorHelper;
import com.google.appengine.repackaged.org.codehaus.jackson.map.deser.ValueInstantiators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

@Api(name = "cycleUseApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "agriexpensesvr.dcit.uwi",
                ownerName = "agriexpensesvr.dcit.uwi",
                packagePath = ""
        ))
public class CycleUseEndpoint extends BaseEndpoint<CycleUse, Key> {

    public CycleUseEndpoint(){
        this.service = new GenericDaoImpl(CycleUse.class);
    }

    //for unit testing
    public CycleUseEndpoint(GenericDao service){
        this.service = service;
    }

    /**
     * This method lists all the entities inserted in datastore. It uses HTTP
     * GET method and paging support.
     *
     * @return A CollectionResponse class containing the list of all entities
     *         persisted and a cursor to the next page.
     */
    @SuppressWarnings({ "unchecked", "unused" })
    @ApiMethod(name = "listCycleUse")
    public CollectionResponse<CycleUse> listCycleUse(
            @Nullable @Named("cursor") String cursorString,
            @Nullable @Named("limit") Integer limit) {
        Cursor cursor = null;
        List<CycleUse> execute = null;

        try {
            Query query = service.createQuery("select from CycleUse as CycleUse");
            if (cursorString != null && cursorString != "") {
                cursor = Cursor.fromWebSafeString(cursorString);
                query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
            }

            if (limit != null) {
                query.setFirstResult(0);
                query.setMaxResults(limit);
            }

            execute = (List<CycleUse>) query.getResultList();
            cursor = JPACursorHelper.getCursor(execute);
            if (cursor != null)
                cursorString = cursor.toWebSafeString();

            // Tight loop for fetching all entities from datastore and
            // accomodate
            // for lazy fetch.
            for (CycleUse obj : execute)
                ;
        } finally {
        }

        return CollectionResponse.<CycleUse> builder().setItems(execute)
                .setNextPageToken(cursorString).build();
    }

    @ApiMethod(name = "getAllCycleUse")
    public List<CycleUse> getAllCycleUse(@Named("namespace") String namespace) {
        NamespaceManager.set(namespace);
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
        com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
                "CycleUse");

        PreparedQuery pq = datastore.prepare(q);
        List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());
        Iterator<Entity> i = results.iterator();
        List<CycleUse> cL = new ArrayList<CycleUse>();
        while (i.hasNext()) {
            Entity e = i.next();
            // System.out.println(e.toString());
            CycleUse c = new CycleUse();

            c.setId(Integer.parseInt("" + e.getProperty("id")));
            c.setCycleid(Integer.parseInt("" + e.getProperty("cycleid")));
            c.setPurchaseId(Integer.parseInt("" + e.getProperty("purchaseId")));
            c.setAmount((Double) e.getProperty("amount"));
            c.setCost((Double) e.getProperty("cost"));
            c.setResource((String) e.getProperty("resource"));
            c.setKeyrep((String) e.getProperty("keyrep"));
            cL.add(c);
        }
        return cL;
    }

    @ApiMethod(name = "deleteAll", httpMethod = HttpMethod.GET)
    public void deleteAll(@Named("namespace") String namespace) throws InternalServerErrorException{
        NamespaceManager.set(namespace);
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
        com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
                "CycleUse");

        PreparedQuery pq = datastore.prepare(q);
        List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());
        Iterator<Entity> i = results.iterator();

        while (i.hasNext()) {
            String keyrep = (String) i.next().getProperty("keyrep");
            removeCycleUse(keyrep, namespace);
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
    @ApiMethod(name = "getCycleUse")
    public CycleUse getCycleUse(@Named("namespace") String namespace,
                                @Named("keyrep") String keyrep) {
        NamespaceManager.set(namespace);
        try {
            return super.GetByKey(keyrep);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        // DatastoreService
        // datastore=DatastoreServiceFactory.getDatastoreService();
		/*
		 * Entity et = null; try { et=datastore.get(k); } catch
		 * (com.google.appengine.api.datastore.EntityNotFoundException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } if(et==null)
		 * return null; cycleuse.setAmount((Double) et.getProperty("amount"));
		 * cycleuse.setCost((Double) et.getProperty("cost"));
		 * cycleuse.setCycleid((Integer) et.getProperty("cycleid"));
		 * cycleuse.setPurchaseId((Integer) et.getProperty("purchaseId"));
		 * cycleuse.setResource((String) et.getProperty("resource")); /*try {
		 * cycleuse = mgr.find(CycleUse.class, id); } finally { mgr.close(); }
		 */
    }


    /**
     * This method gets the entity having primary key id. It uses HTTP GET
     * method.
     *
     * //@param id
     *            the primary key of the java bean.
     * @return The entity with primary key id.
     */
    @ApiMethod(name = "cycleUseWithId")
    public CycleUse cycleUseWithIdOnly(@Named("namespace") String namespace,
                                @Named("ID") int id) {
        NamespaceManager.set(namespace);
        CycleUse cycleuse = null;
        Key k = KeyFactory.createKey("CycleUse",id);
        String keyString = KeyFactory.keyToString(k);
        try {
            cycleuse = super.Get(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cycleuse;
        // DatastoreService
        // datastore=DatastoreServiceFactory.getDatastoreService();
		/*
		 * Entity et = null; try { et=datastore.get(k); } catch
		 * (com.google.appengine.api.datastore.EntityNotFoundException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } if(et==null)
		 * return null; cycleuse.setAmount((Double) et.getProperty("amount"));
		 * cycleuse.setCost((Double) et.getProperty("cost"));
		 * cycleuse.setCycleid((Integer) et.getProperty("cycleid"));
		 * cycleuse.setPurchaseId((Integer) et.getProperty("purchaseId"));
		 * cycleuse.setResource((String) et.getProperty("resource")); /*try {
		 * cycleuse = mgr.find(CycleUse.class, id); } finally { mgr.close(); }
		 */
    }



    /**
     * This inserts a new entity into App Engine datastore. If the entity
     * already exists in the datastore, an exception is thrown. It uses HTTP
     * POST method.
     *
     * @param cycleuse
     *            the entity to be inserted.
     * @return The inserted entity.
     */
    @ApiMethod(name = "insertCycleUse")
    public CycleUse insertCycleUse(CycleUse cycleuse) throws InternalServerErrorException {
        // TODO
        NamespaceManager.set(cycleuse.getAccount());
        Key k = super.createKey(cycleuse.getId());
        cycleuse.setKey(k);
        cycleuse.setKeyrep(KeyFactory.keyToString(k));
            if (containsCycleUse(cycleuse)) {
                throw new EntityExistsException("Object already exists ");
            }
            cycleuse.setKeyrep(KeyFactory.keyToString(k));
            cycleuse.setAccount(KeyFactory.keyToString(k));// using account to store
            cycleuse = super.create(cycleuse);
        return cycleuse;
    }

    /**
     * This method is used for updating an existing entity. If the entity does
     * not exist in the datastore, an exception is thrown. It uses HTTP PUT
     * method.
     *
     * @param cycleUse
     *            the entity to be updated.
     * @return The updated entity.
     */
    @ApiMethod(name = "updateCycleUse")
    public CycleUse updateCycleUse(CycleUse cycleUse) throws InternalServerErrorException {
        Key k = super.createKey(cycleUse.getId());
        CycleUse findCycleUse = super.update(cycleUse,k);
        if(findCycleUse==null) {
            findCycleUse = new CycleUse();
            if (cycleUse.getResource() != null)
                findCycleUse.setResource(cycleUse.getResource());
            if (cycleUse.getAmount() != 0)
                findCycleUse.setAmount(cycleUse.getAmount());
            super.create(findCycleUse);
        }
        return findCycleUse;
    }

    /**
     * This method removes the entity with primary key id. It uses HTTP DELETE
     * method.
     *
     * //@param id
     *            the primary key of the entity to be deleted.
     */
    @ApiMethod(name = "removeCycleUse", httpMethod = HttpMethod.DELETE)
    public void removeCycleUse(@Named("keyrep") String keyrep,  @Named("namespace") String namespace) throws InternalServerErrorException {
        NamespaceManager.set(namespace);
        super.remove(keyrep);

    }

    private boolean containsCycleUse(CycleUse cycleuse) {
        NamespaceManager.set(cycleuse.getAccount());
        Key key = cycleuse.getKey();
        return super.contains(cycleuse, key);
    }


}
