package kz.mergen.kursvalut;

import android.app.Application;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import kz.mergen.kursvalut.Ui.BankFragment;
import kz.mergen.kursvalut.Ui.PunktsFragment;
import kz.mergen.kursvalut.Utils.DatabaseController;
import kz.mergen.kursvalut.Utils.LocationService;
import kz.mergen.kursvalut.Utils.ParserUtils;
import kz.mergen.kursvalut.Utils.ServiceHelper;
import kz.mergen.kursvalut.Utils.SettingsHelper;

/**
 * Created by arman on 09.10.17.
 */

public class App extends Application {


    private static App instance;
    public DatabaseController.DatabaseHelper databaseHelper;
    public ServiceHelper serviceHelper;
    public PunktsFragment punktsFragment;
    public BankFragment bankFragment;
    public SettingsHelper settingsHelper;
    public ParserUtils parserUtils;

    public static App getInstance(){
        return instance;
    }

    public boolean fragmentIsShow(Fragment fragment){
        if(fragment== punktsFragment){
            return punktsFragment.isShown;
        } else {
            return bankFragment.isShown;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        databaseHelper = new DatabaseController.DatabaseHelper();
        serviceHelper = new ServiceHelper(getApplicationContext());
        settingsHelper = new SettingsHelper();
        punktsFragment = new PunktsFragment();
        bankFragment = new BankFragment();
        parserUtils = new ParserUtils();
    }

    public Location getLocation(String title, double latitude, double longitude){
        Location location = new Location(title);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }
    public void showToastMessage(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

}
