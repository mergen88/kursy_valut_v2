package kz.mergen.kursvalut.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;

import kz.mergen.kursvalut.App;
import kz.mergen.kursvalut.Models.BankModel;
import kz.mergen.kursvalut.Models.KursLocation;
import kz.mergen.kursvalut.Models.PunktModel;

/**
 * Created by arman on 03.10.17.
 */

public class DatabaseController extends SQLiteOpenHelper {
    public static int VERSION = 1;
    public static String DATABASE_NAME = "rates.db";
    public static String BANK_TABLE = "bank_tb";
    public static String RATES_TABLE = "rates_tb";
    public static String CUR_NAME_COL = "currency_name";
    public static String CUR_MONEY_COL = "currency_money";
    public static String CUR_CHANGE_COL = "currency_change";
    public static String DATE_CHANGE_COL = "date_change";
    public static String PUNKT_NAME_COL = "punkt_name";
    public static String PUNKT_CHANGE_DATE_COL = "punkt_change_date";
    public static String PUNKT_ADDRESS_COL = "punkt_address";
    public static String PUNKT_CURRENCY_COL = "punkt_currency";
    public static String WORK_TIME_COL = "work_time";
    public static String PUNKT_LATITUDE = "p_latitude";
    public static String PUNKT_LONGITUDE = "p_longitude";
    public static String PUNKT_TELNUMBER_COL = "punkt_telnumber";
    public static String IPKA = " (_id INTEGER PRIMARY KEY AUTOINCREMENT, ";
    private String CREATE_BANK = "CREATE TABLE "+BANK_TABLE+IPKA+
            CUR_NAME_COL+" TEXT, "+
            CUR_MONEY_COL+" TEXT, "+
            CUR_CHANGE_COL+" TEXT, "+
            DATE_CHANGE_COL+" TEXT);";
    private String CREATE_RATES = "CREATE TABLE "+RATES_TABLE+IPKA+
            PUNKT_NAME_COL+" TEXT, "+
            PUNKT_CHANGE_DATE_COL+" TEXT, "+
            PUNKT_CURRENCY_COL+" TEXT, "+
            PUNKT_TELNUMBER_COL+" TEXT, "+
            WORK_TIME_COL+" TEXT, "+
            PUNKT_LATITUDE+" DOUBLE, "+
            PUNKT_LONGITUDE+" DOUBLE, "+
            PUNKT_ADDRESS_COL+" TEXT);";

    public DatabaseController(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RATES);
        db.execSQL(CREATE_BANK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS + "+RATES_TABLE+";");
        db.execSQL("DROP TABLE IF EXISTS + "+BANK_TABLE+";");
        onCreate(db);
    }

    public static class DatabaseHelper{
        private SQLiteDatabase database;


        private SQLiteDatabase getDatabase(Context context){
            if(database ==null){
                database = new DatabaseController(context).getWritableDatabase();
            }
            return database;
        }
        public void saveBank(Context context, ArrayList<BankModel> bankModels){
            getDatabase(context).execSQL("DELETE FROM "+ BANK_TABLE);
            ContentValues values = new ContentValues();
            for(BankModel bankModel : bankModels){
                values.put(CUR_NAME_COL,bankModel.getValuta());
                values.put(CUR_MONEY_COL,bankModel.getMoney());
                values.put(CUR_CHANGE_COL,bankModel.getMoney_change());
                values.put(DATE_CHANGE_COL,bankModel.getDate_change());
                getDatabase(context).insert(BANK_TABLE,null,values);
                values.clear();
            }
        }
        public boolean hasPunkt(Context context, String punktName){
            Cursor cursor = getDatabase(context).query(RATES_TABLE,new String[]{PUNKT_NAME_COL},PUNKT_NAME_COL+"= ?",new String[]{punktName},null,null,null);
            Log.d("AAA","has punkt "+cursor.getCount());
            if(cursor.getCount()!=0){
                return true;
            }
            return false;
        }
        public void savePunkt(Context context, ArrayList<PunktModel> punktModels){
            ContentValues values = new ContentValues();
            for(PunktModel punktModel : punktModels){
                values.put(PUNKT_NAME_COL,punktModel.getExchanger_name());
                values.put(PUNKT_ADDRESS_COL,punktModel.getAddress());
                values.put(PUNKT_CHANGE_DATE_COL, punktModel.date_change);
                values.put(PUNKT_TELNUMBER_COL, punktModel.getTelnumber());
                values.put(WORK_TIME_COL, punktModel.getWork_time());
                values.put(PUNKT_LATITUDE, punktModel.getLatitude());
                values.put(PUNKT_LONGITUDE, punktModel.getLongitude());
                values.put(PUNKT_CURRENCY_COL,punktModel.getCurrencys());
                if(hasPunkt(context, punktModel.getExchanger_name())){
                    getDatabase(context).update(RATES_TABLE,values,PUNKT_NAME_COL+" = ?",new String[]{punktModel.getExchanger_name()});
                    Log.d("AAA","update");
                } else {
                    getDatabase(context).insert(RATES_TABLE,null,values);
                }
                values.clear();
            }
        }
        public boolean hasLocation(Context context, String address){
            Cursor cursor = getDatabase(context).query(RATES_TABLE,new String[]{PUNKT_LATITUDE,PUNKT_LONGITUDE},PUNKT_ADDRESS_COL+"= ? AND "+PUNKT_LONGITUDE+" IS NOT NULL AND "+PUNKT_LATITUDE+" IS NOT NULL",new String[]{address},null,null,null);
            cursor.moveToFirst();
            Log.d("AAA","hasloc "+cursor.getCount());
            if(cursor.getCount()!=0) {
                return true;
            }
            cursor.close();
            return false;
        }

