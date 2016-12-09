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

@Api(name = "resourcePurchaseApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "agriexpensesvr.dcit.uwi",
                ownerName = "agriexpensesvr.dcit.uwi",
                packagePath = ""
        ))
public class ResourcePurchaseEndpoint extends BaseEndpoint<ResourcePurchase,Key> {


    public ResourcePurchaseEndpoint(){
        super.service = new GenericDaoImpl<ResourcePurchase,Key>(ResourcePurchase.class);
    }

    //For unit testing purposes
    public ResourcePurchaseEndpoint(GenericDao service){
        super.service = service;
    }

    /**
     * This method lists all the entities inserted in datastore. It uses HTTP
     * GET method and paging support.
     *
     * @return A CollectionResponse class containing the list of all entities
     *         persisted and a cursor to the next page.
     */
    @SuppressWarnings({ "unchecked", "unused" })
    @ApiMethod(name = "listRPurchase")
    public CollectionResponse<ResourcePurchase> listRPurchase(
            @Nullable @Named("cursor") String cursorString,
            @Nullable @Named("limit") Integer limit) {
        Cursor cursor = null;
        List<ResourcePurchase> execute = null;

        try {
            Query query = service.createQuery("select from ResourcePurchase as ResourcePurchase");
            if (cursorString != null && cursorString != "") {
                cursor = Cursor.fromWebSafeString(cursorString);
                query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
            }

            if (limit != null) {
                query.setFirstResult(0);
                query.setMaxResults(limit);
            }

            execute = (List<ResourcePurchase>) query.getResultList();
            cursor = JPACursorHelper.getCursor(execute);
            if (cursor != null)
                cursorString = cursor.toWebSafeString();
            for (ResourcePurchase obj : execute)
                System.out.println(obj.getElementName());
        } catch (Exception e){

        }

        return CollectionResponse.<ResourcePurchase> builder().setItems(execute)
                .setNextPageToken(cursorString).build();
    }

    @SuppressWarnings("unchecked")
    @ApiMethod(name = "fetchAllPurchases")
    public List<ResourcePurchase> fetchAllPurchases() {

        EntityManager mgr = null;
        List<ResourcePurchase> execute = null;
        Query query = null;

		/* For namespace list fetching */
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
                Entities.NAMESPACE_METADATA_KIND);

        List<String> results = new ArrayList<String>();
        for (Entity e : ds.prepare(q).asIterable()) {
            if (e.getKey().getId() != 0) {
                System.out.println("<default>");
            } else {
                // System.out.println(e.getKey().getName());
                results.add(Entities.getNamespaceFromNamespaceKey(e.getKey()));
            }
        }
        query = service.createQuery("SELECT FROM ResourcePurchase AS ResourcePurchase");

        // Set each namespace then return all results under that given namespace

        List<ResourcePurchase> purchaseList = new ArrayList<ResourcePurchase>();

