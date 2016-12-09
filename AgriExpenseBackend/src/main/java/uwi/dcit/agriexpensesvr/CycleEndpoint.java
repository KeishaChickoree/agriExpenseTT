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
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;


@Api( name = "cycleApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "agriexpensesvr.dcit.uwi",
                ownerName = "agriexpensesvr.dcit.uwi",
                packagePath = ""
        ))
public class CycleEndpoint extends BaseEndpoint<Cycle,Key>{

    //For runtime instantiation of service for querying data
    public CycleEndpoint() {
        this.service = new GenericDaoImpl(Cycle.class);
    }//end constructor 1

    //For dependency injection and unit/integration testing purposes
    public CycleEndpoint(GenericDao service){
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
    @ApiMethod(name = "listCycle")
    public CollectionResponse<Cycle> listCycle(
            @Nullable @Named("cursor") String cursorString,
            @Nullable @Named("limit") Integer limit) {

        Cursor cursor;
        List<Cycle> execute = null;

        try {
            Query query = service.createQuery("select from Cycle as Cycle");
            if (cursorString != null && !cursorString.equals("")) {
                cursor = Cursor.fromWebSafeString(cursorString);
                query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
            }

            if (limit != null) {
                query.setFirstResult(0);
                query.setMaxResults(limit);
            }

            execute = (List<Cycle>) query.getResultList();
            cursor = JPACursorHelper.getCursor(execute);
            if (cursor != null)
                cursorString = cursor.toWebSafeString();

            // Tight loop for fetching all entities from datastore and
            // accomodate
            // for lazy fetch.
//            for (Cycle obj : execute)
//                ;
        } finally {

        }

        return CollectionResponse.<Cycle> builder().setItems(execute)
                .setNextPageToken(cursorString).build();
    }

    @SuppressWarnings("unchecked")
    @ApiMethod(name = "fetchAllCycles")
    public List<Cycle> fetchAllCycles() {

        List<Cycle> execute;
        Query query;

		/* For namespace list fetching */
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
                Entities.NAMESPACE_METADATA_KIND);

        List<String> results = new ArrayList<>();
        for (Entity e : ds.prepare(q).asIterable()) {
            if (e.getKey().getId() != 0) {
                System.out.println("<default>");
            } else {
                // System.out.println(e.getKey().getName());
                results.add(Entities.getNamespaceFromNamespaceKey(e.getKey()));
            }
        }
        query = service.createQuery("SELECT FROM Cycle AS Cycle");

        // Set each namespace then return all results under that given namespace

        List<Cycle> cycleList = new ArrayList<>();

        for (String i : results) {

            NamespaceManager.set(i);
//            execute = (List<Cycle>) query.getResultList();
            execute = super.getAll();
            for (Cycle obj : execute) {
                cycleList.add(obj);
            }
        }
        return cycleList;
    }

    @ApiMethod(name = "getAllCycles")
    public List<Cycle> getAllCycles(@Named("namespace") String namespace) {
        NamespaceManager.set(namespace);

        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
        com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
                "Cycle");

