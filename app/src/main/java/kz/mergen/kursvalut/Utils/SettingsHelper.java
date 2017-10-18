package kz.mergen.kursvalut.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.Preference;

import kz.mergen.kursvalut.Models.KursLocation;

/**
 * Created by arman on 30.09.17.
 */

public class SettingsHelper {
    private String APP_PREFERENCE = "appsettings";
    private String CITY = "city_pos";
    private String MYPOS_LAT = "mypos_lat";
    private String MYPOS_LONG = "mypos_long";
    private String FIRST_RUN = "first_run";
    private SharedPreferences preferences;

    private SharedPreferences getPreferences(Context context){
        if(preferences==null){
            preferences = context.getSharedPreferences(APP_PREFERENCE,Context.MODE_PRIVATE);
        }
            return preferences;
    }
    public void saveMyPos(Context context, Location location){
        getPreferences(context).edit().putString(MYPOS_LAT, String.valueOf(location.getLatitude())).putString(MYPOS_LONG, String.valueOf(location.getLongitude())).commit();
    }
    public Location getMyPos(Context context){
        String latitude =  getPreferences(context).getString(MYPOS_LAT,null);
        String longitude =  getPreferences(context).getString(MYPOS_LONG,null);
        if(latitude==null||longitude==null){
            return null;
        }
        Location location = new Location("");
        location.setLongitude(Double.parseDouble(longitude));
        location.setLatitude(Double.parseDouble(latitude));
        return location;
    }
    public int getCityPosition(Context context){
        return getPreferences(context).getInt(CITY,1);
    }
    public void saveCityPosition(Context context, int position){
        getPreferences(context).edit().putInt(CITY, position).commit();
    }
    public void saveFirstRun(Context context, boolean firstRun){
        getPreferences(context).edit().putBoolean(FIRST_RUN, firstRun).commit();
    }
    public boolean isFirstRun(Context context){
        return getPreferences(context).getBoolean(FIRST_RUN, true);
    }
}
