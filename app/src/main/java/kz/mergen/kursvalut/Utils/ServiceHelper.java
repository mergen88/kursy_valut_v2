package kz.mergen.kursvalut.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;

/**
 * Created by arman on 30.09.17.
 */

public class ServiceHelper {

    private Context context;
    private AppResultReceiver resultReceiver;

    public ServiceHelper(Context context){
        this.context = context;
        resultReceiver = new AppResultReceiver(new Handler());

    }
    public ServiceHelper initListener(AppResultReceiver.Receiver receiver){
        resultReceiver.setReceiver(receiver);
        return this;
    }

    public void getMyLocation(){
        LocationService.startService(context,resultReceiver);
    }
    public void getKursKzPunkts(){
        MainService.sendCommand(context, MainService.SERVICE_COMMAND.GETPUNKTS, null, resultReceiver);
    }

    public void getBankKurs(){
        MainService.sendCommand(context, MainService.SERVICE_COMMAND.GETBANK, null, resultReceiver);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