        PreparedQuery pq = datastore.prepare(q);
        List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());
        Iterator<Entity> i = results.iterator();
        List<Cycle> cL = new ArrayList<>();
        while (i.hasNext()) {
            Entity e = i.next();
            // System.out.println(e.toString());
            Cycle c = new Cycle();

            c.setId(Integer.parseInt("" + e.getProperty("id")));
            c.setCropId(Integer.parseInt("" + e.getProperty("cropId")));
            c.setLandQty((Double) e.getProperty("landQty"));
            c.setLandType((String) e.getProperty("landType"));
            c.setTotalSpent((Double) e.getProperty("totalSpent"));
            c.setHarvestAmt((Double) e.getProperty("harvestAmt"));
            c.setHarvestType((String) e.getProperty("harvestType"));
            c.setCostPer((Double) e.getProperty("costPer"));
            c.setKeyrep((String) e.getProperty("keyrep"));
            c.setClosed((String) e.getProperty("closed"));
            cL.add(c);
        }
        return cL;
    }

    @SuppressWarnings("unchecked")
    @ApiMethod(name = "getMatchingCycles")
    public List<Cycle> getMatchingCyles(@Named("cropName") String cropName,
                                        @Named("selectedArea") String selectedArea,
                                        @Named("start_date") String start_date,
                                        @Named("end_date") String end_date) {

        List<Cycle> execute;
        Query query;

		/* For namespace list fetching */
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
                Entities.NAMESPACE_METADATA_KIND);

        List<String> results = new ArrayList<>();
        for (Entity e : ds.prepare(q).asIterable()) {
            if (e.getKey().getId() != 0) {
                System.out.println("<default>");
            } else {
                // System.out.println(e.getKey().getName());
                results.add(Entities.getNamespaceFromNamespaceKey(e.getKey()));
            }
        }

        if (selectedArea.equals("Nationwide")) {
            query = service
                    .createQuery("SELECT FROM Cycle AS Cycle WHERE cropName = :p1 AND startDate >= :p2 AND startDate <= :p3 ORDER BY startDate ASC");
            query.setParameter("p1", cropName.toUpperCase());
            query.setParameter("p2", Long.parseLong(start_date));
            query.setParameter("p3", Long.parseLong(end_date));
        } else {
            query = service
                    .createQuery("SELECT FROM Cycle AS Cycle WHERE cropName = :p1 AND county = :p2 AND startDate >= :p3 AND startDate <= :p4 ORDER BY startDate ASC");
            query.setParameter("p1", cropName.toUpperCase());
            query.setParameter("p2", selectedArea.toUpperCase());
            query.setParameter("p3", Long.parseLong(start_date));
            query.setParameter("p4", Long.parseLong(end_date));
        }

        // Set each namespace then return all results under that given namespace

        List<Cycle> cycleList = new ArrayList<>();

        for (String i : results) {

            NamespaceManager.set(i);
            execute = (List<Cycle>) query.getResultList();
            for (Cycle obj : execute) {
                cycleList.add(obj);
            }
        }
        return cycleList;
    }

    @ApiMethod(name = "deleteAll", httpMethod = HttpMethod.GET)
    public void deleteAll(@Named("namespace") String namespace) throws InternalServerErrorException{
        NamespaceManager.set(namespace);

        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
        com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
                "Cycle");

        PreparedQuery pq = datastore.prepare(q);
        List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());

        for (Entity result : results) {
            String key = (String) result.getProperty("key");
            removeCycle(key, namespace);
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
    @ApiMethod(name = "getCycle")
     public Cycle getCycle(@Named("namespace") String namespace,
                           @Named("keyrep") String keyrep) {
        NamespaceManager.set(namespace);;
        return super.GetByKey(keyrep);
    }

    /**
     * This method gets the entity having primary key id. It uses HTTP GET
     * method.
     *It is mainly used when updating a cycle with respect to the cloud's database.
     * Note that a Transaction Log's Key Representation cannot be used to find that of a cycle
     * hence we pass the ID of the cycle and create the key in this method itself.
     * //@param id
     *            the primary key of the java bean.
     * @return The entity with primary key id.
     */
    @ApiMethod(name = "CycleWithID")
    public Cycle CycleIdOnly(@Named("namespace") String namespace,
                         @Named("ID") int id) throws InternalServerErrorException {
        NamespaceManager.set(namespace);
        return super.Get(id);
    }



    /**
     * This inserts a new entity into App Engine datastore. If the entity
     * already exists in the datastore, an exception is thrown. It uses HTTP
     * POST method.
     *
     * @param cycle
     *            the entity to be inserted.
     * @return The inserted entity.
     */
    @ApiMethod(name = "insertCycle")
    public Cycle insertCycle(Cycle cycle) throws InternalServerErrorException{
        // TODO
        NamespaceManager.set(cycle.getAccount());
        Key k = super.createKey(cycle.getId());
        cycle.setKey(k);
        cycle.setKeyrep(KeyFactory.keyToString(k));

            if (containsCycle(cycle)) {
                throw new EntityExistsException("Object already exists!" +
                        "");
            }
            else {
                // using account to store
                // the string rep of the
                // key
                cycle.setKeyrep(KeyFactory.keyToString(k));
                cycle.setAccount(KeyFactory.keyToString(k));
                cycle = super.create(cycle);

            }
        return cycle;
    }

    /**
     * This method is used for updating an existing entity. If the entity does
     * not exist in the datastore, an exception  is thrown. It uses HTTP PUT
     * method.
     *
     * @param cycle
     *            the entity to be updated.
     * @return The updated entity.
     */
    @ApiMethod(name = "updateCycle")
    public Cycle updateCycle(Cycle cycle) throws InternalServerErrorException {
        //System.out.println(cycle.getKeyrep());
        NamespaceManager.set(cycle.getAccount());
        Key k = super.createKey(cycle.getId());
        Cycle c = null;
       if(!contains(c,k)){
            if(cycle.getHarvestAmt()!=0)
                c.setHarvestAmt(cycle.getHarvestAmt());
            if(cycle.getHarvestType()!=null)
                c.setHarvestType(cycle.getHarvestType());
            if(cycle.getTotalSpent()!=0.0)
                c.setTotalSpent(cycle.getTotalSpent());
            if(cycle.getTotalSpent()==-1.00)
                c.setTotalSpent(0.00);
            if(cycle.getClosed().equals("closed"))
                c.setClosed("closed");
           c = super.create(c);
        }
        return c;
    }

    /**
     * This method removes the entity with primary key id. It uses HTTP DELETE
     * method.
     *
     * //@param id
     *            the primary key of the entity to be deleted.
     */
    @ApiMethod(name = "removeCycle", httpMethod = HttpMethod.DELETE)
    public void removeCycle(@Named("KeyRep") String keyRep, @Named("namespace") String namespace) throws InternalServerErrorException {
        NamespaceManager.set(namespace);
        super.remove(keyRep);

    }

    @ApiMethod(name="getCycles", httpMethod = HttpMethod.GET)
    public List<Cycle> getACycles(){
        return  service.getAll();
    }

    private boolean containsCycle(Cycle cycle) {
        NamespaceManager.set(cycle.getAccount());
        Key key = cycle.getKey();
        return super.contains(cycle, key);
    }
}