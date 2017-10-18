package kz.mergen.kursvalut.Utils;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import kz.mergen.kursvalut.App;
import kz.mergen.kursvalut.Models.BankModel;
import kz.mergen.kursvalut.Models.KursLocation;
import kz.mergen.kursvalut.Models.PunktModel;
import kz.mergen.kursvalut.Models.ResponceModel;
import kz.mergen.kursvalut.R;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MainService extends IntentService {

    private Context context;
    private ResultReceiver resultReceiver;
    private App app;
    private String optional;
    private SERVICE_COMMAND command;

    public static enum SERVICE_COMMAND implements Serializable {
        GETPUNKTS, GETBANK
    }

    public MainService() {
        super("MainService");
    }

    public static void sendCommand(Context context, SERVICE_COMMAND service_command, String optional, AppResultReceiver receiver) {
        Intent intent = new Intent(context, MainService.class);
        intent.putExtra(Constants.APP_RECEIVER, receiver);
        intent.putExtra(Constants.SERVICE_COMMAND, service_command);
        if (optional != null) {
            intent.putExtra(Constants.OPTIONAL, optional);
        }
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = App.getInstance();
        context = getApplicationContext();
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        command = (SERVICE_COMMAND) intent.getSerializableExtra(Constants.SERVICE_COMMAND);
        resultReceiver = intent.getParcelableExtra(Constants.APP_RECEIVER);

        if (intent.hasExtra(Constants.OPTIONAL)) {
            optional = intent.getStringExtra(Constants.OPTIONAL);
        }
        resultReceiver.send(Constants.STATUS_RUNNING, Bundle.EMPTY);
        ResponceModel responceModel = openConnection();
        if (responceModel != null) {
            if (responceModel.getErrorCode() == 200) {
                switch (command) {
                    case GETBANK:
                        saveBank(responceModel.getResponce());
                        resultReceiver.send(Constants.STATUS_BANK_LOADED, Bundle.EMPTY);
                        break;
                    case GETPUNKTS:
                        savePunkt(responceModel.getResponce());
                        resultReceiver.send(Constants.STATUS_PUNKT_LOADED, Bundle.EMPTY);
                        break;
                }
            } else {
                resultReceiver.send(Constants.STATUS_ERROR, Bundle.EMPTY);
            }
        }


    }


    private ResponceModel openConnection() {
        if (ServiceHelper.isOnline(context)) {
            switch (command) {
                case GETPUNKTS:
                    return getKursKz(app.settingsHelper.getCityPosition(context));
                case GETBANK:
                    return getNBKKZ();


            }
        } else {
            resultReceiver.send(Constants.STATUS_ERROR, Bundle.EMPTY);
        }
        return null;
    }

    private void saveBank(String responce) {
        String allNbk = XmlParser.getInstance().parse(responce.toString()).findTag(Constants.TAG_NAME_NBK, new String[]{
                Constants.CURRENCY,
                Constants.MONEY,
                Constants.UPDATE_DATE,
                Constants.CHANGE
        }).toString();
        ArrayList<BankModel> bankModels = new ArrayList<BankModel>();
        int count = allNbk.split("##").length;
        Log.d("AAA", "bank " + allNbk);
        for (int i = 0; i < count; i += 4) {
            bankModels.add(new BankModel(allNbk.split("##")[i].toString(),
                    allNbk.split("##")[i + 2].toString(),
                    allNbk.split("##")[i + 1].toString(),
                    allNbk.split("##")[i + 3].toString()));
        }
        app.databaseHelper.saveBank(context, bankModels);

    }

    private void savePunkt(String responce) {

        try {
            JSONArray jsonArray = new JSONArray(responce);

            Log.d("AAA","resp "+jsonArray.getJSONObject(0).getString("punkt_worktime"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<PunktModel> punktModels = app.parserUtils.getPunktModelsKursKZ(responce);
        app.databaseHelper.savePunkt(getApplicationContext(), punktModels);
    }



    private ResponceModel getNBKKZ() {
        ResponceModel responceModel = null;
        try {
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
            Request.Builder request = new Request.Builder();
            request.url(Constants.KAZ_NBK).get();
            Response response = httpClient.newCall(request.build()).execute();
            responceModel = new ResponceModel(response.code(), response.body().string());
        } catch (Exception e) {

        }
        return responceModel;
    }


    private ResponceModel getKursKz(int city){
        Log.d("AAA","getKursKz");
        ResponceModel responceModel = null;
        try{
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build();
            Request.Builder request = new Request.Builder();
            request.url(Constants.OBMENIKI+Constants.CITYS[city-1]).get();
            Response response = httpClient.newCall(request.build()).execute();
            responceModel = new ResponceModel(response.code(), response.body().string());
        } catch (Exception e){

        }

        return responceModel;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}
