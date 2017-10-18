package kz.mergen.kursvalut;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import kz.mergen.kursvalut.Models.KursLocation;
import kz.mergen.kursvalut.Models.PunktModel;
import kz.mergen.kursvalut.Utils.ServiceHelper;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        ArrayList<PunktModel> punktModels =  App.getInstance().databaseHelper.getPunkts(appContext);
        for(PunktModel punktModel : punktModels){
            Log.d("AAA","has "+App.getInstance().databaseHelper.hasLocation(appContext,punktModel.getAddress())+" "+punktModel.getAddress());
        }

       // KursLocation location = App.getInstance().databaseHelper.getPunkt(appContext,item).getLocation();
       // Log.d("AAA","loca "+location.getLatitude()+" "+location.getLongitude());



        //assertEquals("kz.mergen.kursvalut", appContext.getPackageName());
    }

}
