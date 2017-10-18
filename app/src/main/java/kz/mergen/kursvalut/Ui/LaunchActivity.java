package kz.mergen.kursvalut.Ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import kz.mergen.kursvalut.App;
import kz.mergen.kursvalut.R;
import kz.mergen.kursvalut.Utils.AppResultReceiver;

public class LaunchActivity extends AppCompatActivity implements AppResultReceiver.Receiver{

    private TextView loading;
    private ProgressBar logoProgress;
    private ImageView logoImage;
    private AlphaAnimation alphaAnimation;
    private boolean bankUpdated = false;
    private boolean punktsUpdated = false;
    private App app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        loading = (TextView)findViewById(R.id.logo_loading);
        logoProgress = (ProgressBar)findViewById(R.id.logo_progress);
        logoImage = (ImageView)findViewById(R.id.appLogo);
        app = App.getInstance();
        alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setRepeatCount(100);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        loading.setAnimation(alphaAnimation);
        app.serviceHelper.initListener(this);
        updateData();
    }


    private void showHome(){
        if(bankUpdated&&punktsUpdated){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
    }
    private void updateData(){
        app.serviceHelper.getBankKurs();
        app.serviceHelper.getKursKzPunkts();
    }
    @Override
    public void onLoading() {
        Log.d("AAA","onLoading");
        logoProgress.setAlpha(1);
        loading.setAlpha(1);
        alphaAnimation.start();
    }

    @Override
    public void onErrorLoaded() {
        app.showToastMessage(getString(R.string.error_connection));
        alphaAnimation.cancel();
        logoProgress.setAlpha(0);
        loading.setAlpha(0);
    }

    @Override
    public void onBankLoaded() {
        bankUpdated = true;
        showHome();
    }

    @Override
    public void onPunktLoaded() {
        punktsUpdated = true;
        showHome();
    }

    @Override
    public void onLocationLoaded() {

    }
}