        for (String i : results) {

            NamespaceManager.set(i);
            execute = (List<ResourcePurchase>) query.getResultList();
            for (ResourcePurchase obj : execute) {
                purchaseList.add(obj);
            }
        }
        return purchaseList;
    }

    @ApiMethod(name = "getAllPurchases")
    public List<ResourcePurchase> getAllPurchases(@Named("namespace") String namespace) {
        NamespaceManager.set(namespace);
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
        com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
                "ResourcePurchase");

        PreparedQuery pq = datastore.prepare(q);
        List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());
        Iterator<Entity> i = results.iterator();
        List<ResourcePurchase> pL = new ArrayList<ResourcePurchase>();
        System.out.println("record Holder:------------------");
        while (i.hasNext()) {
            System.out.println("record-------------  -----");
            Entity e = i.next();
            System.out.println(e.toString());
            ResourcePurchase p = new ResourcePurchase();

            p.setpId(Integer.parseInt("" + e.getProperty("pId")));
            p.setType((String) e.getProperty("type"));
            p.setResourceId(Integer.parseInt("" + e.getProperty("resourceId")));
            p.setQuantifier((String) e.getProperty("quantifier"));
            p.setQty((Double) e.getProperty("qty"));
            p.setCost((Double) e.getProperty("cost"));
            p.setQtyRemaining((Double) e.getProperty("qtyRemaining"));
            p.setKeyrep((String) e.getProperty("keyrep"));
            p.setPurchaseDate((long) e.getProperty("purchaseDate"));
            pL.add(p);
        }
        return pL;
    }

    @ApiMethod(name = "deleteAll", httpMethod = HttpMethod.GET)
    public void deleteAll(@Named("namespace") String namespace) throws InternalServerErrorException{
        NamespaceManager.set(namespace);
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
        com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
                "ResourcePurchase");

        PreparedQuery pq = datastore.prepare(q);
        List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());
        Iterator<Entity> i = results.iterator();

        while (i.hasNext()) {
            String key = (String) i.next().getProperty("keyrep");
            removeRPurchase(key, namespace);
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
    @ApiMethod(name = "getRPurchase")
    public ResourcePurchase getRPurchase(@Named("namespace") String namespace,
                                  @Named("keyrep") String keyrep) {
        NamespaceManager.set(namespace);
        ResourcePurchase rpurchase = super.GetByKey(keyrep);
        return rpurchase;
		/*
         * DatastoreService
		 * datastore=DatastoreServiceFactory.getDatastoreService(); Key
		 * k=KeyFactory.stringToKey(id); Entity et = null; try {
		 * et=datastore.get(k); } catch
		 * (com.google.appengine.api.datastore.EntityNotFoundException e) {
		 *
		 * e.printStackTrace(); } RPurchase p=new RPurchase(); if(et==null){
		 * return null; }
		 *
		 * p.setCost((Double) et.getProperty("cost")); p.setQty((Double)
		 * et.getProperty("qty")); p.setQuantifier((String)
		 * et.getProperty("quantifier")); p.setResourceId((Integer)
		 * et.getProperty("resourceId")); p.setQtyRemaining((Double)
		 * et.getProperty("qtyRemaining")); p.setType((String)
		 * et.getProperty("type")); return p;
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
    @ApiMethod(name = "purchaseWithID")
    public ResourcePurchase purchaseWithID(@Named("namespace") String namespace,
                                         @Named("ID") int id) {
        NamespaceManager.set(namespace);
        ResourcePurchase rpurchase = null;
        try {
            rpurchase = super.Get(id);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("---000---_");
        return rpurchase;
    }

    /**
     * This inserts a new entity into App Engine datastore. If the entity
     * already exists in the datastore, an exception is thrown. It uses HTTP
     * POST method.
     *
     * @param rpurchase
     *            the entity to be inserted.
     * @return The inserted entity.
     */
    @ApiMethod(name = "insertRPurchase")
    public ResourcePurchase insertRPurchase(ResourcePurchase rpurchase) throws InternalServerErrorException {
        // TODO
        NamespaceManager.set(rpurchase.getAccount());
        Key k = KeyFactory.createKey("ResourcePurchase", rpurchase.getpId());
        rpurchase.setKey(k);
        rpurchase.setKeyrep(KeyFactory.keyToString(k));
        System.out.println("---------HERE");

            if (containsRPurchase(rpurchase)) {
                throw new EntityExistsException("Object already exists");
            }
            else{
                rpurchase.setKeyrep(KeyFactory.keyToString(k));
                rpurchase.setAccount(KeyFactory.keyToString(k));
                rpurchase = super.create(rpurchase);
            }

        return rpurchase;
    }

    /**
     * This method is used for updating an existing entity. If the entity does
     * not exist in the datastore, an exception is thrown. It uses HTTP PUT
     * method.
     *
     * @param-rpurchase
     *            the entity to be updated.
     * @return The updated entity.
     */
    @ApiMethod(name = "updateRPurchase")
    public ResourcePurchase updateRPurchase(ResourcePurchase rPurchase) throws InternalServerErrorException {
        NamespaceManager.set(rPurchase.getAccount());
        Key k = KeyFactory.createKey("ResourcePurchase", rPurchase.getpId());
        ResourcePurchase currentRPurchase = super.update(rPurchase,k);

        if(currentRPurchase!=null){
                if(rPurchase.getQtyRemaining()==-1.00)
                    currentRPurchase.setQtyRemaining(0.00);
                else
                    //(rPurchase.getQtyRemaining()!=0)
                    currentRPurchase.setQtyRemaining(rPurchase.getQtyRemaining());
                if(rPurchase.getQuantifier()!=null)
                    currentRPurchase.setQuantifier(rPurchase.getQuantifier());
                if(rPurchase.getType()!=null)
                    currentRPurchase.setType(rPurchase.getType());
                currentRPurchase = super.create(currentRPurchase);
        }
        return currentRPurchase;
    }

//    @ApiMethod(name = "deletePurchase", httpMethod = HttpMethod.DELETE)
//    public void deletePurchase(@Named("keyrep") String keyrep,
//                               @Named("namespace") String namespace) {
//        System.out.println("1111111111111");
//        NamespaceManager.set(namespace);
//        Key k = KeyFactory.stringToKey(keyrep);
//        EntityManager mgr = getEntityManager();
//        ResourcePurchase purchase = mgr.find(ResourcePurchase.class, k);
//        try {
//            mgr.remove(purchase);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * This method removes the entity with primary key id. It uses HTTP DELETE
     * method.
     *
     * //@param id
     *            the primary key of the entity to be deleted.
     */
    @ApiMethod(name = "removeRPurchase", httpMethod = HttpMethod.DELETE)
    public void removeRPurchase(@Named("keyRep") String keyRep,
                                @Named("namespace") String namespace) throws InternalServerErrorException {
        System.out.println("1111111111111");
        NamespaceManager.set(namespace);
        super.remove(keyRep);
    }

    private boolean containsRPurchase(ResourcePurchase rpurchase) {
        NamespaceManager.set(rpurchase.getAccount());
        return super.contains(rpurchase,rpurchase.getKey());
    }
}
