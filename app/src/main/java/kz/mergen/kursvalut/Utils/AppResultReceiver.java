package kz.mergen.kursvalut.Utils;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.Serializable;

import kz.mergen.kursvalut.Models.KursLocation;

/**
 * Created by arman on 13.10.17.
 */

public class AppResultReceiver extends ResultReceiver  {





    public interface Receiver  {
        void onLoading();
        void onErrorLoaded();
        void onBankLoaded();
        void onPunktLoaded();
        void onLocationLoaded();
    }

    private Receiver receiver;

    public AppResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if(receiver!=null){
            if(resultCode==Constants.STATUS_RUNNING){
                receiver.onLoading();
            }
            if(resultCode==Constants.STATUS_ERROR){
                receiver.onErrorLoaded();
            }
            if(resultCode==Constants.STATUS_BANK_LOADED){
                receiver.onBankLoaded();
            }
            if(resultCode==Constants.STATUS_PUNKT_LOADED){
                receiver.onPunktLoaded();
            }
            if(resultCode==Constants.STATUS_LOCATION_LOADED){
                receiver.onLocationLoaded();
            }

        }
    }
}