        public PunktModel getPunkt(Context context, String name){
            Cursor cursor = getDatabase(context).rawQuery("SELECT * FROM "+RATES_TABLE+" WHERE "+PUNKT_NAME_COL +"= '"+name+"';",null);
            cursor.moveToFirst();
                return new PunktModel(
                        cursor.getString(cursor.getColumnIndex(PUNKT_NAME_COL)),
                        cursor.getString(cursor.getColumnIndex(PUNKT_CURRENCY_COL)),
                        cursor.getString(cursor.getColumnIndex(WORK_TIME_COL)),
                        cursor.getString(cursor.getColumnIndex(PUNKT_ADDRESS_COL)),
                        cursor.getString(cursor.getColumnIndex(PUNKT_TELNUMBER_COL)),
                        cursor.getDouble(cursor.getColumnIndex(PUNKT_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(PUNKT_LONGITUDE)),
                        cursor.getString(cursor.getColumnIndex(PUNKT_CHANGE_DATE_COL))
                );

        }
        public ArrayList<BankModel> getBank(Context context){
            Cursor cursor = getDatabase(context).query(BANK_TABLE,null,null,null,null,null,null,null);
            ArrayList<BankModel> bankModels = new ArrayList<>();
            while (cursor.moveToNext()){
                bankModels.add(new BankModel(
                        cursor.getString(cursor.getColumnIndex(CUR_NAME_COL)),
                        cursor.getString(cursor.getColumnIndex(CUR_MONEY_COL)),
                        cursor.getString(cursor.getColumnIndex(DATE_CHANGE_COL)),
                        cursor.getString(cursor.getColumnIndex(CUR_CHANGE_COL))
                ));
            }
            cursor.close();
            return bankModels;
        }
        public ArrayList<PunktModel> getPunkts(Context context, int count, int offset){
            Cursor cursor = getDatabase(context).query(RATES_TABLE,null,null,null,null,null,null,null);
            ArrayList<PunktModel> punktModels = new ArrayList<>();
            int o = 1;
            int c = 0;
            while (cursor.moveToNext()){
                if(o>offset){
                    if(c<count){
                        punktModels.add(new PunktModel(
                                cursor.getString(cursor.getColumnIndex(PUNKT_NAME_COL)),
                                cursor.getString(cursor.getColumnIndex(PUNKT_CURRENCY_COL)),
                                cursor.getString(cursor.getColumnIndex(WORK_TIME_COL)),
                                cursor.getString(cursor.getColumnIndex(PUNKT_ADDRESS_COL)),
                                cursor.getString(cursor.getColumnIndex(PUNKT_TELNUMBER_COL)),
                                cursor.getDouble(cursor.getColumnIndex(PUNKT_LATITUDE)),
                                cursor.getDouble(cursor.getColumnIndex(PUNKT_LONGITUDE)),
                                cursor.getString(cursor.getColumnIndex(PUNKT_CHANGE_DATE_COL))
                        ));
                    } else {
                        break;
                    }
                    c++;
                }
                o++;
            }
            cursor.close();
            return punktModels;
        }
    }


}
