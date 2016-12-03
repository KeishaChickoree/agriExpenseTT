package uwi.dcit.AgriExpenseTT.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import uwi.dcit.AgriExpenseTT.models.CloudKeyContract;
import uwi.dcit.AgriExpenseTT.models.CountryContract;
import uwi.dcit.AgriExpenseTT.models.CountyContract;
import uwi.dcit.AgriExpenseTT.models.CycleContract;
import uwi.dcit.AgriExpenseTT.models.CycleResourceContract;
import uwi.dcit.AgriExpenseTT.models.LabourContract;
import uwi.dcit.AgriExpenseTT.models.RedoLogContract;
import uwi.dcit.AgriExpenseTT.models.ResourceContract;
import uwi.dcit.AgriExpenseTT.models.ResourcePurchaseContract;
import uwi.dcit.AgriExpenseTT.models.TransactionLogContract;
import uwi.dcit.AgriExpenseTT.models.UpdateAccountContract;

/**
 * Created by Tony on 12/01/2016.
 */

public class CreateFacade implements DbActions {

    private Context ctx;
    private String TAG_NAME;

    public CreateFacade( Context c, String s){
        // this.db = db;
        ctx = c;
        TAG_NAME = s;
    }

    public void createDb(SQLiteDatabase db) {

        db.execSQL(ResourceContract.SQL_CREATE_RESOURCE);
        db.execSQL(CycleContract.SQL_CREATE_CYCLE);
        db.execSQL(ResourcePurchaseContract.SQL_CREATE_RESOURCE_PURCHASE);
        db.execSQL(CycleResourceContract.SQL_CREATE_CYCLE_RESOURCE);
        db.execSQL(LabourContract.SQL_CREATE_LABOUR);
        db.execSQL(CloudKeyContract.SQL_DELETE_CLOUD_KEY);
        db.execSQL(RedoLogContract.SQL_CREATE_REDO_LOG);
        db.execSQL(TransactionLogContract.SQL_CREATE_TRANSACTION_LOG);
        db.execSQL(UpdateAccountContract.SQL_CREATE_UPDATE_ACCOUNT);
        db.execSQL(CountryContract.SQL_CREATE_COUNTRIES);
        db.execSQL(CountyContract.SQL_CREATE_COUNTIES);
    }

    public void dropTables(SQLiteDatabase db) {

        db.execSQL(ResourceContract.SQL_DELETE_RESOURCE);
        db.execSQL(CycleContract.SQL_DELETE_CYCLE);
        db.execSQL(ResourcePurchaseContract.SQL_DELETE_RESOURCE_PURCHASE);
        db.execSQL(CycleResourceContract.SQL_DELETE_CYCLE_RESOURCE);
        db.execSQL(LabourContract.SQL_DELETE_LABOUR);
        db.execSQL(CloudKeyContract.SQL_DELETE_CLOUD_KEY);
        db.execSQL(RedoLogContract.SQL_DELETE_REDO_LOG);
        db.execSQL(TransactionLogContract.SQL_DELETE_TRANSACTION_LOG);
        db.execSQL(UpdateAccountContract.SQL_DELETE_UPDATE_ACCOUNT);
        db.execSQL(CountryContract.SQL_DELETE_COUNTRIES);
        db.execSQL(CountyContract.SQL_DELETE_COUNTIES);
    }


    public void insertData(SQLiteDatabase db){

        ArrayList<String> crops = CropDataHelper.getCrops(ctx);
        for (String crop : crops) {
            Log.i(TAG_NAME, "Inserting Crop: " + crop + " to database");
            DbQuery.insertResource(db, DHelper.cat_plantingMaterial, crop);
        }

        DbQuery.insertResource(db, DHelper.cat_fertilizer, "Fersan (7.12.40 + 1TEM)");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "Magic Grow (7.12.40 + TE HYDROPHONIC)");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "Hydro YARA Liva (15.0.15)");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "Techni - Grow (7.12.27 + TE)");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "Ferqidd (10.13.32 + TE)");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "Plant Prod (7.12.27 + TE)");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "Flower Plus (9.18.36 + TE)");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "Iron Chelate Powder (FE - EDTA)");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "Magnesium Sulphate (Mg SO4)");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "12-24-12 FERTILIZER");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "HARVEST MORE 10-55-10");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "HARVEST MORE 13-0-44");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "HARVEST MORE 5-5-45");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "NPK 12-12-17");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "UREA 46-0-0");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "PLANT BOOSTER");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "MIRACLE GRO ALL PROPOSE PLANT FOOD");
        DbQuery.insertResource(db, DHelper.cat_fertilizer, "SCOTTS FLOWER AND VEGETABLE PLANT FOOD");

        DbQuery.insertResource(db, DHelper.cat_soilAmendment, "Cow manure");
        DbQuery.insertResource(db, DHelper.cat_soilAmendment, "Compost");
        DbQuery.insertResource(db, DHelper.cat_soilAmendment, "Gypsum");
        DbQuery.insertResource(db, DHelper.cat_soilAmendment, "Limestone");
        DbQuery.insertResource(db, DHelper.cat_soilAmendment, "Sulphur");
        DbQuery.insertResource(db, DHelper.cat_soilAmendment, "Molasses");
        DbQuery.insertResource(db, DHelper.cat_soilAmendment, "Chicken manure");
        DbQuery.insertResource(db, DHelper.cat_soilAmendment, "Horse manure");
        DbQuery.insertResource(db, DHelper.cat_soilAmendment, "Calphos");
        DbQuery.insertResource(db, DHelper.cat_soilAmendment, "Sharp sand");

        DbQuery.insertResource(db, DHelper.cat_chemical, "Fungicide");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Insecticide");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Weedicide");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Algicides");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Antimicrobials");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Biopesticides");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Biocides");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Fumigants");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Herbicides");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Miticides");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Microbial pesticides");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Molluscicides");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Nematicides");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Ovicides");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Pheromones");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Repellents");
        DbQuery.insertResource(db, DHelper.cat_chemical, "Rodenticides");

        for (String [] country : CountryContract.countries){
            DbQuery.insertCountry(db, country[0], country[1]);
        }

        for (String [] county : CountyContract.counties){
            DbQuery.insertCounty(db, county[0], county[1]);
        }

    }

}
