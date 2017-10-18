package kz.mergen.kursvalut.Utils;

import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kz.mergen.kursvalut.Models.CurrencyModel;
import kz.mergen.kursvalut.Models.KursLocation;
import kz.mergen.kursvalut.Models.PunktModel;

/**
 * Created by arman on 06.10.17.
 */

public class ParserUtils {


    public ArrayList<PunktModel> getPunktModelsKursKZ(String json){

        ArrayList<PunktModel> punktModels = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            Log.d("AAA","kursy "+json.toString());
            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject punktObject = jsonArray.getJSONObject(i);
                punktModels.add(new PunktModel(
                        punktObject.getString("punkt_name"),
                        punktObject.getString("currencys"),
                        punktObject.getString("punkt_worktime"),
                        punktObject.getString("punkt_address"),
                        punktObject.getString("punkt_telnumbers").toString().replaceAll("]","").replaceAll("\\[",""),
                        punktObject.getDouble("punkt_latitude"),
                        punktObject.getDouble("punkt_longitude"),
                        punktObject.getString("punkt_change_date")
                ));
            }
        } catch (JSONException e){
            Log.d("AAA","parse exception "+e.getMessage());
        }
        return punktModels;
    }
    public ArrayList<CurrencyModel> getCurrencyModelsKursKZ(String json){
        Log.d("AAA","curr "+json);
        ArrayList<CurrencyModel> currencyModels = new ArrayList<CurrencyModel>();
        try {
            JSONArray data_currencys = new JSONArray(json);

            for(int i=0;i<data_currencys.length();i++){
                JSONObject currency = data_currencys.optJSONObject(i);
                if(currency!=null){
                    currencyModels.add(new CurrencyModel(currency.getString("currency_name"),currency.getDouble("currency_prodaja"),currency.getDouble("currency_pokupka")));
                }
            }
        } catch (JSONException e){

        }
        return currencyModels;
    }
}
