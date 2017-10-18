package kz.mergen.kursvalut.Ui;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.special.ResideMenu.ResideMenu;

import kz.mergen.kursvalut.Adapters.CitysAdapter;
import kz.mergen.kursvalut.App;
import kz.mergen.kursvalut.Models.KursLocation;
import kz.mergen.kursvalut.R;
import kz.mergen.kursvalut.Utils.AppResultReceiver;
import kz.mergen.kursvalut.Utils.LocationService;

public class HomeActivity extends AppCompatActivity implements AppResultReceiver.Receiver {

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout toolbarLayout;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private ImageView toolbarImage;
    private float[] a;
    private ResideMenu resideMenu;
    private Spinner city_list;
    private TextView punkts_view;
    private FragmentTransaction transaction;
    private int city = 1;
    private TextView bankView;
    private TextView address_view;
    private App app;
    private FloatingActionButton show_map_fab;
    private FloatingActionMenu phones_menu_fab;
    private int appBarHeight;
    private boolean isBank = true;
    private boolean isExpandle = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        app = App.getInstance();
        app.serviceHelper.initListener(this);
        city = app.settingsHelper.getCityPosition(HomeActivity.this);
        initViews();
        initBankData();
        Log.d("AAA","homeActivity");
    }

    public void openMenu(){
        resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
    }


    private void initBankData(){
        app.serviceHelper.getBankKurs();

    }
    private void initPunktsData(){
        app.serviceHelper.getKursKzPunkts();
        app.serviceHelper.getMyLocation();
    }

    public void openFragment(BaseFragment fragment){
        transaction = getSupportFragmentManager().beginTransaction();
        if(app.fragmentIsShow(fragment)){
            fragment.initData();
        } else {
            transaction.replace(R.id.frame_container,fragment);
            transaction.commit();
        }

    }
    private void initAlpha(int value){
        a = new float[value];
        float aa = ((float) 1)/value;
        float ab = 0;
        for (int i = 0; i<a.length;i++){
            a[i] = 1-ab;
            ab+=aa;
        }
    }
    private float getAlpha(int alpha){
        int value = Math.abs(alpha);
        if(value<a.length){
            return a[value];
        }
        return 0;
    }
    private void initViews(){
        toolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        toolbarImage = (ImageView)findViewById(R.id.toolbarImage);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        progressBar = (ProgressBar)findViewById(R.id.toolbar_progressbar);
        address_view = (TextView)findViewById(R.id.subTitle);
        show_map_fab = (FloatingActionButton)findViewById(R.id.show_map);
        phones_menu_fab = (FloatingActionMenu)findViewById(R.id.phones_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        initAlpha((appBarLayout.getLayoutParams().height-toolbar.getLayoutParams().height)-30);
        appBarHeight = appBarLayout.getLayoutParams().height;
        resideMenu = new ResideMenu(this,R.layout.reside_left_view,R.layout.reside_left_view);
        resideMenu.setBackground(R.drawable.background);
        resideMenu.attachToActivity(this);
        resideMenu.setUse3D(false);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setScaleValue(0.6f);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenu();
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                toolbarImage.setAlpha(getAlpha(verticalOffset));


                if(getAlpha(verticalOffset)>0.8){
                    appBarLayout.setBackgroundColor(getResources().getColor(R.color.layout_background));
                } else {
                    appBarLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                if(getAlpha(verticalOffset)<0.5){
                    show_map_fab.hide(true);
                    if (isExpandle){

                        supportInvalidateOptionsMenu();
                        isExpandle = false;
                    }
                } else {
                    show_map_fab.show(true);
                    if (!isExpandle){

                        supportInvalidateOptionsMenu();
                        isExpandle = true;
                    }
                }
            }
        });

        bankView = (TextView)resideMenu.getLeftMenuView().findViewById(R.id.bank_view);
        punkts_view = (TextView) resideMenu.getLeftMenuView().findViewById(R.id.punkts_view);
        city_list = (Spinner)resideMenu.getLeftMenuView().findViewById(R.id.city_view);
        CitysAdapter arrayAdapter = new CitysAdapter(HomeActivity.this,getResources().getStringArray(R.array.citys));

        punkts_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPunktsData();
                toolbar.setEnabled(false);
                resideMenu.closeMenu();
            }
        });
        bankView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBankData();
                resideMenu.closeMenu();
            }
        });
        city_list.setAdapter(arrayAdapter);
        city_list.setSelection(city);
        city_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                app.settingsHelper.saveCityPosition(getApplicationContext(),position);
                city = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onLoading() {
        progressBar.setVisibility(View.VISIBLE);
        Log.d("AAA","onloading");
    }

    @Override
    public void onErrorLoaded() {
        progressBar.setVisibility(View.GONE);
        Log.d("AAA","onErrorLoaded");
    }
    private void setInfoBank(String title, String address, String phones){
        if(title!=null){
            toolbarLayout.setTitle(title);
        }
        if(address!=null){
            address_view.setText(address);
        }
        if(phones!=null){
            phones_menu_fab.showMenu(false);
        }else {
            phones_menu_fab.hideMenu(false);
        }


    }
    @Override
    public void onBankLoaded() {
        progressBar.setVisibility(View.GONE);
        appBarLayout.setExpanded(true);
        setInfoBank(getString(R.string.kurs_nbk), null,null);
        isBank = true;
        supportInvalidateOptionsMenu();
        openFragment(app.bankFragment);
    }

    @Override
    public void onPunktLoaded() {
        progressBar.setVisibility(View.GONE);
        appBarLayout.setExpanded(false);
        setInfoBank(getString(R.string.kurs_exchanger),null,null);
        toolbarLayout.setTitle(getString(R.string.kurs_exchanger));
        isBank = false;
        supportInvalidateOptionsMenu();
        openFragment(app.punktsFragment);
    }

    @Override
    public void onLocationLoaded() {

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(menu!=null){
            if(isExpandle){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("AAA","onOptionsItemSelected "+item.getItemId());
        switch (item.getItemId()){
            case R.id.map_view:
                Log.d("AAA","map_view");
                break;
            case R.id.sort_punkt:
                Log.d("AAA","sort_punkt");
                app.punktsFragment.sortByLocation();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isBank){
            getMenuInflater().inflate(R.menu.bank_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.punkt_menu, menu);
        }
        return true;
    }
}
